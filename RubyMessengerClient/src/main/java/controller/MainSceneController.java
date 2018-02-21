package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import model.FriendsList;
import model.User;
import view.FriendsListCellFactory;
import common.ClientInterface;
import common.ServerInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import model.ChatRoom;
import model.ClientImplementation;
import model.FriendshipRequest;
import model.Message;
import model.RequestsList;
import model.ServerImplementation;
import model.XMLCreator;
import org.controlsfx.control.Notifications;
import view.FriendsListCallback;
import view.RequestsListCellFactory;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class MainSceneController implements Initializable, FriendsListCallback {

    private ServerInterface server;
    private ClientInterface client;
    private boolean fileAccept , checked;
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
    private JFXButton logoutImgBtn;
    @FXML
    private ImageView userImg;
    @FXML
    private Label profileUsernameLabel;
    @FXML
    private ImageView statusIcon;
    @FXML
    private JFXComboBox<String> modeOptionsCB;
    @FXML
    private FriendshipRequestController friendshipRequestRootController;
    private JFXListView mRequestsListVw;
    
    private long lastSenderId;
    private GridPane chatterMsgsPane;
    private ImageView chatterImgVw;

    // Esraa Hassan start
    HashMap<String, List<Message>> savedChats = new HashMap<String, List<Message>>();
    // Esraa Hassan end
    /*
    @FXML
    private TextField usernameOrEmailField;
    @FXML
    private Button sendRequestBtn;
     */
    
    // Ahmed 
    Parent root;
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
            
            modeOptionsCB.getItems().addAll("available", "busy", "away");
            modeOptionsCB.setValue(client.getUser().getUserMode());
            statusIcon.setImage(new Image("/" + modeOptionsCB.getValue() + ".png"));
            modeOptionsCB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        User mUser = client.getUser();
                        mUser.setUserMode(modeOptionsCB.getValue());
                        server.notifyMyFriendsChangingMode(mUser);
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    statusIcon.setImage(new Image("/" + modeOptionsCB.getValue() + ".png"));
                }
            });
            
            userNameLabel = profileRootController.getUserNameLabel();
            userNameLabel.setText(client.getUser().getFirstName() + " " + client.getUser().getLastName());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        
        mFriendsLVw = friendsRootController.getFriendsListView();
        mRequestsListVw = friendshipRequestRootController.getFriendRequestsListVw();
        populateFriendsList();
        populateRequestsList();
        
        friendshipRequestRootController.getSendFriendRequestBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String friend = friendshipRequestRootController.getFriendRequestUserTxt().getText();
                    if (client.checkFriendUserExistence(friend) != null) {
                        client.sendFriendRequest(friend);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Friend request has been sent successfully");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("");
                        alert.setHeaderText(null);
                        alert.setContentText("User not found !!");
                        alert.showAndWait();
                    }
                } catch (RemoteException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("request error");
                    alert.setContentText("server is down now");
                    alert.showAndWait();
                }
            }
        });
        
                /*
        menuTabbedPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                // when change the selected tab
            }
        });
        */
    }

    public void renderAnnouncement(String message) {
        
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
    public void recievNotificationFromOnlineFriend(User user){
        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setContentText("Your Friend " + username + " is now online");
        alert.showAndWait();*/
        System.out.println("This is user: "+this.username+" , receiving notification from user "+user.getUsername());
        Notifications notificationBuilder = Notifications.create()
                .title("Notification")
                .text("Your Friend " + user.getFirstName() + " " + user.getLastName() + " is now online")
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
        
        ObservableList<User> myFriendFriendsList = mFriendsLVw.getItems();
        for (int i = 0; i < myFriendFriendsList.size(); i++) {
            User u = myFriendFriendsList.get(i);
            if (user.getUserId() == u.getUserId()) {
                u.setUserStatus(user.getUserStatus());
            }
        }
        mFriendsLVw.refresh();
    }
    // Esraa Hassan end

    // Mahmoud Marzouk
    public void populateFriendsList() {
        try {
            // Esraa Hassan start
            // this code is to send notification to all friends of this user that this user is noe online
            ArrayList<User> friendsList = new FriendsList(client.getUser()).getFriends();
            ArrayList<ClientInterface> clients_friendsList = server.getOnlineClientsFromUserObjects(friendsList);
            server.sendNotificationToOnlineFriends(client.getUser(), clients_friendsList);
            // Esraa Hassan end
            ObservableList<User> friends = FXCollections.observableArrayList(friendsList);
            FriendsListCellFactory friendsListFactory = new FriendsListCellFactory();
            friendsListFactory.setController(MainSceneController.this);
            mFriendsLVw.setCellFactory(friendsListFactory);
            mFriendsLVw.setItems(friends);
       } catch (RemoteException ex) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("service error");
           alert.setContentText("server is down now");
           alert.showAndWait();
       }
    }
    
    public void populateRequestsList() {
        try {
             ObservableList<FriendshipRequest> incomingRequests = 
                     FXCollections.observableArrayList(new RequestsList(client.getUser()).getRequests());
             RequestsListCellFactory requestsListFactory = new RequestsListCellFactory();
             requestsListFactory.setController(MainSceneController.this);
             mRequestsListVw.setCellFactory(requestsListFactory);
             mRequestsListVw.setItems(incomingRequests);
        } catch (RemoteException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("service error");
            alert.setContentText("server is down now");
            alert.showAndWait();
        }
    }
    
    private ChatRoomController openChatRoom(String chatRoomid, User user, boolean isFocused) {
        ChatRoomController returnedChatRoomCtrl = null;
        HashMap<String, ChatRoomController> chatRoomControllers = ((ClientImplementation)client).getChatRoomControllers();
        if (chatRoomControllers.get(chatRoomid) == null) {
            Tab chatTab = null;
            
            chatTab = new Tab(user.getFirstName() + " " + user.getLastName());
                
            chatTab.setId(chatRoomid);
            
            chatTab.setOnCloseRequest(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    String chatRoomid = ((Tab)event.getSource()).getId();
                    ((ClientImplementation)client).getChatRoomControllers().remove(chatRoomid);
                }
            });
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChatRoom.fxml"));
                chatTab.setContent((AnchorPane)loader.load());
                ChatRoomController chatRoomCtrl = loader.getController();
                
                //khaled start
                //send file action
                chatRoomCtrl.getAttachFileImgBtn().setOnMouseClicked((event) -> {
                    String roomid = chatRoomsTabbedPane.getSelectionModel().getSelectedItem().getId();
                    ChatRoom chatRoom = ((ClientImplementation)client).getChatRoomControllers().get(roomid)
                                        .getmChatRoom();
                    boolean isFileSent = chatRoom.isFileSent();
                    if(isFileSent){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("attach file");
                        alert.setHeaderText("you can't send another file");
                        alert.setContentText("there is already a file sent now");
                        alert.show();
                    }
                    else{
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Choose File");
                        File file = fileChooser.showOpenDialog(mStage);
                        if (file != null) {
                            ArrayList<User> roomUsers = chatRoom.getUsers();
                            try{
                            long senderId = client.getUser().getUserId();
                            String senderName = client.getUser().getFirstName()+" "+client.getUser().getLastName();
                            for(User chatter : roomUsers){
                                if(chatter.getUserId() != senderId && chatter.getUserStatus().toLowerCase().equals("online")){
                                    boolean accepted = server.askUsersSendFile(senderName , chatter.getUserId() , file.getName());
                                    if(accepted){
                                        Notifications notificationBuilder = Notifications.create()
                                                    .title("Notification")
                                                    .text(chatter.getUsername()+" accepted your file , it's being sent now")
                                                    .graphic(null)
                                                    .hideAfter(Duration.seconds(5))
                                                    .position(Pos.BOTTOM_RIGHT);
                                                notificationBuilder.darkStyle();
                                                Platform.runLater(() -> {
                                                    notificationBuilder.show();
                                                });
                                        chatRoom.setIsFileSent(true);
                                        Thread t = new Thread(() -> {
                                            try{
                                                FileInputStream in = new FileInputStream(file);
                                                byte[] mydata = new byte[1024 * 1024];
                                                int length = in.read(mydata);
                                                while (length > 0) {
                                                    server.sendFile(mydata , file.getName() , length ,chatter.getUserId());
                                                    length = in.read(mydata);
                                                }
                                            }
                                            catch(FileNotFoundException ex){
                                                Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            catch(IOException ex){
                                                Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                                /////////notify file sent//////////
                                                Notifications notificationBuilder2 = Notifications.create()
                                                    .title("Notification")
                                                    .text("file sent successfully to " + chatter.getUsername())
                                                    .graphic(null)
                                                    .hideAfter(Duration.seconds(5))
                                                    .position(Pos.BOTTOM_RIGHT);
                                                notificationBuilder.darkStyle();
                                                Platform.runLater(() -> {
                                                    notificationBuilder.show();
                                                });
                                                chatRoom.setIsFileSent(false);
                                            try {
                                                server.notifyFileSendDone(chatter.getUserId() , senderName);
                                                ///////////////
                                            } catch (RemoteException ex) {
                                                Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        });
                                        t.start();
                                        
                                    }
                                    else{
                                        Notifications notificationBuilder = Notifications.create()
                                            .title("Notification")
                                            .text( chatter.getUsername()+" refused to receive file from you")
                                            .graphic(null)
                                            .hideAfter(Duration.seconds(5))
                                            .position(Pos.BOTTOM_RIGHT);
                                        notificationBuilder.darkStyle();
                                        notificationBuilder.show();
                                    }
                                }

                            }
                            } catch (RemoteException ex) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("service error");
                                alert.setContentText("server is down now");
                                alert.showAndWait();
                            }catch (IOException ex) {
                                Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                });
                
                //khaled end
                chatRoomCtrl.getSendMsgImgBtn().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            if (!chatRoomCtrl.getMsgTxtField().getText().isEmpty()) {
                                Message msg = new Message();
                                String chatRoomid = chatRoomsTabbedPane.getSelectionModel().getSelectedItem().getId();
                                ChatRoom chatRoom = ((ClientImplementation)client).getChatRoomControllers().get(chatRoomid)
                                        .getmChatRoom();
                                
                                msg.setSender(client.getUser());
                                msg.setReceiver(chatRoom);
                                msg.setMessageContent(chatRoomCtrl.getMsgTxtField().getText());
                                // Esraa Hassan start
                                msg.setColor(chatRoomCtrl.getColorPicked());
                                msg.setFontSize(chatRoomCtrl.getSizePicked());
                                msg.setFontWeight(chatRoomCtrl.getFontWeight()); // to be changed
                                msg.setFontStyle(chatRoomCtrl.getFontPosture()); // to be changed
                                //Esraa Hassan end
                                // set font
                                server.forWardMessage(msg);
                            }
                        }catch (RemoteException ex) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("service error");
                            alert.setContentText("server is down now");
                            alert.showAndWait();
                        }
                    }
                });
                
                // Esraa Hassan start
                chatRoomCtrl.getSaveChatImgBtn().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("save chat ...");
                        /*
                        Iterator it = savedChats.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry)it.next();
                            List<Message> currentSavedMsgs = (List<Message>) pair.getValue();
                            System.out.println("chat room id = "+pair.getKey());
                            for(int i =0 ;i<currentSavedMsgs.size();i++)
                                System.out.println("sender: "+currentSavedMsgs.get(i).getSender().getFirstName()+" "+currentSavedMsgs.get(i).getSender().getLastName()+" msg: "+currentSavedMsgs.get(i).getMessageContent());
                            it.remove(); // avoids a ConcurrentModificationException
                        }*/
                        System.out.println("chat room id "+chatRoomid);
                        //System.out.println(savedChats.get(chatRoomid).s);
                        // Esraa Hassan start 2
                        XMLCreator xmlCreator = new XMLCreator();
                        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                        xmlCreator.writeXmlChat(savedChats.get(chatRoomid), username, fileName+ "output.xml");
                      
                        try {
                            System.out.println("Adding style sheet");
                            xmlCreator.addingStylesheet(fileName+ "output.xml");
                            System.out.println("Style sheet added");
                            //System.out.println("Validating schema ... ");
                            //xmlCreator.validateSchema(fileName+ "output.xml");
                            //System.out.println("Valid XML against schema");
                        } catch (TransformerConfigurationException ex) {
                            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Cannot add style sheet");
                        } catch (ParserConfigurationException ex) {
                            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Cannot add style sheet");
                        }
                        // Esraa Hassan end 2
                    }
                });
                // Esraa Hassan end
                
                chatRoomCtrl.getChatReceiversTxtLabel().setText(user.getFirstName() + " " + user.getLastName());
                
                ChatRoom chR = chatRoomCtrl.getmChatRoom();          
                chR.setChatRoomId(chatRoomid);
                chR.getUsers().add(client.getUser());
                chR.getUsers().add(user);
                chatRoomCtrl.setmChatRoom(chR);
                chatRoomControllers.put(chatRoomid, chatRoomCtrl);
                returnedChatRoomCtrl = chatRoomCtrl;
                
            } catch (RemoteException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("service error");
                alert.setContentText("server is down now");
                alert.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            chatRoomsTabbedPane.getTabs().add(chatTab);
            if (isFocused)
                chatRoomsTabbedPane.getSelectionModel().select(chatTab);
            
        } else {
            
            for (Tab t : chatRoomsTabbedPane.getTabs()) {
                if (t.getId().equals(chatRoomid)) {
                    chatRoomsTabbedPane.getSelectionModel().select(t);
                }
            }
            
        }
        
        return returnedChatRoomCtrl;
    }
    public void showFileSentNotification(String senderName){
        Notifications notificationBuilder = Notifications.create()
            .title("Notification")
            .text("file received successfully from " + senderName +" at public downloads")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        Platform.runLater(() -> {
            notificationBuilder.show();
        });
    }
    public boolean showFileRequestAlert(String senderName ,String fileName){
        
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("download confirmation");
            alert.setHeaderText(senderName+" wants to send you a file "+fileName);
            alert.setContentText("Do you agree?");

            ButtonType yesBtn = new ButtonType("Accept");
            ButtonType noBtn = new ButtonType("Decline");

            alert.getButtonTypes().setAll(yesBtn, noBtn);
                Optional<ButtonType> result = alert.showAndWait();
                 if (result.get() == yesBtn)
                     fileAccept = true;
                 else
                     fileAccept = false;
                 checked = true;
        });
        while(!checked){
            System.out.println("waiting");
        }
        checked = false;
        if(fileAccept)
            return true;
        else
            return false;
    }
    @Override
    public void onCellDoubleClickedAction(User user) {
        String chatRoomid = "u" + user.getUserId();
        // Esraa Hassan start
        savedChats.put(chatRoomid,new ArrayList<Message>());
        // Esraa Hassan end
        
        openChatRoom(chatRoomid, user, true);
    }
    
    public void showReceivedMessage(Message message) { // to do
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                long mClientUserId = 0;
                try {
                    mClientUserId = client.getUser().getUserId();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
                long senderId = message.getSender().getUserId();
                ChatRoomController chatRoomCtrl = null;
                
                /* for message drawing ***********************************/
                Pos position = null;
                Color bkgColor = null;
                Color fontColor = null;
                /********************************************************/
                
                if (senderId != mClientUserId) { // incoming message
                    Tab mTab = null;
                    String receivedChatRoomId = "u" + senderId;
                    
                    for (Tab tab : chatRoomsTabbedPane.getTabs()) {
                        if (tab.getId().equals(receivedChatRoomId)) {
                            mTab = tab;
                        }
                    }
                    if (mTab != null) {
                        chatRoomCtrl = ((ClientImplementation)client).getChatRoomControllers().get(receivedChatRoomId);
                    } else {
                        chatRoomCtrl = openChatRoom(receivedChatRoomId, message.getSender(), false);
                    }
                    
                    // draw message
                    position = Pos.CENTER_LEFT;
                    bkgColor = Color.rgb(237, 241, 248);
                    
                    Label msgTxt = new Label(message.getMessageContent());
                    msgTxt.setWrapText(true);
                    //msgTxt.getStyleClass().add("chatBubble");
                    msgTxt.setPadding(new Insets(10));
                    msgTxt.setFont(new Font("Arial", message.getFontSize()));
                    msgTxt.setTextFill(Color.valueOf(message.getColor()));
                    msgTxt.setBackground(new Background(new BackgroundFill(bkgColor,
                            new CornerRadii(20),
                            new Insets(2, -2, 2, -2))));
                    StackPane stackPane = new StackPane(msgTxt);
                    stackPane.setPrefWidth(360);
                    stackPane.setAlignment(position);
                    
                    if (lastSenderId == message.getSender().getUserId()) { // next messages from the same user
                        chatterMsgsPane.getRowConstraints().add(new RowConstraints());
                        GridPane.setRowIndex(chatterImgVw, chatterMsgsPane.getRowConstraints().size() - 1);
                        chatterMsgsPane.add(stackPane, 1, 
                                chatterMsgsPane.getRowConstraints().size() - 1);
                    } else { // first message from the same user
                        chatterMsgsPane = new GridPane(); 
                        chatterMsgsPane.getColumnConstraints().addAll(new ColumnConstraints(41), new ColumnConstraints());
                        
                        chatterImgVw = new ImageView("/user.png");
                        chatterImgVw.setFitHeight(35.0);
                        chatterImgVw.setFitWidth(35.0);
                        chatterImgVw.setClip(new Circle(16, 16, 16)); 
                        
                        chatterMsgsPane.getRowConstraints().add(new RowConstraints());
                        Label senderName = new Label(message.getSender().getFirstName() 
                                    + " " + message.getSender().getLastName());
                        chatterMsgsPane.add(senderName, 1, 0);
                        chatterMsgsPane.getRowConstraints().add(new RowConstraints());
                        chatterMsgsPane.add(chatterImgVw, 0, 
                                chatterMsgsPane.getRowConstraints().size() - 1);
                        chatterMsgsPane.add(stackPane, 1, 
                                chatterMsgsPane.getRowConstraints().size() - 1);

                        VBox.setMargin(chatterMsgsPane, new Insets(8, 0, 8, 0));
                        chatRoomCtrl.getShowMsgsBox().getChildren().add(chatterMsgsPane);
                    }
                    
                } else { // outcoming message
                    chatRoomCtrl = 
                            ((ClientImplementation)client).getChatRoomControllers().get(message.getReceiver().getChatRoomId());
                    
                    position = Pos.CENTER_RIGHT;
                    bkgColor = Color.rgb(91, 132, 215);
                    
                    Label msgTxt = new Label(message.getMessageContent());
                    msgTxt.setWrapText(true);
                    //msgTxt.getStyleClass().add("chatBubble");
                    msgTxt.setPadding(new Insets(10));
                    msgTxt.setFont(new Font("Arial", message.getFontSize()));
                    msgTxt.setTextFill(Color.valueOf(message.getColor()));
                    msgTxt.setBackground(new Background(new BackgroundFill(bkgColor,
                            new CornerRadii(20),
                            new Insets(2, -2, 2, -2))));
                    StackPane stackPane = new StackPane(msgTxt);
                    stackPane.setPrefWidth(360);
                    stackPane.setAlignment(position);
                    
                    chatRoomCtrl.getShowMsgsBox().getChildren().add(stackPane);
                }
                
                lastSenderId = message.getSender().getUserId();
                
                chatRoomCtrl.getMsgTxtField().clear();
                
                // Esraa Hassan start
                String thisChatRoomID = chatRoomCtrl.getmChatRoom().getChatRoomId();
                List<Message> currentMsgs = savedChats.get(thisChatRoomID);
                if(currentMsgs == null){
                    currentMsgs = new ArrayList<Message>();
                }
                currentMsgs.add(message);
                savedChats.put(thisChatRoomID, currentMsgs);
                // Esraa Hassan end
                
            }
        });
    }
    
    public void notifyNewFriendRequest(User u) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // System.out.println("you have a friendship request from " + u.getUsername());
                Notifications notificationBuilder = Notifications.create()
                    .title("New friendship request")
                     .text(u.getFirstName() + " " + u.getLastName() + " has sent you a friendship request")
                     .graphic(null)
                     .hideAfter(Duration.seconds(5))
                     .position(Pos.BOTTOM_RIGHT)
                     .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // to do action here
                        }
                    });
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                
                populateRequestsList();
            }
        });
    }
    
    public void updateMyFriendModeInFriendsList(User user) {
        ObservableList<User> myFriendFriendsList = mFriendsLVw.getItems();
        for (int i = 0; i < myFriendFriendsList.size(); i++) {
            User u = myFriendFriendsList.get(i);
            if (user.getUserId() == u.getUserId()) {
                u.setUserMode(user.getUserMode());
            }
        }
        mFriendsLVw.refresh();
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
    public void closeMainScene() throws RemoteException {
        server.unregister(client);
        System.exit(0);
    }
    
    @FXML
    public void minimizeMainScene() {
        mStage.setIconified(true);
    }
    
    @FXML
    public void logout(){
        try {
            server.unregister(client);
            
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/fxml/Login.fxml").openStream());
            FrontController controller = loader.<FrontController>getController();
            controller.setServer(server);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("styles/styles.css");
            mStage.close();
            Stage frontStage = new Stage(StageStyle.UNDECORATED);
            frontStage.setScene(scene);
            frontStage.show();
        
        } catch (RemoteException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("service error");
            alert.setContentText("server is down now");
            alert.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
