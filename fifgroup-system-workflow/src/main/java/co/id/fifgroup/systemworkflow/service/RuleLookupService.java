package co.id.fifgroup.systemworkflow.service;

import java.util.List;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface RuleLookupService {

	public List<KeyValue> getLookups(String searchCriteria, String attributeLabel, int limit, int offset);
	
	public int countLookups(String searchCriteria, String attributeLabel);
	
	public KeyValue getLookupValue(String attributeLabel, String key);
}
