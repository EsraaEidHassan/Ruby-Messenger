package view;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 12/02/2018
 */
public class FriendsListCell extends ListCell<User> {
    AnchorPane cellLayout = new AnchorPane();

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        
        if (item != null && !empty) {
            cellLayout.getChildren().add(new Label(item.getUsername()));
        } else {
            setGraphic(null);
        }
    }
    
}
