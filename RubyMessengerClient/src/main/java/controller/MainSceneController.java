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

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class MainSceneController implements Initializable {

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
}
