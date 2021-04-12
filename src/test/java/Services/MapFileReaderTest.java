package Services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidMap;
import Models.GameState;
import Models.Map;

/**
 * Test class for parsing map file to map of game state.
 *
 */
public class MapFileReaderTest {
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
	 * File reader to parse the map file.
	 */
	MapFileReader d_mapFileReader;

	/**
	 * Setup before each MapService Operations
	 * 
	 * @throws InvalidMap Invalid map exception
	 */
	@Before
	public void setup() throws InvalidMap {
		d_mapFileReader = new MapFileReader();
		d_mapservice = new MapService();
		d_map = new Map();
		d_state = new GameState();
		d_mapLines = d_mapservice.loadFile("canada.map");
	}

	/**
	 * This test case is used to test the functionality of reading conquest map.
	 *
	 *
	 * @throws IOException throws IOException
	 * @throws InvalidMap Invalid map exception
	 */
	@Test
	public void testReadMapFile() throws IOException, InvalidMap {
		d_mapFileReader.parseMapFile(d_state, d_map, d_mapLines);
		
		assertNotNull(d_state.getD_map());
		assertEquals(d_state.getD_map().getD_continents().size(), 6);
		assertEquals(d_state.getD_map().getD_countries().size(), 31);
	}
	
}