package Models;

import java.util.ArrayList;
import java.util.List;

import Services.PlayerService;
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
			Player l_playerOfTargetCountry = getPlayerOfTargetCountry(p_gameState);
			Country l_targetCountry = p_gameState.getD_map().getCountryByName(d_targetCountryName);
			Country l_sourceCountry = p_gameState.getD_map().getCountryByName(d_sourceCountryName);
			Integer l_sourceArmiesToUpdate = l_sourceCountry.getD_armies() - this.d_numberOfArmiesToPlace;
			l_sourceCountry.setD_armies(l_sourceArmiesToUpdate);
			
			if (l_playerOfTargetCountry.getPlayerName().equalsIgnoreCase(this.d_playerInitiator.getPlayerName())) {
				deployArmiesToTarget(l_targetCountry);
			} else if (l_targetCountry.getD_armies() == 0) {
				l_targetCountry.setD_armies(d_numberOfArmiesToPlace);
				l_playerOfTargetCountry.getD_coutriesOwned().remove(l_targetCountry);
				this.d_playerInitiator.getD_coutriesOwned().add(l_targetCountry);
				System.out.println("\nPlayer : " + this.d_playerInitiator.getPlayerName() + " is assigned with Country : "
						+ l_targetCountry.getD_countryName() + " and armies : " + l_targetCountry.getD_armies());
				this.updateContinents(this.d_playerInitiator, l_playerOfTargetCountry, p_gameState);
			} else {
				Integer l_armiesInAttack = this.d_numberOfArmiesToPlace < l_targetCountry.getD_armies()
						? this.d_numberOfArmiesToPlace
						: l_targetCountry.getD_armies();

				List<Integer> l_attackerArmies = generateRandomArmyUnits(l_armiesInAttack, "attacker");
				List<Integer> l_defenderArmies = generateRandomArmyUnits(l_armiesInAttack, "defender");
				this.produceBattleResult(l_sourceCountry, l_targetCountry, l_attackerArmies, l_defenderArmies,
						l_playerOfTargetCountry);
				this.updateContinents(this.d_playerInitiator, l_playerOfTargetCountry, p_gameState);
			}
		}
	}

	/**
	 * Retrieves the player owning the target country.
	 * 
	 * @param p_gameState current state of the game
	 * @return target country player
	 */
	private Player getPlayerOfTargetCountry(GameState p_gameState) {
		Player l_playerOfTargetCountry = null;
		for (Player l_player : p_gameState.getD_players()) {
			String l_cont = l_player.getCountryNames().stream()
					.filter(l_country -> l_country.equalsIgnoreCase(this.d_targetCountryName)).findFirst().orElse(null);
			if (!CommonUtil.isEmpty(l_cont)) {
				l_playerOfTargetCountry = l_player;
			}
		}
		return l_playerOfTargetCountry;
	}

	/**
	 * If the target territory is of same player, it will just move armies.
	 * 
	 * @param p_targetCountry country to which armies have to be moved
	 */
	private void deployArmiesToTarget(Country p_targetCountry) {
		Integer l_updatedTargetContArmies = p_targetCountry.getD_armies() + this.d_numberOfArmiesToPlace;
		p_targetCountry.setD_armies(l_updatedTargetContArmies);
	}

	/**
	 * Produces the battle result of advance order based on attackers and defenders
	 * army units.
	 * 
	 * @param p_sourceCountry         country from which armies have to be moved
	 * @param p_targetCountry         country to which armies have to be moved
	 * @param p_attackerArmies        random army numbers of attacker
	 * @param p_defenderArmies        random army numbers of defender
	 * @param p_playerOfTargetCountry player owning the target country
	 */
	private void produceBattleResult(Country p_sourceCountry, Country p_targetCountry, List<Integer> p_attackerArmies,
			List<Integer> p_defenderArmies, Player p_playerOfTargetCountry) {
		Integer l_attackerArmiesLeft = this.d_numberOfArmiesToPlace > p_targetCountry.getD_armies()
				? this.d_numberOfArmiesToPlace - p_targetCountry.getD_armies()
				: 0;
		Integer l_defenderArmiesLeft = this.d_numberOfArmiesToPlace < p_targetCountry.getD_armies()
				? p_targetCountry.getD_armies() - this.d_numberOfArmiesToPlace
				: 0;
		for (int l_i = 0; l_i < p_attackerArmies.size(); l_i++) {
			if (p_attackerArmies.get(l_i) > p_defenderArmies.get(l_i)) {
				l_attackerArmiesLeft++;
			} else {
				l_defenderArmiesLeft++;
			}
		}
		this.handleSurvivingArmies(l_attackerArmiesLeft, l_defenderArmiesLeft, p_sourceCountry, p_targetCountry,
				p_playerOfTargetCountry);
	}

	/**
	 * Process surviving armies and transferring ownership of countries.
	 * 
	 * @param p_attackerArmiesLeft    remaining attacking armies from battle
	 * @param p_defenderArmiesLeft    remaining defending armies from battle
	 * @param p_sourceCountry         source country
	 * @param p_targetCountry         target country
	 * @param p_playerOfTargetCountry player owning the target country
	 */
	private void handleSurvivingArmies(Integer p_attackerArmiesLeft, Integer p_defenderArmiesLeft,
			Country p_sourceCountry, Country p_targetCountry, Player p_playerOfTargetCountry) {
		if (p_defenderArmiesLeft == 0) {
			p_targetCountry.setD_armies(p_attackerArmiesLeft);
			p_playerOfTargetCountry.getD_coutriesOwned().remove(p_targetCountry);
			this.d_playerInitiator.getD_coutriesOwned().add(p_targetCountry);
			System.out.println("/nPlayer : " + this.d_playerInitiator.getPlayerName() + " is assigned with Country : "
					+ p_targetCountry.getD_countryName() + " and armies : " + p_targetCountry.getD_armies());
		} else {
			p_targetCountry.setD_armies(p_defenderArmiesLeft);

			Integer l_sourceArmiesToUpdate = p_sourceCountry.getD_armies() + p_attackerArmiesLeft;
			p_sourceCountry.setD_armies(l_sourceArmiesToUpdate);
			System.out.println(
					"/nCountry : " + p_targetCountry.getD_countryName() + " is left with " + p_targetCountry.getD_armies()
							+ " armies and is still owned by player : " + p_playerOfTargetCountry.getPlayerName());
			System.out.println(
					"Country : " + p_sourceCountry.getD_countryName() + " is left with " + p_sourceCountry.getD_armies()
							+ " armies and is still owned by player : " + this.d_playerInitiator.getPlayerName());
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
					+ " is not executed as armies given in advance order exceeds armies of source country : "
					+ this.d_sourceCountryName);
			return false;
		}
		if (this.d_numberOfArmiesToPlace == l_country.getD_armies()) {
			System.err.println("\n" + this.currentOrder() + " is not executed as source country : "
					+ this.d_sourceCountryName + " has " + l_country.getD_armies()
					+ " army units and all of those cannot be given advance order, atleast one army unit has to retain the territory.");
			return false;
		}
		return true;
	}

	/**
	 * Gives current advance order which is being executed.
	 * 
	 * @return advance order command
	 */
	private String currentOrder() {
		return "Advance Order : " + "advance" + " " + this.d_sourceCountryName + " " + this.d_targetCountryName + " "
				+ this.d_numberOfArmiesToPlace;
	}

	@Override
	public void printOrder() {
		System.out.println("\n----------Advance order issued by player " + this.d_playerInitiator.getPlayerName()+"----------");
		System.out.println("Move " + this.d_numberOfArmiesToPlace + " armies from " + this.d_sourceCountryName + " to "
				+ this.d_targetCountryName);
	}

	/**
	 * Generates random army units based attacker and defender's winning
	 * probability.
	 * 
	 * @param l_size number of random armies to be generated
	 * @param l_role armies to be generated is for defender or for attacker
	 * @return List random army units based on probability
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
	 * returns random integer between minimum and maximum range.
	 * 
	 * @param maximum upper limit
	 * @param minimum lower limit
	 * @return int random number
	 */
	private static int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}

	/**
	 * Updates continents of players based on battle results.
	 * 
	 * @param p_playerOfSourceCountry player owning source country
	 * @param p_playerOfTargetCountry player owning target country
	 * @param p_gameState             current state of the game
	 */
	private void updateContinents(Player p_playerOfSourceCountry, Player p_playerOfTargetCountry,
			GameState p_gameState) {
		System.out.println("Updating continents of players involved in battle...");
		List<Player> l_playesList = new ArrayList<>();
		p_playerOfSourceCountry.setD_continentsOwned(new ArrayList<>());
		p_playerOfTargetCountry.setD_continentsOwned(new ArrayList<>());
		l_playesList.add(p_playerOfSourceCountry);
		l_playesList.add(p_playerOfTargetCountry);

		PlayerService l_playerService = new PlayerService();
		l_playerService.performContinentAssignment(l_playesList, p_gameState.getD_map().getD_continents());
	}
}
