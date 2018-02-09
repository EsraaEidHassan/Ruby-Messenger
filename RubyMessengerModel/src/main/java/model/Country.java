package model;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public class Country {

    private long countryId;
    private String countryCode;
    private String countryName;

    public Country(long countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public Country(long countryId, String countryCode, String countryName) {
        this.countryId = countryId;
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
