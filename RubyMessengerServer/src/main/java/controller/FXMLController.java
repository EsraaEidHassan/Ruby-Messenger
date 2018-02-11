package controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Server;
import model.ServerImplementation;

public class FXMLController implements Initializable {
    
    //Esraa Hassan
    @FXML
    private Button startServer;
    @FXML
    private Button stopServer;
    @FXML
    private Button sendAnnouncementButton;
    @FXML
    private Button onlineAndOfflineUsersButton;
    @FXML
    private Button genderStatisticsButton;
    @FXML
    private Label btnClose;
    @FXML
    private Label btnMinimize;
    @FXML
    private AnchorPane topbar;
    
    Server server;
    Stage primaryStage;
    
    //---------This for moving the stage freely :)-------------------------//
    //---------------------------------------------------------------------//
    private double startMoveX = -1, startMoveY = -1;
    private Boolean dragging = false;
    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;
    /*@FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Esraa Hassan
        server = new Server();
        
        startServer.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println("starting ...");
                server.startServer();
                startServer.setDisable(true);
                stopServer.setDisable(false);
                sendAnnouncementButton.setDisable(false);
                onlineAndOfflineUsersButton.setDisable(false);
                genderStatisticsButton.setDisable(false);
            }
            
        });
        stopServer.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println("stoping ...");
                server.stopServer();
                startServer.setDisable(false);
                stopServer.setDisable(true);
                sendAnnouncementButton.setDisable(true);
                onlineAndOfflineUsersButton.setDisable(true);
                genderStatisticsButton.setDisable(true);
            }
            
        });
        sendAnnouncementButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("sending announcement ...");
                    
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Announce");
                    dialog.setHeaderText("Enter your message:");
                    dialog.setContentText("message : ");

                    Optional<String> result = dialog.showAndWait();

                    if(result.isPresent()){
                        ServerImplementation serverImpl = server.getServerImpl();
                        serverImpl.sendAnnouncement(result.get());
                    }
                    else{
                        //do nothing
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        onlineAndOfflineUsersButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Showing number of online and offline users .... ");
                    ServerImplementation serverImpl = server.getServerImpl();
                    int[] online_offline_array = serverImpl.getOnlineAndOfflineUsers();
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Online And Offline Users");
                    alert.setContentText("There are "+online_offline_array[0]+" online users, and "
                            +online_offline_array[1] +" offline users .");
                    alert.showAndWait();
                } catch (RemoteException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        genderStatisticsButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                showBirthdayStatistics();
            }
        });
        
        btnClose.setOnMouseClicked((MouseEvent event) -> {
            System.out.println("Exit Server");
            // todo : unregister all users
            Platform.exit();
            System.exit(0);
        });
        
        btnMinimize.setOnMouseClicked((MouseEvent event) -> {

            System.out.println("minimize window");
            Stage stage = (Stage) ((Label) event.getSource()).getScene().getWindow();

            stage.setIconified(true);
        });
        
        //-----------------------Drag---------------------------//
        
       /*
        startMoveX and startMoveY are recorded at the start of a move operation and they are used 
        to compute an ending position. They are also used in a computation that moves around a Rectangle, 
        moveTrackingRect, during the operation. The Rectangle is a visual cue that will go away when the 
        operation is ended.

        dragging is a state variable used to ignore MouseEvents that are not involved in the move operation.
        moveTrackingRect is an outline showing what the move operation might look like. 
        moveTrackingPopup is the windowing container for the Rectangle.
       */
       
        topbar.setOnDragDetected((MouseEvent event) -> {
            startMoveX = event.getScreenX();
            startMoveY = event.getScreenY();
            dragging = true;

            moveTrackingRect = new Rectangle();
            moveTrackingRect.setWidth(topbar.getWidth());
            moveTrackingRect.setHeight(topbar.getHeight());

            moveTrackingPopup = new Popup();
            moveTrackingPopup.getContent().add(moveTrackingRect);
            moveTrackingPopup.show(topbar.getScene().getWindow());
            moveTrackingPopup.setOnHidden((e) -> resetMoveOperation());
        });

        topbar.setOnMouseDragged((MouseEvent event) -> {

            if (dragging) {

                double endMoveX = event.getScreenX();
                double endMoveY = event.getScreenY();

                Window w = topbar.getScene().getWindow();

                double stageX = w.getX();
                double stageY = w.getY();

                moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
                moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
            }
        });

        topbar.setOnMouseReleased((MouseEvent event) -> {

            if (dragging) {
                double endMoveX = event.getScreenX();
                double endMoveY = event.getScreenY();

                Window w = topbar.getScene().getWindow();

                double stageX = w.getX();
                double stageY = w.getY();

                w.setX(stageX + (endMoveX - startMoveX));
                w.setY(stageY + (endMoveY - startMoveY));

                if (moveTrackingPopup != null) {
                    moveTrackingPopup.hide();
                    moveTrackingPopup = null;
                }
            }

            resetMoveOperation();

        });
    }
    
    private void resetMoveOperation() {
        startMoveX = 0;
        startMoveY = 0;
        dragging = false;
        moveTrackingRect = null;
    }

    public void setStage(Stage stage){
        this.primaryStage = stage;
    }
    
    public void showBirthdayStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/GenderStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            GenderStatisticsController controller = loader.getController();
            controller.setGenderData(server.getServerImpl());

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
