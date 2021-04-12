/**
 *
 */
package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is the class of Random Player, who deploys armies randomly , attacks random neighboring countries 
 * and moves armies on his own territories randomly.
 */
public class RandomPlayer extends PlayerBehaviorStrategy {

	/**
	 * List containing deploy order countries.
	 */
	ArrayList<Country> d_deployCountries = new ArrayList<Country>();
	
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
			l_command = createDeployOrder(p_player);
		}else{
			if(p_player.getD_cardsOwnedByPlayer().size()>0){
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createDeployOrder(Player p_player){
		Random l_random = new Random();
		Country l_randomCountry = getRandomCountry(p_player.getD_coutriesOwned());
		d_deployCountries.add(l_randomCountry);
		int l_armiesToDeploy = l_random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;

		return String.format("deploy %s %d", l_randomCountry.getD_countryName(), l_armiesToDeploy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createAdvanceOrder(Player p_player, GameState p_gameState){
		int l_armiesToSend;
		Random l_random = new Random();
		Country l_randomOwnCountry = getRandomCountry(d_deployCountries);
		Country l_randomNeighbor = p_gameState.getD_map().getCountry(l_randomOwnCountry.getD_adjacentCountryIds().get(l_random.nextInt(l_randomOwnCountry.getD_adjacentCountryIds().size())));

		if (l_randomOwnCountry.getD_armies()>1) {
			l_armiesToSend = l_random.nextInt(l_randomOwnCountry.getD_armies() - 1) + 1;
		} else {
			l_armiesToSend = 1;
		}
		return "advance "+l_randomOwnCountry.getD_countryName()+" "+l_randomNeighbor.getD_countryName()+" "+ l_armiesToSend;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createCardOrder(Player p_player, GameState p_gameState, String p_cardName){
		int l_armiesToSend;
		Random l_random = new Random();
		Country l_randomOwnCountry = getRandomCountry(p_player.getD_coutriesOwned());
		Country l_randomEnemyNeighbor = p_gameState.getD_map().getCountry(randomEnemyNeighbor(p_player, l_randomOwnCountry).get(l_random.nextInt(randomEnemyNeighbor(p_player, l_randomOwnCountry).size())));
		Player l_randomPlayer = getRandomPlayer(p_player, p_gameState);

		if (l_randomOwnCountry.getD_armies()>1) {
			l_armiesToSend = l_random.nextInt(l_randomOwnCountry.getD_armies() - 1) + 1;
		} else {
			l_armiesToSend = 1;
		}
		switch(p_cardName){
			case "bomb":
				return "bomb "+l_randomEnemyNeighbor.getD_countryName();
			case "blockade":
				return "blockade "+ l_randomOwnCountry.getD_countryName();
			case "airlift":
				return "airlift "+ l_randomOwnCountry.getD_countryName()+" "+getRandomCountry(p_player.getD_coutriesOwned())+" "+l_armiesToSend;
			case "negotiate":
				return "negotiate"+" "+l_randomPlayer;
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

	private Country getRandomCountry(List<Country> p_listOfCountries){
		Random l_random = new Random();
		return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
	}

	private ArrayList<Integer> randomEnemyNeighbor(Player p_player, Country p_country){
		ArrayList<Integer> l_enemyNeighbors = new ArrayList<Integer>();

		for(Integer l_countryID : p_country.getD_adjacentCountryIds()){
			if(!p_player.getCountryIDs().contains(l_countryID))
				l_enemyNeighbors.add(l_countryID);
		}
		return l_enemyNeighbors;
	}

	private Player getRandomPlayer(Player p_player, GameState p_gameState){
		ArrayList<Player> l_playerList = new ArrayList<Player>();
		Random l_random = new Random();

		for(Player l_player : p_gameState.getD_players()){
			if(!l_player.equals(p_player))
				l_playerList.add(p_player);
		}
		return l_playerList.get(l_random.nextInt(l_playerList.size()));
 	}
}
