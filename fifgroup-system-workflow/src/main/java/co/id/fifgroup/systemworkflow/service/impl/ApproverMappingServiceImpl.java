package co.id.fifgroup.systemworkflow.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.systemworkflow.constants.ApproverType;
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.domain.ApproverMappingExample;
import co.id.fifgroup.systemworkflow.dto.SupervisorDTO;
import co.id.fifgroup.systemworkflow.service.ApproverMappingService;

import co.id.fifgroup.systemworkflow.dao.ApproverMappingMapper;
import co.id.fifgroup.systemworkflow.dao.SupervisorFinder;

@Transactional(readOnly=true)
@Service
public class ApproverMappingServiceImpl implements ApproverMappingService{

	@Autowired
	private ApproverMappingMapper approverMappingMapper;
	@Autowired
	private SupervisorFinder supervisorFinder;
	
	@Override
	public List<ApproverMapping> getApproverMappingByApproverType(
			ApproverType approverType) {
		ApproverMappingExample approverMappingExample = new ApproverMappingExample();
		approverMappingExample.createCriteria().andApproverTypeEqualTo(approverType.toString());
		approverMappingExample.setOrderByClause("UPPER(APPROVER_NAME) ASC"); 
		return approverMappingMapper.selectByExample(approverMappingExample);
	}

	@Override
	public ApproverMapping getApproverMappingByApproverId(UUID approverId) {
		return approverMappingMapper.selectByPrimaryKey(approverId);
	}

	@Override
	public ApproverMapping getApproverMappingByName(String approverName) {
		ApproverMappingExample approverMappingExample = new ApproverMappingExample();
		approverMappingExample.createCriteria().andApproverNameEqualTo(approverName);
		approverMappingExample.setOrderByClause("UPPER(APPROVER_NAME) ASC"); 
		List<ApproverMapping> approverMappings = approverMappingMapper.selectByExample(approverMappingExample);
		return approverMappings.size() > 0 ? approverMappings.get(0) : null;
	}

	@Override
	@Transactional(readOnly=false)
	public int insertNewApproverMapping(ApproverMapping approverMapping) {
		return approverMappingMapper.insert(approverMapping);
	}

	@Override
	public Integer maxLevelSequence(String approverType, UUID avmTrxId) {
		return supervisorFinder.maxLevelSequence(approverType, avmTrxId);
	}

	@Override
	public SupervisorDTO getPreviousSupervisor(String approverType,
			UUID avmTrxId, int levelSequence, int basedOn) {
		return supervisorFinder.getPreviousSupervisor(approverType, avmTrxId, levelSequence, basedOn);
	}	
}
