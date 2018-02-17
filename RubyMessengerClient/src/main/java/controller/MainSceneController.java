
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
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
import model.User;
import view.FriendsListCellFactory;
import common.ClientInterface;
import common.ServerInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ChatRoom;
import org.controlsfx.control.Notifications;
//import org.controlsfx.control.Notifications;
import view.FriendsListCallback;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class MainSceneController implements Initializable, FriendsListCallback {

    private ServerInterface server;
    private ClientInterface client;
    // Esraa Hassan
    private String username;

    // Ahmed St
    private ClientInterface receiver;
    private ChatRoom chatRoom;
    ArrayList<User> chatRoomUsers;
    private FXMLLoader loader;
    private Scene scene;
    private Parent root;
    private Stage mStage;
    // Ahmed En
    private double xOffset;
    private double yOffset;
    @FXML
    private BorderPane mainPane;
    @FXML
    private JFXTabPane menuTabbedPane;
    @FXML
    private FriendsContentController friendsRootController;
    @FXML
    private TabPane chatRoomsTabbedPane;
    private JFXListView mFriendsLVw;
    @FXML
    private ProfileController profileRootController;
    private Label userNameLabel;
    @FXML
    private ImageView logoutImgBtn;
    @FXML
    private ImageView userImg;
    @FXML
    private Label profileUsernameLabel;
    @FXML
    private ImageView statusIcon;
    @FXML
    private JFXComboBox<String> statusOptionsCB;

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
        try {
            this.username = this.client.getUser().getUsername();
        } catch (RemoteException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initController();
            }
        });
    }
    
    private void initController() {
        mStage = (Stage) mainPane.getScene().getWindow();
        
        try {
            profileUsernameLabel.setText(this.username);
            Circle clip = new Circle(38, 38, 38);
            userImg.setClip(clip);
            statusOptionsCB.getItems().addAll("available", "busy", "away");
            statusOptionsCB.setValue("available");
            statusOptionsCB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    statusIcon.setImage(new Image("/" + statusOptionsCB.getValue() + ".png"));
                }
            });
            
            userNameLabel = profileRootController.getUserNameLabel();
            userNameLabel.setText(client.getUser().getFirstName() + " " + client.getUser().getLastName());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
                
        mFriendsLVw = friendsRootController.getFriendsListView();
        populateFriendsList();
        
        menuTabbedPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
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
        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Announcement from the server");
        alert.setContentText("From server : " + message);
        alert.showAndWait();*/
        Notifications notificationBuilder = Notifications.create()
                    .title("Announcement from the server")
                    .text("From server : " + message)
                    .graphic(null)
                    .hideAfter(Duration.seconds(8))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("announcement has been clicked");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
    }
    
    // Esraa Hassan start
    public void recievNotificationFromOnlineFriend(String username){
        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setContentText("Your Friend " + username + " is now online");
        alert.showAndWait();*/
        System.out.println("This is user: "+this.username+" , receiving notification from user "+username);
        Notifications notificationBuilder = Notifications.create()
                .title("Notification")
                .text("Your Friend " + username + " is now online")
                .graphic(null)
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("notification has been clicked");
                    }
                });
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }
    // Esraa Hassan end

    // Mahmoud Marzouk
    private void populateFriendsList() {
        try {
             // Esraa Hassan start
             // this code is to send notification to all friends of this user that this user is noe online
             ArrayList<User> friendsList = new FriendsList(client.getUser()).getFriends();
             ArrayList<ClientInterface> clients_friendsList = server.getOnlineClientsFromUserObjects(friendsList);
             server.sendNotificationToOnlineFriends(this.username, clients_friendsList);
             // Esraa Hassan end
            ObservableList<User> friends = FXCollections.observableArrayList(friendsList);
            FriendsListCellFactory friendsListFactory = new FriendsListCellFactory();
            friendsListFactory.setController(this);
            mFriendsLVw.setCellFactory(friendsListFactory);
            mFriendsLVw.setItems(friends);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    private void openNewChatTabUI(String chatTabName) {
        Tab chatTab = new Tab(chatTabName);
        try {
            chatTab.setContent((AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/ChatRoom.fxml")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        chatRoomsTabbedPane.getTabs().add(chatTab);
        chatRoomsTabbedPane.getSelectionModel().select(chatTab);
    }
   

    @Override
    public void onCellDoubleClickedAction(User user) {
        /*
        try {
            chatRoom = new ChatRoom();
            chatRoomUsers.add(client.getUser());
            chatRoomUsers.add(user);
            chatRoom.setRoomClients(chatRoomUsers);
            */
            openNewChatTabUI("chat");
            /*
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
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
    
    @FXML
    public void holdChatWindow(MouseEvent event) {
        xOffset = mStage.getX() - event.getScreenX();
        yOffset = mStage.getY() - event.getScreenY();
    }
    
    @FXML
    public void dragChatWindow(MouseEvent event) {
        mStage.setX(event.getScreenX() + xOffset);
        mStage.setY(event.getScreenY() + yOffset);
    }
    
    @FXML
    public void closeMainScene() {
        // other operations before closing
        mStage.close();
    }
    
    @FXML
    public void minimizeMainScene() {
        mStage.setIconified(true);
    }
    
    @FXML
    public void logout(){
        // logout action here
    }

}
