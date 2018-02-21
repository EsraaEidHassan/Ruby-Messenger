package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import common.ClientInterface;
import common.ServerInterface;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.ClientImplementation;
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
    private AnchorPane signUpRootPane;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField fname;
    @FXML
    private JFXButton closeImgBtn;
    @FXML
    private JFXButton minimizeImgBtn;
    @FXML
    private JFXTextField lname;
    @FXML
    private JFXRadioButton male;
    @FXML
    private JFXRadioButton female;
    @FXML
    private JFXButton registerBtn;
    @FXML
    private JFXComboBox countryComboBox;
    
    //@FXML
    //AnchorPane mainAnchorPane;
    
    // Abd Alfattah (Start)
    @FXML
    private ServerInterface server = null;
    private Country userCountry;
    private Stage mStage;
    private double xOffset;
    private double yOffset;
    // Abd Alfattah (End)
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Esraa Hassan
        //mainAnchorPane.getStyleClass().add("mainAnchorPane");
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initController();
                
                // khaled start
                
                
                //khaled end
                
                registerBtn.requestFocus();
            }
        });
        
        // Esraa Hassan start
        userCountry = new Country();
        //countryComboBox.getItems().clear();
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
        String em = email.getText();
        String gend = null;
        gend=(male.isSelected()?"Male":"Female");
        
        
        // dummy country object ( needs fix) // Esraa Hassan : Fixed 
        //Country count = new Country(); // commented by Esraa Hassan 
        boolean signUpStatus = false;
        if(uName.trim().equals("")||fName.trim().equals("")||lName.trim().equals("")||pass.trim().equals("")
                ||em.trim().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("registeration error");
            alert.setContentText("you must fill all data");
            alert.showAndWait();
        }
        else if(!em.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("registeration error");
            alert.setContentText("invalid email");
            alert.showAndWait();
        }
        else{
            User userReg = new User(uName, pass, em, fName, lName, gend, userCountry, LocalDateTime.now(), "offline", "available");
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
                goToMainScenePage(userReg);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("registration");
                alert.setContentText("sign-up failed! try later");
                alert.showAndWait();
            }
        }
    }
    @FXML
    public void backAction(){
        goToLoginPage();
    }
    
    // Esraa Hassan Start
    private void goToMainScenePage(User user){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root;
            root = loader.load(getClass().getResource("/fxml/UserMainScene.fxml").openStream());
            MainSceneController mainController = loader.<MainSceneController>getController();
            ClientInterface client = new ClientImplementation(mainController);
            client.setUser(user);
            
            // Esraa Hassan
            this.server.register(client);
            mainController.setClient(client);
            mainController.setServer(server);
            //System.out.println(client.getUser().getUsername());
            Scene scene = new Scene(root);
            Stage mStage = (Stage) signUpRootPane.getScene().getWindow();
            mStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Esraa Hassan End
    private void goToLoginPage(){
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(getClass().getResource("/fxml/Login.fxml").openStream());
            FrontController controller = loader.<FrontController>getController();
            controller.setServer(server);
            //loader.setController(fronController);
            Stage mainStage =(Stage) this.username.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/styles.css");
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
    private void initController() {
        mStage = (Stage) signUpRootPane.getScene().getWindow();
        registerBtn.requestFocus();
         
        closeImgBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mStage.close();
                System.exit(0);
            }
        });

        minimizeImgBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mStage.setIconified(true);
                registerBtn.requestFocus();
            }
        });
        
        
    }
    @FXML
    public void holdChatWindow(MouseEvent event) {
        xOffset = mStage.getX() - event.getScreenX();
        yOffset = mStage.getY() - event.getScreenY();
    }

    @FXML
    public void dragChatWindow(MouseEvent event) {
        mStage.setX(event.getScreenX() + xOffset);
        mStage.setY(event.getScreenY() + yOffset);
    }
}

