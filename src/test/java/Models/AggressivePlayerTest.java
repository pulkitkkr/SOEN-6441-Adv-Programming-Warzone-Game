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
	 * Object of GameState Class.
	 */
	GameState d_gameState;
	
	/**
	 * Object of PlayerBehaviorStrategy Class.
	 */
	PlayerBehaviorStrategy d_playerBehaviorStrategy;
	
	/**
     * Setup For testing Aggressive Player Behavior Strategy.
     */
    @Before
    public void setup() {

        Continent l_continent = new Continent("Europe");

        Country l_country1 = new Country(1, "Portugal", 10);
        Country l_country2 = new Country(2,"France", 5);
        
        ArrayList<Country> l_list = new ArrayList<Country>();
        l_list.add(l_country1);
        l_list.add(l_country2);

        d_playerBehaviorStrategy = new AggressivePlayer();
        d_player = new Player();
        
        d_player.setD_coutriesOwned(l_list);
        d_player.setStrategy(d_playerBehaviorStrategy);
        d_player.setD_noOfUnallocatedArmies(1);
        
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
    public void testcreateOrder() throws IOException{
        assertEquals(d_player.getPlayerOrder(d_gameState).split(" ")[0],"deploy");
    }
}
