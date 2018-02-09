package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author toshiba
 */
public class SignupController implements Initializable {

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
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryLabel.getStyleClass().add("label");
        username.getStyleClass().add("username");
        male.getStyleClass().add("button");
        female.getStyleClass().add("button");
        //password.getStyleClass().add("textField");
        //email.getStyleClass().add("button");
        //fname.getStyleClass().add("button");
        //lname.getStyleClass().add("button");
        signupButton.getStyleClass().add("button");
    }    
    
}
