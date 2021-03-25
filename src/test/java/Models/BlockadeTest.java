package Models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test functionality of Blockade class functions.
 */
public class BlockadeTest {

	/**
	 * First Player.
	 */
	Player d_player1;

	/**
	 * Second Player.
	 */
	Player d_player2;

	/**
	 * Blockade Order One.
	 */
	Blockade d_blokadeOrder1;

	/**
	 * Blockade Order Two.
	 */
	Blockade d_blokadeOrder2;

	/**
	 * Blockade Order Three.
	 */
	Blockade d_blokadeOrder3;

	/**
	 * Target Country.
	 */
	String d_targetCountry;

	/**
	 * Order List.
	 */
	List<Order> d_order_list;

	/**
	 * Game State.
	 */
	GameState d_gameState;

	/**
	 * Setup before each test case.
	 */
	@Before
	public void setup() {
		d_gameState = new GameState();
		d_order_list = new ArrayList<Order>();
		d_player1 = new Player();
		d_player1.setPlayerName("avni");
		d_player2 = new Player();
		d_player2.setPlayerName("priya");
		List<Country> l_countryList = new ArrayList<Country>();
		l_countryList.add(new Country("India"));
		l_countryList.add(new Country("Canada"));
		d_player1.setD_coutriesOwned(l_countryList);
		d_player2.setD_coutriesOwned(l_countryList);

		List<Country> l_mapCountries = new ArrayList<Country>();
		Country l_country1 = new Country(1, "Canada", 1);
		Country l_country2 = new Country(2, "India", 2);
		l_country1.setD_armies(10);
		l_country2.setD_armies(5);

		l_mapCountries.add(l_country1);
		l_mapCountries.add(l_country2);

		Map l_map = new Map();
		l_map.setD_countries(l_mapCountries);
		d_gameState.setD_map(l_map);
		d_blokadeOrder1 = new Blockade(d_player1, "India");
		d_blokadeOrder2 = new Blockade(d_player1, "USA");

		d_order_list.add(d_blokadeOrder1);
		d_order_list.add(d_blokadeOrder2);

		d_player2.setD_ordersToExecute(d_order_list);

		d_blokadeOrder3 = new Blockade(d_player2, "India");

	}

	/**
	 * Test Blockade order execution.
	 */
	@Test
	public void testBlockadeExecution() {
		d_blokadeOrder1.execute(d_gameState);
		Country l_countryIndia = d_gameState.getD_map().getCountryByName("India");
		assertEquals("15", l_countryIndia.getD_armies().toString());
	}

	/**
	 * Test Validation of Blockade Order.
	 */
	public void testValidBlockadeOrder() {
		// Validation 1 :- Validates whether target country belongs to the Player who
		// executed the order or not.
		boolean l_actualBoolean = d_blokadeOrder1.valid();
		assertTrue(l_actualBoolean);

		boolean l_actualBoolean2 = d_blokadeOrder2.valid();
		assertFalse(l_actualBoolean2);
	}

}
