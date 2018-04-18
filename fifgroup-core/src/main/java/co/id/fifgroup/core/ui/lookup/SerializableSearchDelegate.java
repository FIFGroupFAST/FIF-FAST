package co.id.fifgroup.core.ui.lookup;

import java.io.Serializable;
import java.util.List;

public interface SerializableSearchDelegate<M> extends Serializable {
	
	public List<M> findBySearchCriteria(String searchCriteria, int limit, int offset);
	
	public int countBySearchCriteria(String searchCriteria);

	void mapper(KeyValue keyValue, M data);
	
}
