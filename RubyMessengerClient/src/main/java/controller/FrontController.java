package controller;
// abdelfata7 start
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

 // abdelfata7 end

// khaled start

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader; 
import model.ServerInterface;

//khaled end
public class FrontController implements Initializable {
    // abdelfata7 start
    @FXML
    private Label noAccount;
    
    @FXML
    TextField username;
    
    // abdelfata7 end
    
    // khaled start
    TextField userNameField; //temp
    TextField passwordField; //temp
    ServerInterface serverRef;
    //khaled end
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // abdelfata7 start
        noAccount.getStyleClass().add("label");
        username.getStyleClass().add("username");
    
        // abdelfata7 end
        
        // khaled start
        
        try {
            Registry reg = LocateRegistry.getRegistry(2000);
            serverRef = (ServerInterface) reg.lookup("chat");                        
        } 
        catch (RemoteException | NotBoundException ex) {
            showServerError();
        }
    
        //khaled end
    }
    
    // abdelfata7 start
    
    
    // abdelfata7 end
    
    // khaled start
    public void signInAction(){
        String userName = userNameField.getText();
        String password = passwordField.getText();
        if(userName.trim().equals("") || password.trim().equals("") ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("login error");
            alert.setContentText("you must type your username and password to sign in");
            alert.showAndWait();
        }
        else{
            
            if(true){ //suppose that signIn returned a user 
                Stage mainStage =(Stage) userNameField.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                Parent root=null;
                 //root = loader.load(getClass().getResource("UserMainScene.fxml").openStream());
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
            }
        }
    }
    public static void showServerError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("server error");
        alert.setContentText("server not found! , try later");
        alert.showAndWait();
    }
    //khaled end
}
