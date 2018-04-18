package co.id.fifgroup.core.util;

public class StringUtil {
	
	public static String wrap( String value, String wrapper,boolean upper) {
		if (null == value) 
			throw new IllegalArgumentException("value must not be null.");
		return String.format("%s%s%s", wrapper, upper ? value.toUpperCase() : value, wrapper);
	}
	
	public static String wrap( String value, String wrapper) {
		return wrap(value, wrapper, false);
	}
	
	public static String replace(String value, String oldChar, String newChar){
		return value.replace(oldChar, newChar);
	}
}
