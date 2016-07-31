package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.KeyAlreadyExistsException;
import com.nichesoftware.exceptions.ServerException;

import java.sql.*;

/**
 * Created by n_che on 27/04/2016.
 */
public class AbstractDaoJdbc {

    protected void close(Connection cx, PreparedStatement ps, ResultSet rs) {
        try { if(rs != null) rs.close(); } catch (SQLException ignored) { }
        try { if(ps != null) ps.close(); } catch (SQLException ignored) { }
        try { if(cx != null) cx.close(); } catch (SQLException ignored) { }
    }

    /**
     * Gestion des erreurs SQL
     * @param e
     * @throws GenericException
     * @throws ServerException
     */
    protected void handleSqlException(SQLException e) throws GenericException, ServerException {
        // SQLSTATE 23505: Unique violation because of duplicated key (errorCode -104 HSQLDB, 23505 h2)
        if (e.getErrorCode() == 1062 || e.getErrorCode() == -104 || e.getErrorCode() == 23505) {
            throw new KeyAlreadyExistsException();
        } else if (e.getErrorCode() == 1044) {
            // Mauvais nom de base
            throw new GenericException("Mauvais nom de base");
        } else if (e.getErrorCode() == 1136) {
            // Mauvais nombre de colonnes
            throw new GenericException("Mauvais nombre de colonnes");
        } else if (e.getErrorCode() == 1146) {
            // Mauvais nom de table
            throw new GenericException("Mauvais nom de table");
        } else if (e.getErrorCode() == 0 && e.getSQLState().equals("01004")) {
            // Mauvais type de colonne
            throw new GenericException("Mauvais type de colonne");
        } else if (e.getErrorCode() == 1452) {
            // Clé étrangère absente
            throw new GenericException("Clé étrangère absente");
        } else {
            throw new GenericException("SQL error code: " + e.getErrorCode() + " & sql state: " + e.getSQLState());
        }

    }
}
