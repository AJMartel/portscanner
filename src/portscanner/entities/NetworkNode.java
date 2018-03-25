/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portscanner.entities;

import java.util.ArrayList;

/**
 *
 * @author William Deming
 */
public class NetworkNode {
    private ArrayList<Port> ports = new ArrayList<Port>();
    private String address;
    
    public NetworkNode(ArrayList<Port> ports, String address){
        this.ports = ports;
        this.address = address;
    }
    
    public ArrayList<Port> getPorts(){
        return ports;
    }
    
    public void setPorts(ArrayList<Port> ports){
        this.ports = ports;
    }
    
    public String getAddress(){
        return address;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
   
    public void printContents(){
        System.out.println("  Node " + address);
        for(int i = 0; i < ports.size(); i++){
            System.out.println("    Port: " + ports.get(i).getNumber()  +
                    "\t\tExpected Status: " + ports.get(i).getStatus());
        }
    }
}