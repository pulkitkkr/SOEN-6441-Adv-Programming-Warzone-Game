package Controllers;

import Constants.ApplicationConstants;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.GameState;
import Models.Order;
import Models.Player;
import Services.MapService;
import Services.PlayerService;
import Utils.Command;
import Utils.CommonUtil;
import Utils.ExceptionLogHandler;
import Views.MapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * This is the entry point of the Game and keeps the track of current Game State.
 */
public class GameEngineController {

	/**
	 * d_gameState stores the information about current GamePlay.
	 */
	GameState d_gameState = new GameState();

	/**
	 * d_mapService instance is used to handle load, read, parse, edit, and save map file.
	 */
	MapService d_mapService = new MapService();

	/**
	 * Player Service instance to edit players and issue orders.
	 */
	PlayerService d_playerService = new PlayerService();

	/**
	 * getD_gameState is a getter method to get current game state.
	 *
	 * @return the current game state
	 */
	public GameState getD_gameState() {
		return d_gameState;
	}

	/**
	 * The main method responsible for accepting command from users and redirecting
	 * those to corresponding logical flows.
	 *
	 * @param p_args the program doesn't use default command line arguments
	 */
	public static void main(String[] p_args) {
		GameEngineController l_game = new GameEngineController();

		l_game.getD_gameState().updateLog("Initializing the Game ......"+System.lineSeparator(), "start");
		l_game.initGamePlay();
	}

