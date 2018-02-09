package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FriendshipCategory;
import util.FriendshipCategoryCtrlInt;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public class FriendshipCategoryDao implements FriendshipCategoryCtrlInt {

    private Connection dbConn;
    private ResultSet results;
    private PreparedStatement insStmt, updateStmt, delStmt;

    public FriendshipCategoryDao() {
        dbConn = OracleDbConn.getDatabaseConnection();
    }

    @Override
    public FriendshipCategory retrieveFriendshipCategory(long categoryId) {
        FriendshipCategory friendshipCategory = null;
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM FRIENDSHIP_CATEGORIES WHERE CATEGORY_ID = " + categoryId);
            if (results.next()) {
                String categoryName = results.getString("CATEGORY_DESC");
                friendshipCategory = new FriendshipCategory(categoryId, categoryName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friendshipCategory;
    }

    @Override
    public int insertFriendshipCategory(FriendshipCategory friendshipCategory) {
        int rowsAffected = 0;
        try {
            insStmt = dbConn.prepareStatement("INSERT INTO FRIENDSHIP_CATEGORIES (CATEGORY_ID, CATEGORY_DESC) VALUES (?, ?)");
            insStmt.setLong(1, friendshipCategory.getCategoryId());
            insStmt.setString(2, friendshipCategory.getCategoryName());

            rowsAffected = insStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int updateFriendshipCategory(long categoryId, FriendshipCategory friendshipCategory) {
        int rowsAffected = 0;
        try {
            updateStmt = dbConn.prepareStatement("UPDATE FRIENDSHIP_CATEGORIES SET CATEGORY_ID = ?, CATEGORY_DESC = ? "
                    + "WHERE CATEGORY_ID = " + categoryId);
            updateStmt.setLong(1, friendshipCategory.getCategoryId());
            updateStmt.setString(2, friendshipCategory.getCategoryName());

            rowsAffected = updateStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteFriendshipCategory(long categoryId) {
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM FRIENDSHIP_CATEGORIES WHERE CATEGORY_ID = " + categoryId);
            rowsAffected = delStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

}
