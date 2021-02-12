package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Map {
	List<Continent> d_continents;
	List<Country> d_countries;
	int d_countryId;
	int d_continentId;
	HashMap<Integer, Boolean> countryReach = new HashMap<Integer, Boolean>();

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
	public void insertCountryData(int p_countryId, int p_continentValue) {
		this.d_countryId = p_countryId;
		this.d_continentId = p_continentValue;	
	}


	public void checkContinents() {
		for(Continent c: d_continents) {
			System.out.println(c.getD_continentId());
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
	public void checkContinentConnectivity(){

	}

	/**
	 * Checks country connectivity in the map
	 * @return bool value for condition if all the countries are connected
	 */
	public boolean checkCountryConnectivity(){
		for(Country c: d_countries){
			countryReach.put(c.getD_countryId(), false);
		}
		dfsCountry(d_countries.get(0));
		return !countryReach.containsValue(false);
	}

	/**
	 * Iteratively applies the DFS search from the entered node
	 * @param p_c Country visited first
	 */
	public void dfsCountry(Country p_c){
		countryReach.put(p_c.getD_countryId(), true);
		for (Country c: d_countries){
			if(!c.getD_countryId().equals(p_c.getD_countryId())){
				for (Country l_nextCountry : getAdjacentCountry(c)) {
					if (!countryReach.get(l_nextCountry.getD_countryId())) {
						dfsCountry(l_nextCountry);
					}
				}
			}
		}
	}

	/**
	 * Gets the Adjacent Country Objects
	 * @param p_c Country
	 * @return list of Adjacent Country Objects
	 */
	public List<Country> getAdjacentCountry(Country p_c){
		List<Country> l_adjCountries= new ArrayList<Country>();
		for (int i: p_c.getD_adjacentCountryIds()){
			l_adjCountries.add(getCountry(i));
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
