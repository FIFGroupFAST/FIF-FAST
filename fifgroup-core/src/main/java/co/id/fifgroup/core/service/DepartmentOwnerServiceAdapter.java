package co.id.fifgroup.core.service;

import java.util.List;

import co.id.fifgroup.core.ui.lookup.KeyValue;


public interface DepartmentOwnerServiceAdapter {
	List<KeyValue> getDepartmentOwnerByOrgCodeAndOwnerName(
			String organizationCode, String ownerName, Integer limit, Integer offset);
	
	int countDepartmentOwnerByOrgCodeAndOwnerName(
			String organizationCode, String ownerName, Integer limit, Integer offset);
}
