package co.id.fifgroup.workstructure.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.workstructure.domain.OrganizationExample;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.basicsetup.service.OtherInfoComponentService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.workstructure.dao.OrganizationMapper;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.finder.OrganizationFinder;

@Component
public class OrganizationValidator implements Validator<OrganizationDTO> {

	@Autowired
	private OtherInfoComponentService otherInfoComponentServiceImpl;

	public static final String UNIQUE_FIELD = "uniqueField";
	public static final String HEADER_ID = "headerId";
	public static final String VERSION_NUMBER = "versionNumber";
	public static final String ORGANIZATION_CODE = "organization.code";
	public static final String ORGANIZATION_NAME = "organization.name";
	public static final String ORGANIZATION_LEVEL = "organization.level";
	public static final String KPP_CODE = "kpp.code";
	public static final String LOCATION = "location";
	public static final String HEAD_OF_ORGANIZATION = "headOfOrganization";
	public static final String BRANCH_CODE = "branchCode";
	public static final String COST_CENTRE_CODE = "costCentreCode";
	public static final String ZIP_CODE = "zipCode";
	public static final String ADDRESS = "address";
	public static final String NPWP = "npwp";

	@Autowired
	private OrganizationMapper orgMapper;
	@Autowired
	private OrganizationFinder orgFinder;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private OrganizationSetupService organizationSetupServiceImpl;

	@Override
	public void validate(OrganizationDTO subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		int countCode = 0;
		int countName = 0;

		if (null == subject)
			throw new IllegalArgumentException(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.subject") }));

		if (subject.getId() == null) {
			OrganizationExample example = new OrganizationExample();
			example.createCriteria().andOrganizationCodeLikeInsensitive(subject.getCode());
			countCode = orgMapper.countByExample(example);

			example.clear();
			example.createCriteria().andOrganizationNameLikeInsensitive(subject.getName());
			countName = orgMapper.countByExample(example);
		}

		if (subject.isUpload()) {
			if (countCode > 0 && countName > 0) {
				OrganizationDTO orgDTO = orgFinder.findLastVersionByCode(subject.getCode(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				if (orgDTO != null) {
					if (orgDTO.isFuture())
						violations.put(UNIQUE_FIELD,
								Labels.getLabel("common.cannotBeUploadedThereIsFutureVersionFor", new Object[] { subject.getCode() + "-" + subject.getName() }));
					else {
						// Calendar dateFromFuture = Calendar.getInstance();
						// dateFromFuture.setTime(subject.getDateFrom());
						// dateFromFuture.add(Calendar.DAY_OF_MONTH, -1);
						// violations.put(HEADER_ID, orgDTO.getId().toString());
						// violations.put(VERSION_NUMBER,
						// orgDTO.getVersionNumber().toString());
						// orgDTO.setDateTo(dateFromFuture.getTime());
						// organizationSetupServiceImpl.updateDetail(orgDTO);
					}
				}
			}
		}

		if (isNullOrEmpty(subject.getCode()))
			violations.put(ORGANIZATION_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationCode") }));
		else if (!ValidationRule.isAlphaNumeric(subject.getCode()))
			violations.put(ORGANIZATION_CODE, Labels.getLabel("common.cannotContainSpaceOrCharacter", new Object[] { Labels.getLabel("wos.organizationCode") }));
		else if (subject.getId() == null) {
			if (countCode > 0)
				violations.put(ORGANIZATION_CODE, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.organizationCode") }));
		}

		if (isNullOrEmpty(subject.getName()))
			violations.put(ORGANIZATION_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationName") }));
		else if (subject.getId() == null) {
			if (countName > 0)
				violations.put(ORGANIZATION_NAME, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.organizationName") }));
		}

		if (isNullOrEmpty(subject.getLevelCode()))
			violations.put(ORGANIZATION_LEVEL, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationLevel") }));

		if (isNullOrEmpty(subject.getKppCode()))
			violations.put(KPP_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.kpp") }));

		if (subject.getLocation().getId() == null)
			violations.put(LOCATION, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.location") }));

		if (subject.getOrganizationHeadId() == null)
			violations.put(HEAD_OF_ORGANIZATION, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.headOfOrganization") }));

		if (isNullOrEmpty(subject.getBranchCode()))
			violations.put(BRANCH_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.branchCode") }));

		if (isNullOrEmpty(subject.getCostCenterCode()))
			violations.put(COST_CENTRE_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.costCenter") }));

		if (!subject.isUpload()) {
			if (isNullOrEmpty(subject.getZipCode()))
				violations.put(ZIP_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("address.zipcode") }));

			if (isNullOrEmpty(subject.getAddress()))
				violations.put(ADDRESS, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("address.address") }));
		}

		if (!isNullOrEmpty(subject.getNpwp())) {
			if (!ValidationRule.isNPWP(subject.getNpwp())) {
				violations.put(NPWP, Labels.getLabel("wos.npwpFormat", new Object[] { "xx.xxx.xxx.x-xxx.xxx" }));
			}
		}

		if (subject.getOtherInfoComponent() != null) {
			otherInfoComponentServiceImpl.validate(subject.getOtherInfoComponent());
			if (!otherInfoComponentServiceImpl.validate(subject.getOtherInfoComponent()))
				violations.put("", "");
		}

		if (violations.size() > 0)
			throw new ValidationException(violations);
	}

}