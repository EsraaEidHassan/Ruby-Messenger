package model;

import java.time.LocalDateTime;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public class Friendship {

    private User fromUser;
    private User friend;
    private LocalDateTime friendshipStartDate;
    private LocalDateTime friendshipEndDate;
    private String friendBlockedYN;
    private FriendshipCategory friendshipCategory;

    /**
     * Friendship mandatory attributes
     *
     * @param fromUser
     * @param friend
     */
    public Friendship(User fromUser, User friend) {
        /* default values */
        this.friendshipStartDate = LocalDateTime.now();
        this.friendshipEndDate = null;
        this.friendBlockedYN = "N";
        // category is null by default
        // -----------------------------------------------------------------------------------------------
        this.fromUser = fromUser;
        this.friend = friend;
    }

    public Friendship(User fromUser, User friend, LocalDateTime friendshipStartDate, LocalDateTime friendshipEndDate,
            String friendBlockedYN, FriendshipCategory friendshipCategory) {
        this.fromUser = fromUser;
        this.friend = friend;
        this.friendshipStartDate = friendshipStartDate;
        this.friendshipEndDate = friendshipEndDate;
        this.friendBlockedYN = friendBlockedYN;
        this.friendshipCategory = friendshipCategory;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public LocalDateTime getFriendshipStartDate() {
        return friendshipStartDate;
    }

    public void setFriendshipStartDate(LocalDateTime friendshipStartDate) {
        this.friendshipStartDate = friendshipStartDate;
    }

    public LocalDateTime getFriendshipEndDate() {
        return friendshipEndDate;
    }

    public void setFriendshipEndDate(LocalDateTime friendshipEndDate) {
        this.friendshipEndDate = friendshipEndDate;
    }

    public String getFriendBlockedYN() {
        return friendBlockedYN;
    }

    public void setFriendBlockedYN(String friendBlockedYN) {
        this.friendBlockedYN = friendBlockedYN;
    }

    public FriendshipCategory getFriendshipCategory() {
        return friendshipCategory;
    }

    public void setFriendshipCategory(FriendshipCategory friendshipCategory) {
        this.friendshipCategory = friendshipCategory;
    }

}
