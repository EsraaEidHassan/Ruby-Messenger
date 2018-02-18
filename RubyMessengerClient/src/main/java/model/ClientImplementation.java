package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import common.ClientInterface;
import controller.ChatRoomController;
import controller.MainSceneController;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import model.User;

/**
 *
 * @author khaled
 */
public class ClientImplementation extends UnicastRemoteObject implements ClientInterface {

    private MainSceneController myHomePage;
    private User user;
    private HashMap<String, ChatRoomController> chatRoomControllers = new HashMap<>();


    public ClientImplementation() throws RemoteException {
    }

    public ClientImplementation(MainSceneController mainController) throws RemoteException {
        myHomePage = mainController;
    }

    public MainSceneController getMyHomePage() {
        return myHomePage;
    }

    public void setMyHomePage(MainSceneController myHomePage) {
        this.myHomePage = myHomePage;
    }

    public HashMap<String, ChatRoomController> getChatRoomControllers() {
        return chatRoomControllers;
    }

    public void setChatRoomControllers(HashMap<String, ChatRoomController> chatRoomControllers) {
        this.chatRoomControllers = chatRoomControllers;
    }

    @Override
    public User getUser() throws RemoteException {
        return user;
    }

    @Override
    public void setUser(User user) throws RemoteException {
        this.user = user;
    }

    // Mahmoud Marzouk 10/02/2018
    @Override
    public void receive(Message msg) throws RemoteException {
        myHomePage.showReceivedMessage(msg);
    }

    @Override
    public void recieveAnnouncement(String message) throws RemoteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myHomePage.renderAnnouncement(message);
            }
        });
    }

    @Override
    public boolean findClient(String usernameOrEmail) throws RemoteException {
        boolean res;
        if (user.getUsername().equals(usernameOrEmail) || user.getEmail().equals(usernameOrEmail)) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }
    
    @Override
    public void receiveFriendRequest(User fromUser) throws RemoteException {
        myHomePage.notifyNewFriendRequest(fromUser);
    }
    
    @Override
    public void sendFriendRequest(String usernameOrEmail) throws RemoteException {
        myHomePage.getServer().forwardFriendshipRequest(user, usernameOrEmail);
    }

    // Esraa Hassan start
    @Override
    public void recievNotificationFromOnlineFriend(String username) throws RemoteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myHomePage.recievNotificationFromOnlineFriend(username);
            }
        });
    }
    // Esraa Hassan end

 
    
}
