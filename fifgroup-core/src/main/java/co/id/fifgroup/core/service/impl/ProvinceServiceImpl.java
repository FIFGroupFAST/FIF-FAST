package co.id.fifgroup.core.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.dao.ProvinceMapper;
import co.id.fifgroup.core.domain.Province;
import co.id.fifgroup.core.domain.ProvinceExample;
import co.id.fifgroup.core.service.ProvinceService;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;

@Transactional
@Service
public class ProvinceServiceImpl implements ProvinceService {

	@Autowired
	private ProvinceMapper provinceMapper;

	@Override
	@Transactional(readOnly = true)
	public List<Province> getProvincesByExample(ProvinceExample provinceExample) {
		return provinceMapper.selectByExample(provinceExample);
	}

	@Override
	@Transactional(readOnly = true)
	public int getCountProvinceByExample(ProvinceExample provinceExample) {
		return provinceMapper.countByExample(provinceExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Province> getProvincesByExample(
			ProvinceExample provinceExample, int limit, int offset) {
		provinceExample.setOrderByClause("TRIM(UPPER(PROVINSI)) ASC");
		return provinceMapper.selectByExampleWithRowbounds(provinceExample, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly=true)
	public String getProvinceValue(String provinceCode) {
		ProvinceExample provinceExample = new ProvinceExample();
		provinceExample.createCriteria().andProvinsiCodeLikeInsensitive(provinceCode);
		List<Province> provinces = getProvincesByExample(provinceExample);
		
		for(Province province : provinces) {
			if(province.getProvinsiCode().equalsIgnoreCase(provinceCode)) 
				provinceCode = province.getProvinsi();
		}
		
		return provinceCode;
	}
	
	@Override
	@Transactional(readOnly=true)
	public String getProvinceCode(String provinceName) {
		ProvinceExample provinceExample = new ProvinceExample();
		provinceExample.createCriteria().andProvinsiLikeInsensitive(provinceName);
		List<Province> provinces = getProvincesByExample(provinceExample);
		
		for(Province province : provinces) {
			if(province.getProvinsi().equalsIgnoreCase(provinceName)) 
				provinceName = province.getProvinsiCode();
		}
		
		return provinceName;
	}
}
