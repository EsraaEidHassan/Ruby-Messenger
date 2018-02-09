package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.Friendship;
import model.FriendshipCategory;
import model.User;
import util.FriendshipCtrlInt;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public class FriendshipDao implements FriendshipCtrlInt {

    private Connection dbConn;
    private ResultSet results;
    private PreparedStatement insStmt, updateStmt, delStmt;

    public FriendshipDao() {
        dbConn = OracleDbConn.getDatabaseConnection();
    }

    @Override
    public Friendship retrieveFriendship(long fromUserId, long friendId) {
        Friendship friendship = null;
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIPS WHERE FROM_USER = " + fromUserId
                    + " AND FRIEND = " + friendId);
            if (results.next()) {
                UserDao userController = new UserDao();
                User fromUser = userController.retrieveUser(fromUserId);
                User friend = userController.retrieveUser(friendId);
                LocalDateTime friendshipStartDate = results.getTimestamp("START_DATE").toLocalDateTime();
                LocalDateTime friendshipEndDate = results.getTimestamp("End_DATE").toLocalDateTime();
                String friendBlockedYN = results.getString("BLOCKED_YN");
                FriendshipCategory friendshipCategory
                        = new FriendshipCategoryDao().retrieveFriendshipCategory(results.getLong("FRIENDSHIP_CATEGORY"));

                friendship = new Friendship(fromUser, friend, friendshipStartDate, friendshipEndDate, friendBlockedYN, friendshipCategory);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friendship;
    }

    @Override
    public ArrayList<Friendship> retrieveAllFriendships(long fromUserId) {
        ArrayList<Friendship> friendships = new ArrayList<>();
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIPS WHERE FROM_USER = " + fromUserId);
            if (results.next()) {
                UserDao userController = new UserDao();
                User fromUser = userController.retrieveUser(fromUserId);
                User friend = userController.retrieveUser(results.getLong("FRIEND"));
                LocalDateTime friendshipStartDate = results.getTimestamp("START_DATE").toLocalDateTime();
                LocalDateTime friendshipEndDate = results.getTimestamp("End_DATE").toLocalDateTime();
                String friendBlockedYN = results.getString("BLOCKED_YN");
                FriendshipCategory friendshipCategory
                        = new FriendshipCategoryDao().retrieveFriendshipCategory(results.getLong("FRIENDSHIP_CATEGORY"));

                friendships.add(new Friendship(fromUser, friend, friendshipStartDate, friendshipEndDate, friendBlockedYN,
                        friendshipCategory));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friendships;
    }

    @Override
    public int insertFriendship(Friendship friendship) {
        int rowsAffected = 0;
        try {
            insStmt = dbConn.prepareStatement("INSERT INTO FRIENDSHIPS (FROM_USER, FRIEND, START_DATE, END_DATE, "
                    + "BLOCKED_YN, FRIENDSHIP_CATEGORY) "
                    + "VALUES (?, ?, ?, ?, ?, ?)");
            insStmt.setLong(1, friendship.getFromUser().getUserId());
            insStmt.setLong(2, friendship.getFriend().getUserId());
            insStmt.setTimestamp(3, Timestamp.valueOf(friendship.getFriendshipStartDate()));
            insStmt.setTimestamp(4, Timestamp.valueOf(friendship.getFriendshipEndDate()));
            insStmt.setString(5, friendship.getFriendBlockedYN());
            insStmt.setLong(6, friendship.getFriendshipCategory().getCategoryId());

            rowsAffected = insStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int updateFriendship(long fromUserId, long friendId, Friendship friendship) {
        int rowsAffected = 0;
        try {
            updateStmt = dbConn.prepareStatement("UPDATE FRIENDSHIPS SET FROM_USER = ?, FRIEND = ?, "
                    + "START_DATE = ?, END_DATE = ?, BLOCKED_YN = ?, FRIENDSHIP_CATEGORY = ? "
                    + "WHERE FROM_USER = " + fromUserId + " AND FRIEND = " + friendId);
            updateStmt.setLong(1, friendship.getFromUser().getUserId());
            updateStmt.setLong(2, friendship.getFriend().getUserId());
            updateStmt.setTimestamp(3, Timestamp.valueOf(friendship.getFriendshipStartDate()));
            updateStmt.setTimestamp(4, Timestamp.valueOf(friendship.getFriendshipEndDate()));
            updateStmt.setString(5, friendship.getFriendBlockedYN());
            updateStmt.setLong(6, friendship.getFriendshipCategory().getCategoryId());

            rowsAffected = updateStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteFriendship(long fromUserId, long friendId) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIPS WHERE FROM_USER = ? AND FRIEND = ?");
            delStmt.setLong(1, fromUserId);
            delStmt.setLong(2, friendId);
            
            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteAllFriendships(long fromUserId) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIPS WHERE FROM_USER = " + fromUserId);
            
            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

}
