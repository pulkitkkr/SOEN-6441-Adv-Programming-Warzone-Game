package Models;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
	 * Existing Player List.
	 */
	List<Player> l_exisitingPlayerList = new ArrayList<Player>();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	/**
	 * The setup is called before each test case of this class is executed.
	 */
	@Before
	public void setup() {
		System.setOut(new PrintStream(outContent));
		d_playerInfo = new Player();
		l_exisitingPlayerList.add(new Player("Avneet"));
		l_exisitingPlayerList.add(new Player("Zalak"));
	}

	/**
	 * The testAddPlayers is used to test the add functionality of addRemovePlayers
	 * function.
	 */
	@Test
	public void testAddPlayers() {
		List<Player> l_updatedPlayers = d_playerInfo.addRemovePlayers(l_exisitingPlayerList, "add", "Jhanvi");
		assertEquals("Jhanvi", l_updatedPlayers.get(2).getPlayerName());
		d_playerInfo.addRemovePlayers(l_exisitingPlayerList, "add", "Avneet");
		assertEquals("Player with name : Avneet already Exists. Changes are not made", outContent.toString());
	}

	/**
	 * The testRemovePlayers is used to test the remove functionality of
	 * addRemovePlayers function.
	 */
	@Test
	public void testRemovePlayers() {
		List<Player> l_updatedPlayers = d_playerInfo.addRemovePlayers(l_exisitingPlayerList, "remove", "Avneet");
		assertEquals(1, l_updatedPlayers.size());
		d_playerInfo.addRemovePlayers(l_exisitingPlayerList, "remove", "Bhoomi");
		assertEquals("Player with name : Bhoomi does not Exist. Changes are not made", outContent.toString());
	}
}
