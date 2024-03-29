package Models;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import Constants.ApplicationConstants;
import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Services.GameService;
import Utils.Command;
import Utils.ExceptionLogHandler;
import Views.MapView;

/**
 * Issue Order Phase implementation for GamePlay using State Pattern.
 */
public class IssueOrderPhase extends Phase {

	/**
	 * Constructor to initialize the value of current game engine.
	 *
	 * @param p_gameEngine game engine instance to update state
	 * @param p_gameState  instance of current game state in GameEngine
	 */
	public IssueOrderPhase(GameEngine p_gameEngine, GameState p_gameState) {
		super(p_gameEngine, p_gameState);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performLoadGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
        askForOrder(p_player);
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
    	if(p_player.getD_cardsOwnedByPlayer().contains(p_enteredCommand.split(" ")[0])) {
    		p_player.handleCardCommands(p_enteredCommand, d_gameState);
    	}
    }

	@Override
	protected void performShowMap(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap {
		MapView l_mapView = new MapView(d_gameState);
		l_mapView.showMap();

		askForOrder(p_player);
	}

	@Override
	protected void performAdvance(String p_command, Player p_player) throws IOException {
		p_player.createAdvanceOrder(p_command, d_gameState);
		d_gameState.updateLog(p_player.getD_playerLog(), "effect");
	}

	@Override
	public void initPhase(boolean p_isTournamentMode) {
		 while (d_gameEngine.getD_CurrentPhase() instanceof IssueOrderPhase) {
	            issueOrders(p_isTournamentMode);
	        }
	}

	@Override
	protected void performCreateDeploy(String p_command, Player p_player) throws IOException {
		p_player.createDeployOrder(p_command);
		d_gameState.updateLog(p_player.getD_playerLog(), "effect");
	}

	/**
	 * Accepts orders from players.
	 *
	 * @param p_isTournamentMode if game is being played in tournament mode
	 */
	protected void issueOrders(boolean p_isTournamentMode){
        // issue orders for each player
        do {
            for (Player l_player : d_gameState.getD_players()) {
				//System.out.println("l_player :" + l_player.getPlayerName());
				if(l_player.getD_coutriesOwned().size()==0){
					l_player.setD_moreOrders(false);
				}
                if (l_player.getD_moreOrders() && !l_player.getPlayerName().equals("Neutral")) {
                    try {
                        l_player.issue_order(this);
                        l_player.checkForMoreOrders(p_isTournamentMode);
                    } catch (InvalidCommand | IOException | InvalidMap l_exception) {
                        d_gameEngine.setD_gameEngineLog(l_exception.getMessage(), "effect");
                    }
                }
            }
        } while (d_playerService.checkForMoreOrders(d_gameState.getD_players()));

        d_gameEngine.setOrderExecutionPhase();
    }

	/**
     * Asks for order commands from user.
     * 
     * @param p_player player for which commands are to be issued
     * @throws InvalidCommand exception if command is invalid
     * @throws IOException  indicates failure in I/O operation
     * @throws InvalidMap indicates failure in using the invalid map
     */
    public void askForOrder(Player p_player) throws InvalidCommand, IOException, InvalidMap{

        String l_commandEntered = p_player.getPlayerOrder(d_gameState);

        if(l_commandEntered == null) return;

        d_gameState.updateLog("(Player: "+p_player.getPlayerName()+") "+ l_commandEntered, "order");
        handleCommand(l_commandEntered, p_player);
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performAssignCountries(Command p_command, Player p_player, boolean isTournamentMode, GameState p_gameState)
			throws InvalidCommand, IOException, InvalidMap {
		printInvalidCommandInState();
		askForOrder(p_player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createPlayers(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap {
		printInvalidCommandInState();
		askForOrder(p_player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performEditNeighbour(Command p_command, Player p_player)
			throws InvalidCommand, InvalidMap, IOException {
		printInvalidCommandInState();
		askForOrder(p_player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performEditCountry(Command p_command, Player p_player)
			throws InvalidCommand, InvalidMap, IOException {
		printInvalidCommandInState();
		askForOrder(p_player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performValidateMap(Command p_command, Player p_player)
			throws InvalidMap, InvalidCommand, IOException {
		printInvalidCommandInState();
		askForOrder(p_player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
		printInvalidCommandInState();
		askForOrder(p_player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performSaveMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
		printInvalidCommandInState();
		askForOrder(p_player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performEditContinent(Command p_command, Player p_player)
			throws IOException, InvalidCommand, InvalidMap {
		printInvalidCommandInState();
		askForOrder(p_player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
		printInvalidCommandInState();
		askForOrder(p_player);
	}

	@Override
	protected void tournamentGamePlay(Command p_enteredCommand) {
		// printInvalidCommandInState();
	}
}
