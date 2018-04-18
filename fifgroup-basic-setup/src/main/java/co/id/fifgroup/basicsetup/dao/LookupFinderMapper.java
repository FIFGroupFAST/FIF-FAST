package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.basicsetup.constant.LookupType;
import co.id.fifgroup.basicsetup.dto.LookupDTO;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface LookupFinderMapper {

	public List<LookupDTO> getLookupHeaderDtoByLookupNameAndDescriptionAndType(@Param("lookupName") String lookupName, @Param("description") String description, @Param("lookupType") LookupType lookupType, @Param("groupId") Long groupId);
	public int getCountLookupHeaderDtoByLookupNameAndDescriptionAndType(@Param("lookupName") String lookupName, @Param("description") String description, @Param("lookupType") LookupType lookupType, @Param("groupId") Long groupId);
	public LookupDTO getLookupDependentByLookupId(Long lookupId);
	public LookupDTO getLookupDynamicByLookupId(Long lookupId);
	public KeyValue getLookupDependentWithKeyCaseInsensitive(@Param("lookupId") Long lookupId, @Param("key") String key);
	public List<KeyValue> getLookupKeyValues(@Param("lookupName") String lookupName, @Param("key")String key);
	
	//Add by HBP 15103120081532 - Modifikasi Form PD Untuk Budget Control 
	public LookupDTO getLookupByName(@Param("lookupName")String lookupName);
	//End add by HBP 15103120081532 - Modifikasi Form PD Untuk Budget Control
}
