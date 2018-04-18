package co.id.fifgroup.basicsetup.validation;

import static co.id.fifgroup.core.validation.ValidationRule.isAlphaNumeric;
import static co.id.fifgroup.core.validation.ValidationRule.isDigit;
import static co.id.fifgroup.core.validation.ValidationRule.isNPWP;
import static com.google.common.base.Strings.isNullOrEmpty;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.constant.UploadExtension;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.OtherInfoComponentService;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

@Component
public class CompanyValidator implements Validator<CompanyDTO> {

	public static final String COMPANY_CODE = "company.companyCode";
	public static final String COMPANY_NAME = "company.companyName";
	public static final String BUSINESS_GROUP = "company.businessGroup";
	public static final String EFFECTIVE_DATE_FROM = "company.effectiveDateFrom";
	public static final String EFFECTIVE_DATE_TO = "company.effectiveDateTo";
	public static final String COMPANY_LOGO = "company.companyLogo";
	public static final String NPWP = "company.npwp";
	public static final String KPP = "company.kpp";
	public static final String KPJ_NUMBER = "company.kpjNumber";
	public static final String GL_CODE = "company.glCode";
	public static final String CURRENCY = "company.currency";
	public static final String COUNTRY = "company.country";
	public static final String PROVINCE = "company.province";
	public static final String CITY = "company.city";
	public static final String ZIP_CODE = "company.zipCode";
	public static final String ADDRESS = "company.address";
	
	private Date dateFromBeforeCurrentEdit;
	private Date dateToBeforeCurrentEdit;
	
	@Autowired
	private CompanyService companyServiceImpl;
	@Autowired
	private OtherInfoComponentService otherInfoComponentServiceImpl;
	
	@Override
	public void validate(CompanyDTO subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		formValidation(subject, violations);
		boolean validOtherInfo = true;
		if(subject.getOtherInfoComponent() != null) {
			otherInfoComponentServiceImpl.validate(subject.getOtherInfoComponent());
		}
		if(violations.size() > 0 || !validOtherInfo) throw new ValidationException(violations);
	}

	public void setComponentHistorical(Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) {
		this.dateFromBeforeCurrentEdit = dateFromBeforeCurrentEdit;
		this.dateToBeforeCurrentEdit = dateToBeforeCurrentEdit;
	}
	
	private void formValidation(CompanyDTO subject, Map<String, String> errors) {
		companyCodeValidation(subject, errors);
		companyNameValidation(subject, errors);
		companyBusinessGroupValidation(subject, errors);
		companyEffectiveDateFromValidation(subject, errors);
		companyEffectiveDateToValidation(subject, errors);
		companyLogoValidation(subject, errors);
		companyNpwpValidation(subject, errors);
		companyKppValidation(subject, errors);
		companyKpjNumberValidation(subject, errors);
		companyGlCodeValidation(subject, errors);
		companyCurrencyValidation(subject, errors);
		companyCountryValidation(subject, errors);
		companyProvinceValidation(subject, errors);
		companyCityValidation(subject, errors);
		companyZipCodeValidation(subject, errors);
		companyAddress(subject, errors);
	}
	
