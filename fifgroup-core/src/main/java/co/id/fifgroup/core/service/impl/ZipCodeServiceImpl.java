package co.id.fifgroup.core.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.dao.ZipCodeDtoMapper;
import co.id.fifgroup.core.dao.ZipCodeMapper;
import co.id.fifgroup.core.domain.ZipCode;
import co.id.fifgroup.core.domain.ZipCodeExample;
import co.id.fifgroup.core.dto.ZipCodeDto;
import co.id.fifgroup.core.service.ZipCodeService;

@Transactional
@Service
public class ZipCodeServiceImpl implements ZipCodeService {

	@Autowired
	private ZipCodeMapper zipCodeMapper;
	@Autowired
	private ZipCodeDtoMapper zipCodeDtoMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<ZipCode> getZipCodesByExample(ZipCodeExample zipCodeExample) {
		return zipCodeMapper.selectByExample(zipCodeExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ZipCode> getZipCodesByExample(ZipCodeExample zipCodeExample,
			int limit, int offset) {
		return zipCodeMapper.selectByExampleWithRowbounds(zipCodeExample, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountZipCodeByExample(ZipCodeExample zipCodeExample) {
		return zipCodeMapper.countByExample(zipCodeExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ZipCodeDto> getZipCodeByKecamatanAndKelurahanAndZipCode(
			String kecamatan, String kelurahan, String zipCode, String provinceCode, String cityCode, String kecamatanCode, String kelurahanCode) {
		return zipCodeDtoMapper.getZipCodeByKecamatanAndKelurahanAndZipCode(kecamatan, kelurahan, zipCode, provinceCode, cityCode, kecamatanCode, kelurahanCode);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ZipCodeDto> getZipCodeByKecamatanAndKelurahanAndZipCode(
			String kecamatan, String kelurahan, String zipCode, String provinceCode, String cityCode, String kecamatanCode, String kelurahanCode, int limit,
			int offset) {
		return zipCodeDtoMapper.getZipCodeByKecamatanAndKelurahanAndZipCodeWithRowbounds(kecamatan, kelurahan, zipCode, provinceCode, cityCode, kecamatanCode, kelurahanCode, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountZipCodeByKecamatanAndKelurahanAndZipCode(
			String kecamatan, String kelurahan, String zipCode, String provinceCode, String cityCode, String kecamatanCode, String kelurahanCode) {
		return zipCodeDtoMapper.getCountZipCodeByKecamatanAndKelurahanAndZipCode(kecamatan, kelurahan, zipCode, provinceCode, cityCode, kecamatanCode, kelurahanCode);
	}

}
