package Controllers;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.GameState;
import Models.Map;
import Services.MapService;
import Utils.Command;

public class GameEngineControllerTest {

	@Test(expected = InvalidCommand.class)
	public void test_InvalidCommand_performEditMap() throws IOException, InvalidCommand, InvalidMap {
		MapService l_mapService = new MapService();
		GameState l_gameState = new GameState();
		Command l_command = new Command("editmap");
		GameEngineController.performMapEdit(l_command, l_mapService, l_gameState);
	}

	@Test(expected = InvalidCommand.class)
	public void test_InvalidCommand_performEditContinent() throws IOException, InvalidCommand {
		MapService l_mapService = new MapService();
		GameState l_gameState = new GameState();
		Command l_command = new Command("editcontinent -add");
		GameEngineController.performEditContinent(l_command, l_mapService, l_gameState);
	}

	@Test
	public void test_validCommand_performEditContinent() throws IOException, InvalidCommand {
		MapService l_mapService = new MapService();
		GameState l_gameState = new GameState();
		Map l_map = new Map();
		l_map.setD_mapFile("testeditmap.map");
		l_gameState.setD_map(l_map);
		Command l_addCommand = new Command("editcontinent -add Europe 10 -add America 20");
		GameEngineController.performEditContinent(l_addCommand, l_mapService, l_gameState);
		assertEquals(l_gameState.getD_map().getD_continents().size(), 2);
		assertEquals(l_gameState.getD_map().getD_continents().get(0).getD_continentName(), "Europe");
		assertEquals(l_gameState.getD_map().getD_continents().get(0).getD_continentValue().toString(), "10");
		assertEquals(l_gameState.getD_map().getD_continents().get(1).getD_continentName(), "America");
		assertEquals(l_gameState.getD_map().getD_continents().get(1).getD_continentValue().toString(), "20");

		Command l_removeCommand = new Command("editcontinent -remove Europe");
		GameEngineController.performEditContinent(l_removeCommand, l_mapService, l_gameState);
		assertEquals(l_gameState.getD_map().getD_continents().size(), 1);
	}

	@Test(expected = InvalidCommand.class)
	public void test_InvalidCommand_performSaveMap() throws IOException, InvalidCommand, InvalidMap {
		MapService l_mapService = new MapService();
		GameState l_gameState = new GameState();
		Command l_command = new Command("savemap");
		GameEngineController.performSaveMap(l_command, l_gameState, l_mapService);
	}
}
