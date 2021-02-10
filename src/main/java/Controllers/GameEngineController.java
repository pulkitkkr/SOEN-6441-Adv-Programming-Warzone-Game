package Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Models.GameState;

public class GameEngineController {
	public static void main(String[] p_args) throws IOException {
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
		GameState l_gameState = new GameState();
		while (true) {
			System.out.println("Enter Game Commands or type Exit for quitting");
			String l_command = l_reader.readLine();
			if("Exit".equalsIgnoreCase(l_command))
				System.exit(0);
			else {
				System.out.println("Command you have entered : " + l_command);
			}	
		}
	}
}
