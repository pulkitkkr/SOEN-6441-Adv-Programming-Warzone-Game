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

    Map l_map;
    MapService l_ms;
    GameState l_gameState;

    @Before
    public void beforeValidateTest(){
        l_map=new Map();
        l_gameState=new GameState();
        l_ms= new MapService();
    }

    @Test (expected = InvalidMap.class)
    public void validateNoContinent() throws InvalidMap{
        assertEquals(l_map.Validate(), false);
    }

    @Test (expected = InvalidMap.class)
    public void testValidate() throws InvalidMap {
        l_map= l_ms.loadMap(l_gameState, "canada.map");
        assertEquals(l_map.Validate(), true);
        l_map= l_ms.loadMap(l_gameState, "swiss.map");
        l_map.Validate();
    }

    @Test (expected = InvalidMap.class)
    public void validateNoCountry() throws InvalidMap{
        Continent l_continent = new Continent();
        List <Continent> l_continents = new ArrayList<Continent>();
        l_continents.add(l_continent);
        l_map.setD_continents(l_continents);
        l_map.Validate();
    }

    @Test (expected = InvalidMap.class)
    public void testCheckContinentConnectivity() throws  InvalidMap{
          l_map= l_ms.loadMap(l_gameState, "continentConnectivity.map");
          l_map.Validate();
    }

}
