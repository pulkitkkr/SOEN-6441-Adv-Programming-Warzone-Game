package Models;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * This is the OrderTest Class.
 * 
 */
public class OrderTest {

	/**
	 * Order class reference.
	 */
	Order d_orderDetails;

	/**
	 * Player class reference.
	 */
	Player d_playerInfo;

	/**
	 * The setup is called before each test case of this class is executed.
	 */
	@Before
	public void setup() {
		d_orderDetails = new Order();
		d_playerInfo = new Player();
	}

	/**
	 * Used to test country name entered by player in deploy command to check if the
	 * entered country name belongs to player or not. If it does not belongs to
	 * player, order will not be executed.
	 */
	@Test
	public void testValidateDeployOrderCountry() {
		d_orderDetails.setD_targetCountryName("India");
		List<Country> l_countryList = new ArrayList<Country>();
		l_countryList.add(new Country("India"));
		l_countryList.add(new Country("Canada"));
		d_playerInfo.setD_coutriesOwned(l_countryList);
		boolean l_actualBoolean = d_orderDetails.validateDeployOrderCountry(d_playerInfo, d_orderDetails);
		assertTrue(l_actualBoolean);
	}
}
