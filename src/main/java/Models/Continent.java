package Models;
import Utils.CommonUtil;
import java.util.ArrayList;
import java.util.List;

public class Continent {
	Integer d_continentID;
	String d_continentName;
	Integer d_continentValue;
	List<Country> d_countries;

	public Continent(Integer p_continentID, String p_continentName, int p_continentValue) {
		this.d_continentID=p_continentID;
		this.d_continentName=p_continentName;
		this.d_continentValue=p_continentValue;
	}
	public Continent(){

	}
	public Continent(String p_continentName) {
		this.d_continentName = p_continentName;
	}
	public Integer getD_continentID() {
		return d_continentID;
	}
	public void setD_continentID(Integer p_continentID) {
		this.d_continentID = p_continentID;
	}
	public String getD_continentName() {
		return d_continentName;
	}
	public void setD_continentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}
	public Integer getD_continentValue() {
		return d_continentValue;
	}
	public void setD_continentValue(Integer p_continentValue) {
		this.d_continentValue = p_continentValue;
	}
	public List<Country> getD_countries() {
		return d_countries;
	}
	public void setD_countries(List<Country> p_countries) {
		this.d_countries = p_countries;
	}

	/**
	 * Adds country to the continent
	 * @param p_country country to be added
	 */
	public void addCountry(Country p_country){
		if (d_countries!=null){
			d_countries.add(p_country);
		}
		else{
			d_countries=new ArrayList<Country>();
			d_countries.add(p_country);
		}
	}

	/**
	 * removes Country from Continent
	 * @param p_country country to be removed
	 */
	public void removeCountry(Country p_country){
		if(d_countries==null){
			System.out.println("No such Country Exists");
		}else {
			d_countries.remove(p_country);
		}
	}

	/**
	 * Removes particular country ID from the neighbor list of all countries in continent
	 * @param p_countryId ID of country to be removed
	 */
	public void removeCountryNeighboursFromAll(Integer p_countryId){
		if (null!=d_countries && !d_countries.isEmpty()) {
			for (Country c: d_countries){
				if (!CommonUtil.isNull(c.d_adjacentCountryIds)) {
					if (c.getD_adjacentCountryIds().contains(p_countryId)){
						c.removeNeighbour(p_countryId);
					}
				}
			}
		}
	}

	/**
	 * Add a neighbor to particular country in continent
	 * @param p_countryInContinent ID of country to be added to
	 * @param p_neighbourCountry ID of neighbour to be added
	 */
	public void addCountryNeighbours(Integer p_countryInContinent, Integer p_neighbourCountry){
		for (Country c: d_countries){
			if (c.getD_countryId().equals(p_countryInContinent)){
				c.addNeighbour(p_neighbourCountry);
			}
		}
	}

	/**
	 * Removes the specified neighbor from given country in continent
	 * @param p_countryId ID of country to be updated
	 * @param p_neighbourCountryId neighbor ID to be removed
	 */
	public void removeSpecificNeighbour(Integer p_countryId, Integer p_neighbourCountryId){
		for (Country c:d_countries){
			if (c.getD_countryId().equals(p_countryId)){
				c.removeNeighbour(p_neighbourCountryId);
			}
		}
	}

}
