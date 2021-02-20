package Models;

import java.util.ArrayList;
import java.util.List;

import Utils.CommonUtil;

public class Player {
	private String name;
	List<Country> d_coutriesOwned;
	List<Continent> d_continentsOwned;
	List<Order> d_ordersToExecute;
	Integer d_noOfUnallocatedArmies;

	/**
	 * 
	 * @param p_playerName
	 */
	public Player(String p_playerName) {
		this.name = p_playerName;
	}
	
	public Player() {
		
	}

	public String getPlayerName() {
		return name;
	}

	public void setPlayerName(String name) {
		this.name = name;
	}

	public List<Country> getD_coutriesOwned() {
		return d_coutriesOwned;
	}

	public void setD_coutriesOwned(List<Country> p_coutriesOwned) {
		this.d_coutriesOwned = p_coutriesOwned;
	}

	public List<Continent> getD_continentsOwned() {
		return d_continentsOwned;
	}

	public void setD_continentsOwned(List<Continent> p_continentsOwned) {
		this.d_continentsOwned = p_continentsOwned;
	}

	public List<Order> getD_ordersToExecute() {
		return d_ordersToExecute;
	}

	public void setD_ordersToExecute(List<Order> p_ordersToExecute) {
		this.d_ordersToExecute = p_ordersToExecute;
	}

	public Integer getD_noOfUnallocatedArmies() {
		return d_noOfUnallocatedArmies;
	}

	public void setD_noOfUnallocatedArmies(Integer p_noOfUnallocatedArmies) {
		this.d_noOfUnallocatedArmies = p_noOfUnallocatedArmies;
	}
	

	public void playerInfo(GameState p_gameState, String p_operation, String p_argument) {
		List<Player> l_updatedPlayers = this.addRemovePlayers(p_gameState.getD_players(), p_operation, p_argument);

		if (!CommonUtil.isNull(l_updatedPlayers)) {
			p_gameState.setD_players(l_updatedPlayers);
		}
	}

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