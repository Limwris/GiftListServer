package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.User;

import javax.naming.NamingException;
import java.sql.*;

/**
 * Created by n_che on 28/04/2016.
 * Sources:   - https://books.google.fr/books?id=eSm5YK1l8TkC&pg=PA378&lpg=PA378&dq=handling+sqlexception+error+code+java+mysql&source=bl&ots=U1M5JrRAAF&sig=C8T1yB9a2e5uxRxjypMjeUL1Q9Q&hl=fr&sa=X&ved=0ahUKEwjS0IH0q7HMAhXHzxQKHdCYAV8Q6AEIazAI#v=onepage&q=handling%20sqlexception%20error%20code%20java%20mysql&f=false
 *
 */
public class UserDao extends AbstractDaoJdbc implements IUserDao {
    @Override
    public User findByUsername(String username) throws ServerException, GenericException {

        User user = null;
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = getConnection();

            String sql = "SELECT id, username, password, creation_date FROM user_data WHERE username = ?";

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
        } catch (ClassNotFoundException e) {
            throw new GenericException();
        } catch (NamingException e) {
            throw new GenericException();
        } finally {
            close(cx, ps, rs);
        }

        return user;
    }

    @Override
    public void createUser(User user) throws ServerException, GenericException {

        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = getConnection();

            String sql = "INSERT INTO user_data(username, password, creation_date) VALUES (?, ?, ?);";
            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setDate(3, new Date(user.getCreationDate().getTime()));

            int retVal = ps.executeUpdate();

            if (retVal != 1) {
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } catch (NamingException e) {
            throw new GenericException();
        } catch (ClassNotFoundException e) {
            throw new GenericException();
        } finally {
            close(cx, ps, null);
        }
    }
}
