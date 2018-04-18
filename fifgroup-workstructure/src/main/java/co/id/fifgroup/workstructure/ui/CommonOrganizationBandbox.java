package co.id.fifgroup.workstructure.ui;

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

import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;

public class CommonOrganizationBandbox extends CommonPopupDoubleBandbox{

	private static final long serialVersionUID = 1L;
	
	private CommonBranchBandbox bnbBranch;
	private CommonEmployeeBandboxAdapter bnbEmployee;
	private CommonLocationBandbox bnbLocation;
	private Date effectiveOnDate; 
	
	public CommonOrganizationBandbox() {
		this.setTitle(Labels.getLabel("common.listOfOrganization"));
		this.setSearchText1(Labels.getLabel("common.organizationCode"));
		this.setSearchText2(Labels.getLabel("common.organizationName"));
		this.setHeaderLabel1(Labels.getLabel("common.organizationCode"));
		this.setHeaderLabel2(Labels.getLabel("common.organizationName"));
		if (SecurityType.VIEW_OWN.getValue().equals(getSecurityType())
				|| SecurityType.VIEW_SINGLE.getValue().equals(getSecurityType())) {
			this.setDisabled(true);
			setRawValue(new KeyValue(getSecurityProfile().getOrganizationId()
					, getSecurityProfile().getOrganizationCode()
					, getSecurityProfile().getOrganizationName()));
		}
		if (SecurityType.VIEW_ALL.getValue().equals(getSecurityType())
				|| SecurityType.VIEW_FUNCTIONAL.getValue().equals(getSecurityType())) {
			this.effectiveOnDate = null;
		} else {
			this.effectiveOnDate = DateUtil.truncate(new Date());
		}
		
		setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<Organization>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public List<Organization> findBySearchCriteria(
					String searchCriteria1, String searchCriteria2, int limit,
					int offset) {
			
				if((SecurityType.VIEW_BRANCH.getValue().equals(getSecurityType()) || SecurityType.VIEW_ALL.getValue().equals(getSecurityType())) 
						&& bnbBranch != null
						&& bnbBranch.getKeyValue() != null 
						&& bnbBranch.getKeyValue().getKey() != null) {
					return getOrganizationService().getOrganizationByBranchAndSecurityFilter(getSecurityProfile().getWorkspaceCompanyId(), 
							Long.parseLong(bnbBranch.getKeyValue().getKey().toString()), 
							searchCriteria1, 
							searchCriteria2, 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations(), 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations(), 
							effectiveOnDate,
							limit, offset);						
				} else {
					return getOrganizationService().getOrganizationByBranchAndSecurityFilter(getSecurityProfile().getWorkspaceCompanyId(), 
							null, 
							searchCriteria1, 
							searchCriteria2, 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations(), 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations(),
							effectiveOnDate, 
							limit, offset);
				}
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				if((SecurityType.VIEW_BRANCH.getValue().equals(getSecurityType()) || SecurityType.VIEW_ALL.getValue().equals(getSecurityType())) 
						&& bnbBranch != null
						&& bnbBranch.getKeyValue() != null 
						&& bnbBranch.getKeyValue().getKey() != null) {
					return getOrganizationService().countOrganizationByBranchAndSecurityFilter(getSecurityProfile().getWorkspaceCompanyId(), 
							Long.parseLong(bnbBranch.getKeyValue().getKey().toString()), 
							searchCriteria1, 
							searchCriteria2, 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations(), 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations(), effectiveOnDate);						
				} else {
					return getOrganizationService().countOrganizationByBranchAndSecurityFilter(getSecurityProfile().getWorkspaceCompanyId(), 
							null, 
							searchCriteria1, 
							searchCriteria2, 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations(), 
							getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations(), effectiveOnDate);
				}
			}

			@Override
			public void mapper(KeyValue keyValue, Organization data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getOrganizationCode());
				keyValue.setDescription(data.getOrganizationName());
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

	public CommonBranchBandbox getBnbBranch() {
		return bnbBranch;
	}

	public void setBnbBranch(CommonBranchBandbox bnbBranch) {
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
	
}
