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
				List<Map<String, String>> l_operations_list = l_command.getOperationsAndArguments();

				if (null == l_operations_list || l_operations_list.isEmpty()) {
					System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_EDITMAP);
				} else {
					for (Map<String, String> l_map : l_operations_list) {
						if (l_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
							l_mapservice.editMap(l_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
						} else {
							System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_EDITMAP);
						}
					}
				}
				break;
			}
			case "editcontinent": {
				List<Map<String, String>> l_operations_list = l_command.getOperationsAndArguments();
				if (null == l_operations_list || l_operations_list.isEmpty()) {
					System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
				} else {
					for (Map<String, String> l_map : l_operations_list) {
						if (l_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
								&& l_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
							System.out.println("Valid args received");
							l_mapservice.editContinent(l_gameState, l_map.get(ApplicationConstants.ARGUMENTS),
									l_map.get(ApplicationConstants.OPERATION));
						} else {
							System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
						}
					}
				}
				break;
			}
			case "savemap": {
				List<Map<String, String>> l_operations_list = l_command.getOperationsAndArguments();

				if (null == l_operations_list || l_operations_list.isEmpty()) {
					System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
				} else {
					for (Map<String, String> l_map : l_operations_list) {
						if (l_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
							boolean l_fileUpdateStatus = l_mapservice.writeDataToFile(l_gameState,
									l_map.get(ApplicationConstants.ARGUMENTS));
							if (l_fileUpdateStatus)
								System.out.println("Required changes has been done in map file");
							else
								System.out.println(l_gameState.getError());
						} else {
							System.out.println(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
						}
					}
				}
				break;
			}
			case "Exit": {
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
}
