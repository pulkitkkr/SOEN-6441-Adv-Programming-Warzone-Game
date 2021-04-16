package Models;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Cheater player strategy test.
 *
 */
public class CheaterPlayerTest {
    /**
     * Cheater Player to test.
     */
    Player d_player;

    /**
     * Opponent Player to test.
     */
    Player d_randomPlayer;

    /**
     * Strategy of Player.
     */
    PlayerBehaviorStrategy d_playerBehaviorStrategy;

    /**
     * Cheater player strategy.
     */
    CheaterPlayer d_cheaterPlayer = new CheaterPlayer();

    /**
     * Game State.
     */
    GameState d_gameState = new GameState();

    /**
     * Country.
     */
    Country d_country1;

    /**
     * Setup For testing Aggressive Behavior Strategy.
     */
    @Before
    public void setup() {

        this.d_country1 = new Country(1, "Spain", 1);
        Country l_country2 = new Country(2, "France", 1);
        Country l_country3 = new Country(3, "Portugal", 1);


        d_country1.addNeighbour(3); // Spain ---> Portugal
        d_country1.addNeighbour(2); // Spain ---> France
        l_country2.addNeighbour(3); // France ----> Portugal

        l_country2.setD_armies(3); // France(3)
        l_country3.setD_armies(2); // Portugal(2)

        ArrayList<Country> l_allCountries = new ArrayList<Country>();
        l_allCountries.add(d_country1);
        l_allCountries.add(l_country2);
        l_allCountries.add(l_country3);


        Continent l_continent = new Continent("Europe");
        l_continent.setD_continentID(1);
        l_continent.setD_countries(l_allCountries);

        ArrayList<Continent> l_continents = new ArrayList<Continent>();
        l_continents.add(l_continent);

        ArrayList<Country> l_ownedCountriesPlayerOne = new ArrayList<Country>();
        l_ownedCountriesPlayerOne.add(d_country1);

        ArrayList<Country> l_ownedCountriesPlayerTwo = new ArrayList<Country>();
        l_ownedCountriesPlayerTwo.add(l_country2);
        l_ownedCountriesPlayerTwo.add(l_country3);

        d_playerBehaviorStrategy = new CheaterPlayer();
        d_player = new Player("DummyPlayer");
        d_player.setD_noOfUnallocatedArmies(10);
        d_player.setD_coutriesOwned(l_ownedCountriesPlayerOne);
        d_player.setStrategy(d_playerBehaviorStrategy);

        d_randomPlayer = new Player("Opponent");
        RandomPlayer l_randomPlayerBehaviorStrategy = new RandomPlayer();
        d_randomPlayer.setStrategy(l_randomPlayerBehaviorStrategy);
        d_randomPlayer.setD_coutriesOwned(l_ownedCountriesPlayerTwo);
        d_randomPlayer.setD_noOfUnallocatedArmies(0);


        List<Player> l_listOfPlayer = new ArrayList<Player>();
        l_listOfPlayer.add(d_player);
        l_listOfPlayer.add(d_randomPlayer);

        Map l_map = new Map();
        l_map.setD_countries(l_allCountries);
        l_map.setD_continents(l_continents);
        d_gameState.setD_map(l_map);
        d_gameState.setD_players(l_listOfPlayer);
    }


    /**
     * Checks if it creates an null Order.
     *
     * @throws IOException Exception
     */
    @Test
    public void testOrderCreationToBeNull() throws IOException {
        String l_receivedOrder = d_player.getPlayerOrder(d_gameState);
        assertNull(l_receivedOrder);
    }

    /**
     * Checks all unallocated armies are deployed to player's country.
     *
     * @throws IOException Exception
     */
    @Test
    public void testUnallocatedArmiesDeployment() throws IOException {
        String l_receivedOrder = d_player.getPlayerOrder(d_gameState);
        assertNull(l_receivedOrder);

        int l_unallocatedArmies = d_player.getD_noOfUnallocatedArmies();
        assertEquals(0, l_unallocatedArmies);
    }

    /**
     * Checks that player now owns all enemy countries.
     *
     * @throws IOException Exception
     */
    @Test
    public void testCheaterOwnsAllEnemies() throws IOException {
        String l_receivedOrder = d_player.getPlayerOrder(d_gameState);
        assertNull(l_receivedOrder);

        int l_ownedCountriesCount = d_player.getD_coutriesOwned().size();
        assertEquals(3, l_ownedCountriesCount);

        int l_opponentOwnedCountriesCount = d_randomPlayer.getD_coutriesOwned().size();
        assertEquals(0, l_opponentOwnedCountriesCount);
    }
}