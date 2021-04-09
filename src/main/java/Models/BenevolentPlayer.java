/**
 * 
 */
package Models;

import java.io.IOException;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;

/**
 * This is the class of Benevolent Player who focuses only on defending his own countries and
 * will never attack.
 *
 */
public class BenevolentPlayer extends PlayerBehaviorStrategy {

	/**
	 * object of player class.
	 */
	Player d_player;
	
	/**
	 * object of IssueOrderPhase class.
	 */
	IssueOrderPhase d_issueOrder;
	
	/**
	 * object of GameState class
	 */
	GameState d_gameState;
	
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
		d_player = p_player;
		d_issueOrder = p_issueOrder;
		d_gameState = p_gameState;
		
		p_issueOrder.askForOrder(p_player);
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

}
