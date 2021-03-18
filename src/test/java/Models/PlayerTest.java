package Models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
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
	List<Order> order_list = new ArrayList<Order>();

	Player d_player = new Player();

	/**
	 * The setup is called before each test case of this class is executed.
	 */
	@Before
	public void setup() {
		d_exisitingPlayerList.add(new Player("Avneet"));
		d_exisitingPlayerList.add(new Player("Zalak"));
	}

	/**
	 * Tests first order in the players list of orders, then check if its removed
	 * from the list.
	 */
	@Test
	public void testNextOrder() {

		Player player1 = d_exisitingPlayerList.get(0);
		Player player2 = d_exisitingPlayerList.get(1);

		Order o1 = new Deploy(player1, "India", 5);
		Order o2 = new Deploy(player2, "Finland", 6);

		order_list.add(o1);
		order_list.add(o2);

		d_exisitingPlayerList.get(0).setD_ordersToExecute(order_list);

		d_exisitingPlayerList.get(1).setD_ordersToExecute(order_list);

		Order l_order = d_exisitingPlayerList.get(0).next_order();
		assertEquals(o1, l_order);
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
}
