package Models;

import java.util.List;

public class Country {
	Integer d_armies;
	Integer d_countryId;
	String d_continentId;
	List<Integer> d_edjacentCountryIds;
	
	public Integer getD_armies() {
		return d_armies;
	}
	public void setD_armies(Integer d_armies) {
		this.d_armies = d_armies;
	}
	public Integer getD_countryId() {
		return d_countryId;
	}
	public void setD_countryId(Integer d_countryId) {
		this.d_countryId = d_countryId;
	}
	public String getD_continentId() {
		return d_continentId;
	}
	public void setD_continentId(String d_continentId) {
		this.d_continentId = d_continentId;
	}
	public List<Integer> getD_edjacentCountryIds() {
		return d_edjacentCountryIds;
	}
	public void setD_edjacentCountryIds(List<Integer> d_edjacentCountryIds) {
		this.d_edjacentCountryIds = d_edjacentCountryIds;
	}
}
