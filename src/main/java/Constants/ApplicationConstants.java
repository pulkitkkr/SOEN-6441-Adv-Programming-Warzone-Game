package Constants;

public final class ApplicationConstants {

	public static final String INVALID_COMMAND_ERROR_EDITMAP = "Invalid command. Kindly provide command in Format of : editmap filename";
	public static final String INVALID_COMMAND_ERROR_EDITCONTINENT = "Invalid command. Kindly provide command in Format of : editcontinent -add continentID continentvalue -remove continentID";
	public static final String INVALID_COMMAND_ERROR_SAVEMAP = "Invalid command. Kindly provide command in Format of : savemap filename";
	public static final String INVALID_COMMAND_ERROR_LOADMAP = "Invalid command. Kindly provide command in Format of : loadmap filename";
	public static final String INVALID_COMMAND_ERROR_VALIDATEMAP= "Invalid command! validatemap is not supposed to have any arguments";
	public static final String VALID_MAP= "The loaded map is valid!";

	public static final String ARGUMENTS = "arguments";
	public static final String OPERATION = "operation";

	public static final String CONTINENTS = "[continents]";
	public static final String COUNTRIES = "[countries]";
	public static final String BORDERS = "[borders]";

	// private constructor so it can't be instantiated.
	private ApplicationConstants() {
	}
}
