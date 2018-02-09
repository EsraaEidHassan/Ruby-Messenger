package util;

import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public interface UserControllerIntrface {
    User retrieveUser(long userId);
    User retrieveUser(String username, String password);
    int insertUser(User u);
    int updateUser(long userId, User u);
    int deleteUser(long userId);
}
