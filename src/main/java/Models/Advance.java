package Models;

import Utils.CommonUtil;

/**
 * Concrete Command of Command pattern.
 *
 */
public class Advance implements Order {
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
	 * Player.
	 */
	Player d_playerInitiator;

	/**
	 * The constructor receives all the parameters necessary to implement the order.
	 * These are then encapsulated in the order.
	 * 
	 * @param p_playerInitiator       player that created the order
	 * @param p_sourceCountryName     country from which armies are to be
	 *                                transferred
	 * @param p_targetCountry         country that will receive the new armies
	 * @param p_numberOfArmiesToPlace number of armies to be added
	 */
	public Advance(Player p_playerInitiator, String p_sourceCountryName, String p_targetCountry,
			Integer p_numberOfArmiesToPlace) {
		this.d_targetCountryName = p_targetCountry;
		this.d_sourceCountryName = p_sourceCountryName;
		this.d_playerInitiator = p_playerInitiator;
		this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
	}

	/**
	 * Enacts the order object and makes necessary changes in game state.
	 * 
	 * @param p_gameState current state of the game
	 */

	@Override
	public void execute(GameState p_gameState) {
		if (valid()) {
			Player l_playerOfTargetCountry = null;
			for (Player l_player : p_gameState.getD_players()) {
				String l_cont = l_player.getCountryNames().stream()
						.filter(l_country -> l_country.equalsIgnoreCase(this.d_targetCountryName)).findFirst()
						.orElse(null);
				if(!CommonUtil.isEmpty(l_cont)) {
					l_playerOfTargetCountry = l_player;
				}
			}
		}
	}

	/**
	 * Validates whether country given for deploy belongs to players countries or
	 * not.
	 */

	@Override
	public boolean valid() {
		Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_sourceCountryName.toString()))
				.findFirst().orElse(null);
		if (l_country == null) {
			System.err.println(
					"\nAdvance Order : " + "advance" + " " + this.d_sourceCountryName + " " + this.d_targetCountryName
							+ " " + this.d_numberOfArmiesToPlace + " is not executed since Source country : "
							+ this.d_sourceCountryName + " given in advance command does not belongs to the player : "
							+ d_playerInitiator.getPlayerName());
			return false;
		}
		if (this.d_numberOfArmiesToPlace > l_country.getD_armies()) {
			System.err.println(
					"Given advance order cant be executed as armies in advance order exceeds armies of source country : "
							+ this.d_sourceCountryName);
			return false;
		}
		return true;
	}

	@Override
	public void printOrder() {

	}

}
