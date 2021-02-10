package Models;

import java.util.List;

public class GameState {
	Map d_map;
	List<Player> d_players;
	List<Order> d_unexecutedOrders;

	public Map getD_map() {
		return d_map;
	}

	public void setD_map(Map d_map) {
		this.d_map = d_map;
	}

	public List<Player> getD_players() {
		return d_players;
	}

	public void setD_players(List<Player> d_players) {
		this.d_players = d_players;
	}

	public List<Order> getD_unexecutedOrders() {
		return d_unexecutedOrders;
	}

	public void setD_unexecutedOrders(List<Order> d_unexecutedOrders) {
		this.d_unexecutedOrders = d_unexecutedOrders;
	}
}
