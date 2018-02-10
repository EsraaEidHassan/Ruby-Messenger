/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import common.ClientInterface;
import model.Message;
import model.User;

/**
 *
 * @author khaled
 */
public class ClientImplementation extends UnicastRemoteObject implements ClientInterface{

    private User user;

    
    public ClientImplementation() throws RemoteException{
    }

    @Override
    public boolean receive(Message msg, User sender) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setUser(User user) throws RemoteException{
        this.user = user;
    }
    
    @Override
    public User getUser() throws RemoteException{
        return this.user;
    }
}
