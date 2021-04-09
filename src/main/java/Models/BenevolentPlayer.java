/**
 * 
 */
package Models;

/**
 * This is the class of Benevolent Player who focuses only on defending his own countries and
 * will never attack.
 *
 */
public class BenevolentPlayer extends PlayerBehaviorStrategy {

	/**
	 * The parameterized constructor is used to create benevolent player.
	 * @param p_player Player Class Object
	 */
	public BenevolentPlayer(Player p_player) {
		super(p_player);
	}

	/**
	 * This method creates a new order.
	 * @param p_player object of Player class
	 * @param p_issueOrder object of IssueOrderPhase class
	 * @return Order object of order class
	 */
	@Override
	public Order createOrder(Player p_player, IssueOrderPhase p_issueOrder) {
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
