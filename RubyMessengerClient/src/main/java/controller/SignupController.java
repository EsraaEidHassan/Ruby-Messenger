package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import common.ServerInterface;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
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
    @FXML
    private ToggleGroup gender;
    private ServerInterface server = null;
    private Country userCountry;
    
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
        
        // Esraa Hassan start
        userCountry = new Country();
        countryComboBox.getItems().clear();
        // Esraa Hassan end
        
        // Abd Alfattah (Start)
        
        

//        try {
//            registry = LocateRegistry.getRegistry(2000);
//            server = (ServerInterface) registry.lookup("chat");
//        } catch (RemoteException | NotBoundException ex) {
//            System.out.println("error");
//        }

        // Abd Alfattah (End)
        
        
        
        
        
        
        
        
        
        
        
        
        // Abd Alfattah (End)
        
    }    
    
    
    
    // Abd Alfattah (Start)
     
    // Method to collect user info and send it to server
    @FXML
    public void sendDataSignUp(){
        String uName = username.getText();
        String fName = fname.getText();
        String lName = lname.getText();
        String pass = password.getText();
        //String gend = gender.getSelectedToggle().getUserData().toString(); //this makes null pointer exception
        String gend = "Male";
        String em = email.getText();
        
        // dummy country object ( needs fix) // Esraa Hassan : Fixed 
        //Country count = new Country(); // commented by Esraa Hassan 
        boolean signUpStatus = false;
        if(uName.trim().equals("")||fName.trim().equals("")||lName.trim().equals("")||pass.trim().equals("")
                ||gend.trim().equals("")||em.trim().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("login error");
            alert.setContentText("you must fill all data");
            alert.showAndWait();
        }
        else{
            User userReg = new User(uName, pass, em, fName, lName, gend, userCountry);
            try {
                signUpStatus = server.signup_user(userReg);
            } catch (RemoteException ex) {
                Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(signUpStatus){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("registration");
                alert.setContentText("you were signed-up successfully");
                alert.showAndWait();
                goToLoginPage();
            }
            else{
                System.out.println("Sign Up failed");
            }
        }
    }
    @FXML
    public void backAction(){
        goToLoginPage();
    }
    
    
    private void goToLoginPage(){
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(getClass().getResource("/fxml/Scene.fxml").openStream());
            Stage mainStage =(Stage) this.username.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            mainStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void setServer(ServerInterface server){
        this.server = server;
    }
    
    // Esraa Hassan start
    public void populateCountriesInComboBox(){
        
        try {
            List<Country> countries = server.retrieveAllCountries();
            countryComboBox.setVisibleRowCount(7);
            
            countryComboBox.setConverter(new StringConverter<Country>() {
                @Override
                public String toString(Country object) {
                    return object.getCountryName();
                }

                @Override
                public Country fromString(String string) {
                    return null;
                }
            });

            countryComboBox.setItems(FXCollections.observableArrayList(countries));
            
            countryComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                Country current = (Country) newVal;
                String selectionText = "Country name: " + current.getCountryName() + " ID is : " + current.getCountryId();
                System.out.println(selectionText);
                
                userCountry = current;
                }
            );
            
            countryComboBox.setValue(userCountry);

        } catch (RemoteException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
            countryComboBox.getItems().addAll("Egypt","KSA");
            countryComboBox.setValue("Egypt");
        }
        
    }
    // Esraa Hassan end
    
    // Abd Alfattah (End)
}
