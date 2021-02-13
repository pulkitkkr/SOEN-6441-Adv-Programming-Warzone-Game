package Services;

import Models.Continent;
import Models.Map;
import Models.Country;
import Exceptions.InvalidMap;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


import static Constants.ApplicationConstants.CONTINENTS;
import static Constants.ApplicationConstants.COUNTRIES;
import static Constants.ApplicationConstants.BORDERS;

public class MapService {

	Map m;
	public Map constructMap(String p_loadFilePath) {
		m= new Map();
		List<String> l_listString = loadFile(p_loadFilePath);
		List<String> l_continentData = l_listString.subList(l_listString.indexOf(CONTINENTS) + 1, l_listString.indexOf(COUNTRIES) - 1);
		List<Continent> l_continentObjects = parseContinentsMetaData(l_continentData);
		List<String> l_countryData = l_listString.subList(l_listString.indexOf(COUNTRIES) + 1, l_listString.indexOf(BORDERS) - 1);
		List<String> l_bordersMetaData = l_listString.subList(l_listString.indexOf(BORDERS) + 1, l_listString.size());
		List<Country> l_countryObjects = parseCountriesMetaData(l_countryData,l_bordersMetaData);
		l_continentObjects=linkCountryContinents(l_countryObjects, l_continentObjects);
		m.setD_continents(l_continentObjects);
		m.setD_countries(l_countryObjects);
		return m;
	}
	public List<String> loadFile(String p_loadFilePath) {
		//p_loadFilePath = "C:/Users/ishaa/Downloads/europe/europe.map";
		File l_mapFile = new File(p_loadFilePath);
		List<String> l_lineList = new ArrayList<>();

		BufferedReader l_reader;
		try {
			l_reader = new BufferedReader(new FileReader(l_mapFile));
			l_lineList = l_reader.lines().collect(Collectors.toList());
		} catch (FileNotFoundException l_e) {
			l_e.printStackTrace();
		}
		//extractDataFromFile(list);
		return l_lineList;
	}
	public List<Continent> parseContinentsMetaData(List<String> p_continentList) {
		//LinkedHashMap<String, Integer> l_continentData = new LinkedHashMap<String, Integer>();
		List<Continent> l_continents = new ArrayList<Continent>();
		int count=1;
		for (String cont : p_continentList) {
			String[] l_metaData = cont.split(" ");
			//continentdata.put(l_metaData[0], Integer.parseInt(l_metaData[1]));
			l_continents.add(new Continent(count, l_metaData[0], Integer.parseInt(l_metaData[1])));
			count++;
		}
		return l_continents;
	}

	public List<Country> parseCountriesMetaData(List<String> p_countriesList, List<String> p_bordersList) {

		LinkedHashMap<Integer, List<Integer>> l_countryNeighbors = new LinkedHashMap<Integer, List<Integer>>();
		List<Country> l_countriesList = new ArrayList<Country>();

		for (String country : p_countriesList) {
			String[] l_metaDataCountries = country.split(" ");
			l_countriesList.add(new Country(Integer.parseInt(l_metaDataCountries[0]), Integer.parseInt(l_metaDataCountries[2])));
		}
		for(String b : p_bordersList) {
			ArrayList<Integer> l_neighbours = new ArrayList<Integer>();

			String[] l_splitString = b.split(" ");
			int l_countryId = Integer.parseInt(l_splitString[0]);
			for ( int i=1;i<=l_splitString.length-1;i++) {
				l_neighbours.add(Integer.parseInt(l_splitString[i]));

			}
			l_countryNeighbors.put(Integer.parseInt(l_splitString[0]), l_neighbours);
		}
		for(Country c: l_countriesList) {
			List<Integer> l_adjacentCountries = l_countryNeighbors.get(c.getD_countryId());
			c.setD_adjacentCountryIds(l_adjacentCountries);
		}
		return l_countriesList;
	}

	public List<Continent> linkCountryContinents(List<Country> p_countries, List<Continent> p_continents){
		for (Country c: p_countries){
			System.out.println(c.getD_countryId());
			for (Continent cont: p_continents){
				System.out.println(cont.getD_continentId());
				if (cont.getD_continentID().equals(c.getD_continentId())) {
					System.out.println("Matched");
					cont.addCountry(c);
				}
			}
		}
		return p_continents;
	}

	public static void main(String[] p_args) throws InvalidMap {
		MapService l_ms=new MapService();
		Map l_map= l_ms.constructMap("C:/Users/ishaa/Downloads/europe/europe.map");
		l_map.checkContinents();
		l_map.checkCountries();
		System.out.println(l_map.Validate());
	}

}
