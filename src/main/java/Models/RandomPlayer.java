/**
 * 
 */
package Models;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is the class of Random Player, who deploys armies randomly , attacks random neighboring countries 
 * and moves armies on his own territories randomly.
 */
public class RandomPlayer extends PlayerBehaviorStrategy {
	
	/**
	 * This method creates a new order.
	 * @param p_player object of Player class
	 * @param p_gameState object of GameState class
	 * 
	 * @return Order object of order class
	 */
	@Override
	public String createOrder(Player p_player, GameState p_gameState) {
		String l_command;

		super.setObjects(p_player, p_gameState);
		if(p_player.getD_noOfUnallocatedArmies()>0) {
			p_player.setD_moreOrders(true);
			l_command = createDeployOrder(p_player);
		}else{
			if(p_player.getD_cardsOwnedByPlayer().size()>0){
				p_player.setD_moreOrders(true);
				Random l_random = new Random();
				int l_randomIndex = l_random.nextInt(p_player.getD_cardsOwnedByPlayer().size()+1);
				if(l_randomIndex==p_player.getD_cardsOwnedByPlayer().size()){
					l_command= createAdvanceOrder(p_player, p_gameState);
				}else{
					l_command = createCardOrder(p_player, p_gameState, p_player.getD_cardsOwnedByPlayer().get(l_randomIndex));
				}
			}else{
				l_command = createAdvanceOrder(p_player, p_gameState);
			}
		}
		return l_command;
	}

	public String createDeployOrder(Player p_player){
		Random l_random = new Random();
		Country l_randomCountry = getRandomOwnCountry(p_player);
		int l_armiesToDeploy = l_random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;
		p_player.setD_noOfUnallocatedArmies(p_player.getD_noOfUnallocatedArmies() - l_armiesToDeploy);

		return String.format("deploy %s %d", l_randomCountry.getD_countryName(), l_armiesToDeploy);
	}

	public String createAdvanceOrder(Player p_player, GameState p_gameState){
		Random l_random = new Random();
		Country l_randomOwnCountry = getRandomOwnCountry(p_player);
		Country l_randomNeighbor = p_gameState.getD_map().getCountry(l_randomOwnCountry.getD_adjacentCountryIds().get(l_random.nextInt(l_randomOwnCountry.getD_adjacentCountryIds().size())));
		int l_armiesToSend = l_random.nextInt(l_randomOwnCountry.getD_armies() - 1) + 1;
		l_randomOwnCountry.setD_armies(l_randomOwnCountry.getD_armies() - l_armiesToSend);

		return "advance "+l_randomOwnCountry.getD_countryName()+" "+l_randomNeighbor.getD_countryName()+" "+ l_armiesToSend;
	}

	public String createCardOrder(Player p_player, GameState p_gameState,  String p_cardName){
		Random l_random = new Random();
		Country l_randomOwnCountry = getRandomOwnCountry(p_player);
		Country l_randomEnemyNeighbor = p_gameState.getD_map().getCountry(randomEnemyNeighbor(p_player, l_randomOwnCountry).get(l_random.nextInt(randomEnemyNeighbor(p_player, l_randomOwnCountry).size())));
		int l_armiesToSend = l_random.nextInt(l_randomOwnCountry.getD_armies() - 1) + 1;
		switch(p_cardName){
			case "bomb":
				return "bomb "+l_randomEnemyNeighbor.getD_countryName();
			case "blockade":
				return "blockade "+ l_randomOwnCountry.getD_countryName();
			case "airlift":
				return "airlift "+ l_randomOwnCountry.getD_countryName()+" "+getRandomOwnCountry(p_player)+" "+l_armiesToSend;
		}
		return null;
	}
	
	/**
	 * This method returns the player behavior.
	 * @return String player behavior
	 */
	@Override
	public String getPlayerBehavior() {
		return "Random";
	}

	public Country getRandomOwnCountry(Player p_player){
		Random l_random = new Random();
		return p_player.getD_coutriesOwned().get(l_random.nextInt(p_player.getD_coutriesOwned().size()));
	}

	public ArrayList<Integer> randomEnemyNeighbor(Player p_player, Country p_country){
		ArrayList<Integer> l_enemyNeighbors = new ArrayList<Integer>();
		for(Integer l_countryID : p_country.getD_adjacentCountryIds()){
			if(!p_player.getCountryIDs().contains(l_countryID))
				l_enemyNeighbors.add(l_countryID);
		}
		return l_enemyNeighbors;
	}
}
