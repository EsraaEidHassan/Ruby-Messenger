/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import common.ClientInterface;
import javafx.application.Platform;
import model.Message;
import model.User;

/**
 *
 * @author khaled
 */
public class ClientImplementation extends UnicastRemoteObject implements ClientInterface{

    private User user;
    private MainSceneController myHomePage;
    
    
    public ClientImplementation(MainSceneController mainController) throws RemoteException{
        myHomePage = mainController;
    }
    
    // mahmoud marzouk 10/02/2018
    
    @Override
    public boolean receive(Message msg) throws RemoteException {
        User sender = msg.getSender();
        return true;
    }
    
    @Override
    public void setUser(User user) throws RemoteException{
        this.user = user;
    }
    
    @Override
    public User getUser() throws RemoteException{
        return this.user;
    }

    @Override
    public void recieveAnnouncement(String message) throws RemoteException {
        Platform.runLater(new Runnable (){
            @Override
            public void run() {
                myHomePage.renderAnnouncement(message);
            }
        
        });
    }
}
