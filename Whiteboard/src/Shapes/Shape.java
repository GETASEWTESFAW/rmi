/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shapes;

import Common.IClient;
import java.awt.Color;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 *
 * @author biruk
 */
public abstract class Shape extends Figure {
    protected int x2;
    protected int y2;
    
    public Shape(IClient owner, int x1, int y1, int x2, int y2, Color color, String shape) throws RemoteException {
        super(owner, x1, y1, color, shape);
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public void setFinalCoordinates(int x2, int y2) {
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public int getX2() {
        return this.x2;
    }
    
    public int getY2() {
        return this.y2;
    }
    
    
    @Override
    public abstract void draw(Graphics g);

    @Override
    public abstract HashMap<String, Object> getFigureInformation() throws RemoteException;
    
    
}
