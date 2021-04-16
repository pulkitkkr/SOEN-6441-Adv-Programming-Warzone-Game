package Controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.Continent;
import Models.GameState;
import Models.Map;
import Models.Phase;
import Models.StartUpPhase;

/**
 * This class is used to test functionality of GameEngineController class
 * functions.
 */
public class GameEngineTest {

	/**
	 * Object of Map class.
	 */
	Map d_map;

	/**
	 * object of GameState class.
	 */
	Phase d_currentPhase;

	/**
	 * object of GameEngineController class.
	 */
	GameEngine d_gameEngine;

	/**
	 * setup before each test case.
	 */
	@Before
	public void setup() {
		d_map = new Map();
		d_gameEngine = new GameEngine();
		d_currentPhase = d_gameEngine.getD_CurrentPhase();
	}

	/**
	 * Tests the {@link InvalidCommand } in editmap command.
	 * 
	 * @throws IOException    Exception
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap Exception
	 */
	@Test(expected = InvalidCommand.class)
	public void testPerformEditMapInvalidCommand() throws IOException, InvalidCommand, InvalidMap {
		d_currentPhase.handleCommand("editmap");
	}

	/**
	 * Tests the {@link InvalidCommand} in editcontinent command
	 * 
	 * @throws IOException    Exception
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap     Exception
	 */
	@Test
	public void testPerformEditContinentInvalidCommand() throws InvalidCommand, IOException, InvalidMap {
		d_currentPhase.handleCommand("editcontinent");
		GameState l_state = d_currentPhase.getD_gameState();

		assertEquals("Log: Can not Edit Continent, please perform `editmap` first" + System.lineSeparator(),
				l_state.getRecentLog());
	}

	/**
	 * Tests the editcontinent command
	 * 
	 * @throws IOException    Exception
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap     Exception
	 */
	@Test
	public void testPerformEditContinentValidCommand() throws IOException, InvalidCommand, InvalidMap {
		d_map.setD_mapFile("testeditmap.map");
		GameState l_state = d_currentPhase.getD_gameState();

		l_state.setD_map(d_map);
		d_currentPhase.setD_gameState(l_state);

		d_currentPhase.handleCommand("editcontinent -add Europe 10 -add America 20");

		l_state = d_currentPhase.getD_gameState();

		List<Continent> l_continents = l_state.getD_map().getD_continents();
		assertEquals(2, l_continents.size());
		assertEquals("Europe", l_continents.get(0).getD_continentName());
		assertEquals("10", l_continents.get(0).getD_continentValue().toString());
		assertEquals("America", l_continents.get(1).getD_continentName());
		assertEquals("20", l_continents.get(1).getD_continentValue().toString());

		d_currentPhase.handleCommand("editcontinent -remove Europe");

		l_state = d_currentPhase.getD_gameState();
		l_continents = l_state.getD_map().getD_continents();
		assertEquals(1, l_continents.size());
	}

	/**
	 * Tests the {@link InvalidCommand } in savemap
	 *
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap     Exception
	 * @throws IOException Exception
	 */
	@Test
	public void testPerformSaveMapInvalidCommand() throws InvalidCommand, InvalidMap, IOException {
		d_currentPhase.handleCommand("savemap");
		GameState l_state = d_currentPhase.getD_gameState();

		assertEquals("Log: No map found to save, Please `editmap` first" + System.lineSeparator(),
				l_state.getRecentLog());

	}

	/**
	 * Tests savegame command.
	 *
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap     Exception
	 * @throws IOException Exception
	 */
	@Test
	public void testPerformSaveGameValidCommand() throws InvalidCommand, InvalidMap, IOException {
		d_currentPhase.handleCommand("savegame hello.txt");
		GameState l_state = d_currentPhase.getD_gameState();

		assertEquals("Log: Game Saved Successfully to hello.txt" + System.lineSeparator(),
				l_state.getRecentLog());

	}
	
	/**
	 * Tests loadgame command.
	 *
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap     Exception
	 * @throws IOException Exception
	 */
	@Test(expected = FileNotFoundException.class)
	public void testPerformLoadGameValidCommand() throws InvalidCommand, InvalidMap, IOException {
		d_currentPhase.handleCommand("loadgame abc.txt");
	}

	/**
	 * Tests if the assigned country is valid of not.
	 * 
	 * @throws InvalidCommand Exception
	 * @throws IOException    Exception
	 * @throws InvalidMap Exception
	 */
	@Test(expected = InvalidCommand.class)
	public void testAssignCountriesInvalidCommand() throws IOException, InvalidMap, InvalidCommand {
		d_currentPhase.getD_gameState().setD_loadCommand();
		d_currentPhase.handleCommand("assigncountries -add india");
		;
	}

	/**
	 * Validates correct startup phase.
	 */
	@Test
	public void testCorrectStartupPhase() {
		assertTrue(d_gameEngine.getD_CurrentPhase() instanceof StartUpPhase);
	}
}
