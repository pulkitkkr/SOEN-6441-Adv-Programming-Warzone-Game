/**
 * 
 */
package Models;

import java.io.IOException;
import java.util.List;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;

/**
 * This is the abstract strategy class of Player Behavior.
 */
public abstract class PlayerBehaviorStrategy {
	
//	/**
//	 * This is the default constructor.
//	 */
//	public PlayerBehaviorStrategy() {
//	}
	
	/**
	 * This method creates a new order.
	 * 
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
	public abstract Order createOrder(Player p_player, IssueOrderPhase p_issueOrder, GameState p_gameState) throws InvalidCommand, IOException, InvalidMap;
	
	/**
	 * This method defines which country to attack.
	 * @return Country object of class Country
	 */
	public abstract Country toAttack();
	
	/**
	 * This method defines from which country the attack will be initiated.
	 * @return Country object of class Country
	 */
	public abstract Country toAttackFrom();
	
	/**
	 * This method defines where to move the armies from.
	 * @return Country object of class Country
	 */
	public abstract Country toMoveFrom();
	
	/**
	 * This method defines the placement of more armies in order to defend the country.
	 * @return Country object of class Country
	 */
	public abstract Country toDefend();
	
}
