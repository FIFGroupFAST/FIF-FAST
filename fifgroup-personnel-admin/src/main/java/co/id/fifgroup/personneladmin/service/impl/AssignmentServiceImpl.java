package co.id.fifgroup.personneladmin.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.personneladmin.dto.AssignmentDTO;
import co.id.fifgroup.personneladmin.service.AssignmentService;

import co.id.fifgroup.personneladmin.finder.AssignmentFinder;

@Transactional(readOnly=true)
@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	private AssignmentFinder assignmentFinder;
	
	@Override
	public List<AssignmentDTO> getAssignmentByEffectiveOnDate(Long personId,
			Long companyId, Date effectiveOnDate, String assignmentType) {
		return assignmentFinder.getAssignmentByEffectiveOnDate(personId, companyId, effectiveOnDate, assignmentType);
	}

	@Override
	public Date getLastPromotionDate(Long personId, Long companyId) {
		return assignmentFinder.getLastPromotionDate(personId, companyId);
	}

	@Override
	public Integer getPrimaryAssignmentActiveByJobAndOrganization(Long jobId,
			Long organizationId, Date effectiveOnDate, Long companyId) {
		return assignmentFinder.getPrimaryAssignmentActiveByJobAndOrganization(jobId, organizationId, effectiveOnDate, companyId);
	}
}
