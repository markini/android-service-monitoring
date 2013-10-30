package at.marki.Server.Servlet;

/**
 * Created by marki on 29.10.13.
 */

import at.marki.Server.Gui.ServerManagementGui;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;

public class ServletGetNewData extends HttpServlet {

    private static final String TAG = "ServletGetNewData";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // DATABASE CALL -----------------------------------------------------------------------

        response.setStatus(HttpServletResponse.SC_OK);

        // RETURN JSON -------------------------------------------------------------------------
        String message = "default message";

        try {

            message = ServerManagementGui.getMessage();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", message);

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
            ((DefaultListModel) gui.listLog.getModel()).addElement("new ping from device");
            ((DefaultListModel) gui.listMessages.getModel()).addElement("message: " + message);
        }
    }
}