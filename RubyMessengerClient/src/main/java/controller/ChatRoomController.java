/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import model.Message;
import model.User;
import common.ClientInterface;
import common.ServerInterface;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.ChatRoom;

/**
 *
 * @author Ahmed
 */
public class ChatRoomController implements Initializable {
    
    private ServerInterface server;
    private ClientInterface client;

    
    @FXML
    private TextArea messageTextArea;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    public void sendMessage(){
        
        
        
    }   
    
}
