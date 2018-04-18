package co.id.fifgroup.systemworkflow.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.personneladmin.service.PeopleTypeService;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;

public class ResolverEmployee {

	public static List<AVMApprovalHistory> getApprovers(AVMApprover approver) {
		List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
		AVMApprovalHistory history = new AVMApprovalHistory();
		
		if (getPeopleTypeService().isActivePersonByUUID(approver.getApproverId())) {
			//vacation rule
			VacationRule vacationRule = getVacationRuleService().getActiveVacationRuleByApproverId(approver.getApproverId(), new Date());
			
			if (vacationRule != null) {
				history.setApproverId(vacationRule.getSubstituteApproverId());
				history.setVacationApproverId(approver.getApproverId());
				history.getApproverUUIDs().add(vacationRule.getSubstituteApproverId());
			} else {
				history.setApproverId(approver.getApproverId());
				history.getApproverUUIDs().add(approver.getApproverId());
			}
			
			approvalHistories.add(history);
		}
		return approvalHistories;
	}
	
	public static VacationRuleService getVacationRuleService() {
		return (VacationRuleService) ApplicationContextUtil.getApplicationContext().getBean("vacationRuleServiceImpl");
	}
	
	public static PeopleTypeService getPeopleTypeService() {
		return (PeopleTypeService) ApplicationContextUtil.getApplicationContext().getBean("peopleTypeService");
	}
}
