package co.id.fifgroup.basicsetup.common;

import java.io.Serializable;

public interface LookupCriteriaBasicSetup extends Serializable {

	public String getLookupName();
	public String getParentDetailCode();
	public Long getScope();
	public Long getBusinessGroupId();
	
}
