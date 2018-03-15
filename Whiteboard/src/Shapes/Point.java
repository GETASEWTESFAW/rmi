/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shapes;

import java.io.Serializable;

/**
 *
 * @author biruk
 */
public class Point implements Serializable {
    private int X;
    private int Y;

    public Point(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }
    
    public int getX() {
        return this.X;
    }
    
    public int getY() {
        return this.Y;
    }
}
