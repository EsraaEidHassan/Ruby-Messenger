package controller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
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
    
    Server server;
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
        
    }    
}
