/**
 * 
 */
package Models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Bhoomi Shah
 *
 * The abstract class manages all the cards.
 */
public abstract class Cards {
	Bomb b = new Bomb();
	Blockade bd = new Blockade();
	Airlift a = new Airlift();
	Diplomacy d = new Diplomacy();

	/**
	 * enum of objects of each card type.
	 * 
	 * @author Bhoomi Shah
	 *
	 */
	enum cards {
		b, bd, a, d
	}

	private static final List<cards> VALUES = Collections.unmodifiableList(Arrays.asList(cards.values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	/**
	 * This method picks any random card available from the set of cards in enum and assigns it to the player after the country has been conquered.
	 * 
	 * @return cards any random card 
	 */
	public static cards randomCard() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
