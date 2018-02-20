/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Message;
import model.User;

/**
 *
 * @author toshiba
 */
public interface ClientInterface extends Remote {
    public boolean sendFileRequest(String senderName ,String fileName)throws RemoteException;
    public void reciveFile(byte[] filePartData,String fileName,int length)throws RemoteException; 
    //public boolean receive(Message msg  , User sender) throws RemoteException;
    //Esraa Hassan
    User getUser() throws RemoteException;
    
    void setUser(User client) throws RemoteException;

    void receive(Message msg) throws RemoteException;

    void recieveAnnouncement(String message) throws RemoteException;
    
    // Mahmoud Marzouk
    void sendFriendRequest(String usernameOrEmail) throws RemoteException;
    
    boolean findClient(String usernameOrEmail) throws RemoteException;
    
    void receiveFriendRequest(User fromUser) throws RemoteException;
    
    User checkFriendUserExistence(String usernameOrEmail) throws RemoteException;
    
    void updateFriendMode(User user) throws RemoteException;
    
    // Esraa Hassan 
    public void recievNotificationFromOnlineFriend(User user) throws RemoteException;
}
