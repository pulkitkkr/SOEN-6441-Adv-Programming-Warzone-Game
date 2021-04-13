package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import Constants.ApplicationConstants;
import Controllers.GameEngine;
import Exceptions.InvalidMap;
import Services.MapService;
import Utils.Command;

public class Tournament {

	MapService d_mapService = new MapService();

	List<GameState> d_gameStateList = new ArrayList<GameState>();

	public List<GameState> getD_gameStateList() {
		return d_gameStateList;
	}

	public void setD_gameStateList(List<GameState> d_gameStateList) {
		this.d_gameStateList = d_gameStateList;
	}

	public boolean parseTournamentCommand(GameState p_gameState, String p_operation, String p_argument,
			GameEngine p_gameEngine) throws InvalidMap {

		// tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D
		// maxnumberofturns

		switch (p_operation) {
		case "M":
			String[] l_listOfMapFiles = p_argument.split(" ");
			int l_mapFilesSize = l_listOfMapFiles.length;

			if (l_mapFilesSize >= 1 & l_mapFilesSize <= 5) {
				for (String l_mapToLoad : l_listOfMapFiles) {
					GameState l_gameState = new GameState();
					// Loads the map if it is valid or resets the game state
					Models.Map l_loadedMap = d_mapService.loadMap(l_gameState, l_mapToLoad);
					if (l_loadedMap.Validate()) {
						l_gameState.setD_loadCommand();
						p_gameEngine.setD_gameEngineLog(l_mapToLoad + " has been loaded to start the game", "effect");
						d_gameStateList.add(l_gameState);
					} else {
						d_mapService.resetMap(l_gameState, l_mapToLoad);
						return false;
					}
				}
			} else {
				p_gameEngine.setD_gameEngineLog(
						"User entered invalid number of maps in command, Range of map :- 1<=map<=5", "effect");
				return false;
			}
			return true;
		case "P":
			String[] l_listofplayerstrategies = p_argument.split(" ");
			int l_playerStrategiesSize = l_listofplayerstrategies.length;
			List<Player> l_listOfPlayers = p_gameState.getD_players();
			List<Player> l_playersInTheGame = new ArrayList<>();
			if (l_playerStrategiesSize >= 2 && l_playerStrategiesSize <= 4) {
				for (String l_strategy : l_listofplayerstrategies) {
					for (Player l_pl : l_listOfPlayers) {
						if (l_pl.getD_playerBehaviorStrategy().getPlayerBehavior().equalsIgnoreCase(l_strategy)) {
							l_playersInTheGame.add(l_pl);
							p_gameEngine.setD_gameEngineLog("Player:  " + l_pl.getPlayerName() + " with strategy: "
									+ l_strategy + " has been added in tournament.", "effect");
						}
					}
				}
			} else {
				p_gameEngine.setD_gameEngineLog(
						"User entered invalid number of strategies in command, Range of strategies :- 2<=strategy<=4",
						"effect");
				return false;
			}
			if (l_playersInTheGame.size() < 2) {
				p_gameEngine.setD_gameEngineLog("There has to be atleast 2 or more eligible players to play the tournament.", "effect");
				return false;
			}
			for (GameState l_gameState : d_gameStateList) {
				l_gameState.setD_players(l_playersInTheGame);
			}
			return true;
		case "G":
			int l_noOfGames = Integer.parseInt(p_argument.split(" ")[0]);

			if (l_noOfGames >= 1 && l_noOfGames <= 5) {
				List<GameState> l_additionalGameStates = new ArrayList<>();

				for (int l_gameNumber = 0; l_gameNumber < l_noOfGames; l_gameNumber++) {
					for (GameState l_gameState : d_gameStateList) {
						GameState l_gameStateToAdd = new GameState();
						l_gameStateToAdd.setD_map(l_gameState.getD_map());
						l_gameStateToAdd.setD_players(l_gameState.getD_players());
						l_gameStateToAdd.setD_loadCommand();
						l_additionalGameStates.add(l_gameStateToAdd);
					}
				}
				d_gameStateList.addAll(l_additionalGameStates);
				return true;
			} else {
				p_gameEngine.setD_gameEngineLog(
						"User entered invalid number of games in command, Range of games :- 1<=number of games<=5",
						"effect");
				return false;
			}

		case "D":
			int l_maxTurns = Integer.parseInt(p_argument.split(" ")[0]);
			if (l_maxTurns >= 10 && l_maxTurns <= 50) {
				for (GameState l_gameState : d_gameStateList) {
					l_gameState.setD_maxnumberofturns(l_maxTurns);
					l_gameState.setD_numberOfTurnsLeft(l_maxTurns);
				}
			} else {
				p_gameEngine.setD_gameEngineLog(
						"User entered invalid number of turns in command, Range of turns :- 10<=number of turns<=50",
						"effect");
				return false;
			}
		default:
			p_gameEngine.setD_gameEngineLog("Invalid Command Provided", "effect");
			return false;
		}
	}

	public boolean requiredTournamentArgPresent(List<Map<String, String>> p_operations_list, Command p_command)  {
		String l_argumentKey = new String();
		if(p_operations_list.size() != 4)
			return false;
		
		for (Map<String, String> l_map : p_operations_list) {
			if(p_command.checkRequiredKeysPresent(ApplicationConstants.ARGUMENTS, l_map)
							&& p_command.checkRequiredKeysPresent(ApplicationConstants.OPERATION, l_map)) {
				l_argumentKey.concat(l_map.get(ApplicationConstants.OPERATION));
			}
		}
		if(!l_argumentKey.equalsIgnoreCase("MPGD"))
			return false;
		return true;
	}

}
