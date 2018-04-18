package co.id.fifgroup.core.ui.lookup;

import java.io.Serializable;
import java.util.List;

public interface SerializableSearchDelegateQuadrupleCriteria<M> extends Serializable {
	
	public List<M> findBySearchCriteria(String searchCriteria1, String searchCriteria2, String searchCriteria3, String searchCriteria4, int limit, int offset);
	
	public int countBySearchCriteria(String searchCriteria1, String searchCriteria2, String searchCriteria3, String searchCriteria4);
	
	void mapper(QuadrupleBandKeyValue keyValue, M data);
	
}
