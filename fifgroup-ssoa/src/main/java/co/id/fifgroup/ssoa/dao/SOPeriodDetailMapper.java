package co.id.fifgroup.ssoa.dao;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.SOPeriodDetail;
import co.id.fifgroup.ssoa.domain.SOPeriodDetailExample;
import co.id.fifgroup.ssoa.dto.SOPeriodDetailDTO;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SOPeriodDetailMapper {
	int countByExample(SOPeriodDetailExample example);
	int deleteByPrimaryKey(Long id);
	int deleteByHeaderKey(Long id);
	int insert(SOPeriodDetail record);
	int insertSelective(SOPeriodDetail record);
	List<SOPeriodDetail> selectByExampleWithRowbounds(SOPeriodDetailExample example, RowBounds rowBounds);
	List<SOPeriodDetail> selectByExample(SOPeriodDetailExample example);
	SOPeriodDetail selectByPrimaryKey(Long id);
	int updateByExampleSelective(@Param("record") SOPeriodDetail record, @Param("example") SOPeriodDetailExample example);
	int updateByExample(@Param("record") SOPeriodDetail record, @Param("example") SOPeriodDetailExample example);
	int updateByPrimaryKeySelective(SOPeriodDetail record);
	int updateByPrimaryKey(SOPeriodDetail record);
	
	List<SOPeriodDetail> selectDetailBranch(SOPeriodDetail example);
	List<SOPeriodDetail> selectStatusStockOpname(String id);
		
}