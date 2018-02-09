package util;

import java.util.ArrayList;
import model.FriendshipRequest;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public interface FriendshipRequestCtrlInt {
    
    FriendshipRequest retrieveFriendshipRequest(long fromUserId, long toUserId);
    
    ArrayList<FriendshipRequest> retrieveOutcomingFriendshipRequests(long fromUserId);
    
    ArrayList<FriendshipRequest> retrieveIncomingFriendshipRequests(long toUserId);
    
    int insertFriendshipRequest(FriendshipRequest friendshipRequest);
    
    int updateFriendshipRequest(long fromUserId, long toUserId, FriendshipRequest friendshipRequest);
    
    int deleteFriendshipRequest(long fromUserId, long toUserId);
    
    int deleteOutcomingFriendshipRequests(long fromUserId);
    
    int deleteIncomingFriendshipRequests(long toUserId);
    
}
