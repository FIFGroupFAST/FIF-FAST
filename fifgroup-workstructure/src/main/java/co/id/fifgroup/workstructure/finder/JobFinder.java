package co.id.fifgroup.workstructure.finder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.workstructure.domain.Job;

import co.id.fifgroup.basicsetup.domain.LookupDependent;
import co.id.fifgroup.basicsetup.dto.OtherInfoDTO;
import co.id.fifgroup.workstructure.dto.GradeDTO;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.JobRoleDTO;
import co.id.fifgroup.workstructure.dto.JobSpecificationDTO;
import co.id.fifgroup.workstructure.dto.JobValidGradeDTO;

public interface JobFinder {
	
	
	List<JobDTO> findByExampleAndJobGroups(@Param("job") JobDTO jobDTO, @Param("jobGroups")List<String> jobGroups, @Param("canBeActing") boolean canBeActing, @Param("businessGroupId") Long businessGroupId, RowBounds rowBounds);
	int countByExampleAndJobGroups(@Param("job") JobDTO jobDTO, @Param("jobGroups")List<String> jobGroups, @Param("canBeActing") boolean canBeActing, @Param("businessGroupId") Long businessGroupId);
	List<JobDTO> findByExample(@Param("job") JobDTO jobDTO, RowBounds rowBounds);
	Integer countByExample(@Param("job") JobDTO jobDTO);
	List<JobDTO> findByExample(@Param("job") JobDTO jobDTO);
	JobDTO findByIdAndVersionNumber(@Param("id") Long id, @Param("versionNumber") Integer versionNumber);
	JobDTO findByIdAndDate(@Param("id") Long id, @Param("effectiveDate") Date effectiveDate);
	List<Integer> findVersionsById(@Param("id") Long id);
	List<JobDTO> findByInquiry(@Param("job")JobDTO example);
	Integer isManager(@Param("id") Long id);
	Integer isKeyJob(@Param("id") Long id);
	JobDTO findById(@Param("id") Long id, @Param("processDate") Date processDate);
	JobDTO findByUuid(@Param("uuid") UUID uuid);
	Integer isHaveFuture(@Param("id") Long id);
	JobDTO findLastVersionByCode(@Param("jobCode") String code, @Param("companyId") Long companyId);
	List<JobRoleDTO> findJobRoles(@Param("id") Long id);
	List<JobValidGradeDTO> findValidGrades(@Param("id") Long id, @Param("gradeCode") String gradeCode, @Param("gradeName")String gradeName, RowBounds rowBounds);
	Integer countValidGrades(@Param("id") Long id, @Param("gradeCode") String gradeCode, @Param("gradeName")String gradeName);
	JobDTO findByCode(@Param("jobCode") String jobCode, @Param("companyId") Long companyId);
	JobDTO findByCodeAndName(@Param("jobCode") String jobCode, @Param("jobName") String jobName, @Param("companyId") Long companyId);
	String getValueOtherInfoByInfoId(@Param("jobId") Long jobId, @Param("infoId") Long infoId, @Param("effectiveOnDate") Date effectiveOnDate);
	JobDTO findJobForByCodeAndName(@Param("jobCode") String jobCode, @Param("jobName") String jobName, @Param("companyId") Long companyId);
	Integer isFutureExist(@Param("id") Long id);
	List<JobDTO> findActiveJob(@Param("job") JobDTO jobDTO, RowBounds rowBounds);
	List<JobDTO> findActiveJob2(@Param("job") JobDTO jobDTO, RowBounds rowBounds);
	Integer countActiveJob(@Param("job") JobDTO jobDTO);
	List<JobDTO> findByGradeId(@Param("job") JobDTO jobDTO, @Param("validGradeId") Long validGradeId, @Param("organizationId") Long organizationId, RowBounds rowBounds);
	Integer countByGradeId(@Param("job")JobDTO jobDTO, @Param("validGradeId") Long validGradeId, @Param("organizationId") Long organizationId);
	List<JobSpecificationDTO> findSpecifications(@Param("id") Long id);
	List<OtherInfoDTO> findJobOtherInfos(@Param("id") Long id);
	public List<Long> getGradeSetIdByExample(@Param("job") JobDTO example);
	public List<JobValidGradeDTO> findValidGradesForPromotion(@Param("id") Long id, @Param("effectiveDate") Date effectiveDate);
	public JobDTO findIsKeyJobById(@Param("id") Long id);
	public Long findVersionIdById(@Param("id") Long id);
	List<GradeDTO> findValidGradeByJob(@Param("id") Long id, @Param("gradeCode") String gradeCode, @Param("gradeName")String gradeName, RowBounds rowBounds);
	int countValidGradeByJob(@Param("id") Long id, @Param("gradeCode") String gradeCode, @Param("gradeName")String gradeName, @Param("effectiveOnDate") Date effectiveOnDate );
	/*add by rim ticket 14040715181325*/
	List<JobDTO> findByOrgId(@Param("job") JobDTO jobDTO,@Param("organizationId") Long organizationId, RowBounds rowBounds);
	Integer countByOrgId(@Param("job") JobDTO jobDTO,@Param("organizationId") Long organizationId);
	List<JobDTO> findByOrgIdIn(@Param("job") JobDTO jobDTO,@Param("organizationIds") List<Integer> organizationIds, RowBounds rowBounds);
	//14040715181325_CR HCMS - MPP_JAR
	Integer countByOrgIdIn(@Param("job") JobDTO jobDTO,@Param("organizationIds") List<Integer> organizationIds);
	List<JobDTO> findByOrgIdIn(@Param("job") JobDTO jobDTO,@Param("organizationIds") List<Integer> organizationIds);
	/*end add by rim*/
	List<Job> findJobAvailableInMpp(@Param("job") JobDTO jobDTO, @Param("organizations") List<Long> organizations, RowBounds rowBounds);
	Integer countJobAvailableInMpp(@Param("job") JobDTO jobDTO, @Param("organizations") List<Long> organizations);
	
//	START 14091015011812 - Enhancement System E-Psychotest(NPK) (tambahan untuk filter job, yang ada MPP nya dan sudah dibuat employee requisition dan belum dicreate vacancy nya) add by JAR
	List<Job> findJobAvailableInMppAndReadyToCreateVacancy(@Param("job") JobDTO jobDTO, @Param("organizations") List<Long> organizations, @Param("branchId") Long branchId, RowBounds rowBounds);
	Integer countJobAvailableInMppAndReadyToCreateVacancy(@Param("job") JobDTO jobDTO, @Param("organizations") List<Long> organizations, @Param("branchId") Long branchId);
//	END 14091015011812 - Enhancement System E-Psychotest(NPK) (tambahan untuk filter job, yang ada MPP nya dan sudah dibuat employee requisition dan belum dicreate vacancy nya) add by JAR
	//end add 14040715181325_CR HCMS - MPP_JAR
	List<LookupDependent> getDocumentList();
	
