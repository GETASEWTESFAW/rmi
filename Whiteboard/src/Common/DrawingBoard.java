/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import Client.ClientUI;
import Shapes.Eraser;
import Shapes.IrregularShape;
import Shapes.Line;
import Shapes.Oval;
import Shapes.Point;
import Shapes.Rectangle;
import Shapes.Shape;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author biruk
 */
public class DrawingBoard extends JPanel implements IDrawingBoardChangeListener, IMessageBoardChangeListener, ITerminationListener {
    private String shapeType;
    private IFigure figure;
    private Color shapeColor;
    private Font font;
    private boolean shapeIsFilled;   
    private JFrame board;
 
    private LinkedList<IFigure> figures;
    private ArrayList<INotificationListener> notificationListeners;
    
    private IHandler handler;
    private IClient client;
    private IServer server;
    
    private String username;
    private String hostname;
    private int userType;
    
    public DrawingBoard(JFrame board, String username, String hostname) throws RemoteException, NotBoundException, MalformedURLException {
        this.shapeType = Constants.Shape_Type_Line;
        this.figure = null;
        this.shapeColor = Color.BLACK;
        this.shapeIsFilled = false;
        
        this.username = username;
        this.hostname = hostname;
        this.userType = userType;
        this.board = board;
        
        this.notificationListeners = new ArrayList<>();
        
        try {
            
                server = new WhiteboardServer();
                Registry registry = LocateRegistry.createRegistry(1090);
                registry.rebind("hello", server);
                client = new WhiteboardClient(username);
                client.addDrawingBoardChangeListener(this);
                client.addMessageBoardChangeListener(this);
                handler = server.registerAdmin(client);
                          
        }
        catch (ConnectException ex) {
            JOptionPane.showMessageDialog(null, "Connection Failed", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        
        this.figures = handler.getFigures();
        setBackground(Color.WHITE);
        
        DrawingPanelMouseEventHandler mouseHandler = new DrawingPanelMouseEventHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

   
    public IHandler getHandler() {
        return this.handler;
    }
    
    public void addNotificationListener(INotificationListener listener) {
        this.notificationListeners.add(listener);
    }
    
    public void setShapeFilled(boolean shapeIsFilled) {
        this.shapeIsFilled = shapeIsFilled;
    }
    
    public void setShapeColor(Color color) {
        this.shapeColor = color;
    }
    
    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
        
        if (this.shapeType == Constants.Shape_Type_Eraser) {
            setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("src/Icons/Eraser.png").getImage(), new java.awt.Point(0, 0), "Eraser Cursor"));
        }
        else if (this.shapeType == Constants.Shape_Type_Irregular) {
            setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("src/Icons/Pen.png").getImage(), new java.awt.Point(0, 0), "Pen Cursor"));
        }
        else if (this.shapeType == Constants.Shape_Type_Line || this.shapeType == Constants.Shape_Type_Rectangle || this.shapeType == Constants.Shape_Type_Oval) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
        else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    public void setTextFont(Font font) {
        this.font = font;
    }
    
    public void undo() {
        try {
            IFigure figure = this.figures.removeFirst();
            System.out.println(figure.getShape());
            repaint();
        }
        catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NoSuchElementException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public IFigure constructLocalFigure(IFigure figure) throws RemoteException {
        IFigure newFigure = null;
        HashMap<String, Object> information;
        
        switch (figure.getShape()) {
            case Constants.Shape_Type_Line:
                information = figure.getFigureInformation();
                newFigure = new Line((IClient)information.get("Owner"), (int)information.get("X1"), (int)information.get("Y1"), (int)information.get("X2"), (int)information.get("Y2"), (Color)information.get("Color"));
                break;
            case Constants.Shape_Type_Rectangle:
                information = figure.getFigureInformation();
                newFigure = new Rectangle((IClient)information.get("Owner"), (int)information.get("X1"), (int)information.get("Y1"), (int)information.get("X2"), (int)information.get("Y2"), (Color)information.get("Color"), (boolean)information.get("isFilled"));
                break;
            case Constants.Shape_Type_Oval:
                information = figure.getFigureInformation();
                newFigure = new Oval((IClient)information.get("Owner"), (int)information.get("X1"), (int)information.get("Y1"), (int)information.get("X2"), (int)information.get("Y2"), (Color)information.get("Color"), (boolean)information.get("isFilled"));
                break;
            case Constants.Shape_Type_Irregular:
                information = figure.getFigureInformation();
                newFigure = new IrregularShape((IClient)information.get("Owner"), (int)information.get("X1"), (int)information.get("Y1"), (Color)information.get("Color"), (int)information.get("Width"), (ArrayList<Point>)information.get("Points"));
                break;
            case Constants.Shape_Type_Eraser:
                information = figure.getFigureInformation();
                newFigure = new Eraser((IClient)information.get("Owner"), (int)information.get("X1"), (int)information.get("Y1"), (int)information.get("Width"), (ArrayList<Point>)information.get("Points"));
                break;
        }        
        return newFigure;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            figures = handler.getFigures();
            for (int i = figures.size()-1; i >= 0; i--) {
                if (figures.get(i) != null)
                    constructLocalFigure(figures.get(i)).draw(g);
            }

            if (figure != null)
                figure.draw(g);
        }
        
        catch (NullPointerException ex) {
            
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errors", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ConnectException ex) {
            JOptionPane.showMessageDialog(null, "Connection Failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void syncDrawingBoard() {
        repaint();
    }
    
    @Override
    public void syncDrawingBoardCurrentNull() {
        figure = null;
        repaint();
    }

    @Override
    public void syncMessageBoard() {
        for (INotificationListener listener : notificationListeners) {
            listener.syncMessageBoard();
        }
    }

    @Override
    public void kickedOut() {
        for (INotificationListener listener : notificationListeners) {
            listener.kickedOut();
        }
    }

    @Override
    public void serverTerminated() {
        for (INotificationListener listener : notificationListeners) {
            listener.serverTerminated();
        }
    }

    private class DrawingPanelMouseEventHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            try {
                switch (shapeType) {
                    case Constants.Shape_Type_Line:
                        figure = new Line(client, e.getX(), e.getY(), e.getX(), e.getY(), shapeColor);
                        break;
                    case Constants.Shape_Type_Rectangle:
                        figure = new Rectangle(client, e.getX(), e.getY(), e.getX(), e.getY(), shapeColor, shapeIsFilled);
                        break;
                    case Constants.Shape_Type_Oval:
                        figure = new Oval(client, e.getX(), e.getY(), e.getX(), e.getY(), shapeColor, shapeIsFilled);
                        break;
                    case Constants.Shape_Type_Irregular:
                        figure = new IrregularShape(client, e.getX()-Constants.Horizontal_Offset_Irregular_Shape, e.getY()-Constants.Vertical_Offset_Irregular_Shape, shapeColor, 5);
                        break;
                    case Constants.Shape_Type_Eraser:
                        figure = new Eraser(client, e.getX(), e.getY(), Constants.Eraser_Size);
                        repaint();
                        break;
                }
            }
            catch (ConnectException ex) {
                JOptionPane.showMessageDialog(null, "Connection Failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            if (shapeType.equals(Constants.Shape_Type_Line) || shapeType.equals(Constants.Shape_Type_Rectangle) || shapeType.equals(Constants.Shape_Type_Oval)) {
                ((Shape)figure).setFinalCoordinates(e.getX(), e.getY());
                try {
                    handler.addFigure(figure);
                } 
                catch (ConnectException ex) {
                    JOptionPane.showMessageDialog(null, "Connection Failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            else if (shapeType.equals(Constants.Shape_Type_Irregular) || shapeType.equals(Constants.Shape_Type_Eraser)) {
                try {
                    handler.addFigure(figure);
                } 
                catch (ConnectException ex) {
                    JOptionPane.showMessageDialog(null, "Connection Failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(DrawingBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            repaint();
        }
        
        @Override
        public void mouseMoved(MouseEvent e) {
            
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            if (shapeType.equals(Constants.Shape_Type_Line) || shapeType.equals(Constants.Shape_Type_Rectangle) || shapeType.equals(Constants.Shape_Type_Oval)) {
                ((Shape)figure).setFinalCoordinates(e.getX(), e.getY());
            }
            else if (shapeType.equals(Constants.Shape_Type_Irregular)) {
                ((IrregularShape)figure).addPoint(e.getX()-Constants.Horizontal_Offset_Irregular_Shape, e.getY()-Constants.Vertical_Offset_Irregular_Shape);
            }
            else if (shapeType.equals(Constants.Shape_Type_Eraser)) {
                ((Eraser)figure).addPoint(e.getX(), e.getY());
            }
            repaint();
        }
    }
}
