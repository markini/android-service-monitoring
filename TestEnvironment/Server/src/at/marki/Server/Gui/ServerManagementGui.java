package at.marki.Server.Gui;

import at.marki.Server.Data.Data;
import at.marki.Server.Servlet.GCMSend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marki on 27.10.13.
 */
public class ServerManagementGui {
    private JButton buttonSendGcmMessage;
    private JPanel root_panel;
    private JTextField editTextGcmMessage;
    public JList listLog;
    public JList listMessages;

    private static ServerManagementGui instance = null;

    public ServerManagementGui() {
        buttonSendGcmMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message;

                message = getMessage();
                ArrayList<String> devices = new ArrayList<String>();
                devices.add(Data.gcmId);
                GCMSend.sendMessage(message, devices);
                ((DefaultListModel) listMessages.getModel()).addElement("message: " + message);
            }
        });
        instance = this;

        listLog.setModel(new DefaultListModel<String>());
        listMessages.setModel(new DefaultListModel<String>());
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm", Locale.GERMANY);
        Date resultTime = new Date(System.currentTimeMillis());

        if (instance != null && instance.editTextGcmMessage != null && instance.editTextGcmMessage.getText() != null) {
            message = instance.editTextGcmMessage.getText();
        }

        String time = sdf.format(resultTime);
        message = message + time;
        return message;
    }
}
