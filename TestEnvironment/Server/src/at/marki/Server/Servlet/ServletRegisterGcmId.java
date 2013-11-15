package at.marki.Server.Servlet;

/**
 * Created by marki on 29.10.13.
 */

import at.marki.Server.Io.GcmIdManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletRegisterGcmId extends HttpServlet {

    private static final String TAG = "ServletGetNewData";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_OK);

        String gcmId = request.getParameter("id");
        System.out.println("trying to add id: " + gcmId);
        GcmIdManager.addGcmId(gcmId);
        return;
    }
}