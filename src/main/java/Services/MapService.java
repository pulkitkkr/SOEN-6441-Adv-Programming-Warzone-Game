package Services;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static Constants.ApplicationConstants.CONTINENTS;
import static Constants.ApplicationConstants.COUNTRIES;
import static Constants.ApplicationConstants.BORDERS;

public class MapService {

	HashMap<Integer, Integer> countrydata = new HashMap<Integer, Integer>();

	public void loadFile(String p_loadFilePath) {
		p_loadFilePath = "/Users/Avneetpannu/Documents/Concordia/SOEN 6441/CheckingFunctionality/MapFiles/canada.map";
		File file = new File(p_loadFilePath);
		List<String> list = new ArrayList<>();

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			list = br.lines().collect(Collectors.toList());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		extractDataFromFile(list);
	}

	public static void extractDataFromFile(List<String> list) {
		List<String> l_continentData = list.subList(list.indexOf(CONTINENTS) + 1, list.indexOf(COUNTRIES) - 1);
		parseContinentsMetaData(l_continentData);

		List<String> l_countryData = list.subList(list.indexOf(COUNTRIES) + 1, list.indexOf(BORDERS) - 1);
		parseCountriesMetaData(l_countryData);

		List<String> l_bordersMetaData = list.subList(list.indexOf(BORDERS) + 1, list.size());
		parseNeighborsMetaData(l_bordersMetaData);

	}

	static void parseContinentsMetaData(List<String> continentList) {
		LinkedHashMap<String, Integer> continentdata = new LinkedHashMap<String, Integer>();

		for (String cont : continentList) {
			String[] metaData = cont.split(" ");
			continentdata.put(metaData[0], Integer.parseInt(metaData[1]));
		}

//		continentdata.entrySet().forEach(entry -> {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		});

	}

	static void parseCountriesMetaData(List<String> countriesList) {

		LinkedHashMap<Integer, Integer> countryData = new LinkedHashMap<Integer, Integer>();

		for (String country : countriesList) {
			String[] metaDataCountries = country.split(" ");
			countryData.put(Integer.parseInt(metaDataCountries[0]), Integer.parseInt(metaDataCountries[2]));
		}

//		countryData.entrySet().forEach(entry -> {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		});

	}

	static void parseNeighborsMetaData(List<String> borders) {
		LinkedHashMap<Integer, List<Integer>> neighbors = new LinkedHashMap<Integer, List<Integer>>();
		ArrayList<Integer> n = new ArrayList<Integer>();
		// TO-DO
	}

}
