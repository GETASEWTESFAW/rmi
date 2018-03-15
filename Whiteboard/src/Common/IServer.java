/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import Common.IFigure;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author biruk
 */
public interface IServer extends Remote, Serializable {
    void addFigure(IFigure figure) throws RemoteException;
    void undo(IClient client) throws RemoteException;
    LinkedList<IFigure> getFigures() throws RemoteException;
    
    void broadcastMessage(IClient client, String message) throws RemoteException;
    LinkedList<Message> getMessages() throws RemoteException; 
    
    IHandler registerClient(IClient client) throws RemoteException;
    void disconnectCient(String username) throws RemoteException;
    void kickoutClient(String username) throws RemoteException;
    void validateClientsList() throws RemoteException;
    LinkedList<IClient> getClients() throws RemoteException;
    void addClientListChangeListener(IClientsListChangeListener listener) throws RemoteException;
    
    IHandler registerAdmin(IClient client) throws RemoteException;
    IClient getAdministrator() throws RemoteException;
    
    int requestMembership(IClient client) throws RemoteException;   
    
    void newBoard() throws RemoteException;
    void openBoard(ObjectInputStream inputStream) throws RemoteException;
    void saveBoard(ObjectOutputStream outputStream ) throws RemoteException;
    
    void addTerminationListener(IServerTerminationReadyListener listener) throws RemoteException;
    void close() throws RemoteException;
}
