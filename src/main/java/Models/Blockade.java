package Models;

public class Blockade implements Card {

	Player d_playerInitiator;

	String d_targetCountry;

	public Blockade() {

	}

	public Blockade(Player p_playerInitiator, String p_targetCountry) {
		this.d_playerInitiator = p_playerInitiator;
		this.d_targetCountry = p_targetCountry;
	}

	@Override
	public void execute(GameState p_gameState) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean valid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void printCard() {
		// TODO Auto-generated method stub

	}

	public String toString() {
		return "blockade";
	}
}