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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class ActivityStatisticsController implements Initializable {

    @FXML
    private HBox Pane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserDao dao = new UserDao();
        int activityCount[] = dao.retrieveOnlineOflineCount();//0 online 1 offline
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Users Activity Statistics");
        xAxis.setLabel("status");       
        yAxis.setLabel("number of users");
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("now");       
        series1.getData().add(new XYChart.Data("Online", activityCount[0]));
        series1.getData().add(new XYChart.Data("Offline", activityCount[1]));
 
        bc.getData().add(series1);
        Pane.getChildren().add(bc);
    }    
    
}
