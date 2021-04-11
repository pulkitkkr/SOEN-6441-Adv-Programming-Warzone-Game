/**
 * 
 */
package Models;

import java.io.IOException;
import java.util.Random;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;

/**
 * This is the class of Aggressive Player, who gathers all his armies, attacks from his strongest territory
 * and deploys armies to maximize his forces on one country.
 *
 */
public class AggressivePlayer extends PlayerBehaviorStrategy{
		
	/**
	 * Random class object initialized.
	 */
	Random d_random = new Random();
	
	/**
	 * This method creates a new order.
	 * @param p_player object of Player class
	 * @param p_gameState object of GameState class
	 * 
	 * @return String form of order
	 */
	@Override
	public String createOrder(Player p_player, GameState p_gameState) {
		return null;
	}

	/**
	 * This method defines which country to attack.
	 * @return Country object of class Country
	 */
	@Override
	public Country toAttack() {
		//need to check adjacency
		return null;
	}

	/**
	 * This method defines from which country the attack will be initiated.
	 * @return Country object of class Country
	 */
	@Override
	public Country toAttackFrom() {
		Country l_maxArmiesCountry = d_player.getD_coutriesOwned().get(0);
		for(Country l_country : d_player.getD_coutriesOwned()) {
			if(l_maxArmiesCountry.getD_armies() < l_country.getD_armies() && d_player.getD_coutriesOwned().contains(l_country)) {
				l_maxArmiesCountry = l_country;
			}
		}
		return l_maxArmiesCountry;
	}

	/**
	 * This method defines where to move the armies from.
	 * @return Country object of class Country
	 */
	@Override
	public Country toMoveFrom() {
		return null;
	}

	/**
	 * This method defines the placement of more armies in order to defend the country.
	 * @return Country object of class Country
	 */
	@Override
	public Country toDefend() {
		return d_player.getD_coutriesOwned().get(d_random.nextInt(d_player.d_coutriesOwned.size() - 1));
	}

	/**
	 * This method returns the player behavior.
	 * @return String player behavior
	 */
	@Override
	public String getPlayerBehavior() {
		return "Aggressive";
	}
}
