/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chats;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServerHandler se encarga de crear el servidor y manejarlo
 * @author kenic
 */
public class ServerHandler implements Runnable{
    @Override
    public void run() {
        ServerSocket server;
        try {
            server = new ServerSocket(8010);
            Envio llegada;
            while (true) {
                    String mensaje,ip;
                    int port;
                    Socket cliente = server.accept();
                    System.out.println("Client Connected"+ "\n");
                    ObjectInputStream din = new ObjectInputStream(cliente.getInputStream());                       
                    llegada = (Envio) din.readObject();
                    String smg = llegada.getMensaje();
                    System.out.println(smg+ "\n");
                    port = llegada.getport();
                    cliente.close();
                    din.close();
                    System.out.println(port);
                    Socket cliente2 = new Socket("127.0.0.1",port);
                    ObjectOutputStream dout = new ObjectOutputStream(cliente2.getOutputStream());
                    dout.writeObject(llegada);                   
                    cliente2.close();
                    //System.out.println(clientCount);

            }
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
}
