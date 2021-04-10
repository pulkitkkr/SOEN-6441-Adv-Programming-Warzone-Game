package Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import Constants.ApplicationConstants;
import Exceptions.InvalidCommand;
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
	 * @param p_gameState current state of game.
	 * @param p_loadFileName map file name.
	 * @return Map object after processing map file.
	 * @throws InvalidMap indicates Map Object Validation failure
	 */
	public Map loadMap(GameState p_gameState, String p_loadFileName) throws InvalidMap {
		Map l_map = new Map();
		List<String> l_linesOfFile = loadFile(p_loadFileName);

		if (null != l_linesOfFile && !l_linesOfFile.isEmpty()) {
			if(l_linesOfFile.contains("[Territories]")) {
				MapReaderAdapter l_mapReaderAdapter = new MapReaderAdapter(new ConquestMapFileReader());
				l_mapReaderAdapter.parseMapFile(p_gameState, l_map, l_linesOfFile);
			} else if(l_linesOfFile.contains("[countries]")) {
				MapFileReader l_mapFileReader = new MapFileReader();
				l_mapFileReader.parseMapFile(p_gameState, l_map, l_linesOfFile);
			}
		}
		return l_map;
	}

	

	/**
	 * The loadFile method load and read map file.
	 * 
	 * @param p_loadFileName map file name to load.
	 * @return List of lines from map file.
	 * @throws InvalidMap indicates Map Object Validation failure
	 */
	public List<String> loadFile(String p_loadFileName) throws InvalidMap{

		String l_filePath = CommonUtil.getMapFilePath(p_loadFileName);
		List<String> l_lineList = new ArrayList<>();

		BufferedReader l_reader;
		try {
			l_reader = new BufferedReader(new FileReader(l_filePath));
			l_lineList = l_reader.lines().collect(Collectors.toList());
			l_reader.close();
		} catch (IOException l_e1) {
			throw new InvalidMap("Map File not Found!");
		}
		return l_lineList;
	}

	/**
	 * Method is responsible for creating a new map if map to be edited does not
	 * exists, and if it exists it parses the map file to game state object.
	 * 
	 * @param p_gameState GameState model class object
	 * @param p_editFilePath consists of base filepath
	 * @throws InvalidMap indicates Map Object Validation failure
	 * @throws IOException triggered in case the file does not exist or the file name is invalid
	 */
	public void editMap(GameState p_gameState, String p_editFilePath) throws IOException, InvalidMap {

		String l_filePath = CommonUtil.getMapFilePath(p_editFilePath);
		File l_fileToBeEdited = new File(l_filePath);

		if (l_fileToBeEdited.createNewFile()) {
			System.out.println("File has been created.");
			Map l_map = new Map();
			l_map.setD_mapFile(p_editFilePath);
			p_gameState.setD_map(l_map);
			p_gameState.updateLog(p_editFilePath+ " File has been created for user to edit", "effect");
		} else {
			System.out.println("File already exists.");
			this.loadMap(p_gameState, p_editFilePath);
			if (null == p_gameState.getD_map()) {
				p_gameState.setD_map(new Map());
			}
			p_gameState.getD_map().setD_mapFile(p_editFilePath);
			p_gameState.updateLog(p_editFilePath+ " already exists and is loaded for editing", "effect");
		}
	}

	/**
	 * Controls the Flow of Edit Operations: editcontinent, editcountry, editneighbor.
     *
	 * @param p_gameState Current GameState Object.
	 * @param p_argument Arguments for the pertaining command operation.
	 * @param p_operation Add/Remove operation to be performed.
	 * @param p_switchParameter Type of Edit Operation to be performed.
	 * @throws IOException Exception.
	 * @throws InvalidMap invalidmap exception.
	 * @throws InvalidCommand invalid command exception
	 */
	public void editFunctions(GameState p_gameState, String p_argument, String p_operation, Integer p_switchParameter) throws IOException, InvalidMap, InvalidCommand{
		Map l_updatedMap;
		String l_mapFileName = p_gameState.getD_map().getD_mapFile();
		Map l_mapToBeUpdated = (CommonUtil.isNull(p_gameState.getD_map().getD_continents()) && CommonUtil.isNull(p_gameState.getD_map().getD_countries())) ? this.loadMap(p_gameState, l_mapFileName) : p_gameState.getD_map();

		// Edit Control Logic for Continent, Country & Neighbor
		if(!CommonUtil.isNull(l_mapToBeUpdated)){
			switch(p_switchParameter){
				case 1:
					l_updatedMap = addRemoveContinents(p_gameState, l_mapToBeUpdated, p_operation, p_argument);
					break;
				case 2:
					l_updatedMap = addRemoveCountry(p_gameState, l_mapToBeUpdated, p_operation, p_argument);
					break;
				case 3:
					l_updatedMap = addRemoveNeighbour(p_gameState, l_mapToBeUpdated, p_operation, p_argument);
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + p_switchParameter);
			}
			p_gameState.setD_map(l_updatedMap);
			p_gameState.getD_map().setD_mapFile(l_mapFileName);
		}
	}

	/**
	 * Constructs updated Continents list based on passed operations - Add/Remove
	 * and Arguments.
	 *
	 * @param p_gameState Current GameState Object
	 * @param p_mapToBeUpdated Map Object to be Updated
	 * @param p_operation Operation to perform on Continents
	 * @param p_argument Arguments pertaining to the operations
	 * @return List of updated continents
	 * @throws InvalidMap invalidmap exception
	 */
	public Map addRemoveContinents(GameState p_gameState, Map p_mapToBeUpdated, String p_operation, String p_argument) throws InvalidMap {

		try {
			if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length==2) {
				p_mapToBeUpdated.addContinent(p_argument.split(" ")[0], Integer.parseInt(p_argument.split(" ")[1]));
				this.setD_MapServiceLog("Continent "+ p_argument.split(" ")[0]+ " added successfully!", p_gameState);
			} else if (p_operation.equalsIgnoreCase("remove") && p_argument.split(" ").length==1) {
				p_mapToBeUpdated.removeContinent(p_argument.split(" ")[0]);
				this.setD_MapServiceLog("Continent "+ p_argument.split(" ")[0]+ " removed successfully!", p_gameState);
			} else {
				throw new InvalidMap("Continent "+p_argument.split(" ")[0]+" couldn't be added/removed. Changes are not made due to Invalid Command Passed.");
			}
		} catch (InvalidMap | NumberFormatException l_e) {
			this.setD_MapServiceLog(l_e.getMessage(), p_gameState);
		}
		return p_mapToBeUpdated;
	}

	/**
	 * Performs the add/remove operation on the countries in map.
	 *
	 * @param p_gameState Current GameState Object
	 * @param p_mapToBeUpdated The Map to be updated
	 * @param p_operation Operation to be performed
	 * @param p_argument Arguments for the pertaining command operation
	 * @return Updated Map Object
	 * @throws InvalidMap invalidmap exception
	 */
	public Map addRemoveCountry(GameState p_gameState, Map p_mapToBeUpdated, String p_argument, String p_operation) throws InvalidMap{

		try {
			if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length==2){
				p_mapToBeUpdated.addCountry(p_argument.split(" ")[0], p_argument.split(" ")[1]);
				this.setD_MapServiceLog("Country "+ p_argument.split(" ")[0]+ " added successfully!", p_gameState);
			}else if(p_operation.equalsIgnoreCase("remove")&& p_argument.split(" ").length==1){
				p_mapToBeUpdated.removeCountry(p_argument.split(" ")[0]);
				this.setD_MapServiceLog("Country "+ p_argument.split(" ")[0]+ " removed successfully!", p_gameState);
			}else{
				throw new InvalidMap("Country "+p_argument.split(" ")[0]+" could not be "+ p_operation +"ed!");
			}
		} catch (InvalidMap l_e) {
			this.setD_MapServiceLog(l_e.getMessage(), p_gameState);
		}
		return p_mapToBeUpdated;
	}

	/**
	 * Performs the add/remove operation on Map Object.
	 *
	 * @param p_gameState Current GameState Object
	 * @param p_mapToBeUpdated The Map to be updated
	 * @param p_operation Add/Remove operation to be performed
	 * @param p_argument Arguments for the pertaining command operation
	 * @return map to be updated
	 * @throws InvalidMap invalidmap exception
	 */
	public Map addRemoveNeighbour(GameState p_gameState, Map p_mapToBeUpdated, String p_argument, String p_operation) throws InvalidMap{

		try {
			if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length==2){
				p_mapToBeUpdated.addCountryNeighbour(p_argument.split(" ")[0], p_argument.split(" ")[1]);
				this.setD_MapServiceLog("Neighbour Pair "+p_argument.split(" ")[0]+" "+p_argument.split(" ")[1]+" added successfully!", p_gameState);
			}else if(p_operation.equalsIgnoreCase("remove") && p_argument.split(" ").length==2){
				p_mapToBeUpdated.removeCountryNeighbour(p_argument.split(" ")[0], p_argument.split(" ")[1]);
				this.setD_MapServiceLog("Neighbour Pair "+p_argument.split(" ")[0]+" "+p_argument.split(" ")[1]+" removed successfully!", p_gameState);
			}else{
				throw new InvalidMap("Neighbour could not be "+ p_operation +"ed!");
			}
		} catch (InvalidMap l_e) {
			this.setD_MapServiceLog(l_e.getMessage(), p_gameState);
		}
		return p_mapToBeUpdated;
	}

	/**
	 * Parses the updated map to .map file and stores it at required location.
	 * 
	 * @param p_gameState Current GameState
	 * @param p_fileName filename to save things in
	 * @return true/false based on successful save operation of map to file
	 * @throws InvalidMap InvalidMap exception
	 */
	public boolean saveMap(GameState p_gameState, String p_fileName) throws InvalidMap {
		boolean l_flagValidate = false;
		try {

			// Verifies if the file linked to savemap and edited by user are same
			if (!p_fileName.equalsIgnoreCase(p_gameState.getD_map().getD_mapFile())) {
				p_gameState.setError("Kindly provide same file name to save which you have given for edit");
				return false;
			} else {
				if (null != p_gameState.getD_map()) {
					Models.Map l_currentMap = p_gameState.getD_map();

					// Proceeds to save the map if it passes the validation check
					this.setD_MapServiceLog("Validating Map......", p_gameState);
					//boolean l_mapValidationStatus = l_currentMap.Validate();
					if (l_currentMap.Validate()) {
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
						p_gameState.updateLog("Map Saved Successfully", "effect");
						l_writer.close();
					}
				} else {
					p_gameState.updateLog("Validation failed! Cannot Save the Map file!", "effect");
					p_gameState.setError("Validation Failed");
					return false;
				}
			}
			return true;
		} catch (IOException | InvalidMap l_e) {
			this.setD_MapServiceLog(l_e.getMessage(), p_gameState);
			p_gameState.updateLog("Couldn't save the changes in map file!", "effect");
			p_gameState.setError("Error in saving map file");
			return false;
		}
	}

	/**
	 * Retrieves country and boarder data from game state and writes it to file
	 * writer.
	 * 
	 * @param p_gameState Current GameState Object
	 * @param p_writer Writer object for file
	 * @throws IOException handles I/0
	 */
	private void writeCountryAndBoarderMetaData(GameState p_gameState, FileWriter p_writer) throws IOException {
		String l_countryMetaData = new String();
		String l_bordersMetaData = new String();
		List<String> l_bordersList = new ArrayList<>();

		// Writes Country Objects to File And Organizes Border Data for each of them
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

		// Writes Border data to the File
		if (null != l_bordersList && !l_bordersList.isEmpty()) {
			p_writer.write(System.lineSeparator() + ApplicationConstants.BORDERS + System.lineSeparator());
			for (String l_borderStr : l_bordersList) {
				p_writer.write(l_borderStr + System.lineSeparator());
			}
		}
	}

	/**
	 * Retrieves continents' data from game state and writes it to file.
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

	/**
	 * Resets Game State's Map.
	 *
	 * @param p_gameState object of GameState class
	 * @param p_fileToLoad File which couldn't be loaded
	 */
	public void resetMap(GameState p_gameState, String p_fileToLoad) {
		System.out.println("Map cannot be loaded, as it is invalid. Kindly provide valid map");
		p_gameState.updateLog(p_fileToLoad+" map could not be loaded as it is invalid!", "effect");
		p_gameState.setD_map(new Models.Map());
	}

	/**
	 * Set the log of map editor methods.
	 *
	 * @param p_MapServiceLog String containing log
	 * @param p_gameState current gamestate instance
	 */
	public void setD_MapServiceLog(String p_MapServiceLog, GameState p_gameState){
		System.out.println(p_MapServiceLog);
		p_gameState.updateLog(p_MapServiceLog, "effect");
	}
}
