package Models;

import java.util.List;

public class Map {
	List<Continent> d_continents;
	List<Country> d_countries;
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
}
