package Models;

/**
 * This model class manages all the cards owned by the player.
 */
public interface Card extends Order {

	public Boolean checkValidOrder(GameState p_gameState);

}
