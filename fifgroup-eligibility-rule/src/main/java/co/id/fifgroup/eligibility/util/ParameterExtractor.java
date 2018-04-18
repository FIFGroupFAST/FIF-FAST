package co.id.fifgroup.eligibility.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterExtractor {
	private static Pattern pattern = Pattern.compile("[\\#\\$]\\{([a-zA-Z_]+).*\\}");
	
	public static Set<String> getParameters(String query) {
		Set<String> result = new HashSet<>();
		if (query != null) {
			Matcher matcher = pattern.matcher(query);
			while (matcher.find()) {
				result.add(matcher.group(1));
			}
		}
		return result;
	}
	
}
