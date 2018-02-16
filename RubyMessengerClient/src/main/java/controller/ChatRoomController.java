package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;

/**
 *
 * @author Mahmoud.Marzouk
 */
public class ChatRoomController implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // do controller initialize code here ...
            }
        });
    }
    
}
