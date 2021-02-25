package Views;


import java.util.*;

import Constants.ApplicationConstants;
import Exceptions.InvalidMap;
import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Player;
import Models.Map;
import Utils.CommonUtil;
import org.davidmoten.text.utils.WordWrap;

/**
 * This is the MapView Class.
 */
public class MapView {
	List<Player> d_players;
	GameState d_gameState;
	Map d_map;
	List<Country> d_countries;
	List<Continent> d_continents;

	public static final String ANSI_RESET = "\u001B[0m";

	public MapView(GameState p_gameState){
		d_gameState = p_gameState;
		d_map = p_gameState.getD_map();
		d_map = p_gameState.getD_map();
		d_countries = d_map.getD_countries();
		d_continents = d_map.getD_continents();
	}

	public MapView(GameState p_gameState, List<Player> p_players){
		d_gameState = p_gameState;
		d_players = p_players;
		d_map = p_gameState.getD_map();
		d_countries = d_map.getD_countries();
		d_continents = d_map.getD_continents();
	}

	private String getColorizedString(String p_color, String p_s) {
		if(p_color == null) return p_s;

		return p_color + p_s + ANSI_RESET;
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
		String l_continentName = p_continentName+" ( "+ApplicationConstants.CONTROL_VALUE+" : "+ d_gameState.getD_map().getContinent(p_continentName).getD_continentValue()+" )";

		renderSeparator();
		if(d_players != null){
			l_continentName = getColorizedString(getContinentColor(p_continentName), l_continentName);
		}
		renderCenteredString(ApplicationConstants.CONSOLE_WIDTH, l_continentName);
		renderSeparator();
	}

	private String getFormattedCountryName(int p_index, String p_countryName){
		String l_indexedString = String.format("%02d. %s", p_index, p_countryName);

		if(d_players != null){
			String l_armies = "( "+ApplicationConstants.ARMIES+" : "+ getCountryArmies(p_countryName)+" )";
			l_indexedString = String.format("%02d. %s %s", p_index, p_countryName, l_armies);
		}
		return getColorizedString(getCountryColor(p_countryName), String.format("%-30s", l_indexedString));
	}

	private void renderFormattedAdjacentCountryName(String p_countryName, List<Country> p_adjCountries){
		StringBuilder l_commaSeparatedCountries = new StringBuilder();

		for(Country c: p_adjCountries) {
			l_commaSeparatedCountries.append(c.getD_countryName()).append(", ");
		}
		String l_adjacentCountry = ApplicationConstants.CONNECTIVITY+" : "+ WordWrap.from(l_commaSeparatedCountries.toString()).maxWidth(ApplicationConstants.CONSOLE_WIDTH).wrap();
		System.out.println(getColorizedString(getCountryColor(p_countryName),l_adjacentCountry));
		System.out.println();
	}

	public String getCountryColor(String p_countryName){
		if(getCountryOwner(p_countryName) != null){
			return getCountryOwner(p_countryName).getD_color();
		}else{
			return null;
		}
	}

	public String getContinentColor(String p_continentName){
		if(getContinentOwner(p_continentName) != null){
			return getContinentOwner(p_continentName).getD_color();
		}else{
			return null;
		}
	}

	public Player getCountryOwner(String p_countryName){
		if (d_players != null) {
			for (Player p: d_players){
				if(p.getCountryNames().contains(p_countryName)){
					return p;
				}
			}
		}
		return null;
	}

	public void renderPlayerInfo(Player p_player){
		System.out.println(p_player.getPlayerName()+ " -> "+ getColorizedString(p_player.getD_color(), " COLOR "));
	}

	public void renderPlayers(){
		renderSeparator();
		renderCenteredString(ApplicationConstants.CONSOLE_WIDTH, "GAME PLAYERS");
		renderSeparator();

		for(Player p: d_players){
			renderPlayerInfo(p);
		}
	}

	public Player getContinentOwner(String p_continentName){
		if (d_players != null) {
			for (Player p: d_players){
				if(!CommonUtil.isNull(p.getContinentNames()) && p.getContinentNames().contains(p_continentName)){
					return p;
				}
			}
		}
		return null;
	}

	public Integer getCountryArmies(String p_countryName){
		Integer l_armies = d_gameState.getD_map().getCountryByName(p_countryName).getD_armies();

		if(l_armies == null)
			return 0;
		return l_armies;
	}

	/**
	 * This method displays the list of continents and countries present in the .map file.
	 */
	public void showMap() {

		if(d_players != null){
			renderPlayers();
		}
		d_continents.forEach(l_continent -> {
			renderContinentName(l_continent.getD_continentName());

			List<Country> l_continentCountries = l_continent.getD_countries();
			final int[] l_countryIndex = {1};

			l_continentCountries.forEach((l_country) -> {
				String l_formattedCountryName = getFormattedCountryName(l_countryIndex[0]++, l_country.getD_countryName());
				System.out.println(l_formattedCountryName);
				try {
					List<Country> l_adjCountries = d_map.getAdjacentCountry(l_country);

					renderFormattedAdjacentCountryName(l_country.getD_countryName(), l_adjCountries);
				} catch (InvalidMap l_invalidMap) {
					System.out.println(l_invalidMap.getMessage());
				}
			});
		});
	}
}
