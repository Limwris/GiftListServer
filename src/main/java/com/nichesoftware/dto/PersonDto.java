package com.nichesoftware.dto;

/**
 * Created by n_che on 02/05/2016.
 */
public class PersonDto {
    /**
     * Prénom
     */
    private String firstName;
    /**
     * Nom
     */
    private String lastName;

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
}
