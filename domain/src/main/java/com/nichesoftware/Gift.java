package com.nichesoftware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by n_che on 27/04/2016.
 */
public class Gift {
    /**
     * Constructeur
     */
    public Gift(int id) {
        this.id = id;
    }

    // Fields ----------------------------------------------------------------------------------------------------------
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
     * Description du cadeau
     */
    private String description;
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
    private Map<String, Double> amountByUser = new HashMap<>();

    // Getter ----------------------------------------------------------------------------------------------------------
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
     * Getter sur le nom du cadeau
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter sur la description du cadeau
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter sur l'url pointant vers un site proposant le cadeau
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter sur le flag indiquant si la cadeau a été acheté
     * @return isBought
     */
    public boolean isBought() {
        return isBought;
    }

    /**
     * Getter sur les montants alloués par utilisateur au cadeau
     * @return
     */
    public Map<String, Double> getAmountByUser() {
        return amountByUser;
    }

    // Setter ----------------------------------------------------------------------------------------------------------
    /**
     * Setter sur le montant du cadeau
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Setter sur le nom du cadeau
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter sur la description du cadeau
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter sur l'url pointant vers un site proposant le cadeau
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Setter sur le flag indiquant si la cadeau a été acheté
     * @param isBought
     */
    public void setIsBought(boolean isBought) {
        this.isBought = isBought;
    }

    /**
     * Setter sur les montants alloués par utilisateur au cadeau
     * @param amountByUser
     */
    public void setAmountByUser(Map<String, Double> amountByUser) {
        this.amountByUser = amountByUser;
    }
}
