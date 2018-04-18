package co.id.fifgroup.core.ui.lookup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public final class Lookups {

	private static final Map<String, LookupCriteria> cache =
			Collections.synchronizedMap(new HashMap<String, LookupCriteria>());
	
	public static final LookupCriteria getInstance(String lookupName) {
		if (!cache.containsKey(lookupName)) {
			cache.put(lookupName, new StaticLookupCriteria(lookupName, null));
		}
		return cache.get(lookupName);
		
		
	}
}
