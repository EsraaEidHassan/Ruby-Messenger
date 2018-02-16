package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public class ChatRoom implements Serializable {

    private ArrayList<User> roomUsers;

    public ArrayList<User> getRoomUsers() {
        return roomUsers;
    }

    public void setRoomClients(ArrayList<User> getRoomUsers) {
        this.roomUsers = getRoomUsers;
    }

}
