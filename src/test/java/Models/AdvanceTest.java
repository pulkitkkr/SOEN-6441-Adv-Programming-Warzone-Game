package Models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * This class is used to test functionality of Advance order.
 */
public class AdvanceTest {
	/**
	 * Checks whether advance order given is invalid.
	 */
	@Test
	public void testInvalidAdvanceOrder() {
		Player l_player = new Player("Zalak");
		Country l_country1 = new Country("India");
		l_country1.setD_armies(12);
		Country l_country2 = new Country("Canada");
		l_country2.setD_armies(10);
		List<Country> l_countries = Arrays.asList(l_country1, l_country2);
		l_player.setD_coutriesOwned(l_countries);

		assertFalse(new Advance(l_player, "India", "France", 15).valid());
		assertFalse(new Advance(l_player, "Canada", "France", 10).valid());
		assertFalse(new Advance(l_player, "Italy", "France", 10).valid());
		assertTrue(new Advance(l_player, "India", "France", 10).valid());
	}

	/**
	 * Checks if Attacker has won battle, countries and armies are updated correctly
	 * or not.
	 */
	@Test
	public void testAttackersWin() {
		Player l_sourcePlayer = new Player("Zalak");
		Country l_country1 = new Country("India");
		l_country1.setD_armies(7);
		List<Country> l_s1 = new ArrayList<>();
		l_s1.add(l_country1);
		l_sourcePlayer.setD_coutriesOwned(l_s1);

		Player l_targetPlayer = new Player("Mike");
		Country l_country2 = new Country("Canada");
		l_country2.setD_armies(4);
		List<Country> l_s2 = new ArrayList<>();
		l_s2.add(l_country2);
		l_targetPlayer.setD_coutriesOwned(l_s2);

		Advance l_advance = new Advance(l_sourcePlayer, "India", "Canada", 5);
		l_advance.handleSurvivingArmies(5, 0, l_country1, l_country2, l_targetPlayer);

		assertEquals(l_targetPlayer.getD_coutriesOwned().size(), 0);
		assertEquals(l_sourcePlayer.getD_coutriesOwned().size(), 2);
		assertEquals(l_sourcePlayer.getD_coutriesOwned().get(1).getD_armies().toString(), "5");
	}

	/**
	 * Checks if Defender has won battle, countries and armies are updated correctly
	 * or not.
	 */
	@Test
	public void testDefendersWin() {
		Player l_sourcePlayer = new Player("Zalak");
		Country l_country1 = new Country("India");
		l_country1.setD_armies(2);
		List<Country> l_s1 = new ArrayList<>();
		l_s1.add(l_country1);
		l_sourcePlayer.setD_coutriesOwned(l_s1);

		Player l_targetPlayer = new Player("Mike");
		Country l_country2 = new Country("Canada");
		l_country2.setD_armies(4);
		List<Country> l_s2 = new ArrayList<>();
		l_s2.add(l_country2);
		l_targetPlayer.setD_coutriesOwned(l_s2);

		Advance l_advance = new Advance(l_sourcePlayer, "India", "Canada", 5);
		l_advance.handleSurvivingArmies(3, 2, l_country1, l_country2, l_targetPlayer);

		assertEquals(l_targetPlayer.getD_coutriesOwned().size(), 1);
		assertEquals(l_sourcePlayer.getD_coutriesOwned().size(), 1);
		assertEquals(l_sourcePlayer.getD_coutriesOwned().get(0).getD_armies().toString(), "5");
		assertEquals(l_targetPlayer.getD_coutriesOwned().get(0).getD_armies().toString(), "2");
	}
	/**
	 * Checks if armies are deployed to target or not.
	 */
	@Test
	public void testDeployToTarget() {
		Player l_sourcePlayer = new Player("Zalak");
		List<Country> l_s1 = new ArrayList<>();
		
		Country l_country1 = new Country("India");
		l_country1.setD_armies(7);
		l_s1.add(l_country1);

		Country l_country2 = new Country("Canada");
		l_country2.setD_armies(4);
		l_s1.add(l_country2);
		l_sourcePlayer.setD_coutriesOwned(l_s1);

		Advance l_advance = new Advance(l_sourcePlayer, "India", "Canada", 3);
		l_advance.deployArmiesToTarget(l_country2);
		assertEquals(l_country2.getD_armies().toString(), "7");
	}
}
