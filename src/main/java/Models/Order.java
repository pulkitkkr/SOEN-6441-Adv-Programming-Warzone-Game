package Models;

/**
 * Command of the Command pattern : This model class manages the orders given by
 * the players.
 */
public interface Order {

	/**
	 * Method that will be called by the Receiver to execute the Order.
	 * 
	 * @param p_gameState current state of the game.
	 */
	public void execute(GameState p_gameState);

	/**
	 * 
	 * @return boolean true or false
	 */
	public boolean valid();

	/**
	 * Print order information.
	 */
	public void printOrder();
}
