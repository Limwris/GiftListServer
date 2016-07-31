package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by n_che on 02/05/2016.
 */
public class GiftDao extends AbstractDaoJdbc implements IGiftDao {
    /**
     * Data source
     */
    @Autowired
    private DataSource dataSource;

    @Override
    public void getGifts(User user, Room room) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = dataSource.getConnection();
            String sql = "SELECT U.username, UG.allocatedAmount, G.* FROM user AS U, room AS R, user_room AS UR, gifts AS G, user_gift AS UG WHERE U.idUser = UR.userId AND R.idRoom = UR.roomId AND G.roomId = R.idRoom AND UG.userId = U.idUser AND UG.giftId = G.idGifts AND R.idRoom = ?;";

            ps = cx.prepareStatement(sql);
            ps.setInt(1, room.getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                int giftId = rs.getInt(ID_ROW);
                Gift gift = room.getGiftById(giftId);
                if (gift == null) {
                    gift = new Gift(giftId);
                    gift.setPrice(rs.getDouble(PRICE_ROW));
                    gift.setDescription(rs.getString(DESCRIPTION_ROW));
                    gift.setName(rs.getString(NAME_ROW));
                    room.addGift(gift);
                }

                gift.getAmountByUser().put(rs.getString(IUserDao.USERNAME_ROW), rs.getDouble(AMOUNT_ROW));
            }

        } catch (SQLException e) {
            handleSqlException(e);
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
            cx = dataSource.getConnection();
            String sql = "SELECT G.*, UG.allocatedAmount, U.username, U.idUser FROM rooms AS R, gifts AS G, user_gifts AS UG, user AS U WHERE G.roomId = R.idRoom AND R.idGifts = ? AND UG.roomId = R.idRoom AND UG.userId = ? AND UG.userId = U.idUser;";
            ps = cx.prepareStatement(sql);
            ps.setInt(1, giftId);
            ps.setInt(2, user.getId());

            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(ID_ROW) == giftId) {
                    if (gift == null) {
                        gift = new Gift(giftId);
                        gift.setName(rs.getString(NAME_ROW));
                        gift.setDescription(rs.getString(DESCRIPTION_ROW));
                        gift.setPrice(rs.getDouble(PRICE_ROW));
                    }
                    gift.getAmountByUser().put(rs.getString(IUserDao.USERNAME_ROW), rs.getDouble(AMOUNT_ROW));
                } else {
                    throw new GenericException("Le cadeau n'a pas pu être récupéré.");
                }
            }

        } catch (SQLException e) {
            handleSqlException(e);
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
    public Gift addGift(User user, Room room, final String giftName, final double giftPrice,
                        final double allocatedAmount, final String description) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Gift gift = null;

        try {
            cx = dataSource.getConnection();

            String sql = "INSERT INTO gifts(name, price, roomId, description) VALUES (?, ?, ?, ?);";
            ps = cx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, giftName);
            ps.setDouble(2, giftPrice);
            ps.setInt(3, room.getId());
            ps.setString(4, description);

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int giftId = rs.getInt(1);
                gift = new Gift(giftId);
                gift.setName(giftName);
                gift.setDescription(description);
                gift.setPrice(giftPrice);
                gift.getAmountByUser().put(user.getUsername(), allocatedAmount);

                String sql_foreign_key = "INSERT INTO user_gift(userId, giftId, allocatedAmount) VALUES (?, ?, ?);";
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
        } finally {
            close(cx, ps, rs);
        }

        return gift;
    }

    @Override
    public Gift updateGift(User user, Gift gift) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = dataSource.getConnection();
            String sql= "UPDATE user_gift AS UG, gifts AS G SET UG.allocatedAmount = ?, G.description = ? WHERE UG.userId = ? AND UG.giftId = ? AND G.idGifts = ?;";
            ps = cx.prepareStatement(sql);
            ps.setDouble(1, gift.getAmountByUser().get(user.getUsername()));
            ps.setString(2, gift.getDescription());
            ps.setInt(3, user.getId());
            ps.setInt(4, gift.getId());
            ps.setInt(5, gift.getId());

            int retVal = ps.executeUpdate();

            if (retVal != 1) {
                throw new GenericException("La mise à jour du cadeau a échoué.");
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, null);
        }

        return gift;
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
