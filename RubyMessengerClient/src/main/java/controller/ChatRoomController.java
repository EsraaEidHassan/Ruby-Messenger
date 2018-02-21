/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.Serializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.ChatRoom;
import view.CmbCellFactoryForColorPicker;

/**
 *
 * @author Ahmed
 */
public class ChatRoomController implements Initializable, Serializable {

    @FXML
    private JFXButton sendMsgImgBtn;
    @FXML
    private TextField msgTxtField;
    @FXML
    private VBox showMsgsBox;
    @FXML
    private ImageView attachFileImgBtn;
   
    public ImageView getAttachFileImgBtn() {
        return attachFileImgBtn;
    }

    public void setAttachFileImgBtn(ImageView attachFileImgBtn) {
        this.attachFileImgBtn = attachFileImgBtn;
    }

    @FXML
    private ComboBox<String> toAddGroupMembers;
    @FXML
    private JFXButton addGroupMembersImgBtn;
    @FXML
    private Label chatReceiversTxtLabel;
            
    // Esraa Hassan start
    @FXML
    private JFXButton saveChatImgBtn;
    // Esraa Hassan end
    // Esraa Hassan start
    @FXML
    private JFXToggleButton italicButton;
    @FXML
    private JFXToggleButton boldButton;
    @FXML
    private JFXComboBox sizeComboBox;
    @FXML
    private JFXColorPicker fontColorPicker;
    private String colorPicked;
    private int sizePicked;
    private boolean italic;
    private boolean bold;
    // Esraa Hassan end
    private ChatRoom mChatRoom = new ChatRoom();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Esraa Hassan start
        fontColorPicker.setValue(Color.BLACK);
        colorPicked = toRGBCode(fontColorPicker.getValue());
        fontColorPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            Color color = (Color) newVal;
            System.out.println("Color : " + toRGBCode(color));
            colorPicked = toRGBCode(color);
            setTextFieldStyle();
        }
        );

        sizeComboBox.getItems().addAll(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28);
        sizeComboBox.setValue(14);
        sizePicked = 14;

        sizeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            //Country current = (Country) newVal;
            int size = (int) newVal;
            System.out.println("Size : " + size);
            sizePicked = size;
            setTextFieldStyle();
        }
        );

        italic = false;
        bold = false;

        boldButton.setOnAction((ActionEvent e) -> {
            if (bold) {
                bold = false;
            } else {
                bold = true;
            }
            System.out.println("Now bold is " + bold);
            setTextFieldStyle();
        });
        italicButton.setOnAction((ActionEvent e) -> {
            if (italic) {
                italic = false;
            } else {
                italic = true;
            }
            System.out.println("Now italic is " + italic);
            setTextFieldStyle();
        });
        // Esraa Hassan end

    }
    
    public JFXButton getSendMsgImgBtn() {
        return sendMsgImgBtn;
    }

    public void setSendMsgImgBtn(JFXButton sendMsgImgBtn) {
        this.sendMsgImgBtn = sendMsgImgBtn;
    }

    public TextField getMsgTxtField() {
        return msgTxtField;
    }

    public void setMsgTxtField(TextField msgTxtField) {
        this.msgTxtField = msgTxtField;
    }

    public VBox getShowMsgsBox() {
        return showMsgsBox;
    }

    public void setShowMsgsBox(VBox showMsgsBox) {
        this.showMsgsBox = showMsgsBox;
    }

    public ChatRoom getmChatRoom() {
        return mChatRoom;
    }

    public void setmChatRoom(ChatRoom mChatRoom) {
        this.mChatRoom = mChatRoom;
    }

    // Esraa Hassan start
    public JFXButton getSaveChatImgBtn() {
        return saveChatImgBtn;
    }

    public void setSaveChatImgBtn(JFXButton saveChatImgBtn) {
        this.saveChatImgBtn = saveChatImgBtn;
    }

    // Esraa Hassan end
    // Esraa Hassan start
    public JFXToggleButton getItalicToggleButton() {
        return italicButton;
    }

    public void setItalicToggleButton(JFXToggleButton italicButton) {
        this.italicButton = italicButton;
    }

    public JFXToggleButton getBoldToggleButton() {
        return boldButton;
    }

    public void setBoldToggleButton(JFXToggleButton boldButton) {
        this.boldButton = boldButton;
    }

    public JFXComboBox getSizeComboBox() {
        return sizeComboBox;
    }

    public void setSizeComboBox(JFXComboBox sizeComboBox) {
        this.sizeComboBox = sizeComboBox;
    }

    public JFXColorPicker getFontColorPicker() {
        return fontColorPicker;
    }

    public void setFontColorPicker(JFXColorPicker fontColorPicker) {
        this.fontColorPicker = fontColorPicker;
    }

    public ComboBox<String> getToAddGroupMembers() {
        return toAddGroupMembers;
    }

    public void setToAddGroupMembers(ComboBox<String> toAddGroupMembers) {
        this.toAddGroupMembers = toAddGroupMembers;
    }

    public JFXButton getAddGroupMembersImgBtn() {
        return addGroupMembersImgBtn;
    }

    public void setAddGroupMembersImgBtn(JFXButton addGroupMembersImgBtn) {
        this.addGroupMembersImgBtn = addGroupMembersImgBtn;
    }

    public Label getChatReceiversTxtLabel() {
        return chatReceiversTxtLabel;
    }

    public void setChatReceiversTxtLabel(Label chatReceiversTxtLabel) {
        this.chatReceiversTxtLabel = chatReceiversTxtLabel;
    }
    
    public String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public String getColorPicked() {
        return this.colorPicked;
    }

    public int getSizePicked() {
        return this.sizePicked;
    }

    public FontWeight getFontWeight() {
        if (bold) {
            return FontWeight.BOLD;
        } else {
            return FontWeight.LIGHT;
        }
    }

    public FontPosture getFontPosture() {
        if (italic) {
            return FontPosture.ITALIC;
        } else {
            return FontPosture.REGULAR;
        }
    }

    public void setTextFieldStyle() {
        String str = msgTxtField.getText().toString();
        msgTxtField.setText("");
        msgTxtField.setStyle("-fx-text-fill: " + colorPicked + ";" + "-fx-font-size: " + sizePicked + ";" + " -fx-font-weight:" + getFontWeight().name() + ";" + " -fx-font-style:" + getFontPosture().name());
        msgTxtField.setText(str);
    }
    // Esraa Hassan end

}
