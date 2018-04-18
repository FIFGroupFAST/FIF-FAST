package co.id.fifgroup.ssoa.dao;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.SOPeriod;
import co.id.fifgroup.ssoa.domain.SOPeriodExample;
import co.id.fifgroup.ssoa.dto.SOPeriodDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SOPeriodMapper {
	int countByExample(SOPeriodExample example);
	int deleteByPrimaryKey(Long id);
	int insert(SOPeriod record);
	int insertSelective(SOPeriod record);
	List<SOPeriod> selectByExampleWithRowbounds(SOPeriodExample example, RowBounds rowBounds);
	List<SOPeriod> selectByExample(SOPeriodExample example);
	SOPeriod selectByPrimaryKey(Long id);
	List<Branch> getUnClosedBranchStatus();
	int updateByExampleSelective(@Param("record") SOPeriod record, @Param("example") SOPeriodExample example);
	int updateByExample(@Param("record") SOPeriod record, @Param("example") SOPeriodExample example);
	int updateByPrimaryKeySelective(SOPeriod record);
	int updateByPrimaryKey(SOPeriod record);
	List<SOPeriod> selectStatusSOPeriod(String id);
	List<Assets> selectByBranchId(@Param("branchId")Long branchId,@Param("companyId")Long companyId);
	List<Branch> selectAllBranch(Branch example);
	SOPeriod getLastDataSOPeriod();
	//int updateCountStatus (SOPeriod soPeriod);
	List <SOPeriod> gettatusSOPeriod(SOPeriod soperiod);
	List <SOPeriodDTO> getNotStartedBranch(Long soPeriodId); 
	int updateStatusSOPeriod(StockOpnameDTO stockOpnameDto);
	public List<KeyValue> getLookupKeyValues(@Param("lookupName") String lookupName,@Param("key")String key);
	Long getNotificationMessageByName();
	Long getNotificationTransfer();
	Long getResendNotificationMessageByName();
}