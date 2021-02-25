package Models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * This class is used to test functionality of Order class functions.
 * 
 */
public class OrderTest {

	/**
	 * Order class reference.
	 */
	Order d_orderDetails;

	/**
	 * Player class reference.
	 */
	Player d_playerInfo;

	/**
	 * The setup is called before each test case of this class is executed.
	 */
	@Before
	public void setup() {
		d_orderDetails = new Order();
		d_playerInfo = new Player();
	}

	/**
	 * Used to test country name entered by player in deploy command to check if the
	 * entered country name belongs to player or not. If it does not belongs to
	 * player, order will not be executed.
	 */
	@Test
	public void testValidateDeployOrderCountry() {
		d_orderDetails.setD_targetCountryName("India");
		List<Country> l_countryList = new ArrayList<Country>();
		l_countryList.add(new Country("India"));
		l_countryList.add(new Country("Canada"));
		d_playerInfo.setD_coutriesOwned(l_countryList);
		boolean l_actualBoolean = d_orderDetails.validateDeployOrderCountry(d_playerInfo, d_orderDetails);
		assertTrue(l_actualBoolean);
	}

	/**
	 * Used to test execution of deploy order and check if required armies are
	 * deployed at country level or not
	 */
	@Test
	public void testDeployOrderExecution() {
		Order l_order1 = new Order("deploy", "India", 5);
		Order l_order2 = new Order("deploy", "Canada", 15);
		Player l_player = new Player();
		List<Country> l_playersCountries = new ArrayList<Country>();
		l_playersCountries.add(new Country("India"));
		l_playersCountries.add(new Country("Canada"));
		l_player.setD_coutriesOwned(l_playersCountries);

		List<Country> l_mapCountries = new ArrayList<Country>();
		Country l_country1 = new Country(1, "Canada", 1);
		Country l_country2 = new Country(1, "India", 2);
		l_country2.setD_armies(5);
		Country l_country3 = new Country(1, "Japan", 2);

		l_mapCountries.add(l_country1);
		l_mapCountries.add(l_country2);
		l_mapCountries.add(l_country3);

		Map l_map = new Map();
		l_map.setD_countries(l_mapCountries);
		GameState l_gameState = new GameState();
		l_gameState.setD_map(l_map);

		l_order1.execute(l_gameState, l_player);
		Country l_countryIndia = l_gameState.getD_map().getCountryByName("India");
		assertEquals(l_countryIndia.getD_armies().toString(), "10");

		l_order2.execute(l_gameState, l_player);
		Country l_countryCanada = l_gameState.getD_map().getCountryByName("Canada");
		assertEquals(l_countryCanada.getD_armies().toString(), "15");

	}
}
