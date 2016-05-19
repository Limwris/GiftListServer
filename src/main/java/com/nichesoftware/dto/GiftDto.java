package com.nichesoftware.dto;

/**
 * Created by n_che on 02/05/2016.
 */
public class GiftDto {
    /**
     * Montant du cadeau
     */
    private double price;
    /**
     * Nom du cadeau
     */
    private String name;
    /**
     * Montant alloué par l'utilisateur au cadeau
     */
    private double amount;
    /**
     * Identifiant unique de la personne à laquelle est rattaché le cadeau
     */
    private int personId;

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
     * Getter sur le montant alloué par l'utilisateur au cadeau
     * @return amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setter sur le montant alloué par l'utilisateur au cadeau
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Getter sur l'identifiant unique de la personne associée
     * @return personId
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Setter sur l'identifiant unique de la personne associée
     * @param personId
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
