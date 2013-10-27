package at.marki.Server.Gui;

import at.marki.Server.Server.SimpleJettyServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by marki on 27.10.13.
 */
public class ServerManagementGui {
    public JButton btn_send_gcm_message;
    public JTextPane txt_show_log;
    public JTextPane txt_show_incoming;
    public JPanel root_panel;

    public static ServerManagementGui instance = null;

    public ServerManagementGui() {
        btn_send_gcm_message.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.out.println("Fucking button clicked!!!");
            }
        });
        instance = this;
    }

    public void startGui(){
        JFrame frame = new JFrame("ServerManagementGui");
        frame.setContentPane(new ServerManagementGui().root_panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static ServerManagementGui getGui(){
        return instance;
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("ServerManagementGui");
//        frame.setContentPane(new ServerManagementGui().root_panel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//
//        try {
//            SimpleJettyServer.startServer();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
