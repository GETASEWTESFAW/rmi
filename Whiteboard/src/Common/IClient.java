/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author biruk
 */
public interface IClient extends Remote {
    void removeClient() throws RemoteException;
    
    void syncDrawingBoard() throws RemoteException;
    void cleanAndRefreshDrawingBoard() throws RemoteException;
    void syncMessageBoard() throws RemoteException;
    
    void setName(String name) throws RemoteException;
    String getName() throws RemoteException;
    
    void addDrawingBoardChangeListener(IDrawingBoardChangeListener listener) throws RemoteException;
    void addMessageBoardChangeListener(IMessageBoardChangeListener listener) throws RemoteException;
    void addRemovedListener(ITerminationListener listener) throws RemoteException;
    
    void ping(String message) throws RemoteException;
    void serverTerminated() throws RemoteException;
}
