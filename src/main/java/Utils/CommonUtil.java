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
	 * 
	 * @param str to check string
	 * @return true if String is empty else false
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().isEmpty());
	}

	/**
	 * 
	 * @param str to check string
	 * @return true if string is not empty else false
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 
	 * @param object to check object
	 * @return true if object is null else false
	 */
	public static boolean isNull(Object object) {
		return (object == null);
	}

	/**
	 * 
	 * @param collection to check collection
	 * @return true if collection is empty else false
	 */
	public static boolean isCollectionEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * 
	 * @param map to check Map
	 * @return true if map is empty else false
	 */
	public static boolean isMapEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}
	
	/**
	 * Generates absolute file path from the given map file
	 * @param p_fileName
	 * @return map file along with its path
	 */
	public static String getMapFilePath(String p_fileName) {
		String l_absolutePath = new File("").getAbsolutePath();
		return l_absolutePath + File.separator + ApplicationConstants.SRC_MAIN_RESOURCES + File.separator + p_fileName;
	}
}
