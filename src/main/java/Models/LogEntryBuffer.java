package Models;

import Views.LogWriter;
import java.util.Observable;

/**
 * The class records the corresponding logs for various stages in game.
 */
public class LogEntryBuffer extends Observable {

    /**
     * Log Message to be recorded.
     */
    String d_logMessage;

    public LogEntryBuffer(){
        //d_logMessages = new ArrayList<String>();
        LogWriter l_logWriter = new LogWriter();
        this.addObserver(l_logWriter);
    }

    /**
     * Getter for the Log Message.
     *
     * @return Log Message
     */
    public String getD_logMessage(){
        return d_logMessage;
    }

    /**
     * Sets the Log Message and Notifies the Observer Objects.
     *
     * @param p_messageToUpdate Log Message to Set
     */
    public void currentLog(String p_messageToUpdate){
        d_logMessage = p_messageToUpdate;
        setChanged();
        notifyObservers();
    }
}
