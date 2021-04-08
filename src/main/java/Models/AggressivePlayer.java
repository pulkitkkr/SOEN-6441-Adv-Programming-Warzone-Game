/**
 * 
 */
package Models;

/**
 * This is the class of Aggressive Player, who gathers all his armies, attacks from his strongest territory
 * and deploys armies to maximize his forces on one country.
 *
 */
public class AggressivePlayer extends PlayerBehaviorStrategy{

	/**
	 * The parameterized constructor is used to create aggressive player.
	 * @param p_player Player Class Object
	 */
	public AggressivePlayer(Player p_player) {
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
