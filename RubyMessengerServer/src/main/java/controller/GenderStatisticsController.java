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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Server;
import model.ServerImplementation;

/**
 * FXML Controller class
 *
 * @author toshiba
 */
public class GenderStatisticsController implements Initializable {

    @FXML
    private HBox Pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserDao dao = new UserDao();
        int[] genderCounter = dao.retrieveMaleFemaleCount(); // index 0 for male , index 1 for female
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Males", genderCounter[0]),
                        new PieChart.Data("Females", genderCounter[1]));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Users Gender Percentage");    
        Pane.getChildren().add(chart);

    }

}
