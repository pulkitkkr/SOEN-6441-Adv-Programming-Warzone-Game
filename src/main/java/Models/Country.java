package Models;

import java.util.HashMap;
import java.util.List;

public class Country {
	Integer d_armies;
	Integer d_countryId;
	Integer d_continentId;
	List<Integer> d_adjacentCountryIds;
	HashMap<Integer, List<Integer>> d_adjacentCountries;

	public Country(int p_countryId, int p_continentId) {
		this.d_countryId=p_countryId;
		this.d_continentId=p_continentId;
	}

	public Integer getD_armies() {
		return d_armies;
	}
	public void setD_armies(Integer p_armies) {
		this.d_armies = p_armies;
	}
	public Integer getD_countryId() {
		return d_countryId;
	}
	public void setD_countryId(Integer p_countryId) {
		this.d_countryId = p_countryId;
	}
	public int getD_continentId() {
		return d_continentId;
	}
	public void setD_continentId(int p_continentId) {
		this.d_continentId = p_continentId;
	}
	public List<Integer> getD_adjacentCountryIds() {
		return d_adjacentCountryIds;
	}
	public void setD_adjacentCountryIds(List<Integer> p_adjacentCountryIds) {
		this.d_adjacentCountryIds = p_adjacentCountryIds;
	}

}
