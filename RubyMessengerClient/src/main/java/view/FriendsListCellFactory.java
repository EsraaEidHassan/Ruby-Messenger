package view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 12/02/2018
 */
public class FriendsListCellFactory implements Callback<ListView<User>, ListCell<User>>{

    @Override
    public ListCell<User> call(ListView<User> param) {
        return new FriendsListCell();
    }
    
}
