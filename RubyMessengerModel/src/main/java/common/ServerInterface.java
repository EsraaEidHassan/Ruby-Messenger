/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.User;

/**
 *
 * @author toshiba
 */
public interface ServerInterface extends Remote{
    
    //Esraa Hassan
    public void register(ClientInterface client)throws RemoteException;
    public boolean getAcceptedState()throws RemoteException;
    public boolean getDecidedState() throws RemoteException;
    public void unregister(ClientInterface client)throws RemoteException;
    public boolean signup_user(User user)throws RemoteException;
    public User signInUser(String username, String password)throws RemoteException;
}
