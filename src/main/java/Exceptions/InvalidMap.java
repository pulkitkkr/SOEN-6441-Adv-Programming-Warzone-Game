package Exceptions;

/**
 * Defined Map Exceptions are thrown by this class.
 */
public class InvalidMap extends Exception{
	
	/**
	 * calls super class when the message is invalid.
	 * 
	 * @param p_message message
	 */
    public InvalidMap(String p_message) {
        super(p_message);
    }
}
