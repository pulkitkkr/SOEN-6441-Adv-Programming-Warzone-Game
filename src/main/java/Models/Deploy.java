package Models;

import java.io.Serializable;

/**
 * Concrete Command of Command pattern.
 *
 */
public class Deploy implements Order, Serializable {
	/**
	 * name of the target country.
	 */
	String d_targetCountryName;

	/**
	 * number of armies to be placed.
	 */
	Integer d_numberOfArmiesToPlace;

	/**
	 * Player Initiator.
	 */
	Player d_playerInitiator;

	/**
	 * Sets the Log containing Information about orders.
	 */
	String d_orderExecutionLog;

	/**
	 * The constructor receives all the parameters necessary to implement the order.
	 * These are then encapsulated in the order.
	 * 
	 * @param p_playerInitiator       player that created the order
	 * @param p_targetCountry         country that will receive the new armies
	 * @param p_numberOfArmiesToPlace number of armies to be added
	 */
	public Deploy(Player p_playerInitiator, String p_targetCountry, Integer p_numberOfArmiesToPlace) {
		this.d_targetCountryName = p_targetCountry;
		this.d_playerInitiator = p_playerInitiator;
		this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
	}

	/**
	 * Executes the deploy order.
	 * 
	 * @param p_gameState current state of the game.
	 */
	@Override
	public void execute(GameState p_gameState) {

		if (valid(p_gameState)) {
			for (Country l_country : p_gameState.getD_map().getD_countries()) {
				if (l_country.getD_countryName().equalsIgnoreCase(this.d_targetCountryName)) {
					Integer l_armiesToUpdate = l_country.getD_armies() == null ? this.d_numberOfArmiesToPlace
							: l_country.getD_armies() + this.d_numberOfArmiesToPlace;
					l_country.setD_armies(l_armiesToUpdate);
					this.setD_orderExecutionLog(+l_armiesToUpdate
							+ " armies have been deployed successfully on country : " + l_country.getD_countryName(),
							"default");
				}
			}

		} else {
			this.setD_orderExecutionLog("Deploy Order = " + "deploy" + " " + this.d_targetCountryName + " "
					+ this.d_numberOfArmiesToPlace + " is not executed since Target country: "
					+ this.d_targetCountryName + " given in deploy command does not belongs to the player : "
					+ d_playerInitiator.getPlayerName(), "error");
			d_playerInitiator.setD_noOfUnallocatedArmies(
					d_playerInitiator.getD_noOfUnallocatedArmies() + this.d_numberOfArmiesToPlace);
		}
		p_gameState.updateLog(orderExecutionLog(), "effect");
	}

	/**
	 * Validates whether country given for deploy belongs to players countries or
	 * not.
	 */
	@Override
	public boolean valid(GameState p_gameState) {
		Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryName.toString()))
				.findFirst().orElse(null);
		return l_country != null;
	}

	/**
	 * Prints deploy Order.
	 */
	@Override
	public void printOrder() {
		this.d_orderExecutionLog = "\n---------- Deploy order issued by player " + this.d_playerInitiator.getPlayerName()+" ----------\n"+System.lineSeparator()+"Deploy " + this.d_numberOfArmiesToPlace + " armies to " + this.d_targetCountryName;
		System.out.println(this.d_orderExecutionLog);
	}

	/**
	 * Gets order execution log.
	 */
	@Override
	public String orderExecutionLog() {
		return d_orderExecutionLog;
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
	 * Return order name.
	 * 
	 * @return String
	 */
	@Override
	public String getOrderName() {
		return "deploy";
	}
}
