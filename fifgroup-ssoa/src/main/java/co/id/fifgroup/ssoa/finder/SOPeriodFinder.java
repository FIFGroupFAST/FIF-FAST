package co.id.fifgroup.ssoa.finder;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.SOPeriod;
import co.id.fifgroup.ssoa.domain.SOPeriodDetail;
import co.id.fifgroup.ssoa.domain.SOPeriodExample;
import co.id.fifgroup.ssoa.dto.SOPeriodDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDetailDTO;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.annotations.Param;

public interface SOPeriodFinder {
	List<SOPeriodDTO> selectByExample(SOPeriodExample example,RowBounds rowBounds);
	List<SOPeriodDTO> selectByExample(SOPeriodExample example);
	List<SOPeriodDetailDTO> selectDetailByExample(@Param("example")SOPeriodDetail example, @Param("rowBounds")RowBounds rowBounds,@Param("taskGroupId") Long taskGroupId);
	List<SOPeriodDetailDTO> selectDetailByHeaderId(@Param("id") Long id);
	int countByExample(SOPeriodExample example);
	int countDetailByExample(@Param("example")SOPeriodDetail example,@Param("taskGroupId") Long taskGroupId);
	
	public List<KeyValue> getLookupKeyValues(@Param("lookupName") String lookupName,@Param("key")String key);
}