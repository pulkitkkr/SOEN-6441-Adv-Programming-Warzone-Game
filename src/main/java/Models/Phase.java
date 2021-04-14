package Models;

import java.io.IOException;

import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Services.MapService;
import Services.PlayerService;
import Utils.Command;

/**
 * This Interface enforces the method requirement for Each Game Phase.
 */
public abstract class Phase {
	/**
	 * d_gameState stores the information about current GamePlay.
	 */
	GameState d_gameState;

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

	Player d_player = new Player();

	/**
	 * it is a flag to check if map is loaded.
	 */
	boolean l_isMapLoaded;

	Tournament d_tournament = new Tournament();

	/**
	 * Constructor to initialize the value of current game engine.
	 *
	 * @param p_gameEngine game engine instance to update state
	 * @param p_gameState  game engine instance to game state
	 */
	public Phase(GameEngine p_gameEngine, GameState p_gameState) {
		d_gameEngine = p_gameEngine;
		d_gameState = p_gameState;
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
	 * @param p_gameState game state instance to set for phase
	 */
	public void setD_gameState(GameState p_gameState) {
		d_gameState = p_gameState;
	}

	/**
	 * handle command methods handles all state specific commands that can be
	 * entered by user.
	 *
	 * @param p_enteredCommand command entered by the user in CLI
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap     indicates map is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	public void handleCommand(String p_enteredCommand) throws InvalidMap, InvalidCommand, IOException {
		commandHandler(p_enteredCommand, null);
	}

	/**
	 * handle command methods handles all state specific commands that can be
	 * entered by user.
	 *
	 * @param p_enteredCommand command entered by the user in CLI
	 * @param p_player         instance to Player Object
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap     indicates map is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	public void handleCommand(String p_enteredCommand, Player p_player) throws InvalidMap, InvalidCommand, IOException {
		commandHandler(p_enteredCommand, p_player);
	}

	/**
	 * Processes the command entered by user and redirects them to specific phase
	 * implementations.
	 * 
	 * @param p_enteredCommand command entered by the user in CLI
	 * @param p_player         instance to Player Object
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap     indicates map is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	private void commandHandler(String p_enteredCommand, Player p_player)
			throws InvalidMap, InvalidCommand, IOException {
		Command l_command = new Command(p_enteredCommand);
		String l_rootCommand = l_command.getRootCommand();
		l_isMapLoaded = d_gameState.getD_map() != null;

		d_gameState.updateLog(l_command.getD_command(), "command");

		switch (l_rootCommand) {
		case "editmap": {
			performMapEdit(l_command, p_player);
			break;
		}
		case "editcontinent": {
			performEditContinent(l_command, p_player);
			break;
		}
		case "savemap": {
			performSaveMap(l_command, p_player);
			break;
		}
		case "loadmap": {
			performLoadMap(l_command, p_player);
			break;
		}
		case "validatemap": {
			performValidateMap(l_command, p_player);
			break;
		}
		case "editcountry": {
			performEditCountry(l_command, p_player);
			break;
		}
		case "editneighbor": {
			performEditNeighbour(l_command, p_player);
			break;
		}
		case "gameplayer": {
			createPlayers(l_command, p_player);
			break;
		}
		case "assigncountries": {
			performAssignCountries(l_command, p_player, false, d_gameState);
			break;
		}
		case "showmap": {
			performShowMap(l_command, p_player);
			break;
		}
		case "deploy": {
			performCreateDeploy(p_enteredCommand, p_player);
			break;
		}
		case "advance": {
			performAdvance(p_enteredCommand, p_player);
			break;
		}
		case "airlift":
		case "blockade":
		case "negotiate":
		case "bomb": {
			performCardHandle(p_enteredCommand, p_player);
			break;
		}
		case "tournament": {
			tournamentGamePlay(l_command);
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
	 * Handles the Card Commands.
	 *
	 * @param p_enteredCommand String of entered Command
	 * @param p_player         player instance
	 * @throws IOException Io exception
	 */
	protected abstract void performCardHandle(String p_enteredCommand, Player p_player) throws IOException;

	/**
	 * this method handles the show map command.
	 *
	 * @param p_command command entered by user
	 * @param p_player  instance of player object
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap     indicates map is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	protected abstract void performShowMap(Command p_command, Player p_player)
			throws InvalidCommand, IOException, InvalidMap;

	/**
	 * this method handles the advance order in game play.
	 *
	 * @param p_command command entered by user
	 * @param p_player  instance of player object
	 * @throws IOException indicates failure in I/O operation
	 */
	protected abstract void performAdvance(String p_command, Player p_player) throws IOException;

	protected abstract void tournamentGamePlay(Command p_command) throws InvalidCommand, InvalidMap;

	/**
	 * This is the main method executed on phase change.
	 */
	public abstract void initPhase(boolean isTournamentMode);

	/**
	 * This method handles the deploy order in gameplay.
	 *
	 * @param p_command command entered by user
	 * @param p_player  instance of player object
	 * @throws IOException indicates failure in I/O operation
	 */
	protected abstract void performCreateDeploy(String p_command, Player p_player) throws IOException;

	/**
	 * Method to Log and Print if the command can't be executed in current phase.
	 */
	public void printInvalidCommandInState() {
		d_gameEngine.setD_gameEngineLog("Invalid Command in Current State", "effect");
	}

	/**
	 * Basic validation of <strong>assigncountries</strong> for checking required
	 * arguments and redirecting control to model for assigning countries to
	 * players.
	 *
	 * @param p_command command entered by the user on CLI
	 * @param p_player  instance of Player Object
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap     indicates map is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	protected abstract void performAssignCountries(Command p_command, Player p_player, boolean p_isTournamentMode, GameState p_gameState)
			throws InvalidCommand, IOException, InvalidMap;

	/**
	 * Basic validation of <strong>gameplayer</strong> command for checking required
	 * arguments and redirecting control to model for adding or removing players.
	 *
	 * @param p_command command entered by the user on CLI
	 * @param p_player  instance of Player Object
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap     indicates map is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	protected abstract void createPlayers(Command p_command, Player p_player)
			throws InvalidCommand, IOException, InvalidMap;

	/**
	 * Basic validation of <strong>editneighbor</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @param p_player  instance of Player Object
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap     indicates map is invalid
	 * @throws IOException    handles File I/O Exception
	 */
	protected abstract void performEditNeighbour(Command p_command, Player p_player)
			throws InvalidCommand, InvalidMap, IOException;

	/**
	 * Basic validation of <strong>editcountry</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @param p_player  instance of Player Object
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap     indicates map is invalid
	 * @throws IOException    handles File I/O Exception
	 * @throws InvalidMap     indicates map is invalid
	 */
	protected abstract void performEditCountry(Command p_command, Player p_player)
			throws InvalidCommand, InvalidMap, IOException;

	/**
	 * Basic validation of <strong>validatemap</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @param p_player  instance of Player Object
	 * @throws InvalidCommand indicates when command is invalid
	 * @throws InvalidMap     indicates when map is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	protected abstract void performValidateMap(Command p_command, Player p_player)
			throws InvalidMap, InvalidCommand, IOException;

	/**
	 * Basic validation of <strong>loadmap</strong> command for checking required
	 * arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @param p_player  instance of Player Object
	 * @throws InvalidMap     indicates Map Object Validation failure
	 * @throws InvalidCommand indicates when command is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	protected abstract void performLoadMap(Command p_command, Player p_player)
			throws InvalidCommand, InvalidMap, IOException;

	/**
	 * Basic validation of <strong>savemap</strong> command for checking required
	 * arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @param p_player  instance of Player Object
	 * @throws InvalidMap     indicates when map is invalid
	 * @throws InvalidCommand indicates when command is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	protected abstract void performSaveMap(Command p_command, Player p_player)
			throws InvalidCommand, InvalidMap, IOException;

	/**
	 * Basic validation of <strong>editcontinent</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @param p_player  instance of Player Object
	 * @throws IOException    indicates failure in I/O operation
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap     indicates map is invalid
	 */
	protected abstract void performEditContinent(Command p_command, Player p_player)
			throws IOException, InvalidCommand, InvalidMap;

	/**
	 * Basic validation of <strong>editmap</strong> command for checking required
	 * arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @param p_player  instance of Player Object
	 * @throws IOException    indicates when failure in I/O operation
	 * @throws InvalidMap     indicates Map Object Validation failure
	 * @throws InvalidCommand indicates when command is invalid
	 */
	protected abstract void performMapEdit(Command p_command, Player p_player)
			throws IOException, InvalidCommand, InvalidMap;

}
