/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
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
import java.io.Serializable;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.ChatRoom;

/**
 *
 * @author Ahmed
 */
public class ChatRoomController implements Initializable, Serializable {
    
    @FXML
    private JFXButton sendMsgImgBtn;
    @FXML
    private TextField msgTxtField;
    @FXML
    private Label testLabel;
    private ChatRoom mChatRoom = new ChatRoom();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public JFXButton getSendMsgImgBtn() {
        return sendMsgImgBtn;
    }

    public void setSendMsgImgBtn(JFXButton sendMsgImgBtn) {
        this.sendMsgImgBtn = sendMsgImgBtn;
    }

    public TextField getMsgTxtField() {
        return msgTxtField;
    }

    public void setMsgTxtField(TextField msgTxtField) {
        this.msgTxtField = msgTxtField;
    }

    public Label getTestLabel() {
        return testLabel;
    }

    public void setTestLabel(Label testLabel) {
        this.testLabel = testLabel;
    }

    public ChatRoom getmChatRoom() {
        return mChatRoom;
    }

    public void setmChatRoom(ChatRoom mChatRoom) {
        this.mChatRoom = mChatRoom;
    }
    
}
