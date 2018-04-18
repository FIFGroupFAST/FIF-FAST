package co.id.fifgroup.core.service;

import java.util.List;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface ResponsibilityServiceAdapter {
	
	List<KeyValue> getResponsibilityKeyValues();
	
	KeyValue getResponsibilityKeyValueById(Long id);
	
}
