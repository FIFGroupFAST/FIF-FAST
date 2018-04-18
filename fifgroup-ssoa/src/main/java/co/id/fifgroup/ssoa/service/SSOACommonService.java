package co.id.fifgroup.ssoa.service;

import java.util.List;

import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;

public interface SSOACommonService {
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code);
	public StockOpnameDTO getOnePersonByBranchId(Long branchId,Long companyId);
	public List<KeyValue> getLookupKeyValues(String lookupName, String key);
}