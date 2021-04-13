package Models;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class is used to test functionality of GameState class functions.
 */
public class GameState {

	/**
	 * map object.
	 */
	Map d_map;

	List<Map> d_mapList;

	/**
	 * Log Entries for existing game state.
	 */
	LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

	/**
	 * list of players.
	 */
	List<Player> d_players;

	/**
	 * list of unexecuted orders.
	 */
	List<Order> d_unexecutedOrders;

	/**
	 * error message.
	 */
	String d_error;

	/**
	 * Checks if user has used load command.
	 */
	Boolean d_loadCommand = false;

	int d_maxnumberofturns = 0;
	
	int d_numberOfTurnsLeft = 0;

	/**
	 * getter method to get the map.
	 * 
	 * @return map object
	 */
	public Map getD_map() {
		return d_map;
	}

	/**
	 * setter method to set the map.
	 * 
	 * @param p_map map object
	 */
	public void setD_map(Map p_map) {
		this.d_map = p_map;
	}

	/**
	 * getter method to get the list of players.
	 * 
	 * @return list of players
	 */
	public List<Player> getD_players() {
		return d_players;
	}

	/**
	 * setter method to set the players.
	 * 
	 * @param p_players list of players
	 */
	public void setD_players(List<Player> p_players) {
		this.d_players = p_players;
	}

	/**
	 * getter method to get the list of orders which are yet to be executed.
	 * 
	 * @return list of orders
	 */
	public List<Order> getD_unexecutedOrders() {
		return d_unexecutedOrders;
	}

	/**
	 * setter method to set the unexecuted orders.
	 * 
	 * @param p_unexecutedOrders list of unexecuted orders
	 */
	public void setD_unexecutedOrders(List<Order> p_unexecutedOrders) {
		this.d_unexecutedOrders = p_unexecutedOrders;
	}

	/**
	 * getter method to get the error message.
	 * 
	 * @return error message
	 */
	public String getError() {
		return d_error;
	}

	/**
	 * setter method to set the error message.
	 * 
	 * @param p_error error message
	 */
	public void setError(String p_error) {
		this.d_error = p_error;
	}

	/**
	 * Message to be added in the log.
	 *
	 * @param p_logMessage Log Message to be set in the Object
	 * @param p_logType    Type of Log Message to be Added
	 */
	public void updateLog(String p_logMessage, String p_logType) {
		d_logEntryBuffer.currentLog(p_logMessage, p_logType);
	}

	/**
	 * Fetches the most recent Log in current GameState.
	 *
	 * @return recent Log Message
	 */
	public String getRecentLog() {
		return d_logEntryBuffer.getD_logMessage();
	}

	/**
	 * Sets the Boolean load map variable.
	 */
	public void setD_loadCommand() {
		this.d_loadCommand = true;
	}

	/**
	 * Returns if load command is used.
	 *
	 * @return bool value if map is loaded
	 */
	public boolean getD_loadCommand() {
		return this.d_loadCommand;
	}
	

	public int getD_maxnumberofturns() {
		return d_maxnumberofturns;
	}

	public void setD_maxnumberofturns(int d_maxnumberofturns) {
		this.d_maxnumberofturns = d_maxnumberofturns;
	}

	public int getD_numberOfTurnsLeft() {
		return d_numberOfTurnsLeft;
	}

	public void setD_numberOfTurnsLeft(int d_numberOfTurnsLeft) {
		this.d_numberOfTurnsLeft = d_numberOfTurnsLeft;
	}

}
