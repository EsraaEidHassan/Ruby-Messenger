/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import common.ClientInterface;
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
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
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
}
