package model;

import controller.FriendshipDao;
import java.util.ArrayList;

/**
 * @author Mahmoud.Marzouk
 * @since 12/02/2018
 */
public class FriendsList {

    ArrayList<User> friends;

    public FriendsList(User u) {
        friends = new FriendshipDao().retrieveAllFriends(u);
    }

    public ArrayList<User> getFriends() {
        return friends;
    }
    
    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return friends.toString();
    }
    
}
