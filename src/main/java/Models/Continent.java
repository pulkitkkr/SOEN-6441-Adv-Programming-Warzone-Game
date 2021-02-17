package Models;

import java.util.List;

public class Continent {
	String d_continentId;
	Integer d_continentValue;
	List<Country> d_countries;

	public String getD_continentId() {
		return d_continentId;
	}
	public void setD_continentId(String p_continentId) {
		this.d_continentId = p_continentId;
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
}
