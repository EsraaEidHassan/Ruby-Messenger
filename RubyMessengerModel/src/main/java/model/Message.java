package model;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import model.User;

/**
 *
 * @author khaled
 */
public class Message implements Serializable {
    private String messageContent ;
    private User sender ;
    ArrayList<User> receivers;
    private Color color;
    private int fontSize;
    private FontPosture fontStyle;
    private FontWeight fontWeight;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public ArrayList<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(ArrayList<User> receivers) {
        this.receivers = receivers;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public FontPosture getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(FontPosture fontStyle) {
        this.fontStyle = fontStyle;
    }

    public FontWeight getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(FontWeight fontWeight) {
        this.fontWeight = fontWeight;
    }
}
