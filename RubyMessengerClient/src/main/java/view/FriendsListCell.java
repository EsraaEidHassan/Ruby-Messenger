package view;

import com.jfoenix.controls.JFXListCell;
import controller.FriendsContentController;
import controller.MainSceneController;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 12/02/2018
 */
public class FriendsListCell extends JFXListCell<User> {

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
            Label usernameTxt = new Label(item.getUsername());
            usernameTxt.setTextFill(Color.WHITE);
            cellLayout.getChildren().add(usernameTxt);
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
