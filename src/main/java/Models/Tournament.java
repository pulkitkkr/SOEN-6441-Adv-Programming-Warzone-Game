package Models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import Exceptions.InvalidMap;
import Services.MapService;

public class Tournament {

	MapService d_mapService = new MapService();

	GameState d_gameState = new GameState();

	int d_NoOfGames;

	int d_NoOfTurns;

	String d_TournamentModeLog;

	ArrayList<Map> d_listofmapfiles = new ArrayList<>();

	ArrayList<String> d_listofplayerstrategies = new ArrayList<>();

	LinkedHashMap<String, Player> d_mappingPlayersToStrategies = new LinkedHashMap<String, Player>();

	private String d_strategyString = "";

	public void parseTournamentCommand(GameState p_gameState, String p_operation, String p_argument) throws InvalidMap {

		// tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D
		// maxnumberofturns

		switch (p_operation) {
		case "M":
			d_strategyString = p_argument;
			String[] l_listOfMapFiles = p_argument.split(" ");
			int l_mapFilesSize = l_listOfMapFiles.length;

			boolean l_flagValidate = false;

			if (l_mapFilesSize >= 1 & l_mapFilesSize <= 5) {
				for (String l_mapToLoad : l_listOfMapFiles) {
					Models.Map l_loadMap = d_mapService.loadMap(d_gameState, l_mapToLoad);
					if (l_loadMap.Validate()) {
						l_flagValidate = true;
						d_listofmapfiles.add(l_loadMap);
						System.out.println("Map is loaded: " + l_mapToLoad);
					}
					if (!l_flagValidate) {
						System.out.println("Map loaded is invalid:- " + l_mapToLoad);
					}
				}
				p_gameState.setD_ArrayOfMap(d_listofmapfiles);
			} else {
				System.err.println("User entered invalid number of maps in command, Range of map :- 1<=map<=5");
			}
			break;
		case "P":
			String[] l_listofplayerstrategies = p_argument.split(" ");
			int l_playerStrategiesSize = l_listofplayerstrategies.length;
			List<Player> l_listOfPlayers = p_gameState.getD_players();

			if (l_playerStrategiesSize >= 2 && l_playerStrategiesSize <= 4) {
				for (String l_strategy : l_listofplayerstrategies) {
					for (Player l_pl : l_listOfPlayers) {
						if (l_pl.getD_playerBehaviorStrategy().getPlayerBehavior().equalsIgnoreCase(l_strategy)
								// && !mapping.containsKey(l_strategy)
								&& !l_pl.getD_playerBehaviorStrategy().getPlayerBehavior().equalsIgnoreCase("Human")) {
							d_mappingPlayersToStrategies.put(l_strategy, l_pl);
							System.out
									.println("Player:  " + l_pl.getPlayerName() + " srategy: " + l_strategy + " added");
						}
					}

				}
				p_gameState.setD_mappingOfPlayerStrategies(d_mappingPlayersToStrategies);
			} else {
				System.err.println(
						"User entered invalid number of strategies in command, Range of strategies :- 2<=strategy<=4");
			}
			if (d_mappingPlayersToStrategies.size() < 2) {
				System.err.println("Player with single strategy cannot play the tournament mode");
			}

			break;
		case "G":
			int l_noOfGames = Integer.parseInt(p_argument.split(" ")[0]);

			if (l_noOfGames >= 1 && l_noOfGames <= 5) {
				d_NoOfGames = l_noOfGames;
			} else {
				System.err.println(
						"User entered invalid number of games in command, Range of games :- 1<=number of games<=5");
			}

			break;
		case "D":
			int l_maxTurns = Integer.parseInt(p_argument.split(" ")[0]);
			if (l_maxTurns >= 10 && l_maxTurns <= 50) {
				d_NoOfTurns = l_maxTurns;
			} else {
				System.err.println(
						"User entered invalid number of turns in command, Range of turns :- 10<=number of turns<=50");
			}

			break;
		default:
			System.err.println("Invalid");

			this.setD_TournamentModeLog("Invalid Operation");
		}
	}

	public void executeTournamentMode() {
		for (Map map : d_listofmapfiles) {
			for (int i = 0; i < d_NoOfGames; i++) {

			}
		}

	}

	public void printTournamentModeResult() {

	}

	public void setD_TournamentModeLog(String p_TournamentModeLog) {
		this.d_TournamentModeLog = p_TournamentModeLog;
		System.out.println(p_TournamentModeLog);
	}

}
