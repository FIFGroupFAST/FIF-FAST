package co.id.fifgroup.workstructure.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.workstructure.constant.BranchSizeType;
import co.id.fifgroup.workstructure.domain.BranchSizeUploadStageExample;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.BranchSizeSetupService;
import co.id.fifgroup.workstructure.service.BranchSizeUploadService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.workstructure.dto.BranchSizeStageDTO;

@Component
public class BranchSizeUploadValidator implements Validator<BranchSizeStageDTO>{
	
	private Logger logger = LoggerFactory.getLogger(BranchSizeUploadValidator.class);

	private final String EFFECTIVE_START_DATE = "effective start date";
	private final String ORGANIZATION = "organization";
	private final String BRANCH_SIZE = "branch size";
	
	@Autowired
	private BranchSizeUploadService branchSizeUploadServiceImpl;
	@Autowired
	private OrganizationSetupService organizationSetupServiceImpl;
	@Autowired
	private BranchSizeSetupService branchSizeSetupServiceImpl;
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Override
	public void validate(BranchSizeStageDTO subject) throws ValidationException {
		Map<String, String> vex = new HashMap<String, String>();
		
		dateFromValidate(subject, vex);
		orgCodeValidate(subject, vex);
		sizeCodeValidate(subject, vex);		
		
		if(vex.size() > 0)
			throw new ValidationException(vex);
	}
	
	private void dateFromValidate(BranchSizeStageDTO subject, Map<String, String> vex) {
		if(subject.getEffectiveStartDate() == null) {
			vex.put(EFFECTIVE_START_DATE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.effectiveStartDate") }));
		}
		else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				Date dateFrom = sdf.parse(sdf.format(subject.getEffectiveStartDate()));
				if(dateFrom.compareTo(DateUtil.truncate(new Date())) <= 0)
					vex.put(EFFECTIVE_START_DATE, Labels.getLabel("common.effectiveDateFromMustBeHigherThanToday"));
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	private void sizeCodeValidate(BranchSizeStageDTO subject, Map<String, String> vex) {
		if(subject.getSizeCode() == null) {
			vex.put(BRANCH_SIZE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.branchSize") }));
		} else {
			boolean isExist = false;
			for(BranchSizeType size : BranchSizeType.values()) {
				if(!size.equals(BranchSizeType.SELECT)) {
					if(size.getDesc().toUpperCase().equals(subject.getSizeCode().toUpperCase())) {
						isExist = true;
						break;
					}
				}
			}
			if(!isExist)
				vex.put(BRANCH_SIZE, Labels.getLabel("common.isNotExist", new Object[] { Labels.getLabel("wos.branchSize") }));
		}
	}
	
	private void orgCodeValidate(BranchSizeStageDTO subject, Map<String, String> vex) {
		if(subject.getOrganizationCode() == null) {
			vex.put(ORGANIZATION, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationCode") }));
		} else {
			int countCode = 0;
			
			Organization org = organizationSetupServiceImpl.findByCode(subject.getOrganizationCode(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			if(org == null) {
				vex.put(ORGANIZATION, Labels.getLabel("common.isNotExist", new Object[] { Labels.getLabel("wos.organization") }));
			}
			else {
				subject.setOrganizationId(org.getId());
				
				//get future in staging
				BranchSizeUploadStageExample example = new BranchSizeUploadStageExample();
				example.createCriteria().andOrganizationIdEqualTo(subject.getOrganizationId())
				.andIsClosedEqualTo(false);
				countCode = branchSizeUploadServiceImpl.countByExample(example);			
				
				if(countCode > 0) {
					vex.put(BRANCH_SIZE, Labels.getLabel("common.isAlreadyUploaded", new Object[] { Labels.getLabel("wos.code") }));
				}
				
				//get future in branch size setup0
				if(branchSizeSetupServiceImpl.isFutureExist(subject.getOrganizationId()))	
					vex.put(BRANCH_SIZE, Labels.getLabel("common.cannotBeUploadedThereIsFutureVersionFor", new Object[] { Labels.getLabel("wos.code") }));
			}
		}
	}

}
