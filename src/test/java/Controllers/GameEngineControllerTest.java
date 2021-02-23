package Controllers;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.GameState;
import Models.Map;
import Services.MapService;
import Utils.Command;

public class GameEngineControllerTest {

	MapService d_mapservice;
	Map d_map;
	GameState d_state;

	/**
	 * setup before each test case
	 */
	@Before
	public void setup() {
		d_mapservice = new MapService();
		d_map = new Map();
		d_state = new GameState();
	}

	/**
	 * Tests the {@link InvalidCommand } in editmap command
	 * @throws IOException Exception
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap Exception
	 */
	@Test(expected = InvalidCommand.class)
	public void testPerformEditMapInvalidCommand() throws IOException, InvalidCommand, InvalidMap {
		Command l_command = new Command("editmap");
		GameEngineController.performMapEdit(l_command, d_mapservice, d_state);
	}

	/**
	 * Tests the {@link InvalidCommand} in editcontinent command
	 * @throws IOException Exception
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap Exception
	 */
	@Test(expected = InvalidCommand.class)
	public void testPerformEditContinentInvalidCommand() throws IOException, InvalidCommand, InvalidMap {
		Command l_command = new Command("editcontinent -add");
		GameEngineController.performEditContinent(l_command, d_mapservice, d_state);
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
		GameEngineController.performEditContinent(l_addCommand, d_mapservice, d_state);
		assertEquals(d_state.getD_map().getD_continents().size(), 2);
		assertEquals(d_state.getD_map().getD_continents().get(0).getD_continentName(), "Europe");
		assertEquals(d_state.getD_map().getD_continents().get(0).getD_continentValue().toString(), "10");
		assertEquals(d_state.getD_map().getD_continents().get(1).getD_continentName(), "America");
		assertEquals(d_state.getD_map().getD_continents().get(1).getD_continentValue().toString(), "20");

		Command l_removeCommand = new Command("editcontinent -remove Europe");
		GameEngineController.performEditContinent(l_removeCommand, d_mapservice, d_state);
		assertEquals(d_state.getD_map().getD_continents().size(), 1);
	}

	/**
	 * Tests the {@link InvalidCommand } in savemap
	 * @throws IOException Exception
	 * @throws InvalidCommand Exception
	 * @throws InvalidMap Exception
	 */
	@Test(expected = InvalidCommand.class)
	public void testPerformSaveMapInvalidCommand() throws IOException, InvalidCommand, InvalidMap {
		Command l_command = new Command("savemap");
		GameEngineController.performSaveMap(l_command, d_state, d_mapservice);
	}
}
