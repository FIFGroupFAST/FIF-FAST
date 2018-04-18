package co.id.fifgroup.systemworkflow.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.dto.VacationRuleDTO;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;
import co.id.fifgroup.systemworkflow.validation.VacationRuleValidator;

import co.id.fifgroup.avm.util.StringUtil;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.systemworkflow.dao.VacationRuleFinder;
import co.id.fifgroup.systemworkflow.dao.VacationRuleMapper;

@Transactional
@Service
public class VacationRuleServiceImpl implements VacationRuleService {

	@Autowired
	private VacationRuleMapper vacationRuleMapper;
	@Autowired
	private VacationRuleFinder vacationRuleFinder;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private VacationRuleValidator vacationRuleValidator;

	@Override
	public List<VacationRule> getAllVacationRule() {
		return vacationRuleMapper.selectByExampleWithBLOBs(null);
	}

	@Override
	public List<VacationRule> getVacationRuleByApproverId(UUID approverId) {
		return vacationRuleMapper.selectByExampleWithBLOBs(null);
	}

	@Override
	public void saveVacationRule(VacationRule vacationRule) throws ValidationException {
		vacationRuleValidator.validate(vacationRule);
		Long createdBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date createdDate = new Date();
		if (vacationRule.getVacationRuleId() == null) {
			vacationRule.setCreatedBy(createdBy);
			vacationRule.setCreatedDate(createdDate);
			vacationRule.setLastUpdatedBy(createdBy);
			vacationRule.setLastUpdatedDate(createdDate);
			vacationRuleMapper.insert(vacationRule);			
		} else {
			vacationRule.setLastUpdatedBy(createdBy);
			vacationRule.setLastUpdatedDate(createdDate);
			vacationRuleMapper.updateByPrimaryKeyWithBLOBs(vacationRule);			
		}
	}

	@Override
	public void deleteVacationRule(VacationRule vacationRule) {
		vacationRuleMapper.deleteByPrimaryKey(vacationRule.getVacationRuleId());
	}

	@Override
	public List<VacationRuleDTO> getVacationByCriteria(String approverName,
			String substituteApproverName, Date startDate, Date endDate) {
		approverName = StringUtil.wrappedByWildCard(approverName);
		substituteApproverName = StringUtil.wrappedByWildCard(substituteApproverName);
		Date dateFrom = startDate;
		Date dateTo = endDate; 
		if (startDate == null) {
			Calendar cal = GregorianCalendar.getInstance();
			cal.clear();
			cal.set(1990, 11, 31);
			dateFrom = DateUtils.truncate(cal.getTime(), Calendar.DATE);
		}
		if (endDate == null) {
			dateTo = DateUtils.truncate(DateUtil.MAX_DATE, Calendar.DATE);
		}
		return vacationRuleFinder.getVacationRule(approverName, substituteApproverName, dateFrom, dateTo);
	}

	@Override
	public int countActiveVacationRule(VacationRule vacationRule) {
		return vacationRuleFinder.countActiveVacationRule(vacationRule.getApproverId(), vacationRule.getEffectiveDateFrom(), vacationRule.getEffectiveDateTo());
	}

	@Override
	public boolean isCurrent(VacationRule vacationRule) {
		Calendar today = Calendar.getInstance();
		Calendar startDate = Calendar.getInstance(); 
		startDate.setTime(vacationRule.getEffectiveDateFrom());
		Calendar endDate = Calendar.getInstance(); 
		endDate.setTime(vacationRule.getEffectiveDateTo());
		today = DateUtil.truncate(today);
		startDate = DateUtil.truncate(startDate);
		endDate = DateUtil.truncate(endDate);
		if(startDate.before(today) || startDate.equals(today)) {
			if(endDate.equals(today) || endDate.after(today)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isFuture(VacationRule vacationRule) {
		Calendar today = Calendar.getInstance();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(vacationRule.getEffectiveDateFrom());
		today = DateUtil.truncate(today);
		startDate = DateUtil.truncate(startDate);
		if(startDate.after(today))
			return true;
		
		return false;
	}

	@Override
	public VacationRule getActiveVacationRuleByApproverId(UUID approverId,
			Date effectiveOnDate) {
		VacationRule lastVacationRule = null;
		VacationRule vacationRule = null;
		vacationRule = vacationRuleFinder.getActiveVacationRuleByApproverId(approverId, effectiveOnDate);
		if (vacationRule != null) {
			lastVacationRule = vacationRule;
			do {
				vacationRule = vacationRuleFinder.getActiveVacationRuleByApproverId(vacationRule.getSubstituteApproverId(), effectiveOnDate);
				if (vacationRule != null)
					lastVacationRule = vacationRule;
			} while (vacationRule != null);
		}
		return lastVacationRule;
	}

	@Override
	public List<VacationRuleDTO> getVacationRuleByApproverId(UUID approverId, String substituteApproverName,
			Date startDate, Date endDate) {
		substituteApproverName = StringUtil.wrappedByWildCard(substituteApproverName);
		Date dateFrom = startDate;
		Date dateTo = endDate; 
		if (startDate == null) {
			Calendar cal = GregorianCalendar.getInstance();
			cal.clear();
			cal.set(1990, 11, 31);
			dateFrom = DateUtils.truncate(cal.getTime(), Calendar.DATE);
		}
		if (endDate == null) {
			dateTo = DateUtils.truncate(DateUtil.MAX_DATE, Calendar.DATE);
		}
		return vacationRuleFinder.getVacationRuleByApproverId(approverId, substituteApproverName, dateFrom, dateTo);
	}
	
	
}
