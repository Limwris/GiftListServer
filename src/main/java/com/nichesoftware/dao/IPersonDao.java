package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Person;
import com.nichesoftware.model.User;

import java.util.List;

/**
 * Created by n_che on 27/04/2016.
 */
public interface IPersonDao {
    String ID_ROW = "id";
    String FIRST_NAME_ROW = "firstname";
    String LAST_NAME_ROW = "lastname";

    void addPersonToUser(User user, Person person) throws ServerException, GenericException;
    List<Person> getPersons(User user, final String firstnamePerson, final String lastnamePerson);
    //////////////////
    //     CRUD     //
    //////////////////
    // Create
    void savePerson(User user, final String firstName, final String lastName) throws ServerException, GenericException;
    // Retreive
//    Person getPerson(User user, final String id) throws ServerException, GenericException;
    List<Person> findPersons(final String firstName, final String lastName) throws ServerException, GenericException;
    List<Person> getAllPersons(final String username) throws ServerException, GenericException;
    // Update
    boolean updatePerson(User user, Person person) throws ServerException, GenericException;
    // Delete
    boolean deletePerson(User user, Person person) throws ServerException, GenericException;
}
