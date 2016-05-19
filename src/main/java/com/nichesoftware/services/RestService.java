package com.nichesoftware.services;

import com.nichesoftware.dao.*;
import com.nichesoftware.exceptions.*;
import com.nichesoftware.model.Person;
import com.nichesoftware.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by n_che on 27/04/2016.
 */
@Service
public class RestService implements IRestService {
    @Autowired
    private IGiftDao giftDao;
    @Autowired
    private IPersonDao personDao;
    @Autowired
    private IUserDao userDao;

    @Override
    public List<Person> getAllPersons(final String username) throws GenericException, ServerException {
//        IPersonDao personDao = new PersonDao();
        return personDao.getAllPersons(username);
    }

    @Override
    public List<Person> getPersons(String username, String firstnamePerson, String lastnamePerson) throws GenericException, ServerException {
//        IUserDao userDao = new UserDao();
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

//        IPersonDao personDao = new PersonDao();
        return personDao.getPersons(user, firstnamePerson, lastnamePerson);
    }

    @Override
    public void addGift(final String username, int personId, final String giftName, double giftPrice, double allocatedAmount) throws GenericException, ServerException {
//        IUserDao userDao = new UserDao();
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

//        IPersonDao personDao = new PersonDao();
        user.setPersons(personDao.getAllPersons(username));

        Person person = user.getPersonById(personId);
        if (person == null) {
            throw new GenericException("La personne n'est pas présente dans la liste de l'utilisateur.");
        }

//        IGiftDao giftDao = new GiftDao();
        giftDao.addGift(user, person, giftName, giftPrice, allocatedAmount);
    }

    @Override
    public List<Person> getGifts(final String username) throws GenericException, ServerException {
//        IUserDao userDao = new UserDao();
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

//        IGiftDao giftDao = new GiftDao();
        giftDao.getGifts(user);
        return user.getPersons();
    }

    @Override
    public User createUser(String username, String password) throws GenericException, ServerException {
//        IUserDao userDao = new UserDao();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userDao.createUser(user);

        // Il est nécessaire de venir chercher l'utilisateur depuis la base car ce dernier
        //   contient un certain nombre d'informations nécessaires pour générer le token.
        return userDao.findByUsername(username);
    }

    /**
     * Basic HTTP authentication
     * Sources:   - http://java2novice.com/restful-web-services/http-basic-authentication/
     *            - http://blog.ineat-conseil.fr/2013/01/restful-authentication/
     *            - http://blog.jdriven.com/2014/10/stateless-spring-security-part-1-stateless-csrf-protection/
     *            - http://blog.jdriven.com/2014/10/stateless-spring-security-part-2-stateless-authentication/
     *            - http://stackoverflow.com/questions/10826293/restful-authentication-via-spring
     * @param username
     * @param password
     * @return
     * @throws GenericException
     * @throws ServerException
     */
    @Override
    public User authenticate(String username, String password) throws GenericException, ServerException {

//        IUserDao userDao = new UserDao();
        User user = userDao.findByUsername(username);

        if(user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationException();
        }

        return user;
    }

    @Override
    public void addPerson(String username, String firstnamePerson, String lastnamePerson) throws GenericException, ServerException {
//        IUserDao userDao = new UserDao();
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new AuthenticationException();
        }

//        IPersonDao personDao = new PersonDao();
        personDao.savePerson(user, firstnamePerson, lastnamePerson);
    }

    public IGiftDao getGiftDao() {
        return giftDao;
    }

    public void setGiftDao(IGiftDao giftDao) {
        this.giftDao = giftDao;
    }

    public IPersonDao getPersonDao() {
        return personDao;
    }

    public void setPersonDao(IPersonDao personDao) {
        this.personDao = personDao;
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
}
