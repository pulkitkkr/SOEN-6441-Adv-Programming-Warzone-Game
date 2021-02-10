package Models;

public class Order {
	String d_orderAction;
	Integer d_targetCountryId;
	Integer d_sourceCountryId;
	Integer d_numberOfArmiesToMove;
	
	public String getD_orderAction() {
		return d_orderAction;
	}
	public void setD_orderAction(String d_orderAction) {
		this.d_orderAction = d_orderAction;
	}
	public Integer getD_targetCountryId() {
		return d_targetCountryId;
	}
	public void setD_targetCountryId(Integer d_targetCountryId) {
		this.d_targetCountryId = d_targetCountryId;
	}
	public Integer getD_sourceCountryId() {
		return d_sourceCountryId;
	}
	public void setD_sourceCountryId(Integer d_sourceCountryId) {
		this.d_sourceCountryId = d_sourceCountryId;
	}
	public Integer getD_numberOfArmiesToMove() {
		return d_numberOfArmiesToMove;
	}
	public void setD_numberOfArmiesToMove(Integer d_numberOfArmiesToMove) {
		this.d_numberOfArmiesToMove = d_numberOfArmiesToMove;
	}
}
