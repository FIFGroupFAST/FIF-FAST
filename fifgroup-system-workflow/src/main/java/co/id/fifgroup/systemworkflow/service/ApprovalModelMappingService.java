package co.id.fifgroup.systemworkflow.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetail;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingHeader;
import co.id.fifgroup.systemworkflow.domain.LevelMessageMapping;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMappingDTO;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMappingHeaderDTO;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMessageMappingDTO;

public interface ApprovalModelMappingService {

	public List<ApprovalModelMappingHeader> getAllApprovalModelMappingHeader();
	
	public List<ApprovalModelMappingHeaderDTO> getApprovalModelMappingHeaderByCriteria(Long scopeId, TrxType trxType, Long orgHierId, Date effectiveOnDate);
	
	public List<ApprovalModelMappingDetail> getApprovalModelMappingDetailByHeaderId(Long headerId);
	
	public List<ApprovalModelMessageMappingDTO> getApprovalModelMessageMappingDtoByHeaderId(Long headerId);
	
	public List<LevelMessageMapping> getLevelMessageMappingByDetailId(Long detailId);
	
	public void saveApprovalModelMapping(ApprovalModelMappingDTO approvalModelMappingDto) throws ValidationException;
	
	public ApprovalModelMappingHeader getApprovalModelMappingHeaderByTransactionId(Long transactionId, Long companyId, Date transactionDate);
	
	public ApprovalModelMappingDetail getApprovalModelMappingDetailByHeaderIdAndPriority(Long headerId, Long priority);
	
	public int countApprovalModelMappingActiveByScopeAndTransactionId(Long scopeId, Long transactionid, Date effectiveStartDate);
	
	public ApprovalModelMappingHeader getApprovalModelMappingActiveByScopeAndTransactionId(Long scopeId, Long transactionid, Date effectiveOnDate);
	
	public void deleteApprovalModelMapping(ApprovalModelMappingDTO approvalModelMappingDto);
}
