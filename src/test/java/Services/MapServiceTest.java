package Services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Models.Continent;
import Models.GameState;
import Utils.CommonUtil;

public class MapServiceTest {
	MapService d_mapservice;

	@Before
	public void setup() {
		d_mapservice = new MapService();
	}
	@Test
	public void editMap() throws IOException {
		d_mapservice.editMap(new GameState(), "testeditmap.map");
		File l_file = new File(CommonUtil.getMapFilePath("testeditmap.map"));
		assertTrue(l_file.exists());
	}
	@Test
	public void test_editContinentAdd() throws IOException {
		List<Continent> l_updatedContinents = d_mapservice.addRemoveContinents(new ArrayList<>(), "Add", "Asia 10");
		assertEquals(l_updatedContinents.size(), 1);
		assertEquals(l_updatedContinents.get(0).getD_continentName(), "Asia");
		assertEquals(l_updatedContinents.get(0).getD_continentValue().toString(), "10");
	}
	@Test
	public void test_editContinentRemove() throws IOException {
		
		List<Continent> l_continents = new ArrayList<>();
		Continent c1 = new Continent();
		c1.setD_continentID(1);
		c1.setD_continentName("Asia");
		c1.setD_continentValue(10);
		
		Continent c2 = new Continent();
		c2.setD_continentID(2);
		c2.setD_continentName("Europe");
		c2.setD_continentValue(20);
		
		l_continents.add(c1);
		l_continents.add(c2);
		
		List<Continent> l_updatedContinents = d_mapservice.addRemoveContinents(l_continents, "Remove", "Asia");
		assertEquals(l_updatedContinents.size(), 1);
		assertEquals(l_updatedContinents.get(0).getD_continentName(), "Europe");
		assertEquals(l_updatedContinents.get(0).getD_continentValue().toString(), "20");
	}
}
