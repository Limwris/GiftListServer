package com.nichesoftware.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kattleya on 22/05/2016.
 */
public class Room {
    /**
     * Identifiant unique de la salle
     */
    private int id;
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
    /**
     * Liste des cadeaux associés à cette personne
     */
    private List<Gift> giftList = new ArrayList<Gift>();


    /**
     * Contructeur par défaut
     * @param id
     * @param roomName
     * @param occasion
     */
    public Room(final int id, final String roomName, final String occasion) {
        this.id = id;
        this.roomName = roomName;
        this.occasion = occasion;
    }

    /**
     * Contructeur avec liste de cadeau
     * @param id
     * @param roomName
     * @param occasion
     * @param gifts
     */
    public Room(final int id, final String roomName, final String occasion, List<Gift> gifts) {
        this.id = id;
        this.roomName = roomName;
        this.occasion = occasion;
        this.giftList = gifts;
    }

    /**
     * Getter sur le nom de la salle
     * @return roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Setter sur le nom de la salle
     * @param roomName
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Getter sur le prénom de la personne concernée par la salle
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter sur le prénom de la personne concernée par la salle
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter sur le nom de la personne concernée par la salle
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter sur le nom de la personne concernée par la salle
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter sur la liste des cadeaux associés à cette personne
     * @return List<Gift> giftList
     */
    public List<Gift> getGiftList() {
        return giftList;
    }

    /**
     * Setter sur la liste des cadeaux associés à cette personne
     * @param giftList
     */
    public void setGiftList(List<Gift> giftList) {
        this.giftList = giftList;
    }

    /**
     * Getter sur l'identifiant unique de la personne
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter sur l'occasion du cadeau
     * @return occasion
     */
    public String getOccasion() {
        return occasion;
    }

    /**
     * Setter sur l'occasion du cadeau
     * @param occasion
     */
    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    /**
     * Ajout d'un cadeau à la liste
     * @param gift
     */
    public void addGift(Gift gift) {
        if (giftList == null) {
            giftList = new ArrayList<>();
        }
        giftList.add(gift);
    }

    /**
     * Recherche un cadeau dans la liste des cadeaux de la salle
     * @param giftId - identifiant du cadeau recherchée
     * @return room  - cadeau correspondant à l'identifiant passé en paramètre, nul sinon
     */
    public Gift getGiftById(final int giftId) {
        for (Gift gift : giftList) {
            if (gift.getId() == giftId) {
                return gift;
            }
        }
        return null;
    }
}
