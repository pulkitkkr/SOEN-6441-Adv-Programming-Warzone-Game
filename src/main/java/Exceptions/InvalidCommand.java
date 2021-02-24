package Exceptions;

/**
 * Command Exceptions are thrown by this class
 */
public class InvalidCommand extends Exception {
    public InvalidCommand(String p_message) {
        super(p_message);
    }
}
