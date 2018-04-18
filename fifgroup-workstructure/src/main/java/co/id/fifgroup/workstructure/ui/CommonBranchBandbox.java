package co.id.fifgroup.workstructure.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
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
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;

public class CommonBranchBandbox extends CommonPopupDoubleBandbox {

	private static final long serialVersionUID = 1L;

	private CommonOrganizationBandbox bnbOrganization;
	private CommonEmployeeBandboxAdapter bnbEmployee;
	private CommonLocationBandbox bnbLocation;
	private Organization org;
	//add GAL 15103120081532 - Modifikasi Form PD Untuk Budget Control - hanya akan menampilkan data branch yg aktif saja
	private Date effectiveOnDate = DateUtils.truncate(new Date(), Calendar.DATE);
	//
	
	// start added by Jatis for phase 2
	private boolean isCompanyDisplayed = true;

	public boolean isCompanyDisplayed() {
		return isCompanyDisplayed;
	}

	public void setCompanyDisplayed(boolean isCompanyDisplayed) {
		this.isCompanyDisplayed = isCompanyDisplayed;
	}
	// end added by Jatis for phase 2

	public CommonBranchBandbox() {
		this.setTitle(Labels.getLabel("common.listOfBranch"));
		this.setSearchText1(Labels.getLabel("common.branchCode"));
		this.setSearchText2(Labels.getLabel("common.branchName"));
		this.setHeaderLabel1(Labels.getLabel("common.branchCode"));
		this.setHeaderLabel2(Labels.getLabel("common.branchName"));
        // start modified by Jatis for phase 2
		if (getSecurityType() != null
				&& (SecurityType.VIEW_BRANCH.getValue().equals(getSecurityType()) ||
					SecurityType.VIEW_HIERARCHY.getValue().equals(getSecurityType())) ||
					SecurityType.VIEW_OWN.getValue().equals(getSecurityType())
				) {
			this.setDisabled(true);
			Long branchId = null;
			if (SecurityType.VIEW_BRANCH.getValue().equals(getSecurityType()) ||
				SecurityType.VIEW_OWN.getValue().equals(getSecurityType()))
				branchId = getSecurityProfile().getBranchId();
				setRawValue(new KeyValue(branchId, getSecurityProfile().getBranchName(), getSecurityProfile().getBranchName()));
        // end modified by Jatis for phase 2
		}
		setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<Organization>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Organization> findBySearchCriteria(
					String searchCriteria1, String searchCriteria2, int limit,
					int offset) {
				return getOrganizationService().findBranches(
						getSecurityProfile().getSecurity().getSecurityFilter()
								.getInOrganizations(),
						getSecurityProfile().getSecurity().getSecurityFilter()
								.getNotInOrganizations(), searchCriteria1,
								//edit GAL 15103120081532 - Modifikasi Form PD Untuk Budget Control - ambil branch yg aktif saja
						searchCriteria2, limit, offset, effectiveOnDate);
								//
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				return getOrganizationService().countBranches(
						getSecurityProfile().getSecurity().getSecurityFilter()
								.getInOrganizations(),
						getSecurityProfile().getSecurity().getSecurityFilter()
								.getNotInOrganizations(), searchCriteria1,
								//edit GAL 15103120081532 - Modifikasi Form PD Untuk Budget Control - ambil branch yg aktif saja
						searchCriteria2, effectiveOnDate);
								//
			}

			@Override
			public void mapper(KeyValue keyValue, Organization data) {
				if (null != data) {
					CompanyDTO company = getCompanyService().findCompanyById(
							data.getCompanyId(), new Date());
					keyValue.setKey(data.getId());
					keyValue.setValue(data.getOrganizationCode());

					String companyName = ((null != company) ? company
							.getCompanyName() : "");
                    // start modified by Jatis for phase 2
					if(isCompanyDisplayed==true){
						keyValue.setDescription(data.getOrganizationName() + " ("
								+ companyName + ")");
					} else {
						keyValue.setDescription(data.getOrganizationName());
					}
                    // end modified by Jatis for phase 2
					setOrg(data);
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

	public CommonOrganizationBandbox getBnbOrganization() {
		return bnbOrganization;
	}

	public void setBnbOrganization(CommonOrganizationBandbox bnbOrganization) {
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
}