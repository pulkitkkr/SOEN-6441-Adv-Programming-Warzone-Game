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
public class OrderExecutionPhase extends Phase{

    /**
     * It's a constructor that init the GameEngine context in Phase class.
     *
     * @param p_gameEngine GameEngine Context
     * @param p_gameState current Game State
     */
    public OrderExecutionPhase(GameEngine p_gameEngine, GameState p_gameState){
        super(p_gameEngine, p_gameState);
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

            while (!CommonUtil.isCollectionEmpty(d_gameState.getD_players())) {
                System.out.println("Press Y/y if you want to continue for next turn or else press N/n");
                BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

                try {
                    String l_continue = l_reader.readLine();

                    if (l_continue.equalsIgnoreCase("N")) {
                        break;
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
    protected void performEditNeighbour(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performEditCountry(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
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
    protected void performEditContinent(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }
}
