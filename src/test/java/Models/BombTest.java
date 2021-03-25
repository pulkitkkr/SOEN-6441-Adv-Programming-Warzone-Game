package Models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BombTest {

	/**
	 * Player 1 object.
	 */
	Player d_player1;

	/**
	 * Player 2 object.
	 */
	Player d_player2;

	/**
	 * Bomb Order1.
	 */
	Bomb d_bombOrder1;
	
	/**
	 * Bomb Order2.
	 */
	Bomb d_bombOrder2;

	/**
	 * name of the target country.
	 */
	String d_targetCountry;

	/**
	 * list of orders.
	 */
	List<Order> d_order_list;
	
	/**
	 * Game State object.
	 */
	GameState d_gameState;
	
	@Before
	public void setup() {
		d_gameState = new GameState();
		d_order_list = new ArrayList<Order>();
		
		d_player1 = new Player();
		d_player1.setPlayerName("Prime");
		d_player2 = new Player();
		d_player2.setPlayerName("Bhoomi");
		
		List<Country> l_countryList = new ArrayList<Country>();
		l_countryList.add(new Country("Finland"));
		l_countryList.add(new Country("Norway"));
		d_player1.setD_coutriesOwned(l_countryList);
		d_player2.setD_coutriesOwned(l_countryList);

		List<Country> l_mapCountries = new ArrayList<Country>();
		Country l_country1 = new Country(1, "Finland", 1);
		Country l_country2 = new Country(2, "Norway", 2);
		l_country1.setD_armies(5);
		l_country2.setD_armies(20);

		l_mapCountries.add(l_country1);
		l_mapCountries.add(l_country2);

		Map l_map = new Map();
		l_map.setD_countries(l_mapCountries);
		d_gameState.setD_map(l_map);
		d_bombOrder1 = new Bomb(d_player1, "Norway");
		d_bombOrder2 = new Bomb(d_player1, "Japan");

		d_order_list.add(d_bombOrder1);
		d_order_list.add(d_bombOrder2);

		d_player2.setD_ordersToExecute(d_order_list);
	}

	@Test
	public void testBombCard() {
		d_bombOrder1.execute(d_gameState);
		Country l_targetCountry = d_gameState.getD_map().getCountryByName("Norway");
		assertEquals("10", l_targetCountry.getD_armies().toString());
	}

	@Test
	public void testValidBombOrder() {
		// #1 : Validates whether target country belongs to the Player who executed the
		// order or not
		boolean l_actualBoolean = d_bombOrder1.valid();
		assertTrue(l_actualBoolean);

		boolean l_actualBoolean2 = d_bombOrder2.valid();
		assertFalse(l_actualBoolean2);
	}

}
