/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.rmi.RemoteException;

/**
 *
 * @author biruk
 */
public interface IMessageBoardChangeListener {
    void syncMessageBoard() throws RemoteException;
}
