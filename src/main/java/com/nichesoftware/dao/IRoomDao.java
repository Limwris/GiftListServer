package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;

import java.util.List;

/**
 * Created by Kattleya on 22/05/2016.
 */
public interface IRoomDao {
    String ID_ROW = "idRoom";
    String NAME_ROW = "roomName";
    String OCCASION_ROW = "occasion";

    void inviteUserToRoom(User user, Room room) throws ServerException, GenericException;
    //////////////////
    //     CRUD     //
    //////////////////
    // Create
    void saveRoom(User user, final String roomName, final String occasion) throws ServerException, GenericException;
    // Retreive
    Room getRoom(User user, final int id) throws ServerException, GenericException;
    List<Room> getAllRooms(User user) throws ServerException, GenericException;
    // Update
    boolean updateRoom(Room room, User user) throws ServerException, GenericException;
    // Delete
    boolean deleteRoom(Room room, User user) throws ServerException, GenericException;
}
