package Models;

import Utils.CommonUtil;

/**
 * Implementation of blockade order. The blockade cards change one of your
 * territories to a neutral and tripled the number of armies on that territory.
 */
public class Blockade implements Card {

	/**
	 * Player owning blockade card.
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
	public Blockade(Player p_playerInitiator, String p_targetCountry) {
		this.d_playerInitiator = p_playerInitiator;
		this.d_targetCountryID = p_targetCountry;
	}

	/**
	 * Executes the Blockade order.
	 * 
	 * @param p_gameState current state of the game.
	 */
	@Override
	public void execute(GameState p_gameState) {
		if (valid(p_gameState)) {
			Country l_targetCountryID = p_gameState.getD_map().getCountryByName(d_targetCountryID);
			Integer l_noOfArmiesOnTargetCountry = l_targetCountryID.getD_armies() == 0 ? 1
					: l_targetCountryID.getD_armies();
			l_targetCountryID.setD_armies(l_noOfArmiesOnTargetCountry * 3);

			// change territory to neutral territory
			d_playerInitiator.getD_coutriesOwned().remove(l_targetCountryID);

			Player l_player = p_gameState.getD_players().stream()
					.filter(l_pl -> l_pl.getPlayerName().equalsIgnoreCase("Neutral")).findFirst().orElse(null);

			// assign neutral territory to the existing neutral player.
			if (!CommonUtil.isNull(l_player)) {
				l_player.getD_coutriesOwned().add(l_targetCountryID);
				System.out.println("Neutral territory: " + l_targetCountryID.getD_countryName() + "assigned to the Neutral Player.");
			}

			d_playerInitiator.removeCard("blockade");
			this.setD_orderExecutionLog("\nPlayer : " + this.d_playerInitiator.getPlayerName()
					+ " is executing defensive blockade on Country :  " + l_targetCountryID.getD_countryName()
					+ " with armies :  " + l_targetCountryID.getD_armies(), "default");
			p_gameState.updateLog(orderExecutionLog(), "effect");
		}
	}

	/**
	 * Validates whether target country belongs to the Player who executed the order
	 * or not and also make sure that any attacks, airlifts, or other actions must
	 * happen before the country changes into a neutral.
	 * 
	 * @return boolean if given advance command is valid or not.
	 */
	@Override
	public boolean valid(GameState p_gameState) {

		// Validates whether target country belongs to the Player who executed the order
		// or not
		Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryID)).findFirst()
				.orElse(null);

		if (CommonUtil.isNull(l_country)) {
			this.setD_orderExecutionLog(this.currentOrder() + " is not executed since Target country : "
					+ this.d_targetCountryID + " given in blockade command does not owned to the player : "
					+ d_playerInitiator.getPlayerName()
					+ " The card will have no affect and you don't get the card back.", "error");
			p_gameState.updateLog(orderExecutionLog(), "effect");
			return false;
		}
		return true;
	}

	/**
	 * Print Blockade order.
	 */
	@Override
	public void printOrder() {
		this.d_orderExecutionLog = "----------Blockade card order issued by player "
				+ this.d_playerInitiator.getPlayerName() + "----------" + System.lineSeparator()
				+ "Creating a defensive blockade " + "on country ID: " + this.d_targetCountryID;
		System.out.println(System.lineSeparator() + this.d_orderExecutionLog);

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
	 * @param p_gameState Gamestate
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
	 * @return String
	 */
	@Override
	public String getOrderName() {
		return "blockade";
	}

	/**
	 * Gives current blockade order which is being executed.
	 * 
	 * @return advance order command
	 */
	private String currentOrder() {
		return "Blockade card order : " + "blockade" + " " + this.d_targetCountryID;
	}

	/**
	 * Execution log.
	 * 
	 * @return String return execution log
	 */
	public String orderExecutionLog() {
		return this.d_orderExecutionLog;
	}

}
