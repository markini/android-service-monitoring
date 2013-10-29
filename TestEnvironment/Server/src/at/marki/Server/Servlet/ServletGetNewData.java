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
import java.io.IOException;
import java.util.logging.Logger;

public class ServletGetNewData extends HttpServlet {

    private static final String TAG = "ServletGetNewData";
    private final static Logger LOGGER = Logger.getLogger(ServletGetNewData.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // DATABASE CALL -----------------------------------------------------------------------

        response.setStatus(HttpServletResponse.SC_OK);

        // RETURN JSON -------------------------------------------------------------------------

        try {
            String message = "default message";
            if (ServerManagementGui.getGui().editTextGcmMessage.getText() != null) {
                message = ServerManagementGui.getGui().editTextGcmMessage.getText();
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", message);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(jsonObject.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}