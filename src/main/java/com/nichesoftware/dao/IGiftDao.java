package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;

import java.util.List;

/**
 * Created by n_che on 02/05/2016.
 */
public interface IGiftDao {
    String ID_ROW = "id";
    String NAME_ROW = "name";
    String PRICE_ROW = "price";
    String AMOUNT_ROW = "allocatedAmount";
    String ROOM_ID_ROW = "roomId";
    String ROOM_NAME_ROW = "roomName";

    void addGift(User user, Room room, final String giftName,
                 final double giftPrice, final double allocatedAmount) throws ServerException, GenericException;
    void updateGift(User user, Gift gift) throws ServerException, GenericException;
    void getGifts(User user, Room room) throws ServerException, GenericException;
}
