package Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import Constants.ApplicationConstants;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.Country;
import Models.GameState;
import Models.Order;
import Models.Player;
import Services.MapService;
import Utils.Command;
import Utils.CommonUtil;

/**
 * This is the entry point of the Game and keeps the track of current Game State
 */
public class GameEngineController {
	/**
	 * gameState stores the information about current GamePlay
	 */
	GameState d_gameState = new GameState();
	/**
	 * Map Service instance to handle load, read, parse, edit, and save map file
	 */
	MapService d_mapService = new MapService();
	/**
	 * Player Service instance to edit players and issue orders
	 */
	Player d_playerService = new Player();

	/**
	 * getD_gameState is a getter method to get current game state
	 *
	 * @return the current game state
	 */
	public GameState getD_gameState() {
		return d_gameState;
	}

	/**
	 * main method responsible for accepting command from users and redirecting
	 * those to corresponding logical flows
	 *
	 * @param p_args the program doesn't use command line arguments
	 */
	public static void main(String[] p_args) {
		GameEngineController l_game = new GameEngineController();

		l_game.initGamePlay();
	}

	/**
	 * @param p_enteredCommand command entered by the user in CLI
	 * @throws InvalidMap     indicates map is invalid
	 * @throws InvalidCommand indicates command is invalid
	 */
	public void handleCommand(String p_enteredCommand) throws InvalidMap, InvalidCommand, IOException {
		Command l_command = new Command(p_enteredCommand);
		String l_rootCommand = l_command.getRootCommand();
		boolean l_isMapLoaded = d_gameState.getD_map() != null;

		switch (l_rootCommand) {
		case "editmap": {
			performMapEdit(l_command);
			break;
		}
		case "editcontinent": {
			if (!l_isMapLoaded) {
				System.out.println("Can not Edit Continent, please perform `loadmap` or `editmap` first");
				break;
			}

			performEditContinent(l_command);
			break;
		}
		case "savemap": {
			if (!l_isMapLoaded) {
				System.out.println("No map found to save, Please `loadmap` & `editmap` first");
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
				break;
			}
			performValidateMap(l_command);
			break;
		}
		case "gameplayer": {
			createPlayers(l_command);
			break;
		}
		case "assigncountries": {
			assignCountries(l_command);
			break;
		}
		case "exit": {
			System.out.println("Exit Command Entered");
			System.exit(0);
			break;
		}
		default: {
			System.out.println("Invalid Command");
			break;
		}
		}
	}

