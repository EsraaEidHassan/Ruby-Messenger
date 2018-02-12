package model;

import java.io.Serializable;
import model.User;
import model.User;

/**
 *
 * @author khaled
 */
public class Message implements Serializable {

    private String messageContent;
    private User sender;

    // mahmoud marzouk 10/02/2018
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

}
