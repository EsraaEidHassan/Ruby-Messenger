package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public class UserDao {
    
    private Connection dbConn;
    private ResultSet results;
    private PreparedStatement insStmt, updateStmt, delStmt;

    public UserDao() {
        dbConn = OracleDbConn.getDatabaseConnection();
    }
    
    public User retrieveUser(int userId) {
        // we need to check the return value from this method to avoid null pointer exceptions
        User u = null;
        try {
            results = dbConn.createStatement().executeQuery("select * from users where user_id = " + userId);
            if (results.next()) {
                String userName = results.getString("USERNAME");
                String password = results.getString("PASSWORD");
                String email = results.getString("EMAIL");
                String firstName = results.getString("FIRST_NAME");
                String lastName = results.getString("LAST_NAME");
                String gender = results.getString("GENDER");
                String country = results.getString("COUNTRY");
                u = new User(userId, userName, password, email, firstName, lastName, gender, country);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }

    public void insertUser(User u) {
        //insStmt
    }

    public void updateUser(int userId, User u) {
        
    }

    public void deleteUser(int userId) {
        
    }
    
}
