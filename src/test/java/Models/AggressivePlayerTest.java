/**
 * 
 */
package Models;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the working of Aggressive Player Behavior.
 */
public class AggressivePlayerTest {
	
	/**
	 * Object of Aggressive Player.
	 */
	Player d_player;
	
	/**
	 * Country class object;
	 */
	Country d_country;
	
	/**
	 * Object of GameState Class.
	 */
	GameState d_gameState;
	
	/**
	 * Object of PlayerBehaviorStrategy Class.
	 */
	PlayerBehaviorStrategy d_playerBehaviorStrategy;
	
	/**
	 * Aggressive Player Object.
	 */
	AggressivePlayer d_aggressivePlayer = new AggressivePlayer();
	
	/**
     * Setup for testing Aggressive Player Behavior Strategy.
     */
    @Before
    public void setup() {
        this.d_country = new Country(1, "Spain", 1);
        Country l_country1 = new Country(1, "Portugal", 1);
        Country l_country2 = new Country(1, "France", 1);
        
        l_country1.setD_countryId(3);
		d_country.addNeighbour(3);

		l_country2.setD_countryId(2);
		d_country.addNeighbour(2);

		this.d_country.setD_armies(10);
		l_country1.setD_armies(3);
		l_country2.setD_armies(2);

		ArrayList<Country> l_list = new ArrayList<Country>();
		l_list.add(d_country);
		l_list.add(l_country1);
		l_list.add(l_country2);

		d_playerBehaviorStrategy = new AggressivePlayer();
		d_player = new Player("Bhoomi");
		d_player.setD_coutriesOwned(l_list);
		d_player.setStrategy(d_playerBehaviorStrategy);
		d_player.setD_noOfUnallocatedArmies(8);

		List<Player> l_listOfPlayer = new ArrayList<Player>();
		l_listOfPlayer.add(d_player);

		Map l_map = new Map();
		l_map.setD_countries(l_list);
		d_gameState.setD_map(l_map);
		d_gameState.setD_players(l_listOfPlayer);

    }

    /**
     * Tests the creation of Order and deployment of the first order in the list.
     *
     * @throws IOException Input/Output Exception
     */
    @Test
    public void testCreateOrder() throws IOException{
        assertEquals("deploy", d_player.getPlayerOrder(d_gameState).split(" ")[0]);
    }
    
    /**
	 * Check if aggressive player deploy armies on strongest country or not.
	 */
	@Test
	public void testStrongestCountry() {
		assertEquals("Spain", d_aggressivePlayer.getStrongestCountry(d_player,d_gameState).getD_countryName());
	}

	/**
	 * Check if aggressive player attacks to enemy territory or not.
	 */
	@Test
	public void testStrongestCountryWithAdjacentCountry() {
		assertEquals(null, d_aggressivePlayer.getStrongestCountryWithAdjacentCountry(d_player, d_gameState).getD_countryName());
	}
}
