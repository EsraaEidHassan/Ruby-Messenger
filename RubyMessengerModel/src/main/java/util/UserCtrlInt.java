package util;

import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public interface UserCtrlInt {

    User retrieveUser(long userId);
    
    User retrieveUser(String userName, String password);
    
    int insertUser(User u);
    
    int updateUser(long userId, User u);
    
    int deleteUser(long userId);
}
