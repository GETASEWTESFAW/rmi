/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author biruk
 */
public class Constants {
    public static final String Shape_Type_Line = "Line";
    public static final String Shape_Type_Rectangle = "Rectangle";
    public static final String Shape_Type_Oval = "Oval";
    public static final String Shape_Type_Text = "Text";
    public static final String Shape_Type_Irregular = "Pen";
    public static final String Shape_Type_Eraser = "Eraser";
    
    public static final int Pending_Client_isAccepted = 1;
    public static final int Pending_Client_isRejected = 0;
    public static final int Pending_Client_isWaiting = -1;
    
    public static final int Maximum_Number_of_Pending_Clients = 50;
    
    public static final int User_Type_Client = 0;
    public static final int User_Type_Administrator = 1;
    
    public static final String No_Clients_Message = "No Clients.";
    
    public static final int Vertical_Offset_Irregular_Shape=-15;
    public static final int Horizontal_Offset_Irregular_Shape=10;
    
    public static final int Eraser_Size = 10;
    
    public static final String Client_Removed_Message = "You have been Kicked Out By The Administrator.";
    public static final String Server_Terminated_Message = "Server Terminated.";
}
