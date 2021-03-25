package Models;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Controllers.GameEngine;
import Exceptions.InvalidMap;

/**
 * This class is used to test functionalities of order execution phase.
 *
 */
public class OrderExecutionPhaseTest {
	/**
	 * First Player.
	 */
	Player d_player1;

	/**
	 * Second Player.
	 */
	Player d_player2;

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
		d_player2 = new Player();
		d_player2.setPlayerName("b");

		List<Country> l_countryList = new ArrayList<Country>();
		Country l_country = new Country(0, "France", 1);
		l_country.setD_armies(9);
		l_countryList.add(l_country);

		Country l_countryNeighbour = new Country(1, "Belgium", 1);
		l_countryNeighbour.addNeighbour(0);
		l_country.addNeighbour(1);
		l_countryNeighbour.setD_armies(10);
		l_countryList.add(l_countryNeighbour);

		d_player1.setD_coutriesOwned(l_countryList);

		Map l_map = new Map();
		l_map.setD_countries(l_countryList);
		d_gameState.setD_map(l_map);
		d_gameState.setD_players(Arrays.asList(d_player1, d_player2));
	}

	/**
	 * Checks if one of the player has conquered all countries to end the game.
	 */
	@Test
	public void testEndOfTheGame() {
		OrderExecutionPhase l_orderExec = new OrderExecutionPhase(new GameEngine(), d_gameState);
		assertTrue(l_orderExec.checkEndOftheGame(d_gameState));
	}
}
