package co.id.fifgroup.basicsetup.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.GlobalSettingFinderMapper;
import co.id.fifgroup.basicsetup.dao.GlobalSettingMapper;
import co.id.fifgroup.basicsetup.domain.GlobalSetting;
import co.id.fifgroup.basicsetup.domain.GlobalSettingExample;
import co.id.fifgroup.basicsetup.domain.GlobalSettingExample.Criteria;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.basicsetup.service.GlobalSettingService;
import co.id.fifgroup.basicsetup.validation.GlobalSettingValidator;
import co.id.fifgroup.core.service.GlobalSettingServiceAdapter;

@Transactional
@Service
public class GlobalSettingServiceImpl implements GlobalSettingService, GlobalSettingServiceAdapter {

	@Autowired
	private GlobalSettingMapper globalSettingMapper;
	@Autowired
	private GlobalSettingFinderMapper globalSettingFinderMapper;
	@Autowired
	private GlobalSettingValidator globalSettingValidator;
	
	private static final Map<String, GlobalSetting> cachedValues= Collections.synchronizedMap(new HashMap<String, GlobalSetting>());

	@Override
	@Transactional
	public void save(GlobalSetting globalSetting) throws Exception {
		globalSettingValidator.validate(globalSetting);
		if(globalSetting.getGlobalSettingId() == null) {
			globalSettingMapper.insert(globalSetting);
		} else {
			globalSettingMapper.updateByPrimaryKey(globalSetting);
		}
		cachedValues.put(globalSetting.getCode(), globalSetting);
	}

	@Override
	@Transactional(readOnly=true)
	public GlobalSetting getGlobalSettingByCode(String globalSettingCode) {
		if (cachedValues.containsKey(globalSettingCode))
			return cachedValues.get(globalSettingCode);
		GlobalSettingExample globalSettingExample = new GlobalSettingExample();
		Criteria criteriaGlobalSetting = globalSettingExample.createCriteria();
		criteriaGlobalSetting.andCodeEqualTo(globalSettingCode);
		List<GlobalSetting> resultGlobalSetting = globalSettingMapper.selectByExample(globalSettingExample);
		if(resultGlobalSetting.size() == 1) {
			cachedValues.put(globalSettingCode, resultGlobalSetting.get(0));
			return resultGlobalSetting.get(0);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountGlobalSettingByExample(
			GlobalSettingExample globalSettingExample) {
		return globalSettingMapper.countByExample(globalSettingExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<GlobalSetting> getGlobalSettingByExampleWithRowbounds(
			GlobalSettingExample globalSettingExample, int limit, int offset) {
		return globalSettingMapper.selectByExampleWithRowbounds(globalSettingExample, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public List<GlobalSetting> getGlobalSettingByExample(
			GlobalSettingExample globalSettingExample) {
		return globalSettingMapper.selectByExample(globalSettingExample);
	}

	@Override
	@Transactional(readOnly=true)
	public String getGlobalSettingValueByCode(String globalSettingCode) {
		GlobalSetting globalSetting = getGlobalSettingByCode(globalSettingCode);
		if(globalSetting != null) {
			return globalSetting.getValue();
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<GlobalSettingDTO> getGlobalSettingDtoByCode(
			String code, String description) {
		return globalSettingFinderMapper.getGlobalSettingDtoByCodeAndDescription(code, description);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountGlobalSettingDtoByCode(String code) {
		return globalSettingFinderMapper.getCountGlobalSettingDtoByCode(code);
	}

}
