package Views;

import Models.LogEntryBuffer;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Observable;
import java.util.Observer;

/**
 * The class updates the Log File composed of logs derived from LogEntryBuffer Class.
 */
public class LogWriter implements Observer {

    /**
     * Updated LogEntry Buffer Observable Object.
     */
    LogEntryBuffer d_logEntryBuffer;

    /**
     * Writes the updated LogEntryBuffer Object into Log file.
     *
     * @param p_observable LogEntryBuffer Object.
     * @param p_object Object
     */
    @Override
    public void update(Observable p_observable, Object p_object) {
        d_logEntryBuffer = (LogEntryBuffer) p_observable;
        File l_logfile = new File("LogFile.txt");
        String l_logMessage = d_logEntryBuffer.getD_logMessage();

        try{
            if(l_logMessage.equals("Initializing the Game ......"+System.lineSeparator()+System.lineSeparator())) {
                Files.newBufferedWriter(Paths.get("LogFile.txt"), StandardOpenOption.TRUNCATE_EXISTING).write(" ");
            }
            Files.write(Paths.get("LogFile.txt"), l_logMessage.getBytes(StandardCharsets.US_ASCII), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }catch(Exception l_e){
            l_e.printStackTrace();
        }
    }
}
