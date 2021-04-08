/**
 * 
 */
package Models;

/**
 * This is the class of Random Player, who deploys armies randomly , attacks random neighboring countries 
 * and moves armies on his own territories randomly.
 */
public class RandomPlayer extends PlayerBehaviorStrategy {

	/**
	 * The parameterized constructor is used to create random player.
	 * @param p_player Player Class Object
	 */
	public RandomPlayer(Player p_player) {
		super(p_player);
	}

	/**
	 * This method creates a new order.
	 * @return Order object of order class
	 */
	@Override
	public Order createOrder() {
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
