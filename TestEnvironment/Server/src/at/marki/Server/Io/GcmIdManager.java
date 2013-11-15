package at.marki.Server.Io;

import at.marki.Server.Data.Data;

import java.io.*;

/**
 * Created by marki on 14.11.13.
 */
public class GcmIdManager {

    private static final String path = "C:/temp/temp.txt";

    public static void addGcmId(String id) {

        try {
            File file = new File(path);
            file.getParentFile().mkdirs();

            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(id);
            printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static String getGcmId() {
        BufferedReader reader = null;
        try {
            File file = new File(path);
            StringBuilder contents = new StringBuilder();

            reader = new BufferedReader(new FileReader(file));
            String text;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(System.getProperty("line.separator"));
            }

            String gcmId = contents.toString();
            Data.gcmId = gcmId;
            return gcmId;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
