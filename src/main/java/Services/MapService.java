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
import Utils.CommonUtil;

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
			List<String> l_continentData = getMetaData(l_linesOfFile, "continent");
			List<Continent> l_continentObjects = parseContinentsMetaData(l_continentData);
			List<String> l_countryData = getMetaData(l_linesOfFile, "country");
			List<String> l_bordersMetaData = getMetaData(l_linesOfFile, "border");
			List<Country> l_countryObjects = parseCountriesMetaData(l_countryData);
			l_countryObjects=parseBorderMetaData(l_countryObjects,l_bordersMetaData);
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

		String l_filePath = CommonUtil.getMapFilePath(p_loadFileName);
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
	 * Returns the corresponding map file lines
	 * @param p_fileLines All Lines in the map document
	 * @param p_switchParameter Type of lines needed : country, continent, borders
	 * @return required set of lines
	 */
	public List<String> getMetaData(List<String> p_fileLines, String p_switchParameter){
		switch (p_switchParameter){
			case "continent":
				List<String> l_continentLines = p_fileLines.subList(p_fileLines.indexOf(ApplicationConstants.CONTINENTS) + 1, p_fileLines.indexOf(ApplicationConstants.COUNTRIES) - 1);
				return l_continentLines;
			case "country":
				List<String> l_countryLines = p_fileLines.subList(p_fileLines.indexOf(ApplicationConstants.COUNTRIES) + 1, p_fileLines.indexOf(ApplicationConstants.BORDERS) - 1);
				return l_countryLines;
			case "border":
				List<String> l_bordersLines = p_fileLines.subList(p_fileLines.indexOf(ApplicationConstants.BORDERS) + 1, p_fileLines.size());
				return l_bordersLines;
			default:
				return null;
		}
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
	 * @return list of processed country meta data.
	 */
	public List<Country> parseCountriesMetaData(List<String> p_countriesList) {

		LinkedHashMap<Integer, List<Integer>> l_countryNeighbors = new LinkedHashMap<Integer, List<Integer>>();
		List<Country> l_countriesList = new ArrayList<Country>();

		for (String country : p_countriesList) {
			String[] l_metaDataCountries = country.split(" ");
			l_countriesList.add(new Country(Integer.parseInt(l_metaDataCountries[0]), l_metaDataCountries[1],
					Integer.parseInt(l_metaDataCountries[2])));
		}
		return l_countriesList;
	}

	/**
	 * Links the Country Objects to their respective neighbours
	 * @param p_countriesList Total Country Objects Initialized
	 * @param p_bordersList Border Data Lines
	 * @return Updated Country Objects
	 */
	public List<Country> parseBorderMetaData(List<Country> p_countriesList, List<String> p_bordersList){
		LinkedHashMap<Integer, List<Integer>> l_countryNeighbors = new LinkedHashMap<Integer, List<Integer>>();
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
		for (Country c : p_countriesList) {
			List<Integer> l_adjacentCountries = l_countryNeighbors.get(c.getD_countryId());
			c.setD_adjacentCountryIds(l_adjacentCountries);
		}
		return p_countriesList;
	}

	/**
	 * Links countries to corresponding continents and sets them in object of
	 * content
	 * 
	 * @param p_countries Total Country Objects
	 * @param p_continents Total Continent Objects
	 * @return list of updated continents
	 */
	public List<Continent> linkCountryContinents(List<Country> p_countries, List<Continent> p_continents) {
		for (Country c : p_countries) {
			for (Continent cont : p_continents) {
				//System.out.println(cont.getD_continentID());
				if (cont.getD_continentID().equals(c.getD_continentId())) {
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
	 * @param p_gameState Current GameState
	 * @param p_editFilePath Path of the File to Edit
	 * @throws IOException handles I/O
	 */
	public void editMap(GameState p_gameState, String p_editFilePath) throws IOException {

		String l_filePath = CommonUtil.getMapFilePath(p_editFilePath);
		File l_fileToBeEdited = new File(l_filePath);
		if (l_fileToBeEdited.createNewFile()) {
			System.out.println("File has been created.");
			Map l_map = new Map();
			l_map.setD_mapFile(p_editFilePath);
			p_gameState.setD_map(l_map);
		} else {
			System.out.println("File already exists.");
			this.loadMap(p_gameState, p_editFilePath);
			if(null != p_gameState.getD_map())
				p_gameState.getD_map().setD_mapFile(p_editFilePath);
		}
	}

	/**
	 * Processing of Continents given in commands which are to be added or removed
	 * from selected map though editmap
	 * 
	 * @param p_gameState Current GameState
	 * @param p_argument Arguments pertaining to the operation
	 * @param p_operation Operation to be performed on continents
	 * @throws IOException handles I/0
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
	 * @param p_continentData List of Continents to Update
	 * @param p_operation Operation to perform on Continents
	 * @param p_argument Arguments pertaining to the operations
	 * @return List of updated continents
	 */
	public List<Continent> addRemoveContinents(List<Continent> p_continentData, String p_operation,
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
	 * Controls the Flow of Edit Country Command : Adds/removes a country
	 * @param p_gameState Current GameState
	 * @param p_operation Add/Remove operation to be performed
	 * @param p_argument Arguments for the pertaining command operation
	 */
	public void editCountry(GameState p_gameState, String p_operation, String p_argument){
		String l_mapFileName= p_gameState.getD_map().getD_mapFile();
		Map l_mapToBeUpdated = (CommonUtil.isNull(p_gameState.getD_map().getD_continents())
				&& CommonUtil.isNull(p_gameState.getD_map().getD_countries())) ? this.loadMap(p_gameState, l_mapFileName)
				: p_gameState.getD_map();
		if(!CommonUtil.isNull(l_mapToBeUpdated)) {
			Map l_updatedMap = addRemoveCountry(l_mapToBeUpdated, p_operation, p_argument);
			p_gameState.setD_map(l_updatedMap);
		}
	}

	/**
	 * Performs the add/remove operation on the countries in map
	 * @param p_mapToBeUpdated The Map to be updated
	 * @param p_operation Operation to be performed
	 * @param p_argument Arguments for the pertaining command operation
	 * @return Updated Map Object
	 */
	public Map addRemoveCountry(Map p_mapToBeUpdated, String p_operation, String p_argument){
		if (p_operation.equalsIgnoreCase("add")){
			p_mapToBeUpdated.addCountry(Integer.parseInt(p_argument.split(" ")[0]), Integer.parseInt(p_argument.split(" ")[1]));
		}else if(p_operation.equalsIgnoreCase("remove")){
			p_mapToBeUpdated.removeCountry(Integer.parseInt(p_argument.split(" ")[0]));
		}else{
			System.out.println("Couldn't Save your changes");
		}
		return p_mapToBeUpdated;
	}

	public Country removeCountryNeighbour(Integer p_countryIDToRemove, Country p_countryObject){
		for(Integer l_cid: p_countryObject.getD_adjacentCountryIds()){
			if(l_cid.equals(p_countryIDToRemove)){
				p_countryObject.removeNeighbour(p_countryIDToRemove);
			}
		}
		return p_countryObject;
	}

	public Country addCountryNeighbour(Integer p_countryIDToAdd, Country p_countryObject){
		if(!p_countryObject.getD_adjacentCountryIds().contains(p_countryIDToAdd)){
			p_countryObject.addNeighbour(p_countryIDToAdd);
		}
		else{
			System.out.println("Country ID "+ p_countryIDToAdd+" is already a neighbour for Country ID "+p_countryObject.getD_countryId());
		}
		return p_countryObject;
	}
	/**
	 * Parses the updated map to .map file and stores it at required location
	 * 
	 * @param p_gameState Current GameState
	 * @param p_fileName filename to save things in
	 * @return true/false based on successful save operation of map to file
	 * @throws InvalidMap handles InvalidMap
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
						Files.deleteIfExists(Paths.get(CommonUtil.getMapFilePath(p_fileName)));
						FileWriter l_writer = new FileWriter(CommonUtil.getMapFilePath(p_fileName));

						if (null != p_gameState.getD_map().getD_continents()
								&& !p_gameState.getD_map().getD_continents().isEmpty()) {
							writeContinentMetadata(p_gameState, l_writer);
						}
						if (null != p_gameState.getD_map().getD_countries()
								&& !p_gameState.getD_map().getD_countries().isEmpty()) {
							writeCountryAndBoarderMetaData(p_gameState, l_writer);
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

	/**
	 * Retrieves country and boarder data from game state and writes it to file
	 * writer
	 * 
	 * @param p_gameState Current GameState Object
	 * @param p_writer Writer object for file
	 * @throws IOException handles I/0
	 */
	private void writeCountryAndBoarderMetaData(GameState p_gameState, FileWriter p_writer) throws IOException {
		String l_countryMetaData = new String();
		String l_bordersMetaData = new String();
		List<String> l_bordersList = new ArrayList<>();
		p_writer.write(System.lineSeparator() + ApplicationConstants.COUNTRIES + System.lineSeparator());
		for (Country l_country : p_gameState.getD_map().getD_countries()) {
			l_countryMetaData = new String();
			l_countryMetaData = l_country.getD_countryId().toString().concat(" ").concat(l_country.getD_countryName())
					.concat(" ").concat(l_country.getD_continentId().toString());
			p_writer.write(l_countryMetaData + System.lineSeparator());

			if (null != l_country.getD_adjacentCountryIds() && !l_country.getD_adjacentCountryIds().isEmpty()) {
				l_bordersMetaData = new String();
				l_bordersMetaData = l_country.getD_countryId().toString();
				for (Integer l_adjCountry : l_country.getD_adjacentCountryIds()) {
					l_bordersMetaData = l_bordersMetaData.concat(" ").concat(l_adjCountry.toString());
				}
				l_bordersList.add(l_bordersMetaData);
			}
		}
		if (null != l_bordersList && !l_bordersList.isEmpty()) {
			p_writer.write(System.lineSeparator() + ApplicationConstants.BORDERS + System.lineSeparator());
			for (String l_borderStr : l_bordersList) {
				p_writer.write(l_borderStr + System.lineSeparator());
			}
		}
	}

	/**
	 * Retrieves continents' data from game state and writes it to file w
	 * 
	 * @param p_gameState Current GameState
	 * @param p_writer Writer Object for file
	 * @throws IOException handles I/O
	 */
	private void writeContinentMetadata(GameState p_gameState, FileWriter p_writer) throws IOException {
		p_writer.write(System.lineSeparator() + ApplicationConstants.CONTINENTS + System.lineSeparator());
		for (Continent l_continent : p_gameState.getD_map().getD_continents()) {
			p_writer.write(
					l_continent.getD_continentName().concat(" ").concat(l_continent.getD_continentValue().toString())
							+ System.lineSeparator());
		}
	}
}
