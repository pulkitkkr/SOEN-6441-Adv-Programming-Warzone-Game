/**
 * 
 */
package Models;

/**
 * This is the abstract strategy class of Player Behavior.
 */
public abstract class PlayerBehaviorStrategy {
	
	/**
	 * Object of Player class.
	 */
	Player d_player;
	
	/**
	 * The parameterized constructor is used to create player.
	 * @param p_player Player Class Object
	 */
	public PlayerBehaviorStrategy(Player p_player) {
		d_player = p_player;
	}
	
	/**
	 * This method creates a new order.
	 * @return Order object of order class
	 */
	public abstract Order createOrder();
	
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
