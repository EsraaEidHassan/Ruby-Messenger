package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import common.ServerInterface;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.Country;
import model.User;

/**
 * FXML Controller class
 *
 * @author toshiba
 */
public class SignupController implements Initializable {

    // Esraa Hassan
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private Label countryLabel;
    @FXML
    private ComboBox countryComboBox;
    @FXML
    private Button signupButton;
    //@FXML
    //AnchorPane mainAnchorPane;
    
    // Abd Alfattah (Start)
    ToggleGroup gender;
    Registry registry;
    ServerInterface server = null;
    
    // Abd Alfattah (End)
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Esraa Hassan
        //mainAnchorPane.getStyleClass().add("mainAnchorPane");
        countryLabel.getStyleClass().add("label");
        username.getStyleClass().add("username");
        male.getStyleClass().add("button");
        female.getStyleClass().add("button");
        signupButton.getStyleClass().add("button");
        
        
        // Abd Alfattah (Start)
        
        
        try {
            registry = LocateRegistry.getRegistry(2000);
            server = (ServerInterface) registry.lookup("chat");
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Abd Alfattah (End)
        
        
        
        
        
        
        
        
        
        
        
        
        // Abd Alfattah (End)
        
    }    
    
    
    
    // Abd Alfattah (Start)
     
    // Method to collect user info and send it to server
    public boolean sendDataSignUp(){
        String uName = username.getText();
        String fName = fname.getText();
        String lName = lname.getText();
        String pass = password.getText();
        String gend = gender.getSelectedToggle().getUserData().toString();
        String em = email.getText();
        
        // dummy country object ( needs fix)
        Country count = new Country();
        boolean signUpStatus = false;
        User userReg = new User(uName, pass, em, fName, lName, gend, count);
        
        try {
            signUpStatus = server.signup_user(userReg);
        } catch (RemoteException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(signUpStatus == false){
            System.out.println("Sign Up failed");
            
        }
        return signUpStatus; 
    }
    
    // Abd Alfattah (End)
}
