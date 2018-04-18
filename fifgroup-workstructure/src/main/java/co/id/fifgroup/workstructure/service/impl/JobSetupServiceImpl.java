package co.id.fifgroup.workstructure.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.workstructure.domain.JobDocument;
import co.id.fifgroup.core.util.IdGenerator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.version.AbstractVersioningService;
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobDocumentExample;
import co.id.fifgroup.workstructure.domain.JobExample;
import co.id.fifgroup.workstructure.domain.JobInfo;
import co.id.fifgroup.workstructure.domain.JobInfoExample;
import co.id.fifgroup.workstructure.domain.JobRoleExample;
import co.id.fifgroup.workstructure.domain.JobSpecification;
import co.id.fifgroup.workstructure.domain.JobSpecificationExample;
import co.id.fifgroup.workstructure.domain.JobValidGradeExample;
import co.id.fifgroup.workstructure.domain.JobVersion;
import co.id.fifgroup.workstructure.domain.JobVersionExample;
import co.id.fifgroup.workstructure.service.JobSetupService;

import co.id.fifgroup.basicsetup.domain.LookupDependent;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.service.SecurityService;
//import co.id.fifgroup.core.service.TraDevelopmentServiceAdapter;
import co.id.fifgroup.workstructure.dao.JobDocumentMapper;
import co.id.fifgroup.workstructure.dao.JobInfoMapper;
import co.id.fifgroup.workstructure.dao.JobMapper;
import co.id.fifgroup.workstructure.dao.JobRoleMapper;
import co.id.fifgroup.workstructure.dao.JobSpecificationMapper;
import co.id.fifgroup.workstructure.dao.JobValidGradeMapper;
import co.id.fifgroup.workstructure.dao.JobVersionMapper;
import co.id.fifgroup.workstructure.dto.GradeDTO;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.JobRoleDTO;
import co.id.fifgroup.workstructure.dto.JobSpecificationDTO;
import co.id.fifgroup.workstructure.dto.JobValidGradeDTO;
import co.id.fifgroup.workstructure.dto.OtherInfoDTO;
import co.id.fifgroup.workstructure.finder.JobFinder;
import co.id.fifgroup.workstructure.validation.JobValidator;

@Transactional
@Service
public class JobSetupServiceImpl extends AbstractVersioningService<JobDTO> implements JobSetupService{
	
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private JobFinder jobFinder;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private JobVersionMapper jobVersionMapper;
	@Autowired
	private JobInfoMapper jobInfoMapper;
	@Autowired
	private JobValidGradeMapper jobValidGradeMapper;
	@Autowired
	private JobRoleMapper jobRoleMapper;
	@Autowired
	private JobSpecificationMapper jobSpecMapper;
	@Autowired
	private JobValidator jobValidator;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CompanyService companyServiceImpl;
	@Autowired 
	JobDocumentMapper jobDocumentMapper;
	// start added by WLY - JATIS - phase 2 Training Admin
	/*@Autowired
	private TraDevelopmentServiceAdapter developmentAdministartionBankServiceImpl;*/
	// end added by WLY - JATIS - phase 2 Training Admin
	
