package Models;

/**
 * Command of the Command pattern : This model class manages the orders given by
 * the players.
 */
public interface Order {
	/**
	 * Method that will be called by the Receiver to execute the Order
	 */
	public void execute();

	/**
	 * 
	 * @return
	 */
	public boolean valid();

	/**
	 * 
	 */
	public void printOrder();
}
