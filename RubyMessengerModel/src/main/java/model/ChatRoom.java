package model;

import java.io.Serializable;
import java.util.ArrayList;
import model.User;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public class ChatRoom implements Serializable {

    private ArrayList<User> roomUsers;

    public ArrayList<User> getRoomUsers() {
        return roomUsers;
    }

    public void setRoomUsers(ArrayList<User> roomUsers) {
        this.roomUsers = roomUsers;
    }

}
