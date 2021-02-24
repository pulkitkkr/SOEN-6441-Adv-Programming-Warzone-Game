package Models;

import java.util.List;

/**
 * This class handles all the players.
 */
public class Player {
	
	/**
	 * List of countries owned by the player.
	 */
	List<Country> d_coutriesOwned;
	
	/**
	 * List of continents owned by the player.
	 */
	List<Continent> d_continentsOwned;
	
	/**
	 * List of orders player wants to execute.
	 */
	List<Order> d_ordersToExecute;
	
	/**
	 * Integer of unallocated armies owned by the player.
	 */
	Integer d_noOfUnallocatedArmies;
	
	/**
	 * Stores player name.
	 */
	String d_playerName;
	
	/**
	 * getter method to get the countries owned by the player.
	 * 
	 * @return List of countries
	 */
	public List<Country> getD_coutriesOwned() {
		return d_coutriesOwned;
	}
	
	/**
	 * setter method to store the countries owned by the player.
	 * 
	 * @param p_coutriesOwned the list of countries player owns
	 */
	public void setD_coutriesOwned(List<Country> p_coutriesOwned) {
		this.d_coutriesOwned = p_coutriesOwned;
	}
	
	/**
	 * getter method to get the continents owned by the player.
	 * 
	 * @return List of continents
	 */
	public List<Continent> getD_continentsOwned() {
		return d_continentsOwned;
	}
	
	/**
	 * setter method to store the continents owned by the player.
	 * 
	 * @param p_continentsOwned the list of continents owned by the player
	 */
	public void setD_continentsOwned(List<Continent> p_continentsOwned) {
		this.d_continentsOwned = p_continentsOwned;
	}
	
	/**
	 * getter method to get the list of the orders given by the player.
	 * 
	 * @return List order to be executed
	 */
	public List<Order> getD_ordersToExecute() {
		return d_ordersToExecute;
	}
	
	/**
	 * setter method to store the list of orders given by the player.
	 * 
	 * @param p_ordersToExecute List of orders to execute
	 */
	public void setD_ordersToExecute(List<Order> p_ordersToExecute) {
		this.d_ordersToExecute = p_ordersToExecute;
	}
	
	/**
	 * getter method to get the name of the player.
	 * 
	 * @return String name of the player
	 */
	public String getD_playerName() {
		return d_playerName;
	}
	
	/**
	 * setter method to store the player's name.
	 * 
	 * @param p_playerName name of the player
	 */
	public void setD_playerName(String p_playerName) {
		this.d_playerName = p_playerName;
	}
	
	/**
	 * getter method to get the number of unallocated armies owned by the player.
	 * 
	 * @return Integer number of unallocated armies
	 */
	public Integer getD_noOfUnallocatedArmies() {
		return d_noOfUnallocatedArmies;
	}
	
	/**
	 * setter method to store the number of unallocated armies owned by the player.
	 * 
	 * @param p_noOfUnallocatedArmies number of unallocated armies
	 */
	public void setD_noOfUnallocatedArmies(Integer p_noOfUnallocatedArmies) {
		this.d_noOfUnallocatedArmies = p_noOfUnallocatedArmies;
	}
}