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
 *  This Interface enforces the method requirement for Each Game Phase.
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
     * it is a flag to check if map is loaded.
     */
    boolean l_isMapLoaded;

    /**
     * Constructor to initialize the value of current game engine.
     *
     * @param p_gameEngine game engine instance to update state
     */
    public Phase(GameEngine p_gameEngine){
        d_gameEngine = p_gameEngine;
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
     * setD_gameState is a setter method for current game state.
     *
     */
    public void setD_gameState(GameState p_gameState) {
        d_gameState = p_gameState;
    }

    /**
     * handle command methods handles all state specific commands that can be entered by user.
     *
     * @param p_enteredCommand command entered by the user in CLI
     */
    public void handleCommand(String p_enteredCommand) throws InvalidMap, InvalidCommand, IOException {
        Command l_command = new Command(p_enteredCommand);
        String l_rootCommand = l_command.getRootCommand();
        l_isMapLoaded = d_gameState.getD_map() != null;

        d_gameState.updateLog(l_command.getD_command(), "command");

        switch (l_rootCommand) {
            case "editmap": {
                performMapEdit(l_command);
                break;
            }
            case "editcontinent": {
                performEditContinent(l_command);
                break;
            }
            case "savemap": {
                performSaveMap(l_command);
                break;
            }
            case "loadmap": {
                performLoadMap(l_command);
                break;
            }
            case "validatemap": {
                performValidateMap(l_command);
                break;
            }
            case "editcountry": {
                performEditCountry(l_command);
                break;
            }
            case "editneighbor": {
                performEditNeighbour(l_command);
                break;
            }
            case "gameplayer": {
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

    /**
     * Method to Log and Print if the command can't be executed in current phase.
     */
    public void printInvalidCommandInState(){
        d_gameEngine.setD_gameEngineLog("Invalid Command in State", "effect");
    }



    /**
     * Invokes order execution logic for all unexecuted orders.
     */
    protected void executeOrders() {
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
    protected void issueOrders() throws IOException, InvalidCommand{
        // Issuing order for players
        do {
            for (Player l_player : d_gameState.getD_players()) {
                if (l_player.getD_moreOrders())
                    l_player.issue_order(d_gameState);
            }
        } while (d_playerService.checkForMoreOrders(d_gameState.getD_players()));
    }

    /**
     * Basic validation of <strong>assigncountries</strong> for checking required
     * arguments and redirecting control to model for assigning countries to
     * players.
     *
     * @param p_command command entered by the user on CLI
     * @throws InvalidCommand indicates command is invalid
     * @throws IOException    indicates failure in I/O operation
     */
    protected abstract void performAssignCountries(Command p_command) throws InvalidCommand, IOException;


    /**
     * Basic validation of <strong>gameplayer</strong> command for checking required
     * arguments and redirecting control to model for adding or removing players.
     *
     * @param p_command command entered by the user on CLI
     * @throws InvalidCommand indicates command is invalid
     */
    protected abstract void createPlayers(Command p_command) throws InvalidCommand;


    /**
     * Basic validation of <strong>editneighbor</strong> command for checking
     * required arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap indicates map is invalid
     * @throws IOException handles File I/O Exception
     */
    protected abstract void performEditNeighbour(Command p_command) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Basic validation of <strong>editcountry</strong> command for checking
     * required arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap indicates map is invalid
     * @throws IOException handles File I/O Exception
     * @throws InvalidMap     indicates map is invalid
     */
    protected abstract void performEditCountry(Command p_command) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Basic validation of <strong>validatemap</strong> command for checking
     * required arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @throws InvalidCommand indicates when command is invalid
     * @throws InvalidMap indicates when map is invalid
     */
    protected abstract void performValidateMap(Command p_command) throws InvalidMap, InvalidCommand;

    /**
     * Basic validation of <strong>loadmap</strong> command for checking required
     * arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @throws InvalidMap indicates Map Object Validation failure
     * @throws InvalidCommand indicates when command is invalid
     */
    protected abstract void performLoadMap(Command p_command) throws InvalidCommand, InvalidMap;

    /**
     * Basic validation of <strong>savemap</strong> command for checking required
     * arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @throws InvalidMap     indicates when map is invalid
     * @throws InvalidCommand indicates when command is invalid
     */
    protected abstract void performSaveMap(Command p_command) throws InvalidCommand, InvalidMap;

    /**
     * Basic validation of <strong>editcontinent</strong> command for checking
     * required arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @throws IOException    indicates failure in I/O operation
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap     indicates map is invalid
     */
    protected abstract void performEditContinent(Command p_command) throws IOException, InvalidCommand, InvalidMap;


    /**
     * Basic validation of <strong>editmap</strong> command for checking required
     * arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @throws IOException indicates when failure in I/O operation
     * @throws InvalidMap indicates Map Object Validation failure
     * @throws InvalidCommand indicates when command is invalid
     */
    protected abstract void performMapEdit(Command p_command) throws IOException, InvalidCommand, InvalidMap;

}
