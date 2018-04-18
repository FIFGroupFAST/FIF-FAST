package co.id.fifgroup.workstructure.validation;

import java.math.BigDecimal;
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

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.workstructure.domain.LobUploadStageExample;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.LobSetupService;
import co.id.fifgroup.workstructure.service.LobUploadService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.LobElementDTO;
import co.id.fifgroup.workstructure.dto.LobStageDTO;

@Component
public class LobUploadValidator implements Validator<LobStageDTO>{
	
	private Logger logger = LoggerFactory.getLogger(LobUploadValidator.class);

	private final String EFFECTIVE_START_DATE = "effective start date";
	private final String PRODUCT_CODE = "product";
	private final String PERCENTAGE = "percentage";
	private final String ORGANIZATION = "organization";
	private final String JOB = "job";
	private final String lookupProduct = "WSU_PRODUCT";
	
	@Autowired
	private OrganizationSetupService organizationSetupServiceImpl;
	@Autowired
	private JobSetupService jobSetupServiceImpl;
	@Autowired
	private LobUploadService lobUploadServiceImpl;
	@Autowired
	private LookupService lookupServiceImpl;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private LobSetupService lobSetupServiceImpl;
	
	@Override
	public void validate(LobStageDTO subject) throws ValidationException {
		Map<String, String> vex = new HashMap<String, String>();
		
		dateFromValidate(subject, vex);
		organizationValidate(subject, vex);
		jobValidate(subject, vex);
		productCodeValidate(subject, vex);
		percentageValidate(subject, vex);
		uniqueInOneBatchNumberValidate(subject, vex);
		hasFutureValidate(subject, vex);		
		
		if(vex.size() > 0) 
			throw new ValidationException(vex);
		
	}
	
	private void dateFromValidate(LobStageDTO subject, Map<String, String> vex) {
		if(subject.getEffectiveStartDate() == null) {
			vex.put(EFFECTIVE_START_DATE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.effectiveStartDate") }));
		}
		else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				Date dateFrom = sdf.parse(sdf.format(subject.getEffectiveStartDate()));
				if(dateFrom.compareTo(new Date()) <= 0)
					vex.put(EFFECTIVE_START_DATE, Labels.getLabel("common.effectiveDateFromMustBeHigherThanToday"));
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}			
	}
	
	private void organizationValidate(LobStageDTO subject, Map<String, String> vex) {
		if(subject.getOrganizationCode() == null)
			vex.put(ORGANIZATION, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationCode") }));
		else {
			Organization org = organizationSetupServiceImpl.findByCode(subject.getOrganizationCode(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			if(org != null) {
				subject.setOrganizationId(org.getId());
				if(organizationSetupServiceImpl.isHaveFuture(org.getId()))
					vex.put(ORGANIZATION, Labels.getLabel("wos.organizationHasFuture"));
			} else {
				vex.put(ORGANIZATION, Labels.getLabel("common.isNotExist", new Object[] { Labels.getLabel("wos.organizationCode") }));
			}
		}
	}
	
	private void jobValidate(LobStageDTO subject, Map<String, String> vex) {
		if(subject.getJobCode() == null)
			vex.put(JOB, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.jobCode") }));
		else {
			JobDTO job = jobSetupServiceImpl.findByCode(subject.getJobCode());
			if(job != null) {
				subject.setJobId(job.getId());
				if(jobSetupServiceImpl.isHaveFuture(job.getId()))
					vex.put(JOB, Labels.getLabel("wos.jobHasFuture"));
			}
			else
				vex.put(JOB, Labels.getLabel("common.isNotExist", new Object[] { Labels.getLabel("wos.jobCode") }));
		}
	}
	
	private void productCodeValidate(LobStageDTO subject, Map<String, String> vex) {
		if(subject.getLobElements().isEmpty()) {
			vex.put(PRODUCT_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.product") }));
		} else {
			for(LobElementDTO element : subject.getLobElements()) {
				if (element != null) {
					KeyValue kvProduct = lookupServiceImpl.getLookupCaseInsensitive(lookupProduct, element.getProductCode(), securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
					if(kvProduct == null) {
						vex.put(PRODUCT_CODE, Labels.getLabel("common.isNotExist", new Object[] { Labels.getLabel("wos.product") + element.getProductCode()}));
						break;
					}
				}
			}
		}
	}
	
	private void percentageValidate(LobStageDTO subject, Map<String, String> vex) {
		BigDecimal percent = BigDecimal.ZERO;
		for(LobElementDTO element : subject.getLobElements()) {
			if (element != null) {
				percent = percent.add(element.getPercentage());
			}
		}
		if(percent.compareTo(new BigDecimal(100.0)) == 0)
			percent = new BigDecimal(100);

		if(!percent.equals(new BigDecimal(100))) {
			vex.put(PERCENTAGE, Labels.getLabel("wos.totalOfPercentageMustBeOneHundred"));
		}		
	}
	
	private void uniqueInOneBatchNumberValidate(LobStageDTO subject, Map<String, String> vex) {
		if(subject.getOrganizationId() != null && subject.getJobId() != null) {
			LobUploadStageExample example = new LobUploadStageExample();
			example.createCriteria().andOrganizationIdEqualTo(subject.getOrganizationId())
				.andJobIdEqualTo(subject.getJobId())
				.andIsClosedEqualTo(false);
			if(lobUploadServiceImpl.countByExample(example) > 0) {
				vex.put(PRODUCT_CODE, Labels.getLabel("common.isAlreadyUploaded", new Object[] { Labels.getLabel("wos.lobSetup") }));
			}
		}
	}
	
	private void hasFutureValidate(LobStageDTO subject, Map<String, String> vex) {
		if(subject.getOrganizationId() != null && subject.getJobId() != null) {
			if(lobSetupServiceImpl.isFutureExist(subject.getOrganizationId(), subject.getJobId()))
				vex.put(PRODUCT_CODE, Labels.getLabel("common.cannotBeUploadedThereIsFutureVersionFor", new Object[] { Labels.getLabel("wos.lobSetup") }));			
		}
	}
}
