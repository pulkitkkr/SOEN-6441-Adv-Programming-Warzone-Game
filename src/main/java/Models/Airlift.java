package Models;

/**
 * Class handles the execute and validation of Airlift Validate.
 *
 */
public class Airlift implements Card {

	/**
	 * Player the card is owned by.
	 */
	Player d_player;

	/**
	 * Country to take armies from.
	 */
	String d_sourceCountryName;

	/**
	 * Country to drop armies to.
	 */
	String d_targetCountryName;

	/**
	 * Number of armies to Airlift
	 */
	Integer d_numberOfArmies;

	/**
	 * Records the execution log.
	 */
	String d_orderExecutionLog;

	/**
	 * Constructor Initialising the card parameters.
	 *
	 * @param p_sourceCountryName source country to airlift from.
	 * @param p_targetCountryName target country to drop off armies to.
	 * @param p_noOfArmies No of armies to airlift
	 * @param p_player player owning the card
	 */
	public Airlift(String p_sourceCountryName, String p_targetCountryName, Integer p_noOfArmies, Player p_player){
		this.d_numberOfArmies = p_noOfArmies;
		this.d_targetCountryName = p_targetCountryName;
		this.d_sourceCountryName = p_sourceCountryName;
		this.d_player = p_player;
	}


	/**
	 * 
	 */
	@Override
	public void execute(GameState p_gameState) {
		if(valid()){
			System.out.println("Passed Validation and Entered execute");
			Country l_sourceCountry = p_gameState.getD_map().getCountryByName(d_sourceCountryName);
			Country l_targetCountry = p_gameState.getD_map().getCountryByName(d_targetCountryName);
			Integer l_updatedTargetArmies = l_targetCountry.getD_armies() + this.d_numberOfArmies;
			Integer l_updatedSourceArmies = l_sourceCountry.getD_armies() - this.d_numberOfArmies;
			l_targetCountry.setD_armies(l_updatedTargetArmies);
			l_sourceCountry.setD_armies(l_updatedSourceArmies);
			d_player.removeCard("airlift");
			this.setD_orderExecutionLog("Airlift Operation from "+ d_sourceCountryName+ "to "+d_targetCountryName+" successful!", "default");
			p_gameState.updateLog(d_orderExecutionLog, "effect");
		}else{
			this.setD_orderExecutionLog("Cannot Complete Execution of given Airlift Command!", "error");
			p_gameState.updateLog(d_orderExecutionLog, "effect");
		}
	}

	/**
	 * 
	 */
	@Override
	public boolean valid() {
		Country l_sourceCountry = d_player.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_sourceCountryName.toString()))
				.findFirst().orElse(null);
		if (l_sourceCountry == null) {
			this.setD_orderExecutionLog(this.currentOrder() + " is not executed since Source country : "
					+ this.d_sourceCountryName + " given in card order does not belongs to the player : "
					+ d_player.getPlayerName(), "error");
			return false;
		}
		Country l_targetCountry = d_player.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryName.toString()))
				.findFirst().orElse(null);
		if (l_targetCountry == null){
			this.setD_orderExecutionLog(this.currentOrder() + " is not executed since Target country : "
					+ this.d_sourceCountryName + " given in card order does not belongs to the player : "
					+ d_player.getPlayerName(), "error");
			return false;
		}
		if (this.d_numberOfArmies > l_sourceCountry.getD_armies()) {
			this.setD_orderExecutionLog(this.currentOrder()
					+ " is not executed as armies given in card order exceeds armies of source country : "
					+ this.d_sourceCountryName, "error");
			return false;
		}
		return true;
	}

	@Override
	public void printOrder() {
		this.d_orderExecutionLog = "----------Airlift order issued by player " + this.d_player.getPlayerName()+"----------"+System.lineSeparator()+"Move " + this.d_numberOfArmies + " armies from " + this.d_sourceCountryName + " to " + this.d_targetCountryName;
		System.out.println(this.d_orderExecutionLog);
	}

	@Override
	public String orderExecutionLog() {
		return this.d_orderExecutionLog;
	}

	/**
	 * Prints and Sets the order execution log.
	 *
	 * @param p_orderExecutionLog String to be set as log
	 * @param p_logType type of log : error, default
	 */
	public void setD_orderExecutionLog(String p_orderExecutionLog,String p_logType) {
		this.d_orderExecutionLog = p_orderExecutionLog;
		if(p_logType.equals("error")) {
			System.err.println(p_orderExecutionLog);
		}else{
			System.out.println(p_orderExecutionLog);
		}
	}
	/**
	 * Gives current advance order which is being executed.
	 *
	 * @return advance order command
	 */
	private String currentOrder() {
		return "Advance Order : " + "advance" + " " + this.d_sourceCountryName + " " + this.d_targetCountryName + " "
				+ this.d_numberOfArmies;
	}

	@Override
	public Boolean checkValidOrder(GameState p_GameState) {
		Country l_sourceCountry = p_GameState.getD_map().getCountryByName(d_sourceCountryName);
		Country l_targetCountry = p_GameState.getD_map().getCountryByName(d_targetCountryName);
		if(l_sourceCountry==null){
			this.setD_orderExecutionLog("Invalid Source Country! Doesn't exist on the map!", "error");
			return false;
		}
		if(l_targetCountry==null){
			this.setD_orderExecutionLog("Invalid Target Country! Doesn't exist on the map!", "error");
			return false;
		}
		return true;
	}

	@Override
	public String getD_cardName() {
		return "airlift";
	}
}
