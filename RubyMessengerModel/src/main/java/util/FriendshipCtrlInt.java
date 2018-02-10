package util;

import java.util.ArrayList;
import model.Friendship;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public interface FriendshipCtrlInt {

    Friendship retrieveFriendship(User fromUser, User friend);

    ArrayList<Friendship> retrieveAllFriendships(User fromUser);
    
    int insertFriendship(Friendship friendship);

    int updateFriendship(Friendship friendship);
    
    int updateFriendship(User fromUser, User friend, Friendship friendship);

    int deleteFriendship(Friendship friendship);

    int deleteAllFriendships(User fromUser);

}
