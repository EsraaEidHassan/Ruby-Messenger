package model;

import java.time.LocalDateTime;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public class User {

    private long userId;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private Country country;
    private LocalDateTime registrationDate;
    private String userStatus;
    private String userMode;

    /**
     * User mandatory attributes
     *
     * @param userId
     * @param userName
     * @param password
     * @param email
     */
    public User(long userId, String userName, String password, String email) {
        /* default values */
        this.registrationDate = LocalDateTime.now(); // to specify date and time, we can use of(..) method
        this.userStatus = "offline";
        this.userMode = "available";
        // -----------------------------------------------------------------------------------------------
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User(long userId, String userName, String password, String email, String firstName, String lastName,
            String gender, Country country) {
        this(userId, userName, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.country = country;
    }

    public User(long userId, String userName, String password, String email, String firstName, String lastName,
            String gender, Country country, LocalDateTime registrationDate, String userStatus, String userMode) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.country = country;
        this.registrationDate = registrationDate;
        this.userStatus = userStatus;
        this.userMode = userMode;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserMode() {
        return userMode;
    }

    public void setUserMode(String userMode) {
        this.userMode = userMode;
    }

}
