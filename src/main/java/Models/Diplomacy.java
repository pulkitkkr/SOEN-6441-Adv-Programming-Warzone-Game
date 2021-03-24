package Models;

/**
 * 
 *
 */
public class Diplomacy implements Card {

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

	@Override
	public String getD_cardName() {
		return null;
	}
}
