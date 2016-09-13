package com.nichesoftware.service;

import com.nichesoftware.User;
import com.nichesoftware.dao.IUserDAO;
import com.nichesoftware.service.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by n_che on 08/09/2016.
 */
@Service
public class AuthenticationService implements IAuthenticationService {
    // Fields   --------------------------------------------------------------------------------------------------------
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class.getSimpleName());

    @Autowired
    private IUserDAO userDAO;

    // Methods   -------------------------------------------------------------------------------------------------------
    @Override
    public User createUser(final String username, final String password, final String phoneNumber) throws Exception {
        logger.info("[Entering] createUser");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);

        userDAO.createUser(user);

        // Il est nécessaire de venir chercher l'utilisateur depuis la base car ce dernier
        //   contient un certain nombre d'informations nécessaires pour générer le token.
        return userDAO.findByUsername(username);
    }

    @Override
    public User findByUsername(final String username) throws Exception {
        logger.info("[Entering] authenticate");
        User user = userDAO.findByUsername(username);

        return user;
    }

    /**
     * Basic HTTP authentication
     * Sources:   - http://java2novice.com/restful-web-services/http-basic-authentication/
     *            - http://blog.ineat-conseil.fr/2013/01/restful-authentication/
     *            - http://blog.jdriven.com/2014/10/stateless-spring-security-part-1-stateless-csrf-protection/
     *            - http://blog.jdriven.com/2014/10/stateless-spring-security-part-2-stateless-authentication/
     *            - http://stackoverflow.com/questions/10826293/restful-authentication-via-spring
     * @param username - Identifiant de l'utilisateur
     * @param password - Mot de passe de l'utilisateur
     * @return         - Utilisateur correspondant au username//password passé en paramètre
     * @throws Exception
     */
    @Override
    public User authenticate(final String username, final String password) throws Exception {
        logger.info("[Entering] authenticate");
        User user = userDAO.findByUsername(username);

        if(user == null || !user.getPassword().equals(password)) {
            throw new BadRequestException();
        }

        return user;
    }

//    @Override
//    public List<User> retreiveAvailableUsers(List<String> phoneNumbers, int roomId) throws DAOException {
//        logger.info("[Entering] retreiveAvailableUsers");
//
//        List<User> users = userDAO.retreiveAllUsers();
//
//        for (Iterator<User> iter = users.listIterator(); iter.hasNext(); ) {
//            User user = iter.next();
//            // - Si le numéro de téléphone de l'utilisateur n'est pas présent dans la liste des numéros
//            // - Ou si l'utilisateur est déjà dans la salle passée en paramètre
//            if (!phoneNumbers.contains(user.getPhoneNumber()) || roomDao.hasRoom(user, roomId)) {
//                iter.remove();
//            }
//        }
//
//        return users;
//    }

    @Override
    public void updateGcmToken(final String username, final String gcmToken) throws Exception {
        logger.info("[Entering] updateGcmToken");
        User user = userDAO.findByUsername(username);
        user.setGcmId(gcmToken);
        userDAO.updateUser(user);
    }

    // Getters   -------------------------------------------------------------------------------------------------------
    public IUserDAO getUserDAO() {
        return userDAO;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
