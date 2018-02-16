
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import model.User;
import common.ClientInterface;
import common.ServerInterface;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.FriendsList;

// Ahmed Start
import model.Message;
import view.FriendsListCallback;
import view.FriendsListCellFactory;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class MainSceneController implements Initializable, FriendsListCallback {

    private ServerInterface server;
    private ClientInterface client;
    private Stage mStage;
    
    @FXML
    private BorderPane mainPane;
    @FXML
    private JFXTabPane tabbedPane;
    @FXML
    private FriendsContentController friendsRootController;
    @FXML
    private TabPane chatRoomsTabbedPane;
    private JFXListView mFriendsLVw;
    
    /*
    @FXML
    private TextField usernameOrEmailField;
    @FXML
    private Button sendRequestBtn;
    */

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
                initController();
                //populateFriendsList();
            }
        });
    }

    private void initController() {
        mStage = (Stage) mainPane.getScene().getWindow();
        
        mFriendsLVw = friendsRootController.getFriendsListView();
        populateFriendsList();
        
        Tab tab1 = new Tab("tab1");
        try {
            tab1.setContent((AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/ChatRoom.fxml")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        chatRoomsTabbedPane.getTabs().add(tab1);
        
        tabbedPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                // when change the selected tab
            }
        });
        
        /*
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
        */
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
            mFriendsLVw.setCellFactory(friendsListFactory);
            mFriendsLVw.setItems(friends);
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

    // Ahmed Start 
    public boolean sendMessage(String message) {
        Message msg = new Message();
        msg.setMessageContent(message);
        return false;

    }
    // Ahmed End
    
    
}

