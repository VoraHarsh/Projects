
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
public class JavaConnect1 {
     Connection conn1=null;
    public static Connection ConnecrDb1(){
        
        
         try{
            Class.forName("org.sqlite.JDBC");
           
            Connection conn1=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Harsh Vora\\eclipse-workspace\\MovieBookingSystem\\moviedatabase.sqlite");
             JOptionPane.showMessageDialog(null,"Connection Established");
             return conn1;
           
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    
    
    }

}
