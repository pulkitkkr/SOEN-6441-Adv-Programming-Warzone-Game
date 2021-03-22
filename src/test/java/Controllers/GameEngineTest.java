package Controllers;

import static Constants.ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import Models.Continent;
import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.GameState;
import Models.Map;
import Utils.Command;

/**
 * This class is used to test functionality of GameEngineController class functions.
 */
public class GameEngineTest {

	/**
	 * Object of Map class.
	 */
	Map d_map;
	
	/**
	 * object of GameState class.
	 */
	GameState d_state;
	
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
		d_state = d_gameEngine.getD_gameState();
	}

	/**
	 * Tests the {@link InvalidCommand } in editmap command.
	 * 
	 * @throws IOException Exception
	 * @throws InvalidCommand Exception
	 */
	@Test(expected = InvalidCommand.class)
	public void testPerformEditMapInvalidCommand() throws IOException, InvalidCommand, InvalidMap {
		Command l_command = new Command("editmap");
		d_gameEngine.performMapEdit(l_command);
	}

	/**
	 * Tests the {@link InvalidCommand} in editcontinent command
	 * @throws IOException Exception
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap Exception
	 */
	@Test(expected = InvalidCommand.class)
	public void testPerformEditContinentInvalidCommand() throws InvalidCommand, IOException, InvalidMap {
		Command l_command = new Command("editcontinent -add");
		d_gameEngine.performEditContinent(l_command);
	}

	/**
	 * Tests the editcontinent command
	 * @throws IOException Exception
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap Exception
	 */
	@Test
	public void testPerformEditContinentValidCommand() throws IOException, InvalidCommand, InvalidMap {
		d_map.setD_mapFile("testeditmap.map");
		d_state.setD_map(d_map);
		Command l_addCommand = new Command("editcontinent -add Europe 10 -add America 20");
		d_gameEngine.performEditContinent(l_addCommand);

		List<Continent> l_continents = d_state.getD_map().getD_continents();
		assertEquals(l_continents.size(), 2);
		assertEquals(l_continents.get(0).getD_continentName(), "Europe");
		assertEquals(l_continents.get(0).getD_continentValue().toString(), "10");
		assertEquals(l_continents.get(1).getD_continentName(), "America");
		assertEquals(l_continents.get(1).getD_continentValue().toString(), "20");

		Command l_removeCommand = new Command("editcontinent -remove Europe");
		d_gameEngine.performEditContinent(l_removeCommand);
		l_continents = d_state.getD_map().getD_continents();
		assertEquals( 1, l_continents.size());
	}

	/**
	 * Tests the {@link InvalidCommand } in savemap
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap Exception
	 */
	@Test(expected = InvalidCommand.class)
	public void testPerformSaveMapInvalidCommand() throws InvalidCommand, InvalidMap {
		Command l_command = new Command("savemap");
		d_gameEngine.performSaveMap(l_command);
	}
	
	/**
	 * Tests if the assigned country is valid of not.
	 * 
	 * @throws InvalidCommand Exception
	 * @throws IOException Exception
	 */
	@Test(expected = InvalidCommand.class)
	public void testAssignCountriesInvalidCommand() throws InvalidCommand, IOException {
		Command l_command = new Command("assigncountries -add india");
		d_gameEngine.assignCountries(l_command);
	}
}
