package co.id.fifgroup.workstructure.validation;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.workstructure.domain.JobExample;
import co.id.fifgroup.workstructure.service.GradeSetSetupService;
import co.id.fifgroup.workstructure.service.JobSetupService;

import co.id.fifgroup.basicsetup.constant.OtherInfoFormName;
import co.id.fifgroup.basicsetup.domain.OtherInfoComponent;
import co.id.fifgroup.basicsetup.domain.OtherInfoDetail;
import co.id.fifgroup.basicsetup.service.OtherInfoComponentService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.upload.UploadUtil;
import co.id.fifgroup.workstructure.dao.JobMapper;
import co.id.fifgroup.workstructure.dto.GradeSetDTO;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.JobRoleDTO;
import co.id.fifgroup.workstructure.dto.JobValidGradeDTO;
import co.id.fifgroup.workstructure.dto.OtherInfoDTO;
import co.id.fifgroup.workstructure.finder.JobFinder;

@Component
public class JobValidator implements Validator<JobDTO>{
	
	@Autowired
	private JobFinder jobFinder;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private JobSetupService jobSetupServiceImpl;
	@Autowired
	private GradeSetSetupService gradeSetSetupServiceImpl;
	@Autowired
	private OtherInfoComponentService otherInfoComponentServiceImpl;
	
	public static final String UNIQUE_FIELD = "uniqueField";
	public static final String JOB_CODE = "jobCode";
	public static final String JOB_NAME = "jobName";
	public static final String JOB_FOR_CODE = "jobForCode";
	public static final String JOB_TYPE_CODE = "jobTypeCode";
	public static final String PEOPLE_CATEGORY_CODE = "peopleCategory";
	public static final String JOB_GROUP = "jobGroup";
	public static final String GRADE_SET_NAME = "gradeSetName";
	public static final String JOB_ROLE = "jobRole";
	public static final String JOB_VALID_GRADE = "jobValidGrade";
	public static final String JOB_VALID_OTHER_INFO = "jobValidOtherInfo";
	public static final String JOB_DOCUMENT_EMPTY = "jobDocumentEmpty";
	public static final String JOB_DOCUMENT = "jobDocument";
	
	
	
	@Override
	public void validate(JobDTO subject) throws ValidationException {
		String jobCode = subject.getJobCode();
		String jobName = subject.getJobName();
		String jobForCode = subject.getJobForCode();
		String jobTypeCode = subject.getJobTypeCode();
		String peopleCategoryCode = subject.getPeopleCategoryCode();
		String jobGroup = subject.getJobGroupCode();
		Long gradeSetId = subject.getGradeSetId();
		List<JobValidGradeDTO> jobValidGrades = subject.getJobValidGrades();
		int countCode = 0;
		int countName = 0;

		if(subject.getId() == null) {
			JobExample jobExample = new JobExample();
			jobExample.createCriteria().andJobCodeLikeInsensitive(jobCode).
			andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			countCode = jobMapper.countByExample(jobExample);
			
			jobExample.clear();
			jobExample.createCriteria().andJobNameLikeInsensitive(jobName).
			andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			countName = jobMapper.countByExample(jobExample);
		}
		
		Map<String, String> mapValidate = new HashMap<>();

		if(jobName == null || jobName.equals("")) {
			mapValidate.put(JOB_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.jobName") }));
		}
		else if(subject.getId() == null){
			if(countName > 0) {
				mapValidate.put(JOB_NAME, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.jobName") }));
			}
		}
		
		if(jobCode == null || jobCode.equals("")) {
			mapValidate.put(JOB_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.jobCode") }));
		}
		else if(!ValidationRule.isAlphaNumeric(jobCode)) {
			mapValidate.put(JOB_CODE, Labels.getLabel("wos.cannotContainSpaceOrCharacter", new Object[] { Labels.getLabel("wos.jobCode") }));
		}
		else if(subject.getId() == null){
			if(countCode > 0) {
				mapValidate.put(JOB_CODE, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.jobCode") }));
			}
		}			
		
		if(jobForCode == null || jobForCode.equals("")) {
			mapValidate.put(JOB_FOR_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.jobFor") }));
		}
		
		if(jobTypeCode == null || jobTypeCode.equals("")) {
			mapValidate.put(JOB_TYPE_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.jobType") }));
		}
		
		if(peopleCategoryCode == null || peopleCategoryCode.equals("")) {
			mapValidate.put(PEOPLE_CATEGORY_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.peopleCategory") }));
		}
		
		if(jobGroup == null || jobGroup.equals("")) {
			mapValidate.put(JOB_GROUP, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.jobGroup") }));
		}
		
		if(!subject.isUpload()) {
			if(gradeSetId == null) {
				mapValidate.put(GRADE_SET_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.gradeSet") }));
			} else {
				GradeSetDTO gradeSet = gradeSetSetupServiceImpl.findById(gradeSetId);
				if(gradeSet == null)
					mapValidate.put(GRADE_SET_NAME, Labels.getLabel("common.inactive"));
			}
		}
		
		if(jobValidGrades != null) {
			if(jobValidGrades.size() == 0)
				mapValidate.put(JOB_VALID_GRADE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.grade") }));
		}
		
		if(subject.getOtherInfoComponent()!=null){
			if(!otherInfoComponentServiceImpl.validate(subject.getOtherInfoComponent())) {
				mapValidate.put("", "");
			}
		}
		
		// start added by WLY, ITSM 15061610224064, 22/06/2015
		// if job from upload job then no need to validate documents
		if (!subject.isUpload()) {
		// end added by WLY, ITSM 15061610224064, 22/06/2015
			if(subject.isValidJobsDocuments()){
				if(subject.getJobDocuments() !=null && subject.getJobDocuments().size()>0){
				}else{
					mapValidate.put(JOB_DOCUMENT_EMPTY, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.documentList") }));
				}
			}else{
				mapValidate.put(JOB_DOCUMENT, "");
			}
		// start added by WLY, ITSM 15061610224064, 22/06/2015
		}
		// end added by WLY, ITSM 15061610224064, 22/06/2015
		if(mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}		
	}

}
