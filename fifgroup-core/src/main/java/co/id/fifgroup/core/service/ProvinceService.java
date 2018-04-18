package co.id.fifgroup.core.service;

import java.util.List;

import co.id.fifgroup.core.domain.Province;
import co.id.fifgroup.core.domain.ProvinceExample;

public interface ProvinceService {

	public List<Province> getProvincesByExample(ProvinceExample provinceExample);
	public List<Province> getProvincesByExample(ProvinceExample provinceExample, int limit, int offset);
	public int getCountProvinceByExample(ProvinceExample provinceExample);
	public String getProvinceValue(String provinceCode);
	public String getProvinceCode(String provinceValue);
}
