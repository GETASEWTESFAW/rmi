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

public abstract class RegularShape extends Shape {
    protected boolean isFilled;
    
    public RegularShape(IClient client, int x1, int y1, int x2, int y2, Color color, boolean isFilled, String shape) throws RemoteException {
        super(client, x1, y1, x2, y2, color, shape);
        this.isFilled = isFilled;
    }
    
    public void setFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }
    
    public boolean isFilled() {
        return this.isFilled;
    }
    
    public int getMinX() {
        return Math.min(this.x1, this.x2);
    }
    
    public int getMinY() {
        return Math.min(this.y1, this.y2);
    }
    
    public int getWidth() {
        return Math.abs(this.x1 - this.x2);
    }
    
    public int getHeight() {
        return Math.abs(this.y1 - this.y2);
    }
    
    @Override
    public abstract void draw(Graphics g);
    
    @Override
    public abstract HashMap<String, Object> getFigureInformation() throws RemoteException;
}

