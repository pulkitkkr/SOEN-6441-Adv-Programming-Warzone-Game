package Services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Constants.ApplicationConstants;
import Models.Continent;
import Models.Country;
import Models.GameState;

/**
 * Writer class to create generic map file.
 *
 */
public class MapFileWriter implements Serializable {
	/**
	 * Reads map, parses it and stores it in specific type of map file.
	 * 
	 * @param p_gameState current state of the game
	 * @param l_writer    file writer
	 * @param l_mapFormat format in which map file has to be saved
	 * @throws IOException
	 */
	public void parseMapToFile(GameState p_gameState, FileWriter l_writer, String l_mapFormat) throws IOException {
		if (null != p_gameState.getD_map().getD_continents()
				&& !p_gameState.getD_map().getD_continents().isEmpty()) {
			writeContinentMetadata(p_gameState, l_writer);
		}
		if (null != p_gameState.getD_map().getD_countries()
				&& !p_gameState.getD_map().getD_countries().isEmpty()) {
			writeCountryAndBoarderMetaData(p_gameState, l_writer);
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
}
