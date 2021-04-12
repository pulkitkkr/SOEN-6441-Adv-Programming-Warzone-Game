package Models;

import java.util.ArrayList;

import Exceptions.InvalidMap;
import Services.MapService;

public class Tournament {

	MapService d_mapService = new MapService();

	GameState d_gameState = new GameState();

	int d_NoOfGames;

	int d_NoOfTurns;

	String d_TournamentModeLog;

	
	ArrayList<Map> listofmapfiles = new ArrayList<>();

	ArrayList<Map> listofplayerstrategies = new ArrayList<>();
	private String strategyString = "";

	public void parseTournamentCommand(GameState p_gameState, String p_operation, String p_argument) throws InvalidMap {

		// tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D
		// maxnumberofturns

		switch (p_operation) {
		case "M":
			strategyString = p_argument;
			String[] l_listOfMapFiles = p_argument.split(" ");
			int mapFilesSize = l_listOfMapFiles.length;

			boolean l_flagValidate = false;

			if (mapFilesSize >= 1 & mapFilesSize <= 5) {
				for (String mapToLoad : l_listOfMapFiles) {
					Models.Map l_mapToLoad = d_mapService.loadMap(d_gameState, mapToLoad);
					if (l_mapToLoad.Validate()) {
						l_flagValidate = true;
						listofmapfiles.add(l_mapToLoad);
						System.err.println("map loaded:- " + mapToLoad);
					}
					if (!l_flagValidate) {
						System.err.println("map loaded invalid:- " + mapToLoad);
					}
				}
			} else {
				System.err.println("invalid maps");
			}
			break;
		case "P":
			String obj2 = p_argument.split(" ")[0];
			System.err.println("entered P mode");

			break;
		case "G":
			String obj3 = p_argument.split(" ")[0];
			int noOfGames = Integer.parseInt(obj3);

			if (noOfGames >= 1 && noOfGames <= 5) {
				d_NoOfGames = Integer.parseInt(obj3);
			} else {
				System.err.println("invalid");
			}

			System.out.println("Games" + d_NoOfGames);

			break;
		case "D":
			String obj4 = p_argument.split(" ")[0];
			int maxTurns = Integer.parseInt(obj4);
			if (maxTurns >= 10 && maxTurns <= 50) {
				d_NoOfTurns = Integer.parseInt(obj4);
			} else {
				System.err.println("invalid");

			}

			System.out.println("TURNES" + d_NoOfTurns);

			break;
		default:
			System.err.println("Invalid");

			this.setD_TournamentModeLog("Invalid Operation");
		}
	}

	public void executeTournamentMode() {

	}

	public void printTournamentModeResult() {

	}

	public void setD_TournamentModeLog(String p_TournamentModeLog) {
		this.d_TournamentModeLog = p_TournamentModeLog;
		System.out.println(p_TournamentModeLog);
	}

}
