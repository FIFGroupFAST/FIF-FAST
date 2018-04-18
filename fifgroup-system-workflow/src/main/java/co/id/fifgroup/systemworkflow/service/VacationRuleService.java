package co.id.fifgroup.systemworkflow.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.dto.VacationRuleDTO;

public interface VacationRuleService {

	public List<VacationRule> getAllVacationRule();
	
	public List<VacationRule> getVacationRuleByApproverId(UUID approverId);
	
	public void saveVacationRule(VacationRule vacationRule) throws ValidationException;
	
	public void deleteVacationRule(VacationRule vacationRule);
	
	public List<VacationRuleDTO> getVacationByCriteria(String approverName, String substituteApproverName, Date startDate, Date endDate);
	
	public int countActiveVacationRule(VacationRule vacationRule);
	
	public boolean isCurrent(VacationRule vacationRule);
	
	public boolean isFuture(VacationRule vacationRule);
	
	public VacationRule getActiveVacationRuleByApproverId(UUID approverId, Date effectiveOnDate);
	
	public List<VacationRuleDTO> getVacationRuleByApproverId(UUID approverId, String substituteApproverName, Date startDate, Date endDate);
}
