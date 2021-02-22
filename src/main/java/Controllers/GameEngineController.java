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

public class GameEngineController {
	/**
	 * main method responsible for accepting command from users and redirecting
	 * those to corresponding logical flows
	 * 
	 * @param p_args main method args
	 * @throws IOException handles I/O
	 * @throws InvalidMap handles Invalid Map
	 */
	public static void main(String[] p_args) throws IOException, InvalidMap {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
		GameState l_gameState = new GameState();

		while (true) {
			try {
				System.out.println("Enter Game Commands or type Exit for quitting");
				String l_commandEntered = l_reader.readLine();
				System.out.println("Command you have entered : " + l_commandEntered);

				Command l_command = new Command(l_commandEntered);
				MapService l_mapService = new MapService();
				String l_rootCommand = l_command.getRootCommand();

				switch (l_rootCommand) {
				case "editmap": {
					performMapEdit(l_command, l_mapService, l_gameState);
					break;
				}
				case "editcontinent": {
					performEditContinent(l_command, l_mapService, l_gameState);
					break;
				}
				case "savemap": {
					performSaveMap(l_command, l_gameState, l_mapService);
					break;
				}
				case "loadmap": {
					performLoadMap(l_gameState, l_command, l_mapService);
					break;
				}
				case "validatemap": {
					performValidateMap(l_gameState, l_command);
					break;
				}
				case "editcountry" : {
					performEditCountry(l_command, l_mapService, l_gameState);
					break;
				}
				case "editneighbor" : {
						performEditNeighbour(l_command, l_mapService, l_gameState);
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
	 * @param p_command Command Entered
	 * @param p_mapService Map Service Object
	 * @param p_gameState Current GameState
	 * @throws IOException Handles I/O
	 * @throws InvalidCommand Handles Invalid command
	 */
	public static void performMapEdit(Command p_command, MapService p_mapService, GameState p_gameState)
			throws IOException, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					p_mapService.editMap(p_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
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
	 * @param p_command Command Entered
	 * @param p_mapService Map Service Object
	 * @param p_gameState Current Game State
	 * @throws IOException handles I/O
	 * @throws InvalidCommand handles Invalid Command
	 */
	public static void performEditContinent(Command p_command, MapService p_mapService, GameState p_gameState)
			throws IOException, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					System.out.println("Valid args received");
					p_mapService.editContinent(p_gameState, l_map.get(ApplicationConstants.ARGUMENTS),
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
	 * @param p_command Command Entered
	 * @param p_gameState Current GameState
	 * @param p_mapService Map Service Object
	 * @throws InvalidCommand handles invalid command
	 * @throws IOException handles I/O
	 * @throws InvalidMap handles Invalid Maps
	 */
	public static void performSaveMap(Command p_command, GameState p_gameState, MapService p_mapService)
			throws InvalidCommand, IOException, InvalidMap {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					boolean l_fileUpdateStatus = p_mapService.saveMap(p_gameState,
							l_map.get(ApplicationConstants.ARGUMENTS));
					if (l_fileUpdateStatus)
						System.out.println("Required changes has been done in map file");
					else
						System.out.println(p_gameState.getError());
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
	 * @param p_gameState Current GameState
	 * @param p_command Command Entered
	 * @param p_mapService MapService Object
	 * @throws InvalidCommand Handles Invalid Command
	 */
	private static void performLoadMap(GameState p_gameState, Command p_command, MapService p_mapService)
			throws InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_LOADMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					p_mapService.loadMap(p_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
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
	 * @param p_gameState Current GameState
	 * @param p_command Command Entered
	 * @throws InvalidMap Handles Invalid Map
	 * @throws InvalidCommand Handles Invalid Commands
	 */
	private static void performValidateMap(GameState p_gameState, Command p_command) throws InvalidMap, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			Models.Map l_currentMap = p_gameState.getD_map();
			if (l_currentMap == null) {
				throw new InvalidMap(ApplicationConstants.INVALID_MAP_ERROR_EMPTY);
			} else {
				if (l_currentMap.Validate()) {
					System.out.println(ApplicationConstants.VALID_MAP);
				}else{
					System.out.println("Failed to Validate map!");
				}
			}

		} else {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_VALIDATEMAP);
		}
	}

	/**
	 * Control Logic for Edit Map performed by MapService
	 * @param p_command command entered
	 * @param p_mapService MapService Object
	 * @param p_gameState Current GameState
	 * @throws IOException Handles I/O
	 * @throws InvalidCommand Handles Invalid Commands
	 */
	public static void performEditCountry(Command p_command, MapService p_mapService, GameState p_gameState)
			throws IOException, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					System.out.println("Valid args received");
					p_mapService.editCountry(p_gameState, l_map.get(ApplicationConstants.OPERATION),
							l_map.get(ApplicationConstants.ARGUMENTS));
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
				}
			}
		}
	}

	public static void performEditNeighbour(Command p_command, MapService p_mapService, GameState p_gameState)
			throws IOException, InvalidCommand {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					System.out.println("Valid args received");
					p_mapService.editNeighbour(p_gameState, l_map.get(ApplicationConstants.OPERATION),
							l_map.get(ApplicationConstants.ARGUMENTS));
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
				}
			}
		}
	}
}
