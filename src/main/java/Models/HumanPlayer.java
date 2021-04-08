/**
 * 
 */
package Models;

/**
 * This is the class of Human Player which requires user interaction in order to make decisions
 */
public class HumanPlayer extends PlayerBehaviorStrategy{	
	
	/**
	 * The parameterized constructor is used to create human player.
	 * @param p_player Player Class Object
	 */
	public HumanPlayer(Player p_player) {
		super(p_player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method creates a new order.
	 * @return Order object of order class
	 */
	@Override
	public Order createOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method defines which country to attack.
	 * @return Country object of class Country
	 */
	@Override
	public Country toAttack() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method defines from which country the attack will be initiated.
	 * @return Country object of class Country
	 */
	@Override
	public Country toAttackFrom() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

}
