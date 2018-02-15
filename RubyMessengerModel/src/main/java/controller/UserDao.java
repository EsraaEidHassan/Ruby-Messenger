package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Country;
import model.User;
import util.UserCtrlInt;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public class UserDao implements UserCtrlInt {

    private Connection dbConn;
    private ResultSet results;
    private PreparedStatement insStmt, updateStmt, delStmt;

    public UserDao() {
        dbConn = OracleDbConn.getDatabaseConnection();
    }

    @Override
    public User retrieveUser(long userId) {
        // we need to check the return value from this method to avoid null pointer exceptions
        User u = null;
        try {
            results = dbConn.createStatement().executeQuery("select * from users where user_id = " + userId);
            if (results.next()) {
                String username = results.getString("USERNAME");
                String password = results.getString("PASSWORD");
                String email = results.getString("EMAIL");
                String firstName = results.getString("FIRST_NAME");
                String lastName = results.getString("LAST_NAME");
                String gender = results.getString("GENDER");
                Country country = new CountryDao().retrieveCountry(results.getLong("COUNTRY"));
                u = new User(username, password, email, firstName, lastName, gender, country);
                u.setUserId(userId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }

    @Override
    public User retrieveUser(String username) {
        // we need to check the return value from this method to avoid null pointer exceptions
        User u = null;
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM USERS WHERE USERNAME = '" + username + "'");
            if (results.next()) {
                long userId = results.getLong("USER_ID");
                String password = results.getString("PASSWORD");
                String email = results.getString("EMAIL");
                String firstName = results.getString("FIRST_NAME");
                String lastName = results.getString("LAST_NAME");
                String gender = results.getString("GENDER");
                Country country = new CountryDao().retrieveCountry(results.getLong("COUNTRY"));
                u = new User(username, password, email, firstName, lastName, gender, country);
                u.setUserId(userId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }

    @Override
    public User retrieveUser(String username, String password) {
        // we need to check the return value from this method to avoid null pointer exceptions
        User u = null;
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM USERS WHERE USERNAME = '" + username
                    + "' AND PASSWORD = '" + password + "'");
            if (results.next()) {
                long userId = results.getLong("USER_ID");
                //String username = results.getString("USERNAME");
                //String password = results.getString("PASSWORD");
                String email = results.getString("EMAIL");
                String firstName = results.getString("FIRST_NAME");
                String lastName = results.getString("LAST_NAME");
                String gender = results.getString("GENDER");
                String status = results.getString("USER_STATUS");
                String mode = results.getString("USER_MODE");
                Country country = new CountryDao().retrieveCountry(results.getLong("COUNTRY"));
                u = new User(username, password, email, firstName, lastName, gender, country);
                u.setUserId(userId);
                u.setUserStatus(status);
                u.setUserMode(mode);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }
    
    // Esraa Hassan start
    @Override
    public int[] retrieveMaleFemaleCount(){
        int[] male_female_count = new int[2]; // index 0 for male // index 1 for female
        long female_count = 0;
        long male_count = 0;
        try {
            // getting number of female;
            results = dbConn.createStatement().executeQuery("SELECT count(*) FROM USERS WHERE upper(GENDER) = upper('Female')");
            if (results.next()) {
                female_count = results.getLong(1);
            }
            // getting number of male;
            results = dbConn.createStatement().executeQuery("SELECT count(*) FROM USERS WHERE upper(GENDER) = upper('Male')");
            if (results.next()) {
                male_count = results.getLong(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        male_female_count[0] = (int)male_count;
        male_female_count[1] = (int)female_count;
        
        return male_female_count;
    }
    // Esraa Hassan end

    public int insertUser(User u) {
        int rowsAffected = 0;
        try {
            insStmt = dbConn.prepareStatement("INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, EMAIL, FIRST_NAME, LAST_NAME, "
                    + "GENDER, COUNTRY, REGISTRATION_DATE, USER_STATUS, USER_MODE) "
                    + "VALUES (USER_ID_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insStmt.setString(1, u.getUsername());
            insStmt.setString(2, u.getPassword());
            insStmt.setString(3, u.getEmail());
            insStmt.setString(4, u.getFirstName());
            insStmt.setString(5, u.getLastName());
            insStmt.setString(6, u.getGender());
            insStmt.setLong(7, u.getCountry().getCountryId());
            insStmt.setTimestamp(8, Timestamp.valueOf(u.getRegistrationDate()));
            insStmt.setString(9, u.getUserStatus());
            insStmt.setString(10, u.getUserMode());

            rowsAffected = insStmt.executeUpdate();
            //dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    public int updateUser(User u) {
        int rowsAffected = 0;
        try {
            updateStmt = dbConn.prepareStatement("UPDATE USERS SET USERNAME = ?, PASSWORD = ?, EMAIL = ?, FIRST_NAME = ?, "
                    + "LAST_NAME = ?, GENDER = ?, COUNTRY = ?, REGISTRATION_DATE = ?, USER_STATUS = ?, USER_MODE = ? "
                    + "WHERE USER_ID = " + u.getUserId());
            updateStmt.setString(2, u.getUsername());
            updateStmt.setString(3, u.getPassword());
            updateStmt.setString(4, u.getEmail());
            updateStmt.setString(5, u.getFirstName());
            updateStmt.setString(6, u.getLastName());
            updateStmt.setString(7, u.getGender());
            updateStmt.setLong(8, u.getCountry().getCountryId());
            updateStmt.setTimestamp(9, Timestamp.valueOf(u.getRegistrationDate()));
            updateStmt.setString(10, u.getUserStatus());
            updateStmt.setString(11, u.getUserMode());

            rowsAffected = updateStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    public int updateUser(long userId, User u) {
        int rowsAffected = 0;
        try {
            updateStmt = dbConn.prepareStatement("UPDATE USERS SET USER_ID = ?, USERNAME = ?, PASSWORD = ?, EMAIL = ?, FIRST_NAME = ?, "
                    + "LAST_NAME = ?, GENDER = ?, COUNTRY = ?, REGISTRATION_DATE = ?, USER_STATUS = ?, USER_MODE = ?"
                    + " WHERE USER_ID = " + userId);
            updateStmt.setLong(1, u.getUserId());
            updateStmt.setString(2, u.getUsername());
            updateStmt.setString(3, u.getPassword());
            updateStmt.setString(4, u.getEmail());
            updateStmt.setString(5, u.getFirstName());
            updateStmt.setString(6, u.getLastName());
            updateStmt.setString(7, u.getGender());
            updateStmt.setLong(8, u.getCountry().getCountryId());
            updateStmt.setTimestamp(9, Timestamp.valueOf(u.getRegistrationDate()));
            updateStmt.setString(10, u.getUserStatus());
            updateStmt.setString(11, u.getUserMode());

            rowsAffected = updateStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    public int deleteUser(User u) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM USERS WHERE USER_ID = " + u.getUserId());

            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rowsAffected;
    }

    public int deleteUser(String username) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM USERS WHERE USERNAME = '" + username + "'");

            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rowsAffected;
    }

    //khaled start
    @Override
    public int[] retrieveOnlineOflineCount() {
        int[] online_offline_count = new int[2]; // index 0 for online // index 1 for offline
        long offline_count = 0;
        long online_count = 0;
        try {
            // getting number of offline;
            results = dbConn.createStatement().executeQuery("SELECT count(*) FROM USERS WHERE upper(user_status) = upper('offline')");
            if (results.next()) {
                offline_count = results.getLong(1);
            }
            // getting number of online;
            results = dbConn.createStatement().executeQuery("SELECT count(*) FROM USERS WHERE upper(user_status) = upper('online')");
            if (results.next()) {
                online_count = results.getLong(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        online_offline_count[0] = (int)online_count;
        online_offline_count[1] = (int)offline_count;
        
        return online_offline_count;
    }
    //khaled end
}
