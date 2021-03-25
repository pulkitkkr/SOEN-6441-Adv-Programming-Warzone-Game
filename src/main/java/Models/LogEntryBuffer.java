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

    /**
     * Initialises the Class Instance by adding LogWriter Observer object.
     */
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
     * @param p_logType Type of Log : Command, Order, Effect or Phase
     */
    public void currentLog(String p_messageToUpdate, String p_logType){

        switch(p_logType.toLowerCase()){
            case "command":
                d_logMessage = System.lineSeparator()+ "Command Entered: "+ p_messageToUpdate + System.lineSeparator();
                break;
            case "order":
                d_logMessage = System.lineSeparator()+ " Order Issued: "+p_messageToUpdate+System.lineSeparator();
                break;
            case "phase":
                d_logMessage = System.lineSeparator()+ "======="+ p_messageToUpdate + "======="+System.lineSeparator()+System.lineSeparator();
                break;
            case "effect":
                d_logMessage = "Log: "+ p_messageToUpdate + System.lineSeparator();
                break;
            case "start":
            case "end":
                d_logMessage = p_messageToUpdate + System.lineSeparator();
                break;
        }
        setChanged();
        notifyObservers();
    }
}
