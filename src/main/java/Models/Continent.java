package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * This model class manages all the Continents in the map.
 */
public class Continent {
	
	/**
	 * continent ID.
	 */
	Integer d_continentID;
	
	/**
	 * continent name.
	 */
	String d_continentName;
	
	/**
	 * continent value.
	 */
	Integer d_continentValue;
	
	/**
	 * List of countries.
	 */
	List<Country> d_countries;

	/**
	 * constructor to this class.
	 * 
	 * @param p_continentID continent ID
	 * @param p_continentName continent name
	 * @param p_continentValue continent value
	 */
	public Continent(Integer p_continentID, String p_continentName, int p_continentValue) {
		this.d_continentID=p_continentID;
		this.d_continentName=p_continentName;
		this.d_continentValue=p_continentValue;
	}
	
	/**
	 * contructor to this class.
	 */
	public Continent(){
	}
	
	/**
	 * getter method to get the continent ID.
	 * 
	 * @return continent ID
	 */
	public Integer getD_continentID() {
		return d_continentID;
	}
	
	/**
	 * setter method to set the continenet ID.
	 * 
	 * @param p_continentID continent ID
	 */
	public void setD_continentID(Integer p_continentID) {
		this.d_continentID = p_continentID;
	}
	
	/**
	 * getter method to get the continent name.
	 * 
	 * @return continent name
	 */
	public String getD_continentName() {
		return d_continentName;
	}
	
	/**
	 * setter method to set the continent name.
	 * 
	 * @param p_continentName name of the continent
	 */
	public void setD_continentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}
	
	/**
	 * getter method to get the continent value.
	 * 
	 * @return continent value
	 */
	public Integer getD_continentValue() {
		return d_continentValue;
	}
	
	/**
	 * setter method to set the continent value.
	 * 
	 * @param p_continentValue the continent value
	 */
	public void setD_continentValue(Integer p_continentValue) {
		this.d_continentValue = p_continentValue;
	}
	
	/**
	 * getter method to get the countries.
	 * 
	 * @return list of countries
	 */
	public List<Country> getD_countries() {
		return d_countries;
	}
	
	/**
	 * setter method to set the countries.
	 * 
	 * @param p_countries list of countries
	 */
	public void setD_countries(List<Country> p_countries) {
		this.d_countries = p_countries;
	}
	
	/**
	 * adds the specified country.
	 * 
	 * @param p_c the country to be added
	 */
	public void addCountry(Country p_c){
		if (d_countries!=null){
			d_countries.add(p_c);
		}
		else{
			d_countries=new ArrayList<Country>();
			d_countries.add(p_c);
		}
	}
	
	/**
	 * checks the existence of the specified country.
	 * @param p_countryId country ID to be checked
	 * @return true/false based on the existence
	 */
	public Boolean checkCountry(Integer p_countryId){
		boolean l_flag=false;
		for (Country c: d_countries){
			if (c.getD_countryId().equals(p_countryId)) {
				l_flag = true;
				break;
			}
		}
		return l_flag;
	}

}
