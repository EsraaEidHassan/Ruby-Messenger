package controller;
// abdelfata7 start
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

 // abdelfata7 end

// khaled start
    
    
//khaled end
public class FrontController implements Initializable {
    // abdelfata7 start
    @FXML
    private Label noAccount;
    
    @FXML
    TextField username;
    
    // abdelfata7 end
    
    // khaled start
    
    
    //khaled end
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // abdelfata7 start
        noAccount.getStyleClass().add("label");
        username.getStyleClass().add("username");
    
        // abdelfata7 end
        
        // khaled start
    
    
        //khaled end
    }
    
    // abdelfata7 start
    
    
    // abdelfata7 end
    
    // khaled start
    
    
    //khaled end
}
