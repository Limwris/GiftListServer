package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by n_che on 28/04/2016.
 * Sources:   - https://books.google.fr/books?id=eSm5YK1l8TkC&pg=PA378&lpg=PA378&dq=handling+sqlexception+error+code+java+mysql&source=bl&ots=U1M5JrRAAF&sig=C8T1yB9a2e5uxRxjypMjeUL1Q9Q&hl=fr&sa=X&ved=0ahUKEwjS0IH0q7HMAhXHzxQKHdCYAV8Q6AEIazAI#v=onepage&q=handling%20sqlexception%20error%20code%20java%20mysql&f=false
 *
 */
public class UserDao extends AbstractDaoJdbc implements IUserDao {
    /**
     * Data source
     */
    @Autowired
    private DataSource dataSource;

    @Override
    public User findByUsername(String username) throws ServerException, GenericException {

        User user = null;
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = dataSource.getConnection();

            String sql = "SELECT idUser, username, password, creationDate FROM user WHERE username = ?";

            ps = cx.prepareStatement(sql);
            ps.setString(1, username); // (1,..) premier point d'interrogation
            rs = ps.executeQuery();

            // Need to move the cursor to the first row
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(ID_ROW));
                user.setUsername(rs.getString(USERNAME_ROW));
                user.setPassword(rs.getString(PASSWORD_ROW));
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
    public List<User> retreiveAllUsers() throws GenericException, ServerException {
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
            if (rs.next()) {
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
    public void createUser(User user) throws ServerException, GenericException {

        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = dataSource.getConnection();

            String sql = "INSERT INTO user(username, password, creationDate) VALUES (?, ?, ?);";
            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setDate(3, new Date(user.getCreationDate().getTime()));

            int retVal = ps.executeUpdate();

            if (retVal != 1) {
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, null);
        }
    }

    /**
     * Getter on the data source
     * @return dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Setter on the data source
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
