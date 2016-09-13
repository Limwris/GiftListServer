package com.nichesoftware.dto;

import java.io.Serializable;

/**
 * Created by n_che on 29/04/2016.
 */
public class UserDTO implements Serializable {
    // Fields   --------------------------------------------------------------------------------------------------------
    /**
     * Identifiant unique de l'utilisateur
     */
    private int id;
    /**
     * Identifiant de l'utilisateur
     */
    private String username;
    /**
     * Mot de passe de l'utilisateur
     */
    private String password;
    /**
     * Numéro de téléphone de l'utilisateur
     */
    private String phoneNumber;

    // Getters   -------------------------------------------------------------------------------------------------------
    /**
     * Getter sur l'identifiant unique de l'utilisateur
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter sur l'identifiant de l'utilisateur
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter sur le mot de passe de l'utilisateur
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter sur le numéro de téléphone
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    /**
     * Setter sur l'identifiant unique de l'utilisateur
     * @param id - identfiant de l'utilisateur
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter sur l'identifiant de l'utilisateur
     * @param username - identfiant de l'utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter sur le mot de passe de l'utilisateur
     * @param password - mot de passe de l'utilisateur
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter sur le numéro de téléphone
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
