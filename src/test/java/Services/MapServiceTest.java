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
import org.junit.Test;

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
	 * The setup is called before each test case of this class is executed.
	 */
	@Before
	public void setup() {
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
	public void testEditMap() throws IOException {
		d_mapservice.editMap(new GameState(), "testeditmap.map");
		File l_file = new File(CommonUtil.getMapFilePath("testeditmap.map"));
		assertTrue(l_file.exists());
	}

	/**
	 * This test case is used to test functionality of Add in addRemoveContinents
	 * function.
	 * 
	 * @throws IOException throws IOException.
	 */
	@Test
	public void testEditContinentAdd() throws IOException {
		List<Continent> l_updatedContinents = d_mapservice.addRemoveContinents(new ArrayList<>(), "Add", "Asia 10");
		assertEquals(l_updatedContinents.size(), 1);
		assertEquals(l_updatedContinents.get(0).getD_continentName(), "Asia");
		assertEquals(l_updatedContinents.get(0).getD_continentValue().toString(), "10");
	}

	/**
	 * This test case is used to test functionality of remove in addRemoveContinents
	 * function.
	 * 
	 * @throws IOException throws IOException.
	 */
	@Test
	public void testEditContinentRemove() throws IOException {

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

		List<Continent> l_updatedContinents = d_mapservice.addRemoveContinents(l_continents, "Remove", "Asia");
		assertEquals(l_updatedContinents.size(), 1);
		assertEquals(l_updatedContinents.get(0).getD_continentName(), "Europe");
		assertEquals(l_updatedContinents.get(0).getD_continentValue().toString(), "20");
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
	 * This test case is used to test functionality of saveMap.
	 * 
	 * @throws InvalidMap throws Invalid Map Exception.
	 */
	@Test(expected = InvalidMap.class)
	public void testSaveInvalidMap() throws InvalidMap {
		d_map.setD_mapFile("europe.map");
		d_state.setD_map(d_map);
		d_mapservice.saveMap(d_state, "europe.map");
	}
}
