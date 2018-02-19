package controller;

import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Mahmoud.Marzouk
 */
public class FriendshipRequestController implements Initializable {
    @FXML
    private JFXListView friendRequestsListVw;
    @FXML
    private TextField friendRequestUserTxt;
    @FXML
    private Button sendFriendRequestBtn;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public JFXListView getFriendRequestsListVw() {
        return friendRequestsListVw;
    }

    public void setFriendRequestsListVw(JFXListView friendRequestsListVw) {
        this.friendRequestsListVw = friendRequestsListVw;
    }

    public TextField getFriendRequestUserTxt() {
        return friendRequestUserTxt;
    }

    public void setFriendRequestUserTxt(TextField friendRequestUserTxt) {
        this.friendRequestUserTxt = friendRequestUserTxt;
    }

    public Button getSendFriendRequestBtn() {
        return sendFriendRequestBtn;
    }

    public void setSendFriendRequestBtn(Button sendFriendRequestBtn) {
        this.sendFriendRequestBtn = sendFriendRequestBtn;
    }
    
}
