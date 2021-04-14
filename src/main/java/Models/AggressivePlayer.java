/**
 * 
 */
package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is the class of Aggressive Player, who gathers all his armies, attacks
 * from his strongest territory and deploys armies to maximize his forces on one
 * country.
 *
 */
public class AggressivePlayer extends PlayerBehaviorStrategy {

	/**
	 * List of countries.
	 */
	ArrayList<Country> d_listOfCountries = new ArrayList<Country>();

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
		int l_noOfArmies = 0;
		int l_armiesToDeploy = 0;
		Country l_fromCountry = null;

		Country l_strongestCountry = getStrongestCountryWithAdjacentCountry(p_player, d_gameState);
		Country l_strongestPlayerCountry = getStrongestCountry(p_player, d_gameState);

		// d_listOfCountries.add(l_randomCountry);

		if (l_strongestCountry.getD_countryName().equalsIgnoreCase(l_strongestPlayerCountry.getD_countryName())) {
			l_noOfArmies = l_strongestCountry.getD_armies();

			for (Country l_country : p_player.getD_coutriesOwned()) {
				if (l_country.getD_armies() <= l_noOfArmies && l_country.getD_armies() > 1
						&& !l_country.getD_countryName().equalsIgnoreCase(l_strongestCountry.getD_countryName())) {
					l_noOfArmies = l_country.getD_armies();
					l_fromCountry = l_country;
					l_armiesToDeploy = l_fromCountry.getD_armies() - 1;
				}
			}
		} else {
			l_noOfArmies = l_strongestCountry.getD_armies();
			l_fromCountry = l_strongestPlayerCountry;
			l_armiesToDeploy = l_fromCountry.getD_armies() - 1;
		}

		if (l_fromCountry != null && l_strongestCountry != null) {
			// moveArmies-deployOrder(d_gameState, l_fromCountry, l_strongestCountry,
			// l_armiesToDeploy);
		}

