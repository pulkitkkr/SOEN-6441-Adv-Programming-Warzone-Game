package Constants;

/**
 * 
 * ApplicationConstants class to wrap constants for general utility.
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

	public static final String CONTINENTS = "[continents]";
	public static final String COUNTRIES = "[countries]";
	public static final String BORDERS = "[borders]";
	public static final String SRC_MAIN_RESOURCES = "src/main/resources";
}
