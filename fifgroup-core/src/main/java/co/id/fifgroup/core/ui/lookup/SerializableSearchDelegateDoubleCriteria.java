package co.id.fifgroup.core.ui.lookup;

import java.io.Serializable;
import java.util.List;

public interface SerializableSearchDelegateDoubleCriteria<M> extends Serializable {
	
	public List<M> findBySearchCriteria(String searchCriteria1, String searchCriteria2, int limit, int offset);
	
	public int countBySearchCriteria(String searchCriteria1, String searchCriteria2);
	
	void mapper(KeyValue keyValue, M data);
	
}
