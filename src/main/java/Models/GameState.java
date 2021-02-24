package Models;

import java.util.List;

public class GameState {
	Map d_map;
	List<Player> d_players;
	List<Order> d_unexecutedOrders;
	String d_error;

	public Map getD_map() {
		return d_map;
	}
	
	public void setD_map(Map p_map) {
		this.d_map = p_map;
	}

	public List<Player> getD_players() {
		return d_players;
	}

	public void setD_players(List<Player> p_players) {
		this.d_players = p_players;
	}

	public List<Order> getD_unexecutedOrders() {
		return d_unexecutedOrders;
	}

	public void setD_unexecutedOrders(List<Order> p_unexecutedOrders) {
		this.d_unexecutedOrders = p_unexecutedOrders;
	}

	public String getError() {
		return d_error;
	}

	public void setError(String p_error) {
		this.d_error = p_error;
	}
}
