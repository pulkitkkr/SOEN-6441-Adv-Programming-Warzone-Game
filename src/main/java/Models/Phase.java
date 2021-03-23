package Models;

import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Services.MapService;
import Services.PlayerService;
import Utils.Command;
import Views.MapView;

import java.io.IOException;

/**
 *  This Interface enforces the method requirement for Each Game Phase
 */
public abstract class Phase {
    /**
     * d_gameState stores the information about current GamePlay.
     */
    GameState d_gameState = new GameState();

    /**
     * d_gameState stores the information about current GamePlay.
     */
    GameEngine d_gameEngine;

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
     * Constructor to initialize the value of current game engine
     *
     * @param p_gameEngine game engine instance to update state
     */
    public Phase(GameEngine p_gameEngine){
        d_gameEngine = p_gameEngine;
    }
    /**
     * Shows and Writes GameEngine Logs.
     *
     * @param p_gameEngineLog String of Log message.
     * @param p_logType Type of Log.
     */

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
    public void handleCommand(String p_enteredCommand) throws InvalidMap, InvalidCommand, IOException {
        Command l_command = new Command(p_enteredCommand);
        String l_rootCommand = l_command.getRootCommand();
        boolean l_isMapLoaded = d_gameState.getD_map() != null;

        d_gameState.updateLog(l_command.getD_command(), "command");

        switch (l_rootCommand) {
            case "editmap": {
                performMapEdit(l_command);
                break;
            }
            case "editcontinent": {
                if (!l_isMapLoaded) {
                    d_gameEngine.setD_gameEngineLog("Can not Edit Continent, please perform `editmap` first", "effect");
                    break;
                }
                performEditContinent(l_command);
                break;
            }
            case "savemap": {
                if (!l_isMapLoaded) {
                    d_gameEngine.setD_gameEngineLog("No map found to save, Please `editmap` first", "effect");
                    break;
                }

                performSaveMap(l_command);
                break;
            }
            case "loadmap": {
                performLoadMap(l_command);
                break;
            }
            case "validatemap": {
                if (!l_isMapLoaded) {
                    d_gameEngine.setD_gameEngineLog("No map found to validate, Please `loadmap` & `editmap` first", "effect");
                    break;
                }
                performValidateMap(l_command);
                break;
            }
            case "editcountry": {
                if (!l_isMapLoaded) {
                    d_gameEngine.setD_gameEngineLog("Can not Edit Country, please perform `editmap` first", "effect");
                    break;
                }
                performEditCountry(l_command);
                break;
            }
            case "editneighbor": {
                if (!l_isMapLoaded) {
                    d_gameEngine.setD_gameEngineLog("Can not Edit Neighbors, please perform `editmap` first", "effect");
                    break;
                }
                performEditNeighbour(l_command);
                break;
            }
            case "gameplayer": {
                if (!l_isMapLoaded) {
                    d_gameEngine.setD_gameEngineLog("No map found, Please `loadmap` before adding game players", "effect");
                    break;
                }
                createPlayers(l_command);
                break;
            }
            case "assigncountries": {
                performAssignCountries(l_command);
                break;
            }
            case "showmap": {
                MapView l_mapView = new MapView(d_gameState);
                l_mapView.showMap();
                break;
            }
            case "exit": {
                d_gameEngine.setD_gameEngineLog("Exit Command Entered, Game Ends!", "effect");
                System.exit(0);
                break;
            }
            default: {
                d_gameEngine.setD_gameEngineLog("Invalid Command", "effect");
                break;
            }
        }
    }

    public void printInvalidCommandInState(){
        d_gameEngine.setD_gameEngineLog("Invalid Command in State", "effect");
    }



    /**
     * Invokes order execution logic for all unexecuted orders.
     */
    private void executeOrders() {
        // Executing orders
        d_gameEngine.setD_gameEngineLog("\n********** Starting Execution Of Orders ***********", "start");
        while (d_playerService.unexecutedOrdersExists(d_gameState.getD_players())) {
            for (Player l_player : d_gameState.getD_players()) {
                Order l_order = l_player.next_order();
                if (l_order != null) {
                    l_order.printOrder();
                    d_gameState.updateLog(l_order.orderExecutionLog(), "effect");
                    l_order.execute(d_gameState);
                }
            }
        }
        d_playerService.resetPlayersOrdersFlag(d_gameState.getD_players());
    }

    /**
     * Accepts orders from players.
     *
     * @throws IOException exception in reading inputs from user
     */
    private void issueOrders() throws IOException, InvalidCommand{
        // Issuing order for players
        do {
            for (Player l_player : d_gameState.getD_players()) {
                if (l_player.getD_moreOrders())
                    l_player.issue_order(d_gameState);
            }
        } while (d_playerService.checkForMoreOrders(d_gameState.getD_players()));
    }

    protected abstract void performAssignCountries(Command p_command) throws InvalidCommand, IOException;

    protected abstract void createPlayers(Command p_command) throws InvalidCommand;

    protected abstract void performEditNeighbour(Command p_command) throws InvalidCommand, InvalidMap, IOException;

    protected abstract void performEditCountry(Command p_command) throws InvalidCommand, InvalidMap, IOException;

    protected abstract void performValidateMap(Command p_command) throws InvalidMap, InvalidCommand;

    protected abstract void performLoadMap(Command p_command) throws InvalidCommand, InvalidMap;

    protected abstract void performSaveMap(Command p_command) throws InvalidCommand, InvalidMap;

    protected abstract void performEditContinent(Command p_command) throws IOException, InvalidCommand, InvalidMap;

    protected abstract void performMapEdit(Command p_command) throws IOException, InvalidCommand, InvalidMap;

}
