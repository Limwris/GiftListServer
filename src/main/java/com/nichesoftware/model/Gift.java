package com.nichesoftware.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by n_che on 27/04/2016.
 */
public class Gift {
    /**
     * Identifiant unique du cadeau
     */
    private int id;
    /**
     * Montant du cadeau
     */
    private double price;
    /**
     * Nom du cadeau
     */
    private String name;
    /**
     * Url pointant vers un site proposant le cadeau
     */
    private String url;
    /**
     * Flag indiquant si la cadeau a été acheté
     */
    private boolean isBought;
    /**
     * Montants alloués par utilisateur au cadeau
     */
    private Map<Integer, Double> amountByUser = new HashMap<>();
    /**
     * Liste permettant de faire le lien entre la map des montants et les utilisateurs
     */
    private Map<Integer, User> userById = new HashMap<>();

    /**
     * Constructeur
     */
    public Gift(int id) {
        this.id = id;
    }

    /**
     * Getter sur l'identifiant unique du cadeau
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter sur le montant du cadeau
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter sur le montant du cadeau
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter sur le nom du cadeau
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter sur le nom du cadeau
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter sur l'url pointant vers un site proposant le cadeau
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter sur l'url pointant vers un site proposant le cadeau
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter sur le flag indiquant si la cadeau a été acheté
     * @return isBought
     */
    public boolean isBought() {
        return isBought;
    }

    /**
     * Setter sur le flag indiquant si la cadeau a été acheté
     * @param isBought
     */
    public void setIsBought(boolean isBought) {
        this.isBought = isBought;
    }

    /**
     * Getter sur les montants alloués par utilisateur au cadeau
     * @return
     */
    public Map<Integer, Double> getAmountByUser() {
        return amountByUser;
    }

    /**
     * Setter sur les montants alloués par utilisateur au cadeau
     * @param amountByUser
     */
    public void setAmountByUser(Map<Integer, Double> amountByUser) {
        this.amountByUser = amountByUser;
    }

    /**
     * Getter sur la liste permettant de faire le lien entre la map des montants et les utilisateurs
     * @return userById
     */
    public Map<Integer, User> getUserById() {
        return userById;
    }

    /**
     * Setter sur la liste permettant de faire le lien entre la map des montants et les utilisateurs
     * @param userById
     */
    public void setUserById(Map<Integer, User> userById) {
        this.userById = userById;
    }
}
