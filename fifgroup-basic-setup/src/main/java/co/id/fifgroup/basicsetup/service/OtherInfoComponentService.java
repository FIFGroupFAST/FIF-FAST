package co.id.fifgroup.basicsetup.service;

import org.zkoss.zul.Grid;

import co.id.fifgroup.basicsetup.common.OtherInfoComponentValue;
import co.id.fifgroup.basicsetup.domain.OtherInfoComponent;

public interface OtherInfoComponentService {

	public OtherInfoComponent getOtherInfoComponent(String formName, Long groupId, Long companyId);
	public boolean validate(OtherInfoComponent otherInfoComponent);
	public void setOtherInfoDetailValue(OtherInfoComponent otherInfoComponent, OtherInfoComponentValue otherInfoComponentValue);
	public void setOtherInfoDetailValueToComponent(OtherInfoComponent otherInfoComponent, Long otherInfoDetailIdInValue, String value);
	public void disableAllComponent(Grid grid, boolean disabled);
	
}
