package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Country;
import util.CountryCtrlInt;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public class CountryDao implements CountryCtrlInt {

    private Connection dbConn;
    private ResultSet results;
    private PreparedStatement insStmt, updateStmt, delStmt;

    public CountryDao() {
        dbConn = OracleDbConn.getDatabaseConnection();
    }

    @Override
    public Country retrieveCountry(long countryId) {
        // we need to check the return value from this method to avoid null pointer exceptions
        Country c = null;
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM COUNTRIES WHERE COUNTRY_ID = " + countryId);
            if (results.next()) {
                String countryCode = results.getString("COUNTRY_CODE");
                String countryName = results.getString("COUNTRY_NAME");
                c = new Country(countryId, countryCode, countryName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return c;
    }

    @Override
    public int insertCountry(Country c) {
        int rowsAffected = 0;
        try {
            insStmt = dbConn.prepareStatement("INSERT INTO COUNTRIES (COUNTRY_ID, COUNTRY_CODE, COUNTRY_NAME) "
                    + "VALUES (?, ?, ?)");
            insStmt.setLong(1, c.getCountryId());
            insStmt.setString(2, c.getCountryCode());
            insStmt.setString(3, c.getCountryName());

            rowsAffected = insStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int updateCountry(long countryId, Country c) {
        int rowsAffected = 0;
        try {
            updateStmt = dbConn.prepareStatement("UPDATE COUNTRIES SET COUNTRY_ID = ?, COUNTRY_CODE = ?, COUNTRY_NAME = ? "
                    + "WHERE COUNTRY_ID = " + countryId);
            updateStmt.setLong(1, c.getCountryId());
            updateStmt.setString(2, c.getCountryCode());
            updateStmt.setString(3, c.getCountryName());

            rowsAffected = updateStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteCountry(long countryId) {
        // it may cause ORA-02292 (integrity constraint (CHAT.USERS_R01) violated - child record found)
        // as this table has childs in other tables
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM COUNTRIES WHERE COUNTRY_ID = " + countryId);
            rowsAffected = delStmt.executeUpdate();
            dbConn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

}
