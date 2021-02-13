package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Continent {
	Integer d_continentID;
	String d_continentId;
	Integer d_continentValue;
	List<Country> d_countries;

	public Continent(Integer p_continentID, String p_continentId, int p_continentValue) {
		this.d_continentID=p_continentID;
		this.d_continentId=p_continentId;
		this.d_continentValue=p_continentValue;
	}
	public Integer getD_continentID() {
		return d_continentID;
	}
	public void setD_continentID(Integer p_continentID) {
		this.d_continentID = p_continentID;
	}
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
	public void addCountry(Country c){
		if (d_countries!=null){
			d_countries.add(c);
		}
		else{
			d_countries=new ArrayList<Country>();
			d_countries.add(c);
		}
	}
	public Boolean checkCountry(Integer p_countryId){
		boolean flag=false;
		for (Country c: d_countries){
			if (c.getD_countryId().equals(p_countryId)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

}
