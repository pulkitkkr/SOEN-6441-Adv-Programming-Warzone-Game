package Models;

/**
 * 
 * This is the Order Class.
 * 
 */
public class Order {
	String d_orderAction;
	Integer d_targetCountryId;
	Integer d_sourceCountryId;
	Integer d_numberOfArmiesToMove;
	
	public String getD_orderAction() {
		return d_orderAction;
	}
	public void setD_orderAction(String p_orderAction) {
		this.d_orderAction = p_orderAction;
	}
	public Integer getD_targetCountryId() {
		return d_targetCountryId;
	}
	public void setD_targetCountryId(Integer p_targetCountryId) {
		this.d_targetCountryId = p_targetCountryId;
	}
	public Integer getD_sourceCountryId() {
		return d_sourceCountryId;
	}
	public void setD_sourceCountryId(Integer p_sourceCountryId) {
		this.d_sourceCountryId = p_sourceCountryId;
	}
	public Integer getD_numberOfArmiesToMove() {
		return d_numberOfArmiesToMove;
	}
	public void setD_numberOfArmiesToMove(Integer p_numberOfArmiesToMove) {
		this.d_numberOfArmiesToMove = p_numberOfArmiesToMove;
	}
}
