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
import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Map;

public class MapService {

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

	public List<String> loadFile(String p_loadFileName) {
		String l_absolutePath = new File("").getAbsolutePath();
		String l_filePath = l_absolutePath + File.separator + p_loadFileName + ApplicationConstants.MAPFILEEXTENSION;

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

	public void editMap(GameState p_gameState, String p_editFilePath) throws IOException {
		File l_fileToBeEdited = new File(p_editFilePath);
		if (l_fileToBeEdited.createNewFile()) {
			System.out.println("File has been created.");
		} else {
			System.out.println("File already exists.");
		}
		Map l_map = new Map();
		l_map.setD_mapFile(p_editFilePath);
		p_gameState.setD_map(l_map);
	}

	public void editContinent(GameState p_gameState, String p_argument, String p_operation) throws IOException {
		String l_filePath = p_gameState.getD_map().getD_mapFile();
		Map l_mapToBeUpdated = (null == p_gameState.getD_map().getD_continents()
				&& null == p_gameState.getD_map().getD_countries()) ? this.loadMap(p_gameState, l_filePath)
						: p_gameState.getD_map();
		List<Continent> l_updatedContinents = this.addRemoveContinents(l_mapToBeUpdated.getD_continents(), p_operation,
				p_argument);
		if (null != l_updatedContinents && !l_updatedContinents.isEmpty()) {
			l_mapToBeUpdated.setD_mapFile(l_filePath);
			l_mapToBeUpdated.setD_continents(l_updatedContinents);
			p_gameState.setD_map(l_mapToBeUpdated);
		}
	}

	private List<Continent> addRemoveContinents(List<Continent> p_continentData, String p_operation,
			String p_argument) {
		List<Continent> l_updatedContinents = new ArrayList<>();
		if (null != p_continentData && !p_continentData.isEmpty())
			l_updatedContinents.addAll(p_continentData);

		if (p_operation.equalsIgnoreCase("add")) {
			Continent l_existingContinent = l_updatedContinents.stream().filter(
					l_continet -> l_continet.getD_continentID().equals(Integer.parseInt(p_argument.split(" ")[0])))
					.findFirst().orElse(null);
			if (l_existingContinent == null) {
				Continent l_continentToBeAdded = new Continent();
				l_continentToBeAdded.setD_continentID(Integer.parseInt(p_argument.split(" ")[0]));
				l_continentToBeAdded.setD_continentName("Continent");
				l_continentToBeAdded.setD_continentValue(Integer.parseInt(p_argument.split(" ")[1]));
				l_updatedContinents.add(l_continentToBeAdded);
			} else {
				System.out.println("Continent with continent id : " + Integer.parseInt(p_argument.split(" ")[0])
						+ " already Exists. Changes are not made");
			}
		} else if (p_operation.equalsIgnoreCase("remove")) {
			Continent l_existingContinent = l_updatedContinents.stream().filter(
					l_continet -> l_continet.getD_continentID().equals(Integer.parseInt(p_argument.split(" ")[0])))
					.findFirst().orElse(null);
			if (null != l_existingContinent) {
				l_updatedContinents.remove(l_existingContinent);
			} else {
				System.out.println("Continent with continent id : " + Integer.parseInt(p_argument.split(" ")[0])
						+ " does not Exist. Changes are not made");
			}
		}
		return l_updatedContinents;
	}

	public boolean saveMap(GameState p_gameState, String p_filePath) {
		try {
			if (!p_filePath.equalsIgnoreCase(p_gameState.getD_map().getD_mapFile())) {
				p_gameState.setError("Kindly provide same file name to save which you have given for edit");
				return false;
			} else {
				if (null != p_gameState.getD_map()) {

					Files.deleteIfExists(Paths.get(p_filePath));

					FileWriter l_writer = new FileWriter(p_filePath);

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
									l_bordersMetaData = l_bordersMetaData.concat(" ").concat(l_adjCountry.toString());
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

}
