package Models;

import java.util.List;

public class Map {
	List<Continent> d_continents;
	List<Country> d_countries;
	public List<Continent> getD_continents() {
		return d_continents;
	}
	public void setD_continents(List<Continent> d_continents) {
		this.d_continents = d_continents;
	}
	public List<Country> getD_countries() {
		return d_countries;
	}
	public void setD_countries(List<Country> d_countries) {
		this.d_countries = d_countries;
	}
}
