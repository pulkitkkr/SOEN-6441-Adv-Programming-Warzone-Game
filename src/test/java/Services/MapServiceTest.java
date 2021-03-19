package Services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Map;
import Utils.CommonUtil;

/**
 * This class is used to test functionality of MapService class functions.
 */
public class MapServiceTest {

	/**
	 * MapService reference to store its object.
	 */
	MapService d_mapservice;

	/**
	 * Map reference to store its object.
	 */
	Map d_map;

	/**
	 * GameState reference to store its object.
	 */
	GameState d_state;

	/**
	 * Setup before each MapService Operations
	 */
	@Before
	public void setup() throws InvalidMap {
		d_mapservice = new MapService();
		d_map = new Map();
		d_state = new GameState();
		d_map = d_mapservice.loadMap(d_state, "europe.map");
	}

	/**
	 * This test case is used to test the functionality of EditMap function.
	 *
	 * @throws IOException throws IOException
	 */
	@Test
	public void testEditMap() throws IOException, InvalidMap {
		d_mapservice.editMap(d_state, "test.map");
		File l_file = new File(CommonUtil.getMapFilePath("test.map"));

		assertTrue(l_file.exists());
	}

	/**
	 * tests addition of continent via editcontinent operation
	 * @throws IOException Exceptions
	 * @throws InvalidMap Exception
	 * @throws InvalidCommand Exception
	 */
	@Test
	public void testEditContinentAdd() throws IOException, InvalidMap, InvalidCommand {
		d_state.setD_map(new Map());
		Map l_updatedContinents = d_mapservice.addRemoveContinents(d_state.getD_map(), "Add", "Asia 10");

		assertEquals(l_updatedContinents.getD_continents().size(), 1);
		assertEquals(l_updatedContinents.getD_continents().get(0).getD_continentName(), "Asia");
		assertEquals(l_updatedContinents.getD_continents().get(0).getD_continentValue().toString(), "10");
	}

	/**
	 * tests removal of continent via editcontinent operation
	 *
	 * @throws IOException Exceptions
	 * @throws InvalidMap Exception
	 * @throws InvalidCommand Exception
	 */
	@Test
	public void testEditContinentRemove() throws IOException, InvalidMap, InvalidCommand {
		List<Continent> l_continents = new ArrayList<>();
		Continent l_c1 = new Continent();
		l_c1.setD_continentID(1);
		l_c1.setD_continentName("Asia");
		l_c1.setD_continentValue(10);

		Continent l_c2 = new Continent();
		l_c2.setD_continentID(2);
		l_c2.setD_continentName("Europe");
		l_c2.setD_continentValue(20);

		l_continents.add(l_c1);
		l_continents.add(l_c2);

		Map l_map = new Map();
		l_map.setD_continents(l_continents);
		d_state.setD_map(l_map);
		Map l_updatedContinents = d_mapservice.addRemoveContinents(d_state.getD_map(), "Remove", "Asia");

		assertEquals(l_updatedContinents.getD_continents().size(), 1);
		assertEquals(l_updatedContinents.getD_continents().get(0).getD_continentName(), "Europe");
		assertEquals(l_updatedContinents.getD_continents().get(0).getD_continentValue().toString(), "20");
	}

	/**
	 * This test case is used to test functionality of Load map continent Id and
	 * values.
	 */
	@Test
	public void testContinentIdAndValues() {
		List<Integer> l_actualContinentIdList = new ArrayList<Integer>();
		List<Integer> l_actualContinentValueList = new ArrayList<Integer>();

		List<Integer> l_expectedContinentIdList = new ArrayList<Integer>();
		l_expectedContinentIdList.addAll(Arrays.asList(1, 2, 3, 4));

		List<Integer> l_expectedContinentValueList = new ArrayList<Integer>();
		l_expectedContinentValueList.addAll(Arrays.asList(5, 4, 5, 3));

		for (Continent l_continent : d_map.getD_continents()) {
			l_actualContinentIdList.add(l_continent.getD_continentID());
			l_actualContinentValueList.add(l_continent.getD_continentValue());
		}

		assertEquals(l_expectedContinentIdList, l_actualContinentIdList);
		assertEquals(l_expectedContinentValueList, l_actualContinentValueList);
	}

