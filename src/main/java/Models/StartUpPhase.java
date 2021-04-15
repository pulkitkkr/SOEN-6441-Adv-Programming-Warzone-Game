package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import Constants.ApplicationConstants;
import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Services.GameService;
import Utils.Command;
import Utils.CommonUtil;
import Utils.ExceptionLogHandler;
import Views.MapView;
import Views.TournamentView;

/**
 * Start Up Phase implementation for GamePlay using State Pattern.
 */
public class StartUpPhase extends Phase {

	/**
	 * It's a constructor that init the GameEngine context in Phase class.
	 *
	 * @param p_gameEngine GameEngine Context
	 * @param p_gameState  current Game State
	 */
	public StartUpPhase(GameEngine p_gameEngine, GameState p_gameState) {
		super(p_gameEngine, p_gameState);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performLoadGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_LOADGAME);
        }

        for (Map<String, String> l_map : l_operations_list) {
            if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
                String l_filename = l_map.get(ApplicationConstants.ARGUMENTS);

                try{
                    Phase l_phase= GameService.loadGame(l_filename);

                    this.d_gameEngine.loadPhase(l_phase);
                } catch (ClassNotFoundException l_e) {
                    l_e.printStackTrace();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performSaveGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEGAME);
        }

        for (Map<String, String> l_map : l_operations_list) {
            if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)) {
                String l_filename = l_map.get(ApplicationConstants.ARGUMENTS);
                GameService.saveGame(this, l_filename);
                d_gameEngine.setD_gameEngineLog("Game Saved Successfully to "+l_filename, "effect");
            } else {
                throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEGAME);
            }
        }
    }

    @Override
    protected void performCardHandle(String p_enteredCommand, Player p_player) throws IOException {
        printInvalidCommandInState();
    }

	@Override
	protected void performShowMap(Command p_command, Player p_player) {
		MapView l_mapView = new MapView(d_gameState);
		l_mapView.showMap();
	}

	@Override
	protected void performAdvance(String p_command, Player p_player) {
		printInvalidCommandInState();
	}

	@Override
	protected void performCreateDeploy(String p_command, Player p_player) {
		printInvalidCommandInState();
	}

	/**
	 * {@inheritDoc}
	 */
	public void performMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
		List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

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
	 * {@inheritDoc}
	 */
	public void performEditContinent(Command p_command, Player p_player)
			throws IOException, InvalidCommand, InvalidMap {
		if (!l_isMapLoaded) {
			d_gameEngine.setD_gameEngineLog("Can not Edit Continent, please perform `editmap` first", "effect");
			return;
		}

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
	 * {@inheritDoc}
	 */
	public void performSaveMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap {
		if (!l_isMapLoaded) {
			d_gameEngine.setD_gameEngineLog("No map found to save, Please `editmap` first", "effect");
			return;
		}

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
						d_gameEngine.setD_gameEngineLog("Required changes have been made in map file", "effect");
					} else
						System.out.println(d_gameState.getError());
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_SAVEMAP);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void performLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap {
		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

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
						d_gameState.setD_loadCommand();
						d_gameEngine.setD_gameEngineLog(
								l_map.get(ApplicationConstants.ARGUMENTS) + " has been loaded to start the game",
								"effect");
					} else {
						d_mapService.resetMap(d_gameState, l_map.get(ApplicationConstants.ARGUMENTS));
					}
				} else {
					throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_LOADMAP);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void performValidateMap(Command p_command, Player p_player) throws InvalidMap, InvalidCommand {
		if (!l_isMapLoaded) {
			d_gameEngine.setD_gameEngineLog("No map found to validate, Please `loadmap` & `editmap` first", "effect");
			return;
		}

		List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

		Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
		if (null == l_operations_list || l_operations_list.isEmpty()) {
			Models.Map l_currentMap = d_gameState.getD_map();
			if (l_currentMap == null) {
				throw new InvalidMap(ApplicationConstants.INVALID_MAP_ERROR_EMPTY);
			} else {
				if (l_currentMap.Validate()) {
					d_gameEngine.setD_gameEngineLog(ApplicationConstants.VALID_MAP, "effect");
				} else {
					throw new InvalidMap("Failed to Validate map!");
				}
			}
		} else {
			throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_VALIDATEMAP);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void performEditCountry(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
		if (!l_isMapLoaded) {
			d_gameEngine.setD_gameEngineLog("Can not Edit Country, please perform `editmap` first", "effect");
			return;
		}

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
	 * {@inheritDoc}
	 */
	public void performEditNeighbour(Command p_command, Player p_player)
			throws InvalidCommand, InvalidMap, IOException {
		if (!l_isMapLoaded) {
			d_gameEngine.setD_gameEngineLog("Can not Edit Neighbors, please perform `editmap` first", "effect");
			return;
		}

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
	 * {@inheritDoc}
	 * @throws IOException in case of failure in receiving user input
	 */
	public void createPlayers(Command p_command, Player p_player) throws InvalidCommand, IOException {

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
	 * {@inheritDoc}
	 */
	public void initPhase(boolean isTournamentMode) {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

		while (d_gameEngine.getD_CurrentPhase() instanceof StartUpPhase) {
			try {
				System.out.println("Enter Game Commands or type 'exit' for quitting");
				String l_commandEntered = l_reader.readLine();

				handleCommand(l_commandEntered);
			} catch (InvalidCommand | InvalidMap | IOException l_exception) {
				d_gameEngine.setD_gameEngineLog(l_exception.getMessage(), "effect");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void performAssignCountries(Command p_command, Player p_player, boolean p_istournamentmode,
			GameState p_gameState) throws InvalidCommand {
		if (p_gameState.getD_loadCommand()) {
			List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
			Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
			if (CommonUtil.isCollectionEmpty(l_operations_list) || p_istournamentmode) {
				d_gameEngine.setD_gameState(p_gameState);
				d_gameEngine.setD_isTournamentMode(p_istournamentmode);
				d_playerService.assignCountries(p_gameState);
				d_playerService.assignColors(p_gameState);
				d_playerService.assignArmies(p_gameState);
				d_gameEngine.setIssueOrderPhase(p_istournamentmode);
			} else {
				throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_ERROR_ASSIGNCOUNTRIES);
			}
		} else {
			d_gameEngine.setD_gameEngineLog("Please load a valid map first via loadmap command!", "effect");
		}
	}

	@Override
	protected void tournamentGamePlay(Command p_command) throws InvalidCommand, InvalidMap {

		if (d_gameState.getD_players() != null && d_gameState.getD_players().size() > 1) {
			List<Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
			boolean l_parsingSuccessful = false;
			Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));

			if (CommonUtil.isCollectionEmpty(l_operations_list)
					&& !d_tournament.requiredTournamentArgPresent(l_operations_list, p_command)) {
				throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_TOURNAMENT_MODE);
			} else {
				for (Map<String, String> l_map : l_operations_list) {
					if (p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
							&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
						l_parsingSuccessful = d_tournament.parseTournamentCommand(d_gameState,
								l_map.get(ApplicationConstants.OPERATION), l_map.get(ApplicationConstants.ARGUMENTS),
								d_gameEngine);
						if (!l_parsingSuccessful)
							break;

					} else {
						throw new InvalidCommand(ApplicationConstants.INVALID_COMMAND_TOURNAMENT_MODE);
					}
				}
			}
			if (l_parsingSuccessful) {
				for (GameState l_gameState : d_tournament.getD_gameStateList()) {
					d_gameEngine.setD_gameEngineLog(
							"\nStarting New Game on map : " + l_gameState.getD_map().getD_mapFile() + " .........\n",
							"effect");
					performAssignCountries(new Command("assigncountries"), null, true, l_gameState);

					d_gameEngine.setD_gameEngineLog(
							"\nGame Completed on map : " + l_gameState.getD_map().getD_mapFile() + " .........\n",
							"effect");
				}
				d_gameEngine.setD_gameEngineLog("************ Tournament Completed ************", "effect");
				TournamentView l_tournamentView = new TournamentView(d_tournament);
				l_tournamentView.viewTournament();
			}
		} else {
			d_gameEngine.setD_gameEngineLog("Please add 2 or more players first in the game.", "effect");
		}
	}
}
