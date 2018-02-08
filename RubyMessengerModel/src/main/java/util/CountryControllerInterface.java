package util;

import model.Country;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public interface CountryControllerInterface {
    Country retrieveCountry(long countryId);
    int insertCountry(Country c);
    int updateCountry(long countryId, Country c);
    int deleteCountry(long countryId);
}
