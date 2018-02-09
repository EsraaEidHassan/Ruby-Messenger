/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import controller.UserDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 *
 * @author toshiba
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {
    
    //Esraa Hassan
    private static Vector<ClientInterface> clients = new Vector<>();
    
    // Esraa Hassan
    public ServerImplementation() throws RemoteException {
    }

    @Override
    public void register(ClientInterface client) throws RemoteException {
        clients.add(client);
    }
    
    @Override
    public void unregister(ClientInterface client) throws RemoteException {
        clients.remove(client);
    }

    //Esraa Hassan
    @Override
    public boolean signup_user(User user) throws RemoteException {
        //this comment will be deleted if we resolve dependencies for com.oracle:ojdbc:jar:7
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

    @Override
    public User signInUser(String username) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
