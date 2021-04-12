/*

 */
package Models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to Test the working of Random Player Behavior Strategy.
 */
public class RandomPlayerTest {

    /**
     * Random Player to test.
     */
    Player d_player;

    /**
     * Strategy of Player.
     */
    PlayerBehaviorStrategy d_playerBehaviorStrategy;

    /**
     * Game State.
     */
    GameState d_gameState = new GameState();

    /**
     * Setup For testing Random Behavior Strategy.
     */
    @Before
    public void setup() {

        Continent l_continent = new Continent("Asia");

        Country l_country1 = new Country(1, "India", 1);
        Country l_country2 = new Country(2,"China", 1);
        ArrayList<Country> l_list = new ArrayList<Country>();
        l_list.add(l_country1);
        l_list.add(l_country2);

        d_playerBehaviorStrategy = new RandomPlayer();
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
     * Checks if it creates an Order String and first order is deploy.
     *
     * @throws IOException Exception
     */
    @Test
    public void testOrderCreation() throws IOException{
        assertEquals(d_player.getPlayerOrder(d_gameState).split(" ")[0],"deploy");
    }
}
