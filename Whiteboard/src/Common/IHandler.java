/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author biruk
 */
public interface IHandler extends Remote, Serializable {
    void sendMessage(IClient client, String message) throws RemoteException;
    LinkedList<Message> getMessages() throws RemoteException;
    
    void addFigure(IFigure figure) throws RemoteException;
    void undo() throws RemoteException;
    LinkedList<IFigure> getFigures() throws RemoteException;
    
    IClient getClient() throws RemoteException;
    IServer getServer() throws RemoteException;
    
    void disconnect() throws RemoteException;
}
