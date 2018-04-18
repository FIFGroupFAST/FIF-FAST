package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.FormulaEngineMapper;
import co.id.fifgroup.basicsetup.service.ServiceModel;

@Service
@Transactional(readOnly = true)
public class ServiceGroovyImpl implements ServiceModel {

	@Autowired
	private FormulaEngineMapper formulaEngineMapper;
	
	@Override
	public List<Map<String, Object>> getFromQuery(String sql) {
		return formulaEngineMapper.getFromQuery(sql);
	}

}
