
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
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ChatRoom;
import model.ClientImplementation;
import model.Message;
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
    
    private Stage mStage;
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
        
        /*
        menuTabbedPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                // when change the selected tab
            }
        });
        */

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
    
    private ChatRoomController openChatRoom(String chatRoomName, User user, boolean isOpened) {
        ChatRoomController returnedChatRoomCtrl = null;
        HashMap<String, ChatRoomController> chatRoomControllers = ((ClientImplementation)client).getChatRoomControllers();
        if (chatRoomControllers.get(chatRoomName) == null) {
            Tab chatTab = new Tab(chatRoomName);
            chatTab.setOnCloseRequest(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    String chatRoomName = chatTab.getText();
                    ((ClientImplementation)client).getChatRoomControllers().remove(chatRoomName);
                }
            });
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChatRoom.fxml"));
                chatTab.setContent((AnchorPane)loader.load());
                ChatRoomController chatRoomCtrl = loader.getController();
                
                chatRoomCtrl.getSendMsgImgBtn().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Message msg = new Message();
                            String chatRoomName = chatRoomsTabbedPane.getSelectionModel().getSelectedItem().getText();
                            ChatRoom chatRoom = ((ClientImplementation)client).getChatRoomControllers().get(chatRoomName)
                                    .getmChatRoom();
                            
                            msg.setSender(client.getUser());
                            msg.setReceiver(chatRoom);
                            msg.setMessageContent(chatRoomCtrl.getMsgTxtField().getText());
                            server.forWardMessage(msg);
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                
                ChatRoom chatRoom = chatRoomCtrl.getmChatRoom();
                chatRoom.setChatRoomName(chatRoomName);
                chatRoom.getUsers().add(client.getUser());
                chatRoom.getUsers().add(user);
                chatRoomCtrl.setmChatRoom(chatRoom);
                chatRoomControllers.put(chatRoomName, chatRoomCtrl);
                
                returnedChatRoomCtrl = chatRoomCtrl;
        
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            chatRoomsTabbedPane.getTabs().add(chatTab);
            if (isOpened)
                chatRoomsTabbedPane.getSelectionModel().select(chatTab);
        } else {
            for (Tab t : chatRoomsTabbedPane.getTabs()) {
                if (t.getText().equals(chatRoomName)) {
                    chatRoomsTabbedPane.getSelectionModel().select(t);
                }
            }
        }
        
        return returnedChatRoomCtrl;
    }
    
    @Override
    public void onCellDoubleClickedAction(User user) {
        String chatRoomName = user.getFirstName() + " " + user.getLastName();
        openChatRoom(chatRoomName, user, true);
    }
    
    public void showReceivedMessage(Message message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                long senderId = 0;
                try {
                    senderId = client.getUser().getUserId();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
                
                ChatRoomController chatRoomCtrl;
                if (message.getSender().getUserId() != senderId) { // incoming message
                    Tab mTab = null;
                    String receivedChatRoomName = message.getSender().getFirstName() + " " + message.getSender().getLastName();

                    for (Tab tab : chatRoomsTabbedPane.getTabs()) {
                        if (tab.getText().equals(receivedChatRoomName)) {
                            mTab = tab;
                        }
                    }
                    
                    if (mTab != null) {
                        chatRoomCtrl = ((ClientImplementation)client).getChatRoomControllers().get(receivedChatRoomName);
                    } else {
                        chatRoomCtrl = openChatRoom(receivedChatRoomName, message.getSender(), false);
                    }
                } else { // outcoming message
                    chatRoomCtrl = 
                            ((ClientImplementation)client).getChatRoomControllers().get(message.getReceiver().getChatRoomName());

                }
                chatRoomCtrl.getTestLabel().setText(message.getSender().getFirstName() + " " 
                                + message.getSender().getLastName() + " : "
                                + message.getMessageContent());
                chatRoomCtrl.getMsgTxtField().clear();
            }
        });
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
