/**
 * 
 */
package Models;

import java.io.IOException;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;

/**
 * This is the abstract strategy class of Player Behavior.
 */
public abstract class PlayerBehaviorStrategy {
	
	/**
	 * object of player class.
	 */
	Player d_player;
	
	/**
	 * object of GameState class.
	 */
	GameState d_gameState;

	/**
	 * this method sets the objects of player class and GameState class.
	 * @param p_player object of player class
	 * @param p_gameState object of GameState class
	 */
	public void setObjects(Player p_player, GameState p_gameState) {
		d_player = p_player;
		d_gameState = p_gameState;
	}

	/**
	 * This method creates a new order for Random, Aggressive, Cheater and Benevolent Players.
	 * 
	 * @param p_player object of Player class
	 * @param p_gameState object of GameState class
	 * 
	 * @return Order object of order class
	 */
	public abstract String createOrder(Player p_player, GameState p_gameState) throws IOException;
	
	/**
	 * This method returns the player behavior.
	 * @return String player behavior
	 */
	public abstract String getPlayerBehavior();
	
}
