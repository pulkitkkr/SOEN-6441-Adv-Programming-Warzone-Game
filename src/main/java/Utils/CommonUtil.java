package Utils;

import java.util.Collection;
import java.util.Map;

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

}
