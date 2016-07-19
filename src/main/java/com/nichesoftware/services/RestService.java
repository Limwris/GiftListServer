package com.nichesoftware.services;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nichesoftware.controllers.TokenUtils;
import com.nichesoftware.dao.*;
import com.nichesoftware.dto.InvitationDto;
import com.nichesoftware.dto.NotificationDto;
import com.nichesoftware.exceptions.*;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Invitation;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;
import com.nichesoftware.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by n_che on 27/04/2016.
 */
@Service
public class RestService implements IRestService {
    private static final Logger logger = LoggerFactory.getLogger(RestService.class.getSimpleName());
    @Autowired
    private IGiftDao giftDao;
    @Autowired
    private IRoomDao roomDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IInvitationDao invitationDao;

    @Override
    public Gift addGift(final String username, int roomId, final String giftName,
                        double giftPrice, double allocatedAmount) throws GenericException, ServerException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

        // Ajoute toutes les salles à l'utilisateur
        roomDao.getAllRooms(user);

        Room room = user.getRoomById(roomId);
        if (room == null) {
            throw new GenericException("La salle n'est pas présente dans la liste de l'utilisateur.");
        }

        return giftDao.addGift(user, room, giftName, giftPrice, allocatedAmount);
    }

    @Override
    public List<Gift> getGifts(final String username, int roomId) throws GenericException, ServerException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

        roomDao.getRoom(user, roomId);
        Room room = user.getRoomById(roomId);
        giftDao.getGifts(user, room);
        return room.getGiftList();
    }

    @Override
    public void deleteGift(String username, int giftId) throws ServerException, GenericException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

        giftDao.deleteGift(user, giftId);
    }

    @Override
    public Gift updateGift(String username, int roomId, int giftId, double allocatedAmount) throws ServerException, GenericException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

        roomDao.getRoom(user, roomId);
        Room room = user.getRoomById(roomId);
        giftDao.getGifts(user, room);

        Gift gift = room.getGiftById(giftId);
        if (gift == null) {
            throw new GenericException("Le cadeau n'existe pas dans la salle passée en paramètre");
        }
        // Modifier le montant alloué par l'utilisateur
        gift.getAmountByUser().put(user.getId(), allocatedAmount);
        return giftDao.updateGift(user, gift);
    }

    @Override
    public User createUser(final String username, final String password, final String phoneNumber) throws GenericException, ServerException {
        logger.info("[Entering] createUser");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        userDao.createUser(user);

        // Il est nécessaire de venir chercher l'utilisateur depuis la base car ce dernier
        //   contient un certain nombre d'informations nécessaires pour générer le token.
        return userDao.findByUsername(username);
    }

    /**
     * Basic HTTP authentication
     * Sources:   - http://java2novice.com/restful-web-services/http-basic-authentication/
     *            - http://blog.ineat-conseil.fr/2013/01/restful-authentication/
     *            - http://blog.jdriven.com/2014/10/stateless-spring-security-part-1-stateless-csrf-protection/
     *            - http://blog.jdriven.com/2014/10/stateless-spring-security-part-2-stateless-authentication/
     *            - http://stackoverflow.com/questions/10826293/restful-authentication-via-spring
     * @param username - Identifiant de l'utilisateur
     * @param password - Mot de passe de l'utilisateur
     * @return         - Utilisateur correspondant au username//password passé en paramètre
     * @throws GenericException
     * @throws ServerException
     */
    @Override
    public User authenticate(final String username, final String password) throws GenericException, ServerException {
        logger.info("[Entering] authenticate");
        User user = userDao.findByUsername(username);

        if(user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationException();
        }

        return user;
    }

    @Override
    public List<User> retreiveAvailableUsers(List<String> phoneNumbers, int roomId) throws GenericException, ServerException {
        logger.info("[Entering] retreiveAvailableUsers");

        List<User> users = userDao.retreiveAllUsers();

        for (Iterator<User> iter = users.listIterator(); iter.hasNext(); ) {
            User user = iter.next();
            // - Si le numéro de téléphone de l'utilisateur n'est pas présent dans la liste des numéros
            // - Ou si l'utilisateur est déjà dans la salle passée en paramètre
            if (!phoneNumbers.contains(user.getPhoneNumber()) || roomDao.hasRoom(user, roomId)) {
                iter.remove();
            }
        }

        return users;
    }

    @Override
    public void updateGcmToken(final String username, final String gcmToken) throws GenericException, ServerException {
        logger.info("[Entering] updateGcmToken");
        User user = userDao.findByUsername(username);
        user.setGcmId(gcmToken);
        userDao.updateUser(user);
    }

    @Override
    public void inviteUserToRoom(final String usernameToInvite, int roomId) throws ServerException, GenericException {
        logger.info("[Entering] inviteUserToRoom");
        User userToInvite = userDao.findByUsername(usernameToInvite);
        if (StringUtils.isEmpty(userToInvite.getGcmId())) {
            logger.error("[Entering] inviteUserToRoom - GCM id n'existe pas pour cet utilisateur.");
            throw new InvitationException("L'utilisateur ne peut être contacté actuellement...");
        }

        Room room = roomDao.getRoom(roomId);
        Date expirationDate = invitationDao.inviteToRoom(userToInvite, room);

        Invitation invitation = new Invitation();
        invitation.setExpireDate(expirationDate);
        invitation.setRoom(room);
        invitation.setInvitedUser(userToInvite);

        Gson gson = new GsonBuilder().create();

        Sender sender = new Sender(GcmConstants.API_KEY);

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setTitle("Invitation");
        notificationDto.setBody(String.format("Vous avez ete invite a la salle %s", room.getOccasion()));
        notificationDto.setClickAction(GcmConstants.OPEN_INVITE_TO_ROOM_ACTIVITY);

        Message gcmMessage = new Message.Builder().timeToLive(180)
                .delayWhileIdle(true)
                .addData(GcmConstants.NOTIFICATION, gson.toJson(notificationDto))
                .addData(GcmConstants.DATA, gson.toJson(invitation)).build();

        logger.info(String.format("[Entering] inviteUserToRoom | message : %s / to : %s", gcmMessage, userToInvite.getGcmId()));

        try {
            Result result = sender.send(gcmMessage, userToInvite.getGcmId(), 1);
            logger.info(String.format("[Entering] inviteUserToRoom | result : %s", result.toString()));
        } catch (IOException e) {
            throw new InvitationException();
        }

    }

    @Override
    public void acceptInvitationToRoom(final String username, int roomId) throws ServerException, GenericException {
        logger.info("[Entering] acceptInvitationToRoom");
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

        Room room = roomDao.getRoom(roomId);
        invitationDao.acceptInvitation(user, room);
    }

    @Override
    public List<Room> getPendingInvitation(String username) throws GenericException, ServerException {
        logger.info("[Entering] acceptInvitationToRoom");
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }
        List<Integer> roomIds = invitationDao.checkForPendingInvitation(user);

        List<Room> retVal = new ArrayList<>();
        for (int roomId : roomIds) {
            retVal.add(roomDao.getRoom(roomId));
        }
        return retVal;
    }

    @Override
    public List<Room> getRooms(final String username) throws ServerException, GenericException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

        // Ajoute toutes les salles à l'utilisateur
        roomDao.getAllRooms(user);

        return user.getRooms();
    }

    @Override
    public List<Room> getCompleteRooms(final String username) throws ServerException, GenericException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

        // Ajoute toutes les salles à l'utilisateur
        roomDao.getAllRooms(user);
        for (Room room : user.getRooms()) {
            giftDao.getGifts(user, room);
        }

        return user.getRooms();
    }

    @Override
    public Room getRoom(int roomId) throws ServerException, GenericException {
        Room room = roomDao.getRoom(roomId);
        // Not needed information
        room.setGiftList(null);
        return room;
    }

    @Override
    public List<Room> deleteRoom(String username, int roomId) throws ServerException, GenericException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

        roomDao.getRoom(user, roomId);
        Room room = user.getRoomById(roomId);

        if (room == null) {
            throw new GenericException("La salle n'est pas présente dans la liste de l'utilisateur.");
        }

        roomDao.deleteRoom(room, user);

        // On rafraîchit toutes les salles à l'utilisateur
        user.setRooms(null);
        roomDao.getAllRooms(user);
        for (Room temp : user.getRooms()) {
            giftDao.getGifts(user, temp);
        }

        return user.getRooms();
    }

    @Override
    public Room addRoom(final String username, final String roomName,
                        final String occasion) throws ServerException, GenericException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }
        return roomDao.saveRoom(user, roomName, occasion);
    }

    public IGiftDao getGiftDao() {
        return giftDao;
    }

    public void setGiftDao(IGiftDao giftDao) {
        this.giftDao = giftDao;
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setRoomDao(IRoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public IRoomDao getRoomDao() {
        return roomDao;
    }

    public void setInvitationDao(IInvitationDao invitationDao) {
        this.invitationDao = invitationDao;
    }

    public IInvitationDao getInvitationDao() {
        return this.invitationDao;
    }
}
