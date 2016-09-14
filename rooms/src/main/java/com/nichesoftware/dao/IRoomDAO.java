package com.nichesoftware.dao;

import com.nichesoftware.Room;
import com.nichesoftware.User;

import java.util.List;

/**
 * Created by n_che on 12/09/2016.
 */
public interface IRoomDAO {
    // Fields   --------------------------------------------------------------------------------------------------------
    String ID_ROW = "idRoom";
    String NAME_ROW = "roomName";
    String OCCASION_ROW = "occasion";

    // Methods   -------------------------------------------------------------------------------------------------------
    /**
     * Permet de créer une entrée dans la base de données
     * @param user      - Utilisateur requérant la salle
     * @param roomName  - Nom de la salle
     * @param occasion  - Occasion associée à la salle
     *
     * @return Room
     */
    Room saveRoom(User user, final String roomName, final String occasion) throws DAOException;
    /**
     * Permet de récupérer une salle via son identifiant
     * @param user      - Utilisateur requérant la salle
     * @param id        - Identifiant de la salle
     *
     * @return Room
     */
    Room getRoom(User user, int id) throws DAOException;
    /**
     * Permet de récupérer l'ensemble des salles d'un utilisateur
     * @param user      - Utilisateur requérant la salle
     *
     * @return List<Room>
     */
    List<Room> getAllRooms(User user) throws DAOException;
    /**
     * Permet de mettre à jour les données d'une entrée dans la base
     * @param room      - Salle à mettre à jour
     * @param user      - Utilisateur requérant la salle
     *
     * @return Room
     */
    Room updateRoom(Room room, User user) throws DAOException;
    /**
     * Permet de supprimer une entrée dans la base
     * @param room      - Salle à supprimer
     * @param user      - Utilisateur requérant la salle
     */
    void deleteRoom(Room room, User user) throws DAOException;

    /**
     * Permet de savoir si une salle appartient à un utilisateur
     * @param user      - Utilisateur requérant la salle
     * @param roomId    - Identifiant de la salle
     *
     * @return Room
     */
    boolean hasRoom(User user, int roomId) throws DAOException;
}
