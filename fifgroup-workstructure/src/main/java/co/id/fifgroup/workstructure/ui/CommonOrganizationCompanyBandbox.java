package co.id.fifgroup.workstructure.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupTripleBandbox;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateTripleCriteria;
import co.id.fifgroup.core.ui.lookup.TripleBandKeyValue;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class CommonOrganizationCompanyBandbox extends CommonPopupTripleBandbox{

	private static final long serialVersionUID = 1L;
	
	private CommonBranchCompanyBandbox bnbBranch;
	private CommonEmployeeBandboxAdapter bnbEmployee;
	private CommonLocationBandbox bnbLocation;
	private Date effectiveOnDate; 
	
	private Long companyId;
	private boolean isCompanyMustBeNotNull;
	private boolean isBranchMustBeNotNull;
	private boolean bySecurityType;
	
	public CommonOrganizationCompanyBandbox(boolean bySecurityType){
		this.bySecurityType = bySecurityType;
		init();
	}
	
	public CommonOrganizationCompanyBandbox(){
		this.bySecurityType = false;
		init();
	}
	
	public void init() {
		this.setTitle(Labels.getLabel("common.listOfOrganization"));
		this.setSearchText1(Labels.getLabel("wos.companyName"));
		this.setSearchText2(Labels.getLabel("common.organizationCode"));
		this.setSearchText3(Labels.getLabel("common.organizationName"));
		this.setHeaderLabel1(Labels.getLabel("wos.companyName"));
		this.setHeaderLabel2(Labels.getLabel("common.organizationCode"));
		this.setHeaderLabel3(Labels.getLabel("common.organizationName"));
		this.setConcatValueDescriptionDescription2(true);
		this.setCompanyMustBeNotNull(false);
		this.setBranchMustBeNotNull(false);
		
		if (bySecurityType){
			if (SecurityType.VIEW_OWN.getValue().equals(getSecurityType())
					|| SecurityType.VIEW_SINGLE.getValue().equals(getSecurityType())) {
				this.setDisabled(true);
				setRawValue(new TripleBandKeyValue(getSecurityProfile().getOrganizationId()
						, getSecurityProfile().getOrganizationCode()
						, getSecurityProfile().getOrganizationName()));
			}
			if (SecurityType.VIEW_ALL.getValue().equals(getSecurityType())
					|| SecurityType.VIEW_FUNCTIONAL.getValue().equals(getSecurityType())) {
				this.effectiveOnDate = null;
			} else {
				this.effectiveOnDate = DateUtil.truncate(new Date());
			}
		}
		
		setSearchDelegate(new SerializableSearchDelegateTripleCriteria<OrganizationDTO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<OrganizationDTO> findBySearchCriteria(
					String searchCriteria1, String searchCriteria2,
					String searchCriteria3, int limit, int offset) {
				Long branchId = null;
				if (bnbBranch != null
						&& bnbBranch.getTripleKeyValue() != null 
						&& bnbBranch.getTripleKeyValue().getKey() != null){
					branchId = (Long) bnbBranch.getTripleKeyValue().getKey();
				}
				if ((isCompanyMustBeNotNull() && companyId == null)
						|| (isBranchMustBeNotNull() && branchId == null)){
					return new ArrayList<OrganizationDTO>();
				}
				else {
					return getOrganizationService().getOrganizationByBranchAndCompanyNameAndSecurityFilter(
							companyId, branchId, searchCriteria1, searchCriteria2, searchCriteria3, 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations(), 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations(), 
							effectiveOnDate, limit, offset);
				}
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2, String searchCriteria3) {
				Long branchId = null;
				if (bnbBranch != null
						&& bnbBranch.getTripleKeyValue() != null 
						&& bnbBranch.getTripleKeyValue().getKey() != null){
					branchId = (Long) bnbBranch.getTripleKeyValue().getKey();
				}
				if ((isCompanyMustBeNotNull() && companyId == null)
						|| (isBranchMustBeNotNull() && branchId == null)){
					return 0;
				}
				else {
					return getOrganizationService().countOrganizationByBranchAndCompanyNameAndSecurityFilter(
							companyId, branchId, searchCriteria1, searchCriteria2, searchCriteria3, 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations(), 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations(), 
							effectiveOnDate);
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
		
		addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if(bnbEmployee != null)
					bnbEmployee.clearSelection();
			}
		});
	}
	
	public OrganizationSetupService getOrganizationService() {
		return (OrganizationSetupService) SpringUtil.getBean("organizationSetupServiceImpl");
	}
	
	public SecurityService getSecurityService() {
		return (SecurityService) SpringUtil.getBean("securityServiceImpl");
	}
	
	private CompanyService getCompanyService() {
		return (CompanyService) SpringUtil.getBean("companyServiceImpl");
	}
	
	public CommonBranchCompanyBandbox getBnbBranch() {
		return bnbBranch;
	}

	public void setBnbBranch(CommonBranchCompanyBandbox bnbBranch) {
		this.bnbBranch = bnbBranch;
		if (bnbBranch != null) 
			this.bnbBranch.setBnbOrganization(this);
	}
	
	private SecurityProfile getSecurityProfile() {
		return getSecurityService().getSecurityProfile();
	}
	
	public String getSecurityType() {
		return getSecurityService().getSecurityProfile().getSecurity().getSecurityType();
	}
	
	public void clearSelection() {
		setRawValue(null);
	}

	public CommonEmployeeBandboxAdapter getBnbEmployee() {
		return bnbEmployee;
	}

	public void setBnbEmployee(CommonEmployeeBandboxAdapter bnbEmployee) {
		this.bnbEmployee = bnbEmployee;
	}

	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}

	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}
	
	public CommonLocationBandbox getBnbLocation() {
		return bnbLocation;
	}

	public void setBnbLocation(CommonLocationBandbox bnbLocation) {
		this.bnbLocation = bnbLocation;
	}
	
	public void setRawValue(Long organizationId, String companyName, String organizationCode, String organizationName){
		TripleBandKeyValue keyValue = new TripleBandKeyValue();
		keyValue.setKey(organizationId);
		keyValue.setValue(companyName);
		keyValue.setDescription(organizationCode);
		keyValue.setDescription2(organizationName);
		this.setRawValue(keyValue);
	}
	
	public void setRawValue(Long organizationId, Long companyId, String organizationCode, String organizationName){
		TripleBandKeyValue keyValue = new TripleBandKeyValue();
		keyValue.setKey(organizationId);
		keyValue.setValue(getCompanyService().getCompanyNameById(companyId));
		keyValue.setDescription(organizationCode);
		keyValue.setDescription2(organizationName);
		this.setRawValue(keyValue);
	}
	
	public void setRawValue(Long organizationId, String organizationCode, String organizationName){
		TripleBandKeyValue keyValue = new TripleBandKeyValue();
		keyValue.setKey(organizationId);
		Organization org = getOrganizationService().findOrganizationById(organizationId);
		if (org != null)
			keyValue.setValue(getCompanyService().getCompanyNameById(org.getCompanyId()));
		keyValue.setDescription(organizationCode);
		keyValue.setDescription2(organizationName);
		this.setRawValue(keyValue);
	}
	
	public void setRawValue(Long organizationId){
		if (organizationId != null){
			TripleBandKeyValue keyValue = new TripleBandKeyValue();
			keyValue.setKey(organizationId);
			Organization org = getOrganizationService().findOrganizationById(organizationId);
			if (org != null)
				keyValue.setValue(getCompanyService().getCompanyNameById(org.getCompanyId()));
			keyValue.setDescription(org.getOrganizationCode());
			keyValue.setDescription2(org.getOrganizationName());
			this.setRawValue(keyValue);
		}
		else {
			super.setRawValue(null);
		}
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public boolean isCompanyMustBeNotNull() {
		return isCompanyMustBeNotNull;
	}

	public void setCompanyMustBeNotNull(boolean companyMustBeNotNull) {
		this.isCompanyMustBeNotNull = companyMustBeNotNull;
	}

	public boolean isBranchMustBeNotNull() {
		return isBranchMustBeNotNull;
	}

	public void setBranchMustBeNotNull(boolean isBranchMustBeNotNull) {
		this.isBranchMustBeNotNull = isBranchMustBeNotNull;
	}
}
