package at.marki.Server.Main;

import at.marki.Server.Gui.ServerManagementGui;
import at.marki.Server.Server.SimpleJettyServer;

/**
 * Created by marki on 27.10.13.
 */
class Main {
    public static void main(String[] args) {

        ServerManagementGui gui = new ServerManagementGui();
        gui.startGui();

        try {
            SimpleJettyServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
