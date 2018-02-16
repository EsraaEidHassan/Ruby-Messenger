package view;

import com.jfoenix.controls.JFXListCell;
import controller.FriendsContentController;
import controller.MainSceneController;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 12/02/2018
 */
public class FriendsListCellFactory implements Callback<ListView<User>, JFXListCell<User>>{
    MainSceneController controller;
    
    @Override
    public JFXListCell<User> call(ListView<User> param) {
        return new FriendsListCell(controller);
    }
    
    public void setController(MainSceneController controller) {
        this.controller = controller;
    }
    
}
