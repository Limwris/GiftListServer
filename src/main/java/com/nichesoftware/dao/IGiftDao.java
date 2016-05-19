package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Person;
import com.nichesoftware.model.User;

/**
 * Created by n_che on 02/05/2016.
 */
public interface IGiftDao {
    String ID_ROW = "id";
    String NAME_ROW = "name";
    String PRICE_ROW = "price";
    String AMOUNT_ROW = "allocated_amount";
    String PERSON_ID_ROW = "person_id";
    String PERSON_FIRST_NAME_ROW = "firstname";
    String PERSON_LAST_NAME_ROW = "lastname";

//    List<Gift> getGifts(Person person) throws ServerException, GenericException;
    void getGifts(User user) throws ServerException, GenericException;
    void addGift(User user, Person person, final String giftName,
                 final double giftPrice, final double allocatedAmount) throws ServerException, GenericException;
}
