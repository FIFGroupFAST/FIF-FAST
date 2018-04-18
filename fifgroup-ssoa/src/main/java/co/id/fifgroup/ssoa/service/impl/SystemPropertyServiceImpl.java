package co.id.fifgroup.ssoa.service.impl;

import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.ssoa.dao.SystemPropertyFinder;
import co.id.fifgroup.ssoa.dao.SystemPropertyMapper;
import co.id.fifgroup.ssoa.domain.SystemProperty;
import co.id.fifgroup.ssoa.domain.SystemPropertyExample;
import co.id.fifgroup.ssoa.dto.SystemPropertyDTO;
import co.id.fifgroup.ssoa.service.SystemPropertyService;
import co.id.fifgroup.ssoa.validation.SystemPropertyValidator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class SystemPropertyServiceImpl implements SystemPropertyService {

	@Autowired
	private SystemPropertyMapper systemPropertyMapper;
	@Autowired
	private SystemPropertyFinder systemPropertyFinderMapper;
	@Autowired
	private SystemPropertyValidator systemPropertyValidator;

	@Transactional
	private void insertSystemProperty(SystemProperty systemProperty) {
		systemPropertyMapper.insert(systemProperty);
	}

	@Transactional
	private void updateSystemProperty(SystemProperty systemProperty) {
		systemPropertyMapper.updateByPrimaryKey(systemProperty);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SystemPropertyDTO> getSystemPropertyDtoBySystemPropertyCodeName(String systemPropertyCode, String systemPropertyName) {
		return systemPropertyFinderMapper.getSystemPropertyDtoBySystemPropertyCodeName(systemPropertyCode, systemPropertyName);
	}

	@Override
	@Transactional(readOnly = true)
	public int getCountSystemPropertyDtoBySystemPropertyCodeName(String systemPropertyCode, String systemPropertyName) {
		return systemPropertyFinderMapper.getCountSystemPropertyDtoBySystemPropertyCodeName(systemPropertyCode, systemPropertyName);
	}

	@Override
	@Transactional(readOnly = true)
	public SystemProperty getSystemPropertyById(String systemPropertyCode) {
		return systemPropertyMapper.selectByPrimaryKey(systemPropertyCode);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SystemProperty> getSystemPropertyByExample(SystemPropertyExample systemPropertyExample) {
		return systemPropertyMapper.selectByExample(systemPropertyExample);
	}

	@Transactional(readOnly = true)
	private List<SystemProperty> selectByExample(SystemPropertyExample systemPropertyExample) {
		return systemPropertyMapper.selectByExample(systemPropertyExample);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isExistSystemPropertyForUpdate(SystemProperty record) {
		SystemPropertyExample countSystemPropertyByName = new SystemPropertyExample();
		countSystemPropertyByName.createCriteria().andSystemPropertyNameEqualTo(record.getSystemPropertyName());
		if(countByExample(countSystemPropertyByName) > 0) {
			SystemPropertyExample selectSystemPropertyByCode = new SystemPropertyExample();
			selectSystemPropertyByCode.createCriteria().andSystemPropertyCodeEqualTo(record.getSystemPropertyCode()).andSystemPropertyNameEqualTo(record.getSystemPropertyName());
			if(selectByExample(selectSystemPropertyByCode).size() > 0) {
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
	public int countByExample(SystemPropertyExample example) {
		return systemPropertyMapper.countByExample(example);
	}

	@Override
	@Transactional
	public void save(SystemProperty systemProperty) throws ValidationException{
		
		SystemProperty sp = getSystemPropertyById(systemProperty.getSystemPropertyCode());
		if(sp == null){
			insertSystemProperty(systemProperty);
		}else{
			updateSystemProperty(systemProperty);
		}
		
		systemPropertyValidator.validate(systemProperty);
		if(systemProperty.getSystemPropertyCode() == null) {
			insertSystemProperty(systemProperty);
		} else {
			updateSystemProperty(systemProperty);
		}
	}

	@Override
	@Transactional
	public void delete(SystemPropertyDTO systemPropertyDto) throws Exception{
		systemPropertyMapper.deleteByPrimaryKey(systemPropertyDto.getSystemPropertyCode());
	}
}
