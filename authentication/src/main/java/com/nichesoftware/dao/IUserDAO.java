package com.nichesoftware.dao;

import com.nichesoftware.User;

import java.util.List;

/**
 * Created by n_che on 09/09/2016.
 */
public interface IUserDAO {
    // Fields   --------------------------------------------------------------------------------------------------------
    String ID_ROW = "idUser";
    String USERNAME_ROW = "username";
    String PASSWORD_ROW = "password";
    String PHONE_NUMBER_ROW = "phoneNumber";
    String GCM_ID_ROW = "gcmId";
    String CREATION_DATE_ROW = "creationDate";

    // Methods   -------------------------------------------------------------------------------------------------------
    /**
     * Permet de créer une entrée dans la base de données
     * @param user
     */
    void createUser(User user) throws DAOException;
    /**
     * Permet de récupérer un utilisateur via son identifiant
     * @param username
     * @return User
     */
    User findByUsername(final String username) throws DAOException;
    /**
     * Permet de récupérer l'ensemble des utilisateurs
     * @return List<User>
     */
    List<User> retreiveAllUsers() throws DAOException;
    /**
     * Permet de mettre à jour les données d'une entrée dans la base
     * @param user
     */
    void updateUser(User user) throws DAOException;
}
