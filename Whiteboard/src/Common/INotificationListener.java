/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.LinkedList;

/**
 *
 * @author biruk
 */
public interface INotificationListener {
    void syncMessageBoard();
    void kickedOut();
    void serverTerminated();
}
