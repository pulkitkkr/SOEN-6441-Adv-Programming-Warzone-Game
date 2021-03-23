package Models;

import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Utils.Command;

import java.io.IOException;

/**
 * Issue Order Phase implementation for GamePlay using State Pattern.
 */
public class IssueOrderPhase extends Phase{

    /**
     * It's a constructor that init the GameEngine context in Phase class.
     *
     * @param p_gameEngine GameEngine Context
     */
    public IssueOrderPhase(GameEngine p_gameEngine){
        super(p_gameEngine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performAssignCountries(Command p_command) throws InvalidCommand, IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createPlayers(Command p_command) throws InvalidCommand {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performEditNeighbour(Command p_command) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performEditCountry(Command p_command) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performValidateMap(Command p_command) throws InvalidMap, InvalidCommand {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performLoadMap(Command p_command) throws InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performSaveMap(Command p_command) throws InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performEditContinent(Command p_command) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performMapEdit(Command p_command) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }
}
