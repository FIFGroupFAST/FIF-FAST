package co.id.fifgroup.core.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.dto.BenefitEntitlementDTO;
import co.id.fifgroup.core.dto.BenefitEntitlementSourceDTO;

public interface BenefitEntitlementAdapterMapper {

	public List<BenefitEntitlementDTO> getEntitlement(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("prepayment") boolean prepayment);
	
	public int updateEntitlement(BenefitEntitlementDTO benefitEntitlementDTO);
	
	public int insertEntitlementSource(BenefitEntitlementSourceDTO benefitEntitlementSourceDTO);
	
	public int deleteEntitlementSource(@Param("personId") Long personId, @Param("effectiveStartDate") Date effectiveStartDate, @Param("companyId") Long companyId);
	
	public BenefitEntitlementSourceDTO getEntitlementSource(@Param("personId") Long personId, @Param("effectiveStartDate") Date effectiveStartDate, @Param("companyId") Long companyId, @Param("reason") String reason);
	
	public int updateEntitlementSource(BenefitEntitlementSourceDTO benefitEntitlementSourceDTO);
	
	public void updateBenefitRequestStatusByClaimNumber(@Param("claimNumber")String contractNumber, @Param("newStatus")String newStatus, @Param("processDate")Date processDate, @Param("userId")Long userId);
	
	public void updateBenefitSettlementStatusByClaimNumber(@Param("claimNumber")String contractNumber, @Param("newStatus")String newStatus, @Param("processDate")Date processDate, @Param("userId")Long userId);
	
	public void updateProcessFlagPrevious(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
}
