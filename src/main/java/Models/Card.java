/**
 * 
 */
package Models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This model class manages all the cards owned by the player.
 */
public interface Card {	
	/**
	 * Method that will be called by the Receiver to execute the card logic.
	 * 
	 * @param p_gameState current state of the game.
	 */
	public void execute(GameState p_gameState);

	/**
	 * 
	 * @return boolean true or false
	 */
	public boolean valid();

	/**
	 * Print card information.
	 */
	public void printCard();
}
