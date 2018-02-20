package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import model.Country;
import interfaces.CountryCtrlInt;

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
            // Esraa Hassan (changed if condition to while)
            while (results.next()) {
                String countryCode = results.getString("COUNTRY_CODE");
                String countryName = results.getString("COUNTRY_NAME");
                c = new Country(countryCode, countryName);
                c.setCountryId(countryId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return c;
    }
    
    @Override
    public Country retrieveCountry(String countryCode) {
        // we need to check the return value from this method to avoid null pointer exceptions
        Country c = null;
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM COUNTRIES WHERE COUNTRY_CODE = '" + countryCode + "'");
            // Esraa Hassan (changed if condition to while)
            while (results.next()) {
                long countryId = results.getLong("COUNTRY_ID");
                String countryName = results.getString("COUNTRY_NAME");
                c = new Country(countryCode, countryName);
                c.setCountryId(countryId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return c;
    }
    
    @Override
    public List<Country> retrieveAllCountries() {
        // we need to check the return value from this method to avoid null pointer exceptions
        List<Country> countries = new ArrayList<Country>();
        try {
            results = dbConn.createStatement().executeQuery("SELECT * FROM COUNTRIES");
            // Esraa Hassan (changed if condition to while)
            while (results.next()) {
                long countryId = results.getLong("COUNTRY_ID");
                String countryCode = results.getString("COUNTRY_CODE");
                String countryName = results.getString("COUNTRY_NAME");
                Country c = new Country(countryCode, countryName);
                c.setCountryId(countryId);
                countries.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return countries;
    }

    @Override
    public int insertCountry(Country c) {
        int rowsAffected = 0;
        try {
            insStmt = dbConn.prepareStatement("INSERT INTO COUNTRIES (COUNTRY_ID, COUNTRY_CODE, COUNTRY_NAME) "
                    + "VALUES (COUNTRY_ID_SEQ.NEXTVAL, ?, ?)");
            insStmt.setString(1, c.getCountryCode());
            insStmt.setString(2, c.getCountryName());

            rowsAffected = insStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int updateCountry(Country c) {
        int rowsAffected = 0;
        try {
            updateStmt = dbConn.prepareStatement("UPDATE COUNTRIES SET COUNTRY_CODE = ?, COUNTRY_NAME = ? "
                    + "WHERE COUNTRY_ID = " + c.getCountryId());
            updateStmt.setString(1, c.getCountryCode());
            updateStmt.setString(2, c.getCountryName());

            rowsAffected = updateStmt.executeUpdate();
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteCountry(Country c) {
        // it may cause ORA-02292 (integrity constraint (CHAT.USERS_R01) violated - child record found)
        // as this table has childs in other tables
        int rowsAffected = 0;
        try {
            delStmt = dbConn.prepareStatement("DELETE FROM COUNTRIES WHERE COUNTRY_ID = " + c.getCountryId());
            rowsAffected = delStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public Map<String, Integer> getCountriesUsers() {
        Map<String, Integer> countriesUsers = new HashMap<>();
        
        try {
            results = dbConn.createStatement().executeQuery("SELECT COUNT(USER_ID), NVL(COUNTRY_NAME, 'Undefined') COUNTRY FROM USERS U, COUNTRIES C \n" +
                    "WHERE U.COUNTRY = C.COUNTRY_ID(+)\n" +
                    "GROUP BY NVL(COUNTRY_NAME, 'Undefined')");
            while (results.next()) {
                int usersNo = results.getInt(1);
                String countryName = results.getString(2);
                countriesUsers.put(countryName, usersNo);
            }
                    } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return countriesUsers;
    }

}
