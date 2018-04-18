package co.id.fifgroup.basicsetup.common;

import co.id.fifgroup.basicsetup.constant.QuerySet;
import co.id.fifgroup.basicsetup.dto.LookupDynamicDTO;

public interface LookupQuerySet {

	public void checkCompanyScope(Long companyScope, boolean scopeIsChange) throws Exception;
	public void setLookupQuerySet(LookupDynamicDTO lookupDynamicDto, QuerySet querySet) throws Exception;
	
}
