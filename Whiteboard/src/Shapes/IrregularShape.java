/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shapes;

import Common.IClient;
import Common.Constants;
import java.awt.Color;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author biruk
 */
public class IrregularShape extends Figure {
    protected ArrayList<Point> points = new ArrayList<>();
    protected int width;
    
    public IrregularShape(IClient owner, int x1, int y1, Color color, int width) throws RemoteException {
        super(owner, x1, y1, color, Constants.Shape_Type_Irregular);
        this.width = width;
        points.add(new Point(x1, y1));
    }
    
    public IrregularShape(IClient owner, int x1, int y1, Color color, int width, ArrayList<Point> points) throws RemoteException {
        super(owner, x1, y1, color, Constants.Shape_Type_Irregular);
        this.width = width;
        this.points = points;
    }
    
    public void addPoint(int X, int Y) {
        points.add(new Point(X, Y));
    }
        
    public ArrayList<Point> getPoints() {
        return this.points;
    }
    
    public int getWidth() {
        return this.width;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        for (Point point : points) {
            g.fillOval(point.getX(), point.getY(), width, width);
        }
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

