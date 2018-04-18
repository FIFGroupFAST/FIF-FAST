package co.id.fifgroup.personneladmin.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.personneladmin.dto.AssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PrimaryAssignmentDTO;

import co.id.fifgroup.basicsetup.common.HistoricalService;
import co.id.fifgroup.personneladmin.domain.PrimaryAssignment;

public interface PrimaryAssignmentService extends HistoricalService<PrimaryAssignmentDTO>{
	
	public void save(PrimaryAssignmentDTO primaryAssignmentDTO, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception;
	
	public PrimaryAssignmentDTO getPrimaryAssignmentByEffectiveOnDate(Long personId, Long companyId, Date effectiveOnDate);
	
	public void delete(PrimaryAssignmentDTO primaryAssignment);
	
	public void deleteFuturePrimaryAssignment(Long personId, Long companyId);
	
	public void savePrimaryAssignment(PrimaryAssignmentDTO primaryAssignmentDTO, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception;
	
	public PrimaryAssignmentDTO getPrimaryAssignmentById(Long assignmentId);
	
	public boolean hasFutureTermination(Long personId, Long companyId);
	
	public PrimaryAssignmentDTO getPrimaryAssignmentByEffectiveOnDateForEpmd(Long personId, Long companyId, Date effectiveOnDate);
	
	public boolean isTaskForceActive(PrimaryAssignmentDTO primaryAssignmentDTO);
	
	public PrimaryAssignment getTaskForceTransaction(PrimaryAssignmentDTO primaryAssignmentDTO);
	
	public PrimaryAssignmentDTO getAssignmentIdByEffectiveOnDate(Long personId, Long companyId, Date effectiveOnDate);
	
	public List<Long> getPersonChangeAssignment(Long companyId);
	
	public int countHomebasedRadiusAssignmentFromDateToCurrent(Long personId, Long companyId, Date fromDate, Date toDate, List<Long> listLocationId);
	
	public List<AssignmentDTO> findHomebasedRadiusAssignmentFromDateToCurrent(Long personId, Long companyId, Date fromDate, List<Long> listLocationId);
	
	public AssignmentDTO findLastNonHomeBasedAssignment(Long personId, Long CompanyId,List<Long> homebasedRadiusLocationId);
	
	public List<PrimaryAssignmentDTO> getPrimaryAssignmentByEffectiveOnDateHousing(Long personId,Date effectiveOnDate);

	
}
