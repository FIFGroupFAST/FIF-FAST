package co.id.fifgroup.ssoa.common;


import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import java.util.Date;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zkplus.spring.SpringUtil;


public class SSOACommonBranchBandbox extends CommonPopupDoubleBandbox {

	private static final long serialVersionUID = 1L;
	
	public SSOACommonBranchBandbox() {
		this.setTitle(Labels.getLabel("common.listOfBranch"));
		this.setSearchText1(Labels.getLabel("common.branchCode"));
		this.setSearchText2(Labels.getLabel("common.branchName"));
		this.setHeaderLabel1(Labels.getLabel("common.branchCode"));
		this.setHeaderLabel2(Labels.getLabel("common.branchName"));
		if (getSecurityType() != null && (SecurityType.VIEW_BRANCH.getValue().equals(getSecurityType())
				|| SecurityType.VIEW_HIERARCHY.getValue().equals(getSecurityType()))) {
			this.setDisabled(true);
			Long branchId = null;
			if (SecurityType.VIEW_BRANCH.getValue().equals(getSecurityType()))
				branchId = getSecurityProfile().getBranchId();
			setRawValue(new KeyValue(branchId
					, getSecurityProfile().getBranchName()
					, getSecurityProfile().getBranchName()));
		}
		setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<Organization>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Organization> findBySearchCriteria(
					String searchCriteria1, String searchCriteria2, int limit,
					int offset) {
				return getOrganizationService().findBranches(
						getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations(), 
						getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations(), 
						searchCriteria1, searchCriteria2, limit, offset, null);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				return getOrganizationService().countBranches(
						getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations(), 
						getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations(),
						searchCriteria1, searchCriteria2, null);
			}

			@Override
			public void mapper(KeyValue keyValue, Organization data) {
				if (null != data) {
					CompanyDTO company = getCompanyService().findCompanyById(data.getCompanyId(), new Date());
					keyValue.setKey(data.getId());
					keyValue.setValue(data.getOrganizationCode());
					
					String companyName = ((null != company) ? company.getCompanyName() : "");
					
					keyValue.setDescription(data.getOrganizationName() + " (" + companyName + ")");
				}
			}
		});
		
		addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				
			}
		});
	}
	
	private OrganizationSetupService getOrganizationService() {
		return (OrganizationSetupService) SpringUtil.getBean("organizationSetupServiceImpl");
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
		if(getSecurityService().getSecurityProfile() != null && getSecurityService().getSecurityProfile().getSecurity() != null) {
			return getSecurityService().getSecurityProfile().getSecurity().getSecurityType();
		} else {
			return null;
		}
	}

	
	
	

}