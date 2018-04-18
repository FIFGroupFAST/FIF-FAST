package co.id.fifgroup.basicsetup.service;

import java.util.List;
import java.util.Map;

public interface ServiceModel {

	public List<Map<String, Object>> getFromQuery(String sql);
	
}
