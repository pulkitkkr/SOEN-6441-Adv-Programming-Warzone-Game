package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import Constants.ApplicationConstants;
import Controllers.GameEngineController;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Utils.Command;
import Utils.CommonUtil;

/**
 * 
 * This class depicts player's information and services.
 *
 */
public class Player {
	private String name;

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
		this.name = p_playerName;
		this.d_noOfUnallocatedArmies = 0;
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
				System.out.print(
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
				System.out.print(
						"Player with name : " + p_argument.split(" ")[0] + " does not Exist. Changes are not made");
			}
		}
		return l_updatedPlayers;
	}

	/**
	 * This method is used to assign countries randomly among players
	 * 
	 * @param p_gameState current game state with map and player information
	 */
	public void assignCountries(GameState p_gameState) {
		if (this.checkPlayersAvailability(p_gameState)) {
			List<Country> l_countries = p_gameState.getD_map().getD_countries();
			int l_countriesPerPlayer = Math.floorDiv(l_countries.size(), p_gameState.getD_players().size());
			this.randomCountryAssignment(l_countriesPerPlayer, l_countries, p_gameState.getD_players());
			this.performContinentAssignment(p_gameState.getD_players(), p_gameState.getD_map().getD_continents());
			System.out.println("Countries have been assigned to Players");
		}
	}

	/**
	 * Check whether players are loaded or not
	 * 
	 * @param p_gameState current game state with map and player information
	 * @return boolean players exists or not
	 */
	public boolean checkPlayersAvailability(GameState p_gameState) {
		if (p_gameState.getD_players() == null || p_gameState.getD_players().isEmpty()) {
			System.out.println("Kindly add players before assigning countries");
			return false;
		}
		return true;
	}

	/**
	 * Performs random country assignment to all players
	 * 
	 * @param p_countriesPerPlayer countries which are to be assigned to each player
	 * @param p_countries          list of all countries present in map
	 * @param p_players            list of all available players
	 */
	private void randomCountryAssignment(int p_countriesPerPlayer, List<Country> p_countries, List<Player> p_players) {
		List<Country> l_unassignedCountries = new ArrayList<>();
		l_unassignedCountries.addAll(p_countries);

		for (Player l_pl : p_players) {
			for (int i = 0; i < p_countriesPerPlayer; i++) {
				Random l_random = new Random();
				int l_randomIndex = l_random.nextInt(l_unassignedCountries.size());
				Country l_randomCountry = l_unassignedCountries.get(l_randomIndex);

				if (l_pl.getD_coutriesOwned() == null)
					l_pl.setD_coutriesOwned(new ArrayList<Country>());
				l_pl.getD_coutriesOwned().add(l_randomCountry);
				System.out.println("Player : " + l_pl.getPlayerName() + " is assigned with country : "
						+ l_randomCountry.getD_countryName());
				l_unassignedCountries.remove(l_randomCountry);
			}
			if (l_unassignedCountries.isEmpty())
				break;
		}
		if (!l_unassignedCountries.isEmpty()) {
			randomCountryAssignment(1, l_unassignedCountries, p_players);
		}
	}

	/**
	 * Checks if player is having any continent as a result of random country
	 * assignment
	 * 
	 * @param p_players    list of all available players
	 * @param l_continents list of continents in map
	 */
	private void performContinentAssignment(List<Player> p_players, List<Continent> p_continents) {
		for (Player l_pl : p_players) {
			List<String> l_countriesOwned = new ArrayList<>();
			l_pl.getD_coutriesOwned().forEach(l_country -> l_countriesOwned.add(l_country.getD_countryName()));
			for (Continent l_cont : p_continents) {
				List<String> l_countriesOfContinent = new ArrayList<>();
				l_cont.getD_countries().forEach(l_count -> l_countriesOfContinent.add(l_count.getD_countryName()));

				if (l_countriesOwned.containsAll(l_countriesOfContinent)) {
					if (l_pl.getD_continentsOwned() == null)
						l_pl.setD_continentsOwned(new ArrayList<>());
					l_pl.getD_continentsOwned().add(l_cont);
					System.out.println("Player : " + l_pl.getPlayerName() + " is assigned with continent : "
							+ l_cont.getD_continentName());
				}
			}
		}
	}

	/**
	 * Issue order which takes order as an input and add it to players unassigned
	 * orders pool
	 */
	public void issue_order() {
		Scanner l_reader = new Scanner(System.in);
		String l_commandEntered;
		try {
			System.out.println("Please enter command to deploy reinforcement armies on the map for player : "
					+ this.getPlayerName());
			l_commandEntered = l_reader.nextLine();
			Command l_command = new Command(l_commandEntered);
			if (l_command.getRootCommand().equalsIgnoreCase("deploy")) {
				createDeployOrder(l_commandEntered, this);
			} else {
				l_reader.close();
				throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_DEPLOY_ORDER);
			}
		} catch (InvalidCommand e) {
			e.printStackTrace();
		}
		l_reader.close();
	}

	/**
	 * @param p_command
	 * @throws InvalidCommand
	 */
	private void createDeployOrder(String p_commandEntered, Player p_player) throws InvalidCommand {
		List<Order> l_orders = CommonUtil.isCollectionEmpty(p_player.getD_ordersToExecute()) ? new ArrayList<>()
				: p_player.getD_ordersToExecute();

		if (p_commandEntered.split(" ").length != 3) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_DEPLOY_ORDER);
		} else {
			String l_countryName = p_commandEntered.split(" ")[1];
			String l_noOfArmies = p_commandEntered.split(" ")[2];

			if (!CommonUtil.isEmpty(l_countryName) && !CommonUtil.isEmpty(l_noOfArmies)) {
				System.out.println("Valid args received");
				Order l_orderObject = new Order(p_commandEntered.split(" ")[0], l_countryName,
						Integer.parseInt(l_noOfArmies));
				l_orders.add(l_orderObject);
				p_player.setD_ordersToExecute(l_orders);
			} else {
				throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_DEPLOY_ORDER);
			}

		}

	}

	/**
	 * Assigns armies to each player of the game
	 * 
	 * @param p_gameState current game state with map and player information
	 */
	public void assignArmies(GameState p_gameState) {
		for (Player l_pl : p_gameState.getD_players()) {
			Integer l_armies = this.calculateArmiesForPlayer(l_pl);
			System.out.println("Player : " + l_pl.getPlayerName() + " has been assigned with " + l_armies + " armies");
			l_pl.setD_noOfUnallocatedArmies(l_armies);
		}
		System.out.println("Armies have been assigned successfully to players");
	}

	/**
	 * Calculates armies of player based on countries and continents owned
	 * 
	 * @param p_player player for which armies have to be calculated
	 * @return Integer armies to be assigned to player
	 */
	private Integer calculateArmiesForPlayer(Player p_player) {
		Integer l_armies = 0;
		if (!CommonUtil.isCollectionEmpty(p_player.getD_coutriesOwned())) {
			l_armies = Math.max(3, Math.round((p_player.getD_coutriesOwned().size()) / 3));
		}
		if (!CommonUtil.isCollectionEmpty(p_player.getD_continentsOwned())) {
			Integer l_continentCtrlValue = 0;
			for (Continent l_continent : p_player.getD_continentsOwned()) {
				l_continentCtrlValue = l_continentCtrlValue + l_continent.getD_continentValue();
			}
			l_armies = l_armies + l_continentCtrlValue;
		}
		return l_armies;
	}

	/**
	 * Gives the first order in the players list of orders, then removes it from the
	 * list.
	 * 
	 * @return Order first order from the list of player's order
	 */
	public Order next_order() {
		Order l_order = this.d_ordersToExecute.get(0);
		this.d_ordersToExecute.remove(l_order);
		return l_order;
	}

	/**
	 * Retrieves total number of orders given through out the game
	 * 
	 * @param p_playersList players involved in game
	 * @return int number of total un-executed orders
	 */
	public int getUnexecutedOrdersOfGame(List<Player> p_playersList) {
		int l_totalUnexecutedOrders = 0;
		for (Player l_player : p_playersList) {
			l_totalUnexecutedOrders = l_totalUnexecutedOrders + l_player.getD_ordersToExecute().size();
		}
		return l_totalUnexecutedOrders;
	}

	/**
	 * Check if any of the players have unassigned armies
	 * 
	 * @param p_playersList players involved in game
	 * @return true if unassigned armies exists with any of the players or else
	 *         false
	 */
	public boolean unassignedArmiesExists(List<Player> p_playersList) {
		Integer l_unassignedArmies = 0;
		for (Player l_player : p_playersList) {
			l_unassignedArmies = l_unassignedArmies + l_player.getD_noOfUnallocatedArmies();
		}
		return l_unassignedArmies != 0;
	}
}