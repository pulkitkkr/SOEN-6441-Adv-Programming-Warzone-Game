package Models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Services.MapService;

/**
 * 
 * This is the PlayerTest Class.
 * 
 */
public class PlayerTest {

	/**
	 * Player class reference.
	 */
	Player d_playerInfo;
	/**
	 * Map reference to store its object.
	 */
	Map d_map;

	/**
	 * GameState reference to store its object.
	 */
	GameState d_state;

	/**
	 * MapService reference to store its object.
	 */
	MapService d_mapservice;

	/**
	 * Existing Player List.
	 */
	List<Player> d_exisitingPlayerList = new ArrayList<Player>();

	private final ByteArrayOutputStream d_outContent = new ByteArrayOutputStream();

	/**
	 * The setup is called before each test case of this class is executed.
	 */
	@Before
	public void setup() {
		System.setOut(new PrintStream(d_outContent));
		d_playerInfo = new Player();
		d_exisitingPlayerList.add(new Player("Avneet"));
		d_exisitingPlayerList.add(new Player("Zalak"));
	}

	/**
	 * The testAddPlayers is used to test the add functionality of addRemovePlayers
	 * function.
	 */
	@Test
	public void testAddPlayers() {
		List<Player> l_updatedPlayers = d_playerInfo.addRemovePlayers(d_exisitingPlayerList, "add", "Jhanvi");
		assertEquals("Jhanvi", l_updatedPlayers.get(2).getPlayerName());
		d_playerInfo.addRemovePlayers(d_exisitingPlayerList, "add", "Avneet");
		assertEquals("Player with name : Avneet already Exists. Changes are not made", d_outContent.toString());
	}

	/**
	 * The testRemovePlayers is used to test the remove functionality of
	 * addRemovePlayers function.
	 */
	@Test
	public void testRemovePlayers() {
		List<Player> l_updatedPlayers = d_playerInfo.addRemovePlayers(d_exisitingPlayerList, "remove", "Avneet");
		assertEquals(1, l_updatedPlayers.size());
		d_playerInfo.addRemovePlayers(d_exisitingPlayerList, "remove", "Bhoomi");
		assertEquals("Player with name : Bhoomi does not Exist. Changes are not made", d_outContent.toString());
	}

	/**
	 * Used for checking whether players have been added or not
	 */
	@Test
	public void testPlayersAvailability() {
		GameState l_gameState = new GameState();
		Boolean l_playersExists = d_playerInfo.checkPlayersAvailability(l_gameState);
		assertFalse(l_playersExists);
	}

	/**
	 * Used for checking whether players have been assigned with countries
	 */
	@Test
	public void testPlayerCountryAssignment() {
		d_mapservice = new MapService();
		d_map = new Map();
		d_state = new GameState();
		d_map = d_mapservice.loadMap(d_state, "canada.map");
		d_state.setD_map(d_map);
		d_state.setD_players(d_exisitingPlayerList);
		d_playerInfo.assignCountries(d_state);

		int l_assignedCountriesSize = 0;
		for (Player l_pl : d_state.getD_players()) {
			assertNotNull(l_pl.getD_coutriesOwned());
			l_assignedCountriesSize = l_assignedCountriesSize + l_pl.getD_coutriesOwned().size();
		}
		assertEquals(l_assignedCountriesSize, d_state.getD_map().getD_countries().size());
	}

	/**
	 * Tests first order in the player’s list of orders, then check if its removed
	 * from the list.
	 */
	@Test
	public void testNextOrder() {

		Order l_order1 = new Order();
		l_order1.setD_orderAction("deploy");
		l_order1.setD_numberOfArmiesToMove(5);
		l_order1.setD_sourceCountryId(null);
		l_order1.setD_targetCountryId(1);

		Order l_order2 = new Order();
		l_order1.setD_orderAction("airlift");
		l_order2.setD_numberOfArmiesToMove(6);
		l_order2.setD_sourceCountryId(5);
		l_order2.setD_targetCountryId(2);

		List<Order> l_orderlist = new ArrayList<>();
		l_orderlist.add(l_order1);
		l_orderlist.add(l_order2);

		d_exisitingPlayerList.get(0).setD_ordersToExecute(l_orderlist);
		Order l_order = d_exisitingPlayerList.get(0).next_order();
		assertEquals(l_order, l_order1);
		assertEquals(d_exisitingPlayerList.get(0).getD_ordersToExecute().size(), 1);
	}
}
