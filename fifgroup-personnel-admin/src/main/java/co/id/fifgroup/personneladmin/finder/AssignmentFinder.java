package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.AssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PrimaryAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.SecondaryAssignmentDTO;

public interface AssignmentFinder {

	public List<AssignmentDTO> getAssignmentByEffectiveOnDate(@Param("personId") Long personId,
			@Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate,
			@Param("assignmentType") String assignmentType);
	
	public PrimaryAssignmentDTO getPrimaryAssignmentByEffectiveOnDate(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	
	public List<SecondaryAssignmentDTO> getSecondaryAssignmentByEffectiveOnDate(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate, @Param("secondaryAssignmentType") String secondaryAssignmentType);
	
	public Long countSecondaryAssignmentByEffectiveDate(SecondaryAssignmentDTO secondaryAssignmentDTO);
	
	public Long countFutureSecondaryAssignment(SecondaryAssignmentDTO secondaryAssignmentDTO);
	
	public Date getLastPromotionDate(@Param("personId") Long personId, @Param("companyId") Long companyId);
	
	public Integer getPrimaryAssignmentActiveByJobAndOrganization(@Param("jobId") Long jobId, @Param("organizationId") Long organizationId, 
			@Param("effectiveOnDate") Date effectiveOnDate, @Param("companyId")Long companyId);
	
	public PrimaryAssignmentDTO findPrimaryAssignmentById(@Param("assignmentId") Long assignmentId);
	
	public int countFutureTermination(@Param("personId") Long personId, @Param("companyId") Long companyId);
	
	public PrimaryAssignmentDTO getPrimaryAssignmentByEffectiveOnDateForEpmd(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	
	public PrimaryAssignmentDTO getAssignmentIdByEffectiveOnDate(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	
	public List<Long> getPersonChangeAssignment(@Param("companyId") Long companyId);
	
	public List<AssignmentDTO> findHomebasedRadiusAssignmentFromDateToCurrent(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("fromDate") Date fromDate, @Param("listLocationId")List<Long> listLocationId);
	
	public AssignmentDTO findLastNonHomebasedRadiusAssignment(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("listLocationId")List<Long> listLocationId);
	
	public int countHomebasedRadiusAssignmentFromDateToCurrent(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("listLocationId")List<Long> listLocationId);

	public List<PrimaryAssignmentDTO> getPrimaryAssignmentByEffectiveOnDateHousing(@Param("personId") Long personId, @Param("effectiveOnDate") Date effectiveOnDate);
	
}
