package Models;

public class Deploy implements Order {
	/**
	 * name of the target country.
	 */
	String d_targetCountryName;

	/**
	 * name of the source country.
	 */
	Country d_sourceCountryName;

	/**
	 * number of armies to be placed.
	 */
	Integer d_numberOfArmiesToPlace;

	Player initiator;
	
	GameState gs;

//	Territory target_territory;
//	int to_deploy;
//	Player initiator;

	/**
	 * The constructor receives all the parameters necessary to implement the order.
	 * These are then encapsulated in the order.
	 * 
	 * @param initiator:        player that created the order
	 * @param target_territory: territory that will receive the new armies
	 * @param to_deploy:        number of armies to be added
	 */
	public Deploy(Player initiator, String target_territory, Integer d_numberOfArmiesToPlace, GameState gs) {
		this.d_targetCountryName = target_territory;
		this.initiator = initiator;
		this.d_numberOfArmiesToPlace = d_numberOfArmiesToPlace;
		this.gs =gs;
	}

	/**
	 * Enacts the order object and makes necessary changes in game state.
	 * 
	 * @param p_gameState current state of the game
	 * @param p_player    player who's order is being executed
	 */
	
	
	@Override
	public void execute() {
		if(valid()) {
			for (Country l_country : this.gs.getD_map().getD_countries()) {
				if (l_country.getD_countryName().equalsIgnoreCase(this.d_targetCountryName)) {
					Integer l_armiesToUpdate = l_country.getD_armies() == null ? this.d_numberOfArmiesToPlace
							: l_country.getD_armies() + this.d_numberOfArmiesToPlace;
					l_country.setD_armies(l_armiesToUpdate);
				}
			}
			
		}else {
			System.out.println(
					"\nOrder is not executed as target country given in deploy command doesnt belongs to player : "
							+ initiator.getPlayerName());

		}

	}
//	public void execute(GameState p_gameState, Player p_player) {
//		switch (this.d_orderAction) {
//		case "deploy": {
//			if (this.validateDeployOrderCountry(p_player, this)) {
//				this.executeDeployOrder(this, p_gameState, p_player);
//				System.out.println("\nOrder has been executed successfully. " + this.getD_numberOfArmiesToPlace()
//						+ " number of armies has been deployed to country : " + this.getD_targetCountryName());
//			} else {
//				System.out.println(
//						"\nOrder is not executed as target country given in deploy command doesnt belongs to player : "
//								+ p_player.getPlayerName());
//			}
//			break;
//		}
//		default: {
//			System.out.println("Order was not executed due to invalid Order Command");
//		}
//		}
//	}
	
	
//	/**
//	 * Executes deploy order and updates game state with latest map.
//	 * 
//	 * @param p_order     order which is being executed
//	 * @param p_gameState current state of the game
//	 * @param p_player    player whos order is being executed
//	 */
//	private void executeDeployOrder(Order p_order, GameState p_gameState, Player p_player) {
//		for (Country l_country : p_gameState.getD_map().getD_countries()) {
//			if (l_country.getD_countryName().equalsIgnoreCase(p_order.getD_targetCountryName())) {
//				Integer l_armiesToUpdate = l_country.getD_armies() == null ? p_order.getD_numberOfArmiesToPlace()
//						: l_country.getD_armies() + p_order.getD_numberOfArmiesToPlace();
//				l_country.setD_armies(l_armiesToUpdate);
//			}
//		}
//	}


	/**
	 * Validates whether country given for deploy belongs to players countries or
	 * not.
	 * 
	 * @param p_player player whos order is being executed
	 * @param p_order  order which is being executed
	 * @return true/false
	 */
//	public boolean validateDeployOrderCountry(Player p_player, Order p_order) {
//		Country l_country = p_player.getD_coutriesOwned().stream()
//				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(p_order.getD_targetCountryName())).findFirst()
//				.orElse(null);
//		return l_country != null;
//	}

	@Override
	public boolean valid() {
		Country l_country = initiator.getD_coutriesOwned().stream()
				.filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryName.toString()))
				.findFirst().orElse(null);
		return l_country != null;
	}

	
	

	@Override
	public void printOrder() {
		// TODO Auto-generated method stub

	}

}
