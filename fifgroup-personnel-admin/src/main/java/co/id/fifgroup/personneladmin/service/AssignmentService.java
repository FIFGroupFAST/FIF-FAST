package co.id.fifgroup.personneladmin.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.personneladmin.dto.AssignmentDTO;

public interface AssignmentService {

	public List<AssignmentDTO> getAssignmentByEffectiveOnDate(Long personId, Long companyId, Date effectiveOnDate, String assignmentType);
	
	public Date getLastPromotionDate(Long personId, Long companyId);
	
	public Integer getPrimaryAssignmentActiveByJobAndOrganization(Long jobId, Long organizationId, Date effectiveOnDate, Long companyId);
}
