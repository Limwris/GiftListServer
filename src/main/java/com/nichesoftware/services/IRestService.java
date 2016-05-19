package com.nichesoftware.services;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Person;
import com.nichesoftware.model.User;

import java.util.List;

/**
 * Created by n_che on 28/04/2016.
 */
public interface IRestService {
    List<Person> getAllPersons(final String username) throws GenericException, ServerException;
    List<Person> getPersons(final String username, final String firstnamePerson, final String lastnamePerson) throws GenericException, ServerException;
    void addGift(final String username, int personId, final String giftName, double giftPrice, double allocatedAmount) throws GenericException, ServerException;
    List<Person> getGifts(final String username) throws GenericException, ServerException;
    User createUser(final String username, final String password) throws GenericException, ServerException;
    User authenticate(final String username, final String password) throws GenericException, ServerException;
    void addPerson(final String username, final String firstnamePerson, final String lastnamePerson) throws GenericException, ServerException;
}
