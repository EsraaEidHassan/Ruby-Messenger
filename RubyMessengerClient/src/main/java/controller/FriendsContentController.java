/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXListView;
import common.ClientInterface;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.FriendsList;
import model.User;
import view.FriendsListCallback;
import view.FriendsListCellFactory;

/**
 *
 * @author Mahmoud.Marzouk
 */
public class FriendsContentController implements Initializable {
    
    @FXML
    private JFXListView friendsListView;

    public JFXListView getFriendsListView() {
        return friendsListView;
    }
    
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
