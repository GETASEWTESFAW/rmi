/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import Common.IClient;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author biruk
 */
public class ClientManager {
    private LinkedList<IClient> clients;
    
    public ClientManager() {
        this.clients = new LinkedList<>();
    }
    
    public void addClient(IClient client) {
        this.clients.add(client);
    }
    
    public boolean contains(IClient client) throws RemoteException {
        for (IClient c : clients) {
            if (c.getName().equals(client.getName())) {
                return true;
            }
        }
        return false;
    }
    
    public void removeClient(IClient client) throws RemoteException {
        this.clients.remove(client);
    }
    
    public int numberOfClients() {
        return this.clients.size();
    }
    
    public LinkedList<IClient> getClients() {
        return this.clients;
    }
    
}
