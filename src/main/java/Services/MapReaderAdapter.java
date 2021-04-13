package Services;
import java.util.List;
import Models.GameState;
import Models.Map;

/**
 * Adapter class for reading conquest map file.
 *
 */
public class MapReaderAdapter extends MapFileReader{
	
	private ConquestMapFileReader l_conquestMapFileReader;
	
	/**
	 * Adapter constructor for setting conquest map file reader.
	 * 
	 * @param p_conquestMapFileReader conquest map file reader
	 */
	public MapReaderAdapter(ConquestMapFileReader p_conquestMapFileReader) {
		this.l_conquestMapFileReader = p_conquestMapFileReader;
	}


	/**
	 * Adapter for reading different type of map file through adaptee.
	 * 
	 * @param p_gameState current state of the game
	 * @param p_map map to be set
	 * @param p_linesOfFile lines of loaded file
	 */
	public void parseMapFile(GameState p_gameState, Map p_map, List<String> p_linesOfFile) {
		l_conquestMapFileReader.readConquestFile(p_gameState, p_map, p_linesOfFile);
	}
}
