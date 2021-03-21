package Models;

import java.util.ArrayList;
import java.util.List;

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
				if (!CommonUtil.isEmpty(l_cont)) {
					l_playerOfTargetCountry = l_player;
				}
			}
			Country l_targetCountry = p_gameState.getD_map().getCountryByName(d_targetCountryName);
			List<Integer> l_attackerArmies = generateRandomArmyUnits(this.d_numberOfArmiesToPlace, "attacker");
			List<Integer> l_defenderArmies = generateRandomArmyUnits(l_targetCountry.getD_armies(), "attacker");
			if(l_playerOfTargetCountry.getPlayerName().equalsIgnoreCase(this.d_playerInitiator.getPlayerName())) {
				moveArmiesToTarget(l_targetCountry);
			} else {
				produceBattleResult(l_targetCountry, l_attackerArmies, l_defenderArmies);				
			}
		}
	}

	/**
	 * @param p_targetCountry
	 */
	private void moveArmiesToTarget(Country p_targetCountry) {
		Integer l_updatedTargetContArmies = p_targetCountry.getD_armies() + this.d_numberOfArmiesToPlace;
		p_targetCountry.setD_armies(l_updatedTargetContArmies);
	}

	/**
	 * @param p_targetCountry
	 * @param p_attackerArmies
	 * @param p_defenderArmies
	 */
	private void produceBattleResult(Country p_targetCountry, List<Integer> p_attackerArmies,
			List<Integer> p_defenderArmies) {
		Integer l_armiesInAttack = this.d_numberOfArmiesToPlace < p_targetCountry.getD_armies()
				? this.d_numberOfArmiesToPlace
				: p_targetCountry.getD_armies();
		Integer l_attackerArmiesLeft = this.d_numberOfArmiesToPlace > p_targetCountry.getD_armies()
				? this.d_numberOfArmiesToPlace - p_targetCountry.getD_armies()
				: 0;
		Integer l_defenderArmiesLeft = this.d_numberOfArmiesToPlace < p_targetCountry.getD_armies()
				? p_targetCountry.getD_armies() - this.d_numberOfArmiesToPlace
				: 0;
		for (int l_i = 0; l_i < l_armiesInAttack; l_i++) {
			if (p_attackerArmies.get(l_i) > p_defenderArmies.get(l_i)) {
				l_attackerArmiesLeft++;
			} else {
				l_defenderArmiesLeft++;
			}
		}
	}

	/**
	 * Validates whether country given for deploy belongs to players countries or
	 * not.
	 * 
	 * @return boolean if given advance command is valid or not.
	 */

	@Override
	public boolean valid() {
		Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_sourceCountryName.toString()))
				.findFirst().orElse(null);
		if (l_country == null) {
			System.err.println("\n" + this.currentOrder() + " is not executed since Source country : "
					+ this.d_sourceCountryName + " given in advance command does not belongs to the player : "
					+ d_playerInitiator.getPlayerName());
			return false;
		}
		if (this.d_numberOfArmiesToPlace > l_country.getD_armies()) {
			System.err.println("\n" + this.currentOrder()
					+ "is not executed as armies given in advance order exceeds armies of source country : "
					+ this.d_sourceCountryName);
			return false;
		}
		if (this.d_numberOfArmiesToPlace == l_country.getD_armies()) {
			System.out.println("\n" + this.currentOrder() + "is not executed as source country : "
					+ this.d_sourceCountryName + " has " + l_country.getD_armies()
					+ " army units and all of those cannot be given advance order, atleast one army unit has to retain the territory.");
			return false;
		}
		return true;
	}

	/**
	 * Gives current advance order which is being executed
	 * 
	 * @return advance order command
	 */
	private String currentOrder() {
		return "Advance Order : " + "advance" + " " + this.d_sourceCountryName + " " + this.d_targetCountryName + " "
				+ this.d_numberOfArmiesToPlace;
	}

	@Override
	public void printOrder() {

	}

	/**
	 * @param l_size
	 * @param l_role
	 * @return
	 */
	private List<Integer> generateRandomArmyUnits(int l_size, String l_role) {
		List<Integer> l_armyList = new ArrayList<>();
		Double l_probability = "attacker".equalsIgnoreCase(l_role) ? 0.6 : 0.7;
		for (int l_i = 0; l_i < l_size; l_i++) {
			int l_randomNumber = getRandomInteger(10, 1);
			Integer l_armyUnit = (int) Math.round(l_randomNumber * l_probability);
			l_armyList.add(l_armyUnit);
		}
		return l_armyList;
	}

	/**
	 * returns random integer between minimum and maximum range
	 * 
	 * @param maximum upper limit
	 * @param minimum lower limit
	 */
	private static int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}

}
