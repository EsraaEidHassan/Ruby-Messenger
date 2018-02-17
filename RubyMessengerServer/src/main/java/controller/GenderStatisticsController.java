/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.HostServer;
import model.ServerImplementation;

/**
 * FXML Controller class
 *
 * @author toshiba
 */
public class GenderStatisticsController implements Initializable {

    @FXML
    private HBox Pane;
    
    // Esraa Hassan start
    int[] genderCounter;
    Timeline fiveSecondsWonder;
    Stage stage;
    
    public GenderStatisticsController(Stage stage){
        this.stage = stage;
    }
    // Esraa Hassan end
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserDao dao = new UserDao();
        genderCounter = dao.retrieveMaleFemaleCount(); // index 0 for male , index 1 for female
        // Esraa Hassan Start
        IntegerProperty maleNums = new SimpleIntegerProperty(genderCounter[0]);
        IntegerProperty femaleNums = new SimpleIntegerProperty(genderCounter[1]);
    
        PieChart.Data dataMale =  new PieChart.Data("Males", genderCounter[0]);
        dataMale.pieValueProperty().bind(maleNums);
        PieChart.Data dataFemale = new PieChart.Data("Females", genderCounter[1]);
        dataFemale.pieValueProperty().bind(femaleNums);
        // Esraa Hassan end
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        dataMale,
                        dataFemale);
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Users Gender Percentage");    
        Pane.getChildren().add(chart);
        
        // Esraa Hassan start
        /*new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(5000);
                
                genderCounter = dao.retrieveMaleFemaleCount();

                maleNums.set(genderCounter[0]);
                femaleNums.set(genderCounter[1]);

                System.out.println(chart.getData());

                return null;
            }
        }).start();*/
        fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                genderCounter = dao.retrieveMaleFemaleCount();

                maleNums.set(genderCounter[0]);
                femaleNums.set(genderCounter[1]);

                System.out.println(chart.getData());
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                fiveSecondsWonder.stop();
                stage.close();
            }
        });     
        // Esraa Hassan end
    }
}
