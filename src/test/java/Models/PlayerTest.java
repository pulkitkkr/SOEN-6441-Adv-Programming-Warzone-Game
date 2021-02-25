package Models;

import static org.junit.Assert.assertEquals;

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

	/**
	 * The setup is called before each test case of this class is executed.
	 */
	@Before
	public void setup() {
		d_exisitingPlayerList.add(new Player("Avneet"));
		d_exisitingPlayerList.add(new Player("Zalak"));
	}


	/**
	 * Tests first order in the player�s list of orders, then check if its removed
	 * from the list.
	 */
	@Test
	public void testNextOrder() {

		Order l_order1 = new Order();
		l_order1.setD_orderAction("deploy");
		l_order1.setD_numberOfArmiesToPlace(5);
		l_order1.setD_sourceCountryName(null);
		l_order1.setD_targetCountryName("India");

		Order l_order2 = new Order();
		l_order1.setD_orderAction("airlift");
		l_order2.setD_numberOfArmiesToPlace(6);
		l_order2.setD_sourceCountryName("Canada");
		l_order2.setD_targetCountryName("Finland");

		List<Order> l_orderlist = new ArrayList<>();
		l_orderlist.add(l_order1);
		l_orderlist.add(l_order2);

		d_exisitingPlayerList.get(0).setD_ordersToExecute(l_orderlist);
		Order l_order = d_exisitingPlayerList.get(0).next_order();
		assertEquals(l_order, l_order1);
		assertEquals(d_exisitingPlayerList.get(0).getD_ordersToExecute().size(), 1);
	}
}
