/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package socialmediaapplication;

import java.sql.Connection;
import java.sql.DriverManager;


public class dbConnection {
    public static Connection dbconnect()
    { 
        try
        {
       Class.forName("com.mysql.cj.jdbc.Driver");
       Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/socialmediaapp","root","Mitachi457");
            System.out.println("connected");
            return conn;
        }
        catch(Exception e)
        {
            System.out.println("error");
            return null;
        }
        
    }
    public static void main(String[] args) {
        dbconnect();
        System.out.println("run");
                
    }
}
