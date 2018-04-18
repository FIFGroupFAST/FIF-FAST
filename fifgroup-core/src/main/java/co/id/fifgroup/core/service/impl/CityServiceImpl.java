package co.id.fifgroup.core.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.dao.CityMapper;
import co.id.fifgroup.core.domain.City;
import co.id.fifgroup.core.domain.CityExample;
import co.id.fifgroup.core.domain.CityExample.Criteria;
import co.id.fifgroup.core.service.CityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;

@Transactional
@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityMapper cityMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<City> getCitiesByExample(CityExample cityExample) {
		return cityMapper.selectByExample(cityExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<City> getCitiesByExample(CityExample cityExample, int limit,
			int offset) {
		cityExample.setOrderByClause("TRIM(UPPER(CITY)) ASC");
		return cityMapper.selectByExampleWithRowbounds(cityExample, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountCityByExample(CityExample cityExample) {
		return cityMapper.countByExample(cityExample);
	}
	
	@Override
	@Transactional(readOnly=true)
	public String getCityValue(String cityCode) {
		CityExample cityExample = new CityExample();
		cityExample.createCriteria().andCityCodeLikeInsensitive(cityCode);
		List<City> cities = getCitiesByExample(cityExample);
		
		for(City city : cities) {
			if(city.getCityCode().equalsIgnoreCase(cityCode)) 
				cityCode = city.getCity();
		}
		
		return cityCode;
	}
	
	@Override
	@Transactional(readOnly=true)	
	public String getCityCode(String cityValue) {
		CityExample cityExample = new CityExample();
		cityExample.createCriteria().andCityLikeInsensitive(cityValue);
		List<City> cities = getCitiesByExample(cityExample);
		
		for(City city : cities) {
			if(city.getCity().equalsIgnoreCase(cityValue))
				cityValue = city.getCityCode();
		}
		
		return cityValue;
	}
}
