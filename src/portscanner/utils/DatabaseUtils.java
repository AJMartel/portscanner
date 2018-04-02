/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portscanner.utils;

import java.sql.*;
import java.util.ArrayList;
import portscanner.entities.NetworkNode;
import portscanner.entities.Port;


/**
 *
 * @author William Deming
 */
public class DatabaseUtils {
    public DatabaseUtils(){
        
    }
    
    public void checkDatabase(){
        try{
            System.out.println("Checking if database exists...");
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=Default1!");
            Statement st = connection.createStatement();
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS portscan;");
            connection.close();
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost/portscan?user=root&password=Default1!");
            st = connection.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS computers(id INT unsigned AUTO_INCREMENT, ip VARCHAR(15), network VARCHAR(20), PRIMARY KEY (id))");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS ports(ip VARCHAR(15), port INT(7), status VARCHAR(8), expected_status VARCHAR(8))");
            connection.close();
        }catch(Exception e){ 
            System.out.println(e);
        }
    }

    //Returns all computers from the computers table
    public ArrayList<NetworkNode> getAllComputers(){
        ArrayList<NetworkNode> computers = new ArrayList<NetworkNode>();
        String ip = null;
        String network = null;
        
        try{
            
            System.out.println("Getting computers...");
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/portscan?user=root&password=Default1!");
            String query = "SELECT * FROM computers";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
      
            // iterate through the java resultset
            while (rs.next()){
                ip = rs.getString("ip");
                network = rs.getString("network");
                computers.add(new NetworkNode(ip, network));
            }
            connection.close();
            
        }catch(Exception e){ 
            System.out.println(e);
        }
        
        return computers;
    }
    
    //Returns all ports for a computer from the ports table
    public ArrayList<Port> getAllPorts(String ip){
        ArrayList<Port> ports = new ArrayList<Port>();
        int number = 0;
        String status = null;
        String expected_status = null;
        
        try{
            
            System.out.println("Getting ports for ip " + ip + "...");
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/portscan?user=root&password=Default1!");
            String query = "SELECT * FROM ports WHERE ip = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString (1, ip);
            ResultSet rs = ps.executeQuery(query);
      
            // iterate through the java resultset
            while (rs.next()){
                number = rs.getInt("port");
                status = rs.getString("status");
                expected_status = rs.getString("expected_status");
                ports.add(new Port(number, status, expected_status));
            }
            connection.close();
            
        }catch(Exception e){ 
            System.out.println(e);
        }
        
        return ports;
    }
    
    //Inserts a computer into the computer table
    public void insertComputer(String ip, String network){
        try{
            
            System.out.println("Inserting computer...");
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/portscan?user=root&password=Default1!");
            String query = " insert into computers (ip, network)"
                    + " values (?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString (1, ip);
            ps.setString (2, network);
            ps.execute();
            connection.close();
            
        }catch(Exception e){ 
            System.out.println(e);
        }
    }
    
    //Inserts a port into the ports table
    public void insertPort(String ip, int port, String status, String expected_status){
        try{
            
            System.out.println("Inserting port...");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/portscan?user=root&password=Default1!");
            String query = " insert into ports (ip, port, status, expected_status)"
                    + " values (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString (1, ip);
            ps.setInt (2, port);
            ps.setString (3, status);
            ps.setString (4, expected_status);
            ps.execute();
            connection.close();
            
        }catch(Exception e){ 
            System.out.println(e);
        }
    }  
    
    //Updates the status of a port in the ports table
    public void updatePortStatus(String ip, String status){
        try{
            
            System.out.println("Updating port status...");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/portscan?user=root&password=Default1!");
            String query = "update ports set status = ? where ip = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString (1, status);
            ps.setString (2, ip);
            ps.executeUpdate();
            connection.close();
            
        }catch(Exception e){ 
            System.out.println(e);
        }
    }
    
    public void testAddComputers(){
        insertComputer("10.0.8.11", "Network A");
        insertComputer("10.0.8.12", "Network A");
        insertComputer("10.0.200.220", "Network B");
        insertComputer("10.0.50.30", "Network C");
        insertComputer("10.0.50.32", "Network C");
        insertComputer("10.0.50.33", "Network C");
        insertComputer("10.0.50.37", "Network C");
        
    }
    
    public void testAddPorts(){
        insertPort("10.0.8.11", 21, "closed", "closed");
        insertPort("10.0.8.11", 22, "closed", "closed");
        insertPort("10.0.8.11", 23, "open", "closed");
        insertPort("10.0.8.12", 79, "closed", "closed");
        insertPort("10.0.8.12", 80, "filtered", "closed");
        insertPort("10.0.50.30", 21, "closed", "closed");
        insertPort("10.0.50.30", 22, "closed", "closed");
        insertPort("10.0.50.30", 23, "closed", "open");
        insertPort("10.0.50.30", 100, "filtered", "filtered");
        insertPort("10.0.50.30", 443, "open", "open");
    }
}
