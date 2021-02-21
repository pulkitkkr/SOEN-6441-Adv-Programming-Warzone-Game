package Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import Constants.ApplicationConstants;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.GameState;
import Services.MapService;
import Utils.Command;

/**
 * This is the entry point of the Game and keeps the track of current Game State
 */
public class GameEngineController {
	/**
	 * gameState stores the information about current GamePlay
	 */
	GameState d_gameState = new GameState();

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
	public static void main(String[] p_args){
		GameEngineController l_game = new GameEngineController();

		l_game.initGamePlay();
	}

	/**
	 * @param p_enteredCommand command entered by the user in CLI
	 * @throws InvalidMap indicates map is invalid
	 * @throws InvalidCommand indicates command is invalid
	 */
	private void handleCommand(String p_enteredCommand) throws InvalidMap, InvalidCommand, IOException {
		Command l_command = new Command(p_enteredCommand);
		MapService l_mapService = new MapService();
		String l_rootCommand = l_command.getRootCommand();

		switch (l_rootCommand) {
			case "editmap": {
				performMapEdit(l_command, l_mapService);
				break;
			}
			case "editcontinent": {
				performEditContinent(l_command, l_mapService);
				break;
			}
			case "savemap": {
				performSaveMap(l_command, l_mapService);
				break;
			}
			case "loadmap": {
				performLoadMap(l_command, l_mapService);
				break;
			}
			case "validatemap": {
				performValidateMap(l_command);
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
	 * initGamePlay method initiates the CLI to accept commands from user and maps them to corresponding action handler
	 *
	 * @throws IOException indicates failure in I/O operation
	 * @throws InvalidMap indicates map is invalid
	 * @throws InvalidCommand indicates command is invalid
	 */
	private void initGamePlay() {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
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
	 * @param p_mapService
	 * @throws IOException indicates failure in I/O operation
	 * @throws InvalidCommand indicates command is invalid
	 */
	public void performMapEdit(Command p_command, MapService p_mapService) throws IOException, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (l_operations_list == null || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					p_mapService.editMap(d_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
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
	 * @param p_command
	 * @param p_mapService
	 * @throws IOException
	 * @throws InvalidCommand
	 */
	public void performEditContinent(Command p_command, MapService p_mapService) throws IOException, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (l_operations_list == null || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					System.out.println("Valid args received");
					p_mapService.editContinent(d_gameState, l_map.get(ApplicationConstants.ARGUMENTS),
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
	 * @param p_command
	 * @param p_mapService
	 * @throws InvalidCommand
	 * @throws IOException
	 * @throws InvalidMap
	 */
	public void performSaveMap(Command p_command, MapService p_mapService)
			throws InvalidCommand, IOException, InvalidMap {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					boolean l_fileUpdateStatus = p_mapService.saveMap(d_gameState,
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
	 * @param p_command
	 * @param p_mapService
	 * @throws InvalidCommand
	 */
	private void performLoadMap(Command p_command, MapService p_mapService)
			throws InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_LOADMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					p_mapService.loadMap(d_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
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
	 * @param p_command
	 * @throws InvalidMap
	 * @throws InvalidCommand
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
}
