package com.nichesoftware.dto;

import java.io.Serializable;

/**
 * Created by n_che on 09/09/2016.
 */
public class TokenDTO implements Serializable {
    // Fields   --------------------------------------------------------------------------------------------------------
    /**
     * Token de l'utilisateur
     */
    private String token;

    // Getters   -------------------------------------------------------------------------------------------------------
    /**
     * Getter sur le token de l'utilisateur
     * @return
     */
    public String getToken() {
        return token;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    /**
     * Setter sur le token de l'utilisateur
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
