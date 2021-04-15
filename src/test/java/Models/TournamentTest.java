package Models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Utils.Command;

public class TournamentTest {
	/**
	 * First Player.
	 */
	Player d_player1;

	/**
	 * Second Player.
	 */
	Player d_player2;

	/**
	 * Game State.
	 */
	GameState d_gameState;

	/**
	 * Setup before each test case.
	 * 
	 * @throws InvalidMap Invalid Map
	 */
	@Before
	public void setup() throws InvalidMap {
		d_gameState = new GameState();
		d_player1 = new Player("a");
		d_player1.setStrategy(new RandomPlayer());
		d_player2 = new Player("b");
		d_player2.setStrategy(new RandomPlayer());

		d_gameState.setD_players(Arrays.asList(d_player1, d_player2));
	}

	/**
	 * Tests tournament command in case of invalid map arguments passed.
	 * 
	 * @throws InvalidCommand invalid command passed
	 * @throws InvalidMap     invalid map name passed
	 */
	@Test
	public void testInvalidMapArgs() throws InvalidMap, InvalidCommand {
		Tournament l_tournament = new Tournament();
		assertFalse(l_tournament.parseTournamentCommand(d_gameState, "M",
				"test.map test123.map canada.map conquest.map swiss.map europe.map", new GameEngine()));
	}
	/**
	 * Tests tournament command in case of invalid player arguments passed.
	 * 
	 * @throws InvalidCommand invalid command passed
	 * @throws InvalidMap     invalid map name passed
	 */
	@Test
	public void testInvalidPlayerStrategiesArgs() throws InvalidMap, InvalidCommand {
		Tournament l_tournament = new Tournament();
		assertFalse(l_tournament.parseTournamentCommand(d_gameState, "P",
				"Random Human", new GameEngine()));
	}
	/**
	 * Tests tournament command in case of invalid game arguments passed.
	 * 
	 * @throws InvalidCommand invalid command passed
	 * @throws InvalidMap     invalid map name passed
	 */
	@Test
	public void testInvalidNoOfGamesArgs() throws InvalidMap, InvalidCommand {
		Tournament l_tournament = new Tournament();
		assertFalse(l_tournament.parseTournamentCommand(d_gameState, "G",
				"6", new GameEngine()));
	}
	/**
	 * Tests tournament command in case of invalid turns arguments passed.
	 * 
	 * @throws InvalidCommand invalid command passed
	 * @throws InvalidMap     invalid map name passed
	 */
	@Test
	public void testInvalidNoOfTurnsArgs() throws InvalidMap, InvalidCommand {
		Tournament l_tournament = new Tournament();
		assertFalse(l_tournament.parseTournamentCommand(d_gameState, "D",
				"60", new GameEngine()));
	}

	/**
	 * Checks if valid tournament command is passed and plays the tournament.
	 * 
	 * @throws InvalidCommand invalid command passed
	 * @throws InvalidMap     invalid map name passed
	 */
	@Test
	public void testValidTournament() throws InvalidMap, InvalidCommand {
		StartUpPhase l_startUpPhase = new StartUpPhase(new GameEngine(), d_gameState);
		Tournament l_tournament = new Tournament();
		GameEngine l_gameEngine = new GameEngine();
		l_tournament.parseTournamentCommand(d_gameState, "M",
				"test.map canada.map", l_gameEngine);
		l_tournament.parseTournamentCommand(d_gameState, "P",
				"Aggressive Random", l_gameEngine);
		l_tournament.parseTournamentCommand(d_gameState, "G",
				"3", l_gameEngine);
		l_tournament.parseTournamentCommand(d_gameState, "D",
				"11", l_gameEngine);
		
		assertEquals(l_tournament.getD_gameStateList().size(), 6);
		assertEquals(l_tournament.getD_gameStateList().get(0).getD_map().getD_mapFile(), "test.map");
		assertEquals(l_tournament.getD_gameStateList().get(1).getD_map().getD_mapFile(), "canada.map");
		
		assertEquals(l_tournament.getD_gameStateList().get(0).getD_players().size(), 2);
		assertEquals(l_tournament.getD_gameStateList().get(0).getD_maxnumberofturns(), 11);
		
		l_startUpPhase.tournamentGamePlay(new Command("tournament -M canada.map conquest.map -P Benevolent Random -G 2 -D 10"));
	}
}
