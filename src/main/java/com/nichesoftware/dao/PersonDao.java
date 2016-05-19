package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Person;
import com.nichesoftware.model.User;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by n_che on 27/04/2016.
 */
public class PersonDao extends AbstractDaoJdbc implements IPersonDao {

    @Override
    public void addPersonToUser(User user, Person person) throws ServerException, GenericException {

        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = getConnection();

            String sql = "INSERT INTO user_persons(person_id, user_id) VALUES (?, ?);";
            ps = cx.prepareStatement(sql);
            ps.setInt(1, person.getId());
            ps.setInt(2, user.getId());

            int retVal = ps.executeUpdate();

            if (retVal != 1) {
                // Todo
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

    @Override
    public List<Person> getPersons(User user, String firstnamePerson, String lastnamePerson) {
        return null;
    }

    @Override
    public void savePerson(User user, String firstName, String lastName) throws GenericException, ServerException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = getConnection();

            String sql = "INSERT INTO persons(firstname, lastname) VALUES (?, ?);";
            ps = cx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, firstName);
            ps.setString(2, lastName);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int personId = rs.getInt(1);

                String sql_foreign_key = "INSERT INTO user_persons(person_id, user_id) VALUES (?, ?);";
                ps = cx.prepareStatement(sql_foreign_key);
                ps.setInt(1, personId);
                ps.setInt(2, user.getId());
                ps.execute();
            } else {
                throw new GenericException("La création de la personne a échoué, ou l'ID n'a pu être obtenu.");
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

    @Override
    public List<Person> findPersons(String firstName, String lastName) {

        return null;
    }

    @Override
    public List<Person> getAllPersons(final String username) throws ServerException, GenericException {
        List<Person> persons = new ArrayList<Person>();
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = getConnection();
            String sql = "SELECT persons.id, persons.firstname, persons.lastname FROM persons JOIN user_persons ON user_persons.person_id = persons.id JOIN user_data ON user_data.id= user_persons.user_id WHERE user_data.username = ?";

            ps = cx.prepareStatement(sql);
            ps.setString(1, username); // (1,..) premier point d'interrogation
            rs = ps.executeQuery();

            while (rs.next()){
                persons.add(new Person(rs.getInt(ID_ROW), rs.getString(FIRST_NAME_ROW), rs.getString(LAST_NAME_ROW)));
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

        return persons;
    }

    @Override
    public boolean updatePerson(User user, Person person) {
        return false;
    }

    @Override
    public boolean deletePerson(User user, Person person) {
        return false;
    }
}
