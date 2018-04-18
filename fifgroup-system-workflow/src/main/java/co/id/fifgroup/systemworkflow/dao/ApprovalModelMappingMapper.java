package co.id.fifgroup.systemworkflow.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.systemworkflow.dto.ApprovalModelMappingHeaderDTO;

public interface ApprovalModelMappingMapper {
	
	public void deleteLevelMessageMappingByMappingDetailId(Long mappingDetailId);

	public void deleteApprovalModelMappingDetailByMappingId(Long modelMappingId);
	
	public int countApprovalModelMappingActiveByScopeAndTransactionId(@Param("scopeId") Long scopeId, @Param("transactionId") Long transactionid, @Param("effectiveStartDate") Date effectiveStartDate);
	
	public List<ApprovalModelMappingHeaderDTO> getApprovalModelMappingHeader(@Param("scopeId") Long scopeId, @Param("transactionId") Long transactionId, @Param("orgHierId") Long orgHierId, @Param("effectiveOnDate") Date effectiveOnDate);
}
