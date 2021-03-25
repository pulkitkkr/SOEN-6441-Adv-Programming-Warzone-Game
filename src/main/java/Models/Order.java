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
	 * Validates order.
	 * 
	 * @return boolean true or false
	 * @param p_gameState GameState Instance
	 */
	public boolean valid(GameState p_gameState);

	/**
	 * Print order information.
	 */
	public void printOrder();

	/**
	 * Returns the Log to GameState with Execution Log.
	 *
	 * @return String containing log message
	 */
	public String orderExecutionLog();

	/**
	 * Prints and Sets the order execution log.
	 *
	 * @param p_orderExecutionLog String to be set as log
	 * @param p_logType           type of log : error, default
	 */
	public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType);

	/**
	 * Return order name.
	 * 
	 * @return String
	 */
	public String getOrderName();
}
