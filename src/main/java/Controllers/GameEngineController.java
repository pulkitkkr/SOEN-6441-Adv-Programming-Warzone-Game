package Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameEngineController {
	public static void main(String[] p_args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			System.out.println("Enter Game Commands or type Exit for quitting");
			String l_command = reader.readLine();
			if("Exit".equalsIgnoreCase(l_command))
				System.exit(0);
			else {
				System.out.println("Command you have entered : " + l_command);
			}	
		}
	}
}
