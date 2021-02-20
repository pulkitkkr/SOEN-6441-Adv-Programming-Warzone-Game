package Models;

import java.util.ArrayList;
import java.util.List;

import Utils.CommonUtil;

/**
 * 
 * This class depicts player's information and services.
 *
 */
public class Player {
	private String name;
	List<Country> d_coutriesOwned;
	List<Continent> d_continentsOwned;
	List<Order> d_ordersToExecute;
	Integer d_noOfUnallocatedArmies;

	/**
	 * This parameterized constructor is used to create player with name.
	 * 
	 * @param p_playerName player name.
	 */
	public Player(String p_playerName) {
		this.name = p_playerName;
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
		return name;
	}

	/**
	 * This setter is used to set player's name.
	 * 
	 * @param name set player name.
	 */
	public void setPlayerName(String name) {
		this.name = name;
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
	 * @param p_noOfUnallocatedArmies set number of armies to player.
	 */
	public void setD_noOfUnallocatedArmies(Integer p_noOfUnallocatedArmies) {
		this.d_noOfUnallocatedArmies = p_noOfUnallocatedArmies;
	}

	/**
	 * This method is called by controller to add players, update gameState.
	 * 
	 * @param p_gameState update game state with players information.
	 * @param p_operation operation to add or remove player.
	 * @param p_argument  name of player to add or remove.
	 */
	public void playerInfo(GameState p_gameState, String p_operation, String p_argument) {
		List<Player> l_updatedPlayers = this.addRemovePlayers(p_gameState.getD_players(), p_operation, p_argument);

		if (!CommonUtil.isNull(l_updatedPlayers)) {
			p_gameState.setD_players(l_updatedPlayers);
		}
	}

	/**
	 * This method is used to add and remove players.
	 * 
	 * @param p_existingPlayerList current player list.
	 * @param p_operation          operation to add or remove player.
	 * @param p_argument           name of player to add or remove.
	 * @return return updated list of player.
	 */
	public List<Player> addRemovePlayers(List<Player> p_existingPlayerList, String p_operation, String p_argument) {
		List<Player> l_updatedPlayers = new ArrayList<Player>();
		if (!CommonUtil.isCollectionEmpty(p_existingPlayerList))
			l_updatedPlayers.addAll(p_existingPlayerList);

		String l_enteredPlayerName = p_argument.split(" ")[0];
		boolean l_playerNameAlreadyExist = false;

		if (!CommonUtil.isCollectionEmpty(p_existingPlayerList)) {
			for (Player l_player : p_existingPlayerList) {
				if (l_player.getPlayerName().equalsIgnoreCase(l_enteredPlayerName)) {
					l_playerNameAlreadyExist = true;
				}
			}
		}

		if (p_operation.equalsIgnoreCase("add")) {
			if (l_playerNameAlreadyExist) {
				System.out.println(
						"Player with name : " + p_argument.split(" ")[0] + " already Exists. Changes are not made");
			} else {
				Player l_addNewPlayer = new Player(l_enteredPlayerName);
				l_updatedPlayers.add(l_addNewPlayer);
			}
		} else if (p_operation.equalsIgnoreCase("remove")) {
			if (l_playerNameAlreadyExist) {
				for (Player l_player : p_existingPlayerList) {
					if (l_player.getPlayerName().equalsIgnoreCase(l_enteredPlayerName)) {
						l_updatedPlayers.remove(l_player);
					}
				}
			} else {
				System.out.println(
						"Player with name : " + p_argument.split(" ")[0] + " does not Exist. Changes are not made");
			}
		}
		return l_updatedPlayers;
	}

}