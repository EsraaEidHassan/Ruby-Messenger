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
    public FriendshipRequest retrieveFriendshipRequest(User fromUser, User toUser) {
        FriendshipRequest friendshipRequest = null;
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIP_REQUESTS WHERE FROM_USER = " 
                    + fromUser.getUserId() + " AND TO_USER = " + toUser.getUserId());
            if (results.next()) {
                LocalDateTime requestDate = results.getTimestamp("REQUEST_DATE").toLocalDateTime();
                String requestSeenYN = results.getString("SEEN_YN");
                String requestAcceptedYN = results.getString("ACCEPTED_YN");
                LocalDateTime responseDate = results.getTimestamp("RESPONSE_DATE").toLocalDateTime();

                friendshipRequest = new FriendshipRequest(fromUser, toUser, requestDate, requestSeenYN, 
                        requestAcceptedYN, responseDate);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friendshipRequest;
    }

    @Override
    public ArrayList<FriendshipRequest> retrieveOutcomingFriendshipRequests(User fromUser) {
        ArrayList<FriendshipRequest> outcomingFriendshipRequests = new ArrayList<>();
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIP_REQUESTS WHERE FROM_USER = " 
                    + fromUser.getUserId());
            if (results.next()) {
                User toUser = new UserDao().retrieveUser(results.getLong("TO_USER"));
                LocalDateTime requestDate = results.getTimestamp("REQUEST_DATE").toLocalDateTime();
                String requestSeenYN = results.getString("SEEN_YN");
                String requestAcceptedYN = results.getString("ACCEPTED_YN");
                LocalDateTime responseDate = results.getTimestamp("RESPONSE_DATE").toLocalDateTime();
                outcomingFriendshipRequests.add(new FriendshipRequest(fromUser, toUser, requestDate, requestSeenYN, 
                        requestAcceptedYN, responseDate));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return outcomingFriendshipRequests;
    }
    
    @Override
    public ArrayList<FriendshipRequest> retrieveIncomingFriendshipRequests(User toUser) {
        ArrayList<FriendshipRequest> incomingFriendshipRequests = new ArrayList<>();
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIP_REQUESTS WHERE TO_USER = " + toUser.getUserId());
            if (results.next()) {
                User fromUser = new UserDao().retrieveUser(results.getLong("FROM_USER"));
                LocalDateTime requestDate = results.getTimestamp("REQUEST_DATE").toLocalDateTime();
                String requestSeenYN = results.getString("SEEN_YN");
                String requestAcceptedYN = results.getString("ACCEPTED_YN");
                LocalDateTime responseDate = results.getTimestamp("RESPONSE_DATE").toLocalDateTime();

                incomingFriendshipRequests.add(new FriendshipRequest(fromUser, toUser, requestDate, requestSeenYN, 
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
            insStmt = dbConn.prepareStatement("INSERT INTO FRIENDSHIP_REQUESTS (FROM_USER, TO_USER, REQUEST_DATE, SEEN_YN, "
                    + "ACCEPTED_YN, RESPONSE_DATE "
                    + "VALUES (?, ?, ?, ?, ?, ?)");
            insStmt.setLong(1, friendshipRequest.getFromUser().getUserId());
            insStmt.setLong(2, friendshipRequest.getToUser().getUserId());
            insStmt.setTimestamp(3, Timestamp.valueOf(friendshipRequest.getRequestDate()));
            insStmt.setString(4, friendshipRequest.getSeenYN());
            insStmt.setString(5, friendshipRequest.getAcceptedYN());
            insStmt.setTimestamp(6, Timestamp.valueOf(friendshipRequest.getResponseDate()));

            rowsAffected = insStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int updateFriendshipRequest(FriendshipRequest friendshipRequest) {
        int rowsAffected = 0;
        try {
            updateStmt = dbConn.prepareStatement("UPDATE FRIENDSHIP_REQUESTS SET REQUEST_DATE = ?, SEEN_YN = ?, ACCEPTED_YN = ?, "
                    + "RESPONSE_DATE = ? WHERE FROM_USER = " + friendshipRequest.getFromUser() 
                    + " AND TO_USER = " + friendshipRequest.getToUser());
            updateStmt.setTimestamp(1, Timestamp.valueOf(friendshipRequest.getRequestDate()));
            updateStmt.setString(2, friendshipRequest.getSeenYN());
            updateStmt.setString(3, friendshipRequest.getAcceptedYN());
            updateStmt.setTimestamp(4, Timestamp.valueOf(friendshipRequest.getResponseDate()));
            
            rowsAffected = updateStmt.executeUpdate();
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
                    + "REQUEST_DATE = ?, SEEN_YN = ?, ACCEPTED_YN = ?, RESPONSE_DATE = ? "
                    + "WHERE FROM_USER = " + fromUserId + " AND TO_USER = " + toUserId);
            updateStmt.setLong(1, friendshipRequest.getFromUser().getUserId());
            updateStmt.setLong(2, friendshipRequest.getToUser().getUserId());
            updateStmt.setTimestamp(3, Timestamp.valueOf(friendshipRequest.getRequestDate()));
            updateStmt.setString(4, friendshipRequest.getSeenYN());
            updateStmt.setString(5, friendshipRequest.getAcceptedYN());
            updateStmt.setTimestamp(6, Timestamp.valueOf(friendshipRequest.getResponseDate()));

            rowsAffected = updateStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteFriendshipRequest(FriendshipRequest friendshipRequest) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIP_REQUESTS WHERE FROM_USER = ? AND TO_USER = ?");
            delStmt.setLong(1, friendshipRequest.getFromUser().getUserId());
            delStmt.setLong(2, friendshipRequest.getToUser().getUserId());

            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteOutcomingFriendshipRequests(User fromUser) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIP_REQUESTS WHERE FROM_USER = " + fromUser.getUserId());

            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteIncomingFriendshipRequests(User toUser) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIP_REQUESTS WHERE TO_USER = " + toUser.getUserId());

            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

}
