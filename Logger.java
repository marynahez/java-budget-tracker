package ca.ucalgary.cpsc233group.cpsc233projectgui.util;

import java.io.*;
import java.io.PrintWriter;

/**
 * Class to assist in saving information.
 * E.E
 */
public class Logger {
    private PrintWriter printWriter;
    public Logger(File fileLog) {
        try {
            printWriter = new PrintWriter(new FileOutputStream(fileLog, true));
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open log file");
            System.exit(1);
        }
    }
    public void log(Object object){
        printWriter.println(object);
        printWriter.flush();
    }

}
