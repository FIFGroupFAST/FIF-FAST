package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.ModuleFinder;
import co.id.fifgroup.basicsetup.dao.ModuleMapper;
import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.dto.ModuleDTO;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.basicsetup.validation.ModuleValidator;
import co.id.fifgroup.core.validation.ValidationException;

@Transactional
@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private ModuleMapper moduleMapper;
	@Autowired
	private ModuleFinder moduleFinderMapper;
	@Autowired
	private ModuleValidator moduleValidator;

	@Transactional
	private void insertModule(Module module) {
		moduleMapper.insert(module);
	}

	@Transactional
	private void updateModule(Module module) {
		moduleMapper.updateByPrimaryKey(module);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ModuleDTO> getModuleDtoByModuleName(String moduleName) {
		return moduleFinderMapper.getModuleDtoByModuleName(moduleName);
	}

	@Override
	@Transactional(readOnly = true)
	public int getCountModuleDtoByModuleName(String moduleName) {
		return moduleFinderMapper.getCountModuleDtoByModuleName(moduleName);
	}

	@Override
	@Transactional(readOnly = true)
	public Module getModuleById(Long moduleId) {
		return moduleMapper.selectByPrimaryKey(moduleId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Module> getModuleByExample(ModuleExample moduleExample) {
		return moduleMapper.selectByExample(moduleExample);
	}

	@Transactional(readOnly = true)
	private List<Module> selectByExample(ModuleExample moduleExample) {
		return moduleMapper.selectByExample(moduleExample);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isExistModuleForUpdate(Module record) {
		ModuleExample countModuleByName = new ModuleExample();
		countModuleByName.createCriteria().andModuleNameEqualTo(record.getModuleName());
		if(countByExample(countModuleByName) > 0) {
			ModuleExample selectModuleByCode = new ModuleExample();
			selectModuleByCode.createCriteria().andModuleCodeEqualTo(record.getModuleCode()).andModuleNameEqualTo(record.getModuleName());
			if(selectByExample(selectModuleByCode).size() > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(ModuleExample example) {
		return moduleMapper.countByExample(example);
	}

	@Override
	@Transactional
	public void save(Module module) throws ValidationException{
		moduleValidator.validate(module);
		if(module.getModuleId() == null) {
			insertModule(module);
		} else {
			updateModule(module);
		}
		
	}
}
