package Models;

import Constants.ApplicationConstants;
import Utils.CommonUtil;

public class Blockade implements Card {

	Player d_playerInitiator;

	String d_targetCountryID;

	/**
	 * Sets the Log containing Information about orders.
	 */
	String d_orderExecutionLog;

	public Blockade() {

	}

	public Blockade(Player p_playerInitiator, String p_targetCountry) {
		this.d_playerInitiator = p_playerInitiator;
		this.d_targetCountryID = p_targetCountry;
	}

	@Override
	public void execute(GameState p_gameState) {
		if (valid()) {
			Country l_targetCountryID = p_gameState.getD_map().getCountryByName(d_targetCountryID);

			Integer l_noOfArmiesOnTargetCountry = l_targetCountryID.getD_armies() == 0 ? 1
					: l_targetCountryID.getD_armies();
			l_targetCountryID.setD_armies(l_noOfArmiesOnTargetCountry * 3);

			// change territory to neutral
			d_playerInitiator.getD_coutriesOwned().remove(l_targetCountryID);
			this.setD_orderExecutionLog("\nPlayer : " + this.d_playerInitiator.getPlayerName()
					+ " is executing defensive blockade on Country : " + l_targetCountryID.getD_countryName()
					+ " with armies : " + l_targetCountryID.getD_armies(), "default");
			p_gameState.updateLog(orderExecutionLog(), "effect");

		}

	}

	@Override
	public boolean valid() {

		Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryID)).findFirst()
				.orElse(null);

		if (CommonUtil.isNull(l_country)) {
			this.setD_orderExecutionLog(this.currentOrder() + " is not executed since Target country : "
					+ this.d_targetCountryID + " given in blockade command does not owned to the player : "
					+ d_playerInitiator.getPlayerName()
					+ " The card will have no affect and you don't get the card back.", "error");
			return false;
		}

		for (int i = 0; i < d_playerInitiator.getD_ordersToExecute().size(); i++) {
			Order order = d_playerInitiator.getD_ordersToExecute().get(i);
			System.out.println("------------" + order.getD_cardName());

			if (ApplicationConstants.BLOCKADEVALIDATION.contains(order.getD_cardName())) {
				this.setD_orderExecutionLog(this.currentOrder() + " is not executed because" + "order:- "
						+ order.getD_cardName()
						+ "is pending. Any attacks, airlifts, or other actions must happen before the country changes into a neutral "
						+ " The card will have no affect and you don't get the card back.", "error");
				return false;
			}
		}

		return true;
	}

	public void printOrder() {

		this.d_orderExecutionLog = "----------Blockade card order issued by player "
				+ this.d_playerInitiator.getPlayerName() + "----------" + System.lineSeparator()
				+ "Creating a defensive blockade with armies = " + "on country ID" + this.d_targetCountryID;
		System.out.println(this.d_orderExecutionLog);

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

	@Override
	public Boolean checkValidOrder(GameState p_gameState) {
		Country l_targetCountry = p_gameState.getD_map().getCountryByName(d_targetCountryID);
		if (l_targetCountry == null) {
			this.setD_orderExecutionLog("Invalid Target Country! Doesn't exist on the map!", "error");
			return false;
		}
		return true;
	}

	@Override
	public String getD_cardName() {
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

	public String orderExecutionLog() {
		return this.d_orderExecutionLog;
	}

}
