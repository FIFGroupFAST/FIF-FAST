package co.id.fifgroup.core.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.dto.LeaveEntitlementDTO;
import co.id.fifgroup.core.dto.LeaveEntitlementSourceDTO;

public interface LeaveEntitlementAdapterMapper {

	public List<LeaveEntitlementDTO> getEntitlement(@Param("personId") Long personId, @Param("companyId") Long companyId);
	
	public int updateEntitlement(LeaveEntitlementDTO leaveEntitlementDTO);
	
	public int insertEntitlementSource(LeaveEntitlementSourceDTO leaveEntitlementSourceDTO);
	
	public int deleteEntitlementSource(@Param("personId") Long personId, @Param("effectiveStartDate") Date effectiveStartDate, @Param("companyId") Long companyId);
	
	public LeaveEntitlementSourceDTO getEntitlementSource(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveStartDate") Date effectiveStartDate);
	
	public int updateEntitlementSource(LeaveEntitlementSourceDTO leaveEntitlementSourceDTO);
	
	public void updateProcessFlagPrevious(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	
}
