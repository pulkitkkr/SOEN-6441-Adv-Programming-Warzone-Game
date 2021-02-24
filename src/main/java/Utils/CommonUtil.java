package Utils;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import Constants.ApplicationConstants;

/**
 * Common utility class to check null/empty collections, string, and object
 */
public class CommonUtil {

	/**
	 * This method checks if the string is empty or not.
	 * 
	 * @param str to check string
	 * @return boolean true if String is empty else false
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().isEmpty());
	}

	/**
	 * This method checks if the string is non-empty or not.
	 * 
	 * @param str to check string
	 * @return boolean true if string is not empty else false
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * This method checks if the object is null or not.
	 * 
	 * @param object to check object
	 * @return boolean true if object is null else false
	 */
	public static boolean isNull(Object object) {
		return (object == null);
	}

	/**
	 * This method checks if the collection is empty or not.
	 * 
	 * @param collection to check collection
	 * @return boolean true if collection is empty else false
	 */
	public static boolean isCollectionEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * This method checks if the map is empty or not.
	 * 
	 * @param map to check Map
	 * @return boolean true if map is empty else false
	 */
	public static boolean isMapEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}
	
	/**
	 * Generates absolute file path from the given map file.
	 * 
	 * @param p_fileName filename to map it with the file path
	 * @return string map file along with its path
	 */
	public static String getMapFilePath(String p_fileName) {
		String l_absolutePath = new File("").getAbsolutePath();
		return l_absolutePath + File.separator + ApplicationConstants.SRC_MAIN_RESOURCES + File.separator + p_fileName;
	}
}
