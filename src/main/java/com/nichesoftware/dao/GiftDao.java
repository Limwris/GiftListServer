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
            String sql = "SELECT U.username, R.roomName, UG.allocated_amount, G.* FROM giftlistserver.user AS U, giftlistserver.room AS R, giftlistserver.user_room AS UR, giftlistserver.gifts AS G, giftlistserver.user_gift AS UG WHERE U.idUser = UR.userId AND R.idRoom = UR.roomId AND G.roomId = R.idRoom AND UG.userId = U.idUser AND UG.giftId = G.idGifts AND U.username = ? AND R.idRoom = ?;";

            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getUsername()); // (1,..) premier point d'interrogation
            ps.setInt(2, room.getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                Room temp = user.getRoomById(rs.getInt(ROOM_ID_ROW));
                if (temp == null) {
                    temp = new Room(rs.getInt(ROOM_ID_ROW), rs.getString(ROOM_NAME_ROW), null);
                    user.addRoom(temp);
                }
                Gift gift = new Gift(rs.getInt(ID_ROW));
                gift.setAmount(rs.getDouble(PRICE_ROW));
                gift.setName(rs.getString(NAME_ROW));
                gift.setAmount(rs.getDouble(AMOUNT_ROW));
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
            ps.setDouble(3, gift.getAmount());

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
