package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;

public interface GlobalSettingFinderMapper {

	public List<GlobalSettingDTO> getGlobalSettingDtoByCodeAndDescription(@Param("code") String code, @Param("description") String description);
	public int getCountGlobalSettingDtoByCode(@Param("code") String code);
	
}
