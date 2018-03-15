/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import Common.IClient;
import Common.IServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import Common.IDrawingBoardChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sun.rmi.runtime.Log;

/**
 *
 * @author biruk
 */
public class WhiteboardClient extends UnicastRemoteObject implements IClient {
    private String name;
    private ArrayList<IDrawingBoardChangeListener> drawingBoardListeners;
    private ArrayList<IMessageBoardChangeListener> messageBoardListeners;
    private ArrayList<ITerminationListener> removedListeners;
    
    public WhiteboardClient(String name) throws RemoteException {
        this.name = name;
        this.drawingBoardListeners = new ArrayList<>();
        this.messageBoardListeners = new ArrayList<>();
        this.removedListeners = new ArrayList<>();
    }

    @Override
    public void syncDrawingBoard() throws RemoteException {
        notifyDrawingBoardListeners();
    }
    
    @Override
    public void syncMessageBoard() throws RemoteException {
        notifyMessageBoardListeners();
    }
    
    @Override
    public void setName(String name) throws RemoteException {
        this.name = name;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public void removeClient() throws RemoteException {
        notifyKickedOutListeners();
    } 
    
    @Override
    public void addDrawingBoardChangeListener(IDrawingBoardChangeListener listener) throws RemoteException {
        this.drawingBoardListeners.add(listener);
    }

    @Override
    public void addMessageBoardChangeListener(IMessageBoardChangeListener listener) throws RemoteException {
        this.messageBoardListeners.add(listener);
    }

    @Override
    public void addRemovedListener(ITerminationListener listener) throws RemoteException {
        this.removedListeners.add(listener);
    }
    
    @Override
    public void serverTerminated() throws RemoteException {
        notifyServerTerminatedListeners();
    }

    @Override
    public void cleanAndRefreshDrawingBoard() throws RemoteException {
        notifyDrawingBoardCurrentNullListeners();
    }
    
    private void notifyDrawingBoardListeners() {
        for (IDrawingBoardChangeListener listener : drawingBoardListeners) {
            listener.syncDrawingBoard();
        }
    }
    
    private void notifyDrawingBoardCurrentNullListeners() {
        for (IDrawingBoardChangeListener listener : drawingBoardListeners) {
            listener.syncDrawingBoardCurrentNull();
        }
    }
    
    private void notifyMessageBoardListeners() throws RemoteException {
        for (IMessageBoardChangeListener listener : messageBoardListeners) {
            listener.syncMessageBoard();
        }
    }
    
    private void notifyKickedOutListeners() {
        for (ITerminationListener listener : removedListeners) {
            listener.kickedOut();
        }
    }
    
    private void notifyServerTerminatedListeners() {
        for (ITerminationListener listener : removedListeners) {
            listener.serverTerminated();
        }
    }

    @Override
    public void ping(String message) throws RemoteException {
        Logger.getLogger(WhiteboardClient.class.getName()).log(Level.INFO, "Ping Message : " + message);
    }

}
