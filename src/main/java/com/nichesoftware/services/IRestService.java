package com.nichesoftware.services;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.NotAuthorizedException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;

import java.util.List;

/**
 * Created by n_che on 28/04/2016.
 */
public interface IRestService {
    /******************************************************************************************************************/
    /********************                              Gift                                        ********************/
    /******************************************************************************************************************/
    Gift addGift(final String username, int roomId, final String giftName, double giftPrice, double allocatedAmount)
            throws GenericException, ServerException;
    List<Gift> getGifts(final String username, int roomId) throws GenericException, ServerException;
    void deleteGift(final String username, int giftId) throws ServerException, GenericException;
    Gift updateGift(final String username, int roomId, int giftId, double allocatedAmount) throws ServerException, GenericException;

    /******************************************************************************************************************/
    /********************                              Room                                        ********************/
    /******************************************************************************************************************/
    Room addRoom(final String username, final String roomName, final String occasion)
            throws ServerException, GenericException;
    void inviteUserToRoom(final String usernameToInvite, int roomId) throws ServerException, GenericException;
    void acceptInvitationToRoom(final String username, final String invitationToken, int roomId) throws ServerException, GenericException, NotAuthorizedException;
    List<Room> getRooms(final String username) throws ServerException, GenericException;
    List<Room> getCompleteRooms(final String username) throws ServerException, GenericException;
    void deleteRoom(final String username, int roomId) throws ServerException, GenericException;

    /******************************************************************************************************************/
    /********************                              User                                        ********************/
    /******************************************************************************************************************/
    User createUser(final String username, final String password, final String phoneNumber) throws GenericException, ServerException;
    User authenticate(final String username, final String password) throws GenericException, ServerException;
    List<User> retreiveAvailableUsers(final List<String> phoneNumber, int roomId) throws GenericException, ServerException;
    void updateGcmToken(final String username, final String gcmToken) throws GenericException, ServerException;
}
