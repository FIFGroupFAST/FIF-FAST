package co.id.fifgroup.core.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.domain.HistoricalObject;

public interface HistoricalDao<ID extends Serializable, T extends HistoricalObject> {

	List<T> findById(ID id);
	
	T findById(ID id, Date effectiveDate);
	
	List<T> findByExample(T example, Date effectiveDate);
	
	List<T> findByExample(T example, Date effectiveDate, int offset, int limit);
	
	Integer countByExample(T example, Date effectiveDate);
	
	void end(T entity, Date endDate);
	
	void create(T currentVersion, T newVersion);
	
	void update(T entity);
	
}
