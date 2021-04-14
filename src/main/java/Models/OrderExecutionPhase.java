package Models;

import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Utils.Command;
import Utils.CommonUtil;
import Views.MapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Order Execution Phase implementation for GamePlay using State Pattern.
 */
public class OrderExecutionPhase extends Phase {

	/**
	 * It's a constructor that init the GameEngine context in Phase class.
	 *
	 * @param p_gameEngine GameEngine Context
	 * @param p_gameState  current Game State
	 */
	public OrderExecutionPhase(GameEngine p_gameEngine, GameState p_gameState) {
		super(p_gameEngine, p_gameState);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	protected void performLoadGame(Command p_command) throws InvalidCommand, InvalidMap, IOException {

	}

	/**
	 * @inheritDoc
	 */
	@Override
	protected void performSaveGame(Command p_command) throws InvalidCommand, InvalidMap, IOException  {

	}

	@Override
	protected void performCardHandle(String p_enteredCommand, Player p_player) throws IOException {
		printInvalidCommandInState();
	}

	@Override
	protected void performAdvance(String p_command, Player p_player) {
		printInvalidCommandInState();
	}

	@Override
	public void initPhase() {
		while (d_gameEngine.getD_CurrentPhase() instanceof OrderExecutionPhase) {
			executeOrders();

			MapView l_map_view = new MapView(d_gameState);
			l_map_view.showMap();

			if (this.checkEndOftheGame(d_gameState))
				return;

            while (!CommonUtil.isCollectionEmpty(d_gameState.getD_players())) {
                System.out.println("Press Y/y if you want to continue for next turn or else press N/n");
                BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

				try {
					String l_continue = l_reader.readLine();

                    if (l_continue.equalsIgnoreCase("N")) {
                        d_gameEngine.setStartUpPhase();
                    } else if(l_continue.equalsIgnoreCase("Y")){
                        d_playerService.assignArmies(d_gameState);
                        d_gameEngine.setIssueOrderPhase();
                    } else {
                        System.out.println("Invalid Input");
                    }
                } catch (IOException l_e) {
                    System.out.println("Invalid Input");
                }
            }
        }
    }

	/**
	 * Invokes order execution logic for all unexecuted orders.
	 */
	protected void executeOrders() {
		addNeutralPlayer(d_gameState);
		// Executing orders
		d_gameEngine.setD_gameEngineLog("\nStarting Execution Of Orders.....", "start");
		while (d_playerService.unexecutedOrdersExists(d_gameState.getD_players())) {
			for (Player l_player : d_gameState.getD_players()) {
				Order l_order = l_player.next_order();
				if (l_order != null) {
					l_order.printOrder();
					d_gameState.updateLog(l_order.orderExecutionLog(), "effect");
					l_order.execute(d_gameState);
				}
			}
		}
		d_playerService.resetPlayersFlag(d_gameState.getD_players());
	}

	/**
	 * Add neutral player to game state.
	 *
	 * @param p_gameState GameState
	 */
	public void addNeutralPlayer(GameState p_gameState) {
		Player l_player = p_gameState.getD_players().stream()
				.filter(l_pl -> l_pl.getPlayerName().equalsIgnoreCase("Neutral")).findFirst().orElse(null);
		if (CommonUtil.isNull(l_player)) {
			Player l_neutralPlayer = new Player("Neutral");
			l_neutralPlayer.setD_moreOrders(false);
			p_gameState.getD_players().add(l_neutralPlayer);
		} else {
			return;
		}
	}

	@Override
	protected void performShowMap(Command p_command, Player p_player) {
		MapView l_mapView = new MapView(d_gameState);
		l_mapView.showMap();
	}

	@Override
	protected void performCreateDeploy(String p_command, Player p_player) {
		printInvalidCommandInState();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performAssignCountries(Command p_command, Player p_player) throws InvalidCommand, IOException {
		printInvalidCommandInState();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createPlayers(Command p_command, Player p_player) throws InvalidCommand {
		printInvalidCommandInState();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performEditNeighbour(Command p_command, Player p_player)
			throws InvalidCommand, InvalidMap, IOException {
		printInvalidCommandInState();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performEditCountry(Command p_command, Player p_player)
			throws InvalidCommand, InvalidMap, IOException {
		printInvalidCommandInState();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performValidateMap(Command p_command, Player p_player) throws InvalidMap, InvalidCommand {
		printInvalidCommandInState();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap {
		printInvalidCommandInState();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performSaveMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap {
		printInvalidCommandInState();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performEditContinent(Command p_command, Player p_player)
			throws IOException, InvalidCommand, InvalidMap {
		printInvalidCommandInState();
	}

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

	/**
	 * Checks if single player has conquered all countries of the map to indicate end of the game.
	 *
	 * @param p_gameState Current State of the game
	 * @return true if game has to be ended else false
	 */
	protected Boolean checkEndOftheGame(GameState p_gameState) {
		Integer l_totalCountries = p_gameState.getD_map().getD_countries().size();
		for (Player l_player : p_gameState.getD_players()) {
			if (l_player.getD_coutriesOwned().size() == l_totalCountries) {
				d_gameEngine.setD_gameEngineLog("Player : " + l_player.getPlayerName()
						+ " has won the Game by conquering all countries. Exiting the Game .....", "end");
				return true;
			}
		}
		return false;
	}
}
