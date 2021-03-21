package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Constants.ApplicationConstants;
import Utils.Command;
import Utils.CommonUtil;
import Views.MapView;

/**
 * This class depicts player's information and services.
 */
public class Player {
	/**
	 * color to show details with on map.
	 */
	private String d_color;

	/**
	 * Name of the player.
	 */
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
	List<Order> order_list;

	/**
	 * Number of armies allocated to player.
	 */
	Integer d_noOfUnallocatedArmies;

	/**
	 * More orders to be accepted for player.
	 */
	boolean d_moreOrders;

	/**
	 * This parameterized constructor is used to create player with name and default
	 * armies.
	 * 
	 * @param p_playerName player name.
	 */
	public Player(String p_playerName) {
		this.d_name = p_playerName;
		this.d_noOfUnallocatedArmies = 0;
		this.d_coutriesOwned = new ArrayList<Country>();
		this.order_list = new ArrayList<Order>();
		this.d_moreOrders = true;
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
	 * This getter is used to get color code for player.
	 *
	 * @return Color
	 */
	public String getD_color() {
		return d_color;
	}

	/**
	 *
	 * @param p_color ANSI color code.
	 */
	public void setD_color(String p_color) {
		d_color = p_color;
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
		return order_list;
	}

	/**
	 * This setter is used to set execute orders player.
	 * 
	 * @param p_ordersToExecute set execute orders.
	 */
	public void setD_ordersToExecute(List<Order> p_ordersToExecute) {
		this.order_list = p_ordersToExecute;
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
	 * Gets info about more orders from player are to be accepted or not.
	 * 
	 * @return boolean true if player wants to give more order or else false
	 */
	public boolean getD_moreOrders() {
		return d_moreOrders;
	}

	/**
	 * Sets info about more orders from player are to be accepted or not.
	 * 
	 * @param d_moreOrders Boolean true if player wants to give more order or else
	 *                     false
	 */
	public void setD_moreOrders(boolean d_moreOrders) {
		this.d_moreOrders = d_moreOrders;
	}

	/**
	 * Extracts the list of names of countries owned by the player.
	 *
	 * @return list of country names
	 */
	public List<String> getCountryNames() {
		List<String> l_countryNames = new ArrayList<String>();
		for (Country c : d_coutriesOwned) {
			l_countryNames.add(c.getD_countryName());
		}
		return l_countryNames;
	}

	/**
	 * Retrieves the list of continent names owned by the player.
	 *
	 * @return list of continent names
	 */
	public List<String> getContinentNames() {
		List<String> l_continentNames = new ArrayList<String>();
		if (d_continentsOwned != null) {
			for (Continent c : d_continentsOwned) {
				l_continentNames.add(c.getD_continentName());
			}
			return l_continentNames;
		}
		return null;
	}

	/**
	 * Receiver of command pattern :-Issue order which takes order as an input and
	 * add it to player's order list.
	 * 
	 * @param p_gameState Current state of the game
	 * @throws IOException exception in reading inputs from user
	 */
	public void issue_order(GameState p_gameState) throws IOException {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nPlease enter command to issue order for player : " + this.getPlayerName()
				+ " or give showmap command to view current state of the game.");
		String l_commandEntered = l_reader.readLine();
		Command l_command = new Command(l_commandEntered);
		String l_order = l_command.getRootCommand();

		if ("deploy".equalsIgnoreCase(l_order)) {
			createDeployOrder(l_commandEntered);
			checkForMoreOrders();
		} else if ("advance".equalsIgnoreCase(l_order)) {
			createAdvanceOrder(l_commandEntered, p_gameState);
			checkForMoreOrders();
		} else if ("showmap".equalsIgnoreCase(l_order)) {

			MapView l_mapView = new MapView(p_gameState);
			l_mapView.showMap();
			this.issue_order(p_gameState);

		} else if (ApplicationConstants.CARDS.contains(l_order)) {
			// card orders capture method invocation
		} else {
			System.err.println("Invalid command given at this stage.");
		}
	}

	/**
	 * Checks if there are more order to be accepted for player in next turn or not.
	 * 
	 * @throws IOException  exception in reading inputs from user
	 */
	private void checkForMoreOrders() throws IOException {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nDo you still want to give order for player : " + this.getPlayerName()
				+ " in next turn ? \nPress Y for Yes or N for No");
		String l_nextOrderCheck = l_reader.readLine();
		if (l_nextOrderCheck.equalsIgnoreCase("Y") || l_nextOrderCheck.equalsIgnoreCase("N")) {
			this.setD_moreOrders(l_nextOrderCheck.equalsIgnoreCase("Y") ? true : false);
		} else {
			System.err.println("Invalid Input Passed.");
			this.checkForMoreOrders();
		}
	}

