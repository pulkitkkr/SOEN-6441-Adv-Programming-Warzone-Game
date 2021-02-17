package Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import Constants.ApplicationConstants;
import Models.GameState;
import Services.MapService;
import Utils.Command;

public class GameEngineController {
	public static void main(String[] p_args) throws IOException {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
		GameState l_gameState = new GameState();

		while (true) {
			System.out.println("Enter Game Commands or type Exit for quitting");
			String l_commandEntered = l_reader.readLine();
			System.out.println("Command you have entered : " + l_commandEntered);

			Command l_command = new Command(l_commandEntered);
			MapService l_mapservice = new MapService();
			String l_rootCommand = l_command.getRootCommand();

			switch (l_rootCommand) {
			case "editmap": {
				performMapEdit(l_command, l_mapservice, l_gameState);
				break;
			}
			case "editcontinent": {
				performEditContinent(l_command, l_mapservice, l_gameState);
				break;
			}
			case "savemap": {
				performSaveMap(l_command, l_gameState, l_mapservice);
				break;
			}
			case "loadmap": {
				performLoadMap(l_gameState, l_command, l_mapservice);
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
	}

	private static void performMapEdit(Command p_command, MapService p_mapservice, GameState p_gameState)
			throws IOException {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_EDITMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					p_mapservice.editMap(p_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
				} else {
					System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_EDITMAP);
				}
			}
		}
	}

	private static void performEditContinent(Command p_command, MapService p_mapservice, GameState p_gameState)
			throws IOException {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
						&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
					System.out.println("Valid args received");
					p_mapservice.editContinent(p_gameState, l_map.get(ApplicationConstants.ARGUMENTS),
							l_map.get(ApplicationConstants.OPERATION));
				} else {
					System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
				}
			}
		}
	}

	private static void performSaveMap(Command p_command, GameState p_gameState, MapService p_mapservice) {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					boolean l_fileUpdateStatus = p_mapservice.saveMap(p_gameState,
							l_map.get(ApplicationConstants.ARGUMENTS));
					if (l_fileUpdateStatus)
						System.out.println("Required changes has been done in map file");
					else
						System.out.println(p_gameState.getError());
				} else {
					System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
				}
			}
		}
	}

	private static void performLoadMap(GameState p_gameState, Command p_command, MapService p_mapservice) {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		if (null == l_operations_list || l_operations_list.isEmpty()) {
			System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_LOADMAP);
		} else {
			for (Map<String, String> l_map : l_operations_list) {
				if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
					p_mapservice.loadMap(p_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
				} else {
					System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_LOADMAP);
				}
			}
		}
	}
}
