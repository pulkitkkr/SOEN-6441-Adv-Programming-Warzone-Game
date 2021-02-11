package Models;

import java.util.List;

public class Map {
	List<Continent> d_continents;
	List<Country> d_countries;
	int d_countryId;
	int d_continentId;
	
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
}
