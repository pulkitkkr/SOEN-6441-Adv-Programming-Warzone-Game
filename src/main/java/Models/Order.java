package Models;

/**
 * This model class manages the orders given by the players.
 */
public class Order {
	
	/**
	 * order actions.
	 */
	String d_orderAction;
	
	/**
	 * name of the target country.
	 */
	String d_targetCountryName;
	
	/**
	 * name of the source country.
	 */
	String d_sourceCountryName;
	
	/**
	 * number of armies to be placed.
	 */
	Integer d_numberOfArmiesToPlace;
	
	/**
	 * object of the order class.
	 */
	Order orderObj;

	/**
	 * unparameterized constructor.
	 */
	public Order() {
	}

	/**
	 * parameterized constructor.
	 * 
	 * @param p_orderAction order action
	 * @param p_targetCountryName name of the target country
	 * @param p_numberOfArmiesToPlace number of armies to be placed
	 */
	public Order(String p_orderAction, String p_targetCountryName, Integer p_numberOfArmiesToPlace) {
		this.d_orderAction = p_orderAction;
		this.d_targetCountryName = p_targetCountryName;
		this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
	}

	/**
	 * getter method to get order actions.
	 * 
	 * @return order action
	 */
	public String getD_orderAction() {
		return d_orderAction;
	}

	/**
	 * setter method to set the order actions.
	 * 
	 * @param p_orderAction order action
	 */
	public void setD_orderAction(String p_orderAction) {
		this.d_orderAction = p_orderAction;
	}

	/**
	 * getter method to get the target country name.
	 * @return target country name
	 */
	public String getD_targetCountryName() {
		return d_targetCountryName;
	}

	/**
	 * setter method to set the target country name.
	 * 
	 * @param p_targetCountryName target country name
	 */
	public void setD_targetCountryName(String p_targetCountryName) {
		this.d_targetCountryName = p_targetCountryName;
	}
	
	/**
	 * getter method to get the source country name.
	 * 
	 * @return source country name
	 */
	public String getD_sourceCountryName() {
		return d_sourceCountryName;
	}

	/**
	 * setter method to set the source country name.
	 * 
	 * @param p_sourceCountryName source country name
	 */
	public void setD_sourceCountryName(String p_sourceCountryName) {
		this.d_sourceCountryName = p_sourceCountryName;
	}

	/**
	 * getter method to get the number of armies to be placed.
	 * 
	 * @return number of armies to be placed
	 */
	public Integer getD_numberOfArmiesToPlace() {
		return d_numberOfArmiesToPlace;
	}

	/**
	 * setter method to set the number of armies to be placed.
	 * 
	 * @param p_numberOfArmiesToPlace number of armies to be placed
	 */
	public void setD_numberOfArmiesToPlace(Integer p_numberOfArmiesToPlace) {
		this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
	}

	/**
	 * Enacts the order object and makes necessary changes in game state.
	 * 
	 * @param p_gameState current state of the game
	 * @param p_player player who's order is being executed
	 */
	public void execute(GameState p_gameState, Player p_player) {
		switch (this.d_orderAction) {
		case "deploy": {
			if (this.validateDeployOrderCountry(p_player, this)) {
				this.executeDeployOrder(this, p_gameState, p_player);
				System.out.println("\nOrder has been executed successfully. " + this.getD_numberOfArmiesToPlace()
						+ " number of armies has been deployed to country : "
						+ this.getD_targetCountryName());
			} else {
				System.out.println(
						"\nOrder is not executed as target country given in deploy command doesnt belongs to player : "
								+ p_player.getPlayerName());
			}
			break;
		}
		default: {
			System.out.println("Order was not executed due to invalid Order Command");
		}
		}
	}

	/**
	 * Validates whether country given for deploy belongs to players countries or
	 * not.
	 * 
	 * @param p_player player whos order is being executed
	 * @param p_order order which is being executed
	 * @return true/false
	 */
	public boolean validateDeployOrderCountry(Player p_player, Order p_order) {
		Country l_country = p_player.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(p_order.getD_targetCountryName())).findFirst()
				.orElse(null);
		return l_country != null;
	}

	/**
	 * Executes deploy order and updates game state with latest map.
	 * 
	 * @param p_order order which is being executed
	 * @param p_gameState current state of the game
	 * @param p_player player whos order is being executed
	 */
	private void executeDeployOrder(Order p_order, GameState p_gameState, Player p_player) {
		for (Country l_country : p_gameState.getD_map().getD_countries()) {
			if (l_country.getD_countryName().equalsIgnoreCase(p_order.getD_targetCountryName())) {
				Integer l_armiesToUpdate = l_country.getD_armies() == null ? p_order.getD_numberOfArmiesToPlace()
						: l_country.getD_armies() + p_order.getD_numberOfArmiesToPlace();
				l_country.setD_armies(l_armiesToUpdate);
			}
		}
	}
}
