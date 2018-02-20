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
    
    private String chatRoomId;
    private ArrayList<User> users = new ArrayList<>();
     private boolean isFileSent;

    public boolean isFileSent() {
        return isFileSent;
    }

    public void setIsFileSent(boolean isFileSent) {
        this.isFileSent = isFileSent;
    }


    public ChatRoom() {
    }

    public ChatRoom(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

}
