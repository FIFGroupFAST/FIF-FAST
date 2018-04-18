package co.id.fifgroup.workstructure.ui;

import java.util.Date;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.workstructure.service.LocationSetupService;

import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.workstructure.dto.LocationDTO;


public class CommonLocationBandbox extends CommonPopupDoubleBandbox{

	private CommonEmployeeBandboxAdapter bnbEmployee;
	private Date effectiveOnDate; 
	private CommonBranchBandbox bnbBranch;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4004696507239319741L;
	public CommonLocationBandbox() {
	this.setTitle(Labels.getLabel("${labels.wos.listOfLocation }"));
	this.setSearchText1(Labels.getLabel("${labels.wos.locationCode }"));
	this.setSearchText2(Labels.getLabel("${labels.wos.locationName }"));
	this.setHeaderLabel1(Labels.getLabel("${labels.wos.locationCode }"));
	this.setHeaderLabel2(Labels.getLabel("${labels.wos.locationName }"));
	if (SecurityType.VIEW_OWN.getValue().equals(getSecurityType())
			|| SecurityType.VIEW_SINGLE.getValue().equals(getSecurityType())) {
		this.setDisabled(true);
		if (getSecurityProfile().getOrganizationId() != null) {
			LocationDTO locationDTO = getLocationSetupService().findLocationByOrganizationId(getSecurityProfile().getOrganizationId(),getSecurityProfile().getWorkspaceCompanyId(),
					DateUtil.truncate(new Date()));
			if (locationDTO != null) {
				setRawValue(new KeyValue(locationDTO.getId()
						, locationDTO.getLocationCode()
						, locationDTO.getLocationName()));
			}
		}
		
	}
	if (SecurityType.VIEW_ALL.getValue().equals(getSecurityType())
			|| SecurityType.VIEW_FUNCTIONAL.getValue().equals(getSecurityType())) {
		this.effectiveOnDate = null;
	} else {
		this.effectiveOnDate = DateUtil.truncate(new Date());
	}
	setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<LocationDTO>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3215258472449216509L;

		@Override
		public List<LocationDTO> findBySearchCriteria(
				String locationCode, String locationName,
				int limit, int offset) {
			LocationDTO example = new LocationDTO();
			example.setLocationCode(locationCode);
			example.setLocationName(locationName);
			example.setCompanyId(getSecurityProfile().getWorkspaceCompanyId());
			example.setEffectiveDate(effectiveOnDate);
			return getLocationSetupService().findByExample(example,
					limit, offset);
		}

		@Override
		public int countBySearchCriteria(String locationCode,
				String locationName) {
			LocationDTO example = new LocationDTO();
			example.setLocationCode(locationCode);
			example.setLocationName(locationName);
			example.setCompanyId(getSecurityProfile().getWorkspaceCompanyId());
			example.setEffectiveDate(effectiveOnDate);
			return getLocationSetupService().countByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, LocationDTO data) {
			keyValue.setKey(data.getId());
			keyValue.setValue(data.getLocationCode());
			keyValue.setDescription(data.getLocationName());
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
	
	public CommonEmployeeBandboxAdapter getBnbEmployee() {
		return bnbEmployee;
	}

	public void setBnbEmployee(CommonEmployeeBandboxAdapter bnbEmployee) {
		this.bnbEmployee = bnbEmployee;
	}
	
	public CommonBranchBandbox getBranch(){
		return bnbBranch;
	}
	
	public void setBnbBranch(CommonBranchBandbox bnbBranch) {
		this.bnbBranch = bnbBranch;
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
	
	private SecurityService getSecurityService() {
		return (SecurityService) SpringUtil.getBean("securityServiceImpl");
	}
	
	private LocationSetupService getLocationSetupService() {
		return (LocationSetupService) SpringUtil.getBean("locationSetupServiceImpl");
	}
}
