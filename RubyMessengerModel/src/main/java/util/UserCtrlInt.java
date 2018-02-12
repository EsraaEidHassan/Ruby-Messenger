package util;

import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public interface UserCtrlInt {
    
    User retrieveUser(long userId);
    
    User retrieveUser(String username);
    
    User retrieveUser(String username, String password);
    
    // Esraa Hassan start
    int[] retrieveMaleFemaleCount();
    // Esraa Hassan end
    
    int insertUser(User u);
    
    int updateUser(User u);
    
    int updateUser(long userId, User u);
    
    int deleteUser(User u);
    
    int deleteUser(String username);
}
