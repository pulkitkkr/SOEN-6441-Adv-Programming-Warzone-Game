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
		return "Aggressive";
	}
}
