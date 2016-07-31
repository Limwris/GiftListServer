package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;

/**
 * Created by n_che on 02/05/2016.
 */
public interface IGiftDao {
    String ID_ROW = "idGifts";
    String NAME_ROW = "name";
    String DESCRIPTION_ROW = "description";
    String PRICE_ROW = "price";
    String URL_ROW = "url";
    String AMOUNT_ROW = "allocatedAmount";
    String ROOM_ID_ROW = "roomId";

    Gift addGift(User user, Room room, final String giftName, final double giftPrice,
                 final double allocatedAmount, final String description) throws ServerException, GenericException;
    Gift updateGift(User user, Gift gift) throws ServerException, GenericException;
    void getGifts(User user, Room room) throws ServerException, GenericException;
    Gift getGift(User user, final int giftId) throws ServerException, GenericException ;
    void deleteGift(User user, final int giftId) throws ServerException, GenericException ;
}
