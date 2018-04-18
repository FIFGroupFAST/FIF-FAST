package co.id.fifgroup.basicsetup.service;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.dto.ModuleDTO;

public interface ModuleService {
	
	public List<Module> getModuleByExample(ModuleExample moduleExample);
	public Module getModuleById(Long moduleId);
	public List<ModuleDTO> getModuleDtoByModuleName(String moduleName);
	public int getCountModuleDtoByModuleName(String moduleName);
	public void save(Module module) throws Exception;
	public boolean isExistModuleForUpdate(Module subject);
	public int countByExample(ModuleExample example);
	
}
