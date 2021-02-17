package Services;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import Constants.ApplicationConstants;
import Exceptions.InvalidMap;
import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Map;

/**
 * The MapService class load, read, parse, edit, and save map file.
 */
public class MapService {

	/**
	 * The loadmap method process map file.
	 * 
	 * @param p_gameState    current state of game.
	 * @param p_loadFileName map file name.
	 * @return map object after processing map file.
	 */
	public Map loadMap(GameState p_gameState, String p_loadFileName) {
		Map l_map = new Map();
		List<String> l_linesOfFile = loadFile(p_loadFileName);

		if (null != l_linesOfFile && !l_linesOfFile.isEmpty()) {
			List<String> l_continentData = l_linesOfFile.subList(
					l_linesOfFile.indexOf(ApplicationConstants.CONTINENTS) + 1,
					l_linesOfFile.indexOf(ApplicationConstants.COUNTRIES) - 1);
			List<Continent> l_continentObjects = parseContinentsMetaData(l_continentData);

			List<String> l_countryData = l_linesOfFile.subList(
					l_linesOfFile.indexOf(ApplicationConstants.COUNTRIES) + 1,
					l_linesOfFile.indexOf(ApplicationConstants.BORDERS) - 1);
			List<String> l_bordersMetaData = l_linesOfFile
					.subList(l_linesOfFile.indexOf(ApplicationConstants.BORDERS) + 1, l_linesOfFile.size());
			List<Country> l_countryObjects = parseCountriesMetaData(l_countryData, l_bordersMetaData);
			l_continentObjects = linkCountryContinents(l_countryObjects, l_continentObjects);
			l_map.setD_continents(l_continentObjects);
			l_map.setD_countries(l_countryObjects);
			p_gameState.setD_map(l_map);
		}
		return l_map;
	}

	/**
	 * The loadFile method load and read map file.
	 * 
	 * @param p_loadFileName map file name to load.
	 * @return list of lines from map file.
	 */
	public List<String> loadFile(String p_loadFileName) {

		String l_filePath = this.getMapFilePath(p_loadFileName);
		List<String> l_lineList = new ArrayList<>();

		BufferedReader l_reader;
		try {
			l_reader = new BufferedReader(new FileReader(l_filePath));
			l_lineList = l_reader.lines().collect(Collectors.toList());
			l_reader.close();
		} catch (FileNotFoundException l_e1) {
			l_e1.printStackTrace();
		} catch (IOException l_e2) {
			l_e2.printStackTrace();
		}
		return l_lineList;
	}

	/**
	 * The parseContinentsMetaData method parse extracted continent data of map
	 * file.
	 * 
	 * @param p_continentList includes continent data in list from map file.
	 * @return return list of processed continent meta data.
	 */
	public List<Continent> parseContinentsMetaData(List<String> p_continentList) {
		int l_continentId = 1;
		List<Continent> l_continents = new ArrayList<Continent>();
		for (String cont : p_continentList) {
			String[] l_metaData = cont.split(" ");
			l_continents.add(new Continent(l_continentId, l_metaData[0], Integer.parseInt(l_metaData[1])));
			l_continentId++;
		}
		return l_continents;
	}

	/**
	 * The parseCountriesMetaData method parse extracted country and border data of
	 * map file.
	 * 
	 * @param p_countriesList includes country data in list from map file.
	 * @param p_bordersList   includes border data in list from map file.
	 * @return list of processed country meta data.
	 */
	public List<Country> parseCountriesMetaData(List<String> p_countriesList, List<String> p_bordersList) {

		LinkedHashMap<Integer, List<Integer>> l_countryNeighbors = new LinkedHashMap<Integer, List<Integer>>();
		List<Country> l_countriesList = new ArrayList<Country>();

		for (String country : p_countriesList) {
			String[] l_metaDataCountries = country.split(" ");
			l_countriesList.add(new Country(Integer.parseInt(l_metaDataCountries[0]), l_metaDataCountries[1],
					Integer.parseInt(l_metaDataCountries[2])));
		}
		for (String l_border : p_bordersList) {
			if (null != l_border && !l_border.isEmpty()) {
				ArrayList<Integer> l_neighbours = new ArrayList<Integer>();
				String[] l_splitString = l_border.split(" ");
				for (int i = 1; i <= l_splitString.length - 1; i++) {
					l_neighbours.add(Integer.parseInt(l_splitString[i]));

				}
				l_countryNeighbors.put(Integer.parseInt(l_splitString[0]), l_neighbours);
			}
		}
		for (Country c : l_countriesList) {
			List<Integer> l_adjacentCountries = l_countryNeighbors.get(c.getD_countryId());
			c.setD_adjacentCountryIds(l_adjacentCountries);
		}
		return l_countriesList;
	}

