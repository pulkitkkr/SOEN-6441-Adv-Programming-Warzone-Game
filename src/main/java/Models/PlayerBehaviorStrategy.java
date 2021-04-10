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
	 * object of IssueOrderPhase class.
	 */
	IssueOrderPhase d_issueOrder;
	
	/**
	 * object of GameState class.
	 */
	GameState d_gameState;
	
	/**
	 * this method sets the objects of player class, IssueOrderPhase class and GameState class.
	 * @param p_player object of player class
	 * @param p_issueOrder object of IssueOrderPhase class
	 * @param p_gameState object of GameState class
	 */
	public void setObjects(Player p_player, IssueOrderPhase p_issueOrder, GameState p_gameState) {
		d_player = p_player;
		d_issueOrder = p_issueOrder;
		d_gameState = p_gameState;
	}

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
	 * This method creates a new order for Human Player Behavior.
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
	 * This method creates a new order for Random, Aggressive, Cheater and Benevolent Players.
	 * 
	 * @param p_player object of Player class
	 * @param p_gameState object of GameState class
	 * 
	 * @return Order object of order class
	 */
	public abstract Order createOrder(Player p_player, GameState p_gameState);
	
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
	
	/**
	 * This method returns the player behavior.
	 * @return String player behavior
	 */
	public abstract String getPlayerBehavior();
	
}
