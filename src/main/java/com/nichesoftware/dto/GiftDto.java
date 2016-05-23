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
     * Identifiant unique de la salle à laquelle est rattaché le cadeau
     */
    private int roomId;

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
     * Getter sur l'identifiant unique de la salle associée
     * @return roomId
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Setter sur l'identifiant unique de la salle associée
     * @param roomId
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
