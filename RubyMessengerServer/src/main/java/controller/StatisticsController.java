/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import model.Server;

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
    
    
    //---------This for moving the stage freely :)-------------------------//
    //---------------------------------------------------------------------//
    private double startMoveX = -1, startMoveY = -1;
    private Boolean dragging = false;
    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        genderBtn.setOnAction((event) -> {
            showGenderStatistics();
        });
        activityBtn.setOnAction((event) -> {
            showActivityStatistics();
        });
        countryBtn.setOnAction((event) -> {
            showCountryStatistics();
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
   public void showGenderStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/GenderStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Gender Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(activityBtn.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    public void showActivityStatistics(){
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/ActivityStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Activity Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(activityBtn.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
    
    public void showCountryStatistics(){
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/CountryStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Activity Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(activityBtn.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
