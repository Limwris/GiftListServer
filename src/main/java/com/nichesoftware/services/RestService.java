package com.nichesoftware.services;

import com.nichesoftware.dao.*;
import com.nichesoftware.exceptions.*;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by n_che on 27/04/2016.
 */
@Service
public class RestService implements IRestService {
    @Autowired
    private IGiftDao giftDao;
    @Autowired
    private IRoomDao roomDao;
    @Autowired
    private IUserDao userDao;

    @Override
    public void addGift(final String username, int roomId, final String giftName,
                        double giftPrice, double allocatedAmount) throws GenericException, ServerException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

        user.setRooms(roomDao.getAllRooms(user));

        Room room = user.getRoomById(roomId);
        if (room == null) {
            throw new GenericException("La salle n'est pas présente dans la liste de l'utilisateur.");
        }

        giftDao.addGift(user, room, giftName, giftPrice, allocatedAmount);
    }

    @Override
    public List<Gift> getGifts(final String username, int roomId) throws GenericException, ServerException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

        Room room = roomDao.getRoom(user, roomId);
        giftDao.getGifts(user, room);
        return user.getRoomById(roomId).getGiftList();
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
    public User createUser(final String username, final String password) throws GenericException, ServerException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
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
        User user = userDao.findByUsername(username);

        if(user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationException();
        }

        return user;
    }

    @Override
    public void inviteUserToRoom(final String username, int roomId) throws ServerException, GenericException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }
        Room room = roomDao.getRoom(user, roomId);
        roomDao.inviteUserToRoom(user, room);
    }

    @Override
    public List<Room> getRooms(final String username) throws ServerException, GenericException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }
        return roomDao.getAllRooms(user);
    }

    @Override
    public void deleteRoom(String username, int roomId) throws ServerException, GenericException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }
        Room room = roomDao.getRoom(user, roomId);
        if (room == null) {
            throw new GenericException("La salle n'est pas présente dans la liste de l'utilisateur.");
        }

        roomDao.deleteRoom(room, user);
    }

    @Override
    public void addRoom(final String username, final String roomName,
                        final String occasion) throws ServerException, GenericException {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }
        roomDao.saveRoom(user, roomName, occasion);
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
}
