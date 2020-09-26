
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
        

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kenic
 */
public class Server extends javax.swing.JFrame {
    /**
     * Creates new form Server
     */
    ServerSocket ss;
    HashMap clientColl = new HashMap();
    
    public Server() {
        try{
        initComponents();
            ss = new ServerSocket(2089);
            this.sStatus.setText("Server Started.");
            
            new ClientAccept().start();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    class ClientAccept extends Thread{
        
        public void run() {
            while(true) {
                try {
                    Socket s = ss.accept();
                    String i = new DataInputStream(s.getInputStream()).readUTF();
                    if(clientColl.containsKey(i)){
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("You Are Already Registered....!!");                        
                    } else {
                        clientColl.put(i,s);
                        msgBox.append(i+" Joined!\n");
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("");
                        new MsgRead(s,i).start();
                        new PrepareClientList().start();
                    }                
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
    class MsgRead extends Thread {
        
        Socket s;
        String ID;
        
        MsgRead(Socket s, String ID) {
            this.s = s;
            this.ID = ID;
        }
        
        public void run(){
            while(!clientColl.isEmpty()){
                try{
                    String i = new DataInputStream(s.getInputStream()).readUTF();
                    if(i.equals("mkoihgteazdcvgyhujb096785542AXTY")){
                        clientColl.remove(ID);
                        msgBox.append(ID+": removed! \n");
                        new PrepareClientList().start();                        
                        Set k = clientColl.keySet();
                        Iterator itr = k.iterator();
                        while(itr.hasNext()){
                            String key = (String)itr.next();
                            if(!key.equalsIgnoreCase(ID)){
                                try{
                                new DataOutputStream(((Socket)clientColl.get(key)).getOutputStream()).writeUTF(ID+": LEFT CHAT!");
                                } catch (Exception ex) {
                                    clientColl.remove(key);
                                    msgBox.append(key+": removed!");
                                    new PrepareClientList().start();
                                }
                            }                      
                        }
             
                    }
                    else if(i.contains("#4344554@@@@@67667@@")){
                        i = i.substring(20);
                        StringTokenizer st = new StringTokenizer(i,":");
                        String id = st.nextToken();
                        i = st.nextToken();
                        try{
                            new DataOutputStream(((Socket)clientColl.get(id)).getOutputStream()).writeUTF("< "+ID+" to "+id+" > "+i);
                        } catch (Exception ex) {
                            clientColl.remove(id);
                            msgBox.append(id+": removed!");
                            new PrepareClientList().start();
                        }
                    }
                    else{
                    Set k=clientColl.keySet();
                    Iterator itr = k.iterator();
                        while(itr.hasNext()){
                            String key = (String)itr.next();
                            if(!key.equalsIgnoreCase(ID)){
                                try{
                                new DataOutputStream(((Socket)clientColl.get(key)).getOutputStream()).writeUTF("< "+ID+" to All > "+i);
                                } catch (Exception ex) {
                                    clientColl.remove(key);
                                    msgBox.append(key+": removed!");
                                    new PrepareClientList().start();
                                }
                            }
                        }
                    }
                
                    
                    
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    
    }
    class PrepareClientList extends Thread{
        
        public void run(){
            try {
                String ids = "";
                Set k = clientColl.keySet();
                Iterator itr = k.iterator();
                while(itr.hasNext()){
                    String key = (String)itr.next();
                    ids += key+",";
                }
                if(ids.length()!=0)
                    ids = ids.substring(0, ids.length()-1);
                    itr = k.iterator();
                    while(itr.hasNext()){
                        String key = (String)itr.next();
                        try{
                            new DataOutputStream(((Socket)clientColl.get(key)).getOutputStream()).writeUTF(":;.,/="+ids);                            
                        } catch (Exception ex) {
                            clientColl.remove(key);
                            msgBox.append(key+": removed!");
                        }
                    }
                    
                
            
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
                        
        }
    }        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgBox = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        sStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyServer");

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        msgBox.setColumns(20);
        msgBox.setRows(5);
        jScrollPane1.setViewportView(msgBox);

        jLabel1.setBackground(new java.awt.Color(102, 102, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Server Status");

        sStatus.setText("...............................");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(49, 49, 49)
                        .addComponent(sStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msgBox;
    private javax.swing.JLabel sStatus;
    // End of variables declaration//GEN-END:variables
}