	/**
	 * Handle the commands.
	 *
	 * @param p_enteredCommand command entered by the user in CLI
	 * @throws InvalidMap indicates map is invalid
	 * @throws InvalidCommand indicates command is invalid
	 * @throws IOException indicates failure in I/O operation
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
					System.out.println("Can not Edit Continent, please perform `editmap` first");
					d_gameState.updateLog("Can not Edit Continent, please perform `editmap` first", "effect");
					break;
				}
				performEditContinent(l_command);
				break;
			}
			case "savemap": {
				if (!l_isMapLoaded) {
					System.out.println("No map found to save, Please `editmap` first");
					d_gameState.updateLog("No map found to save, Please `editmap` first", "effect");
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
					System.out.println("No map found to validate, Please `loadmap` & `editmap` first");
					d_gameState.updateLog("No map found to validate, Please `loadmap` & `editmap` first", "effect");
					break;
				}
				performValidateMap(l_command);
				break;
			}
			case "editcountry": {
				if (!l_isMapLoaded) {
					System.out.println("Can not Edit Country, please perform `editmap` first");
					d_gameState.updateLog("Can not Edit Country, please perform `editmap` first", "effect");
					break;
				}
				performEditCountry(l_command);
				break;
			}
			case "editneighbor": {
				if (!l_isMapLoaded) {
					System.out.println("Can not Edit Neighbors, please perform `editmap` first");
					d_gameState.updateLog("Can not Edit Neighbors, please perform `editmap` first", "effect");
					break;
				}
				performEditNeighbour(l_command);
				break;
			}
			case "gameplayer": {
				if (!l_isMapLoaded) {
					System.out.println("No map found, Please `loadmap` before adding game players");
					d_gameState.updateLog("No map found, Please `loadmap` before adding game players", "effect");
					break;
				}
				createPlayers(l_command);
				break;
			}
			case "assigncountries": {
				assignCountries(l_command);
				break;
			}
			case "showmap": {
				MapView l_mapView = new MapView(d_gameState);
				l_mapView.showMap();
				break;
			}
			case "exit": {
				System.out.println("Exit Command Entered");
				d_gameState.updateLog("Exit Command Entered, Game Ends!", "effect");
				System.exit(0);
				break;
			}
			default: {
				System.out.println("Invalid Command");
				d_gameState.updateLog("Invalid Command", "effect");
				break;
			}
		}
	}

	/**
	 * This method initiates the CLI to accept commands from user and maps them to corresponding action handler.
	 */
	private void initGamePlay() {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			try {
				System.out.println("Enter Game Commands or type 'exit' for quitting");
				String l_commandEntered = l_reader.readLine();

				handleCommand(l_commandEntered);
			} catch (InvalidCommand | InvalidMap | IOException l_exception) {
				d_gameState.updateLog(l_exception.getMessage(), "effect");
				System.out.println(l_exception.getMessage());
			}
		}
	}

	/**
	 * Basic validation of <strong>editmap</strong> command for checking required
	 * arguments and redirecting control to model for actual processing.
	 * 
	 * @param p_command command entered by the user on CLI
	 * @throws IOException indicates when failure in I/O operation
	 * @throws InvalidCommand indicates when command is invalid
	 */
	public void performMapEdit(Command p_command) throws IOException, InvalidCommand, InvalidMap {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
		if (l_operations_list == null || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					d_mapService.editMap(d_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITMAP);
				}
			}
		}
	}

	/**
	 * Basic validation of <strong>editcontinent</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws IOException indicates failure in I/O operation
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap indicates map is invalid
	 */
	public void performEditContinent(Command p_command) throws IOException, InvalidCommand, InvalidMap {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
		if (l_operations_list == null || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					d_mapService.editFunctions(d_gameState, l_map.get(ApplicationConstants.ARGUMENTS),
							l_map.get(ApplicationConstants.OPERATION), 1);
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
				}
			}
		}
	}

	/**
	 * Basic validation of <strong>savemap</strong> command for checking required
	 * arguments and redirecting control to model for actual processing.
	 * 
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidMap indicates when map is invalid
	 * @throws InvalidCommand indicates when command is invalid
	 */
	public void performSaveMap(Command p_command) throws InvalidCommand, InvalidMap {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					boolean l_fileUpdateStatus = d_mapService.saveMap(d_gameState,
							l_map.get(ApplicationConstants.ARGUMENTS));
					if (l_fileUpdateStatus) {
						System.out.println("Required changes has been done in map file");
						d_gameState.updateLog("Required changes have been made in map file", "effect");
					} else
						System.out.println(d_gameState.getError());
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
				}
			}
		}
	}

	/**
	 * Basic validation of <strong>loadmap</strong> command for checking required arguments and
	 * redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates when command is invalid
	 */
	private void performLoadMap(Command p_command) throws InvalidCommand, InvalidMap {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
		boolean l_flagValidate = false;

		Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_LOADMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					// Loads the map if it is valid or resets the game state
					Models.Map l_mapToLoad = d_mapService.loadMap(d_gameState,
							l_map.get(ApplicationConstants.ARGUMENTS));
					if (l_mapToLoad.Validate()) {
						l_flagValidate = true;
						System.out.println("Map has been loaded successfully. \n");
						d_gameState.updateLog(l_map.get(ApplicationConstants.ARGUMENTS)+ " has been loaded to start the game", "effect" );
					} else {
						d_mapService.resetMap(d_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
					}
					if(!l_flagValidate){
						d_mapService.resetMap(d_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
					}
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_LOADMAP);
				}
			}
		}
	}

	/**
	 * Basic validation of <strong>validatemap</strong> command for checking required arguments and
	 * redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates when command is invalid
	 * @throws InvalidMap indicates when map is invalid
	 */
	private void performValidateMap(Command p_command) throws InvalidMap, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			Models.Map l_currentMap = d_gameState.getD_map();
			if (l_currentMap == null) {
				throw new InvalidMap(ApplicationConstants.INVALID_MAP_ERROR_EMPTY);
			} else {
				if (l_currentMap.Validate()) {
					d_gameState.updateLog("Entered Map is Valid!", "effect");
					System.out.println(ApplicationConstants.VALID_MAP);
				} else {
					throw new InvalidMap("Failed to Validate map!");
				}
			}
		} else {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_VALIDATEMAP);
		}
	}

	/**
	 * Basic validation of <strong>editcountry</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap indicates map is invalid
	 */
	public void performEditCountry(Command p_command) throws InvalidCommand, InvalidMap, IOException {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					d_mapService.editFunctions(d_gameState, l_map.get(ApplicationConstants.OPERATION),
							l_map.get(ApplicationConstants.ARGUMENTS), 2);
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
				}
			}
		}
	}

	/**
	 * Basic validation of <strong>editneighbor</strong> command for checking
	 * required arguments and redirecting control to model for actual processing.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap indicates map is invalid
	 */
	public void performEditNeighbour(Command p_command) throws InvalidCommand, InvalidMap, IOException {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					d_mapService.editFunctions(d_gameState, l_map.get(ApplicationConstants.OPERATION),
							l_map.get(ApplicationConstants.ARGUMENTS), 3);
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
				}
			}
		}
	}

	/**
	 * Basic validation of <strong>gameplayer</strong> command for checking required
	 * arguments and redirecting control to model for adding or removing players.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 */
	private void createPlayers(Command p_command) throws InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
		if (CommonUtil.isCollectionEmpty(l_operations_list)) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_GAMEPLAYER);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					d_playerService.updatePlayers(d_gameState, l_map.get(ApplicationConstants.OPERATION),
							l_map.get(ApplicationConstants.ARGUMENTS));
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_GAMEPLAYER);
				}
			}
		}
	}

	/**
	 * Basic validation of <strong>assigncountries</strong> for checking required
	 * arguments and redirecting control to model for assigning countries to players.
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	public void assignCountries(Command p_command) throws InvalidCommand, IOException {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
		if (CommonUtil.isCollectionEmpty(l_operations_list)) {
			d_playerService.assignCountries(d_gameState);
			d_playerService.assignColors(d_gameState);

			while (!CommonUtil.isCollectionEmpty(d_gameState.getD_players())) {
				System.out.println("\n********Starting Main Game Loop***********\n");

				d_playerService.assignArmies(d_gameState);
				issueOrders();
				executeOrders();

				MapView l_map_view = new MapView(d_gameState, d_gameState.getD_players());
				l_map_view.showMap();

				System.out.println("Press Y/y if you want to continue for next turn or else press N/n");
				BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
				String l_continue = l_reader.readLine();
				if (l_continue.equalsIgnoreCase("N"))
					break;
			}
		} else {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_ASSIGNCOUNTRIES);
		}
	}

	/**
	 * Invokes order execution logic for all unexecuted orders.
	 */
	private void executeOrders() {
		// Executing orders
		while (d_playerService.unexecutedOrdersExists(d_gameState.getD_players())) {
			for (Player l_player : d_gameState.getD_players()) {
				Order l_order = l_player.next_order();
				if (l_order != null)
					l_order.execute(d_gameState, l_player);
			}
		}
	}

	/**
	 * Accepts orders from players.
	 * 
	 * @throws IOException exception in reading inputs from user
	 */
	private void issueOrders() throws IOException {
		// Issuing order for players
		while (d_playerService.unassignedArmiesExists(d_gameState.getD_players())) {
			for (Player l_player : d_gameState.getD_players()) {
				if (l_player.getD_noOfUnallocatedArmies() != null && l_player.getD_noOfUnallocatedArmies() != 0)
					l_player.issue_order();
			}
		}
	}
}
