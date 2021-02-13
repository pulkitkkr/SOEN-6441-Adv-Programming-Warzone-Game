package Utils;

import java.util.Collection;
import java.util.Map;

public class CommonUtil {

	public static boolean isEmpty(String str) {
		return (str == null || str.trim().isEmpty());
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isNull(Object object) {
		return (object == null);
	}

	public static boolean isCollectionEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	public static boolean isMapEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}

}
