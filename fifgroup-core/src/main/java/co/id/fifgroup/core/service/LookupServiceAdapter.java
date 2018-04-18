package co.id.fifgroup.core.service;

import java.util.List;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface LookupServiceAdapter {

	public List<KeyValue> getLookupValues(String searchCriteria, Long groupId, String lookupName, Long scope, String parentDetailLookup, boolean active, int limit, int offset);
	public int getCountLookupValues(String searchCriteria, Long groupId, String lookupName, Long scope, String parentDetailLookup, boolean active);
	public KeyValue getLookup(String lookupName, String key, Long groupId, Long scope);
	public List<KeyValue> getLookups(String lookupName, Long groupId, Long scope, boolean active);
	
}
