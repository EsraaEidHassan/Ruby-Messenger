package model;

import java.io.Serializable;
import model.User;
import model.User;

/**
 *
 * @author khaled
 */
public class Message implements Serializable {
    // Ahmed Start
    private User receiver;
    
    // Ahmed End
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
    
    // Marzouk End
    
    // Ahmed Start
    
    public User getReceiver(){
        return receiver;
    }
    
    public void setReceiver( User receiver){
        this.receiver = receiver;
    }
    
    
    // Ahmed End

}
