package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import Constants.ApplicationConstants;
import Exceptions.InvalidCommand;
import Services.PlayerService;
import Utils.Command;
import Utils.CommonUtil;

/**
 *
 * This class depicts player's information and services.
 *
 */
public class Player {
	private String d_name;

	/**
	 * List of countries owned by player.
	 */
	List<Country> d_coutriesOwned;

	/**
	 * List of Continents owned by player.
	 */
	List<Continent> d_continentsOwned;

	/**
	 * List of orders of player.
	 */
	List<Order> d_ordersToExecute;

	/**
	 * Number of armies allocated to player.
	 */
	Integer d_noOfUnallocatedArmies;

	/**
	 * This parameterized constructor is used to create player with name and default
	 * armies
	 *
	 * @param p_playerName player name.
	 */
	public Player(String p_playerName) {
		this.d_name = p_playerName;
		this.d_noOfUnallocatedArmies = 0;
		this.d_ordersToExecute = new ArrayList<>();
	}

	/**
	 * This is No argument constructor.
	 */
	public Player() {

	}

	/**
	 * This getter is used to get player's name.
	 *
	 * @return return player name.
	 */
	public String getPlayerName() {
		return d_name;
	}

	/**
	 * This setter is used to set player's p_name.
	 *
	 * @param p_name set player name.
	 */
	public void setPlayerName(String p_name) {
		this.d_name = p_name;
	}

	/**
	 * This getter is used to get list of countries owned by player.
	 *
	 * @return return countries owned by player.
	 */
	public List<Country> getD_coutriesOwned() {
		return d_coutriesOwned;
	}

	/**
	 * This setter is used to set list of countries owned by player.
	 *
	 * @param p_coutriesOwned set countries owned by player.
	 */
	public void setD_coutriesOwned(List<Country> p_coutriesOwned) {
		this.d_coutriesOwned = p_coutriesOwned;
	}

	/**
	 * This getter is used to get list of continents owned by player.
	 *
	 * @return return list of continents owned by player.
	 */
	public List<Continent> getD_continentsOwned() {
		return d_continentsOwned;
	}

	/**
	 * This setter is used to set list of continents owned by player.
	 *
	 * @param p_continentsOwned set continents owned by player.
	 */
	public void setD_continentsOwned(List<Continent> p_continentsOwned) {
		this.d_continentsOwned = p_continentsOwned;
	}

	/**
	 * This getter is used to get execute orders of player.
	 *
	 * @return return execute orders.
	 */
	public List<Order> getD_ordersToExecute() {
		return d_ordersToExecute;
	}

	/**
	 * This setter is used to set execute orders player.
	 *
	 * @param p_ordersToExecute set execute orders.
	 */
	public void setD_ordersToExecute(List<Order> p_ordersToExecute) {
		this.d_ordersToExecute = p_ordersToExecute;
	}

	/**
	 * This getter is used to get allocated armies of player.
	 *
	 * @return return allocated armies of player.
	 */

	public Integer getD_noOfUnallocatedArmies() {
		return d_noOfUnallocatedArmies;
	}

	/**
	 * This setter is used to set number of allocated armies to player.
	 *
	 * @param p_numberOfArmies set number of armies to player.
	 */
	public void setD_noOfUnallocatedArmies(Integer p_numberOfArmies) {
		this.d_noOfUnallocatedArmies = p_numberOfArmies;
	}


	/**
	 * Issue order which takes order as an input and add it to players unassigned
	 * orders pool
	 *
	 * @throws IOException exception in reading inputs from user
	 */
	public void issue_order() throws IOException {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
		PlayerService l_playerService = new PlayerService();

		String l_commandEntered;
		try {
			System.out.println("\nPlease enter command to deploy reinforcement armies on the map for player : "
					+ this.getPlayerName());
			l_commandEntered = l_reader.readLine();
			Command l_command = new Command(l_commandEntered);
			if (l_command.getRootCommand().equalsIgnoreCase("deploy")) {
				l_playerService.createDeployOrder(l_commandEntered, this);
			} else {
				throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_DEPLOY_ORDER);
			}

		} catch (InvalidCommand l_e) {
			l_e.printStackTrace();
		}
	}

	/**
	 * Gives the first order in the players list of orders, then removes it from the
	 * list.
	 *
	 * @return Order first order from the list of player's order
	 */
	public Order next_order() {
		if (CommonUtil.isCollectionEmpty(this.d_ordersToExecute)) {
			return null;
		}
		Order l_order = this.d_ordersToExecute.get(0);
		this.d_ordersToExecute.remove(l_order);
		return l_order;
	}
}
