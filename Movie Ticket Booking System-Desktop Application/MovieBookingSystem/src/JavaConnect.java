/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harsh
 */

import java.sql.*;
import javax.swing.*;

public class JavaConnect {
    Connection conn=null;
    Connection conn1=null;
    public static Connection ConnecrDb()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Harsh Vora\\eclipse-workspace\\MovieBookingSystem\\UserDatabase.sqlite");
         //   Connection conn1=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Harsh\\Documents\\NetBeansProjects\\MovieBookingSystem\\moviedatabase.sqlite");
             JOptionPane.showMessageDialog(null,"Connection Established");
             return conn;
             
           
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
            return null;
        }

    
    
    }
}
