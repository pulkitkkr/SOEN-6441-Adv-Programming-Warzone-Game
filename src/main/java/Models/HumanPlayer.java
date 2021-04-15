/**
 * 
 */
package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;

/**
 * This is the class of Human Player which requires user interaction in order to make decisions.
 */
public class HumanPlayer extends PlayerBehaviorStrategy{	

	/**
	 * This method returns the player behavior.
	 * @return String player behavior
	 */
	@Override
	public String getPlayerBehavior() {
		return "Human";
	}
	
	/**
	 * This method creates a new order.
	 * 
	 * @param p_player object of Player class
	 * @param p_gameState object of GameState class
	 * 
	 * @return Order object of order class
	 */
	@Override
	public String createOrder(Player p_player, GameState p_gameState) throws IOException {

		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nPlease enter command to issue order for player : " + p_player.getPlayerName()
				+ " or give showmap command to view current state of the game.");
		String l_commandEntered = l_reader.readLine();
		return l_commandEntered;
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
}
