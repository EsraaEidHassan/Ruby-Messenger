package view;

import controller.MainSceneController;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 12/02/2018
 */
public class FriendsListCell extends ListCell<User> {

    private AnchorPane cellLayout = new AnchorPane();
    private MainSceneController controller;

    public FriendsListCell() {
    }

    public FriendsListCell(MainSceneController controller) {
        this.controller = controller;
    }

    @Override
    protected void updateItem(final User item, boolean empty) {
        super.updateItem(item, empty);

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        if (item == null || empty) {
            setGraphic(null);
        } else {
            cellLayout.getChildren().add(new Label(item.getUsername()));
            setGraphic(cellLayout);

            cellLayout.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                        controller.onCellDoubleClickedAction(item);
                    }
                }
            });
        }
    }

}
