/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import model.Country;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class CountryStatisticsController implements Initializable {

    @FXML
    private HBox Pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CountryDao cDao = new CountryDao();
        ArrayList<Country> countries = cDao.retrieveAllCountries();
        Map<String , Integer> countryUsers = cDao.getCountriesUsers();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("country users Statistics");
        xAxis.setLabel("countries");       
        yAxis.setLabel("number of users");
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("now"); 
        Iterator it = countryUsers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            series1.getData().add(new XYChart.Data(pair.getKey(), pair.getValue()));
            it.remove(); // avoids a ConcurrentModificationException
        }
        
        bc.getData().add(series1);
        Pane.getChildren().add(bc);
    }    
    
}
