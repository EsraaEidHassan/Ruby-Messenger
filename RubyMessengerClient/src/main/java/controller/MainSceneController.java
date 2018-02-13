/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import common.ClientInterface;
import common.ServerInterface;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.FriendsList;
import model.User;
import view.FriendsListCellFactory;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class MainSceneController implements Initializable {

    private ServerInterface server;
    private ClientInterface client;
    @FXML
    private ListView<User> friendsListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println(client.getUser().getUsername());
    }

    public void setServer(ServerInterface server) {
        this.server = server;
    }

    public void setClient(ClientInterface client) {
        this.client = client;
    }

    public void renderAnnouncement(String message) {
        System.out.println("Sever rejected your connection");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Announcement from the server");
        alert.setContentText("From server : " + message);
        alert.showAndWait();
    }

    public void populateFriendsList() {
        try {
            ObservableList<User> friends = FXCollections.observableArrayList(new FriendsList(client.getUser()).getFriends());
            friendsListView = new ListView<>(friends);
            friendsListView.setCellFactory(new FriendsListCellFactory());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
