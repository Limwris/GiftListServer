package com.nichesoftware.service;

import com.nichesoftware.User;

import java.util.List;

/**
 * Created by n_che on 08/09/2016.
 */
public interface IAuthenticationService {
    /******************************************************************************************************************/
    /********************                              User                                        ********************/
    /******************************************************************************************************************/
    User createUser(final String username, final String password, final String phoneNumber) throws Exception;
    User findByUsername(final String username) throws Exception;
    User authenticate(final String username, final String password) throws Exception;
//    List<User> retreiveAvailableUsers(final List<String> phoneNumber, int roomId) throws Exception;
    void updateGcmToken(final String username, final String gcmToken) throws Exception;
}
