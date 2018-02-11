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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import model.ServerImplementation;

/**
 * FXML Controller class
 *
 * @author toshiba
 */
public class GenderStatisticsController implements Initializable {

    
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;
    
    private ObservableList<String> genderNames = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String[] gender = {"Male" , "Female"};
        // Convert it to a list and add it to our ObservableList of gender.
        genderNames.addAll(Arrays.asList(gender));

        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(genderNames);
    }    
    
    public void setGenderData(ServerImplementation serverImpl) throws RemoteException {
        
        int[] genderCounter = serverImpl.getMaleFemaleUsers(); // index 0 for male , index 1 for female
        /*for (Person p : persons) {
            int month = p.getBirthday().getMonthValue() - 1;
            monthCounter[month]++;
        }*/
        
        //genderCounter[0] = 5;
        //genderCounter[1] = 8;
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each gender. Add it to the series.
        for (int i = 0; i < genderCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(genderNames.get(i), genderCounter[i]));
        }

        barChart.getData().add(series);
    }
    
}
