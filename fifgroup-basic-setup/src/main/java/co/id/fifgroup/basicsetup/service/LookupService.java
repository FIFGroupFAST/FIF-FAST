package co.id.fifgroup.basicsetup.service;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

import co.id.fifgroup.basicsetup.constant.LookupType;
import co.id.fifgroup.basicsetup.domain.LookupDynamic;
import co.id.fifgroup.basicsetup.domain.LookupHeader;
import co.id.fifgroup.basicsetup.domain.LookupHeaderExample;
import co.id.fifgroup.basicsetup.dto.LookupDTO;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface LookupService {

	public void save(LookupDTO lookupDto) throws Exception;
	public List<KeyValue> getLookups(String lookupName, Long groupId, Long scope);
	public KeyValue getLookup(String lookupName, String key, Long groupId, Long scope);
	public int getCountLookup(String lookupName, Long groupId, Long scope);
	public List<KeyValue> getLookups(String lookupName, Long groupId, Long scope, boolean active);
	public List<LookupHeader> getLookupHeaderDependent();
	public List<LookupDTO> getLookupHeaderDtoByLookupNameAndDescriptionAndType(String lookupName, String description, LookupType lookupType, Long groupId);
	public int getCountLookupHeaderDtoByLookupNameAndDescriptionAndType(String lookupName, String description, LookupType lookupType, Long groupId);
	public LookupDTO getLookupByLookupId(Long lookupId);
	public List<LookupHeader> getLookupHeaderByExample(LookupHeaderExample lookupHeaderExample, int limit, int offset);
	public int getCountLookupHeaderByExample(LookupHeaderExample lookupHeaderExample);
	public List<LookupHeader> getLookupHeaderByExample(LookupHeaderExample lookupHeaderExample);
	public void validateDynamicSql(LookupDynamic lookupDynamic) throws SQLSyntaxErrorException;
	public LookupHeader getLookupHeaderByPrimaryKey(Long id);
	public KeyValue getLookupCaseInsensitive(String lookupName, String key, Long groupId, Long scope);
	public List<KeyValue> getLookupKeyValues(String lookupName, String key);
	
	//Add by HBP 15103120081532 - Modifikasi Form PD Untuk Budget Control
	public LookupDTO getLookupByName(String lookupName); 
	//End add by HBP 15103120081532 - Modifikasi Form PD Untuk Budget Control
}
