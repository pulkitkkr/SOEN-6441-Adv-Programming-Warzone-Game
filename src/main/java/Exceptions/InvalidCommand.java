package Exceptions;

/**
 * Command Exceptions are thrown by this class.
 */
public class InvalidCommand extends Exception {
	
	/**
	 * calls super class when the message is invalid.
	 * 
	 * @param p_message message
	 */
    public InvalidCommand(String p_message) {
        super(p_message);
    }
}
