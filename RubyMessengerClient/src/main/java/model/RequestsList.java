/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.FriendshipRequestDao;
import java.util.ArrayList;

/**
 * @author Mahmoud.Marzouk
 */
public class RequestsList {
    ArrayList<FriendshipRequest> requests;

    public RequestsList(User u) {
        requests = new FriendshipRequestDao().retrieveIncomingFriendshipRequests(u);
    }

    public ArrayList<FriendshipRequest> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<FriendshipRequest> requests) {
        this.requests = requests;
    }

}
