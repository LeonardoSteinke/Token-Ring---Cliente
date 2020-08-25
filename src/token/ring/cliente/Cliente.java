package token.ring.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leona
 */
public class Cliente extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    //Processo process;
    Socket socket;
    ServerSocket server;
    int processNumber;
    int PORTNUMBER;
    String TOKEN;
    String content = null;
    String contentReceived = null;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Cliente() throws IOException {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        //process = new Processo();
        try {
            socket = new Socket("localhost", 5555);
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            processNumber = entrada.readInt();
            PORTNUMBER = entrada.readInt();
            System.out.println("Porta: " + PORTNUMBER);

            setTitle("Processo " + processNumber);
            entrada.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        server = new ServerSocket(PORTNUMBER);
        escutaServidor listen = new escutaServidor();
        listen.start();
    }

    private class escutaServidor extends Thread {

        public escutaServidor() {

        }

        @Override
        public void run() {
            while (true) {
                System.out.println("Aguardando conexão...");
                try (Socket conn = server.accept();) {
                    System.out.println("Conectado com: " + conn.getInetAddress().getHostName());
                    ObjectInputStream entrada = new ObjectInputStream(conn.getInputStream());
                    TOKEN = entrada.readUTF();
                    lblToken.setText("true");
                    try {
                        contentReceived = entrada.readUTF();
                        lblReceivedContent.setText(contentReceived);

                    } catch (Exception e) {

                    }
                    entrada.close();
                    conn.close();
                } catch (Exception e) {

                }
                try {
                    Thread.sleep(500);
                    try {
                        socket = new Socket("localhost", (PORTNUMBER + 1));
                    } catch (Exception e) {
                        try {
                            socket = new Socket("localhost", (PORTNUMBER + 2));
                        } catch (Exception ex) {
                            socket = new Socket("localhost", 5556);
                        }
                    }
                    ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
                    saida.writeUTF(TOKEN);
                    TOKEN = null;
                    lblToken.setText("false");
                    if (contentReceived != null) {
                        saida.writeUTF(contentReceived);

                        lblReceivedContent.setText("");
                        contentReceived = null;
                    } else if (content != null) {
                        saida.writeUTF(content);

                        lblContent.setText("");
                        content = null;
                    }
                    saida.flush();
                    saida.close();
                    socket.close();
                } catch (Exception e) {
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        txtConteudo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblToken = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblContent = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblReceivedContent = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Token:");

        lblToken.setText("false");

        jLabel3.setText("Content:");

        jLabel5.setText("ReceivedContent:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtConteudo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblReceivedContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblToken, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(lblToken, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblReceivedContent, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtConteudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(81, 81, 81))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setContent(txtConteudo.getText().toString());
        lblContent.setText(txtConteudo.getText().toString());
        txtConteudo.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Cliente().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblContent;
    private javax.swing.JLabel lblReceivedContent;
    private javax.swing.JLabel lblToken;
    private javax.swing.JTextField txtConteudo;
    // End of variables declaration//GEN-END:variables
}
