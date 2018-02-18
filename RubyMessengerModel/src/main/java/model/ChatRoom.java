package model;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.Tab;
import model.User;
import model.User;

/**
 *
 * @author Mahmoud.Marzouk
 */
public class ChatRoom implements Serializable{
    
    private String chatRoomName;
    private ArrayList<User> users = new ArrayList<>();

    public ChatRoom() {
    }

    public ChatRoom(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

}
