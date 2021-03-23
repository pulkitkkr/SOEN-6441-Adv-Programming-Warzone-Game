package Models;

import Services.MapService;
import Services.PlayerService;

/**
 *  This Interface enforces the method requirement for Each Game Phase
 */
public abstract class Phase {
    /**
     * d_gameState stores the information about current GamePlay.
     */
    GameState d_gameState = new GameState();

    /**
     * d_mapService instance is used to handle load, read, parse, edit, and save map
     * file.
     */
    MapService d_mapService = new MapService();

    /**
     * Player Service instance to edit players and issue orders.
     */
    PlayerService d_playerService = new PlayerService();

    /**
     * Shows and Writes GameEngine Logs.
     *
     * @param p_gameEngineLog String of Log message.
     * @param p_logType Type of Log.
     */
    public void setD_gameEngineLog(String p_gameEngineLog, String p_logType) {
        d_gameState.updateLog(p_gameEngineLog, p_logType);
        System.out.println(p_gameEngineLog);
    }

    /**
     * getD_gameState is a getter method to get current game state.
     *
     * @return the current game state
     */
    public GameState getD_gameState() {
        return d_gameState;
    }

    /**
     * handle command methods handles all state specific commands that can be entered by user
     *
     * @param p_enteredCommand command entered by the user in CLI
     */
    public void handleCommand(String p_enteredCommand) throws Exception{

    };
}