	/**
	 * Links countries to corresponding continents and sets them in object of
	 * content
	 * 
	 * @param p_countries
	 * @param p_continents
	 * @return list of updated continents
	 */
	public List<Continent> linkCountryContinents(List<Country> p_countries, List<Continent> p_continents) {
		for (Country c : p_countries) {
			System.out.println(c.getD_countryId());
			for (Continent cont : p_continents) {
				System.out.println(cont.getD_continentID());
				if (cont.getD_continentID().equals(c.getD_continentId())) {
					System.out.println("Matched");
					cont.addCountry(c);
				}
			}
		}
		return p_continents;
	}

	/**
	 * Method is responsible for creating a new map if map to be edited does not
	 * exists, and if it exists it parses the map file to game state object
	 * 
	 * @param p_gameState
	 * @param p_editFilePath
	 * @throws IOException
	 */
	public void editMap(GameState p_gameState, String p_editFilePath) throws IOException {

		String l_filePath = this.getMapFilePath(p_editFilePath);

		File l_fileToBeEdited = new File(l_filePath);
		if (l_fileToBeEdited.createNewFile()) {
			System.out.println("File has been created.");
			Map l_map = new Map();
			l_map.setD_mapFile(p_editFilePath);
			p_gameState.setD_map(l_map);
		} else {
			System.out.println("File already exists.");
			this.loadMap(p_gameState, p_editFilePath);
			p_gameState.getD_map().setD_mapFile(p_editFilePath);
		}
	}

	/**
	 * Processing of Continents given in commands which are to be added or removed
	 * from selected map though editmapF
	 * 
	 * @param p_gameState
	 * @param p_argument
	 * @param p_operation
	 * @throws IOException
	 */
	public void editContinent(GameState p_gameState, String p_argument, String p_operation) throws IOException {
		String l_mapFileName = p_gameState.getD_map().getD_mapFile();
		Map l_mapToBeUpdated = (null == p_gameState.getD_map().getD_continents()
				&& null == p_gameState.getD_map().getD_countries()) ? this.loadMap(p_gameState, l_mapFileName)
						: p_gameState.getD_map();
		List<Continent> l_updatedContinents = this.addRemoveContinents(l_mapToBeUpdated.getD_continents(), p_operation,
				p_argument);
		if (null != l_updatedContinents && !l_updatedContinents.isEmpty()) {
			l_mapToBeUpdated.setD_mapFile(l_mapFileName);
			l_mapToBeUpdated.setD_continents(l_updatedContinents);
			p_gameState.setD_map(l_mapToBeUpdated);
		}
	}

	/**
	 * Constructs updated Continents list based on passed operations - Add/Remove
	 * and Arguments
	 * 
	 * @param p_continentData
	 * @param p_operation
	 * @param p_argument
	 * @return List of updated continents
	 */
	private List<Continent> addRemoveContinents(List<Continent> p_continentData, String p_operation,
			String p_argument) {
		List<Continent> l_updatedContinents = new ArrayList<>();
		if (null != p_continentData && !p_continentData.isEmpty())
			l_updatedContinents.addAll(p_continentData);

		if (p_operation.equalsIgnoreCase("add")) {
			Continent l_existingContinent = l_updatedContinents.stream()
					.filter(l_continent -> l_continent.getD_continentName().equals(p_argument.split(" ")[0]))
					.findFirst().orElse(null);
			if (l_existingContinent == null) {
				Continent l_continentToBeAdded = new Continent(l_updatedContinents.size() + 1, p_argument.split(" ")[0],
						Integer.parseInt(p_argument.split(" ")[1]));
				l_updatedContinents.add(l_continentToBeAdded);
			} else {
				System.out.println("Continent with continent name : " + p_argument.split(" ")[0]
						+ " already Exists. Changes are not made");
			}
		} else if (p_operation.equalsIgnoreCase("remove")) {
			Continent l_existingContinent = l_updatedContinents.stream()
					.filter(l_continent -> l_continent.getD_continentName().equals(p_argument.split(" ")[0]))
					.findFirst().orElse(null);
			if (null != l_existingContinent) {
				l_updatedContinents.remove(l_existingContinent);
			} else {
				System.out.println("Continent with continent name : " + Integer.parseInt(p_argument.split(" ")[0])
						+ " does not Exist. Changes are not made");
			}
		}
		return l_updatedContinents;
	}

