package co.id.fifgroup.workstructure.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.version.VersioningService;
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobExample;

import co.id.fifgroup.basicsetup.domain.LookupDependent;
import co.id.fifgroup.basicsetup.dto.OtherInfoDTO;
import co.id.fifgroup.workstructure.dto.GradeDTO;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.JobRoleDTO;
import co.id.fifgroup.workstructure.dto.JobSpecificationDTO;
import co.id.fifgroup.workstructure.dto.JobValidGradeDTO;

public interface JobSetupService extends VersioningService<JobDTO> {

	List<JobDTO> findByOrgIdInNoRowbounds(JobDTO jobDTO, List<Integer> organizationIds);
	void save(JobDTO jobDTO) throws Exception;	
	void delete(JobDTO jobDto);
	List<JobDTO> findByExample(JobDTO jobDTO);
	List<JobDTO> findByExample(JobDTO jobDTO, List<String> jobGroups, boolean canBeActing, int limit, int offset);
	int countByExample(JobDTO jobDTO, List<String> jobGroups, boolean canBeActing);
	List<JobDTO> findByExample(JobDTO jobDTO, int limit, int offset);
	Integer countByExample(JobDTO jobDTO);
	List<Job> findByExample(JobExample example);
	List<JobDTO> findByInquiry(JobDTO example); 	
	boolean isManager(Long id);	
	boolean isKeyjob(Long id);
	JobDTO findById(Long id);	
	JobDTO findById(Long id, Date processDate);
	JobDTO findByUuid(UUID uuid);
	Integer countByExample(JobExample example);
	List<JobRoleDTO> getRoles(Long id);
	List<JobValidGradeDTO> getValidGrades(Long id);
	List<JobValidGradeDTO> getValidGrades(Long id, String gradeCode, String gradeName, int limit, int offset);
	Integer countValidGrades(Long id, String gradeCode, String gradeName);
	List<Job> findByExample(JobExample example, int limit, int offset);
	JobDTO findByCode(String jobCode);
	JobDTO findByCodeAndName(String jobCode, String jobName);
	String getValueOtherInfoByInfoId(Long jobId, Long infoId, Date effectiveOnDate);
	JobDTO findJobForByCodeAndName(String jobCode, String jobName);
	Boolean isPIC(Long personId, Long requestorId);
	Boolean isFutureExist(Long jobId);
	List<JobDTO> getActiveJob(JobDTO jobDTO, int limit, int offset);
	Integer countActiveJob(JobDTO jobDTO);
	List<JobDTO> findByGradeId(JobDTO jobDTO, Long validGradeId, Long organizationId, int limit, int offset);
	Integer countByGradeId(JobDTO jobDTO, Long validGradeId, Long organizationId);
	List<JobSpecificationDTO> getSpecifications(Long id);
	List<OtherInfoDTO> getJobOtherInfos(Long id);
	public List<Long> getGradeSetIdIdByExample(JobDTO example);
	public List<JobValidGradeDTO> getValidGradesForPromotion(Long id, Date effectiveDate);
	public JobDTO findIsKeyJobById(Long id);
	public Long findVersionIdById(Long id);
	JobDTO findLastVersionByCode(String code, Long companyId);
	Job findByPrimaryKey(Long jobId);
	boolean isActiveJob(Long jobId, Date effectiveOnDate);
	List<GradeDTO> findValidGradeByJob(Long jobId, String gradeCode, String gradeName, int limit, int offset);
	int countValidGradeByJob(Long jobId, String gradeCode, String gradeName, Date effectiveOnDate ); //recommitGSK
	/* add by RIM ticket 14040715181325 */
	List<JobDTO> findByOrgId(JobDTO jobDTO, Long organizationId, int limit, int offset);
	Integer countByOrgId(JobDTO jobDTO, Long organizationId);
	//14040715181325_CR HCMS - MPP_JAR
	List<JobDTO> findByOrgIdIn(JobDTO jobDTO, List<Integer> organizationIds, int limit, int offset);
	Integer countByOrgIdIn(JobDTO jobDTO, List<Integer> organizationIds);	
	/* end add by RIM */
	List<Job> findJobAvailableInMpp(JobDTO example, List<Long> organizations, int limit, int offset);
	Integer countJobAvailableInMpp(JobDTO example, List<Long> organizations);
//	START 14091015011812 - Enhancement System E-Psychotest(NPK) (tambahan untuk filter job, yang ada MPP nya dan sudah dibuat employee requisition dan belum dicreate vacancy nya) add by JAR
	List<Job> findJobAvailableInMppAndReadyToCreateVacancy(JobDTO example, List<Long> organizations, Long branchId, int limit, int offset);
	Integer countJobAvailableInMppAndReadyToCreateVacancy(JobDTO example, List<Long> organizations, Long branchId);
//	END 14091015011812 - Enhancement System E-Psychotest(NPK) (tambahan untuk filter job, yang ada MPP nya dan sudah dibuat employee requisition dan belum dicreate vacancy nya) add by JAR
	//14040715181325_CR HCMS - MPP_JAR
	List<LookupDependent> getDocumentList();
	List<JobDTO> getActiveJob2(JobDTO jobDTO, int limit, int offset);
	
	// start added by WLY, Phase 2 Training Admin
	List<JobDTO> findJobByCompanyName(String jobCode, String jobName, String companyName, Long companyId, String jobGroupCode, int limit, int offset);
	Integer countJobByCompanyName(String jobCode, String jobName, String companyName, Long companyId, String jobGroupCode);
	// end added by WLY, Phase 2 Training Admin
	
	// start added by jatis, phase 2 career
	List<JobDTO> findJobByJobCode(JobDTO example, List<String> jobCodes, int limit, int offset);
	Integer countJobByJobCode(JobDTO example, List<String> jobCodes);
	List<JobDTO> findJobByJobName(String jobName, int limit, int offset);
	Integer countJobByJobName(String jobName);
	// end added by jatis, phase 2 career
}
