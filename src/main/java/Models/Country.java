package Models;

import java.util.ArrayList;
import java.util.List;

public class Country {

	/**
	 * the number of armies.
	 */
	Integer d_armies;

	/**
	 * country ID.
	 */
	Integer d_countryId;

	/**
	 * continent ID.
	 */
	Integer d_continentId;

	/**
	 * country name.
	 */
	String d_countryName;

	/**
	 * list of the countries which are adjacent to the existing one.
	 */
	List<Integer> d_adjacentCountryIds = new ArrayList<Integer>();;

	/**
	 * constructor of this class.
	 *
	 * @param p_countryId country ID
	 * @param p_countryName country name
	 * @param p_continentId continent ID
	 */
	public Country(int p_countryId, String p_countryName, int p_continentId) {
		d_countryId = p_countryId;
		d_countryName = p_countryName;
		d_continentId = p_continentId;
	}

	/**
	 * constructor of this class.
	 *
	 * @param p_countryId country ID
	 * @param p_continentId continent ID
	 */
	public Country(int p_countryId, int p_continentId) {
		d_countryId = p_countryId;
		d_continentId = p_continentId;
	}

	public Country(String p_countryName) {
		d_countryName = p_countryName;
	}

	/**
	 * getter method to get the armies.
	 *
	 * @return armies
	 */
	public Integer getD_armies() {
		return d_armies;
	}

	/**
	 * setter method to set the armies.
	 *
	 * @param p_armies armies
	 */
	public void setD_armies(Integer p_armies) {
		this.d_armies = p_armies;
	}

	/**
	 * getter method to get the country ID.
	 *
	 * @return country ID
	 */
	public Integer getD_countryId() {
		return d_countryId;
	}

	/**
	 * setter method to set the country ID.
	 *
	 * @param p_countryId country ID
	 */
	public void setD_countryId(Integer p_countryId) {
		this.d_countryId = p_countryId;
	}

	/**
	 * getter method to get the continent ID.
	 *
	 * @return continent ID
	 */
	public Integer getD_continentId() {
		return d_continentId;
	}

	/**
	 * setter method to set the continent ID.
	 *
	 * @param p_continentId continent ID
	 */
	public void setD_continentId(Integer p_continentId) {
		this.d_continentId = p_continentId;
	}

	/**
	 * getter method to get the adjacent country IDs.
	 *
	 * @return list of adjacent country IDs
	 */
	public List<Integer> getD_adjacentCountryIds() {
		if(d_adjacentCountryIds==null){
			d_adjacentCountryIds=new ArrayList<Integer>();
		}

		return d_adjacentCountryIds;
	}

	/**
	 * setter method to set the adjacent country IDs.
	 *
	 * @param p_adjacentCountryIds list of adjacent country IDs
	 */
	public void setD_adjacentCountryIds(List<Integer> p_adjacentCountryIds) {
		this.d_adjacentCountryIds = p_adjacentCountryIds;
	}

	/**
	 * getter method to get the name of the country.
	 *
	 * @return name of the country
	 */
	public String getD_countryName() {
		return d_countryName;
	}

	/**
	 * setter method to set the name of the country.
	 *
	 * @param p_countryName name of the country
	 */
	public void setD_countryName(String p_countryName) {
		this.d_countryName = p_countryName;
	}

	/**
	 * Adds country Id to the neighbor list
	 * @param p_countryId Id of country to be added
	 */
	public void addNeighbour(Integer p_countryId){
		if(!d_adjacentCountryIds.contains(p_countryId))
			d_adjacentCountryIds.add(p_countryId);
	}

	/**
	 * removes country Id from  neighbor list
	 * @param p_countryId Id of country to be removed
	 */
	public void removeNeighbour(Integer p_countryId){
		if(d_adjacentCountryIds.contains(p_countryId)){
			d_adjacentCountryIds.remove(d_adjacentCountryIds.indexOf(p_countryId));
		}else{
			System.out.println("No Such Neighbour Exists");
		}
	}
}
