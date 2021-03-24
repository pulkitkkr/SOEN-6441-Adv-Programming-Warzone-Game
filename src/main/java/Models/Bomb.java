package Models;

/**
 * 
 *
 */
public class Bomb implements Card {

	/**
	 * 
	 */
	@Override
	public void execute(GameState p_gameState) {

	}

	/**
	 * 
	 */
	@Override
	public boolean valid() {

		return false;
	}

	@Override
	public void printOrder() {

	}

	@Override
	public String orderExecutionLog() {
		return null;
	}

	@Override
	public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType) {

	}

	@Override
	public Boolean checkValidOrder(GameState p_gameState) {
		return null;
	}

	/**
	 * Return order name.
	 * 
	 * @return String
	 */
	@Override
	public String getOrderName() {
		return "bomb";
	}
}
