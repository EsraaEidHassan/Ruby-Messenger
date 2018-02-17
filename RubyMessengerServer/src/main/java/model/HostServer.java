/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author toshiba
 */
public class HostServer {
    
    //Esraa Hassan
    Registry registry;
    
    public HostServer() {
        try {
            registry = LocateRegistry.createRegistry(2000);
        } catch (RemoteException ex) {
            Logger.getLogger(HostServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void startServer(){
        try {
            registry.rebind("chat", new ServerImplementation());
            System.out.println("server started");
        } catch (RemoteException ex) {
            Logger.getLogger(HostServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stopServer(){
        try {
            registry.unbind("chat");
            
            System.out.println("server stoped");
        } catch (RemoteException ex) {
            Logger.getLogger(HostServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(HostServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ServerImplementation getServerImpl(){
        try {
            return (ServerImplementation) registry.lookup("chat");
        } catch (RemoteException ex) {
            Logger.getLogger(HostServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(HostServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
