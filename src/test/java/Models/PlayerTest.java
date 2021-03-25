package Models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * This class is used to test functionality of Player class functions.
 *
 */
public class PlayerTest {
	/**
	 * Existing Player List.
	 */
	List<Player> d_exisitingPlayerList = new ArrayList<>();
	/**
	 * Order List
	 */
	List<Order> d_order_list = new ArrayList<Order>();

	/**
	 * Player object
	 */
	Player d_player = new Player();

	/**
	 * Current State of the Game.
	 */
	GameState l_gs = new GameState();
	/**
	 * The setup is called before each test case of this class is executed.
	 */
	@Before
	public void setup() {
		d_exisitingPlayerList.add(new Player("Avneet"));
		d_exisitingPlayerList.add(new Player("Zalak"));
		
		Map l_map = new Map();
		Country l_c1 = new Country(1, "Finland", 10);
		l_c1.setD_adjacentCountryIds(Arrays.asList(2));
		Country l_c2 = new Country(2, "France", 10);
		List<Country> l_countryList = new ArrayList<>();
		l_countryList.add(l_c1);
		l_countryList.add(l_c2);
		l_map.setD_countries(l_countryList);
		l_gs.setD_map(l_map);
	}

	/**
	 * Tests first order in the players list of orders, then check if its removed
	 * from the list.
	 */
	@Test
	public void testNextOrder() {

		Order l_deployOrder1 = new Deploy(d_exisitingPlayerList.get(0), "India", 5);
		Order l_deployOrder2 = new Deploy(d_exisitingPlayerList.get(1), "Finland", 6);

		d_order_list.add(l_deployOrder1);
		d_order_list.add(l_deployOrder2);

		d_exisitingPlayerList.get(0).setD_ordersToExecute(d_order_list);
		d_exisitingPlayerList.get(1).setD_ordersToExecute(d_order_list);

		Order l_order = d_exisitingPlayerList.get(0).next_order();
		assertEquals(l_deployOrder1, l_order);
		assertEquals(1, d_exisitingPlayerList.get(0).getD_ordersToExecute().size());
	}

	/**
	 * Used to check that player cannot deploy more armies than there is in their
	 * reinforcement pool.
	 */
	@Test
	public void testValidateDeployOrderArmies() {
		d_player.setD_noOfUnallocatedArmies(10);
		String l_noOfArmies = "4";
		boolean l_bool = d_player.validateDeployOrderArmies(d_player, l_noOfArmies);
		assertFalse(l_bool);
	}

	/**
	 * Checks whether unassigned armies are updated after firing deploy order and
	 * verifies unexecuted orders list.
	 */
	@Test
	public void testCreateDeployOrder() {
		Player l_pl = new Player("abc");
		l_pl.setD_noOfUnallocatedArmies(20);
		l_pl.createDeployOrder("Deploy India 5");
		assertEquals(l_pl.getD_noOfUnallocatedArmies().toString(), "15");
		assertEquals(l_pl.getD_ordersToExecute().size(), 1);
	}

	/**
	 * Checks whether countries given in advance order are adjacent.
	 */
	@Test
	public void testCountryExists() {		
		Player l_player = new Player("abc");
		assertTrue(l_player.checkAdjacency(l_gs, "Finland", "France"));
		assertFalse(l_player.checkAdjacency(l_gs, "France", "Finland"));
	}
	
	/**
	 * Checks whether advance order given is added in player's queue or not.
	 */
	@Test
	public void testCreateAdvanceOrder() {
		Player l_player = new Player("xyz");
		l_player.createAdvanceOrder("advance Finland France 10", l_gs);
		assertEquals(l_player.getD_ordersToExecute().size(), 1);
	}
	/**
	 * Checks whether advance order given is not added in player's queue as it is invalid.
	 */
	@Test
	public void testCreateAdvanceOrderFailure() {
		Player l_player = new Player("xyz");
		l_player.createAdvanceOrder("advance Finland France 0", l_gs);
		assertEquals(l_player.getD_ordersToExecute().size(), 0);
	}
}
