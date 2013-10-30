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
    public JPanel root_panel;
    public JTextField editTextGcmMessage;
    public JList listLog;
    public JList listMessages;

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
                ((DefaultListModel) listMessages.getModel()).addElement("message: " + message);
            }
        });
        instance = this;

        listLog.setModel(new DefaultListModel());
        listMessages.setModel(new DefaultListModel());
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

    public static String getMessage() {
        String message = "";

        if (instance != null && instance.editTextGcmMessage != null) {
            message = instance.editTextGcmMessage.getText();
        }

        String time = "";
        message = message + time;
        return message;
    }
}
