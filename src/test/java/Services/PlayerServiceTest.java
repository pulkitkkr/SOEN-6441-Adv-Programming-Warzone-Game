package Services;

import Models.GameState;
import Models.Map;
import Models.Player;
import Utils.CommonUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class PlayerServiceTest{
    /**
     * Player class reference.
     */
    Player d_playerInfo;

    /**
     * Player Service reference.
     */
    PlayerService d_playerService;

    /**
     * Map reference to store its object.
     */
    Map d_map;

    /**
     * GameState reference to store its object.
     */
    GameState d_gameState;

    /**
     * MapService reference to store its object.
     */
    MapService d_mapservice;

    /**
     * Existing Player List.
     */
    List<Player> d_exisitingPlayerList = new ArrayList<>();

    private final ByteArrayOutputStream d_outContent = new ByteArrayOutputStream();

    /**
     * The setup is called before each test case of this class is executed.
     */
    @Before
    public void setup() {
        d_playerInfo = new Player();
        d_playerService = new PlayerService();
        d_gameState = new GameState();
        d_exisitingPlayerList.add(new Player("Avneet"));
        d_exisitingPlayerList.add(new Player("Zalak"));
    }



    /**
     * The testAddPlayers is used to test the add functionality of addRemovePlayers
     * function.
     */
    @Test
    public void testAddPlayers() {
        assertFalse(CommonUtil.isCollectionEmpty(d_exisitingPlayerList));
        List<Player> l_updatedPlayers = d_playerService.addRemovePlayers(d_exisitingPlayerList, "add", "Jhanvi");
        assertEquals("Jhanvi", l_updatedPlayers.get(2).getPlayerName());

        System.setOut(new PrintStream(d_outContent));
        d_playerService.addRemovePlayers(d_exisitingPlayerList, "add", "Avneet");
        assertEquals("Player with name : Avneet already Exists. Changes are not made.", d_outContent.toString());
    }

    /**
     * The testRemovePlayers is used to t
     * est the remove functionality of
     * addRemovePlayers function.
     */
    @Test
    public void testRemovePlayers() {
        List<Player> l_updatedPlayers = d_playerService.addRemovePlayers(d_exisitingPlayerList, "remove", "Avneet");
        assertEquals(1, l_updatedPlayers.size());

        System.setOut(new PrintStream(d_outContent));
        d_playerService.addRemovePlayers(d_exisitingPlayerList, "remove", "Bhoomi");
        assertEquals("Player with name : Bhoomi does not Exist. Changes are not made.", d_outContent.toString());
    }

    /**
     * Used for checking whether players have been added or not
     */
    @Test
    public void testPlayersAvailability() {
        boolean l_playersExists = d_playerService.checkPlayersAvailability(d_gameState);
        assertFalse(l_playersExists);
    }

    /**
     * Used for checking whether players have been assigned with countries
     */
    @Test
    public void testPlayerCountryAssignment() {
        d_mapservice = new MapService();
        d_map = new Map();
        d_map = d_mapservice.loadMap(d_gameState, "canada.map");
        d_gameState.setD_map(d_map);
        d_gameState.setD_players(d_exisitingPlayerList);
        d_playerService.assignCountries(d_gameState);

        int l_assignedCountriesSize = 0;
        for (Player l_pl : d_gameState.getD_players()) {
            assertNotNull(l_pl.getD_coutriesOwned());
            l_assignedCountriesSize = l_assignedCountriesSize + l_pl.getD_coutriesOwned().size();
        }
        assertEquals(l_assignedCountriesSize, d_gameState.getD_map().getD_countries().size());
    }
}