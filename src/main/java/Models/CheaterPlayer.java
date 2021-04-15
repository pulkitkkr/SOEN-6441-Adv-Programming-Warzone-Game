/**
 * 
 */
package Models;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Services.PlayerService;

/**
 * This is the class of Cheater Player who directly attacks its neighboring enemy countries during the issue order phase 
 * and doubles the number of armies on his countries which have enemy neighbors.
 */
public class CheaterPlayer extends PlayerBehaviorStrategy {
	
	/**
	 * This method creates a new order.
	 * @param p_player object of Player class
	 * @param p_gameState object of GameState class
	 * 
	 * @return Order object of order class
	 */
	@Override
	public String createOrder(Player p_player, GameState p_gameState) throws IOException {

		if(p_player.getD_noOfUnallocatedArmies() != 0) {
			while(p_player.getD_noOfUnallocatedArmies() > 0) {
				Random l_random = new Random();
				Country l_randomCountry = getRandomCountry(p_player.getD_coutriesOwned());
				int l_armiesToDeploy = l_random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;

				l_randomCountry.setD_armies(l_armiesToDeploy);
				p_player.setD_noOfUnallocatedArmies(p_player.getD_noOfUnallocatedArmies() - l_armiesToDeploy);

				String l_logMessage = "Cheater Player: " + p_player.getPlayerName() +
						" assigned " + l_armiesToDeploy +
						" armies to  " + l_randomCountry.getD_countryName();

				p_gameState.updateLog(l_logMessage, "effect");
			}
		}

		conquerNeighboringEnemies(p_player, p_gameState);
		doubleArmyOnEnemyNeighboredCounties(p_player, p_gameState);

		p_player.checkForMoreOrders(true);
		return null;
	}

	/**
	 *
	 * returns a random country owned by player
	 *
	 * @param p_listOfCountries list of countries owned by player
	 * @return a random country from list
	 */
	private Country getRandomCountry(List<Country> p_listOfCountries){
		Random l_random = new Random();
		return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
	}

	/**
	 * Doubles the number of armies on country that are neighbor to enemies.
	 *
	 * @param p_player object of Player class
	 * @param p_gameState object of GameState class
	 */
	private void doubleArmyOnEnemyNeighboredCounties(Player p_player, GameState p_gameState){
		List<Country> l_countriesOwned = p_player.getD_coutriesOwned();

		for(Country l_ownedCountry : l_countriesOwned) {
			ArrayList<Integer> l_countryEnemies = getEnemies(p_player, l_ownedCountry);

			if(l_countryEnemies.size() == 0) continue;

			Integer l_arimiesInTerritory = l_ownedCountry.getD_armies();

			if(l_arimiesInTerritory == 0) continue;

			l_ownedCountry.setD_armies(l_arimiesInTerritory*2);

			String l_logMessage = "Cheater Player: " + p_player.getPlayerName() +
					" doubled the armies ( Now: " + l_arimiesInTerritory*2 +
					") in " + l_ownedCountry.getD_countryName();

			p_gameState.updateLog(l_logMessage, "effect");

		}
	}

	/**
	 * Conquer all enemies that are neighbor to the country owned by player.
	 *
	 * @param p_player object of Player class
	 * @param p_gameState object of GameState class
	 */
	private void conquerNeighboringEnemies(Player p_player, GameState p_gameState){
		List<Country> l_countriesOwned = p_player.getD_coutriesOwned();

		for(Country l_ownedCountry : l_countriesOwned) {
			ArrayList<Integer> l_countryEnemies = getEnemies(p_player, l_ownedCountry);

			for(Integer l_enemyId: l_countryEnemies) {
				Map l_loadedMap =  p_gameState.getD_map();
				Player l_enemyCountryOwner = this.getCountryOwner(p_gameState, l_enemyId);
				Country l_enemyCountry = l_loadedMap.getCountryByID(l_enemyId);
				this.conquerTargetCountry(p_gameState, l_enemyCountryOwner ,p_player, l_enemyCountry);

				String l_logMessage = "Cheater Player: " + p_player.getPlayerName() +
						" Now owns " + l_enemyCountry.getD_countryName();

				p_gameState.updateLog(l_logMessage, "effect");
			}

		}
	}

	/**
	 * @param p_gameState Current state of the game
	 * @param p_countryId id of the country whose neighbor is to be searched
	 * @return Owner of the Country
	 */

	private Player getCountryOwner(GameState p_gameState, Integer p_countryId){
		List<Player> l_players = p_gameState.getD_players();
		Player l_owner = null;

		for(Player l_player: l_players){
			List<Integer> l_countriesOwned = l_player.getCountryIDs();
			if(l_countriesOwned.contains(p_countryId)){
			l_owner = l_player;
			break;
			}
		}

		return l_owner;
	}

	/**
	 * Conquers Target Country when target country doesn't have any army.
	 *
	 * @param p_gameState             Current state of the game
	 * @param p_cheaterPlayer player owning source country
	 * @param p_targetCPlayer player owning the target country
	 * @param p_targetCountry         target country of the battle
	 */
	private void conquerTargetCountry(GameState p_gameState, Player p_targetCPlayer, Player p_cheaterPlayer, Country p_targetCountry) {
		p_targetCPlayer.getD_coutriesOwned().remove(p_targetCountry);
		p_targetCPlayer.getD_coutriesOwned().add(p_targetCountry);
		// Add Log Here
		this.updateContinents(p_cheaterPlayer, p_targetCPlayer, p_gameState);
	}

	/**
	 * Updates continents of players based on battle results.
	 *
	 * @param p_cheaterPlayer player owning source country
	 * @param p_targetCPlayer player owning target country
	 * @param p_gameState             current state of the game
	 */
	private void updateContinents(Player p_cheaterPlayer, Player p_targetCPlayer,
								  GameState p_gameState) {
		List<Player> l_playesList = new ArrayList<>();
		p_cheaterPlayer.setD_continentsOwned(new ArrayList<>());
		p_targetCPlayer.setD_continentsOwned(new ArrayList<>());
		l_playesList.add(p_cheaterPlayer);
		l_playesList.add(p_targetCPlayer);

		PlayerService l_playerService = new PlayerService();
		l_playerService.performContinentAssignment(l_playesList, p_gameState.getD_map().getD_continents());
	}

	private ArrayList<Integer> getEnemies(Player p_player, Country p_country){
		ArrayList<Integer> l_enemyNeighbors = new ArrayList<Integer>();

		for(Integer l_countryID : p_country.getD_adjacentCountryIds()){
			if(!p_player.getCountryIDs().contains(l_countryID))
				l_enemyNeighbors.add(l_countryID);
		}
		return l_enemyNeighbors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createDeployOrder(Player p_player, GameState p_gameState) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createAdvanceOrder(Player p_player, GameState p_gameState) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createCardOrder(Player p_player, GameState p_gameState, String p_cardName) {
		return null;
	}

	/**
	 * This method returns the player behavior.
	 * @return String player behavior
	 */
	@Override
	public String getPlayerBehavior() {
		return "Cheater";
	}
}
