package Models;

/**
 * Concrete Command of Command pattern.
 *
 */
public class Deploy implements Order {
	/**
	 * name of the target country.
	 */
	String d_targetCountryName;

	/**
	 * number of armies to be placed.
	 */
	Integer d_numberOfArmiesToPlace;

	/**
	 * Player
	 */
	Player d_playerInitiator;

	/**
	 * State of Game
	 */
	GameState d_gameState;

	/**
	 * The constructor receives all the parameters necessary to implement the order.
	 * These are then encapsulated in the order.
	 * 
	 * @param p_playerInitiator       player that created the order
	 * @param p_targetCountry         country that will receive the new armies
	 * @param p_numberOfArmiesToPlace number of armies to be added
	 * @param p_gameState             Game State
	 */
	public Deploy(Player p_playerInitiator, String p_targetCountry, Integer p_numberOfArmiesToPlace,
			GameState p_gameState) {
		this.d_targetCountryName = p_targetCountry;
		this.d_playerInitiator = p_playerInitiator;
		this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
		this.d_gameState = p_gameState;
	}

	/**
	 * Enacts the order object and makes necessary changes in game state.
	 * 
	 * @param p_gameState current state of the game
	 * @param p_player    player who's order is being executed
	 */

	@Override
	public void execute() {
		if (valid()) {
			for (Country l_country : this.d_gameState.getD_map().getD_countries()) {
				if (l_country.getD_countryName().equalsIgnoreCase(this.d_targetCountryName)) {
					Integer l_armiesToUpdate = l_country.getD_armies() == null ? this.d_numberOfArmiesToPlace
							: l_country.getD_armies() + this.d_numberOfArmiesToPlace;
					l_country.setD_armies(l_armiesToUpdate);
				}
			}

		} else {
			System.err.println("\nDeploy Order = " + "deploy" + " " + this.d_targetCountryName + " "
					+ this.d_numberOfArmiesToPlace + " is not executed since Target country: "
					+ this.d_targetCountryName + " given in deploy command does not belongs to the player : "
					+ d_playerInitiator.getPlayerName());
		}

	}

	/**
	 * Validates whether country given for deploy belongs to players countries or
	 * not.
	 * 
	 * @param p_player player whose order is being executed
	 * @param p_order  order which is being executed
	 * @return true/false
	 */

	@Override
	public boolean valid() {
		Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryName.toString()))
				.findFirst().orElse(null);
		return l_country != null;
	}

	@Override
	public void printOrder() {

	}

}
