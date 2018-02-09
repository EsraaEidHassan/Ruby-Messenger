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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader; 
import common.ServerInterface;
import model.User;

//khaled end
public class FrontController implements Initializable {
    // abdelfata7 start
    @FXML
    private Label noAccount;
    
    @FXML
    private TextField username;
    

    @FXML
    private TextField password;
    
    @FXML
    private AnchorPane mainAnchorPane;
    

    // abdelfata7 end
    
    // khaled start
    private FXMLLoader loader;
    private Stage mainStage;
    private Scene scene;
    private Parent root;
    private ServerInterface serverRef;
    //khaled end

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // abdelfata7 start
        noAccount.getStyleClass().add("label");
        username.getStyleClass().add("username");
        mainAnchorPane.getStyleClass().add("mainAnchorPane");
        password.getStyleClass().add("password");

    
        // abdelfata7 end
        
        // khaled start
        loader = new FXMLLoader();
        //khaled end
    }
    
    // abdelfata7 start
    
    
    // abdelfata7 end
    
    // khaled start
    @FXML
    public void signInAction(){
        String userName = this.username.getText();
        String password = this.password.getText();
        if(userName.trim().equals("") || password.trim().equals("") ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("login error");
            alert.setContentText("you must type your username and password to sign in");
            alert.showAndWait();
        }
        else{
            try {
                Registry reg = LocateRegistry.getRegistry(2000);
                serverRef = (ServerInterface) reg.lookup("chat"); 
                User user = serverRef.signInUser(userName, password);
                if(user != null){
                    ClientImplementation clientImpl = new ClientImplementation();
                    clientImpl.setUser(user);
                    //send client object to contacts scene controller
                    /*change scene to main scene of contacts*/
                     //root = loader.load(getClass().getResource("/fxml/UserMainScene.fxml").openStream());
                    mainStage =(Stage) this.username.getScene().getWindow();
                    scene = new Scene(root);
                    mainStage.setScene(scene);
                }
            } 
            catch (RemoteException | NotBoundException ex) {
                showServerError();
            }
            
        }
    }
    @FXML
    public void signUpAction(){
        try {
            /*change scene to sign-up scene*/
            root = loader.load(getClass().getResource("/fxml/signup.fxml").openStream());
            scene = new Scene(root);
            mainStage =(Stage) this.username.getScene().getWindow();
            mainStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
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
