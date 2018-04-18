package co.id.fifgroup.core.service;

import java.util.List;

import co.id.fifgroup.core.domain.City;
import co.id.fifgroup.core.domain.CityExample;

public interface CityService {

	public List<City> getCitiesByExample(CityExample cityExample);
	public List<City> getCitiesByExample(CityExample cityExample, int limit, int offset);
	public int getCountCityByExample(CityExample cityExample);
	public String getCityValue(String cityCode);
	public String getCityCode(String cityValue);
	
}
