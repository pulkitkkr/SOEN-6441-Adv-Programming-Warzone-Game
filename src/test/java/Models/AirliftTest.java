package Models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidMap;

/**
 * This class is used to test functionality of Airlift class functions.
 */
public class AirliftTest {
	/**
	 * First Player.
	 */
	Player d_player1;

	/**
	 * Airlift Order.
	 */
	Airlift d_airliftOrder;

	/**
	 * country to shift armies from.
	 */
	Airlift d_invalidAirLift1;

	/**
	 * Game State.
	 */
	GameState d_gameState;

	/**
	 * Setup before each test case.
	 * 
	 * @throws InvalidMap Invalid Map
	 */
	@Before
	public void setup() throws InvalidMap {
		d_gameState = new GameState();
		d_player1 = new Player();
		d_player1.setPlayerName("a");

		List<Country> l_countryList = new ArrayList<Country>();
		Country l_country = new Country(0, "France", 1);
		l_country.setD_armies(9);
		l_countryList.add(l_country);

		Country l_countryNeighbour = new Country(1, "Belgium", 1);
		l_countryNeighbour.addNeighbour(0);
		l_country.addNeighbour(1);
		l_countryNeighbour.setD_armies(10);
		l_countryList.add(l_countryNeighbour);

		Country l_countryNotNeighbour = new Country(2, "Spain", 1);
		l_countryNotNeighbour.setD_armies(15);
		l_countryList.add(l_countryNotNeighbour);

		Map l_map = new Map();
		l_map.setD_countries(l_countryList);

		d_gameState.setD_map(l_map);
		d_player1.setD_coutriesOwned(l_countryList);
		d_airliftOrder = new Airlift("France", "Spain", 2, d_player1);
	}

	/**
	 * Test Airlift execution.
	 */
	@Test
	public void testAirliftExecution() {
		d_airliftOrder.execute(d_gameState);
		Country l_countryIndia = d_gameState.getD_map().getCountryByName("Spain");
		assertEquals("17", l_countryIndia.getD_armies().toString());
	}

	/**
	 * Test validation of airlift order.
	 */
	@Test
	public void testInvalidAirLift() {
		d_invalidAirLift1 = new Airlift("France", "India", 1, d_player1);
		d_invalidAirLift1.checkValidOrder(d_gameState);
		assertEquals(d_gameState.getRecentLog(),
				"Log: Invalid Target Country! Doesn't exist on the map!" + System.lineSeparator());
	}
}
