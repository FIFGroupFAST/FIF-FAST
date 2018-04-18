package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.basicsetup.dto.ModuleDTO;

public interface ModuleFinder {

	public List<ModuleDTO> getModuleDtoByModuleName(@Param("moduleName") String moduleName);
	public int getCountModuleDtoByModuleName(@Param("moduleName") String moduleName);
	
}
