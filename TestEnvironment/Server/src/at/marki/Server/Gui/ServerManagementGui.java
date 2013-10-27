package at.marki.Server.Gui;

import at.marki.Server.Data.Data;
import at.marki.Server.Servlet.GCMSend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by marki on 27.10.13.
 */
public class ServerManagementGui {
    public JButton buttonSendGcmMessage;
    public JTextPane textPaneLog;
    public JTextPane textPaneIncoming;
    public JPanel root_panel;
    private JButton buttonSetGcmId;
    private JTextField editTextGcmId;
    private JTextField editTextGcmMessage;

    public static ServerManagementGui instance = null;

    public ServerManagementGui() {
        buttonSendGcmMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = "";
                if (editTextGcmMessage != null && editTextGcmMessage.getText() != null) {
                    message = editTextGcmMessage.getText();
                }
                ArrayList<String> devices = new ArrayList<String>();
                devices.add(Data.gcmId);
                GCMSend.sendMessage(message, devices);
            }
        });
        instance = this;
        buttonSetGcmId.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (editTextGcmId != null && editTextGcmId.getText() != null) {
                    Data.gcmId = editTextGcmId.getText();
                }
            }
        });
    }

    public void startGui() {
        JFrame frame = new JFrame("ServerManagementGui");
        frame.setContentPane(new ServerManagementGui().root_panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static ServerManagementGui getGui() {
        return instance;
    }
}
