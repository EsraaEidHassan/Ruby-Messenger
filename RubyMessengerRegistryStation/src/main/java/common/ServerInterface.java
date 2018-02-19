
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import model.Country;
import model.Message;
import model.User;

/**
 *
 * @author toshiba
 */
public interface ServerInterface extends Remote{
    
    //Esraa Hassan
    public void register(ClientInterface client)throws RemoteException;
    //public boolean getAcceptedState()throws RemoteException;
    //public boolean getDecidedState() throws RemoteException;
    public void unregister(ClientInterface client)throws RemoteException;
    public boolean signup_user(User user)throws RemoteException;
    public boolean askUsersSendFile(String senderName, long receiverId , String fileName)throws RemoteException;
    public void sendFile(byte[] data , String fileName , int length  ,long receiverId)throws RemoteException;
    public User signInUser(String username, String password)throws RemoteException;
    public List<Country> retrieveAllCountries()throws RemoteException;
    public void sendAnnouncement(String message)throws RemoteException;
    public ArrayList<ClientInterface> getOnlineClientsFromUserObjects(ArrayList<User> users) throws RemoteException;
    // Mahmoud Marzouk
    void forwardFriendshipRequest(User fromUser, String usernameOrEmail) throws RemoteException;
    void forWardMessage(Message msg) throws RemoteException;
    void clearAllClients() throws RemoteException;
    // Esraa Hassan
    public void sendNotificationToOnlineFriends(String userName,ArrayList<ClientInterface> friends_Clients) throws RemoteException;
    // Esraa Hassan
    public boolean isThisUserLoggedIn(String username) throws RemoteException;
    
    // Ahmed Start
    //public void checkStateOFClients(ClientInterface client) throws RemoteException;
    // Ahmed End
}
