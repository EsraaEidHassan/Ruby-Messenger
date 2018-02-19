package view;

import com.jfoenix.controls.JFXListCell;
import controller.FriendshipDao;
import controller.FriendshipRequestDao;
import controller.MainSceneController;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Friendship;
import model.FriendshipRequest;
import model.User;

/**
 * @author Mahmoud.Marzouk
 * @since 12/02/2018
 */
public class RequestsListCell extends JFXListCell<FriendshipRequest> {
    
    private MainSceneController controller;
    private AnchorPane requestLayout = new AnchorPane();

    public RequestsListCell() {
    }

    public RequestsListCell(MainSceneController controller) {
        this.controller = controller;
    }

    @Override
    protected void updateItem(FriendshipRequest item, boolean empty) {
        super.updateItem(item, empty);

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        if (item == null || empty) {
            setGraphic(null);
        } else {
            ImageView userImgVw = new ImageView("/user.png");
            userImgVw.setFitWidth(46);
            userImgVw.setFitHeight(46);
            /*
            Circle circle = new Circle(25, 25, 25, Color.valueOf("#ffffff00"));
            circle.setStroke(Color.valueOf("#7f8d9a"));
            */
            userImgVw.setClip(new Circle(23, 23, 23));
            Label usernameTxt = new Label(item.getFromUser().getFirstName() + " " + item.getFromUser().getLastName());
            usernameTxt.setMaxSize(90, 24);
            usernameTxt.setEllipsisString("...");
            usernameTxt.setLayoutX(55);
            usernameTxt.setLayoutY(13);
            usernameTxt.setTextFill(Color.WHITE);
            usernameTxt.setFont(Font.font(null, FontWeight.NORMAL, 15));
            
            ImageView approveImg = new ImageView("/approve_request.png");
            approveImg.setFitWidth(36);
            approveImg.setFitHeight(16);
            Button approveBtn = new Button("", approveImg);
            approveBtn.setPrefSize(30, 16);
            approveBtn.setLayoutX(172);
            approveBtn.setLayoutY(9);
            ImageView rejectImg = new ImageView("/reject_request.png");
            rejectImg.setFitWidth(36);
            rejectImg.setFitHeight(16);
            Button rejectBtn = new Button("", rejectImg);
            rejectBtn.setPrefSize(30, 16);
            rejectBtn.setLayoutX(235);
            rejectBtn.setLayoutY(9);
            
            approveBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    item.setAcceptedYN("Y");
                    item.setSeenYN("Y");
                    item.setResponseDate(LocalDateTime.now());
                    new FriendshipRequestDao().updateFriendshipRequest(item);
                    FriendshipDao fDao = new FriendshipDao();
                    fDao.insertFriendship(new Friendship(item.getFromUser(), item.getToUser()));
                    fDao.insertFriendship(new Friendship(item.getToUser(), item.getFromUser()));
                    controller.populateFriendsList();
                    controller.populateRequestsList();
                }
            });
            rejectBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    item.setAcceptedYN("N");
                    item.setSeenYN("Y");
                    item.setResponseDate(LocalDateTime.now());
                    new FriendshipRequestDao().updateFriendshipRequest(item);
                    controller.populateRequestsList();
                }
            });
            
            //requestLayout.getChildren().addAll(userImgVw, circle, usernameTxt, approveBtn, rejectImg);
            requestLayout.getChildren().addAll(userImgVw, usernameTxt, approveBtn, rejectBtn);
            
            setGraphic(requestLayout);
        }
    }

}
