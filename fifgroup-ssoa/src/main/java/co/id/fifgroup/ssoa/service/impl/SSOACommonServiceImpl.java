package co.id.fifgroup.ssoa.service.impl;

import co.id.fifgroup.basicsetup.dao.GlobalSettingFinderMapper;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.ssoa.dao.StockOpnameMapper;
import co.id.fifgroup.ssoa.dao.SystemPropertyFinder;
import co.id.fifgroup.ssoa.dao.SystemPropertyMapper;
import co.id.fifgroup.ssoa.domain.SystemProperty;
import co.id.fifgroup.ssoa.domain.SystemPropertyExample;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.SystemPropertyDTO;
import co.id.fifgroup.ssoa.service.SSOACommonService;
import co.id.fifgroup.ssoa.service.SystemPropertyService;
import co.id.fifgroup.ssoa.validation.SystemPropertyValidator;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("ssoaCommonService")
public class SSOACommonServiceImpl implements SSOACommonService {

	@Autowired
	private GlobalSettingFinderMapper globalsettingMapper;
	
	@Autowired
	private StockOpnameMapper stockOpnameMapper;

	public List<GlobalSettingDTO> getGlobalsettingByCode(String code){
		return globalsettingMapper.getGlobalSettingDtoByCodeAndDescription(code, null);
	}
	
	public StockOpnameDTO getOnePersonByBranchId(Long branchId,Long companyId){
		return stockOpnameMapper.getOnePersonByBranchId(branchId,  companyId);
	}
	
	
	
	@Override
	public List<KeyValue> getLookupKeyValues(String lookupName, String key) {
		// TODO Auto-generated method stub
		return stockOpnameMapper.getLookupKeyValues(lookupName, key);
	}
}
