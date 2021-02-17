package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Exceptions.InvalidMap;
import java.util.Map.Entry;

public class Map {
	String d_mapFile;
	List<Continent> d_continents;
	List<Country> d_countries;
	HashMap<Integer, Boolean> d_countryReach = new HashMap<Integer, Boolean>();

	public String getD_mapFile() {
		return d_mapFile;
	}
	public void setD_mapFile(String p_mapFile) {
		this.d_mapFile = p_mapFile;
	}
	public List<Continent> getD_continents() {
		return d_continents;
	}
	public void setD_continents(List<Continent> p_continents) {
		this.d_continents = p_continents;
	}
	public List<Country> getD_countries() {
		return d_countries;
	}
	public void setD_countries(List<Country> p_countries) {
		this.d_countries = p_countries;
	}

	public void checkContinents() {
		for(Continent c: d_continents) {
			System.out.println(c.getD_continentID());
		}
	}
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
	 * Validates the complete map
	 * @return Bool Value if map is valid
	 * @throws InvalidMap
	 */
	public Boolean Validate() throws InvalidMap{
		return(d_continents != null && checkContinentConnectivity() && checkCountryConnectivity());
	}

	/**
	 * Checks All Continent's Inner Connectivity
	 * @return Bool Value if all are connected
	 * @throws InvalidMap if any continent is not Connected
	 */
	public Boolean checkContinentConnectivity() throws InvalidMap {
		boolean l_flagConnectivity=true;
		for (Continent c:d_continents){
			if (null == c.getD_countries() || c.getD_countries().size()<1){
				throw new InvalidMap(c.getD_continentName()+" has no countries, it must possess atleast 1 country");
			}
			if(!subGraphConnectivity(c)){
				l_flagConnectivity=false;
			}
		}
		return l_flagConnectivity;
	}

	/**
	 * Checks Inner Connectivity of a Continent
	 * @param p_c Continent being checked
	 * @return Bool Value if Continent is Connected
	 * @throws InvalidMap Which country is not connected
	 */
	public boolean subGraphConnectivity(Continent p_c) throws InvalidMap{
		HashMap <Integer, Boolean> l_continentCountry= new HashMap<Integer , Boolean>();

		for (Country c: p_c.getD_countries()){
			l_continentCountry.put(c.getD_countryId(), false);
		}
		dfsSubgraph(p_c.getD_countries().get(0), l_continentCountry, p_c);
		for (Entry<Integer, Boolean> entry: l_continentCountry.entrySet()){
			if(!entry.getValue()){
				Country l_country = getCountry(entry.getKey());
				String l_messageException= l_country.getD_countryName()+" in Continent"+ p_c.getD_continentID()+" is not reachable";
				throw new InvalidMap(l_messageException);
			}
		}
		return !l_continentCountry.containsValue(false);
	}

	/**
	 * DFS Applied to the Continent Subgraph
	 * @param p_c Country visited
	 * @param p_continentCountry Hashmap of Visited Bool Values
	 * @param p_continent continent being checked for connectivity
	 */
	public void dfsSubgraph(Country p_c, HashMap<Integer , Boolean> p_continentCountry, Continent p_continent){
		p_continentCountry.put(p_c.getD_countryId(), true);
		for(Country c: p_continent.getD_countries()){
			if (p_c.getD_adjacentCountryIds().contains(c.getD_countryId())){
				if(!p_continentCountry.get(c.getD_countryId())){
					System.out.println("Country id " + c.getD_countryId() + " continent : " + p_continent.getD_continentID());
					dfsSubgraph(c, p_continentCountry, p_continent);
				}
			}
		}
	}

	/**
	 * Checks country connectivity in the map
	 * @return bool value for condition if all the countries are connected
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
	 * Iteratively applies the DFS search from the entered node
	 * @param p_c Country visited first
	 */
	public void dfsCountry(Country p_c) throws InvalidMap{
		d_countryReach.put(p_c.getD_countryId(), true);
		for (Country l_nextCountry : getAdjacentCountry(p_c)) {
			if (!d_countryReach.get(l_nextCountry.getD_countryId())) {
				dfsCountry(l_nextCountry);
			}
		}
	}

	/**
	 * Gets the Adjacent Country Objects
	 * @param p_c Country
	 * @return list of Adjacent Country Objects
	 */
	public List<Country> getAdjacentCountry(Country p_c) throws InvalidMap{
		List<Country> l_adjCountries= new ArrayList<Country>();
		if(p_c.getD_adjacentCountryIds().size()>0) {
			for (int i : p_c.getD_adjacentCountryIds()) {
				l_adjCountries.add(getCountry(i));
			}
		} else{
			throw new InvalidMap(p_c.getD_countryId()+" doesn't have any adjacent countries");
		}
		return l_adjCountries;
	}


	/**
	 * Finds the Country object from a given country ID
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
