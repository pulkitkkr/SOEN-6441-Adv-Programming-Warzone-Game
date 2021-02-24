package Models;

import java.util.List;

public class Player {
	List<Country> d_coutriesOwned;
	List<Continent> d_continentsOwned;
	List<Order> d_ordersToExecute;
	Integer d_noOfUnallocatedArmies;
	String d_playerName;
	
	public List<Country> getD_coutriesOwned() {
		return d_coutriesOwned;
	}
	public void setD_coutriesOwned(List<Country> p_coutriesOwned) {
		this.d_coutriesOwned = p_coutriesOwned;
	}
	public List<Continent> getD_continentsOwned() {
		return d_continentsOwned;
	}
	public void setD_continentsOwned(List<Continent> p_continentsOwned) {
		this.d_continentsOwned = p_continentsOwned;
	}
	public List<Order> getD_ordersToExecute() {
		return d_ordersToExecute;
	}
	public void setD_ordersToExecute(List<Order> p_ordersToExecute) {
		this.d_ordersToExecute = p_ordersToExecute;
	}
	public String getD_playerName() {
		return d_playerName;
	}
	public void setD_playerName(String p_playerName) {
		this.d_playerName = p_playerName;
	}
	public Integer getD_noOfUnallocatedArmies() {
		return d_noOfUnallocatedArmies;
	}
	public void setD_noOfUnallocatedArmies(Integer p_noOfUnallocatedArmies) {
		this.d_noOfUnallocatedArmies = p_noOfUnallocatedArmies;
	}
}