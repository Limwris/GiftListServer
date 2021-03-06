package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;

/**
 * Created by Kattleya on 22/05/2016.
 */
public interface IRoomDao {
    String ID_ROW = "idRoom";
    String NAME_ROW = "roomName";
    String OCCASION_ROW = "occasion";

    //////////////////
    //     CRUD     //
    //////////////////
    // Create
    Room saveRoom(User user, final String roomName, final String occasion) throws ServerException, GenericException;
    // Retreive
    void getRoom(User user, final int id) throws ServerException, GenericException;
    boolean hasRoom(User user, int roomId) throws ServerException, GenericException;
    Room getRoom(int id) throws ServerException, GenericException;
    void getAllRooms(User user) throws ServerException, GenericException;
    // Update
    Room updateRoom(Room room, User user) throws ServerException, GenericException;
    // Delete
    void deleteRoom(Room room, User user) throws ServerException, GenericException;
}
