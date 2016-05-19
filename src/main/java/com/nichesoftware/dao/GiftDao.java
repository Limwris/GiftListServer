package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Person;
import com.nichesoftware.model.User;

import javax.naming.NamingException;
import java.sql.*;

/**
 * Created by n_che on 02/05/2016.
 */
public class GiftDao extends AbstractDaoJdbc implements IGiftDao {

//    @Override
//    public List<Gift> getGifts(Person person) throws ServerException, GenericException {
//        List<Gift> gifts = new ArrayList<Gift>();
//        Connection cx = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            cx = getConnection();
//            String sql = "SELECT G.* FROM giftlist.persons AS P, giftlist.gifts AS G WHERE G.person_id = P.id AND P.id = ?;";
//
//            ps = cx.prepareStatement(sql);
//            ps.setInt(1, person.getId()); // (1,..) premier point d'interrogation
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Gift gift = new Gift(rs.getInt(ID_ROW));
//                gift.setPrice(rs.getDouble(PRICE_ROW));
//                gift.setName(rs.getString(NAME_ROW));
//                gifts.add(gift);
//            }
//
//        } catch (SQLException e) {
//            handleSqlException(e);
//        } catch (ClassNotFoundException e) {
//            throw new GenericException();
//        } catch (NamingException e) {
//            throw new GenericException();
//        } finally {
//            close(cx, ps, rs);
//        }
//
//        return gifts;
//    }

    @Override
    public void getGifts(User user) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = getConnection();
            String sql = "SELECT U.username, P.firstname, P.lastname, UG.allocated_amount, G.* FROM giftlist.user_data AS U, giftlist.persons AS P, giftlist.user_persons AS UP, giftlist.gifts AS G, giftlist.user_gifts AS UG WHERE U.id = UP.user_id AND P.id = UP.person_id AND G.person_id = P.id AND UG.user_id = U.id AND UG.gift_id = G.id AND U.username = ?;";

            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getUsername()); // (1,..) premier point d'interrogation
            rs = ps.executeQuery();

            while (rs.next()) {
                Person person = user.getPersonById(rs.getInt(PERSON_ID_ROW));
                if (person == null) {
                    person = new Person(rs.getInt(PERSON_ID_ROW), rs.getString(PERSON_FIRST_NAME_ROW), rs.getString(PERSON_LAST_NAME_ROW));
                    user.addPerson(person);
                }
                Gift gift = new Gift(rs.getInt(ID_ROW));
                gift.setAmount(rs.getDouble(PRICE_ROW));
                gift.setName(rs.getString(NAME_ROW));
                gift.setAmount(rs.getDouble(AMOUNT_ROW));
                person.addGift(gift);
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
    }

    @Override
    public void addGift(User user, Person person, final String giftName,
                        final double giftPrice, final double allocatedAmount) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = getConnection();

            String sql = "INSERT INTO giftlist.gifts(name, price, person_id) VALUES (?, ?, ?);";
            ps = cx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, giftName);
            ps.setDouble(2, giftPrice);
            ps.setInt(3, person.getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int giftId = rs.getInt(1);

                String sql_foreign_key = "INSERT INTO giftlist.user_gifts(user_id, gift_id, allocated_amount) VALUES (?, ?, ?);";
                ps = cx.prepareStatement(sql_foreign_key);
                ps.setInt(1, user.getId());
                ps.setInt(2, giftId);
                ps.setDouble(3, allocatedAmount);
                ps.execute();
            } else {
                throw new GenericException("L'ajout du cadeau a échoué.");
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
