package Models;

import java.util.List;

public class Continent {
	Integer d_continentId;
	Integer d_continentValue;
	List<Country> d_countries;

	public Integer getD_continentId() {
		return d_continentId;
	}
	public void setD_continentId(Integer d_continentId) {
		this.d_continentId = d_continentId;
	}
	public Integer getD_continentValue() {
		return d_continentValue;
	}
	public void setD_continentValue(Integer d_continentValue) {
		this.d_continentValue = d_continentValue;
	}
	public List<Country> getD_countries() {
		return d_countries;
	}
	public void setD_countries(List<Country> d_countries) {
		this.d_countries = d_countries;
	}
}
