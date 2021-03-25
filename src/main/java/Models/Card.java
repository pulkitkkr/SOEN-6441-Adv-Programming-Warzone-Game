package Models;

/**
 * This model class manages all the cards owned by the player.
 */
public interface Card extends Order {

	/**
	 * Pre-validation of card type order.
	 * 
	 * @param p_gameState Gamestate
	 * @return true or false
	 */
	public Boolean checkValidOrder(GameState p_gameState);

}
