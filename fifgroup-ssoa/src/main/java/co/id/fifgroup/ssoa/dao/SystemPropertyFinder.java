package co.id.fifgroup.ssoa.dao;

import co.id.fifgroup.ssoa.dto.SystemPropertyDTO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SystemPropertyFinder {
	public List<SystemPropertyDTO> getSystemPropertyDtoBySystemPropertyCodeName(@Param("systemPropertyCode") String systemPropertyCode, @Param("systemPropertyName") String systemPropertyName);
	public int getCountSystemPropertyDtoBySystemPropertyCodeName(@Param("systemPropertyCode") String systemPropertyCode, @Param("systemPropertyName") String systemPropertyName);
}