	/**
	 * Creates the deploy order on the commands entered by the player.
	 * 
	 * @param p_commandEntered command entered by the user
	 */
	public void createDeployOrder(String p_commandEntered) {
		String l_targetCountry;
		String l_noOfArmies;
		try {
			l_targetCountry = p_commandEntered.split(" ")[1];
			l_noOfArmies = p_commandEntered.split(" ")[2];
			if (validateDeployOrderArmies(this, l_noOfArmies)) {
				System.err.println(
						"Given deploy order cant be executed as armies in deploy order exceeds player's unallocated armies.");
			} else {
				this.order_list.add(new Deploy(this, l_targetCountry, Integer.parseInt(l_noOfArmies)));
				Integer l_unallocatedarmies = this.getD_noOfUnallocatedArmies() - Integer.parseInt(l_noOfArmies);
				this.setD_noOfUnallocatedArmies(l_unallocatedarmies);
				System.out.println("Deploy order has been added to queue for execution. For player: " + this.d_name);
			}
		} catch (Exception e) {
			System.err.println("Invalid deploy order entered");

		}

	}

	/**
	 * Used to test number of armies entered in deploy command to check that player
	 * cannot deploy more armies that there is in their reinforcement pool.
	 *
	 * @param p_player     player to create deploy order
	 * @param p_noOfArmies number of armies to deploy
	 * @return boolean to validate armies to deploy
	 */
	public boolean validateDeployOrderArmies(Player p_player, String p_noOfArmies) {
		return p_player.getD_noOfUnallocatedArmies() < Integer.parseInt(p_noOfArmies) ? true : false;
	}

	/**
	 * Gives the first order in the players list of orders, then removes it from the
	 * list.
	 * 
	 * @return Order first order from the list of player's order
	 */
	public Order next_order() {
		if (CommonUtil.isCollectionEmpty(this.order_list)) {
			return null;
		}
		Order l_order = this.order_list.get(0);
		this.order_list.remove(l_order);
		return l_order;
	}
	
	/**
	 * Creates the advance order on the commands entered by the player.
	 * 
	 * @param p_commandEntered command entered by the user
	 * @param p_gameState current state of the game
	 */
	public void createAdvanceOrder(String p_commandEntered, GameState p_gameState) {
		try {
			if(p_commandEntered.split(" ").length == 4) {
				String l_sourceCountry = p_commandEntered.split(" ")[1];
				String l_targetCountry = p_commandEntered.split(" ")[2];
				String l_noOfArmies = p_commandEntered.split(" ")[3];
				if (this.checkCountryExists(l_sourceCountry, p_gameState)
						&& this.checkCountryExists(l_targetCountry, p_gameState)
						&& !checkZeroArmiesInOrder(l_noOfArmies)) {
					this.order_list.add(new Advance(this, l_sourceCountry, l_targetCountry, Integer.parseInt(l_noOfArmies)));
					System.out.println("Advance order has been added to queue for execution. For player: " + this.d_name);
				}
			} else {
				System.err.println("Invalid Arguments Passed For Advance Order");
			}
			
		} catch (Exception e) {
			System.err.println("Invalid Advance Order Given");
		}
	}
	
	/**
	 * Checks if source and target countries given in advance order exists in the map or not 
	 * 
	 * @param p_countryName country name which needs to be checked in map
	 * @param p_gameState current state of the map
	 * @return true if country exists in map or else false
	 */
	private Boolean checkCountryExists(String p_countryName, GameState p_gameState) {
		if(p_gameState.getD_map().getCountryByName(p_countryName)==null) {
			System.err.println("Country : " + p_countryName + " given in advance order doesnt exists in map. Order given is ignored.");
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if given advance order has zero armies to move
	 * 
	 * @param l_noOfArmies number of armies given in order
	 * @return true if given order has zero armies or else false
	 */
	private Boolean checkZeroArmiesInOrder(String l_noOfArmies) {
		if(Integer.parseInt(l_noOfArmies) == 0) {
			System.err.println("Advance order with 0 armies to move cant be issued.");
			return true;
		}
		return false;
	}
}
