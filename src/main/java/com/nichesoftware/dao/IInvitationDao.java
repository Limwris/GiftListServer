package com.nichesoftware.dao;

import com.nichesoftware.dto.InvitationDto;
import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Invitation;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Kattleya on 17/07/2016.
 */
public interface IInvitationDao {
    String ROOM_ID_ROW = "roomId";
    String USER_ID_ROW = "userId";
    String EXPIRATION_DATE_ROW = "expirationDate";

    //////////////////
    //     CRUD     //
    //////////////////
    Date inviteToRoom(User user, Room room) throws ServerException, GenericException;
    List<Integer> checkForPendingInvitation(User user) throws ServerException, GenericException;
    void acceptInvitation(User user, Room room) throws ServerException, GenericException;
    void deleteInvitation(User user, Room room) throws ServerException, GenericException;
}
