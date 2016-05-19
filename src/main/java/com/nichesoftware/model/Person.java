package com.nichesoftware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by n_che on 27/04/2016.
 */
public class Person {
    /**
     * Identifiant unique de la personne
     */
    private int id;
    /**
     * Prénom
     */
    private String firstName;
    /**
     * Nom
     */
    private String lastName;

    /**
     * Liste des cadeaux associés à cette personne
     */
    private List<Gift> giftList = new ArrayList<Gift>();

    /**
     * Contructeur par défaut
     * @param firstName
     * @param lastName
     */
//    public Person(final String firstName, final String lastName) {
//        this.id = UUID.randomUUID().toString();
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }

    /**
     * Contructeur avec liste de cadeau
     * @param firstName
     * @param lastName
     * @param gifts
     */
//    public Person(final String firstName, final String lastName, List<Gift> gifts) {
//        this.id = UUID.randomUUID().toString();
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.giftList = gifts;
//    }

    /**
     * Contructeur par défaut
     * @param id
     * @param firstName
     * @param lastName
     */
    public Person(final int id, final String firstName, final String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Contructeur avec liste de cadeau
     * @param id
     * @param firstName
     * @param lastName
     * @param gifts
     */
    public Person(final int id, final String firstName, final String lastName, List<Gift> gifts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.giftList = gifts;
    }

    /**
     * Getter sur le prénom
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter sur le prénom
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter sur le nom
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter sur le nom
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
     * Ajout d'un cadeau à la liste
     * @param gift
     */
    public void addGift(Gift gift) {
        if (giftList == null) {
            giftList = new ArrayList<>();
        }
        giftList.add(gift);
    }
}
