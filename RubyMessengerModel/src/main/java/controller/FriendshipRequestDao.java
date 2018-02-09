package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.FriendshipRequest;
import model.User;
import util.FriendshipRequestCtrlInt;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public class FriendshipRequestDao implements FriendshipRequestCtrlInt {

    private Connection dbConn;
    private ResultSet results;
    private PreparedStatement insStmt, updateStmt, delStmt;

    public FriendshipRequestDao() {
        dbConn = OracleDbConn.getDatabaseConnection();
    }

    @Override
    public FriendshipRequest retrieveFriendshipRequest(long fromUserId, long toUserId) {
        FriendshipRequest friendshipRequest = null;
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIP_REQUESTS WHERE FROM_USER = " + fromUserId
                    + " AND TO_USER = " + toUserId);
            if (results.next()) {
                UserDao userController = new UserDao();
                User requestFromUser = userController.retrieveUser(fromUserId);
                User requestToUser = userController.retrieveUser(toUserId);
                LocalDateTime requestDate = results.getTimestamp("REQUEST_DATE").toLocalDateTime();
                String requestAcceptedYN = results.getString("ACCEPTED_YN");
                LocalDateTime responseDate = results.getTimestamp("RESPONSE_DATE").toLocalDateTime();

                friendshipRequest = new FriendshipRequest(requestFromUser, requestToUser, requestDate,
                        requestAcceptedYN, responseDate);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friendshipRequest;
    }

    @Override
    public ArrayList<FriendshipRequest> retrieveOutcomingFriendshipRequests(long fromUserId) {
        ArrayList<FriendshipRequest> outcomingFriendshipRequests = new ArrayList<>();
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIP_REQUESTS WHERE FROM_USER = " + fromUserId);
            if (results.next()) {
                UserDao userController = new UserDao();
                User requestFromUser = userController.retrieveUser(fromUserId);
                User requestToUser = userController.retrieveUser(results.getLong("TO_USER"));
                LocalDateTime requestDate = results.getTimestamp("REQUEST_DATE").toLocalDateTime();
                String requestAcceptedYN = results.getString("ACCEPTED_YN");
                LocalDateTime responseDate = results.getTimestamp("RESPONSE_DATE").toLocalDateTime();

                outcomingFriendshipRequests.add(new FriendshipRequest(requestFromUser, requestToUser, requestDate,
                        requestAcceptedYN, responseDate));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return outcomingFriendshipRequests;
    }

    @Override
    public ArrayList<FriendshipRequest> retrieveIncomingFriendshipRequests(long toUserId) {
        ArrayList<FriendshipRequest> incomingFriendshipRequests = new ArrayList<>();
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIP_REQUESTS WHERE TO_USER = " + toUserId);
            if (results.next()) {
                UserDao userController = new UserDao();
                User requestFromUser = userController.retrieveUser(results.getLong("FROM_USER"));
                User requestToUser = userController.retrieveUser(toUserId);
                LocalDateTime requestDate = results.getTimestamp("REQUEST_DATE").toLocalDateTime();
                String requestAcceptedYN = results.getString("ACCEPTED_YN");
                LocalDateTime responseDate = results.getTimestamp("RESPONSE_DATE").toLocalDateTime();

                incomingFriendshipRequests.add(new FriendshipRequest(requestFromUser, requestToUser, requestDate,
                        requestAcceptedYN, responseDate));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return incomingFriendshipRequests;
    }

    @Override
    public int insertFriendshipRequest(FriendshipRequest friendshipRequest) {
        int rowsAffected = 0;
        try {
            insStmt = dbConn.prepareStatement("INSERT INTO FRIENDSHIP_REQUESTS (FROM_USER, TO_USER, REQUEST_DATE, ACCEPTED_YN, RESPONSE_DATE "
                    + "VALUES (?, ?, ?, ?, ?)");
            insStmt.setLong(1, friendshipRequest.getFromUser().getUserId());
            insStmt.setLong(2, friendshipRequest.getToUser().getUserId());
            insStmt.setTimestamp(3, Timestamp.valueOf(friendshipRequest.getRequestDate()));
            insStmt.setString(4, friendshipRequest.getAcceptedYN());
            insStmt.setTimestamp(5, Timestamp.valueOf(friendshipRequest.getResponseDate()));

            rowsAffected = insStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int updateFriendshipRequest(long fromUserId, long toUserId, FriendshipRequest friendshipRequest) {
        int rowsAffected = 0;
        try {
            updateStmt = dbConn.prepareStatement("UPDATE FRIENDSHIP_REQUESTS SET FROM_USER = ?, TO_USER = ?, "
                    + "REQUEST_DATE = ?, ACCEPTED_YN = ?, RESPONSE_DATE = ? "
                    + "WHERE FROM_USER = " + fromUserId + " AND TO_USER = " + toUserId);
            updateStmt.setLong(1, friendshipRequest.getFromUser().getUserId());
            updateStmt.setLong(2, friendshipRequest.getToUser().getUserId());
            updateStmt.setTimestamp(3, Timestamp.valueOf(friendshipRequest.getRequestDate()));
            updateStmt.setString(4, friendshipRequest.getAcceptedYN());
            updateStmt.setTimestamp(5, Timestamp.valueOf(friendshipRequest.getResponseDate()));

            rowsAffected = updateStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteFriendshipRequest(long fromUserId, long toUserId) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIP_REQUESTS WHERE FROM_USER = ? AND TO_USER = ?");
            delStmt.setLong(1, fromUserId);
            delStmt.setLong(2, toUserId);

            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteOutcomingFriendshipRequests(long fromUserId) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIP_REQUESTS WHERE FROM_USER = " + fromUserId);

            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteIncomingFriendshipRequests(long toUserId) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIP_REQUESTS WHERE TO_USER = " + toUserId);

            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

}
