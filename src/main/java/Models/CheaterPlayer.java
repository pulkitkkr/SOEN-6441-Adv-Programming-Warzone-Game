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
	public Order createOrder(Player p_player, GameState p_gameState) {
		super.setObjects(p_player, p_gameState);
		return null;
	}

	/**
	 * This method defines which country to attack.
	 * @return Country object of class Country
	 */
	@Override
	public Country toAttack() {
		return null;
	}

	/**
	 * This method defines from which country the attack will be initiated.
	 * @return Country object of class Country
	 */
	@Override
	public Country toAttackFrom() {
		return null;
	}

	/**
	 * This method defines where to move the armies from.
	 * @return Country object of class Country
	 */
	@Override
	public Country toMoveFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method defines the placement of more armies in order to defend the country.
	 * @return Country object of class Country
	 */
	@Override
	public Country toDefend() {
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

	/**
	 * This method creates a new order.
	 * @param p_player object of Player class
	 * @param p_issueOrder object of IssueOrderPhase class
	 * @param p_gameState object of GameState class
	 * 
	 * @return Order object of order class
	 * 
	 * @throws InvalidMap handles invalid map exception
	 * @throws IOException handles IO exception
	 * @throws InvalidCommand handles Invalid Command exception
	 */
	@Override
	public Order createOrder(Player p_player, IssueOrderPhase p_issueOrder, GameState p_gameState) throws InvalidCommand, IOException, InvalidMap{
		return null;
	}
}
