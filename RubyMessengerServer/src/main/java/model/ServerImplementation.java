/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

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
    @Override
    public int[] getOnlineAndOfflineUsers() throws RemoteException{
        int[] nums = new int[2];
        int counter_online = 0, counter_offline = 0;
        for (ClientInterface client : clients) {
            if(client.getUser().getUserStatus().equalsIgnoreCase("online"))
                counter_online++;
            else{
                counter_offline++;
            }
        }
        nums[0] = counter_online;
        nums[1] = counter_offline;
        
        return nums;
    }
    
    //Esraa Hassan
    @Override
    public int[] getMaleFemaleUsers() throws RemoteException{
        
        UserDao dao = new UserDao();
        int[] male_female_count = dao.retrieveMaleFemaleCount();//index 0 for male //index 1 for female
        return male_female_count;
        
        /*int[] nums = new int[2];
        int counter_male = 0, counter_female = 0;
        for (ClientInterface client : clients) {
            if(client.getUser().getGender().equalsIgnoreCase("male"))
                counter_male++;
            else{
                counter_female++;
            }
        }
        nums[0] = counter_male;
        nums[1] = counter_female;
        
        return nums;*/
    }
    
}
