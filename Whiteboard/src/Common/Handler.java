/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import Common.IClient;
import Common.IFigure;
import Common.IHandler;
import Common.IServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author biruk
 */
public class Handler extends UnicastRemoteObject implements IHandler {
    private IServer server;
    private IClient client;
    
    public Handler(IServer server, IClient client) throws RemoteException {
        this.server = server;
        this.client = client;
    }
    
    @Override
    public void sendMessage(IClient client, String message) throws RemoteException {
        this.server.broadcastMessage(client, message);
    }

    @Override
    public void addFigure(IFigure figure) throws RemoteException {
        this.server.addFigure(figure);
    }

    @Override
    public void undo() throws RemoteException {
        server.undo(client);
    }

    @Override
    public LinkedList<IFigure> getFigures() throws RemoteException {
        return this.server.getFigures();
    }

    @Override
    public IClient getClient() throws RemoteException {
        return this.client;
    }
    
    @Override
    public IServer getServer() throws RemoteException {
        return this.server;
    }

    @Override
    public LinkedList<Message> getMessages() throws RemoteException {
        return this.server.getMessages();
    } 

    @Override
    public void disconnect() throws RemoteException {
        this.server.disconnectCient(this.client.getName());
    }
    
    
}
