package co.id.fifgroup.workstructure.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupTripleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateTripleCriteria;
import co.id.fifgroup.core.ui.lookup.TripleBandKeyValue;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class CommonBranchCompanyBandbox extends CommonPopupTripleBandbox {

	private static final long serialVersionUID = 1L;

	private CommonOrganizationCompanyBandbox bnbOrganization;
	private CommonEmployeeBandboxAdapter bnbEmployee;
	private CommonLocationBandbox bnbLocation;
	private Organization org;
	
	private Long companyInLocation;
	private boolean isCompanyMustBeNotNull;
	private boolean bySecurityType;
	private Long areaId;
	private Date effectiveDate;
	
	public CommonBranchCompanyBandbox(boolean bySecurityType){
		this.bySecurityType = bySecurityType;
		init();
	}
	
	public CommonBranchCompanyBandbox(){
		this.bySecurityType = false;
		init();
	}
	
	public void init() {
		this.setTitle(Labels.getLabel("common.listOfBranch"));
		this.setSearchText1(Labels.getLabel("wos.companyName"));
		this.setSearchText2(Labels.getLabel("common.branchCode"));
		this.setSearchText3(Labels.getLabel("common.branchName"));
		this.setHeaderLabel1(Labels.getLabel("wos.companyName"));
		this.setHeaderLabel2(Labels.getLabel("common.branchCode"));
		this.setHeaderLabel3(Labels.getLabel("common.branchName"));
		this.setConcatValueDescriptionDescription2(true);
		this.setCompanyMustBeNotNull(false);
		
		if (bySecurityType && getSecurityType() != null
				&& ((SecurityType.VIEW_BRANCH.getValue().equals(getSecurityType()) ||
					SecurityType.VIEW_HIERARCHY.getValue().equals(getSecurityType())) ||
					SecurityType.VIEW_OWN.getValue().equals(getSecurityType()))
				) {
			this.setDisabled(true);
			Long branchId = null;
			if (SecurityType.VIEW_BRANCH.getValue().equals(getSecurityType()) ||
				SecurityType.VIEW_OWN.getValue().equals(getSecurityType())) {
				branchId = getSecurityProfile().getBranchId();
				CompanyDTO company = getCompanyService().findCompanyById(
						getSecurityProfile().getCompanyId(), new Date());
				TripleBandKeyValue keyValue = new TripleBandKeyValue();
				keyValue.setKey(branchId);
				keyValue.setValue(company.getCompanyName());
				Organization branch = getOrganizationService().findOrganizationById(branchId);
				if (branch != null){
					//keyValue.setValue(getCompanyService().getCompanyNameById(branch.getCompanyId()));
					keyValue.setDescription(branch.getOrganizationCode());
					keyValue.setDescription2(branch.getOrganizationName());
				}
				setRawValue(keyValue);
			}
		}
		setSearchDelegate(new SerializableSearchDelegateTripleCriteria<OrganizationDTO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<OrganizationDTO> findBySearchCriteria(
					String searchCriteria1, String searchCriteria2,
					String searchCriteria3, int limit, int offset) {
				if ((isCompanyMustBeNotNull() && companyInLocation == null)){
					return new ArrayList<OrganizationDTO>();
				}
				else {
					return getOrganizationService().findBranchesByCompanyNameAndBranchName(
							companyInLocation,
							getSecurityProfile().getSecurity().getSecurityFilter()
									.getInOrganizations(),
							getSecurityProfile().getSecurity().getSecurityFilter()
									.getNotInOrganizations(), searchCriteria1,
							searchCriteria2, searchCriteria3, limit, offset, effectiveDate, areaId);
				}
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2, String searchCriteria3) {
				if ((isCompanyMustBeNotNull() && companyInLocation == null)){
					return 0;
				}
				else {
					return getOrganizationService().countBranchesByCompanyNameAndBranchName(
							companyInLocation,
							getSecurityProfile().getSecurity().getSecurityFilter()
									.getInOrganizations(),
							getSecurityProfile().getSecurity().getSecurityFilter()
									.getNotInOrganizations(), searchCriteria1,
							searchCriteria2, searchCriteria3, effectiveDate, areaId);
				}
			}

			@Override
			public void mapper(TripleBandKeyValue keyValue, OrganizationDTO data) {
				if (null != data) {
					keyValue.setKey(data.getId());
					keyValue.setValue(data.getCompanyName());
					keyValue.setDescription(data.getCode());
					keyValue.setDescription2(data.getName());
				}
			}
		});

		addEventListener(Events.ON_CLOSE,
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						if (bnbEmployee != null)
							bnbEmployee.clearSelection();
						if (bnbOrganization != null)
							bnbOrganization.clearSelection();
					}
				});
	}
	
	public void clearSelection() {
		setRawValue(null);
	}
	
	private OrganizationSetupService getOrganizationService() {
		return (OrganizationSetupService) SpringUtil
				.getBean("organizationSetupServiceImpl");
	}

	private SecurityService getSecurityService() {
		return (SecurityService) SpringUtil.getBean("securityServiceImpl");
	}

	private CompanyService getCompanyService() {
		return (CompanyService) SpringUtil.getBean("companyServiceImpl");
	}

	private SecurityProfile getSecurityProfile() {
		return getSecurityService().getSecurityProfile();
	}

	private String getSecurityType() {
		if (getSecurityService().getSecurityProfile() != null
				&& getSecurityService().getSecurityProfile().getSecurity() != null) {
			return getSecurityService().getSecurityProfile().getSecurity()
					.getSecurityType();
		} else {
			return null;
		}
	}

	public CommonOrganizationCompanyBandbox getBnbOrganization() {
		return bnbOrganization;
	}

	public void setBnbOrganization(CommonOrganizationCompanyBandbox bnbOrganization) {
		this.bnbOrganization = bnbOrganization;
	}

	public CommonEmployeeBandboxAdapter getBnbEmployee() {
		return bnbEmployee;
	}

	public void setBnbEmployee(CommonEmployeeBandboxAdapter bnbEmployee) {
		this.bnbEmployee = bnbEmployee;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public CommonLocationBandbox getBnbLocation() {
		return bnbLocation;
	}

	public void setBnbLocation(CommonLocationBandbox bnbLocation) {
		this.bnbLocation = bnbLocation;
	}

	public Long getCompanyInLocation() {
		return companyInLocation;
	}

	public void setCompanyInLocation(Long companyInLocation) {
		this.companyInLocation = companyInLocation;
	}
	
	public void setRawValue(Long branchId, String companyName, String branchCode, String branchName){
		TripleBandKeyValue keyValue = new TripleBandKeyValue();
		keyValue.setKey(branchId);
		keyValue.setValue(companyName);
		keyValue.setDescription(branchCode);
		keyValue.setDescription2(branchName);
		this.setRawValue(keyValue);
	}
	
	public void setRawValue(Long branchId, Long companyId, String branchCode, String branchName){
		TripleBandKeyValue keyValue = new TripleBandKeyValue();
		keyValue.setKey(branchId);
		keyValue.setValue(getCompanyService().getCompanyNameById(companyId));
		keyValue.setDescription(branchCode);
		keyValue.setDescription2(branchName);
		this.setRawValue(keyValue);
	}
	
	public void setRawValue(Long branchId, String branchCode, String branchName){
		TripleBandKeyValue keyValue = new TripleBandKeyValue();
		keyValue.setKey(branchId);
		OrganizationDTO branch = getOrganizationService().getBranch(branchId, new Date(), null);
		if (branch != null)
			keyValue.setValue(getCompanyService().getCompanyNameById(branch.getCompanyId()));
		keyValue.setDescription(branchCode);
		keyValue.setDescription2(branchName);
		this.setRawValue(keyValue);
	}
	
	public void setRawValue(Long branchId){
		if (branchId != null){
			TripleBandKeyValue keyValue = new TripleBandKeyValue();
			keyValue.setKey(branchId);
			if (companyInLocation != null){
				keyValue.setValue(getCompanyService().getCompanyNameById(companyInLocation));
			}
			else {
				keyValue.setValue("");
			}
			
			Organization branch = getOrganizationService().findOrganizationById(branchId);
			if (branch != null){
				//keyValue.setValue(getCompanyService().getCompanyNameById(branch.getCompanyId()));
				keyValue.setDescription(branch.getOrganizationCode());
				keyValue.setDescription2(branch.getOrganizationName());
			}
			
			
			this.setRawValue(keyValue);
		}
		else {
			super.setRawValue(null);
		}
	}

	public boolean isCompanyMustBeNotNull() {
		return isCompanyMustBeNotNull;
	}

	public void setCompanyMustBeNotNull(boolean isCompanyMustBeNotNull) {
		this.isCompanyMustBeNotNull = isCompanyMustBeNotNull;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	} 
}