package com.nichesoftware.services;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;

import java.util.List;

/**
 * Created by n_che on 28/04/2016.
 */
public interface IRestService {
    void addGift(final String username, int roomId, final String giftName, double giftPrice, double allocatedAmount) throws GenericException, ServerException;
    List<Gift> getGifts(final String username, int roomId) throws GenericException, ServerException;
    User createUser(final String username, final String password) throws GenericException, ServerException;
    User authenticate(final String username, final String password) throws GenericException, ServerException;
    void inviteUserToRoom(final String username, int roomId) throws ServerException, GenericException;
    List<Room> getRooms(final String username) throws ServerException, GenericException;
}