	/**
	 * Parses the updated map to .map file and stores it at required location
	 * 
	 * @param p_gameState
	 * @param p_filePath
	 * @return true/false based on successful save operation of map to file
	 * @throws InvalidMap
	 */
	public boolean saveMap(GameState p_gameState, String p_fileName) throws InvalidMap {
		try {
			if (!p_fileName.equalsIgnoreCase(p_gameState.getD_map().getD_mapFile())) {
				p_gameState.setError("Kindly provide same file name to save which you have given for edit");
				return false;
			} else {
				if (null != p_gameState.getD_map()) {

					Models.Map l_currentMap = p_gameState.getD_map();
					boolean l_mapValidationStatus = l_currentMap.Validate();
					if (l_mapValidationStatus) {
						Files.deleteIfExists(Paths.get(this.getMapFilePath(p_fileName)));

						FileWriter l_writer = new FileWriter(this.getMapFilePath(p_fileName));

						String l_countryMetaData = new String();
						String l_bordersMetaData = new String();
						List<String> l_bordersList = new ArrayList<>();
						if (null != p_gameState.getD_map().getD_continents()
								&& !p_gameState.getD_map().getD_continents().isEmpty()) {
							l_writer.write(
									System.lineSeparator() + ApplicationConstants.CONTINENTS + System.lineSeparator());
							for (Continent l_continent : p_gameState.getD_map().getD_continents()) {
								l_writer.write(l_continent.getD_continentName().concat(" ")
										.concat(l_continent.getD_continentValue().toString()) + System.lineSeparator());
							}
						}
						if (null != p_gameState.getD_map().getD_countries()
								&& !p_gameState.getD_map().getD_countries().isEmpty()) {

							l_writer.write(
									System.lineSeparator() + ApplicationConstants.COUNTRIES + System.lineSeparator());
							for (Country l_country : p_gameState.getD_map().getD_countries()) {
								l_countryMetaData = new String();
								l_countryMetaData = l_country.getD_countryId().toString().concat(" ")
										.concat(l_country.getD_countryName()).concat(" ")
										.concat(l_country.getD_continentId().toString());
								l_writer.write(l_countryMetaData + System.lineSeparator());

								if (null != l_country.getD_adjacentCountryIds()
										&& !l_country.getD_adjacentCountryIds().isEmpty()) {
									l_bordersMetaData = new String();
									l_bordersMetaData = l_country.getD_countryId().toString();
									for (Integer l_adjCountry : l_country.getD_adjacentCountryIds()) {
										l_bordersMetaData = l_bordersMetaData.concat(" ")
												.concat(l_adjCountry.toString());
									}
									l_bordersList.add(l_bordersMetaData);
								}
							}
							if (null != l_bordersList && !l_bordersList.isEmpty()) {
								l_writer.write(
										System.lineSeparator() + ApplicationConstants.BORDERS + System.lineSeparator());
								for (String l_borderStr : l_bordersList) {
									l_writer.write(l_borderStr + System.lineSeparator());
								}
							}
						}

						l_writer.close();
					}
				} else {
					p_gameState.setError("Validation Failed");
					return false;
				}
			}
			return true;
		} catch (IOException l_e) {
			l_e.printStackTrace();
			p_gameState.setError("Error in saving map file");
			return false;
		}
	}

//	public static void main(String[] p_args) throws InvalidMap {
//		MapService l_ms = new MapService();
//		GameState l_gameState = new GameState();
//		Map l_map = l_ms.loadMap(l_gameState, "C:/Users/ishaa/Downloads/europe/europe.map");
//		l_map.checkContinents();
//		l_map.checkCountries();
//		System.out.println(l_map.Validate());
//	}
	private String getMapFilePath(String p_fileName) {
		String l_absolutePath = new File("").getAbsolutePath();
		// String l_filePath = l_absolutePath + File.separator + p_loadFileName +
		// ApplicationConstants.MAPFILEEXTENSION;
		return l_absolutePath + File.separator + ApplicationConstants.SRC_MAIN_RESOURCES + File.separator + p_fileName;
	}

}
