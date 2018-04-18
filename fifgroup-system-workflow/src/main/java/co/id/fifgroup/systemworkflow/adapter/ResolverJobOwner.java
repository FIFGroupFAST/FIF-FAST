package co.id.fifgroup.systemworkflow.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.systemworkflow.constants.BasedOn;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.basicsetup.dto.OtherInfoDetailDTO;
import co.id.fifgroup.basicsetup.service.OtherInfoService;
import co.id.fifgroup.core.domain.transaction.CommonIcpSubmissionTrx;
import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.core.service.OtherInfoServiceAdapter;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class ResolverJobOwner {

	/**
	 * get job owner when job id equals job info 
	 * @param basedLineApprover for based line to find approver
	 * @param approver 
	 * @param trx for based line to get job owner approver 
	 * @return list approver
	 */
	public static List<AVMApprovalHistory> getApprovers(PersonDTO personBasedLine, AVMApprover approver, CommonTrx trx) {
		List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
		AVMApprovalHistory history = new AVMApprovalHistory();		

		List<PersonAssignmentDTO> persons = null;
		PersonDTO person = null;
		
		// get based line organization
		Long basedJob = null;
		Long basedGroupId = null;
		Long basedCompanyId = null;
		if (approver.getBasedOn() == BasedOn.NewAssignment.getCode()) {
			// hardcoded for this interface because doesn't want to change on original CommonTrx
			if (trx instanceof CommonIcpSubmissionTrx) {
				CommonIcpSubmissionTrx icpSubmission = (CommonIcpSubmissionTrx) trx;
				basedJob = icpSubmission.getJobDestinationId();
				basedGroupId = icpSubmission.getBusinessGroupId();
				basedCompanyId = icpSubmission.getCompanyId();
			}
			
		} else {
			basedJob = personBasedLine.getPrimaryAssignmentDTO().getJobId();			
			basedGroupId = personBasedLine.getGroupId();
			basedCompanyId = personBasedLine.getCompanyId();
		}
		
		if (basedGroupId == null) basedGroupId = personBasedLine.getGroupId();

		OtherInfoDetailDTO infoId = getOtherInfoService().getOtherInfoDetail("JOB", basedGroupId, basedCompanyId, "JOB_OWNER");
		if (null != infoId) {
			String jobOwner = getJobSetupService().getValueOtherInfoByInfoId(basedJob, infoId.getOtherInfoDtlId(), new Date());
//			person = getPersonService().getLastPersonInfo(Long.parseLong(jobOwner), new Date());
//			persons = getPersonService().getPersonByJob(Long.parseLong(jobOwner), new Date());
			persons = getPersonService().selectPersonByAssignment(Long.parseLong(jobOwner), null, null, "PICDO", null, basedCompanyId, null);
			if (persons != null && !persons.isEmpty()) {
				person = new PersonDTO();
				person.setPrimaryAssignmentDTO(persons.get(0).getPrimaryAssignmentDTO());
				person.setPersonUUID(persons.get(0).getPersonUUID());
			}
		}
		
		if (person != null ) {
			//vacation rule
			VacationRule vacationRule = null;
			vacationRule = getVacationRuleService().getActiveVacationRuleByApproverId(person.getPersonUUID(), DateUtil.truncate(new Date()));
			
			if (vacationRule != null) {
				history.setApproverId(vacationRule.getSubstituteApproverId());
				history.setVacationApproverId(person.getPersonUUID());
				history.getApproverUUIDs().add(vacationRule.getSubstituteApproverId());
			} else {
				JobDTO job = getJobSetupService().findById(person.getPrimaryAssignmentDTO().getJobId(), new Date());
//				history.setApproverId(person.getPersonUUID());
				history.setApproverId(job.getJobUuid());
				history.setOrganizationId(person.getPrimaryAssignmentDTO().getOrganizationId());
				history.getApproverUUIDs().add(person.getPersonUUID());
			}
			
			approvalHistories.add(history);
				
		} 
		
		return approvalHistories;
	}
	
	public static JobSetupService getJobSetupService() {
		return (JobSetupService) ApplicationContextUtil.getApplicationContext().getBean("jobSetupServiceImpl");
	}
	
	public static OtherInfoService getOtherInfoService() {
		return (OtherInfoService) ApplicationContextUtil.getApplicationContext().getBean("otherInfoServiceImpl");
	}
	
	public static VacationRuleService getVacationRuleService() {
		return (VacationRuleService) ApplicationContextUtil.getApplicationContext().getBean("vacationRuleServiceImpl");
	}
	
	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}
	
}
