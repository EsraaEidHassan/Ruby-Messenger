package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class ActivityStatisticsController implements Initializable {

    @FXML
    private HBox Pane;
    
    // Esraa Hassan start
    int activityCount[];
    Timeline fiveSecondsWonder;
    Stage stage;
    
    public ActivityStatisticsController(Stage stage){
        this.stage = stage;
    }
    // Esraa Hassan end
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserDao dao = new UserDao();
        activityCount = dao.retrieveOnlineOflineCount();//0 online 1 offline
        // Esraa Hassan Start
        IntegerProperty onlineNums = new SimpleIntegerProperty(activityCount[0]);
        IntegerProperty offlineNums = new SimpleIntegerProperty(activityCount[1]);
        // Esraa Hassan end
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Users Activity Statistics");
        xAxis.setLabel("status");       
        yAxis.setLabel("number of users");
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("now");     
        XYChart.Data xyDataOnline =  new XYChart.Data("Online", activityCount[0]); // edited by Esraa
        xyDataOnline.YValueProperty().bind(onlineNums); // edited by Esraa
        XYChart.Data xyDataOffline =  new XYChart.Data("Offline", activityCount[1]);// edited by Esraa
        xyDataOffline.YValueProperty().bind(offlineNums);// edited by Esraa
        series1.getData().add(xyDataOnline);// edited by Esraa
        series1.getData().add(xyDataOffline);// edited by Esraa
 
        bc.getData().add(series1);
        Pane.getChildren().add(bc);
        
        // Esraa Hassan start
        fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                activityCount = dao.retrieveOnlineOflineCount();//0 online 1 offline

                onlineNums.set(activityCount[0]);
                offlineNums.set(activityCount[1]);

                System.out.println(bc.getData());
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