	private void companyCodeValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getCompanyCode())) {
			errors.put(COMPANY_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.companyCode") }));
			return;
		}
		if(subject.getCompanySeqNum() == null) {
			CompanyExample companyExampleCode = new CompanyExample();
			companyExampleCode.createCriteria().andCompanyCodeEqualTo(subject.getCompanyCode());
			List<Company> resultCompany = companyServiceImpl.getCompanyByExample(companyExampleCode);
			if(resultCompany.size() > 0) {
				errors.put(COMPANY_CODE, Labels.getLabel("bse.err.companyCompanyCodeAlreadyExist"));
				return;
			}
		}
		if(!isAlphaNumeric(subject.getCompanyCode())) {
			errors.put(COMPANY_CODE, Labels.getLabel("common.mustBeAlphaNumeric", new Object[] { Labels.getLabel("bse.companyCode") }));
			return;
		}
	}
	
	private void companyNameValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getCompanyName())) {
			errors.put(COMPANY_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.companyName") }));
			return;
		}
		
		if(subject.getCompanySeqNum() == null) {
			CompanyExample companyExampleName = new CompanyExample();
			companyExampleName.createCriteria().andCompanyNameEqualTo(subject.getCompanyName());
			List<Company> resultCompany = companyServiceImpl.getCompanyByExample(companyExampleName);
			if(resultCompany.size() > 0) {
				errors.put(COMPANY_NAME, Labels.getLabel("bse.err.companyCompanyNameAlreadyExist"));
				return;
			}
		}
		
		if(subject.getCompanySeqNum() != null && companyServiceImpl.isFuture(subject)) {
			CompanyExample companyExampleName = new CompanyExample();
			companyExampleName.createCriteria().andCompanyNameEqualTo(subject.getCompanyName()).andCompanyIdNotEqualTo(subject.getCompanyId());
			List<Company> resultCompany = companyServiceImpl.getCompanyByExample(companyExampleName);
			if(resultCompany.size() > 0) {
				errors.put(COMPANY_NAME, Labels.getLabel("bse.err.companyCompanyNameAlreadyExist"));
				return;
			}
		}
		
	}
	
	private void companyBusinessGroupValidation(CompanyDTO subject, Map<String, String> errors) {
		if(subject.getGroupId() == null) {
			errors.put(BUSINESS_GROUP, Labels.getLabel("common.err.fieldRequired", new Object[] { Labels.getLabel("bse.businessGroup") }));
			return;
		}
	}
	
	private void companyEffectiveDateFromValidation(CompanyDTO subject, Map<String, String> errors) {
		if(dateFromBeforeCurrentEdit == null) {
			if(subject.getEffectiveStartDate() == null) {
				errors.put(EFFECTIVE_DATE_FROM, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.effectiveDateFrom") }));
				return;
			}
			Date currentDate = DateUtils.truncate(new Date(), Calendar.DATE);
			Date effectiveStartDate = DateUtils.truncate(subject.getEffectiveStartDate(), Calendar.DATE);
			if(effectiveStartDate.compareTo(currentDate) <= 0) {
				errors.put(EFFECTIVE_DATE_FROM, Labels.getLabel("bse.err.companyEffectiveDateFromMustBeGreaterThanCurrentDate"));
				return;
			}
		} else {
			Date dateFromObject = DateUtils.truncate(subject.getEffectiveStartDate(), Calendar.DATE);
			Date dateFromObjectBeforeUpdate = DateUtils.truncate(dateFromBeforeCurrentEdit, Calendar.DATE);
			if(dateFromObject.compareTo(dateFromObjectBeforeUpdate) != 0) {
				Date currentDate = DateUtils.truncate(new Date(), Calendar.DATE);
				Date effectiveStartDate = DateUtils.truncate(subject.getEffectiveStartDate(), Calendar.DATE);
				if(effectiveStartDate.compareTo(currentDate) <= 0) {
					errors.put(EFFECTIVE_DATE_FROM, Labels.getLabel("bse.err.companyEffectiveDateFromMustBeGreaterThanCurrentDate"));
					return;
				}
			}
		}
		
//		if(subject.getEffectiveStartDate() != null){
//			Date currentDate = DateUtils.truncate(new Date(), Calendar.DATE);
//			Date effectiveStartDate = DateUtils.truncate(subject.getEffectiveStartDate(), Calendar.DATE);
//			if(effectiveStartDate.compareTo(currentDate) <= 0) {
//				errors.put(EFFECTIVE_DATE_FROM, Labels.getLabel("bse.err.companyEffectiveDateFromMustBeGreaterThanCurrentDate"));
//				return;
//			}
//		}
	}
	
	private void companyEffectiveDateToValidation(CompanyDTO subject, Map<String, String> errors) {
		if(subject.getEffectiveStartDate() == null) {
			errors.put(EFFECTIVE_DATE_FROM, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.effectiveDateFrom") }));
			return;
		}
		Date effectiveStartDate = DateUtils.truncate(subject.getEffectiveStartDate(), Calendar.DATE);
		Date effectiveEndDate = DateUtils.truncate(subject.getEffectiveEndDate(), Calendar.DATE);
		if(effectiveEndDate.compareTo(effectiveStartDate) < 0) {
			errors.put(EFFECTIVE_DATE_TO, Labels.getLabel("bse.err.companyEffectiveDateToMustBeGreaterThanOrEqualToEffectiveDateFrom"));
			return;
		}
	}
	
	private void companyLogoValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getCompanyLogo())) {
			errors.put(COMPANY_LOGO, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.companyLogo") }));
			return;
		}
		String extension = subject.getCompanyLogo().substring(subject.getCompanyLogo().indexOf(".") + 1, subject.getCompanyLogo().length());
		if(!extension.toLowerCase().equals(UploadExtension.PNG.toString().toLowerCase()) && !extension.toLowerCase().equals(UploadExtension.JPEG.toString().toLowerCase()) && !extension.toLowerCase().equals(UploadExtension.JPG.toString().toLowerCase())) {
			errors.put(COMPANY_LOGO, Labels.getLabel("bse.err.companyLogoNotValid"));
			return;
		}
	}
	
	private void companyNpwpValidation(CompanyDTO subject, Map<String, String> errors) {
		if(subject.getNpwp() != null && !subject.getNpwp().equals("") && !isNPWP(subject.getNpwp())) {
			errors.put(NPWP, "NPWP is not valid, ex. xx.xxx.xxx.x-xxx.xxx");
			return;
		}
	}
	
	private void companyKppValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getKpp())) {
			errors.put(KPP, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.kpp") }));
			return;
		}
	}
	
	private void companyKpjNumberValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getKpjNumber())) {
			errors.put(KPJ_NUMBER, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.kpjNumber") }));
			return;
		}
		if(!isDigit(subject.getKpjNumber())) {
			errors.put(KPJ_NUMBER, Labels.getLabel("common.mustBeDigit", new Object[] { Labels.getLabel("bse.kpjNumber") }));
			return;
		}
	}
	
	private void companyGlCodeValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getGlCode())) {
			errors.put(GL_CODE, Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.glCode") }));
			return;
		}
	}
	
	private void companyCurrencyValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getCurrencyCode())) {
			errors.put(CURRENCY, Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.currency") }));
			return;
		}
	}
	
	private void companyCountryValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getCountry())) {
			errors.put(COUNTRY, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("address.country") }));
			return;
		}
	}
	
	private void companyProvinceValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getProvince())) {
			errors.put(PROVINCE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("address.province") }));
			return;
		}
	}
	
	private void companyCityValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getCity())) {
			errors.put(CITY, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("address.city") }));
			return;
		}
	}
	
	private void companyZipCodeValidation(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getZipcode())) {
			errors.put(ZIP_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("address.zipcode") }));
			return;
		}
	}
	
	private void companyAddress(CompanyDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getAddressLine())) {
			errors.put(ADDRESS, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("address.address") }));
			return;
		}
	}
}
