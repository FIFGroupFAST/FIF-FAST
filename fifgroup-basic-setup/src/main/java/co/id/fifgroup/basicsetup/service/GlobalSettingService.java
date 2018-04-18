package co.id.fifgroup.basicsetup.service;
import java.util.List;

import co.id.fifgroup.basicsetup.domain.GlobalSetting;
import co.id.fifgroup.basicsetup.domain.GlobalSettingExample;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;

/**
 * @author Achmad Ridwan
 * @version 1.0
 * @created 14-Feb-2013 3:23:51 PM
 */
public interface GlobalSettingService {

	/**
	 * 
	 * @param globalSetting
	 */
	public void save(GlobalSetting globalSetting) throws Exception;
	public int getCountGlobalSettingByExample(GlobalSettingExample globalSettingExample);
	public List<GlobalSetting> getGlobalSettingByExampleWithRowbounds(GlobalSettingExample globalSettingExample, int limit, int offset);
	public List<GlobalSetting> getGlobalSettingByExample(GlobalSettingExample globalSettingExample);
	public GlobalSetting getGlobalSettingByCode(String globalSettingCode);
	public List<GlobalSettingDTO> getGlobalSettingDtoByCode(String code, String description);
	public int getCountGlobalSettingDtoByCode(String code);
	
}