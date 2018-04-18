package co.id.fifgroup.systemworkflow.service;

import java.util.List;
import java.util.UUID;

import co.id.fifgroup.systemworkflow.constants.ApproverType;
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.dto.SupervisorDTO;

public interface ApproverMappingService {

	public List<ApproverMapping> getApproverMappingByApproverType(ApproverType approverType);
	
	public ApproverMapping getApproverMappingByApproverId(UUID approverId);
	
	public ApproverMapping getApproverMappingByName(String approverName);
	
	public int insertNewApproverMapping(ApproverMapping approverMapping);
	
	public Integer maxLevelSequence(String approverType, UUID avmTrxId);
	
	public SupervisorDTO getPreviousSupervisor(String approverType, UUID avmTrxId, int levelSequence, int basedOn);
}
