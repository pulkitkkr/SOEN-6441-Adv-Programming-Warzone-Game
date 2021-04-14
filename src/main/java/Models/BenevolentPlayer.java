package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

/**
 * This is the class of Benevolent Player who focuses only on defending his own
 * countries and will never attack.
 *
 */
public class BenevolentPlayer extends PlayerBehaviorStrategy {

	/**
	 * List containing deploy order countries.
	 */
	ArrayList<Country> d_deployCountries = new ArrayList<Country>();

	/**
	 * This method creates a new order.
	 * 
	 * @param p_player    object of Player class
	 * @param p_gameState object of GameState class
	 * 
	 * @return Order object of order class
	 */
	@Override
	public String createOrder(Player p_player, GameState p_gameState) {
		super.setObjects(p_player, p_gameState);
		String l_command = null;
		if (p_player.getD_noOfUnallocatedArmies() > 0) {
			l_command = createDeployOrder(p_player);
		} else {
			if (p_player.getD_cardsOwnedByPlayer().size() > 0) {
				Random l_random = new Random();
				int l_randomIndex = l_random.nextInt(p_player.getD_cardsOwnedByPlayer().size() + 1);
				if (l_randomIndex == p_player.getD_cardsOwnedByPlayer().size()) {
					l_command = createAdvanceOrder(p_player, p_gameState);
				} else {
					l_command = createCardOrder(p_player, p_gameState,
							p_player.getD_cardsOwnedByPlayer().get(l_randomIndex));
				}
			} else {
				l_command = createAdvanceOrder(p_player, p_gameState);
			}
		}
		return l_command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createDeployOrder(Player p_player) {
		Country l_weakestCountry = getWeakestCountry(p_player);
		d_deployCountries.add(l_weakestCountry);

		Random l_random = new Random();
		int l_armiesToDeploy = l_random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;

		System.out.println("deploy " + l_weakestCountry.getD_countryName() + " " + l_armiesToDeploy);
		return String.format("deploy %s %d", l_weakestCountry.getD_countryName(), l_armiesToDeploy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createAdvanceOrder(Player p_player, GameState p_gameState) {
		// advance on weakest country
		int l_armiesToSend;
		Random l_random = new Random();

		Country l_randomSourceCountry = getRandomCountry(d_deployCountries);
		Country l_weakestTargetCountry = getWeakestNeighbor(l_randomSourceCountry);

		if (l_randomSourceCountry.getD_armies() > 1) {
			l_armiesToSend = l_random.nextInt(l_randomSourceCountry.getD_armies() - 1) + 1;
		} else {
			l_armiesToSend = 1;
		}

		System.out.println("advance " + l_randomSourceCountry.getD_countryName() + " "
				+ l_weakestTargetCountry.getD_countryName() + " " + l_armiesToSend);
		return "advance " + l_randomSourceCountry.getD_countryName() + " " + l_weakestTargetCountry.getD_countryName()
				+ " " + l_armiesToSend;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createCardOrder(Player p_player, GameState p_gameState, String p_cardName) {
		int l_armiesToSend;
		Random l_random = new Random();
		Country l_randomOwnCountry = getRandomCountry(p_player.getD_coutriesOwned());
		Country l_randomEnemyNeighbor = p_gameState.getD_map()
				.getCountry(randomEnemyNeighbor(p_player, l_randomOwnCountry)
						.get(l_random.nextInt(randomEnemyNeighbor(p_player, l_randomOwnCountry).size())));

		if (l_randomOwnCountry.getD_armies() > 1) {
			l_armiesToSend = l_random.nextInt(l_randomOwnCountry.getD_armies() - 1) + 1;
		} else {
			l_armiesToSend = 1;
		}

		switch (p_cardName) {
		case "bomb":
			System.out.println("I am benevolent player, I don't hurt anyone.");
			break;
		case "blockade":
			return "blockade " + l_randomOwnCountry.getD_countryName();
		case "airlift":
			return "airlift " + l_randomOwnCountry.getD_countryName() + " "
					+ getRandomCountry(p_player.getD_coutriesOwned()) + " " + l_armiesToSend;
		case "negotiate":
			return "negotiate" + p_player + " " + l_randomEnemyNeighbor;
		}
		return null;
	}

	/**
	 * This method returns the player behavior.
	 * 
	 * @return String player behavior
	 */
	@Override
	public String getPlayerBehavior() {
		return "Benevolent";
	}

	/**
	 * This method returns random country.
	 * 
	 * @param p_listOfCountries list of countries
	 * @return return country
	 */
	private Country getRandomCountry(List<Country> p_listOfCountries) {
		Random l_random = new Random();
		return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
	}

	/**
	 * This method return weakest Country where benevolent player can deploy armies.
	 * 
	 * @param p_player Player
	 * @return weakest country
	 */
	public Country getWeakestCountry(Player p_player) {
		List<Country> l_countriesOwnedByPlayer = p_player.getD_coutriesOwned();
		Country l_Country = calculateWeakestCountry(l_countriesOwnedByPlayer);
		return l_Country;
	}

	/**
	 * This method return weakest neighbor where Source country can advance armies
	 * to this weakest country.
	 * 
	 * @param l_randomSourceCountry Source country
	 * @return weakest neighbor
	 */
	public Country getWeakestNeighbor(Country l_randomSourceCountry) {
		List<Integer> l_adjacentCountryIds = l_randomSourceCountry.getD_adjacentCountryIds();
		List<Country> l_listOfNeighbors = new ArrayList<Country>();
		for (int l_index = 0; l_index < l_adjacentCountryIds.size(); l_index++) {
			Country l_country = d_gameState.getD_map()
					.getCountry(l_randomSourceCountry.getD_adjacentCountryIds().get(l_index));
			l_listOfNeighbors.add(l_country);
		}
		Country l_Country = calculateWeakestCountry(l_listOfNeighbors);
		return l_Country;
	}

	/**
	 * This method calculates weakest country.
	 * 
	 * @param l_listOfCountries list of countries
	 * @return weakest country
	 */
	public Country calculateWeakestCountry(List<Country> l_listOfCountries) {
		LinkedHashMap<Country, Integer> l_CountryWithArmies = new LinkedHashMap<Country, Integer>();

		int l_smallestNoOfArmies;
		Country l_Country = null;

		// return weakest country from owned countries of player.
		for (Country l_country : l_listOfCountries) {
			l_CountryWithArmies.put(l_country, l_country.getD_armies());
		}
		l_smallestNoOfArmies = Collections.min(l_CountryWithArmies.values());
		for (Entry<Country, Integer> entry : l_CountryWithArmies.entrySet()) {
			if (entry.getValue().equals(l_smallestNoOfArmies)) {
				return entry.getKey();
			}
		}
		return l_Country;

	}

	/**
	 * This method return List of Country Ids of random enemy neighbors.
	 * 
	 * @param p_player  Player
	 * @param p_country Country
	 * @return List of Ids.
	 */
	private ArrayList<Integer> randomEnemyNeighbor(Player p_player, Country p_country) {
		ArrayList<Integer> l_enemyNeighbors = new ArrayList<Integer>();

		for (Integer l_countryID : p_country.getD_adjacentCountryIds()) {
			if (!p_player.getCountryIDs().contains(l_countryID))
				l_enemyNeighbors.add(l_countryID);
		}
		return l_enemyNeighbors;
	}

}
