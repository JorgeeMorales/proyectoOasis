package org.utl.oasis.ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author pasit
 */
public class ConexionBD {
    Connection conn;
    
    public Connection open(){
        String user = "root";
        String password = "12345678";
        String url = "jdbc:mysql://127.0.0.1:3306/oasis?useSSL=false&"
                +"allowPublicKeyRetrieval=true&"
                +"useUnicode=true&characterEncoding=utf-8";
        
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
    }
    
    public void close()
    {
        if (conn!= null) 
        {
            try {
                conn.close();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                System.out.println("Exception controlada.");
            }
        }
    }
}
