package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Exceptions.InvalidMap;
import java.util.Map.Entry;

public class Map {
	
	/**
	 * stores the map file name.
	 */
	String d_mapFile;
	
	/**
	 * list of continents.
	 */
	List<Continent> d_continents;
	
	/**
	 * list of countries.
	 */
	List<Country> d_countries;
	
	/**
	 * HashMap of the countries one can reach from the existing position.
	 */
	HashMap<Integer, Boolean> d_countryReach = new HashMap<Integer, Boolean>();

	/**
	 * getter method to get the map file.
	 * @return d_mapfile
	 */
	public String getD_mapFile() {
		return d_mapFile;
	}
	
	/**
	 * setter method to set the map file.
	 * @param p_mapFile mapfile name
	 */
	public void setD_mapFile(String p_mapFile) {
		this.d_mapFile = p_mapFile;
	}
	
	/**
	 * getter method to get the list of continents.
	 * @return the list of continents
	 */
	public List<Continent> getD_continents() {
		return d_continents;
	}
	
	/**
	 * setter method to set the list of continents.
	 * @param p_continents list of continents
	 */
	public void setD_continents(List<Continent> p_continents) {
		this.d_continents = p_continents;
	}
	
	/**
	 * getter method to get the list of countries.
	 * @return list of countries
	 */
	public List<Country> getD_countries() {
		return d_countries;
	}
	
	/**
	 * setter method to set the countries.
	 * @param p_countries list of countries
	 */
	public void setD_countries(List<Country> p_countries) {
		this.d_countries = p_countries;
	}
	
	/**
	 * adds the continent to the map.
	 * 
	 * @param p_continent continent to add
	 */
	public void addContinent(Continent p_continent){
		d_continents.add(p_continent);
	}
	
	/**
	 * adds the country to the map.
	 *  
	 * @param p_country country to add
	 */
	public void addCountry(Country p_country){
		d_countries.add(p_country);
	}

	/**
	 * check the existing continents
	 */
	public void checkContinents() {
		for(Continent c: d_continents) {
			System.out.println(c.getD_continentID());
		}
	}
	
	/**
	 * check the existing countries
	 */
	public void checkCountries() {
		for (Country c: d_countries) {
			System.out.println("Country Id "+ c.getD_countryId());
			System.out.println("Continent Id "+c.getD_continentId());
			System.out.println("Neighbours:");
			for (int i: c.getD_adjacentCountryIds()) {
				System.out.println(i);
			}
		}
	}

	/**
	 * Validates the complete map.
	 * 
	 * @return Boolean Value if map is valid
	 * @throws InvalidMap checks for the continent and country connectivity
	 */
	public Boolean Validate() throws InvalidMap{
		return(d_continents != null && checkContinentConnectivity() && checkCountryConnectivity());
	}

	/**
	 * Checks All Continent's Inner Connectivity.
	 * 
	 * @return Boolean Value if all are connected
	 * @throws InvalidMap if any continent is not Connected
	 */
	public Boolean checkContinentConnectivity() throws InvalidMap {
		boolean l_flagConnectivity=true;
		for (Continent c:d_continents){
			if (null == c.getD_countries() || c.getD_countries().size()<1){
				throw new InvalidMap(c.getD_continentName() + " has no countries, it must possess atleast 1 country");
			}
			if(!subGraphConnectivity(c)){
				l_flagConnectivity=false;
			}
		}
		return l_flagConnectivity;
	}

	/**
	 * Checks Inner Connectivity of a Continent.
	 * 
	 * @param p_continent the continent being checked
	 * @return Boolean Value if Continent is Connected
	 * @throws InvalidMap Which country is not connected
	 */
	public boolean subGraphConnectivity(Continent p_continent) throws InvalidMap{
		HashMap <Integer, Boolean> l_continentCountry= new HashMap<Integer , Boolean>();

		for (Country c: p_continent.getD_countries()){
			l_continentCountry.put(c.getD_countryId(), false);
		}
		dfsSubgraph(p_continent.getD_countries().get(0), l_continentCountry, p_continent);
		for (Entry<Integer, Boolean> entry: l_continentCountry.entrySet()){
			if(!entry.getValue()){
				Country l_country = getCountry(entry.getKey());
				String l_messageException= l_country.getD_countryName() +" in Continent "+ p_continent.getD_continentID() +" is not reachable";
				throw new InvalidMap(l_messageException);
			}
		}
		return !l_continentCountry.containsValue(false);
	}

	/**
	 * DFS Applied to the Continent Subgraph.
	 * 
	 * @param p_c country visited
	 * @param p_continentCountry Hashmap of Visited Boolean Values
	 * @param p_continent continent being checked for connectivity
	 */
	public void dfsSubgraph(Country p_c, HashMap<Integer , Boolean> p_continentCountry, Continent p_continent){
		p_continentCountry.put(p_c.getD_countryId(), true);
        System.out.println("Country id " + p_c.getD_countryId() + " continent : " + p_continent.getD_continentID());
		for(Country c: p_continent.getD_countries()){
			if (p_c.getD_adjacentCountryIds().contains(c.getD_countryId())){
				if(!p_continentCountry.get(c.getD_countryId())){
					dfsSubgraph(c, p_continentCountry, p_continent);
				}
			}
		}
	}

	/**
	 * Checks country connectivity in the map.
	 * 
	 * @return boolean value for condition if all the countries are connected
	 * @throws InvalidMap pointing out which Country is not connected
	 */
	public boolean checkCountryConnectivity() throws InvalidMap{
		for(Country c: d_countries){
			d_countryReach.put(c.getD_countryId(), false);
		}
		dfsCountry(d_countries.get(0));
		for (Entry<Integer, Boolean> entry: d_countryReach.entrySet()){
			if (!entry.getValue()){
				String l_exceptionMessage=entry.getKey()+" country is not reachable";
				throw new InvalidMap(l_exceptionMessage);
			}
		}
		return !d_countryReach.containsValue(false);
	}

	/**
	 * Iteratively applies the DFS search from the entered node.
	 * 
	 * @param p_country visited first
	 * @throws InvalidMap pointing out which Country is not connected
	 */
	public void dfsCountry(Country p_country) throws InvalidMap{
		d_countryReach.put(p_country.getD_countryId(), true);
		for (Country l_nextCountry : getAdjacentCountry(p_country)) {
			if (!d_countryReach.get(l_nextCountry.getD_countryId())) {
				dfsCountry(l_nextCountry);
			}
		}
	}

	/**
	 * Gets the Adjacent Country Objects.
	 * 
	 * @param p_country the adjacent country
	 * @return list of Adjacent Country Objects
	 * @throws InvalidMap pointing out which Country is not connected
	 */
	public List<Country> getAdjacentCountry(Country p_country) throws InvalidMap{
		List<Country> l_adjCountries= new ArrayList<Country>();
		if(p_country.getD_adjacentCountryIds().size() > 0) {
			for (int i : p_country.getD_adjacentCountryIds()) {
				l_adjCountries.add(getCountry(i));
			}
		} else{
			throw new InvalidMap(p_country.getD_countryId() + " doesn't have any adjacent countries");
		}
		return l_adjCountries;
	}


	/**
	 * Finds the Country object from a given country ID.
	 * 
	 * @param p_countryId ID of the country object to be found
	 * @return matching country object
	 */
	public Country getCountry(Integer p_countryId){
		for (Country c: d_countries){
			if(c.getD_countryId().equals(p_countryId)){
				return c;
			}
		}
		return null;
	}
}
