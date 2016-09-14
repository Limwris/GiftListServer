package com.nichesoftware.service;

import com.nichesoftware.Gift;
import com.nichesoftware.Room;
import com.nichesoftware.User;

import java.util.List;

/**
 * Created by n_che on 12/09/2016.
 */
public interface IRoomService {
    /******************************************************************************************************************/
    /********************                              Gift                                        ********************/
    /******************************************************************************************************************/
    Gift addGift(final String username, int roomId, final String giftName, double giftPrice,
                 double allocatedAmount, final String description) throws Exception;
    Gift getGift(final String username, int roomId) throws Exception;
    List<Gift> getGifts(final String username, int roomId) throws Exception;
    Gift updateGift(final String username, int roomId, int giftId,
                    double allocatedAmount, final String description) throws Exception;
    void deleteGift(final String username, int giftId) throws Exception;

    /******************************************************************************************************************/
    /********************                              Room                                        ********************/
    /******************************************************************************************************************/
    Room addRoom(User user, final String roomName, final String occasion) throws Exception;
    List<Room> getRooms(final String username) throws Exception;
    Room getRoom(int roomId) throws Exception;
    Room updateRoom(final String username, final String roomName, final String occasion) throws Exception;
    List<Room> deleteRoom(final String username, int roomId) throws Exception;
}
