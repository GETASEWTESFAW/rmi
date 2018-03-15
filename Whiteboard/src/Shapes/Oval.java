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
import java.util.HashMap;

/**
 *
 * @author biruk
 */

public class Oval extends RegularShape {
    public Oval(IClient client, int x1, int y1, int x2, int y2, Color color, boolean isFilled) throws RemoteException {
        super(client, x1, y1, x2, y2, color, isFilled, Constants.Shape_Type_Oval);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        if (this.isFilled()) {
            g.fillOval(getMinX(), getMinY(), getWidth(), getHeight());
        }
        else {
            g.drawOval(getMinX(), getMinY(), getWidth(), getHeight());
        }
    }

    @Override
    public HashMap<String, Object> getFigureInformation() throws RemoteException {
        HashMap<String, Object> informationMap = new HashMap<>();
        informationMap.put("X1", getX1());
        informationMap.put("Y1", getY1());
        informationMap.put("X2", getX2());
        informationMap.put("Y2", getY2());
        informationMap.put("Color", getColor());
        informationMap.put("Owner", getOwner());
        informationMap.put("isFilled", isFilled());
        return informationMap;
    }
    
    
}