	// start added by WLY, Phase 2 Training Admin
	List<JobDTO> findJobByCompanyName(
			@Param("businessGroupId") Long businessGroupId, 
			@Param("jobCode") String jobCode,
			@Param("jobName") String jobName, 
			@Param("companyName") String companyName, 
			@Param("companyId") Long companyId,
			@Param("jobGroupCode") String jobGroupCode, 
			RowBounds rowBounds);
	Integer countJobByCompanyName(
			@Param("businessGroupId") Long businessGroupId, 
			@Param("jobCode") String jobCode,
			@Param("jobName") String jobName, 
			@Param("companyName") String companyName, 
			@Param("companyId") Long companyId,
			@Param("jobGroupCode") String jobGroupCode);
	// end added by WLY, Phase 2 Training Admin
	
	// start added by jatis, phase 2 career
	List<JobDTO> findJobByJobCode(@Param("job")JobDTO example,@Param("jobCodes") List<String> jobCodes, RowBounds rowBounds);
	Integer countJobByJobCode(@Param("job")JobDTO example,@Param("jobCodes") List<String> jobCodes);
	List<JobDTO> findJobByJobName(@Param("jobName")String jobName,RowBounds rowBounds);
	Integer countJobByJobName(@Param("jobName")String jobName);
	// end added by jatis, phase 2 career
	
}
