package com.nichesoftware.dao;

import com.nichesoftware.exceptions.GenericException;
import com.nichesoftware.exceptions.ServerException;
import com.nichesoftware.model.Invitation;
import com.nichesoftware.model.Room;
import com.nichesoftware.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Kattleya on 17/07/2016.
 */
public class InvitationDao extends AbstractDaoJdbc implements IInvitationDao {
    private static final Logger logger = LoggerFactory.getLogger(InvitationDao.class.getSimpleName());
    /**
     * Data source
     */
    @Autowired
    private DataSource dataSource;

    @Override
    public Date inviteToRoom(User user, Room room) throws ServerException, GenericException {
        logger.info("[Entering] inviteToRoom");

        Connection cx = null;
        PreparedStatement ps = null;
        // L'invitation est valable 3 jours
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        Date expirationDate = calendar.getTime();

        try {
            cx = dataSource.getConnection();

            final String sql = "INSERT INTO invitation(roomId, userId, expirationDate) VALUES (?, ?, ?);";
            ps = cx.prepareStatement(sql);
            ps.setInt(1, room.getId());
            ps.setInt(2, user.getId());
            ps.setDate(3, new java.sql.Date(expirationDate.getTime()));

            int retVal = ps.executeUpdate();
            logger.info("[SQL Statement] inviteToRoom");

            if (retVal != 1) {
                // Todo
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, null);
        }

        return expirationDate;
    }

    @Override
    public List<Integer> checkForPendingInvitation(User user) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Integer> retVal = new ArrayList<>();

        try {
            cx = dataSource.getConnection();

            final String sql = "SELECT * FROM invitation WHERE (userId = ?);";
            ps = cx.prepareStatement(sql);
            ps.setInt(1, user.getId());

            rs = ps.executeQuery();
            do {
                java.util.Date date = new Date(rs.getDate(EXPIRATION_DATE_ROW).getTime());
                // Si la date d'expiration est dépassée, alors on supprime l'invitation
                if (date.before(new java.util.Date())) {
                    delete(rs.getInt(USER_ID_ROW), rs.getInt(ROOM_ID_ROW));
                } else { // Sinon, on ajoute à la liste des salles auxquelles l'utilisateur est invité
                    retVal.add(rs.getInt(ROOM_ID_ROW));
                }
            } while (rs.next());

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, rs);
        }

        return retVal;
    }

    @Override
    public void acceptInvitation(User user, Room room) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;

        try {
            cx = dataSource.getConnection();

            final String sql = "INSERT INTO user_room(roomId, userId) VALUES (?, ?);";
            ps = cx.prepareStatement(sql);
            ps.setInt(1, room.getId());
            ps.setInt(2, user.getId());

            int retVal = ps.executeUpdate();

            // Si l'invitation a été correctement traité, alors on la supprime de la table
            delete(user.getId(), room.getId());

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
    public void deleteInvitation(User user, Room room) throws ServerException, GenericException {
        delete(user.getId(), room.getId());
    }

    private void delete(int userId, int roomId) throws ServerException, GenericException {
        Connection cx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cx = dataSource.getConnection();

            final String sql = "DELETE FROM invitation WHERE roomId = ? AND userId = ?;";
            ps = cx.prepareStatement(sql);
            ps.setInt(1, roomId);
            ps.setInt(2, userId);

            rs = ps.executeQuery();
            do {
                // Todo
            } while (rs.next());

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            close(cx, ps, rs);
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
