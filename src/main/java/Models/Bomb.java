package Models;

import Utils.CommonUtil;

/**
 * The target country loses half of their army units when bomb card is used by
 * the player.
 */
public class Bomb implements Card {

	/**
	 * Bomb card will be owned by this player.
	 */
	Player d_playerInitiator;

	/**
	 * name of the target country.
	 */
	String d_targetCountryID;

	/**
	 * Sets the Log containing Information about orders.
	 */
	String d_orderExecutionLog;

	/**
	 * The constructor receives all the parameters necessary to implement the order.
	 * 
	 * @param p_playerInitiator Player
	 * @param p_targetCountry   target country ID
	 */
	public Bomb(Player p_playerInitiator, String p_targetCountry) {
		this.d_playerInitiator = p_playerInitiator;
		this.d_targetCountryID = p_targetCountry;
	}

	/**
	 * Executes the Bomb order.
	 * 
	 * @param p_gameState current state of the game.
	 */
	@Override
	public void execute(GameState p_gameState) {
		if (valid()) {
			Country l_targetCountryID = p_gameState.getD_map().getCountryByName(d_targetCountryID);
			Integer l_noOfArmiesOnTargetCountry = l_targetCountryID.getD_armies() == 0 ? 1
					: l_targetCountryID.getD_armies();
			Integer l_newArmies = (int) Math.floor(l_noOfArmiesOnTargetCountry / 2);
			l_targetCountryID.setD_armies(l_newArmies);
			d_playerInitiator.removeCard("bomb");
			this.setD_orderExecutionLog(
					"\nPlayer : " + this.d_playerInitiator.getPlayerName() + " is executing Bomb card on country :  "
							+ l_targetCountryID.getD_countryName() + " with armies :  " + l_noOfArmiesOnTargetCountry
							+ ". New armies: " + l_targetCountryID.getD_armies(),
					"default");
			p_gameState.updateLog(orderExecutionLog(), "effect");
		}
	}

	/**
	 * Gives current bomb order which is being executed.
	 * 
	 * @return advance order command
	 */
	private String currentOrder() {
		return "Bomb card order : " + "bomb" + " " + this.d_targetCountryID;
	}

	/**
	 * Validates whether target country belongs to the Player who executed the order
	 * or not.
	 * 
	 * @return boolean if given advance command is valid or not.
	 */
	@Override
	public boolean valid() {
		Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryID)).findFirst()
				.orElse(null);

		// cannot bomb own territory
		if (!CommonUtil.isNull(l_country)) {
			this.setD_orderExecutionLog(this.currentOrder() + " is not executed since Target country : "
					+ this.d_targetCountryID + " given in bomb command is owned by the player : "
					+ d_playerInitiator.getPlayerName() + " VALIDATES:- You cannot bomb your own territory!", "error");
			return false;
		}

		// Bombs take place after deployments,
		if (!CommonUtil.isNull(d_playerInitiator.getD_ordersToExecute())) {
			for (int l_index = 0; l_index < d_playerInitiator.getD_ordersToExecute().size(); l_index++) {
				Order l_order = d_playerInitiator.getD_ordersToExecute().get(l_index);
				if (l_order.getOrderName().equalsIgnoreCase("deploy")) {
					this.setD_orderExecutionLog(this.currentOrder() + " is not executed because " + "order: "
							+ l_order.getOrderName() + " is pending. VALIDATES :- Bomb take place after deployments.",
							"error");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Printing the Bomb order.
	 */
	@Override
	public void printOrder() {
		this.d_orderExecutionLog = "----------Bomb card order issued by player "
				+ this.d_playerInitiator.getPlayerName() + "----------" + System.lineSeparator()
				+ "Creating a bomb order = " + "on country ID. " + this.d_targetCountryID;
		System.out.println(System.lineSeparator() + this.d_orderExecutionLog);

	}

	/**
	 * Execution log.
	 * 
	 * @return String return execution log
	 */
	public String orderExecutionLog() {
		return this.d_orderExecutionLog;
	}

	/**
	 * Prints and Sets the order execution log.
	 *
	 * @param p_orderExecutionLog String to be set as log
	 * @param p_logType           type of log : error, default
	 */
	public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType) {
		this.d_orderExecutionLog = p_orderExecutionLog;
		if (p_logType.equals("error")) {
			System.err.println(p_orderExecutionLog);
		} else {
			System.out.println(p_orderExecutionLog);
		}
	}

	/**
	 * Pre-validation of card type order.
	 * 
	 * @param p_gameState object of GameState
	 * @return true or false
	 */
	@Override
	public Boolean checkValidOrder(GameState p_gameState) {
		Country l_targetCountry = p_gameState.getD_map().getCountryByName(d_targetCountryID);
		if (l_targetCountry == null) {
			this.setD_orderExecutionLog("Invalid Target Country! Doesn't exist on the map!", "error");
			return false;
		}
		return true;
	}

	/**
	 * Return order name.
	 * 
	 * @return String order name
	 */
	@Override
	public String getOrderName() {
		return "bomb";
	}
}
