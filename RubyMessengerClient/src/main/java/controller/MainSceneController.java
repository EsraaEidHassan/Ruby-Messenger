/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import common.ClientInterface;
import common.ServerInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

    // Ahmed Start
import model.Message;
    // Ahmed End
/**
 * FXML Controller class
 *
 * @author khaled
 */
public class MainSceneController implements Initializable {
    // Ahmed Start
    
    // Ahmed End

    private ServerInterface server;
    private ClientInterface client;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setServer(ServerInterface server){
            this.server = server;
    }
    
    public void setClient(ClientInterface client){
        this.client = client;
    }
    public void renderAnnouncement(String message){
        System.out.println("Sever rejected your connection");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Announcement from the server");
        alert.setContentText("From server : "+message);
        alert.showAndWait();
    }
    
    // Ahmed Start 
    public boolean sendMessage(String message){
        Message msg = new Message();
        msg.setMessageContent(message);
        msg.set
    }
    // Ahmed End
}
