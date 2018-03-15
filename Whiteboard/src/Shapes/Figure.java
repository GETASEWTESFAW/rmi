/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shapes;

import Common.IClient;
import Common.IFigure;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 *
 * @author biruk
 */
public abstract class Figure extends UnicastRemoteObject implements IFigure {
    protected int x1;
    protected int y1;
    protected Color color;
    protected String shape;
    protected IClient owner;
    
    public Figure(IClient owner, int x1, int y1, Color color, String shape) throws RemoteException {
        this.owner = owner;
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
        this.shape = shape;
    }
    
    public void setInitialCoordinate(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public int getX1() {
        return this.x1;
    }
    
    public int getY1() {
        return this.y1;
    }

    @Override
    public IClient getOwner() throws RemoteException {
        return this.owner;
    }
    
    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public String getShape() throws RemoteException {
        return shape;
    }
    
    @Override
    public abstract void draw(Graphics g);

    @Override
    public abstract HashMap<String, Object> getFigureInformation() throws RemoteException;
     
}

