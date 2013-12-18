package at.marki.Server.Server;

import at.marki.Server.Servlet.ServletRegisterGcmId;
import at.marki.Server.Servlet.ServletCheckGCM;
import at.marki.Server.Servlet.ServletGetNewData;
import at.marki.Server.Servlet.ServletServerStatus;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class SimpleJettyServer {

    public static void startServer() throws Exception {

        Server server = new Server(1433);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

		context.addServlet(new ServletHolder(new ServletGetNewData()), "/getData");

        context.addServlet(new ServletHolder(new ServletServerStatus()), "/ping");

        context.addServlet(new ServletHolder(new ServletCheckGCM()), "/gcmCheck");

        context.addServlet(new ServletHolder(new ServletRegisterGcmId()), "/registerGcmId");


//		ServletHolder holder = new ServletHolder(new ServletMessageReceiver());
//		MultipartConfigElement element = new MultipartConfigElement("data/tmp");
//		holder.getRegistration().setMultipartConfig(element);
//		context.addServlet(holder, "/" + Servlet.UPLOAD_MESSAGES);

        server.start();
        server.join();
    }
}
