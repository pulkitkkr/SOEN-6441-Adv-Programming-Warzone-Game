package Models;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import Exceptions.InvalidMap;
import Services.MapService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This is the MapTest Class.
 * 
 */
public class MapTest {

    Map d_map;
    MapService d_ms;
    GameState d_gameState;

    /**
     * Checking Map Model Operations
     */
    @Before
    public void beforeValidateTest(){
        d_map=new Map();
        d_gameState=new GameState();
        d_ms= new MapService();
    }

    /**
     * Checking {@link InvalidMap} for no continent in Map
     * @throws InvalidMap Exception
     */
    @Test (expected = InvalidMap.class)
    public void testValidateNoContinent() throws InvalidMap{
        assertEquals(d_map.Validate(), false);
    }

    /**
     * Tests a valid and invalid Map for Validate function
     * @throws InvalidMap
     */
    @Test (expected = InvalidMap.class)
    public void testValidate() throws InvalidMap {
        d_map= d_ms.loadMap(d_gameState, "canada.map");
        assertEquals(d_map.Validate(), true);
        d_map= d_ms.loadMap(d_gameState, "swiss.map");
        d_map.Validate();
    }

    /**
     * Checking {@link InvalidMap} for no country in Map
     * @throws InvalidMap Exception
     */
    @Test (expected = InvalidMap.class)
    public void testValidateNoCountry() throws InvalidMap{
        Continent l_continent = new Continent();
        List <Continent> l_continents = new ArrayList<Continent>();
        l_continents.add(l_continent);
        d_map.setD_continents(l_continents);
        d_map.Validate();
    }

    /**
     * Checks Continent connectivity of an unconnected continent
     * @throws InvalidMap Exception
     */
    @Test (expected = InvalidMap.class)
    public void testContinentConnectivity() throws  InvalidMap{
          d_map= d_ms.loadMap(d_gameState, "continentConnectivity.map");
          d_map.Validate();
    }

    /**
     * Checks Country Connectivity for not connected countries
     * @throws InvalidMap Exception
     */
    @Test(expected = InvalidMap.class)
    public void testCountryConnectivity() throws InvalidMap{
        d_map.addContinent("Asia", 10);
        d_map.addCountry("India", "Asia");
        d_map.addCountry("China", "Asia");
        d_map.addCountry("Maldives", "Asia");
        d_map.addCountryNeighbour("India", "China");
        d_map.addCountryNeighbour("China", "India");
        d_map.addCountry("India", "Maldives");
        d_map.checkCountryConnectivity();
    }
}
