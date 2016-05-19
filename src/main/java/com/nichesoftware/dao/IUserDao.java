package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.User;

/**
 * Created by n_che on 28/04/2016.
 */
public interface IUserDao {
    String ID_ROW = "id";
    String USERNAME_ROW = "username";
    String PASSWORD_ROW = "password";
    String CREATION_DATE_ROW = "creation_date";
    String PERSONS_ROW = "persons";

    // Create user
    void createUser(User user) throws GenericException, ServerException;
    // Retreive user
    User findByUsername(String username) throws GenericException, ServerException;
}
