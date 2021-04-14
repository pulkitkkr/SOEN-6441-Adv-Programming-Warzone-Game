package Models;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Class to Test the working of Benevolent Player Behavior Strategy.
 */
public class BenevolentPlayerTest {

	/**
	 * Benevolent Player to test.
	 */
	Player d_player;

	/**
	 * Strategy of Player.
	 */
	PlayerBehaviorStrategy d_playerBehaviorStrategy;

	/**
	 * Benevolent player strategy.
	 */
	BenevolentPlayer d_benevolentPlayer = new BenevolentPlayer();

	/**
	 * Game State.
	 */
	GameState d_gameState = new GameState();
	
	/**
	 * Country India.
	 */
	Country d_country1;

	/**
	 * Setup For testing Benevolent Behavior Strategy.
	 */
	@Before
	public void setup() {
		this.d_country1 = new Country(1, "India", 1);
		Country l_country2 = new Country(1, "China", 1);
		Country l_country3 = new Country(1, "Pakistan", 1);

		l_country2.setD_countryId(3);
		d_country1.addNeighbour(3);

		l_country3.setD_countryId(2);
		d_country1.addNeighbour(2);

		this.d_country1.setD_armies(10);
		l_country2.setD_armies(3);
		l_country3.setD_armies(2);

		ArrayList<Country> l_list = new ArrayList<Country>();
		l_list.add(d_country1);
		l_list.add(l_country2);
		l_list.add(l_country3);

		d_playerBehaviorStrategy = new BenevolentPlayer();
		d_player = new Player("Avneet");
		d_player.setD_coutriesOwned(l_list);
		d_player.setStrategy(d_playerBehaviorStrategy);
		d_player.setD_noOfUnallocatedArmies(5);

		List<Player> l_listOfPlayer = new ArrayList<Player>();
		l_listOfPlayer.add(d_player);

		Map l_map = new Map();
		l_map.setD_countries(l_list);
		l_map.setD_countries(l_list);
		d_gameState.setD_map(l_map);
		d_gameState.setD_players(l_listOfPlayer);

	}

	/**
	 * Checks if it creates an Order String and first order is deploy.
	 *
	 * @throws IOException Exception
	 */
	@Test
	public void testOrderCreation() throws IOException {
		assertEquals("deploy", d_player.getPlayerOrder(d_gameState).split(" ")[0]);
	}

	/**
	 * Check if benevolent player deploy armies on weakest country or not.
	 */
	@Test
	public void testWeakestCountry() {
		assertEquals("Pakistan", d_benevolentPlayer.getWeakestCountry(d_player).getD_countryName());
	}

	/**
	 * Check if benevolent player attacks to weakest neighbor or not.
	 */
	@Test
	public void testWeakestNeighbor() {
		assertEquals("Pakistan", d_benevolentPlayer.getWeakestNeighbor(d_country1, d_gameState).getD_countryName());
	}

}
