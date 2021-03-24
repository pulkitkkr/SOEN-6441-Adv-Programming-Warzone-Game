package Constants;

import java.util.Arrays;
import java.util.List;

/**
 * This class initializes all the constants that are going to be used throughout
 * the application.
 *
 */
public final class ApplicationConstants {
	public static final String INVALID_COMMAND_ERROR_EDITMAP = "Invalid command. Kindly provide command in Format of : editmap filename";
	public static final String INVALID_COMMAND_ERROR_EDITCONTINENT = "Invalid command. Kindly provide command in Format of : editcontinent -add continentID continentvalue -remove continentID";
	public static final String INVALID_COMMAND_ERROR_EDITCOUNTRY = "Invalid command. Kindly provide command in Format of : editcountry -add countrytID continentID -remove countryID";
	public static final String INVALID_COMMAND_ERROR_EDITNEIGHBOUR = "Invalid command. Kindly provide command in Format of : editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID";
	public static final String INVALID_COMMAND_ERROR_SAVEMAP = "Invalid command. Kindly provide command in Format of : savemap filename";
	public static final String INVALID_MAP_ERROR_EMPTY = "No Map found! Please load a valid map to check!";
	public static final String INVALID_COMMAND_ERROR_LOADMAP = "Invalid command. Kindly provide command in Format of : loadmap filename";
	public static final String INVALID_COMMAND_ERROR_VALIDATEMAP = "Invalid command! validatemap is not supposed to have any arguments";
	public static final String INVALID_COMMAND_ERROR_GAMEPLAYER = "Invalid command. Kindly provide command in Format of : gameplayer -add playername -remove playername";
	public static final String INVALID_MAP_LOADED = "Map cannot be loaded, as it is invalid. Kindly provide valid map";
	public static final String INVALID_COMMAND_ERROR_ASSIGNCOUNTRIES = "Invalid command. Kindly provide command in Format of : assigncountries";
	public static final String INVALID_COMMAND_ERROR_DEPLOY_ORDER = "Invalid command. Kindly provide command in Format of : deploy countryID <CountryName> <num> (until all reinforcements have been placed)";
	public static final String VALID_MAP = "The loaded map is valid!";

	public static final String ARGUMENTS = "arguments";
	public static final String OPERATION = "operation";

	public static final String MAPFILEEXTENSION = ".map";

	public static final String RED = "\033[0;31m";
	public static final String GREEN = "\033[0;32m";
	public static final String YELLOW = "\033[0;33m";
	public static final String BLUE = "\033[0;34m";
	public static final String PURPLE = "\033[0;35m";
	public static final String CYAN = "\033[0;36m";
	public static final String WHITE = "\u001B[47m";

	public static final String CONTINENTS = "[continents]";
	public static final String COUNTRIES = "[countries]";
	public static final String BORDERS = "[borders]";
	public static final String ARMIES = "Armies";
	public static final String CONTROL_VALUE = "Control Value";
	public static final String CONNECTIVITY = "Connections";
	public static final String SRC_MAIN_RESOURCES = "src/main/resources";
	public static final int CONSOLE_WIDTH = 80;

	public static final List<String> COLORS = Arrays.asList(RED, GREEN, YELLOW, BLUE, PURPLE, CYAN);

	public static final List<Object> CARDS = Arrays.asList("bomb", "blockade", "airlift", "negotiate");
	public static final int SIZE = CARDS.size();
}
