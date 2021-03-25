package Models;

import Exceptions.InvalidMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.addAll;
import static org.junit.Assert.assertEquals;

public class DiplomacyTest {
    /**
     * First Player.
     */
    Player d_player1;

    /**
     * Second Player
     */
    Player d_player2;

    /**
     * Bomb Order.
     */
    Bomb d_bombOrder;

    /**
     * diplomacy order.
     */
    Diplomacy d_diplomacyOrder;

    /**
     * country to shift armies from.
     */
    Airlift d_invalidAirLift1;

    /**
     * Game State.
     */
    GameState d_gameState;

    /**
     * Setup before the tests.
     *
     * @throws InvalidMap Exception
     */
    @Before
    public void setup() throws InvalidMap {
        d_gameState = new GameState();
        d_player1 = new Player();
        d_player1.setPlayerName("a");
        d_player2 = new Player("b");


        List<Country> l_countryList = new ArrayList<Country>();
        Country l_country = new Country(0, "France", 1);
        l_country.setD_armies(9);
        l_countryList.add(l_country);

        Country l_countryNeighbour = new Country(1, "Belgium", 1);
        l_countryNeighbour.addNeighbour(0);
        l_country.addNeighbour(1);
        l_countryNeighbour.setD_armies(10);
        l_countryList.add(l_countryNeighbour);

        List<Country> l_countryList2 = new ArrayList<Country>();
        Country l_countryNotNeighbour = new Country(2, "Spain", 1);
        l_countryNotNeighbour.setD_armies(15);
        l_countryList2.add(l_countryNotNeighbour);

        Map l_map = new Map();
        l_map.setD_countries(new ArrayList<Country>(){{ addAll(l_countryList); addAll(l_countryList2); }});

        d_gameState.setD_map(l_map);
        d_player1.setD_coutriesOwned(l_countryList);
        d_player2.setD_coutriesOwned(l_countryList2);
        List<Player> l_playerList = new ArrayList<Player>();
        l_playerList.add(d_player1);
        l_playerList.add(d_player2);
        d_gameState.setD_players(l_playerList);
        d_diplomacyOrder = new Diplomacy(d_player2.getPlayerName(), d_player1);
        d_bombOrder = new Bomb(d_player2, "France");
    }

    /**
     * Tests if diplomacy works.
     */
    @Test
    public void testDiplomacyExecution(){
        d_diplomacyOrder.execute(d_gameState);
        assertEquals(d_player1.d_negotiatedWith.get(0), d_player2);
    }

    /**
     * Tests the next orders after negotiation if they work.
     */
    @Test
    public void NegotiationWorking(){
        d_diplomacyOrder.execute(d_gameState);
        d_bombOrder.execute(d_gameState);
        assertEquals(d_gameState.getRecentLog().trim(), "Log: Bomb card order : bomb France is not executed as b has negotiation pact with the target country's player!");
    }
}
