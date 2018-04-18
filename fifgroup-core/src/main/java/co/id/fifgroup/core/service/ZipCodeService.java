package co.id.fifgroup.core.service;

import java.util.List;

import co.id.fifgroup.core.domain.ZipCode;
import co.id.fifgroup.core.domain.ZipCodeExample;
import co.id.fifgroup.core.dto.ZipCodeDto;

public interface ZipCodeService {

	public List<ZipCode> getZipCodesByExample(ZipCodeExample zipCodeExample);
	public List<ZipCode> getZipCodesByExample(ZipCodeExample zipCodeExample, int limit, int offset);
	public int getCountZipCodeByExample(ZipCodeExample zipCodeExample);
	public List<ZipCodeDto> getZipCodeByKecamatanAndKelurahanAndZipCode(String kecamatan, String kelurahan, String zipCode, String provinceCode, String cityCode, String kecamatanCode, String kelurahanCode);
	public List<ZipCodeDto> getZipCodeByKecamatanAndKelurahanAndZipCode(String kecamatan, String kelurahan, String zipCode, String provinceCode, String cityCode, String kecamatanCode, String kelurahanCode, int limit, int offset);
	public int getCountZipCodeByKecamatanAndKelurahanAndZipCode(String kecamatan, String kelurahan, String zipCode, String provinceCode, String cityCode, String kecamatanCode, String kelurahanCode);
	
}
