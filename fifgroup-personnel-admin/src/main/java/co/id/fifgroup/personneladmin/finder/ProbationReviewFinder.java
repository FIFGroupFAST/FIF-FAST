package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.dto.ProbationCompetencyReviewDTO;
import co.id.fifgroup.personneladmin.dto.ProbationReviewDTO;

public interface ProbationReviewFinder {

	int countPersonProbationBySecurityFilterExample(@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId,  @Param("gradeExclusions") List<Long> gradeExclusions
			, @Param("companyId") Long companyId
			, @Param("employeeNumber") String employeeNumber
			, @Param("fullName") String fullName);
	
	List<ProbationReviewDTO> selectPersonProbationBySecurityFilterExample(@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId,  @Param("gradeExclusions") List<Long> gradeExclusions
			, @Param("companyId") Long companyId
			, @Param("employeeNumber") String employeeNumber
			, @Param("fullName") String fullName
			, RowBounds rowBounds);
	
	public List<ProbationReviewDTO> getProbationReviewPeople(@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId,  @Param("gradeExclusions") List<Long> gradeExclusions
			,@Param("companyId") Long companyId
			, @Param("organizationId") Long organizationId
			, @Param("jobId") Long jobId
			, @Param("gradeId") Long gradeId
			, @Param("personId") Long personId
			, @Param("probationStatus") String probationStatus
			, @Param("dateFrom") Date dateFrom
			, @Param("dateTo") Date dateTo
			, RowBounds rowBounds);
	
	public int countProbationReviewPeople(@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId,  @Param("gradeExclusions") List<Long> gradeExclusions
			, @Param("companyId") Long companyId
			, @Param("organizationId") Long organizationId
			, @Param("jobId") Long jobId
			, @Param("gradeId") Long gradeId
			, @Param("personId") Long personId
			, @Param("probationStatus") String probationStatus
			, @Param("dateFrom") Date dateFrom
			, @Param("dateTo") Date dateTo);
	
	public List<ProbationReviewDTO> getActiveProbationPeople(@Param("companyId") Long companyId);
	
	public List<ProbationReviewDTO> getProbationReviewPeople(@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId,  @Param("gradeExclusions") List<Long> gradeExclusions
			,@Param("companyId") Long companyId
			, @Param("organizationId") Long organizationId
			, @Param("jobId") Long jobId
			, @Param("gradeId") Long gradeId
			, @Param("personId") Long personId
			, @Param("probationStatus") String probationStatus
			, @Param("dateFrom") Date dateFrom
			, @Param("dateTo") Date dateTo);
	
	public List<ProbationReviewDTO> getProbationEndPeople(@Param("companyId") Long companyId);
	
// start added for career, by Jatis
	public List<ProbationCompetencyReviewDTO> getCompetencyBasedJobId(@Param("jobId") Long jobId, @Param("personId")Long personId);
	
	public List<ProbationCompetencyReviewDTO> getCompetencyReviewByProbationReviewId(@Param("probationReviewId") Long probationReviewId, @Param("personId") Long personId);

	int getProficiencyDetail(Long achievmentBehaviourId);

	int getBehaviour(Long achievmentBehaviourId);
// end added for career, by Jatis
}
