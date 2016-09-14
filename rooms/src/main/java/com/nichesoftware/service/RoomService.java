package com.nichesoftware.service;

import com.nichesoftware.Gift;
import com.nichesoftware.Room;
import com.nichesoftware.User;
import com.nichesoftware.dao.DAOException;
import com.nichesoftware.dao.IRoomDAO;
import com.nichesoftware.service.exception.ConflitException;
import com.nichesoftware.service.exception.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by n_che on 12/09/2016.
 */
@Service
public class RoomService implements IRoomService {
    // Fields   --------------------------------------------------------------------------------------------------------
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class.getSimpleName());

    @Autowired
    private IRoomDAO roomDAO;

    // Methods   -------------------------------------------------------------------------------------------------------
    @Override
    public Gift addGift(String username, int roomId, String giftName, double giftPrice, double allocatedAmount, String description) throws Exception {
        return null;
    }

    @Override
    public Gift getGift(String username, int roomId) throws Exception {
        return null;
    }

    @Override
    public List<Gift> getGifts(String username, int roomId) throws Exception {
        return null;
    }

    @Override
    public Gift updateGift(String username, int roomId, int giftId, double allocatedAmount, String description) throws Exception {
        return null;
    }

    @Override
    public void deleteGift(String username, int giftId) throws Exception {

    }

    @Override
    public Room addRoom(User user, final String roomName, final String occasion) throws Exception {
        Room room = null;
        try {
            room = roomDAO.saveRoom(user, roomName, occasion);
        } catch (DAOException e) {
            ConflitException exception = new ConflitException();
            exception.setErrorCode(e.getCode());
            exception.setErrorMessage("La création de la salle a échoué, ou l'ID n'a pu être obtenu.");
            throw exception;
        }
        return room;
    }

    @Override
    public List<Room> getRooms(String username) throws Exception {
        return null;
    }

    @Override
    public Room getRoom(int roomId) throws Exception {
        return null;
    }

    @Override
    public Room updateRoom(String username, String roomName, String occasion) throws Exception {
        return null;
    }

    @Override
    public List<Room> deleteRoom(String username, int roomId) throws Exception {
        return null;
    }

    // Getters   -------------------------------------------------------------------------------------------------------
    public IRoomDAO getRoomDAO() {
        return roomDAO;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    public void setRoomDAO(IRoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }
}
