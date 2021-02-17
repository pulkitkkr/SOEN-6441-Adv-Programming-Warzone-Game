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
	 * @param p_args
	 * @throws IOException
	 * @throws InvalidMap
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
	 * @param p_command
	 * @param p_mapService
	 * @param p_gameState
	 * @throws IOException
	 * @throws InvalidCommand
	 */
	private static void performMapEdit(Command p_command, MapService p_mapService, GameState p_gameState)
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
	 * @param p_command
	 * @param p_mapService
	 * @param p_gameState
	 * @throws IOException
	 * @throws InvalidCommand
	 */
	private static void performEditContinent(Command p_command, MapService p_mapService, GameState p_gameState)
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
	 * @param p_command
	 * @param p_gameState
	 * @param p_mapService
	 * @throws InvalidCommand
	 * @throws IOException
	 * @throws InvalidMap
	 */
	private static void performSaveMap(Command p_command, GameState p_gameState, MapService p_mapService)
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
	 * @param p_gameState
	 * @param p_command
	 * @param p_mapService
	 * @throws InvalidCommand
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
	 * @param p_gameState
	 * @param p_command
	 * @throws InvalidMap
	 * @throws InvalidCommand
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
				}
			}

		} else {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_VALIDATEMAP);
		}
	}
}
