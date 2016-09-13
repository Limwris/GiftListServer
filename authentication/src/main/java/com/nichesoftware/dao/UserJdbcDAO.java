package com.nichesoftware.dao;

import com.nichesoftware.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDAO extends AbstractJdbcDAO implements IUserDAO {
    // Fields   --------------------------------------------------------------------------------------------------------
    /**
     * Data source
     */
    @Autowired
    private DataSource dataSource;

    // Methods   -------------------------------------------------------------------------------------------------------
    @Override
    public void createUser(User user) throws DAOException {

        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = dataSource.getConnection();

            String sql = "INSERT INTO user(username, password, phoneNumber, gcmId, creationDate) VALUES (?, ?, ?, ?, ?);";
            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getGcmId());
            ps.setDate(5, new Date(new java.util.Date().getTime()));

            int retVal = ps.executeUpdate();

            if (retVal != 1) {
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, null);
        }
    }

    @Override
    public User findByUsername(String username) throws DAOException {

        User user = null;
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = dataSource.getConnection();

            String sql = "SELECT idUser, username, password, phoneNumber, gcmId, creationDate FROM user WHERE username = ?";

            ps = cx.prepareStatement(sql);
            ps.setString(1, username); // (1,..) premier point d'interrogation
            rs = ps.executeQuery();

            // Need to move the cursor to the first row
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(ID_ROW));
                user.setUsername(rs.getString(USERNAME_ROW));
                user.setPassword(rs.getString(PASSWORD_ROW));
                user.setPhoneNumber(rs.getString(PHONE_NUMBER_ROW));
                user.setGcmId(rs.getString(GCM_ID_ROW));
                user.setCreationDate(rs.getDate(CREATION_DATE_ROW));
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, rs);
        }

        return user;
    }

    @Override
    public List<User> retreiveAllUsers() throws DAOException {
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            cx = dataSource.getConnection();

            String sql = "SELECT idUser, username, phoneNumber FROM user";

            ps = cx.prepareStatement(sql);
            rs = ps.executeQuery();

            // Need to move the cursor to the first row
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(ID_ROW));
                user.setUsername(rs.getString(USERNAME_ROW));
                user.setPhoneNumber(rs.getString(PHONE_NUMBER_ROW));
                users.add(user);
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, rs);
        }

        return users;
    }

    @Override
    public void updateUser(User user) throws DAOException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = dataSource.getConnection();

            String sql = "UPDATE user SET phoneNumber = ?, gcmId = ? WHERE username = ?;";
            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getPhoneNumber());
            ps.setString(2, user.getGcmId());
            ps.setString(3, user.getUsername());

            int retVal = ps.executeUpdate();

            if (retVal != 1) {
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, null);
        }
    }

    // Getters   -------------------------------------------------------------------------------------------------------
    /**
     * Getter on the data source
     * @return dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    /**
     * Setter on the data source
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
