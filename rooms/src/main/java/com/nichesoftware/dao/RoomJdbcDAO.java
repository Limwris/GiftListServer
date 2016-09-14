package com.nichesoftware.dao;

import com.nichesoftware.Room;
import com.nichesoftware.User;
import com.nichesoftware.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by n_che on 12/09/2016.
 */
public class RoomJdbcDAO extends AbstractJdbcDAO implements IRoomDAO {
    // Fields   --------------------------------------------------------------------------------------------------------
    /**
     * Data source
     */
    @Autowired
    private DataSource dataSource;

    // Methods   -------------------------------------------------------------------------------------------------------
    @Override
    public Room saveRoom(User user, String roomName, String occasion) throws DAOException {
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Room room = null;

        try {
            cx = dataSource.getConnection();

            String sql = "INSERT INTO room(roomName, occasion) VALUES (?, ?);";
            ps = cx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, roomName);
            if (!StringUtils.isEmpty(occasion)) {
                ps.setString(2, occasion);
            }

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int roomId = rs.getInt(1);
                room = new Room(roomId, roomName, occasion);

                String sql_foreign_key = "INSERT INTO user_room(roomId, userId) VALUES (?, ?);";
                ps = cx.prepareStatement(sql_foreign_key);
                ps.setInt(1, roomId);
                ps.setInt(2, user.getId());
                ps.execute();
            } else {
                throw new DAOException(DAOException.GENERIC_SAVE_FAILED_ERROR);
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, rs);
        }

        return room;
    }

    @Override
    public Room getRoom(User user, int id) throws DAOException {
        return null;
    }

    @Override
    public List<Room> getAllRooms(User user) throws DAOException {
        return null;
    }

    @Override
    public Room updateRoom(Room room, User user) throws DAOException {
        return null;
    }

    @Override
    public void deleteRoom(Room room, User user) throws DAOException {

    }

    @Override
    public boolean hasRoom(User user, int roomId) throws DAOException {
        return false;
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
