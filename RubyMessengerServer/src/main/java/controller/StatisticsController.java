/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.HostServer;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class StatisticsController implements Initializable {

    @FXML
    private Button genderBtn;
    @FXML
    private Button countryBtn;
    @FXML
    private Button activityBtn;
    @FXML
    private Label btnClose;
    @FXML
    private Label btnMinimize;
    @FXML
    private AnchorPane topbar;
    @FXML
    private Button backBtn;
    
    private HostServer server;
    
    //---------This for moving the stage freely :)-------------------------//
    //---------------------------------------------------------------------//
    private double startMoveX = -1, startMoveY = -1;
    private Boolean dragging = false;
    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btnClose.getStyleClass().add("contrlLabel");
        btnMinimize.getStyleClass().add("contrlLabel");
        
        genderBtn.setOnAction((event) -> {
            showGenderStatistics();
        });
        activityBtn.setOnAction((event) -> {
            showActivityStatistics();
        });
        countryBtn.setOnAction((event) -> {
            showCountryStatistics();
        });
        backBtn.setOnAction((event) -> {
            backToHome();
        });
        //-----------------------Drag---------------------------//
        topbar.setOnDragDetected((MouseEvent event) -> {
            startMoveX = event.getScreenX();
            startMoveY = event.getScreenY();
            dragging = true;

            moveTrackingRect = new Rectangle();
            moveTrackingRect.setWidth(topbar.getWidth());
            moveTrackingRect.setHeight(topbar.getHeight());

            moveTrackingPopup = new Popup();
            moveTrackingPopup.getContent().add(moveTrackingRect);
            moveTrackingPopup.show(topbar.getScene().getWindow());
            moveTrackingPopup.setOnHidden((e) -> resetMoveOperation());
        });

        topbar.setOnMouseDragged((MouseEvent event) -> {

            if (dragging) {

                double endMoveX = event.getScreenX();
                double endMoveY = event.getScreenY();

                Window w = topbar.getScene().getWindow();

                double stageX = w.getX();
                double stageY = w.getY();

                moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
                moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
            }
        });

        topbar.setOnMouseReleased((MouseEvent event) -> {

            if (dragging) {
                double endMoveX = event.getScreenX();
                double endMoveY = event.getScreenY();

                Window w = topbar.getScene().getWindow();

                double stageX = w.getX();
                double stageY = w.getY();

                w.setX(stageX + (endMoveX - startMoveX));
                w.setY(stageY + (endMoveY - startMoveY));

                if (moveTrackingPopup != null) {
                    moveTrackingPopup.hide();
                    moveTrackingPopup = null;
                }
            }

            resetMoveOperation();

        });
        btnClose.setOnMouseClicked((MouseEvent event) -> {
            System.out.println("Exit Server");
            // todo : unregister all users
            Platform.exit();
            System.exit(0);
        });
        
        btnMinimize.setOnMouseClicked((MouseEvent event) -> {

            System.out.println("minimize window");
            Stage stage = (Stage) ((Label) event.getSource()).getScene().getWindow();

            stage.setIconified(true);
        });
    }
    
    private void resetMoveOperation() {
        startMoveX = 0;
        startMoveY = 0;
        dragging = false;
        moveTrackingRect = null;
    }

   /////////////////functions of pop up stages////////////////// 
   private void showGenderStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GenderStatistics.fxml"));
            //loader.setLocation(getClass().getResource("/fxml/GenderStatistics.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Gender Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(activityBtn.getScene().getWindow());
            GenderStatisticsController controller = new GenderStatisticsController(dialogStage);
            loader.setController(controller);
            //GenderStatisticsController controller = loader.<GenderStatisticsController(dialogStage)>getController();
            AnchorPane page = (AnchorPane) loader.load();
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.show();
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    private void showActivityStatistics(){
        try {
            
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ActivityStatistics.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Activity Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(activityBtn.getScene().getWindow());
            ActivityStatisticsController controller = new ActivityStatisticsController(dialogStage);
            loader.setController(controller);
            AnchorPane page = (AnchorPane) loader.load();
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
    
    private void showCountryStatistics(){
        try {
            
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CountryStatistics.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Country Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(activityBtn.getScene().getWindow());
            CountryStatisticsController controller = new CountryStatisticsController(dialogStage);
            loader.setController(controller);
            AnchorPane page = (AnchorPane) loader.load();
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void backToHome(){
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(getClass().getResource("/fxml/Scene.fxml").openStream());
            root.lookup("#startServer").setDisable(true);
            root.lookup("#stopServer").setDisable(false);
            root.lookup("#sendAnnouncementButton").setDisable(false);
            root.lookup("#statisticsButton").setDisable(false);
            MainController controller = loader.<MainController>getController();
            controller.setServer(server);
            Stage mainStage =(Stage) this.activityBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            mainStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setServer(HostServer server){
        this.server = server;
    }
}
