package Models;

/**
 * This is the Order Class.
 */
public class Order {
	
	/**
	 * order actions.
	 */
	String d_orderAction;
	
	/**
	 * ID of target country.
	 */
	Integer d_targetCountryId;
	
	/**
	 * ID of source country
	 */
	Integer d_sourceCountryId;
	
	/**
	 * the number of armies to move from source country to target country.
	 */
	Integer d_numberOfArmiesToMove;
	
	/**
	 * getter method to get order action.
	 * 
	 * @return order action
	 */
	public String getD_orderAction() {
		return d_orderAction;
	}
	
	/**
	 * setter method to set order action.
	 * 
	 * @param p_orderAction order action
	 */
	public void setD_orderAction(String p_orderAction) {
		this.d_orderAction = p_orderAction;
	}
	
	/**
	 * getter method to get the target country ID
	 * 
	 * @return target country ID
	 */
	public Integer getD_targetCountryId() {
		return d_targetCountryId;
	}
	
	/**
	 * setter method to set the target country ID
	 * 
	 * @param p_targetCountryId target country ID
	 */
	public void setD_targetCountryId(Integer p_targetCountryId) {
		this.d_targetCountryId = p_targetCountryId;
	}
	
	/**
	 * getter method to get the source country ID
	 * 
	 * @return source country ID
	 */
	public Integer getD_sourceCountryId() {
		return d_sourceCountryId;
	}
	
	/**
	 * setter method to set the source country ID
	 * 
	 * @param p_sourceCountryId source country ID
	 */
	public void setD_sourceCountryId(Integer p_sourceCountryId) {
		this.d_sourceCountryId = p_sourceCountryId;
	}
	
	/**
	 * getter method to get the number of armies to move from source country to target country.
	 * 
	 * @return number of armies to move
	 */
	public Integer getD_numberOfArmiesToMove() {
		return d_numberOfArmiesToMove;
	}
	
	/**
	 * setter method to set the number of armies to move from source country to target country.
	 * 
	 * @param p_numberOfArmiesToMove number of armies to move
	 */
	public void setD_numberOfArmiesToMove(Integer p_numberOfArmiesToMove) {
		this.d_numberOfArmiesToMove = p_numberOfArmiesToMove;
	}
}
