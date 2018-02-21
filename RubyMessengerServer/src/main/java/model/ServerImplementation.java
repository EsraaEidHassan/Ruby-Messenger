
package model;

import common.ServerInterface;
import common.ClientInterface;
import controller.CountryDao;
import controller.FriendshipDao;
import controller.UserDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author toshiba
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {
    UserDao userDao = new UserDao();
    
    //Esraa Hassan
    private static HashMap<Long, ClientInterface> clients = new HashMap<Long, ClientInterface>();
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
        clients.put(client.getUser().getUserId(), client);
        User u = client.getUser();
        u.setUserStatus("online");
        userDao.updateUser(u);
        
    }
    
    @Override
    public void unregister(ClientInterface client) throws RemoteException {
        User u = client.getUser();
        u.setUserStatus("offline");
        userDao.updateUser(u);
        clients.remove(client.getUser().getUserId());
    }
    
    

    //Esraa Hassan
    @Override
    public boolean signup_user(User user) throws RemoteException {
        
        int result = userDao.insertUser(user);//to be edited (add if condition to call register if succeeded)
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
        User user = userDao.retrieveUser(username, password);
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
        for (ClientInterface client : clients.values()) {
            if(client.getUser().getUserStatus().equalsIgnoreCase("online"))
                client.recieveAnnouncement(message);
            else{
                System.out.println(client.getUser().getUsername()+" offline ");
            }
        }
    }
    
    //Esraa Hassan
    
    @Override
    public boolean askUsersSendFile(String senderName, long receiverId, String fileName) throws RemoteException {
        ClientInterface receiver = clients.get(receiverId);
        boolean isAccepted = receiver.sendFileRequest(senderName , fileName);
        return isAccepted;
    }

    @Override
    public synchronized void sendFile(byte[] data, String fileName, int length,long receiverId) throws RemoteException {
        ClientInterface receiver = clients.get(receiverId);
        receiver.reciveFile(data, fileName, length); 
    }
    
    // Mahmoud Marzouk
    @Override
    public void forwardFriendshipRequest(User fromUser, String usernameOrEmail) throws RemoteException {
        for (ClientInterface client : clients.values()) {
            if (client.findClient(usernameOrEmail)){
                client.receiveFriendRequest(fromUser);
            }
        }
    }
    
    // compare user to client and return client object
    @Override
    public ArrayList<ClientInterface> getOnlineClientsFromUserObjects(ArrayList<User> users) throws RemoteException{
        ArrayList<ClientInterface> retrievedClients = new ArrayList<>();
        for (User user : users) {
            ClientInterface client = clients.get(user.getUserId());
            if (client != null) {
                retrievedClients.add(client);
            }
        }
        return retrievedClients;
    }
    
    // Esraa Hassan start
    // we call this function if user logged in
    @Override
    public void sendNotificationToOnlineFriends(User user,ArrayList<ClientInterface> friends_Clients) throws RemoteException {

        for (ClientInterface client : friends_Clients) {
            client.recievNotificationFromOnlineFriend(user);
        }
    }
    // Esraa Hassan end
    
    // Esraa Hassan start
    @Override
    public boolean isThisUserLoggedIn(String username) throws RemoteException{
        boolean signed = false ;
        for (ClientInterface client : clients.values()) {
            if(client.getUser().getUsername().equals(username)){
                signed = true;
                break;
            }
        }
        return signed;
    }
    // Esraa Hassan end
    
    // Ahmed Start

    public void checkStateOFClients() throws RemoteException {
        Thread chekingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Iterator<Map.Entry<Long, ClientInterface>> clientsSetItr = clients.entrySet().iterator();
                    while (clientsSetItr.hasNext()) {
                        long userId = clientsSetItr.next().getKey();
                        try {
                            clientsSetItr.next().getValue().getUser();
                            Thread.sleep(1000);
                        } catch (RemoteException ex) {
                            User user = userDao.retrieveUser(userId);
                            user.setUserStatus("offline");
                            userDao.updateUser(user);
                            // remove user from hash map
                            clients.remove(userId);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        chekingThread.start();
    }
    
    @Override
    public void forWardMessage(Message msg) throws RemoteException{
        ArrayList<User> receivers = msg.getReceiver().getUsers();
        for (User receiver : receivers) {
            ClientInterface client = clients.get(receiver.getUserId());
            if (client != null) {
                client.receive(msg);
            }
        }
    }
    
    @Override
    public void clearAllClients() throws RemoteException {
        clients.clear();
    }
    
    @Override
    public void notifyMyFriendsChangingMode(User user) {
        userDao.updateUser(user);
        for ( User u : new FriendshipDao().retrieveAllFriends(user)) {
            try {
                ClientInterface client = clients.get(u.getUserId());
                if (client != null) {
                    client.updateFriendMode(user);
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }
    // Mahmoud Marzouk end (sending messages handling)

}
