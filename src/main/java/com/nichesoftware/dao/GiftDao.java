package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;

import javax.naming.NamingException;
import java.sql.*;

/**
 * Created by n_che on 02/05/2016.
 */
public class GiftDao extends AbstractDaoJdbc implements IGiftDao {

    @Override
    public void getGifts(User user, Room room) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = getConnection();
            String sql = "SELECT U.username, U.idUser, R.roomName, UG.allocated_amount, G.* FROM giftlistserver.user AS U, giftlistserver.room AS R, giftlistserver.user_room AS UR, giftlistserver.gifts AS G, giftlistserver.user_gift AS UG WHERE U.idUser = UR.userId AND R.idRoom = UR.roomId AND G.roomId = R.idRoom AND UG.userId = U.idUser AND UG.giftId = G.idGifts AND U.username = ? AND R.idRoom = ?;";

            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getUsername()); // (1,..) premier point d'interrogation
            ps.setInt(2, room.getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                Room temp = user.getRoomById(rs.getInt(IRoomDao.ID_ROW));
                if (temp == null) {
                    temp = new Room(rs.getInt(IRoomDao.ID_ROW), rs.getString(IRoomDao.NAME_ROW), null);
                    user.addRoom(temp);
                }
                Gift gift = new Gift(rs.getInt(ID_ROW));
                gift.setPrice(rs.getDouble(PRICE_ROW));
                gift.setName(rs.getString(NAME_ROW));

                User userForGift = new User();
                int userId = rs.getInt(IUserDao.ID_ROW);
                userForGift.setId(userId);
                userForGift.setUsername(rs.getString(IUserDao.USERNAME_ROW));
                gift.getAmountByUser().put(userId, rs.getDouble(AMOUNT_ROW));
                gift.getUserById().put(userId, userForGift);
                temp.addGift(gift);
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
    public Gift getGift(User user, int giftId) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Gift gift = null;

        try {
            cx = getConnection();
            String sql = "SELECT G.*, UG.allocatedAmount, U.username, U.idUser FROM rooms AS R, gifts AS G, user_gifts, user AS U as UG WHERE G.roomId = R.idRoom AND R.idGifts = ? AND UG.roomId = R.idRoom AND UG.userId = ? AND UG.userId = U.idUser;";
            ps = cx.prepareStatement(sql);
            ps.setInt(1, giftId);
            ps.setInt(2, user.getId());

            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString(ID_ROW).equals(giftId)) {
                    if (gift == null) {
                        gift = new Gift(giftId);
                        gift.setName(rs.getString(NAME_ROW));
                        gift.setPrice(rs.getFloat(PRICE_ROW));
                    }
                    User userForGift = new User();
                    int userId = rs.getInt(IUserDao.ID_ROW);
                    userForGift.setId(userId);
                    userForGift.setUsername(rs.getString(IUserDao.USERNAME_ROW));
                    gift.getAmountByUser().put(userId, rs.getDouble(AMOUNT_ROW));
                    gift.getUserById().put(userId, userForGift);
                } else {
                    throw new GenericException("Le cadeau n'a pas pu être récupéré.");
                }
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } catch (NamingException e) {
            throw new GenericException();
        } catch (ClassNotFoundException e) {
            throw new GenericException();
        } finally {
            close(cx, ps, rs);
        }

        return gift;
    }

    @Override
    public void deleteGift(User user, int giftId) throws ServerException, GenericException {
        // Todo
    }

    @Override
    public void addGift(User user, Room room, final String giftName,
                        final double giftPrice, final double allocatedAmount) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = getConnection();

            String sql = "INSERT INTO giftlistserver.gifts(name, price, roomId) VALUES (?, ?, ?);";
            ps = cx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, giftName);
            ps.setDouble(2, giftPrice);
            ps.setInt(3, room.getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int giftId = rs.getInt(1);

                String sql_foreign_key = "INSERT INTO giftlistserver.user_gift(userId, giftId, allocatedAmount) VALUES (?, ?, ?);";
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

    @Override
    public void updateGift(User user, Gift gift) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = getConnection();
            String sql= "INSERT INTO giftlistserver.user_gift(userId, giftId, allocatedAmount) VALUES (?, ?, ?);";
            ps = cx.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, gift.getId());
            ps.setDouble(3, gift.getAmountByUser().get(user.getId()));

            int retVal = ps.executeUpdate();

            if (retVal != 1) {
                throw new GenericException("La mise à jour du cadeau a échoué.");
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
