/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import model.Country;

/**
 * FXML Controller class
 *
 * @author khaled
 */
public class CountryStatisticsController implements Initializable {

    @FXML
    private HBox Pane;
    
    // Note :
    // the binding done only in y Axis (if a country added or removed, this will not be affected)
    // Esraa Hassan start
    Timeline fiveSecondsWonder;
    Map<String , Integer> countryUsers;
    Iterator it;
    int index;
    
    Stage stage;
    
    public CountryStatisticsController(Stage stage){
        this.stage = stage;
    }
    // Esraa Hassan end
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CountryDao cDao = new CountryDao();
        //List<Country> countries = cDao.retrieveAllCountries();
        countryUsers = cDao.getCountriesUsers();
        // Esraa Hassan Start
        IntegerProperty[] countriesIntegerProperty = new IntegerProperty[countryUsers.size()];
        // Esraa Hassan end
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("country users Statistics");
        xAxis.setLabel("countries");       
        yAxis.setLabel("number of users");
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("now");
        it = countryUsers.entrySet().iterator(); // it already defined
        // Esraa Hassan start
        index = 0;
        // Esraa Hassan end
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            // Esraa Hassan start
            XYChart.Data xyData =  new XYChart.Data(pair.getKey(), pair.getValue());
            countriesIntegerProperty[index] =  new SimpleIntegerProperty((int) pair.getValue());
            xyData.YValueProperty().bind(countriesIntegerProperty[index]); // binding to y Axis
            series1.getData().add(xyData);
            System.out.println(xyData);
            
            index++;
            // Esraa Hassan end
            it.remove(); // avoids a ConcurrentModificationException
        }
        
        bc.getData().add(series1);
        Pane.getChildren().add(bc);
        
        // Esraa Hassan start
        fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                countryUsers = cDao.getCountriesUsers();

                it = countryUsers.entrySet().iterator(); 
                index = 0;
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    countriesIntegerProperty[index].set((int) pair.getValue());
                    index++;
                    it.remove();
                }

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
