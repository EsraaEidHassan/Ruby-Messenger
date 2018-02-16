
package model;


import common.ServerInterface;
import common.ClientInterface;
import controller.CountryDao;
import controller.UserDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author toshiba
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {
    
    //Esraa Hassan
    private static Vector<ClientInterface> clients = new Vector<>();
    //private boolean accepted; //old code (accept connection)
    //private boolean decided; //old code (accept connection)
    
    // Esraa Hassan
    public ServerImplementation() throws RemoteException {
    }

    @Override
    public void register(ClientInterface client) throws RemoteException {
        
        /*decided = false; //old code (accept connection)
        accepted = false;
        Platform.runLater(() -> {
            try {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Accept Connection Dialog");
                alert.setHeaderText("New user wants to join");
                alert.setContentText("Username : "+client.getUser().getUsername());

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK){
                    clients.add(client);
                    System.out.println("Done Registring");
                    accepted = true;
                    decided = true;
                } else {
                    // ... user chose CANCEL or closed the dialog
                    System.out.println("Registring rejected");
                    accepted = false;
                    decided = true;
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
                accepted = false;
            }
        });    
        */
        clients.add(client);
    }
    
    
    
    
    
    @Override
    public void unregister(ClientInterface client) throws RemoteException {
        clients.remove(client);
    }

    //Esraa Hassan
    @Override
    public boolean signup_user(User user) throws RemoteException {
        
        UserDao dao = new UserDao();
        int result = dao.insertUser(user);//to be edited (add if condition to call register if succeeded)
        if(result > 0){
            return true;
        }else{
            return false;
        }
        //if signed up, server will call register and keep obj of client (Esraa) + redirect to home page with empty contact list
        //if not . nothing will happen, redirect to signup page @ client
    }

    //Esraa Hassan
    @Override
    public User signInUser(String username, String password) throws RemoteException {
        UserDao dao = new UserDao();
        User user = dao.retrieveUser(username, password);
        return user;
        // don't forget to check user at client (if null , signin faild)
    }
    
    // Esraa Hassan start
    @Override
    public List<Country> retrieveAllCountries() throws RemoteException {
        CountryDao dao = new CountryDao();
        List<Country> countries = dao.retrieveAllCountries();
        return countries;
    }
    // Esraa Hassan end
    
    //Esraa Hassan
    /*@Override //old code (accept connection)
    public boolean getAcceptedState() throws RemoteException {
        return this.accepted;
    }
    
    @Override
    public boolean getDecidedState() throws RemoteException {
        return this.decided;
    }*/

    //Esraa Hassan
    @Override
    public void sendAnnouncement(String message) throws RemoteException {
        
        for (ClientInterface client : clients) {
            if(client.getUser().getUserStatus().equalsIgnoreCase("online"))
                client.recieveAnnouncement(message);
            else{
                System.out.println(client.getUser().getUsername()+" offline ");
            }
        }
    }
    
    //Esraa Hassan
    
    
    // Mahmoud Marzouk
    @Override
    public void forwardFriendshipRequest(User fromUser, String usernameOrEmail) throws RemoteException {
        for (ClientInterface client : clients) {
            if (client.findClient(usernameOrEmail)){
                client.receiveFriendRequest(fromUser);
            }
        }
    }

    // Ahmed
    @Override
    public void sendMessageToUsers(ArrayList<ClientInterface> clientInChat, Message msg) throws RemoteException {
        for (int i = 0; i < clientInChat.size(); i++) {
            try {
                clientInChat.get(i).receive(msg);
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // compare user to client and return client object
    @Override
    public ArrayList<ClientInterface> getOnlineClientsFromUserObjects(ArrayList<User> users) throws RemoteException {
        ArrayList<ClientInterface> retrievedClients = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);
            try {
                for (int j = 0; j < clients.size(); j++) {
                    if (currentUser.getUserId() == clients.get(j).getUser().getUserId()) {
                        retrievedClients.add(clients.get(j));
                    }
                    
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return retrievedClients;
    }
    
}
