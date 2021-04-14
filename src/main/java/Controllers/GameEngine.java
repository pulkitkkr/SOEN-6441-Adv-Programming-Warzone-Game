package Controllers;

import Models.GameState;
import Models.IssueOrderPhase;
import Models.OrderExecutionPhase;
import Models.Phase;
import Models.StartUpPhase;

/**
 * This is the entry point of the Game and keeps the track of current Game
 * State.
 */
public class GameEngine {
	/**
	 * d_gameState stores the information about current GamePlay.
	 */
	GameState d_gameState = new GameState();

	/**
	 * Gets current state of the game.
	 * 
	 * @return state of the game
	 */
	public GameState getD_gameState() {
		return d_gameState;
	}

	/**
	 * Sets state of the game.
	 * 
	 * @param p_gameState state of the game
	 */
	public void setD_gameState(GameState p_gameState) {
		this.d_gameState = p_gameState;
	}

	/**
	 * It is the current game play phase as per state pattern.
	 */
	Phase d_currentPhase = new StartUpPhase(this, d_gameState);
	
	/**
	 * Tournament mode or single game mode.
	 */
	static boolean d_isTournamentMode = false;

	/**
	 * Tournament mode information.
	 * 
	 * @return true if tournament is being played or else false
	 */
	public boolean isD_isTournamentMode() {
		return d_isTournamentMode;
	}

	/**
	 * Sets tournament mode information.
	 * 
	 * @param p_isTournamentMode true if tournament is being played or else false
	 */
	public void setD_isTournamentMode(boolean p_isTournamentMode) {
		GameEngine.d_isTournamentMode = p_isTournamentMode;
	}

	/**
	 * It's used to update context.
	 *
	 * @param p_phase new Phase to set in Game context
	 */
	public void setD_CurrentPhase(Phase p_phase) {
		d_currentPhase = p_phase;
	}

	/**
	 * this methods updates the current phase to StartUp Phase as per State Pattern.
	 */
	public void setStartUpPhase(){
		this.setD_gameEngineLog("Start Up Phase", "phase");
		setD_CurrentPhase(new StartUpPhase(this, d_gameState));
		getD_CurrentPhase().initPhase(d_isTournamentMode);
	}

	/**
	 * this methods updates the current phase to Issue Order Phase as per State
	 * Pattern.
	 * 
	 * @param p_isTournamentMode true if tournament is being played or else false
	 */
	public void setIssueOrderPhase(boolean p_isTournamentMode) {
		this.setD_gameEngineLog("Issue Order Phase", "phase");
		setD_CurrentPhase(new IssueOrderPhase(this, d_gameState));
		getD_CurrentPhase().initPhase(p_isTournamentMode);
	}

	/**
	 * this methods updates the current phase to Order Execution Phase as per State
	 * Pattern.
	 */
	public void setOrderExecutionPhase() {
		this.setD_gameEngineLog("Order Execution Phase", "phase");
		setD_CurrentPhase(new OrderExecutionPhase(this, d_gameState));
		getD_CurrentPhase().initPhase(d_isTournamentMode);
	}

	/**
	 * This method is getter for current Phase of Game Context.
	 *
	 * @return current Phase of Game Context
	 */
	public Phase getD_CurrentPhase() {
		return d_currentPhase;
	}

	/**
	 * Shows and Writes GameEngine Logs.
	 *
	 * @param p_gameEngineLog String of Log message.
	 * @param p_logType       Type of Log.
	 */
	public void setD_gameEngineLog(String p_gameEngineLog, String p_logType) {
		d_currentPhase.getD_gameState().updateLog(p_gameEngineLog, p_logType);
		String l_consoleLogger = p_logType.toLowerCase().equals("phase")
				? "\n************ " + p_gameEngineLog + " ************\n"
				: p_gameEngineLog;
		System.out.println(l_consoleLogger);
	}

	/**
	 * The main method responsible for accepting command from users and redirecting
	 * those to corresponding logical flows.
	 *
	 * @param p_args the program doesn't use default command line arguments
	 */
	public static void main(String[] p_args) {
		GameEngine l_game = new GameEngine();

		l_game.getD_CurrentPhase().getD_gameState().updateLog("Initializing the Game ......" + System.lineSeparator(),
				"start");
		l_game.setD_gameEngineLog("Game Startup Phase", "phase");
		l_game.getD_CurrentPhase().initPhase(d_isTournamentMode);
	}
}
