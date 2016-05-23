package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.KeyAlreadyExistsException;
import com.nichesoftware.exceptions.ServerException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by n_che on 27/04/2016.
 */
public class AbstractDaoJdbc {

    protected void close(Connection cx, PreparedStatement ps, ResultSet rs) {
        try { if(rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if(ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if(cx != null) cx.close(); } catch (SQLException e) { e.printStackTrace(); }
    }

    protected Connection getConnection() throws ClassNotFoundException, SQLException, NamingException {
        // Sur Tomcat:
        //return getConnectionDS();
        // Avec une classe de test:
        return getConnectionDM();
    }

    protected Connection getConnectionDM() throws ClassNotFoundException, SQLException {
        Connection cx;
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/giftlistserver";

        cx = DriverManager.getConnection(url, "scott", "summers");
        return cx;
    }

    protected Connection getConnectionDS() throws ClassNotFoundException, SQLException, NamingException {
        // Lookup data source
        InitialContext context = new InitialContext();
        DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/giftlist");
        // Obtention de la connexion
        Connection cx = ds.getConnection();

        return cx;
    }

    /**
     * Gestion des erreurs SQL
     * @param e
     * @throws GenericException
     * @throws ServerException
     */
    protected void handleSqlException(SQLException e) throws GenericException, ServerException {
        if (e.getErrorCode() == 1062) {
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
