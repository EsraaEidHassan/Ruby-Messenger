package interfaces;

import model.FriendshipCategory;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public interface FriendshipCategoryCtrlInt {
    
    FriendshipCategory retrieveFriendshipCategory(long categoryId);

    int insertFriendshipCategory(FriendshipCategory friendshipCategory);

    int updateFriendshipCategory(FriendshipCategory friendshipCategory);
    
    int updateFriendshipCategory(long categoryId, FriendshipCategory friendshipCategory);

    int deleteFriendshipCategory(FriendshipCategory friendshipCategory);
}
