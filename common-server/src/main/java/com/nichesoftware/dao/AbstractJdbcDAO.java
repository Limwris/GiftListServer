package com.nichesoftware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by n_che on 27/04/2016.
 */
public class AbstractJdbcDAO {

    // Methods   -------------------------------------------------------------------------------------------------------
    protected void close(Connection cx, PreparedStatement ps, ResultSet rs) {
        try { if(rs != null) rs.close(); } catch (SQLException ignored) { }
        try { if(ps != null) ps.close(); } catch (SQLException ignored) { }
        try { if(cx != null) cx.close(); } catch (SQLException ignored) { }
    }

    /**
     * Gestion des erreurs SQL
     * @param e
     * @throws DAOException
     */
    protected void handleSqlException(SQLException e) throws DAOException {
        e.printStackTrace();
        // SQLSTATE 23505: Unique violation because of duplicated key (errorCode -104 HSQLDB, 23505 h2)
        if (e.getErrorCode() == 1062 || e.getErrorCode() == -104 || e.getErrorCode() == 23505) {
            throw new DAOException(DAOException.KEY_ALREADY_EXISTS);
        } else if (e.getErrorCode() == 1044) {
            // Mauvais nom de base
            throw new DAOException(DAOException.BAD_DATABASE_NAME);
        } else if (e.getErrorCode() == 1136) {
            // Mauvais nombre de colonnes
            throw new DAOException(DAOException.BAD_COLUMN_OUT_OF_BOUND);
        } else if (e.getErrorCode() == 1146) {
            // Mauvais nom de table
            throw new DAOException(DAOException.BAD_TABLE_NAME);
        } else if (e.getErrorCode() == 0 && e.getSQLState().equals("01004")) {
            // Mauvais type de colonne
            throw new DAOException(DAOException.BAD_COLUMN_TYPE);
        } else if (e.getErrorCode() == 1452) {
            // Clé étrangère absente
            throw new DAOException(DAOException.BAD_FOREYGN_KEY);
        } else {
            DAOException exception = new DAOException(DAOException.GENERIC_ERROR_CODE);
            exception.setSqlErrorCode(e.getErrorCode());
            exception.setSqlState(e.getSQLState());
            throw exception;
        }
    }
}
