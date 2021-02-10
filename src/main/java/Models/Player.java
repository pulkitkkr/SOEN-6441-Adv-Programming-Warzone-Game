package Models;

import java.util.List;

public class Player {
	List<Country> d_coutriesOwned;
	List<Continent> d_continentsOwned;
	List<Order> d_ordersToExecute;
	Integer d_noOfUnallocatedArmies;
	
	public List<Country> getD_coutriesOwned() {
		return d_coutriesOwned;
	}
	public void setD_coutriesOwned(List<Country> d_coutriesOwned) {
		this.d_coutriesOwned = d_coutriesOwned;
	}
	public List<Continent> getD_continentsOwned() {
		return d_continentsOwned;
	}
	public void setD_continentsOwned(List<Continent> d_continentsOwned) {
		this.d_continentsOwned = d_continentsOwned;
	}
	public List<Order> getD_ordersToExecute() {
		return d_ordersToExecute;
	}
	public void setD_ordersToExecute(List<Order> d_ordersToExecute) {
		this.d_ordersToExecute = d_ordersToExecute;
	}
	public Integer getD_noOfUnallocatedArmies() {
		return d_noOfUnallocatedArmies;
	}
	public void setD_noOfUnallocatedArmies(Integer d_noOfUnallocatedArmies) {
		this.d_noOfUnallocatedArmies = d_noOfUnallocatedArmies;
	}
}
