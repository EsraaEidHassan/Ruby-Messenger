package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Mahmoud.Marzouk
 * @since 09/02/2018
 */
public class FriendshipRequest implements Serializable {
    private User fromUser;
    private User toUser;
    private LocalDateTime requestDate;
    private String seenYN;
    private String acceptedYN;
    private LocalDateTime responseDate;

    public FriendshipRequest(User fromUser, User toUser) {
        /* Default values */
        this.requestDate = LocalDateTime.now();
        this.seenYN = "N";
        this.acceptedYN = "N";
        // response date will be null by default
        // --------------------------------------------------
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
    
    public FriendshipRequest(User fromUser, User toUser, LocalDateTime requestDate, String seenYN, String acceptedYN, 
            LocalDateTime responseDate) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.requestDate = requestDate;
        this.acceptedYN = acceptedYN;
        this.seenYN = seenYN;
        this.responseDate = responseDate;
    }
    
    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getSeenYN() {
        return seenYN;
    }

    public void setSeenYN(String seenYN) {
        this.seenYN = seenYN;
    }

    public String getAcceptedYN() {
        return acceptedYN;
    }

    public void setAcceptedYN(String acceptedYN) {
        this.acceptedYN = acceptedYN;
    }

    public LocalDateTime getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(LocalDateTime responseDate) {
        this.responseDate = responseDate;
    }
    
}
