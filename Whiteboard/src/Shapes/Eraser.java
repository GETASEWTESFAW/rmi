/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shapes;

import Common.Constants;
import Common.IClient;
import java.awt.Color;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author biruk
 */
public class Eraser extends Figure {
    protected ArrayList<Point> points = new ArrayList<>();
    protected int width;
    
    public Eraser(IClient owner, int x1, int y1, int width) throws RemoteException {
        super(owner, x1, y1, Color.WHITE, Constants.Shape_Type_Eraser);
        this.points.add(new Point(x1, y1));
        this.width = width;
    }
    
    public Eraser(IClient owner, int x1, int y1, int width, ArrayList<Point> points) throws RemoteException {
        super(owner, x1, y1, Color.WHITE, Constants.Shape_Type_Eraser);
        this.points = points;
        this.width = width;
    }
    
    public void addPoint(int x, int y) {
        this.points.add(new Point(x, y));
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        for (Point point : points) {
            g.fillRect(point.getX(), point.getY(), width, width);
        }
    }
    
    public ArrayList<Point> getPoints() {
        return this.points;
    }
    
    public int getWidth() {
        return this.width;
    }

    @Override
    public HashMap<String, Object> getFigureInformation() throws RemoteException {
        HashMap<String, Object> informationMap = new HashMap<>();
        informationMap.put("X1", getX1());
        informationMap.put("Y1", getY1());
        informationMap.put("Color", getColor());
        informationMap.put("Points", getPoints());
        informationMap.put("Width", getWidth());
        informationMap.put("Owner", getOwner());
        return informationMap;
    }
}
