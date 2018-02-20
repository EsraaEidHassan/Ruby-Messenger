package view;

import com.jfoenix.controls.JFXListCell;
import controller.FriendsContentController;
import controller.MainSceneController;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 12/02/2018
 */
public class FriendsListCell extends JFXListCell<User> {

    private MainSceneController controller;
    private AnchorPane cellLayout = new AnchorPane();

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
            ImageView userImgVw = new ImageView("/user.png");
            userImgVw.setFitWidth(62);
            userImgVw.setFitHeight(62);
            userImgVw.setLayoutX(3);
            userImgVw.setLayoutY(3);
            Circle circle = new Circle(34, 34, 34, Color.valueOf("#ffffff00"));
            circle.setStroke(Color.valueOf("#7f8d9a"));
            userImgVw.setClip(new Circle(31, 31, 31));
            Label usernameTxt = new Label(item.getFirstName() + " " + item.getLastName());
            usernameTxt.setMaxSize(210, 24);
            usernameTxt.setEllipsisString("...");
            usernameTxt.setLayoutX(80);
            usernameTxt.setLayoutY(22);
            usernameTxt.setTextFill(Color.WHITE);
            usernameTxt.setFont(Font.font(null, FontWeight.BOLD, 15));
            ImageView userStatusImgVw = new ImageView();
            String userStatus = item.getUserStatus();
            if (userStatus.equals("online")) {
                String userMode = item.getUserMode();
                userStatusImgVw.setImage(new Image("/" + userMode + ".png"));     
            } else {
                userStatusImgVw.setImage(new Image("/offline.png")); 
            }
            userStatusImgVw.setFitWidth(16);
            userStatusImgVw.setFitHeight(16);
            userStatusImgVw.setLayoutX(280);
            userStatusImgVw.setLayoutY(26);
            
            cellLayout.getChildren().addAll(userImgVw, circle, usernameTxt, userStatusImgVw);
            cellLayout.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (item.getUserStatus().equals("online") && 
                            event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                        controller.onCellDoubleClickedAction(item);
                    }
                }
            });
            
            setGraphic(cellLayout);
        }
    }

}
