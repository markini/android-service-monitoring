package at.marki.Server.Servlet;

/**
 * Created by marki on 29.10.13.
 */

import at.marki.Server.Data.Data;
import at.marki.Server.Data.Message;
import at.marki.Server.Gui.ServerManagementGui;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServletGetNewData extends HttpServlet {

    private static final String TAG = "ServletGetNewData";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // DATABASE CALL -----------------------------------------------------------------------

        response.setStatus(HttpServletResponse.SC_OK);

        // RETURN JSON -------------------------------------------------------------------------
        Message message;

        try {

            message = Data.currentMessage;
            if(message == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", message.message);
            jsonObject.put("messageId",message.id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(jsonObject.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG + " severe error");
            return;
        }

        System.out.println("sending message " + message);
        ServerManagementGui gui = ServerManagementGui.getGui();
        if (gui != null && gui.listLog != null && gui.listMessages != null) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm:ss", Locale.GERMANY);
            Date resultTime = new Date(System.currentTimeMillis());

            ((DefaultListModel) gui.listLog.getModel()).addElement("Device using http to contact. " + sdf.format(resultTime));
            //((DefaultListModel) gui.listMessages.getModel()).addElement("message: " + message.message);
        }
    }
}