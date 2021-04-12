package Services;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import Models.GameState;
import Models.Map;

/**
 * Adapter class for writing to conquest map file.
 *
 */
public class MapWriterAdapter extends MapFileWriter{
	
	private ConquestMapFileWriter l_conquestMapFileWriter;
	
	/**
	 * Adapter constructor for setting conquest map file Writer.
	 * 
	 * @param p_conquestMapFileWriter conquest map file Writer
	 */
	public MapWriterAdapter(ConquestMapFileWriter p_conquestMapFileWriter) {
		this.l_conquestMapFileWriter = p_conquestMapFileWriter;
	}


	/**
	 * Adapter for writing to different type of map file through adaptee.
	 * 
	 * @param p_gameState current state of the game
	 * @param l_writer file writer
	 * @param l_mapFormat format in which map file has to be saved
	 * @throws IOException
	 */
	public void parseMapToFile(GameState p_gameState, FileWriter l_writer, String l_mapFormat) throws IOException {
		l_conquestMapFileWriter.parseMapToFile(p_gameState, l_writer, l_mapFormat);
	}
}