		return String.format("deploy %s %d", l_strongestCountry.getD_countryName(), l_armiesToDeploy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createAdvanceOrder(Player p_player, GameState p_gameState) {
		int l_noOfArmies = 0;
		Country l_defendingCountry = null;
		Country l_attackingCountry = getStrongestCountryWithAdjacentCountry(p_player, p_gameState);

		l_noOfArmies = l_attackingCountry.getD_armies();
		List<Integer> l_adjacentCountriesList = l_attackingCountry.getD_adjacentCountryIds();

		for (Integer l_adjCountryID : l_adjacentCountriesList) {
			Player l_adjPlayer = getCountryPlayer(p_gameState, l_adjCountryID);
			Country l_adjPlayerCountry = getSelectedCountry(l_adjPlayer, l_adjCountryID);

			if (l_adjPlayerCountry.getD_armies() <= l_noOfArmies
					&& !(p_player.getPlayerName().equalsIgnoreCase(l_adjPlayer.getPlayerName()))) {
				l_noOfArmies = l_adjPlayerCountry.getD_armies();
				l_defendingCountry = l_adjPlayerCountry;
			}
		}
		if (l_defendingCountry != null) {
			// initiateAttack-advanceOrder(p_gameState, l_attackingCountry, l_defendingCountry);
		}

		return "advance " + l_attackingCountry.getD_countryName() + " " + l_defendingCountry.getD_countryName() + " "
				+ l_noOfArmies;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createCardOrder(Player p_player, GameState p_gameState, String p_cardName) {
		Country l_defendingCountry = null;
		Country l_attackingCountry = getStrongestCountryWithAdjacentCountry(p_player, p_gameState);

		int l_noOfArmies = l_attackingCountry.getD_armies();
		List<Integer> l_adjacentCountriesList = l_attackingCountry.getD_adjacentCountryIds();

		Player l_adjPlayer = null;
		for (Integer l_adjCountryID : l_adjacentCountriesList) {
			l_adjPlayer = getCountryPlayer(p_gameState, l_adjCountryID);
			Country l_adjPlayerCountry = getSelectedCountry(l_adjPlayer, l_adjCountryID);

			if (l_adjPlayerCountry.getD_armies() <= l_noOfArmies
					&& !(p_player.getPlayerName().equalsIgnoreCase(l_adjPlayer.getPlayerName()))) {
				l_noOfArmies = l_adjPlayerCountry.getD_armies();
				l_defendingCountry = l_adjPlayerCountry;
			}
		}

		if (l_attackingCountry.getD_armies() > 1) {
			l_noOfArmies = l_attackingCountry.getD_armies() - 1;
		} else {
			l_noOfArmies = 0;
		}
		
		if (l_defendingCountry != null) {
			switch (p_cardName) {
			case "bomb":
				return "bomb " + l_defendingCountry.getD_countryName();
			case "blockade":
				return "blockade " + l_attackingCountry.getD_countryName();
			case "airlift":
				return "airlift " + l_attackingCountry.getD_countryName() + " "
						+ l_defendingCountry + " " + l_noOfArmies;
			case "negotiate":
				return "negotiate" + " " + l_adjPlayer;
			}
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
		return "Aggressive";
	}

	/**
	 * Finds the Player who owns the country using its ID.
	 * 
	 * @param p_gameState GameState class object
	 * @param p_countryID ID of the Country
	 * @return l_player player object if it exists else null
	 */
	private Player getCountryPlayer(GameState p_gameState, Integer p_countryID) {		
		for (Player l_player : p_gameState.getD_players()) {
			for (Country l_country : l_player.getD_coutriesOwned()) {
				if (l_country.getD_countryId() == p_countryID) {
					return l_player;
				}
			}
		}
		return null;
	}
	

	/**
	 * Finds the Country owned by Player using the Country ID.
	 * 
	 * @param p_player Player class object
	 * @param p_adjCountryID ID of the adjacent country
	 * @return l_country selected country object if it exists else null
	 */
	public Country getSelectedCountry(Player p_player, Integer p_adjCountryID) {
		for (Country l_country : p_player.getD_coutriesOwned()) {
			if (l_country.getD_countryId() == p_adjCountryID) {
				return l_country;
			}
		}
		return null;
	}

	/**
	 * Finds the Player's strongest country.
	 * 
	 * @param p_player Player class object
	 * @param p_gameState GameState class object
	 * @return l_strongestCountry the strongest country
	 */
	private Country getStrongestCountry(Player p_player, GameState p_gameState) {
		int l_noOfArmies = 0;
		Country l_strongestCountry = null;

		for (Country l_country : p_player.getD_coutriesOwned()) {
			if (l_country.getD_armies() >= l_noOfArmies) {
				l_noOfArmies = l_country.getD_armies();
				l_strongestCountry = l_country;
			}
		}
		return l_strongestCountry;
	}

	/**
	 * Finds the Player's strongest country who has adjacent enemy countries.
	 * 
	 * @param p_player Player class object
	 * @param p_gameState GameState class object
	 * @return l_strongestCountry the strongest country 
	 */
	private Country getStrongestCountryWithAdjacentCountry(Player p_player, GameState p_gameState) {
		int l_noOfArmies = 0;
		Country l_strongestCountry = null;
		List<Country> l_countriesWithAdjCountries = new ArrayList<Country>();

		for (Country l_country : p_player.getD_coutriesOwned()) {
			for (int l_adjCountryID : l_country.getD_adjacentCountryIds()) {
				Player l_adjPlayer = getCountryPlayer(p_gameState,l_adjCountryID);
				
				if (!p_player.getPlayerName().equalsIgnoreCase(l_adjPlayer.getPlayerName())) {
					l_countriesWithAdjCountries.add(l_country);
					break;
				}
			}
		}

		for (Country l_country : l_countriesWithAdjCountries) {
			if (l_country.getD_armies() >= l_noOfArmies) {
				l_noOfArmies = l_country.getD_armies();
				l_strongestCountry = l_country;
			}
		}

		if (l_strongestCountry == null) {
			l_strongestCountry = getStrongestCountry(p_player, p_gameState);
		}

		return l_strongestCountry;
	}
}
