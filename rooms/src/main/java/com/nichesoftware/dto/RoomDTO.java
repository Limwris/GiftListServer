package com.nichesoftware.dto;

import java.io.Serializable;

/**
 * Created by n_che on 12/09/2016.
 */
public class RoomDTO implements Serializable {
    // Fields   --------------------------------------------------------------------------------------------------------
    /**
     * Identifiant de la salle
     */
    private int roomId;
    /**
     * Nom de la salle
     */
    private String roomName;
    /**
     * Prénom de la personne concernée par la salle
     */
    private String firstName;
    /**
     * Nom de la personne concernée par la salle
     */
    private String lastName;
    /**
     * Occasion du cadeau
     */
    private String occasion;

    // Getters   -------------------------------------------------------------------------------------------------------
    /**
     * Getter sur l'identifiant de la salle
     * @return roomId
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Getter sur le nom de la salle
     * @return roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Getter sur l'occasion du cadeau
     * @return occasion
     */
    public String getOccasion() {
        return occasion;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    /**
     * Setter sur l'identifiant de la salle
     * @param roomId
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Setter sur le nom de la salle
     * @param roomName
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Setter sur l'occasion du cadeau
     * @param occasion
     */
    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }
}