	/**
	 * This test case is used to test functionality of Load map country Id and
	 * neighbors.
	 */
	@Test
	public void testCountryIdAndNeighbors() {
		List<Integer> l_actualCountryIdList = new ArrayList<Integer>();
		LinkedHashMap<Integer, List<Integer>> l_actualCountryNeighbors = new LinkedHashMap<Integer, List<Integer>>();

		List<Integer> l_expectedCountryIdList = new ArrayList<Integer>();
		l_expectedCountryIdList.addAll(Arrays.asList(1, 2, 3, 4, 5));

		LinkedHashMap<Integer, List<Integer>> l_expectedCountryNeighbors = new LinkedHashMap<Integer, List<Integer>>() {
			{
				put(1, new ArrayList<Integer>(Arrays.asList(8, 21, 6, 7, 5, 2, 3, 4)));
				put(2, new ArrayList<Integer>(Arrays.asList(8, 1, 3)));
				put(3, new ArrayList<Integer>(Arrays.asList(1, 2)));
				put(4, new ArrayList<Integer>(Arrays.asList(22, 1, 5)));
				put(5, new ArrayList<Integer>(Arrays.asList(1, 4)));
			}
		};

		for (Country l_country : d_map.getD_countries()) {
			ArrayList<Integer> l_neighbours = new ArrayList<Integer>();
			l_actualCountryIdList.add(l_country.getD_countryId());
			l_neighbours.addAll(l_country.getD_adjacentCountryIds());
			l_actualCountryNeighbors.put(l_country.getD_countryId(), l_neighbours);
		}

		assertEquals(l_expectedCountryIdList, l_actualCountryIdList);
		assertEquals(l_expectedCountryNeighbors, l_actualCountryNeighbors);
	}

	/**
	 * Tests the savemap operation on an Invalid Map
	 *
	 *  @throws InvalidMap Exception
	 */
	@Test(expected = InvalidMap.class)
	public void testSaveInvalidMap() throws InvalidMap {
		d_map.setD_mapFile("europe.map");
		d_state.setD_map(d_map);
		d_mapservice.saveMap(d_state, "europe.map");
	}

	/**
	 * Tests the add country operation via editCountry
	 * @throws IOException Exception
	 * @throws InvalidMap Exception
	 * @throws InvalidCommand Exception
	 */
	@Test
	public void testEditCountryAdd() throws IOException, InvalidMap, InvalidCommand {
		d_mapservice.loadMap(d_state, "test.map");
		d_mapservice.editFunctions(d_state, "add", "China Asia", 2);

		assertEquals(d_state.getD_map().getCountryByName("China").getD_countryName(), "China");
	}

	/**
	 * Tests the Remove Country Operation via editcountry
	 * @throws InvalidMap Exception
	 * @throws InvalidCommand Exception
	 */
	@Test(expected = InvalidMap.class)
	public void testEditCountryRemove() throws InvalidMap, IOException, InvalidCommand {
		d_mapservice.loadMap(d_state, "test.map");
		d_mapservice.editFunctions(d_state, "remove", "Ukraine", 2);
	}

	/**
	 * Tests the add neighbor operation via editneighbor
	 * @throws InvalidMap Exception
	 * @throws IOException Exception
	 * @throws InvalidCommand Exception
	 */
	@Test
	public void testEditNeighborAdd() throws InvalidMap, IOException, InvalidCommand {
		d_mapservice.loadMap(d_state, "test.map");
		d_mapservice.editFunctions(d_state, "Northern-America 10", "add", 1 );
		d_mapservice.editFunctions(d_state, "add", "Canada Northern-America",  2);
		d_mapservice.editFunctions(d_state, "add","Alaska Northern-America", 2);
		d_mapservice.editFunctions(d_state, "add", "Canada Alaska", 3);

		assertEquals(d_state.getD_map().getCountryByName("Canada").getD_adjacentCountryIds().get(0), d_state.getD_map().getCountryByName("Alaska").getD_countryId());
	}

	/**
	 * Tests the remove neighbor operation via editneighbor
	 * @throws InvalidMap Exception
	 * @throws IOException Exception
	 * @throws InvalidCommand Exception
	 */
	@Test(expected = InvalidMap.class)
	public void testEditNeighborRemove() throws InvalidMap, IOException, InvalidCommand{
		d_mapservice.editMap(d_state, "testedit.map");
		d_mapservice.editFunctions(d_state, "add", "Asia 9",  1);
		d_mapservice.editFunctions(d_state, "add", "Maldives Asia", 2);
		d_mapservice.editFunctions(d_state, "add", "Singapore Asia", 2);
		d_mapservice.editFunctions(d_state, "add", "Singapore Maldives", 3);
		d_mapservice.editFunctions(d_state, "remove", "Maldives Singapore", 3);
	}
}
