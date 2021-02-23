package Models;

/**
 * 
 * This is the Order Class.
 * 
 */
public class Order {
	public static String d_orderAction;
	public static Integer d_targetCountryId;
	public static Integer d_sourceCountryId;
	public static Integer d_numberOfArmiesToPlace;
	public static String d_countryName;
	private static Order orderObj;
	
	public static Order getInstance() {
		if(orderObj==null){
			orderObj = new Order();
		}
		return orderObj;
	}

	public Order() {
		
	}
	public Order(String p_orderAction, String p_countryName, Integer p_numberOfArmiesToPlace) {
		this.d_orderAction = p_orderAction;
		this.d_countryName = p_countryName;
		this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
	}

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

	public Integer getD_numberOfArmiesToPlace() {
		return d_numberOfArmiesToPlace;
	}

	public void setD_numberOfArmiesToPlace(Integer p_numberOfArmiesToPlace) {
		this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
	}

	public static String getD_countryName() {
		return d_countryName;
	}

	public void setD_countryName(String p_countryName) {
		this.d_countryName = p_countryName;
	}
	
	
	public void execute() {
		
	}
}
