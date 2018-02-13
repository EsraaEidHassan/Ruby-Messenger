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
    public Friendship retrieveFriendship(User fromUser, User friend) {
        Friendship friendship = null;
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIPS WHERE FROM_USER = " + fromUser.getUserId()
                    + " AND FRIEND = " + friend.getUserId());
            if (results.next()) {
                UserDao userController = new UserDao();
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
    public ArrayList<Friendship> retrieveAllFriendships(User fromUser) {
        ArrayList<Friendship> friendships = new ArrayList<>();
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIPS WHERE FROM_USER = " + fromUser.getUserId());
            if (results.next()) {
                UserDao userController = new UserDao();
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
    public ArrayList<User> retrieveAllFriends(User fromUser) {
        ArrayList<User> friends = new ArrayList<>();
        try {
            results = dbConn.createStatement().executeQuery("SELECT FRIEND FROM FRIENDSHIPS WHERE FROM_USER = " + fromUser.getUserId());
            while (results.next()) {
                UserDao userController = new UserDao();
                User friend = userController.retrieveUser(results.getLong("FRIEND"));

                friends.add(friend);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friends;
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
    public int updateFriendship(Friendship friendship) {
        int rowsAffected = 0;
        try {
            updateStmt = dbConn.prepareStatement("UPDATE FRIENDSHIPS SET START_DATE = ?, END_DATE = ?, "
                    + "BLOCKED_YN = ?, FRIENDSHIP_CATEGORY = ? "
                    + "WHERE FROM_USER = " + friendship.getFromUser().getUserId() + " AND FRIEND = " + friendship.getFriend().getUserId());
            updateStmt.setTimestamp(1, Timestamp.valueOf(friendship.getFriendshipStartDate()));
            updateStmt.setTimestamp(2, Timestamp.valueOf(friendship.getFriendshipEndDate()));
            updateStmt.setString(3, friendship.getFriendBlockedYN());
            updateStmt.setLong(4, friendship.getFriendshipCategory().getCategoryId());

            rowsAffected = updateStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int updateFriendship(User fromUser, User friend, Friendship friendship) {
        int rowsAffected = 0;
        try {
            updateStmt = dbConn.prepareStatement("UPDATE FRIENDSHIPS SET FROM_USER = ?, FRIEND = ?, "
                    + "START_DATE = ?, END_DATE = ?, BLOCKED_YN = ?, FRIENDSHIP_CATEGORY = ? "
                    + "WHERE FROM_USER = " + fromUser.getUserId() + " AND FRIEND = " + friend.getUserId());
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
    public int deleteFriendship(Friendship friendship) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIPS WHERE FROM_USER = ? AND FRIEND = ?");
            delStmt.setLong(1, friendship.getFromUser().getUserId());
            delStmt.setLong(2, friendship.getFriend().getUserId());

            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteAllFriendships(User fromUser) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIPS WHERE FROM_USER = " + fromUser.getUserId());

            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

}
