package Views;

import java.util.*;
import Constants.ApplicationConstants;
import Exceptions.InvalidMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
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

    /**
     * Reset Color ANSI Code.
     */
	public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Constructor to initialise MapView.
     *
     * @param p_gameState Current GameState.
     */
	public MapView(GameState p_gameState){
		d_gameState = p_gameState;
		d_map = p_gameState.getD_map();
		d_map = p_gameState.getD_map();
		d_countries = d_map.getD_countries();
		d_continents = d_map.getD_continents();
	}

    /**
     * Constructor to initialise MapView with Players.
     *
     * @param p_gameState Current GameState
     * @param p_players List of Player Objects
     */
	public MapView(GameState p_gameState, List<Player> p_players){
		d_gameState = p_gameState;
		d_players = p_players;
		d_map = p_gameState.getD_map();
		d_countries = d_map.getD_countries();
		d_continents = d_map.getD_continents();
	}

    /**
     * Returns the Colored String.
     *
     * @param p_color Color to be changed to.
     * @param p_s String to be changed color of.
     * @return colored string.
     */
	private String getColorizedString(String p_color, String p_s) {
		if(p_color == null) return p_s;

		return p_color + p_s + ANSI_RESET;
	}

    /**
     * Renders the Center String for Heading.
     *
     * @param p_width Defined width in formatting.
     * @param p_s String to be rendered.
     */
	private void renderCenteredString (int p_width, String p_s) {
		String l_centeredString = String.format("%-" + p_width  + "s", String.format("%" + (p_s.length() + (p_width - p_s.length()) / 2) + "s", p_s));

		System.out.format(l_centeredString+"\n");
	}

    /**
     * Renders the Separator for heading.
     *
     */
	private void renderSeparator(){
		StringBuilder l_separator = new StringBuilder();

		for (int i = 0; i< ApplicationConstants.CONSOLE_WIDTH -2; i++){
			l_separator.append("-");
		}
		System.out.format("+%s+%n", l_separator.toString());
	}

    /**
     * Renders the continent Name with formatted centered string and separator.
     *
     * @param p_continentName Continent Name to be rendered.
     */
	private void renderContinentName(String p_continentName){
		String l_continentName = p_continentName+" ( "+ApplicationConstants.CONTROL_VALUE+" : "+ d_gameState.getD_map().getContinent(p_continentName).getD_continentValue()+" )";

		renderSeparator();
		if(d_players != null){
			l_continentName = getColorizedString(getContinentColor(p_continentName), l_continentName);
		}
		renderCenteredString(ApplicationConstants.CONSOLE_WIDTH, l_continentName);
		renderSeparator();
	}

    /**
     * Renders the Country Name as Formatted.
     *
     * @param p_index Index of Countries.
     * @param p_countryName Country Name to be rendered.
     * @return Returns the Formatted String
     */
	private String getFormattedCountryName(int p_index, String p_countryName){
		String l_indexedString = String.format("%02d. %s", p_index, p_countryName);

		if(d_players != null){
			String l_armies = "( "+ApplicationConstants.ARMIES+" : "+ getCountryArmies(p_countryName)+" )";
			l_indexedString = String.format("%02d. %s %s", p_index, p_countryName, l_armies);
		}
		return getColorizedString(getCountryColor(p_countryName), String.format("%-30s", l_indexedString));
	}

    /**
     * Renders Adjacent Countries in Formatted Settings.
     *
     * @param p_countryName Country Name to be rendered.
     * @param p_adjCountries List of adjacent countries to be rendered.
     */
	private void renderFormattedAdjacentCountryName(String p_countryName, List<Country> p_adjCountries){
		StringBuilder l_commaSeparatedCountries = new StringBuilder();

		for(int i=0; i<p_adjCountries.size(); i++) {
			l_commaSeparatedCountries.append(p_adjCountries.get(i).getD_countryName());
			if(i<p_adjCountries.size()-1)
				l_commaSeparatedCountries.append(", ");
		}
		String l_adjacentCountry = ApplicationConstants.CONNECTIVITY+" : "+ WordWrap.from(l_commaSeparatedCountries.toString()).maxWidth(ApplicationConstants.CONSOLE_WIDTH).wrap();
		System.out.println(getColorizedString(getCountryColor(p_countryName),l_adjacentCountry));
		System.out.println();
	}

    /**
     * Gets the Color of Country based on Player.
     *
     * @param p_countryName Country Name to be rendered.
     * @return Color of Country.
     */
	private String getCountryColor(String p_countryName){
		if(getCountryOwner(p_countryName) != null){
			return getCountryOwner(p_countryName).getD_color();
		}else{
			return null;
		}
	}

    /**
     * Gets the Color of continent based on Player.
     *
     * @param p_continentName Continent Name to be rendered.
     * @return Color of continent.
     */
	private String getContinentColor(String p_continentName){
		if(getContinentOwner(p_continentName) != null){
			return getContinentOwner(p_continentName).getD_color();
		}else{
			return null;
		}
	}

    /**
     * Gets the player who owns the country.
     *
     * @param p_countryName Name of country
     * @return the player object
     */
	private Player getCountryOwner(String p_countryName){
		if (d_players != null) {
			for (Player p: d_players){
				if(p.getCountryNames().contains(p_countryName)){
					return p;
				}
			}
		}
		return null;
	}

    /**
     * Renders the Player Information in formatted settings.
     *
     * @param p_index Index of the Player
     * @param p_player Player Object
     */
	private void renderPlayerInfo(Integer p_index, Player p_player){
		String l_playerInfo = String.format("%02d. %-8s %s", p_index,p_player.getPlayerName(), " -> "+ getColorizedString(p_player.getD_color(), " COLOR "));
		System.out.println(l_playerInfo);
	}

    /**
     * Renders the Players in Formatted Settings.
     *
     */
	private void renderPlayers(){
		int l_counter = 0;

		renderSeparator();
		renderCenteredString(ApplicationConstants.CONSOLE_WIDTH, "GAME PLAYERS");
		renderSeparator();

		for(Player p: d_players){
			l_counter++;
			renderPlayerInfo(l_counter, p);
		}
	}

    /**
     * Gets the continent owner.
     *
     * @param p_continentName continent name
     * @return player object
     */
	private Player getContinentOwner(String p_continentName){
		if (d_players != null) {
			for (Player p: d_players){
				if(!CommonUtil.isNull(p.getContinentNames()) && p.getContinentNames().contains(p_continentName)){
					return p;
				}
			}
		}
		return null;
	}

    /**
     * Gets the number of armies for a country.
     *
     * @param p_countryName name of the country
     * @return number of armies
     */
	private Integer getCountryArmies(String p_countryName){
		Integer l_armies = d_gameState.getD_map().getCountryByName(p_countryName).getD_armies();

		if(l_armies == null)
			return 0;
		return l_armies;
	}

	/**
	 * This method displays the list of continents and countries present in the .map files alongside current state of the game.
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
