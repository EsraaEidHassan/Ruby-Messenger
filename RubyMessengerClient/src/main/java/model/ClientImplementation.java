package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import common.ClientInterface;
import controller.ChatRoomController;
import controller.FriendshipRequestDao;
import controller.MainSceneController;
import controller.UserDao;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    public boolean sendFileRequest(String senderName, String fileName) throws RemoteException {
        boolean isAccepted = myHomePage.showFileRequestAlert(senderName , fileName);
        return isAccepted;
    }
    
    @Override
    public synchronized void reciveFile(byte[] filePartData, String fileName, int length) throws RemoteException {
        try {
            String pathDefault = "C:\\Users\\Public\\Downloads\\";
            File f = new File(pathDefault + fileName);
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f, true);
            out.write(filePartData , 0, length);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void notifyFileSent(String senderName) throws RemoteException {
        myHomePage.showFileSentNotification(senderName);
    }
    @Override
    public void receiveFriendRequest(User fromUser) throws RemoteException {
        myHomePage.notifyNewFriendRequest(fromUser);
    }
    
    @Override
    public void sendFriendRequest(String usernameOrEmail) throws RemoteException {
        myHomePage.getServer().forwardFriendshipRequest(user, usernameOrEmail);
        new FriendshipRequestDao().insertFriendshipRequest(new FriendshipRequest(user, 
                checkFriendUserExistence(usernameOrEmail)));
    }
    
    public User checkFriendUserExistence(String usernameOrEmail) throws RemoteException {  
        User user = new UserDao().retrieveUser(usernameOrEmail);
        return user;
    }

    // Esraa Hassan start
    @Override
    public void recievNotificationFromOnlineFriend(User user) throws RemoteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myHomePage.recievNotificationFromOnlineFriend(user);
            }
        });
    }
    // Esraa Hassan end



}
