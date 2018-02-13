package util;

import java.util.ArrayList;
import java.util.Map;
import model.Country;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public interface CountryCtrlInt {

    Country retrieveCountry(long countryId);
    
    Country retrieveCountry(String countryCode);
    
    ArrayList<Country> retrieveAllCountries();

    int insertCountry(Country c);
    
    int updateCountry(Country c);

    int updateCountry(long countryId, Country c);

    int deleteCountry(Country c);
    
    Map<String,Integer> getCountriesUsers();
}
