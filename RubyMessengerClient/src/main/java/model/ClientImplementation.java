package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import common.ClientInterface;
import controller.ChatRoomController;
import controller.MainSceneController;
import javafx.application.Platform;
import model.User;

/**
 *
 * @author khaled
 */
public class ClientImplementation extends UnicastRemoteObject implements ClientInterface {

    private MainSceneController myHomePage;
    private User user;


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
    public boolean receive(Message msg) throws RemoteException {
        User sender = msg.getSender();
        return true;
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
    
    
}
