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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.FriendsList;
import model.User;
import view.FriendsListCellFactory;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class MainSceneController implements Initializable, FriendsListCallback {

    private ServerInterface server;
    private ClientInterface client;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ListView friendsListView;
    @FXML
    private TextField usernameOrEmailField;
    @FXML
    private Button sendRequestBtn;

    public ServerInterface getServer() {
        return server;
    }

    public void setServer(ServerInterface server) {
        this.server = server;
    }

    public ClientInterface getClient() {
        return client;
    }

    public void setClient(ClientInterface client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                populateFriendsList();
                sendRequestBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            client.sendFriendRequest(usernameOrEmailField.getText());
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void renderAnnouncement(String message) {
        System.out.println("Sever rejected your connection");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Announcement from the server");
        alert.setContentText("From server : " + message);
        alert.showAndWait();
    }

    // Mahmoud Marzouk
    private void populateFriendsList() {
        try {
            ObservableList<User> friends = FXCollections.observableArrayList(new FriendsList(client.getUser()).getFriends());
            FriendsListCellFactory friendsListFactory = new FriendsListCellFactory();
            friendsListFactory.setController(this);
            friendsListView.setCellFactory(friendsListFactory);
            friendsListView.setItems(friends);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onCellDoubleClickedAction(User user) {
        // to set our open chat action here
        System.out.println(user.getUsername());
    }

    public void notifyNewFriendRequest(User u) {
        // System.out.println("you have a friendship request from " + u.getUsername());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New friendship request");
                alert.setContentText("you have a friendship request from " + u.getUsername());
                alert.showAndWait();
            }
        });
    }

}
