package com.nichesoftware.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by n_che on 27/04/2016.
 */
public class User {
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
     * JsonIgnore protects from Jackson serializing this field.
     */
    @JsonIgnore
    private String password;
    /**
     * Liste des salles
     */
    @JsonIgnore
    private List<Room> rooms = new ArrayList<>();
    /**
     * Date de création de l'utilisateur
     * Utile pour générer le token
     */
    private Date creationDate;

    /**
     * Consctructeur par défaut
     */
    public User() {
        creationDate = new Date();
    }

    /**
     * Getter sur l'identifiant unique de l'utilisateur
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter sur l'identifiant unique de l'utilisateur
     * @param id - identfiant de l'utilisateur
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter sur l'identifiant de l'utilisateur
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter sur l'identifiant de l'utilisateur
     * @param username - identfiant de l'utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter sur le mot de passe de l'utilisateur
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter sur le mot de passe de l'utilisateur
     * @param password - mot de passe de l'utilisateur
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Getter sur la liste des salles
     * @return rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Setter sur la liste des salles
     * @param rooms - liste des salles de l'utilisateur
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Recherche une salle dans la liste des salles de l'utilisateur
     * @param roomId - identifiant de la salle recherchée
     * @return room  - salle correspondant à l'identifiant passé en paramètre, nul sinon
     */
    public Room getRoomById(final int roomId) {
        for (Room room : rooms) {
            if (room.getId() == roomId) {
                return room;
            }
        }
        return null;
    }

    /**
     * Ajoute une salle à la liste
     * @param room - Salle à ajouter à la liste de l'utilisateur
     */
    public void addRoom(Room room) {
        if (rooms == null) {
            rooms = new ArrayList<>();
        }
        rooms.add(room);
    }
}
