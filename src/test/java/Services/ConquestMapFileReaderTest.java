package Services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.GameState;
import Models.Map;

/**
 * @author admin
 *
 */
public class ConquestMapFileReaderTest {
	/**
	 * MapService reference to store its object.
	 */
	MapService d_mapservice;

	/**
	 * Map reference to store its lines.
	 */
	List<String> d_mapLines;
	
	/**
	 * Map reference to store its context.
	 */
	Map d_map;

	/**
	 * GameState reference to store its object.
	 */
	GameState d_state;
	
	/**
	 * Conquest file reader to parse the map file.
	 */
	ConquestMapFileReader d_conquestMapFileReader;

	/**
	 * Setup before each MapService Operations
	 * 
	 * @throws InvalidMap Invalid map exception
	 */
	@Before
	public void setup() throws InvalidMap {
		d_conquestMapFileReader = new ConquestMapFileReader();
		d_mapservice = new MapService();
		d_map = new Map();
		d_state = new GameState();
		d_mapLines = d_mapservice.loadFile("testconquest.map");
	}

	/**
	 * This test case is used to test the functionality of reading conquest map.
	 *
	 *
	 * @throws IOException throws IOException
	 * @throws InvalidMap Invalid map exception
	 */
	@Test
	public void testReadConquestFile() throws IOException, InvalidMap {
		d_conquestMapFileReader.readConquestFile(d_state, d_map, d_mapLines);
		
		assertNotNull(d_state.getD_map());
		assertEquals(d_state.getD_map().getD_continents().size(), 8);
		assertEquals(d_state.getD_map().getD_countries().size(), 99);
	}
	/**
	 * tests addition or deletion of continent via editcontinent operation
	 * @throws IOException Exceptions
	 * @throws InvalidMap Exception
	 * @throws InvalidCommand Exception
	 */
	@Test
	public void testEditMap() throws IOException, InvalidMap, InvalidCommand {
		d_conquestMapFileReader.readConquestFile(d_state, d_map, d_mapLines);
		Map l_updatedContinents = d_mapservice.addRemoveContinents(d_state, d_state.getD_map(), "Add", "Asia 10");
		l_updatedContinents = d_mapservice.addRemoveContinents(d_state, d_state.getD_map(), "Add", "Europe 20");
		
		assertEquals(l_updatedContinents.getD_continents().size(), 10);
		assertEquals(l_updatedContinents.getD_continents().get(8).getD_continentName(), "Asia");
		assertEquals(l_updatedContinents.getD_continents().get(8).getD_continentValue().toString(), "10");
		
		l_updatedContinents = d_mapservice.addRemoveContinents(d_state, d_state.getD_map(), "Remove", "Asia");
		assertEquals(l_updatedContinents.getD_continents().size(), 9);
		
		d_mapservice.editFunctions(d_state, "add", "Swiss Europe", 2);
		d_mapservice.editFunctions(d_state, "add", "Norway Europe", 2);
		assertEquals(d_state.getD_map().getD_countries().size(), 101);
		
		d_mapservice.editFunctions(d_state, "remove", "Swiss", 2);
		assertEquals(d_state.getD_map().getD_countries().size(), 100);
	}
}
