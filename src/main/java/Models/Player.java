package Models;

import java.util.List;

public class Player {
	Integer d_coutriesOwned;
	Integer d_continentsOwned;
	List<Order> d_ordersToExecute;
	
	public Integer getD_coutriesOwned() {
		return d_coutriesOwned;
	}
	public void setD_coutriesOwned(Integer d_coutriesOwned) {
		this.d_coutriesOwned = d_coutriesOwned;
	}
	public Integer getD_continentsOwned() {
		return d_continentsOwned;
	}
	public void setD_continentsOwned(Integer d_continentsOwned) {
		this.d_continentsOwned = d_continentsOwned;
	}
	public List<Order> getD_ordersToExecute() {
		return d_ordersToExecute;
	}
	public void setD_ordersToExecute(List<Order> d_ordersToExecute) {
		this.d_ordersToExecute = d_ordersToExecute;
	}
}
