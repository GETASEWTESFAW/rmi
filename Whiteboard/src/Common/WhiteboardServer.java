/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import Shapes.Eraser;
import Shapes.Figure;
import Shapes.IrregularShape;
import Shapes.Line;
import Shapes.Oval;
import Shapes.Point;
import Shapes.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author biruk
 */
public class WhiteboardServer extends UnicastRemoteObject implements IServer {
    private LinkedList<IFigure>  figures;
    private LinkedList<Message> messages;
    private ClientManager clientManager;
    private IClient administrator;

    private Semaphore figureSemaphore;
    
    private ArrayList<IClientsListChangeListener> clientsListChangedListeners;
    private ArrayList<IServerTerminationReadyListener> terminationListeners;
    
    public WhiteboardServer() throws RemoteException {
        this.clientManager = new ClientManager();
        this.figures = new LinkedList<>();
        this.messages = new LinkedList<>();
        this.clientsListChangedListeners = new ArrayList<>();
        this.terminationListeners = new ArrayList<>();
        this.figureSemaphore = new Semaphore(1);      
    }
    
    @Override
    public void addFigure(IFigure figure) throws RemoteException {
        try {
            figureSemaphore.acquire();
            this.figures.addFirst(figure);
            syncClientsBoard();
            Logger.getLogger(WhiteboardServer.class.getName()).log(Level.INFO, String.format("%s added by %s.\nNumber of Figures : %d", figure.getShape(), figure.getOwner().getName(), figures.size()));
        }
        catch (InterruptedException ex) {
            Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, null, ex);
        }        
        finally {
            figureSemaphore.release();
        }
        
    }

    @Override
    public void undo(IClient client) throws RemoteException {
        try {
            figureSemaphore.acquire();
            for (IFigure figure: figures) {
                if (figure.getOwner().getName().equals(client.getName())) {
                    removeFigure(figure);
                    cleanAndRefreshClientsBoard();
                }
            }
        }
        catch (InterruptedException ex) {
            Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, null, ex);
        }  
        catch (Exception ex) {
            Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            figureSemaphore.release();
        }
    }


    @Override
    public LinkedList<IFigure> getFigures() throws RemoteException {
        return this.figures;
    }

    @Override
    public void broadcastMessage(IClient client, String message) throws RemoteException {
        this.messages.add(new Message(client, message));
        syncClientsMessageBoard();
       
    }
    
    @Override
    public LinkedList<Message> getMessages() throws RemoteException {
        return this.messages;
    }

    @Override
    public IHandler registerClient(IClient client) throws RemoteException {
        if (this.clientManager.contains(client)) {
            return null;
        }
        else {
            this.clientManager.addClient(client);
            notifyClientsListChange();
            Logger.getLogger(WhiteboardServer.class.getName()).log(Level.INFO, "Client Registered : " + client.getName());
            return new Handler(this, client);
        }
    }

    @Override
    public void kickoutClient(String username) throws RemoteException {
        for (IClient client : this.clientManager.getClients()) {
            if (client.getName().equals(username)) {
                client.removeClient();
                this.clientManager.removeClient(client);
                notifyClientsListChange();
                Logger.getLogger(WhiteboardServer.class.getName()).log(Level.INFO, "Client " + username + " Removed.");
            }
        }        
    }

    @Override
    public void disconnectCient(String username) throws RemoteException {
        for (IClient c : this.clientManager.getClients()) {
            if (c.getName().equals(username)) {
                this.clientManager.removeClient(c);
                notifyClientsListChange();
                Logger.getLogger(WhiteboardServer.class.getName()).log(Level.INFO, "Client " + username + " Disconnected.");
            }
        }
    }
    
    
    @Override
    public void validateClientsList() throws RemoteException {
        for (IClient client : this.clientManager.getClients()) {
            String message = "Pinging " + new Date().toString();
            try {
                client.ping(message);
            }
            catch (ConnectException e) {
                this.clientManager.removeClient(client);
            }
        }
        notifyClientsListChange();
    }

    @Override
    public LinkedList<IClient> getClients() throws RemoteException {
        return this.clientManager.getClients();
    }

    @Override
    public IHandler registerAdmin(IClient client) throws RemoteException {
        if (this.administrator != null) {
            throw new IllegalStateException("Only one Administrator is Allowed!");
        }
        else {
            this.administrator = client;
            Logger.getLogger(WhiteboardServer.class.getName()).log(Level.INFO, "Adminisrator registered : " + administrator.getName());
            return new Handler(this, administrator);
        }
    }

    @Override
    public IClient getAdministrator() throws RemoteException {
        return this.administrator;
    }

    @Override
    public int requestMembership(IClient client) throws RemoteException {
     return JOptionPane.showConfirmDialog(null, "Request recieved from " + client.getName() + ". Do You Approve?", "Request Recieved", JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void addClientListChangeListener(IClientsListChangeListener listener) throws RemoteException {
        clientsListChangedListeners.add(listener);
    }

    @Override
    public void newBoard() throws RemoteException {
       
        figures.clear();
        cleanAndRefreshClientsBoard();
    }

    @Override
    public void openBoard(ObjectInputStream inputStream) throws RemoteException {
        figures = new LinkedList<>();
        try {
            while (true) {
                IFigure figure = (IFigure) inputStream.readObject();
                figures.add(figure);
            }
        }
        catch (EOFException ex) {
            syncClientsBoard();
        }
        catch (IOException ex) {
        Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) {
           
        }
        
    }

    @Override
    public void saveBoard(ObjectOutputStream outputStream) throws RemoteException {
        try {
            for (IFigure figure : figures) {
                IFigure newFigure = constructLocalFigure(figure);
                outputStream.writeObject(newFigure);
            }
        } catch (IOException ex) {
            Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addTerminationListener(IServerTerminationReadyListener listener) throws RemoteException {
        terminationListeners.add(listener);
    }
    
    @Override
    public void close() throws RemoteException {
        for (IClient client: clientManager.getClients()) {
            client.serverTerminated();
        }
        notifyTerminationListeners();
    }
    
    private void notifyTerminationListeners() {
        for (IServerTerminationReadyListener listener : terminationListeners) {
            listener.terminate();
        }
    }
    
    private void removeFigure(IFigure figure) {
        Iterator<IFigure> iterator = figures.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(figure)) {
                iterator.remove();
            }
        }
    }
    
    private void syncClientsBoard() {
        for (IClient client : clientManager.getClients()) {
            try {
                client.syncDrawingBoard();
            }
            catch (ConnectException e) {
                try {
                    validateClientsList();
                } catch (RemoteException ex) {
                    Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            catch (RemoteException e) {
                Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, "RemoteException While Syncing Clients: " + e.getMessage());
            }  
        }
        try {
            administrator.syncDrawingBoard();
        }
        catch (RemoteException e) {
            Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, "RemoteException While Syncing Server: " + e.getMessage());
        }
    }
    
    private void cleanAndRefreshClientsBoard() {
        for (IClient client : clientManager.getClients()) {
            try {
                client.cleanAndRefreshDrawingBoard();
            }
            catch (ConnectException e) {
                try {
                    validateClientsList();
                } catch (RemoteException ex) {
                    Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            catch (RemoteException e) {
                Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, "RemoteException While Syncing Clients: " + e.getMessage());
            }  
        }
        try {
            administrator.cleanAndRefreshDrawingBoard();
        }
        
        catch (RemoteException e) {
            Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, "RemoteException While Syncing Server: " + e.getMessage());
        }
    }
    
    private void syncClientsMessageBoard() {
        for (IClient client : clientManager.getClients()) {
            try {
                client.syncMessageBoard();
            }
            catch (ConnectException e) {
                try {
                    validateClientsList();
                } catch (RemoteException ex) {
                    Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            catch (RemoteException e) {
                Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, "RemoteException While Syncing Clients: " + e.getMessage());
            }  
        }
        try {
            administrator.syncMessageBoard();
        }
        catch (RemoteException e) {
            Logger.getLogger(WhiteboardServer.class.getName()).log(Level.SEVERE, "RemoteException While Syncing Server: " + e.getMessage());
        }
    }
    
    private void notifyClientsListChange() {
        for (IClientsListChangeListener listener : clientsListChangedListeners) {
            listener.clientsListChanged();
        }
    }
    
    private IFigure constructLocalFigure(IFigure figure) throws RemoteException {
        IFigure newFigure = null;
        HashMap<String, Object> information;
        
        switch (figure.getShape()) {
            case Constants.Shape_Type_Line:
                information = figure.getFigureInformation();
                newFigure = new Line(null, (int)information.get("X1"), (int)information.get("Y1"), (int)information.get("X2"), (int)information.get("Y2"), (Color)information.get("Color"));
                break;
            case Constants.Shape_Type_Rectangle:
                information = figure.getFigureInformation();
                newFigure = new Rectangle(null, (int)information.get("X1"), (int)information.get("Y1"), (int)information.get("X2"), (int)information.get("Y2"), (Color)information.get("Color"), (boolean)information.get("isFilled"));
                break;
            case Constants.Shape_Type_Oval:
                information = figure.getFigureInformation();
                newFigure = new Oval(null, (int)information.get("X1"), (int)information.get("Y1"), (int)information.get("X2"), (int)information.get("Y2"), (Color)information.get("Color"), (boolean)information.get("isFilled"));
                break;
            case Constants.Shape_Type_Irregular:
                information = figure.getFigureInformation();
                newFigure = new IrregularShape(null, (int)information.get("X1"), (int)information.get("Y1"), (Color)information.get("Color"), (int)information.get("Width"), (ArrayList<Point>)information.get("Points"));
                break;
            case Constants.Shape_Type_Eraser:
                information = figure.getFigureInformation();
                newFigure = new Eraser(null, (int)information.get("X1"), (int)information.get("Y1"), (int)information.get("Width"), (ArrayList<Point>)information.get("Points"));
                break;
        }        
        return newFigure;
    }
    
}
