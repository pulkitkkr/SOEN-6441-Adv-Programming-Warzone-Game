/**
 * 
 */
package Models;

import java.io.IOException;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;

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
		return "Cheater";
	}
}
