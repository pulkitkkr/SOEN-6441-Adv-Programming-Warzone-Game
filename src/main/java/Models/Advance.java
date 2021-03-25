package Models;

import Services.PlayerService;
import Utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

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
	 * Sets the Log containing Information about orders.
	 */
	String d_orderExecutionLog;

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
				conquerTargetCountry(p_gameState, l_playerOfTargetCountry, l_targetCountry);
			} else {
				produceOrderResult(p_gameState, l_playerOfTargetCountry, l_targetCountry, l_sourceCountry);
			}
		} else {
			p_gameState.updateLog(orderExecutionLog(), "effect");
		}
	}

	/**
	 * Produces advance order result.
	 * 
	 * @param p_gameState             current state of the game
	 * @param p_playerOfTargetCountry player of the target country
	 * @param p_targetCountry         target country given in order
	 * @param p_sourceCountry         source country given in order
	 */
	private void produceOrderResult(GameState p_gameState, Player p_playerOfTargetCountry, Country p_targetCountry,
			Country p_sourceCountry) {
		Integer l_armiesInAttack = this.d_numberOfArmiesToPlace < p_targetCountry.getD_armies()
				? this.d_numberOfArmiesToPlace
				: p_targetCountry.getD_armies();

		List<Integer> l_attackerArmies = generateRandomArmyUnits(l_armiesInAttack, "attacker");
		List<Integer> l_defenderArmies = generateRandomArmyUnits(l_armiesInAttack, "defender");
		this.produceBattleResult(p_sourceCountry, p_targetCountry, l_attackerArmies, l_defenderArmies,
				p_playerOfTargetCountry);

		p_gameState.updateLog(orderExecutionLog(), "effect");
		this.updateContinents(this.d_playerInitiator, p_playerOfTargetCountry, p_gameState);
	}

	/**
	 * Conquers Target Country when target country doesn't have any army.
	 * 
	 * @param p_gameState             Current state of the game
	 * @param p_playerOfTargetCountry player owning the target country
	 * @param p_targetCountry         target country of the battle
	 */
	private void conquerTargetCountry(GameState p_gameState, Player p_playerOfTargetCountry, Country p_targetCountry) {
		p_targetCountry.setD_armies(d_numberOfArmiesToPlace);
		p_playerOfTargetCountry.getD_coutriesOwned().remove(p_targetCountry);
		this.d_playerInitiator.getD_coutriesOwned().add(p_targetCountry);
		this.setD_orderExecutionLog(
				"Player : " + this.d_playerInitiator.getPlayerName() + " is assigned with Country : "
						+ p_targetCountry.getD_countryName() + " and armies : " + p_targetCountry.getD_armies(),
				"default");
		p_gameState.updateLog(orderExecutionLog(), "effect");
		this.updateContinents(this.d_playerInitiator, p_playerOfTargetCountry, p_gameState);
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
	public void deployArmiesToTarget(Country p_targetCountry) {
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
	public void handleSurvivingArmies(Integer p_attackerArmiesLeft, Integer p_defenderArmiesLeft,
			Country p_sourceCountry, Country p_targetCountry, Player p_playerOfTargetCountry) {
		if (p_defenderArmiesLeft == 0) {
			p_playerOfTargetCountry.getD_coutriesOwned().remove(p_targetCountry);
			p_targetCountry.setD_armies(p_attackerArmiesLeft);
			this.d_playerInitiator.getD_coutriesOwned().add(p_targetCountry);
			this.setD_orderExecutionLog(
					"Player : " + this.d_playerInitiator.getPlayerName() + " is assigned with Country : "
							+ p_targetCountry.getD_countryName() + " and armies : " + p_targetCountry.getD_armies(),
					"default");
		} else {
			p_targetCountry.setD_armies(p_defenderArmiesLeft);

			Integer l_sourceArmiesToUpdate = p_sourceCountry.getD_armies() + p_attackerArmiesLeft;
			p_sourceCountry.setD_armies(l_sourceArmiesToUpdate);

			String l_country1 = "Country : " + p_targetCountry.getD_countryName() + " is left with "
					+ p_targetCountry.getD_armies() + " armies and is still owned by player : "
					+ p_playerOfTargetCountry.getPlayerName();
			String l_country2 = "Country : " + p_sourceCountry.getD_countryName() + " is left with "
					+ p_sourceCountry.getD_armies() + " armies and is still owned by player : "
					+ this.d_playerInitiator.getPlayerName();
			this.setD_orderExecutionLog(l_country1 + System.lineSeparator() + l_country2, "default");
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
			this.setD_orderExecutionLog(this.currentOrder() + " is not executed since Source country : "
					+ this.d_sourceCountryName + " given in advance command does not belongs to the player : "
					+ d_playerInitiator.getPlayerName(), "error");
			return false;
		}
		if (this.d_numberOfArmiesToPlace > l_country.getD_armies()) {
			this.setD_orderExecutionLog(this.currentOrder()
					+ " is not executed as armies given in advance order exceeds armies of source country : "
					+ this.d_sourceCountryName, "error");
			return false;
		}
		if (this.d_numberOfArmiesToPlace == l_country.getD_armies()) {
			this.setD_orderExecutionLog(this.currentOrder() + " is not executed as source country : "
					+ this.d_sourceCountryName + " has " + l_country.getD_armies()
					+ " army units and all of those cannot be given advance order, atleast one army unit has to retain the territory.",
					"error");
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

	/**
	 * Prints information about order.
	 */
	@Override
	public void printOrder() {
		this.d_orderExecutionLog = "\n---------- Advance order issued by player " + this.d_playerInitiator.getPlayerName()
				+ " ----------\n" + System.lineSeparator() + "Move " + this.d_numberOfArmiesToPlace + " armies from "
				+ this.d_sourceCountryName + " to " + this.d_targetCountryName;
		System.out.println(this.d_orderExecutionLog);
	}

	/**
	 * Gets updated execution log.
	 */
	@Override
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
	 * Generates random army units based attacker and defender's winning
	 * probability.
	 * 
	 * @param p_size number of random armies to be generated
	 * @param p_role armies to be generated is for defender or for attacker
	 * @return List random army units based on probability
	 */
	private List<Integer> generateRandomArmyUnits(int p_size, String p_role) {
		List<Integer> l_armyList = new ArrayList<>();
		Double l_probability = "attacker".equalsIgnoreCase(p_role) ? 0.6 : 0.7;
		for (int l_i = 0; l_i < p_size; l_i++) {
			int l_randomNumber = getRandomInteger(10, 1);
			Integer l_armyUnit = (int) Math.round(l_randomNumber * l_probability);
			l_armyList.add(l_armyUnit);
		}
		return l_armyList;
	}

	/**
	 * returns random integer between minimum and maximum range.
	 * 
	 * @param p_maximum upper limit
	 * @param p_minimum lower limit
	 * @return int random number
	 */
	private static int getRandomInteger(int p_maximum, int p_minimum) {
		return ((int) (Math.random() * (p_maximum - p_minimum))) + p_minimum;
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
