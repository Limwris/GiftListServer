package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Gift;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;
import com.nichesoftware.utils.StringUtils;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kattleya on 22/05/2016.
 */
public class RoomDao extends AbstractDaoJdbc implements IRoomDao {
    @Override
    public void inviteUserToRoom(User user, Room room) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = getConnection();

            String sql = "INSERT INTO user_room(room_id, user_id) VALUES (?, ?);";
            ps = cx.prepareStatement(sql);
            ps.setInt(1, room.getId());
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
    public void saveRoom(User user, String roomName, String occasion, String firstName, String lastName) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = getConnection();

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

                String sql_foreign_key = "INSERT INTO user_room(room_id, user_id) VALUES (?, ?);";
                ps = cx.prepareStatement(sql_foreign_key);
                ps.setInt(1, roomId);
                ps.setInt(2, user.getId());
                ps.execute();
            } else {
                throw new GenericException("La création de la salle a échoué, ou l'ID n'a pu être obtenu.");
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
    public Room getRoom(User user, int id) throws ServerException, GenericException {

        Room room = null;
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = getConnection();

            // L'utilisateur doit être préalablement ajouté à la salle
            String sql = "SELECT room.id, room.roomName, room.occasion FROM room JOIN user_room ON user_room.room_id = room.id JOIN user ON user.id = user_room.user_id WHERE user.username = ? AND room.id = ?";

            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getUsername()); // (1,..) premier point d'interrogation
            ps.setInt(2, id);
            rs = ps.executeQuery();

            // Need to move the cursor to the first row
            if (rs.next()) {
                room = new Room(rs.getInt(ID_ROW), rs.getString(NAME_ROW), rs.getString(OCCASION_ROW));
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

        return room;
    }

    @Override
    public List<Room> getAllRooms(User user) throws ServerException, GenericException {

        List<Room> rooms = new ArrayList<>();
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = getConnection();
            String sql = "SELECT room.id, room.roomName, room.occasion FROM room JOIN user_room ON user_room.room_id = room.id JOIN user ON user.id = user_room.user_id WHERE user.username = ?";

            ps = cx.prepareStatement(sql);
            ps.setString(1, user.getUsername()); // (1,..) premier point d'interrogation
            rs = ps.executeQuery();

            while (rs.next()){
                rooms.add(new Room(rs.getInt(ID_ROW), rs.getString(NAME_ROW), rs.getString(OCCASION_ROW)));
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

        return rooms;
    }

    @Override
    public boolean updateRoom(Room room, User user) throws ServerException, GenericException {
        return false;
    }

    @Override
    public boolean deleteRoom(Room room, User user) throws ServerException, GenericException {
        return false;
    }
}
