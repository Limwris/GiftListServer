package com.nichesoftware.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
     * Liste des personnes
     */
    @JsonIgnore
    private List<Person> persons = new ArrayList<>();
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
     * @param id
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
     * @param username
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
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter sur la liste des personnes
     * @return persons
     */
    public List<Person> getPersons() {
        return persons;
    }

    /**
     * Setter sur la liste des personnes
     * @param persons
     */
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Recherche une personne dans la liste des personnes de l'utilisateur
     * @param personId
     * @return
     */
    public Person getPersonById(final int personId) {
        for (Person person : persons) {
            if (person.getId() == personId) {
                return person;
            }
        }
        return null;
    }

    /**
     * Ajoute une personne à la liste
     * @param person
     */
    public void addPerson(Person person) {
        if (persons == null) {
            persons = new ArrayList<>();
        }
        persons.add(person);
    }
}
