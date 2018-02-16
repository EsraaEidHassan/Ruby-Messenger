package interfaces;

import java.util.ArrayList;
import model.FriendshipRequest;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public interface FriendshipRequestCtrlInt {
    
    FriendshipRequest retrieveFriendshipRequest(User fromUser, User toUser);
    
    ArrayList<FriendshipRequest> retrieveOutcomingFriendshipRequests(User fromUser);
    
    ArrayList<FriendshipRequest> retrieveIncomingFriendshipRequests(User toUser);
    
    int insertFriendshipRequest(FriendshipRequest friendshipRequest);
    
    int updateFriendshipRequest(FriendshipRequest friendshipRequest);
    
    int updateFriendshipRequest(long fromUserId, long toUserId, FriendshipRequest friendshipRequest);
    
    int deleteFriendshipRequest(FriendshipRequest friendshipRequest);
    
    int deleteOutcomingFriendshipRequests(User fromUser);
    
    int deleteIncomingFriendshipRequests(User toUser);
    
}
