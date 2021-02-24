package Views;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Player;

/**
 * This is the MapView Class.
 */
public class MapView {
	
	/**
	 * This method displays the list of continents and countries present in the .map file.
	 * .
	 * @param p_gameState uses GameState model object
	 * @param p_continent uses Continent model object
	 * @param p_country uses Country model object
	 */
	public void showMap(GameState p_gameState, Continent p_continent, Country p_country) {
		
		//loadMap -> fetch all the existing countries and continents from the file and display it here using the iterator.
		
		//Iterator<List<Continent>> l_iteratorForContinents = p_gameState.getContinentList().iterator();	
		String l_continentTable = "|%-16d|%-18s|%-14d|%n";

		System.out.format("+----------------+------------------+------------------+%n");
		System.out.format("| Continent's ID | Continent's name |  Continent Value |%n");
		System.out.format("+----------------+------------------+------------------+%n");

//		while (l_iteratorForContinents.hasNext()) {
//			System.out.format(l_continentTable, p_continent.getD_continentID(), p_continent.getD_continentName(), p_continent.getD_continentValue());
//		}
		System.out.format("+----------------+------------------+------------------+%n");
		System.out.println();
		
		//Iterator<List<Continent>> l_iteratorForCountries = p_gameState.getCountriesList().iterator();	
		String l_countryTable = "|%-16d|%-18s|%-28d|%n";

		System.out.format("+----------------+------------------+----------------------------+%n");
		System.out.format("|  Country's ID  |  Country's name  |   Adjacent countries' ID   |%n");
		System.out.format("+----------------+------------------+----------------------------+%n");

//		while (l_iteratorForCountries.hasNext()) {
//			System.out.format(l_countryTable, p_country.getD_countryId(), p_country.getD_countryName(), p_country.getD_adjacentCountryIds());
//		}
		System.out.format("+----------------+------------------+----------------------------+%n");
	}
	
	/**
	 * This method displays the map from the Player's Perspective; which countries and continents the specified Player owns.
	 * 
	 * @param p_gameState uses GameState model object
	 * @param p_player uses Player model object
	 * @param p_continent uses Continent model object
	 * @param p_country uses Country model object
	 */
	public void showPlayerMap(GameState p_gameState, Player p_player, Continent p_continent, Country p_country) {
		String table = "|%-14d|%-16s|%-28d|%-18d|%-15s|%-15s|%n";

		System.out.format(
				"+--------------+----------------+----------------------------+------------------+---------------+---------------+%n");
		System.out.format(
				"| Country's ID | Country's name |   Adjacent countries' ID   | Continent's Name | No. of armies | Player's name |%n");
		System.out.format(
				"+--------------+----------------+----------------------------+------------------+---------------+---------------+%n");

		List<Country> playerCountries = p_player.getD_coutriesOwned();
		
		for (int i = 0; i < playerCountries.size(); i++) {
			System.out.format(table, p_country.getD_countryId(), p_country.getD_countryName(), p_player.getD_noOfUnallocatedArmies(), 
					p_continent.getD_continentName(), p_country.getD_adjacentCountryIds(), p_player.getD_playerName());
		}
		System.out.format(
				"+--------------+----------------+----------------------------+------------------+---------------+---------------+%n");
	}	
	
	/**
	 * main method is used here for testing.
	 * 
	 * @param args default arguments are not used
	 */
	public static void main(String[] args) {
		MapView l_mp = new MapView();
//		l_mp.showMap(null, null, null);
//		l_mp.showPlayerMap(null, null, null);
	}
}
