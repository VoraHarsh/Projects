
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harsh
 */
public class JavaConnect2 {

   
    
     Connection conn2=null;
    public static Connection ConnecrDb2(){
        
        
         try{
            Class.forName("org.sqlite.JDBC");
           
            Connection conn2=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Harsh Vora\\eclipse-workspace\\MovieBookingSystem\\Ticketdatabase.sqlite");
             JOptionPane.showMessageDialog(null,"Connection Established");
             return conn2;
           
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    
    
    }
    
}
