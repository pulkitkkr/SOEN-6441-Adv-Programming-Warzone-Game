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
	 * This method creates a new order for Random, Aggressive, Cheater and Benevolent Players.
	 * 
	 * @param p_player object of Player class
	 * @param p_gameState object of GameState class
	 * 
	 * @return Order object of order class
	 * @throws IOException Exception
	 */
	public abstract String createOrder(Player p_player, GameState p_gameState) throws IOException;

	/**
	 * Deploy Orders to be defined via Strategy.
	 *
	 * @param p_player player to give deploy orders
	 * @param p_gameState current Gamestate
	 * @return String representing Order
	 */
	public abstract String createDeployOrder(Player p_player, GameState p_gameState);

	/**
	 * Advance Orders to be defined via Strategy.
	 *
	 * @param p_player player to give advance orders
	 * @param p_gameState GameState representing current Game
	 * @return String representing Order.
	 */
	public abstract String createAdvanceOrder(Player p_player, GameState p_gameState);

	/**
	 * Card Orders to be defined via Strategy.
	 *
	 * @param p_player player to give Card Orders.
	 * @param p_gameState GameState representing Current Game
	 * @param p_cardName Card Name to create Order for
	 * @return String representing order
	 */
	public abstract String createCardOrder(Player p_player, GameState p_gameState, String p_cardName);
	
	/**
	 * This method returns the player behavior.
	 * @return String player behavior
	 */
	public abstract String getPlayerBehavior();
	
}
