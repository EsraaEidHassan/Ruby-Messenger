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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import model.ChatRoom;

/**
 *
 * @author Ahmed
 */
public class ChatRoomController implements Initializable {
    
    private ServerInterface server;
    private ClientInterface client;
    private ArrayList<User> users;

    
    @FXML
    private TextArea messageTextArea;
    
    @FXML
    Button sendMessage;
    
    String mess;
    
    ChatRoom chatRoom;
    
    
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
    public void initialize(URL location, ResourceBundle resources) {
        mess = messageTextArea.getText();
    }
    
    public void setChatRoom(ChatRoom chat){
        chatRoom = chat;
    }
    
    public void sendMessage(){
        try {
            ArrayList<ClientInterface> roomClients = new ArrayList<>();
            roomClients = server.getOnlineClientsFromUserObjects(chatRoom.getRoomUsers());
            Message message = new Message();
            message.setMessageContent(mess);
            message.setSender(client.getUser());
            // dummy user as first one in the list
            message.setReceivers(chatRoom.getRoomUsers());
            
            for (int i = 0; i < roomClients.size(); i++) {
                roomClients.get(i).receive(message);
                
            }
            
            
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ChatRoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    } 
    
    
}
