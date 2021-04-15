/**
 * 
 */
package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

/**
 * This is the class of Aggressive Player, who gathers all his armies, attacks
 * from his strongest territory and deploys armies to maximize his forces on one
 * country.
 *
 */
public class AggressivePlayer extends PlayerBehaviorStrategy {

	/**
	 * List containing deploy order countries.
	 */
	ArrayList<Country> d_deployCountries = new ArrayList<Country>();

	/**
	 * List containing advance order countries.
	 */
	ArrayList<Country> d_advanceCountries = new ArrayList<Country>();

	/**
	 * Boolean to check if the armies have advance from Player's one territory to
	 * another or not.
	 */
	Boolean d_hasArmiesMoved = false;

	/**
	 * This method creates a new order.
	 * 
	 * @param p_player    object of Player class
	 * @param p_gameState object of GameState class
	 * 
	 * @return String form of order
	 */
	@Override
	public String createOrder(Player p_player, GameState p_gameState) {
		String l_command;

		super.setObjects(p_player, p_gameState);

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
		Random l_Random = new Random();
		// get strongest country then deploy
		Country l_strongestCountry = getStrongestCountry(p_player, d_gameState);
		d_deployCountries.add(l_strongestCountry);
		int l_armiesToDeploy = l_Random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;
		System.out.println("deploy " + l_strongestCountry.getD_countryName() + " " + l_armiesToDeploy);
		return String.format("deploy %s %d", l_strongestCountry.getD_countryName(), l_armiesToDeploy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createAdvanceOrder(Player p_player, GameState p_gameState) {
		// move armies from its neighbors to maximize armies on source country
		Country l_randomSourceCountry = getRandomCountry(d_deployCountries);
		System.out.println("armies before on country " + l_randomSourceCountry.getD_countryName() + " "
				+ l_randomSourceCountry.getD_armies());
		moveArmiesFromItsNeighbors(p_player, l_randomSourceCountry, p_gameState);
		System.out.println("armies after on country " + l_randomSourceCountry.getD_countryName() + " "
				+ l_randomSourceCountry.getD_armies());
		Country l_randomTargetCountry = getRandomNeighborEnemy(p_player, l_randomSourceCountry, d_gameState);

		// check error here......
		int l_armiesToSend = l_randomSourceCountry.getD_armies() > 1 ? l_randomSourceCountry.getD_armies() : 1;

		// attacks with strongest country
		System.out.println("advance " + l_randomSourceCountry.getD_countryName() + " "
				+ l_randomTargetCountry.getD_countryName() + " " + l_armiesToSend);
		return "advance " + l_randomSourceCountry.getD_countryName() + " " + l_randomTargetCountry.getD_countryName()
				+ " " + l_armiesToSend;

	}

	public void moveArmiesFromItsNeighbors(Player p_player, Country l_randomSourceCountry, GameState p_gameState) {
		List<Integer> l_adjacentCountryIds = l_randomSourceCountry.getD_adjacentCountryIds();
		List<Country> l_listOfNeighbors = new ArrayList<Country>();
		for (int l_index = 0; l_index < l_adjacentCountryIds.size(); l_index++) {
			Country l_country = p_gameState.getD_map()
					.getCountry(l_randomSourceCountry.getD_adjacentCountryIds().get(l_index));
			// check if neighbor belongs to player and then add to list
			if (p_player.getD_coutriesOwned().contains(l_country)) {
				l_listOfNeighbors.add(l_country);
			}
		}

		// send armies from neighbor to source country
		for (Country l_con : l_listOfNeighbors) {
			int armies = l_randomSourceCountry.getD_armies() > 0
					? l_randomSourceCountry.getD_armies() + (l_con.getD_armies())
					: (l_con.getD_armies());
			// check...
			l_randomSourceCountry.setD_armies(armies);

		}
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
	 * This method return weakest neighbor where Source country can advance armies
	 * to this weakest country.
	 * 
	 * @param l_randomSourceCountry Source country
	 * @param p_gameState           GameState
	 * @return weakest neighbor
	 */
	public Country getRandomNeighborEnemy(Player p_player, Country l_randomSourceCountry, GameState p_gameState) {
		List<Integer> l_adjacentCountryIds = l_randomSourceCountry.getD_adjacentCountryIds();
		List<Country> l_listOfEnemyNeighbors = new ArrayList<Country>();
		for (int l_index = 0; l_index < l_adjacentCountryIds.size(); l_index++) {
			Country l_country = p_gameState.getD_map()
					.getCountry(l_randomSourceCountry.getD_adjacentCountryIds().get(l_index));
			// country must not belong to aggressive player
			if (!p_player.getD_coutriesOwned().contains(l_country)) {
				l_listOfEnemyNeighbors.add(l_country);
			}
		}
		Country l_randomEnemyNeighbor = getRandomCountry(l_listOfEnemyNeighbors);

		return l_randomEnemyNeighbor;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createCardOrder(Player p_player, GameState p_gameState, String p_cardName) {
		return p_cardName;
//		Country l_defendingCountry = null;
//		Country l_attackingCountry = getStrongestCountryWithAdjacentCountry(p_player, p_gameState);
//
//		int l_noOfArmies = l_attackingCountry.getD_armies();
//		List<Integer> l_adjacentCountriesList = l_attackingCountry.getD_adjacentCountryIds();
//
//		Player l_adjPlayer = null;
//		for (Integer l_adjCountryID : l_adjacentCountriesList) {
//			l_adjPlayer = getCountryPlayer(p_gameState, l_adjCountryID);
//			Country l_adjPlayerCountry = getSelectedCountry(l_adjPlayer, l_adjCountryID);
//
//			if (l_adjPlayerCountry.getD_armies() <= l_noOfArmies
//					&& !(p_player.getPlayerName().equalsIgnoreCase(l_adjPlayer.getPlayerName()))) {
//				l_noOfArmies = l_adjPlayerCountry.getD_armies();
//				l_defendingCountry = l_adjPlayerCountry;
//			}
//		}
//
//		if (l_attackingCountry.getD_armies() > 1) {
//			l_noOfArmies = l_attackingCountry.getD_armies() - 1;
//		} else {
//			l_noOfArmies = 0;
//		}
//
//		if (l_defendingCountry != null) {
//			switch (p_cardName) {
//			case "bomb":
//				return "bomb " + l_defendingCountry.getD_countryName();
//			case "blockade":
//				return "blockade " + l_attackingCountry.getD_countryName();
//			case "airlift":
//				return "airlift " + l_attackingCountry.getD_countryName() + " " + l_defendingCountry + " "
//						+ l_noOfArmies;
//			case "negotiate":
//				return "negotiate" + " " + l_adjPlayer;
//			}
//		}
//		return null;
	}

	/**
	 * This method returns the player behavior.
	 * 
	 * @return String player behavior
	 */
	@Override
	public String getPlayerBehavior() {
		return "Aggressive";
	}

	public Country getStrongestCountry(Player p_player, GameState p_gameState) {
		List<Country> l_countriesOwnedByPlayer = p_player.getD_coutriesOwned();
		Country l_Country = calculateStrongestCountry(l_countriesOwnedByPlayer);
		return l_Country;
	}

	public Country calculateStrongestCountry(List<Country> l_listOfCountries) {
		LinkedHashMap<Country, Integer> l_CountryWithArmies = new LinkedHashMap<Country, Integer>();

		int l_largestNoOfArmies;
		Country l_Country = null;

		// return strongest country from owned countries of player.
		for (Country l_country : l_listOfCountries) {
			l_CountryWithArmies.put(l_country, l_country.getD_armies());
		}
		l_largestNoOfArmies = Collections.max(l_CountryWithArmies.values());
		for (Entry<Country, Integer> entry : l_CountryWithArmies.entrySet()) {
			if (entry.getValue().equals(l_largestNoOfArmies)) {
				return entry.getKey();
			}
		}
		return l_Country;

	}

}
