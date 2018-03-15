/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 *
 * @author biruk
 */
public interface IFigure extends Remote, Serializable {
    String getShape() throws RemoteException;
    Color getColor() throws RemoteException;
    void draw(Graphics g) throws RemoteException;
    IClient getOwner() throws RemoteException;
    HashMap<String, Object> getFigureInformation() throws RemoteException;
}
