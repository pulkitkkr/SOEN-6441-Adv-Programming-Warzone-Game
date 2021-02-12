package Models;

import java.util.List;

public class Continent {
	Integer d_continentId;
	String d_continentName;
	Integer d_continentValue;
	List<Country> d_countries;

	public Integer getD_continentId() {
		return d_continentId;
	}
	public void setD_continentId(Integer p_continentId) {
		this.d_continentId = p_continentId;
	}
	public String getD_continentName() {
		return d_continentName;
	}
	public void setD_continentName(String d_continentName) {
		this.d_continentName = d_continentName;
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
	public Continent(Integer p_continentId, String p_continentName, Integer p_continentValue) {
		super();
		this.d_continentId = p_continentId;
		this.d_continentName = p_continentName;
		this.d_continentValue = p_continentValue;
	}
	public Continent() {
		super();
	}
}
