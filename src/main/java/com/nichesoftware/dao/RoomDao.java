package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;
import com.nichesoftware.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by Kattleya on 22/05/2016.
 */
public class RoomDao extends AbstractDaoJdbc implements IRoomDao {
    /**
     * Data source
     */
    @Autowired
    private DataSource dataSource;

    @Override
    public void inviteUserToRoom(User user, Room room) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = dataSource.getConnection();

            final String sql = "INSERT INTO user_room(roomId, userId) VALUES (?, ?);";
            ps = cx.prepareStatement(sql);
            ps.setInt(1, room.getId());
            ps.setInt(2, user.getId());

            int retVal = ps.executeUpdate();

            if (retVal != 1) {
                // Todo
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, null);
        }
    }

    @Override
    public void saveRoom(User user, final String roomName,
                         final String occasion) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = dataSource.getConnection();

            String sql = "INSERT INTO room(roomName, occasion) VALUES (?, ?);";
            ps = cx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, roomName);
            if (!StringUtils.isEmpty(occasion)) {
                ps.setString(2, occasion);
            }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int roomId = rs.getInt(1);

                String sql_foreign_key = "INSERT INTO user_room(roomId, userId) VALUES (?, ?);";
                ps = cx.prepareStatement(sql_foreign_key);
                ps.setInt(1, roomId);
                ps.setInt(2, user.getId());
                ps.execute();
            } else {
                throw new GenericException("La création de la salle a échoué, ou l'ID n'a pu être obtenu.");
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, null);
        }
    }

    @Override
    public void getRoom(User user, int id) throws ServerException, GenericException {

        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = dataSource.getConnection();

            // L'utilisateur doit être préalablement ajouté à la salle
            String sql = "SELECT R.idRoom, R.roomName, R.occasion FROM room AS R JOIN user_room AS UR ON UR.roomId = R.idRoom JOIN user AS U ON U.idUser = UR.userId WHERE U.username = ? AND R.idRoom = ?";

            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getUsername()); // (1,..) premier point d'interrogation
            ps.setInt(2, id);
            rs = ps.executeQuery();

            // Need to move the cursor to the first row
            if (rs.next()) {
                Room room = new Room(rs.getInt(ID_ROW), rs.getString(NAME_ROW), rs.getString(OCCASION_ROW));
                user.addRoom(room);
            }

        } catch (SQLException e) {
            handleSqlException(e);
            throw new GenericException();
        } finally {
            close(cx, ps, rs);
        }
    }

    @Override
    public void getAllRooms(User user) throws ServerException, GenericException {

        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = dataSource.getConnection();
            String sql = "SELECT room.idRoom, room.roomName, room.occasion FROM room JOIN user_room ON user_room.roomId = room.idRoom JOIN user ON user.idUser = user_room.userId WHERE user.username = ?";

            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getUsername()); // (1,..) premier point d'interrogation
            rs = ps.executeQuery();

            while (rs.next()){
                user.addRoom(new Room(rs.getInt(ID_ROW), rs.getString(NAME_ROW), rs.getString(OCCASION_ROW)));
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, rs);
        }
    }

    @Override
    public Room getRoom(int id) throws ServerException, GenericException {

        Room room = null;
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = dataSource.getConnection();

            // L'utilisateur doit être préalablement ajouté à la salle
            String sql = "SELECT R.idRoom, R.roomName, R.occasion FROM room AS R WHERE R.idRoom = ?";

            ps = cx.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            // Need to move the cursor to the first row
            if (rs.next()) {
                room = new Room(rs.getInt(ID_ROW), rs.getString(NAME_ROW), rs.getString(OCCASION_ROW));
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, rs);
        }

        return room;
    }

    @Override
    public void updateRoom(Room room, User user) throws ServerException, GenericException {
    }

    @Override
    public void deleteRoom(Room room, User user) throws ServerException, GenericException {
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
