package co.id.fifgroup.workstructure.validation;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.workstructure.constant.BranchSizeType;
import co.id.fifgroup.workstructure.service.BranchSizeSetupService;

import co.id.fifgroup.core.upload.UploadUtil;
import co.id.fifgroup.workstructure.dto.BranchSizeDTO;

@Component
public class BranchSizeValidator implements Validator<BranchSizeDTO>{
	
	public static final String HISTORY = "history";
	public static final String ORGANIZATION = "organization";
	public static final String EFFECTIVE_START_DATE = "effectiveStartDate";
	public static final String EFFECTIVE_END_DATE = "effectiveEndDate";
	public static final String BRANCH_SIZE = "branchSize";
	
	@Autowired
	private BranchSizeSetupService branchSizeSetupServiceImpl;
	
	@Override
	public void validate(BranchSizeDTO subject) throws ValidationException {
		Long organization = subject.getOrganizationId();
		Date effectiveStartDate = subject.getEffectiveStartDate();
		String branchSize = subject.getSizeCode();
		
		Map<String, String> violations = new HashMap<>();
		if (organization != null) {
			List<Long> histories = branchSizeSetupServiceImpl.findHistoriesById(subject.getOrganizationId()); 
			if(histories.size() > 0) {
				Long lastHistoryId = histories.get(histories.size()-1);
				BranchSizeDTO lastBranchSize = branchSizeSetupServiceImpl.findByIdAndHistoryId(lastHistoryId, subject.getOrganizationId());
				if(lastBranchSize.getEffectiveStartDate().after(new Date()))
					violations.put(HISTORY, Labels.getLabel("wos.haveAFuture"));
				else if(lastBranchSize.getEffectiveStartDate().before(new Date()) && lastBranchSize.getEffectiveEndDate().after(new Date())){
					Calendar dateFromFuture = Calendar.getInstance();
					dateFromFuture.setTime(subject.getEffectiveStartDate());
					dateFromFuture.add(Calendar.DAY_OF_MONTH, -1);
//					violations.put(UploadUtil.HEADER_ID, lastBranchSize.getOrganizationId().toString());
					lastBranchSize.setEffectiveEndDate(dateFromFuture.getTime());
					System.out.println("update lastHistory");
					branchSizeSetupServiceImpl.update(lastBranchSize);
				}
			}
		}
		
		if(!subject.isUpload()) {
			if(organization == null)
				violations.put(ORGANIZATION, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationName") }));
			
			if(effectiveStartDate == null)
				violations.put(EFFECTIVE_START_DATE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.effectiveOnDate") }));
			else if(effectiveStartDate.before(DateUtil.truncate(new Date())))
				violations.put(EFFECTIVE_START_DATE, Labels.getLabel("common.effectiveDateFromMustBeHigherThanToday"));
			else if(effectiveStartDate.equals(DateUtil.truncate(new Date())))
				violations.put(EFFECTIVE_START_DATE, Labels.getLabel("common.effectiveDateFromMustBeHigherThanToday"));
			
			if(subject.getEffectiveEndDate() != null) {
				if(DateUtils.truncate(subject.getEffectiveEndDate(), Calendar.DATE).before(DateUtils.truncate(new Date(), Calendar.DATE))) {
					violations.put(EFFECTIVE_END_DATE, Labels.getLabel("common.effectiveDateToMustBeHigherThanOrEqualToEffectiveDateFrom"));
				}
			}
			
			if(branchSize != null) {
				if(branchSize.equals(BranchSizeType.SELECT.toString()))
					violations.put(BRANCH_SIZE, Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("wos.branchSize") }));
			}			

		}		
		if(violations.size() > 0) {
			throw new ValidationException(violations);
		}
	}
}
