package at.marki.Server.Gui;

import at.marki.Server.Data.Data;
import at.marki.Server.Data.Message;
import at.marki.Server.Io.GcmIdManager;
import at.marki.Server.Servlet.GCMSend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by marki on 27.10.13.
 */
public class ServerManagementGui {
    private JButton buttonSendGcmMessage;
    private JPanel root_panel;
    private JTextField editTextGcmMessage;
    public JList listLog;
    public JList listMessages;
    private JLabel textViewMessage;
    private JButton buttonSetMessage;

    private static ServerManagementGui instance = null;

    public ServerManagementGui() {
        buttonSendGcmMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Data.currentMessage == null) {
                    return;
                }
                ArrayList<String> devices = new ArrayList<String>();
                if (Data.gcmId == null) {
                    devices.add(GcmIdManager.getGcmId());
                } else {
                    devices.add(Data.gcmId);
                }
                GCMSend.sendMessage(Data.currentMessage, devices);

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm:ss", Locale.GERMANY);
                Date resultTime = new Date(System.currentTimeMillis());

                ((DefaultListModel) listMessages.getModel()).addElement("message: " + Data.currentMessage.message + " " + sdf.format(resultTime));
            }
        });

        buttonSetMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String messageString;
                String messageId = UUID.randomUUID().toString();

                messageString = getMessage();

                if (messageString == null) {
                    Data.currentMessage = null;
                    return;
                }

                Message message = new Message(messageId, messageString);
                Data.currentMessage = message;
                textViewMessage.setText(message.message);
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
        String message = null;

        if (instance != null && instance.editTextGcmMessage != null && instance.editTextGcmMessage.getText() != null) {
            message = instance.editTextGcmMessage.getText();
        }

        if (message == null) {
            return null;
        }
        return message;
    }
}
