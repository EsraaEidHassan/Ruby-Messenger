package util;

import java.util.ArrayList;
import model.Friendship;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public interface FriendshipCtrlInt {

    Friendship retrieveFriendship(long fromUserId, long friendId);

    ArrayList<Friendship> retrieveAllFriendships(long fromUserId);
    
    int insertFriendship(Friendship friendship);

    int updateFriendship(long fromUserId, long friendId, Friendship friendship);

    int deleteFriendship(long fromUserId, long friendId);

    int deleteAllFriendships(long fromUserId);

}
