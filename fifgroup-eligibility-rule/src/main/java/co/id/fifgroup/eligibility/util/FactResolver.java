package co.id.fifgroup.eligibility.util;

import java.util.List;
import java.util.Map;

public interface FactResolver {

	List<Map<String, Object>> resolveFact(Map<String, Object> params);
}
