/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chats;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kenic
 */
public class ClientHandler implements Runnable{
    int port;
    public ClientHandler(int port){
        this.port = port;
    }
    @Override
    public void run() {   
        System.out.println("Estoy escuchando en el puerto" + port);
        try {
            ServerSocket ss = new ServerSocket(port);
            Envio en;
            while(true){
                Socket s = ss.accept();
                ObjectInputStream din2 = new ObjectInputStream(s.getInputStream());
                en = (Envio) din2.readObject();
                System.out.println("mensaje recibido: " + en.getMensaje());
                s.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e){
            e.getMessage();
        }   
        
        
    }
    
}

class Envio implements Serializable {
    private String message, nombre;
    private int port;
    
    public String getMensaje(){
        return message;
    }
    
    public int getport(){
        return port;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setMensaje(String message){
        this.message = message;
    }
    
    public void setport(int port){
        this.port = port;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
} 