package model;

import java.io.Serializable;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public class Country implements Serializable {
    
    private long countryId;
    private String countryCode;
    private String countryName;

    public Country() {
        countryId = 63; // country is Egypt by default
        countryName = "Egypt"; //Esraa Hassan
        countryCode = "EG"; //Esraa Hassan
    }
    
    public Country(String countryName) {
        this.countryName = countryName;
    }

    public Country(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public long getCountryId() {
        return countryId;
    }
    
    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }
    
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
