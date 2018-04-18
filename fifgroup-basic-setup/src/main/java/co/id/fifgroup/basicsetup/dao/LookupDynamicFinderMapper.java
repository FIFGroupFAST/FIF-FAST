package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface LookupDynamicFinderMapper {

	public List<KeyValue> getLookupDynamic(@Param("sql") String sql);
	public KeyValue getLookupDynamicWithKey(@Param("sql") String sql, @Param("key") String key);
	public List<KeyValue> getLookupDynamicWithSearchCriteria(@Param("sql") String sql, @Param("searchCriteria") String searchCriteria, RowBounds rowBounds);
	public int getCountLookupDynamicWithSearchCriteria(@Param("sql") String sql, @Param("searchCriteria") String searchCriteria);
	public KeyValue getLookupDynamicWithKeyCaseInsensitive(@Param("sql") String sql, @Param("key") String key);
	
}
