/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Date;

/**
 *
 * @author biruk
 */
public class Message implements Serializable {
    private IClient client;
    private String message;
    private Date date;
    
    public Message(IClient client, String message) {
        this.client = client;
        this.message = message;
        this.date = new Date();
    }
    
    public String getMessage() throws RemoteException {
        return String.format("%s : %s\n\n", client.getName(), message);
    }
}
