package Controllers;

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
import Services.MapService;
import Utils.Command;

public class GameEngineControllerTest {

	MapService d_mapservice;
	Map d_map;
	GameState d_state;
	GameEngineController d_gameEngine;

	@Before
	public void setup() {
		d_mapservice = new MapService();
		d_map = new Map();
		d_gameEngine = new GameEngineController();
		d_state = d_gameEngine.getD_gameState();
	}

	@Test(expected = InvalidCommand.class)
	public void testPerformEditMapInvalidCommand() throws IOException, InvalidCommand {
		Command l_command = new Command("editmap");
		d_gameEngine.performMapEdit(l_command, d_mapservice);
	}

	@Test(expected = InvalidCommand.class)
	public void testPerformEditContinentInvalidCommand() throws IOException, InvalidCommand {
		Command l_command = new Command("editcontinent -add");
		d_gameEngine.performEditContinent(l_command, d_mapservice);
	}

	@Test
	public void testPerformEditContinentValidCommand() throws IOException, InvalidCommand {
		d_map.setD_mapFile("testeditmap.map");
		d_state.setD_map(d_map);
		Command l_addCommand = new Command("editcontinent -add Europe 10 -add America 20");
		d_gameEngine.performEditContinent(l_addCommand, d_mapservice);

		List<Continent> l_continents = d_state.getD_map().getD_continents();
		assertEquals(l_continents.size(), 2);
		assertEquals(l_continents.get(0).getD_continentName(), "Europe");
		assertEquals(l_continents.get(0).getD_continentValue().toString(), "10");
		assertEquals(l_continents.get(1).getD_continentName(), "America");
		assertEquals(l_continents.get(1).getD_continentValue().toString(), "20");

		Command l_removeCommand = new Command("editcontinent -remove Europe");
		d_gameEngine.performEditContinent(l_removeCommand, d_mapservice);
		l_continents = d_state.getD_map().getD_continents();
		assertEquals( 1, l_continents.size());
	}

	@Test(expected = InvalidCommand.class)
	public void testPerformSaveMapInvalidCommand() throws IOException, InvalidCommand, InvalidMap {
		Command l_command = new Command("savemap");
		d_gameEngine.performSaveMap(l_command, d_mapservice);
	}
}
