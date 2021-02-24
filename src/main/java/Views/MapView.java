package Views;


import java.util.*;

import Constants.ApplicationConstants;
import Exceptions.InvalidMap;
import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Player;
import Models.Map;
import org.davidmoten.text.utils.WordWrap;

/**
 * This is the MapView Class.
 */
public class MapView {
	Player d_player;
	GameState d_gameState;
	Map d_map;
	List<Country> d_countries;
	List<Continent> d_continents;

	public MapView(GameState p_gameState){
		d_gameState = p_gameState;
		d_map = p_gameState.getD_map();
		d_map = p_gameState.getD_map();
		d_countries = d_map.getD_countries();
		d_continents = d_map.getD_continents();
	}

	public MapView(GameState p_gameState, Player p_player){
		d_gameState = p_gameState;
		d_player = p_player;
		d_map = p_gameState.getD_map();
		d_countries = d_map.getD_countries();
		d_continents = d_map.getD_continents();
	}

	private boolean isPlayerView(){
		return d_player != null;
	}

	private void renderCenteredString (int p_width, String p_s) {
		String l_centeredString = String.format("%-" + p_width  + "s", String.format("%" + (p_s.length() + (p_width - p_s.length()) / 2) + "s", p_s));

		System.out.format(l_centeredString+"\n");
	}

	private void renderSeparator(){
		StringBuilder l_separator = new StringBuilder();

		for (int i = 0; i< ApplicationConstants.CONSOLE_WIDTH -2; i++){
			l_separator.append("-");
		}
		System.out.format("+%s+%n", l_separator.toString());
	}

	private void renderContinentName(String p_continentName){
		renderSeparator();
		renderCenteredString(ApplicationConstants.CONSOLE_WIDTH, p_continentName);
		renderSeparator();
	}

	private String getFormattedCountryName(int p_index, String p_countryName){
		String l_indexedString = String.format("%02d. %s", p_index, p_countryName);

		return String.format("%-30s", l_indexedString);
	}

	private void renderFormattedAdjacentCountryName(List<Country> p_adjCountries){
		StringBuilder l_commaSeparatedCountries = new StringBuilder();

		for(Country c: p_adjCountries) {
			l_commaSeparatedCountries.append(c.getD_countryName()).append(", ");
		}
		System.out.println(WordWrap.from(l_commaSeparatedCountries.toString()).maxWidth(ApplicationConstants.CONSOLE_WIDTH).wrap());
		System.out.println();
	}

	/**
	 * This method displays the list of continents and countries present in the .map file.
	 */
	public void showMap() {
		boolean l_isPlayerView = isPlayerView();

		d_continents.forEach(l_continent -> {
			renderContinentName(l_continent.getD_continentName());

			List<Country> l_continentCountries = l_continent.getD_countries();
			final int[] l_countryIndex = {1};

			l_continentCountries.forEach((l_country) -> {
				String l_formattedCountryName = getFormattedCountryName(l_countryIndex[0]++, l_country.getD_countryName());
				System.out.println(l_formattedCountryName);
				try {
					List<Country> l_adjCountries = d_map.getAdjacentCountry(l_country);

					renderFormattedAdjacentCountryName(l_adjCountries);
				} catch (InvalidMap l_invalidMap) {
					System.out.println(l_invalidMap.getMessage());
				}
			});
		});
	}
}
