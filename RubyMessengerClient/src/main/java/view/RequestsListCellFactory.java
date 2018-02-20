package view;

import com.jfoenix.controls.JFXListCell;
import controller.FriendsContentController;
import controller.MainSceneController;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.FriendshipRequest;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 12/02/2018
 */
public class RequestsListCellFactory implements Callback<ListView<FriendshipRequest>, JFXListCell<FriendshipRequest>>{
    MainSceneController controller;
    
    @Override
    public JFXListCell<FriendshipRequest> call(ListView<FriendshipRequest> param) {
        return new RequestsListCell(controller);
    }
    
    public void setController(MainSceneController controller) {
        this.controller = controller;
    }
    
}
