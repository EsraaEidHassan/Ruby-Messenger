package model;

import java.io.Serializable;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import model.User;
import model.User;

/**
 *
 * @author Mahmoud.Marzouk
 */
public class Message implements Serializable {
    
    private String messageContent;
    private User sender;
    private ChatRoom receiver;
    private String color;
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

    public ChatRoom getReceiver() {
        return receiver;
    }

    public void setReceiver(ChatRoom receiver) {
        this.receiver = receiver;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
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