	@Override
	public void save(JobDTO jobDTO) throws Exception {
		super.save(jobDTO);	
		
		// start added by WLY - JATIS - phase 2 Training Admin
		// mengirimkan notifikasi ke HC Administrator untuk melakukan setup terkait dengan Job tersebut, 
		// seperti competency yang dimiliki oleh Job, development yang diperlukan untuk mendapatkan competency atas Job 
		// dan Individual Performance Plan atas Job tersebut
		/*try {
			developmentAdministartionBankServiceImpl.sendNewJobNotification(jobDTO.getId(), jobDTO.getDateFrom(),
					securityServiceImpl.getSecurityProfile().getPersonId());
		}
		catch (Exception e){
			e.printStackTrace();
		}*/
		// end added by WLY - JATIS - phase 2 Training Admin
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<JobDTO> findByExample(JobDTO jobDTO, int limit, int offset) {
		return jobFinder.findByExample(jobDTO, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countByExample(JobDTO jobDTO) {
		return jobFinder.countByExample(jobDTO);
	}
	
	@Override
	public void insertHeader(JobDTO subject) {		
//		Job job = modelMapper.map(subject, Job.class);
		Job job = new Job();
		job.setCompanyId(subject.getCompanyId());
		job.setCreatedBy(subject.getCreatedBy());
		job.setCreationDate(subject.getCreationDate());
		job.setId(subject.getId());
		job.setJobCode(subject.getJobCode());
		job.setJobName(subject.getJobName());
		job.setJobUuid(subject.getJobUuid());
		job.setLastUpdateDate(subject.getLastUpdateDate());
		job.setLastUpdatedBy(subject.getLastUpdatedBy());
		jobMapper.insert(job);
		subject.setId(job.getId());
	}

	@Override
	public void updateHeader(JobDTO subject) {		
//		Job job = modelMapper.map(subject, Job.class);
		Job job = new Job();
		job.setCompanyId(subject.getCompanyId());
		job.setCreatedBy(subject.getCreatedBy());
		job.setCreationDate(subject.getCreationDate());
		job.setId(subject.getId());
		job.setJobCode(subject.getJobCode());
		job.setJobName(subject.getJobName());
		job.setJobUuid(subject.getJobUuid());
		job.setLastUpdateDate(subject.getLastUpdateDate());
		job.setLastUpdatedBy(subject.getLastUpdatedBy());
		jobMapper.updateByPrimaryKey(job);
	}

	@Override
	public void insertDetail(JobDTO subject) {
//		JobVersion jobVersion = modelMapper.map(subject, JobVersion.class);
		JobVersion jobVersion = new JobVersion();
		jobVersion.setCreatedBy(subject.getCreatedBy());
		jobVersion.setCreationDate(subject.getCreationDate());
		jobVersion.setDateFrom(subject.getDateFrom());
		jobVersion.setDateTo(subject.getDateTo());
		jobVersion.setDescription(subject.getDescription());
		jobVersion.setIsKeyJob(subject.getIsKeyJob());
		jobVersion.setIsManager(subject.getIsManager());
		jobVersion.setJobFilePath(subject.getJobFilePath());
		jobVersion.setJobForCode(subject.getJobForCode());
		jobVersion.setJobGroupCode(subject.getJobGroupCode());
		jobVersion.setJobTypeCode(subject.getJobTypeCode());
		jobVersion.setLastUpdateDate(subject.getLastUpdateDate());
		jobVersion.setLastUpdatedBy(subject.getLastUpdatedBy());
		jobVersion.setMainResponsibility(subject.getMainResponsibility());
		jobVersion.setPeopleCategoryCode(subject.getPeopleCategoryCode());
		jobVersion.setVersionNumber(subject.getVersionNumber());
		jobVersion.setWorkingScheduleId(subject.getWorkingScheduleId());
		jobVersion.setId(subject.getId());
		jobVersion.setGradeSetId(subject.getGradeSetId());
		jobVersionMapper.insert(jobVersion);
		subject.setVersionId(jobVersion.getVersionId());

		if(subject.getJobDocuments() != null){
			for (JobDocument jobDoc : subject.getJobDocuments()) {
				jobDoc.setVersionId(subject.getVersionId());
				jobDocumentMapper.insert(jobDoc);
			}
		}
		
		if(subject.getJobValidGrades() != null) {
			for(JobValidGradeDTO validGrade : subject.getJobValidGrades()) {
//				JobValidGrade jobValidGrade = modelMapper.map(validGrade, JobValidGrade.class);
				validGrade.setCreatedBy(subject.getCreatedBy());
				validGrade.setCreationDate(subject.getCreationDate());
				validGrade.setLastUpdatedBy(subject.getLastUpdatedBy());
				validGrade.setLastUpdateDate(subject.getLastUpdateDate());
				validGrade.setId(subject.getId());
				validGrade.setVersionId(subject.getVersionId());
				jobValidGradeMapper.insert(validGrade);
			}
		}
		if(subject.getJobRoles() != null) {
			for(JobRoleDTO role : subject.getJobRoles()) {
//				JobRole jobRole = modelMapper.map(role, JobRole.class);
				role.setId(subject.getId());
				role.setVersionId(subject.getVersionId());
				role.setCreatedBy(subject.getCreatedBy());
				role.setCreationDate(subject.getCreationDate());
				role.setLastUpdatedBy(subject.getLastUpdatedBy());
				role.setLastUpdateDate(subject.getLastUpdateDate());
				jobRoleMapper.insert(role);
			}
		}
		if(subject.getJobSpecifications() != null) {
			for(JobSpecification spec : subject.getJobSpecifications()) {
//				JobSpecification jobSpec = modelMapper.map(spec, JobSpecification.class);
				spec.setId(subject.getId());
				spec.setVersionId(subject.getVersionId());
				spec.setCreatedBy(subject.getCreatedBy());
				spec.setCreationDate(subject.getCreationDate());
				spec.setLastUpdatedBy(subject.getLastUpdatedBy());
				spec.setLastUpdateDate(subject.getLastUpdateDate());
				jobSpecMapper.insert(spec);
			}	
		}
		if(subject.getJobInfos() != null) {
			for(OtherInfoDTO info : subject.getJobInfos()) {
//				JobInfo jobInfo = modelMapper.map(info, JobInfo.class);
				JobInfo jobInfo = new JobInfo();
				jobInfo.setInfoId(info.getInfoId());
				jobInfo.setInfoValue(info.getInfoValue());
				jobInfo.setVersionId(subject.getVersionId());
				jobInfo.setCreatedBy(subject.getCreatedBy());
				jobInfo.setCreationDate(subject.getCreationDate());
				jobInfo.setLastUpdatedBy(subject.getLastUpdatedBy());
				jobInfo.setLastUpdateDate(subject.getLastUpdateDate());
				
				jobInfoMapper.insert(jobInfo);
			}
		}
	}
	
	@Override
	public void deleteHeader(JobDTO subject) {
		jobMapper.deleteByPrimaryKey(subject.getId());
	}

	@Override
	public void deleteDetail(JobDTO subject) {
		JobInfoExample infoExample = new JobInfoExample();
		infoExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		jobInfoMapper.deleteByExample(infoExample);
		
		JobValidGradeExample validGradeExample = new JobValidGradeExample();
		validGradeExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		jobValidGradeMapper.deleteByExample(validGradeExample);
		
		JobRoleExample roleExample = new JobRoleExample();
		roleExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		jobRoleMapper.deleteByExample(roleExample);
		
		JobSpecificationExample specExample = new JobSpecificationExample();
		specExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		jobSpecMapper.deleteByExample(specExample);		

		JobDocumentExample jobdocExample = new JobDocumentExample();
		jobdocExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		jobDocumentMapper.deleteByExample(jobdocExample);

		jobVersionMapper.deleteByPrimaryKey(subject.getVersionId());
	}

	@Override
	public void updateDetail(JobDTO subject) {
		
//		JobVersion jobVersion = modelMapper.map(subject, JobVersion.class);
		JobVersion jobVersion = new JobVersion();
		jobVersion.setCreatedBy(subject.getCreatedBy());
		jobVersion.setCreationDate(subject.getCreationDate());
		jobVersion.setDateFrom(subject.getDateFrom());
		jobVersion.setDateTo(subject.getDateTo());
		jobVersion.setDescription(subject.getDescription());
		jobVersion.setIsKeyJob(subject.getIsKeyJob());
		jobVersion.setIsManager(subject.getIsManager());
		jobVersion.setJobFilePath(subject.getJobFilePath());
		jobVersion.setJobForCode(subject.getJobForCode());
		jobVersion.setJobGroupCode(subject.getJobGroupCode());
		jobVersion.setJobTypeCode(subject.getJobTypeCode());
		jobVersion.setLastUpdateDate(subject.getLastUpdateDate());
		jobVersion.setLastUpdatedBy(subject.getLastUpdatedBy());
		jobVersion.setMainResponsibility(subject.getMainResponsibility());
		jobVersion.setPeopleCategoryCode(subject.getPeopleCategoryCode());
		jobVersion.setVersionNumber(subject.getVersionNumber());
		jobVersion.setWorkingScheduleId(subject.getWorkingScheduleId());
		jobVersion.setGradeSetId(subject.getGradeSetId());
		jobVersion.setId(subject.getId());
		jobVersion.setVersionId(subject.getVersionId());
		
		JobInfoExample jobInfoExample = new JobInfoExample();
		jobInfoExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		jobInfoMapper.deleteByExample(jobInfoExample);
		
		JobValidGradeExample validGradeExample = new JobValidGradeExample();
		validGradeExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		jobValidGradeMapper.deleteByExample(validGradeExample);
		
		JobRoleExample roleExample = new JobRoleExample();
		roleExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		jobRoleMapper.deleteByExample(roleExample);
		
		JobSpecificationExample specExample = new JobSpecificationExample();
		specExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		jobSpecMapper.deleteByExample(specExample);
		
		JobDocumentExample jobdocExample = new JobDocumentExample();
		jobdocExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		jobDocumentMapper.deleteByExample(jobdocExample);
		
		if(subject.getJobInfos() != null) {
			for(OtherInfoDTO info : subject.getJobInfos()) {
				if(info.getInfoId() != null) {
					JobInfo jobInfo = modelMapper.map(info, JobInfo.class);
					jobInfo.setVersionId(subject.getVersionId());
					jobInfo.setCreatedBy(subject.getCreatedBy());
					jobInfo.setCreationDate(subject.getCreationDate());
					jobInfo.setLastUpdatedBy(subject.getLastUpdatedBy());
					jobInfo.setLastUpdateDate(subject.getLastUpdateDate());
					jobInfoMapper.insert(jobInfo);
				}
			}
		}
		if(subject.getJobValidGrades() != null) {
			for(JobValidGradeDTO validGrade : subject.getJobValidGrades()) {
				if(validGrade.getGradeId() != null) {
//					JobValidGrade jobValidGrade = modelMapper.map(validGrade, JobValidGrade.class);
					validGrade.setId(subject.getId());
					validGrade.setVersionId(subject.getVersionId());
					validGrade.setCreatedBy(subject.getCreatedBy());
					validGrade.setCreationDate(subject.getCreationDate());
					validGrade.setLastUpdatedBy(subject.getLastUpdatedBy());
					validGrade.setLastUpdateDate(subject.getLastUpdateDate());
					jobValidGradeMapper.insert(validGrade);
				}
			}
		}
		if(subject.getJobRoles() != null) {
			for(JobRoleDTO role : subject.getJobRoles()) {
				if(role.getManagementTypeCode() != null) {
//					JobRole jobRole = modelMapper.map(role, JobRole.class);
					role.setId(subject.getId());
					role.setVersionId(subject.getVersionId());
					role.setCreatedBy(subject.getCreatedBy());
					role.setCreationDate(subject.getCreationDate());
					role.setLastUpdatedBy(subject.getLastUpdatedBy());
					role.setLastUpdateDate(subject.getLastUpdateDate());
					jobRoleMapper.insert(role);
				}
			}
			
			
			
		}
		if(subject.getJobSpecifications() != null) {
			for(JobSpecification spec : subject.getJobSpecifications()) {
				if(spec.getQualificationCode() != null) {
//					JobSpecification jobSpec = modelMapper.map(spec, JobSpecification.class);
					spec.setId(subject.getId());
					spec.setVersionId(subject.getVersionId());
					spec.setCreatedBy(subject.getCreatedBy());
					spec.setCreationDate(subject.getCreationDate());
					spec.setLastUpdatedBy(subject.getLastUpdatedBy());
					spec.setLastUpdateDate(subject.getLastUpdateDate());
					jobSpecMapper.insert(spec);
				}
			}	
		}		
		
		if(subject.getJobDocuments() != null){
			for (JobDocument jobDoc : subject.getJobDocuments()) {
				jobDoc.setVersionId(subject.getVersionId());
				jobDocumentMapper.insert(jobDoc);
			}
		}
		
		jobVersionMapper.updateByPrimaryKey(jobVersion);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getNextHeaderId() {
		return idGenerator.getNextHeaderId("WOS_JOBS_S");
	}

	@Override
	@Transactional(readOnly = true)
	public JobDTO findByIdAndVersionNumber(Long id, Integer versionNumber) {
		return jobFinder.findByIdAndVersionNumber(id, versionNumber);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Integer> findVersionsById(Long id) {
		return jobFinder.findVersionsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public void validate(JobDTO subject) throws ValidationException {
		jobValidator.validate(subject);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Job> findByExample(JobExample example) {
		return jobMapper.selectByExampleWithBLOBs(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<JobDTO> findByInquiry(JobDTO example) {
		return jobFinder.findByInquiry(example);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isManager(Long id) {
		return jobFinder.isManager(id) == 1;
	}

	@Override
	@Transactional(readOnly=true)
	public JobDTO findById(Long id) {
		return jobFinder.findById(id, new Date());
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countByExample(JobExample example) {
		return jobMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isHaveFuture(Long id) {
		return jobFinder.isHaveFuture(id) == 1;
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobRoleDTO> getRoles(Long id) {
		return jobFinder.findJobRoles(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobValidGradeDTO> getValidGrades(Long id) {
		return jobFinder.findValidGrades(id, null, null, new RowBounds());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<JobValidGradeDTO> getValidGrades(Long id, String gradeCode,
			String gradeName, int limit, int offset) {
		return jobFinder.findValidGrades(id, gradeCode, gradeName, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer countValidGrades(Long id, String gradeCode, String gradeName) {
		return jobFinder.countValidGrades(id, gradeCode, gradeName);
	}

	@Override
	@Transactional(readOnly = true)
	public JobDTO findByUuid(UUID uuid) {
		return jobFinder.findByUuid(uuid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Job> findByExample(JobExample example, int limit, int offset) {
		example.setOrderByClause("JOB_CODE");
		return jobMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public JobDTO findByCode(String jobCode) {
		return jobFinder.findByCode(jobCode, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	@Transactional(readOnly = true)
	public JobDTO findByCodeAndName(String jobCode, String jobName) {
		return jobFinder.findByCodeAndName(jobCode, jobName, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	@Transactional(readOnly = true)
	public String getValueOtherInfoByInfoId(Long jobId, Long infoId,
			Date effectiveOnDate) {
		return jobFinder.getValueOtherInfoByInfoId(jobId, infoId, effectiveOnDate);
	}

	@Override
	@Transactional(readOnly = true)
	public JobDTO findJobForByCodeAndName(String jobCode, String jobName) {
		return jobFinder.findJobForByCodeAndName(jobCode, jobName, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobDTO> findByExample(JobDTO jobDTO, List<String> jobGroups, boolean canBeActing, int limit, int offset) {
		return jobFinder.findByExampleAndJobGroups(jobDTO, jobGroups, canBeActing, companyServiceImpl.getBusinessGroupIdByCompany(jobDTO.getCompanyId()), new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobDTO> findByExample(JobDTO jobDTO) {
		return jobFinder.findByExample(jobDTO);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean isPIC(Long personId, Long requestorId) {
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isKeyjob(Long id) {
		return jobFinder.isKeyJob(id) == 1;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean isFutureExist(Long jobId) {
		return jobFinder.isFutureExist(jobId) > 0;
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobDTO> getActiveJob(JobDTO jobDTO, int limit, int offset) {
		return jobFinder.findActiveJob(jobDTO, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<JobDTO> getActiveJob2(JobDTO jobDTO, int limit, int offset) {
		return jobFinder.findActiveJob2(jobDTO, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countActiveJob(JobDTO jobDTO) {
		return jobFinder.countActiveJob(jobDTO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobDTO> findByGradeId(JobDTO jobDTO, Long validGradeId, Long organizationId,
			int limit, int offset) {
		return jobFinder.findByGradeId(jobDTO, validGradeId, organizationId, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countByGradeId(JobDTO jobDTO, Long validGradeId, Long organizationId) {
		return jobFinder.countByGradeId(jobDTO, validGradeId, organizationId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobSpecificationDTO> getSpecifications(Long id) {
		return jobFinder.findSpecifications(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<co.id.fifgroup.basicsetup.dto.OtherInfoDTO> getJobOtherInfos(
			Long id) {
		return jobFinder.findJobOtherInfos(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Long> getGradeSetIdIdByExample(JobDTO example) {
		return jobFinder.getGradeSetIdByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<JobValidGradeDTO> getValidGradesForPromotion(Long id, Date effectiveDate) {
		return jobFinder.findValidGradesForPromotion(id, DateUtils.truncate(effectiveDate, Calendar.DATE));
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(JobDTO jobDTO, List<String> jobGroups, boolean canBeActing) {
		return jobFinder.countByExampleAndJobGroups(jobDTO, jobGroups, canBeActing, companyServiceImpl.getBusinessGroupIdByCompany(jobDTO.getCompanyId()));
	}

	@Override
	@Transactional(readOnly=true)
	public JobDTO findIsKeyJobById(Long id) {
		return jobFinder.findIsKeyJobById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Long findVersionIdById(Long id) {
		return jobFinder.findVersionIdById(id);
	}

	@Override
	public JobDTO findLastVersionByCode(String code, Long companyId) {
		return jobFinder.findLastVersionByCode(code, companyId);
	}

	@Override
	public Job findByPrimaryKey(Long jobId) {
		return jobMapper.selectByPrimaryKey(jobId);
	}

	@Override
	public JobDTO findById(Long id, Date processDate) {
		return jobFinder.findById(id, processDate);
	}

	@Override
	public boolean isActiveJob(Long jobId, Date effectiveOnDate) {
		JobVersionExample example = new JobVersionExample();
		example.createCriteria().andIdEqualTo(jobId)
			.andDateFromLessThanOrEqualTo(effectiveOnDate)
			.andDateToGreaterThanOrEqualTo(effectiveOnDate);
		return jobVersionMapper.countByExample(example) > 0;
	}

	@Override
	public List<GradeDTO> findValidGradeByJob(Long jobId, String gradeCode, String gradeName, int limit, int offset) {
		return jobFinder.findValidGradeByJob(jobId, gradeCode, gradeName, new RowBounds(offset, limit));
	}
	
	/*recommit GSK*/
	@Override
	public int countValidGradeByJob(Long jobId, String gradeCode, String gradeName, Date effectiveOnDate  ) {
		return jobFinder.countValidGradeByJob(jobId, gradeCode, gradeName, effectiveOnDate  ); /*recommit GSK*/
	}
	/*end recommit GSK*/
	
	/* add by RIM ticket 14040715181325 */
	@Override
	@Transactional(readOnly = true)
	public List<JobDTO> findByOrgId(JobDTO jobDTO, Long organizationId, int limit, int offset) {
		return jobFinder.findByOrgId(jobDTO, organizationId, new RowBounds(
				offset, limit));
	}

	@Override
	public Integer countByOrgId(JobDTO jobDTO, Long organizationId) {
		return jobFinder.countByOrgId(jobDTO, organizationId);
	}
	/* end add by RIM */
	
	@Override
	public List<Job> findJobAvailableInMpp(JobDTO example, List<Long> organizations, int limit, int offset) {
		return jobFinder.findJobAvailableInMpp(example, organizations, new RowBounds(offset, limit));
	}

	@Override
	public Integer countJobAvailableInMpp(JobDTO example, List<Long> organizations) {
		return jobFinder.countJobAvailableInMpp(example, organizations);
	}
	
	@Override
	public List<Job> findJobAvailableInMppAndReadyToCreateVacancy(JobDTO example, List<Long> organizations, Long branchId, int limit, int offset) {
		return jobFinder.findJobAvailableInMppAndReadyToCreateVacancy(example, organizations, branchId, new RowBounds(offset, limit));
	}

	@Override
	public Integer countJobAvailableInMppAndReadyToCreateVacancy(JobDTO example, List<Long> organizations, Long branchId) {
		return jobFinder.countJobAvailableInMppAndReadyToCreateVacancy(example, organizations, branchId);
	}

	@Override
	public List<JobDTO> findByOrgIdIn(JobDTO jobDTO, List<Integer> organizationIds, int limit, int offset) {
		return jobFinder.findByOrgIdIn(jobDTO, organizationIds, new RowBounds(offset, limit));
	}

	@Override
	public Integer countByOrgIdIn(JobDTO jobDTO, List<Integer> organizationIds) {
		return jobFinder.countByOrgIdIn(jobDTO, organizationIds);
	}
	//14040715181325_CR HCMS - MPP_JAR

	@Override
	public List<JobDTO> findByOrgIdInNoRowbounds(JobDTO jobDTO, List<Integer> organizationIds) {
		return jobFinder.findByOrgIdIn(jobDTO, organizationIds);
	}
	//end add 14040715181325_CR HCMS - MPP_JAR

	@Override
	public List<LookupDependent> getDocumentList() {
		return jobFinder.getDocumentList();
	}
	
	// start added by WLY, Phase 2 Training Admin
	@Override
	public List<JobDTO> findJobByCompanyName(String jobCode, String jobName,
			String companyName, Long companyId, String jobGroupCode, int limit, int offset) {
		return jobFinder.findJobByCompanyName(securityServiceImpl.getSecurityProfile().getBusinessGroupId(),
				jobCode, jobName, companyName, companyId, jobGroupCode, new RowBounds(offset, limit));
	}

	@Override
	public Integer countJobByCompanyName(String jobCode, String jobName,
			String companyName, Long companyId, String jobGroupCode) {
		return jobFinder.countJobByCompanyName(securityServiceImpl.getSecurityProfile().getBusinessGroupId(),
				jobCode, jobName, companyName, companyId, jobGroupCode);
	}
	// end added by WLY, Phase 2 Training Admin
	
	
	// start added by jatis, phase 2 career
	@Override
	public List<JobDTO> findJobByJobCode(JobDTO example, List<String> jobCodes, int limit, int offset){
		if(jobCodes != null && jobCodes.size() > 0){
			return jobFinder.findJobByJobCode(example,jobCodes, new RowBounds(offset, limit));
		}else
			return null;
	}
	
	@Override
	public Integer countJobByJobCode(JobDTO example, List<String> jobCodes) {
		if(jobCodes != null && jobCodes.size() > 0){
			return jobFinder.countJobByJobCode(example, jobCodes);
		} else
			return 0;
		
	}
	
	@Override
	public List<JobDTO> findJobByJobName(String jobName, int limit, int offset) {
		// TODO Auto-generated method stub
		return jobFinder.findJobByJobName(jobName, new RowBounds(offset, limit));
	}

	@Override
	public Integer countJobByJobName(String jobName) {
		// TODO Auto-generated method stub
		return jobFinder.countJobByJobName(jobName);
	}
	// end added by jatis, phase 2 career



	
}