	/**
	 * initGamePlay method initiates the CLI to accept commands from user and maps
	 * them to corresponding action handler
	 *
	 */
	private void initGamePlay() {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				System.out.println("Enter Game Commands or type 'exit' for quitting");
				String l_commandEntered = l_reader.readLine();
				handleCommand(l_commandEntered);
			} catch (InvalidCommand | InvalidMap l_exception) {
				System.out.println(l_exception.getMessage());
			} catch (IOException l_ioException) {
				l_ioException.printStackTrace();
			}
		}
	}

	/**
	 * Basic validation of editmap command for checking required arguments and
	 * redirecting control to model for actual processing
	 * 
	 * @param p_command command entered by the user on CLI
	 * @throws IOException    indicates failure in I/O operation
	 * @throws InvalidCommand indicates command is invalid
	 */
	public void performMapEdit(Command p_command) throws IOException, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

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
	 * Basic validation of editcontinent command for checking required arguments and
	 * redirecting control to model for actual processing
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws IOException    indicates failure in I/O operation
	 * @throws InvalidCommand indicates command is invalid
	 */
	public void performEditContinent(Command p_command) throws IOException, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (l_operations_list == null || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					System.out.println("Valid args received");
					d_mapService.editContinent(d_gameState, l_map.get(ApplicationConstants.ARGUMENTS),
							l_map.get(ApplicationConstants.OPERATION));
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
				}
			}
		}
	}

	/**
	 * Basic validation of savemap command for checking required arguments and
	 * redirecting control to model for actual processing
	 * 
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidMap     indicates map is invalid
	 * @throws InvalidCommand indicates command is invalid
	 */
	public void performSaveMap(Command p_command) throws InvalidCommand, InvalidMap {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					boolean l_fileUpdateStatus = d_mapService.saveMap(d_gameState,
							l_map.get(ApplicationConstants.ARGUMENTS));
					if (l_fileUpdateStatus)
						System.out.println("Required changes has been done in map file");
					else
						System.out.println(d_gameState.getError());
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
				}
			}
		}
	}

	/**
	 * Basic validation of loadmap command for checking required arguments and
	 * redirecting control to model for actual processing
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 */
	private void performLoadMap(Command p_command) throws InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_LOADMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					try {
						Models.Map l_mapToLoad = d_mapService.loadMap(d_gameState,
								l_map.get(ApplicationConstants.ARGUMENTS));
						if (l_mapToLoad.Validate()) {
							System.out.println("Map has been loaded successfully. \n");
						} else {
							d_mapService.resetMap(d_gameState);
						}
					} catch (InvalidMap e) {
						d_mapService.resetMap(d_gameState);
					}
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_LOADMAP);
				}
			}
		}
	}

	/**
	 * Basic validation of validatemap command for checking required arguments and
	 * redirecting control to model for actual processing
	 *
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 * @throws InvalidMap     indicates map is invalid
	 */
	private void performValidateMap(Command p_command) throws InvalidMap, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			Models.Map l_currentMap = d_gameState.getD_map();
			if (l_currentMap == null) {
				throw new InvalidMap(ApplicationConstants.INVALID_MAP_ERROR_EMPTY);
			} else {
				if (l_currentMap.Validate()) {
					System.out.println(ApplicationConstants.VALID_MAP);
				}
			}
		} else {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_VALIDATEMAP);
		}
	}

	/**
	 * Basic validation of create game player command for checking required
	 * arguments and redirecting control to model for adding or removing players
	 * 
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 */
	private void createPlayers(Command p_command) throws InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
		if (CommonUtil.isCollectionEmpty(l_operations_list)) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_GAMEPLAYER);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					d_playerService.playerInfo(d_gameState, l_map.get(ApplicationConstants.OPERATION),
							l_map.get(ApplicationConstants.ARGUMENTS));
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_GAMEPLAYER);
				}
			}
		}
	}

	/**
	 * Basic validation of assign countries for checking required arguments and
	 * redirecting control to model for assigning countries to players
	 * 
	 * @param p_command command entered by the user on CLI
	 * @throws InvalidCommand indicates command is invalid
	 * @throws IOException    indicates failure in I/O operation
	 */
	private void assignCountries(Command p_command) throws InvalidCommand, IOException {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
		if (CommonUtil.isCollectionEmpty(l_operations_list)) {
			d_playerService.assignCountries(d_gameState);

			while (!CommonUtil.isCollectionEmpty(d_gameState.getD_players())) {
				System.out.println("\n********Starting Main Game Loop***********\n");
				d_playerService.assignArmies(d_gameState);

				while (d_playerService.unassignedArmiesExists(d_gameState.getD_players())) {
					for (Player l_player : d_gameState.getD_players()) {
						if (l_player.getD_noOfUnallocatedArmies() != null && l_player.getD_noOfUnallocatedArmies() != 0)
							l_player.issue_order();
					}
				}
				while (d_playerService.unexecutedOrdersExists(d_gameState.getD_players())) {
					for (Player l_player : d_gameState.getD_players()) {
						Order l_order = l_player.next_order();
						if (l_order != null)
							l_order.execute(d_gameState, l_player);
					}
				}
				this.printMap();
			}
		} else {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_ASSIGNCOUNTRIES);
		}
	}

	/**
	 * temp method added for testing main game loop changes
	 */
	private void printMap() {
		System.out.println("\n*******Updated Map**********\n");
		for (Country p_cont : d_gameState.getD_map().getD_countries()) {
			System.out.println("Country : " + p_cont.getD_countryName() + " is assigned with "
					+ (p_cont.getD_armies() == null ? 0 : p_cont.getD_armies()) + " armies");
		}
	}

}
