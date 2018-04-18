package co.id.fifgroup.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.dto.ZipCodeDto;

public interface ZipCodeDtoMapper {

	public List<ZipCodeDto> getZipCodeByKecamatanAndKelurahanAndZipCode(
					@Param("kecamatan") String kecamatan, @Param("kelurahan") String kelurahan, @Param("zipCode") String zipCode, 
					@Param("provinceCode") String provinceCode, @Param("cityCode") String cityCode, @Param("kecamatanCode") String kecamatanCode,
					@Param("kelurahanCode") String kelurahanCode);
	public List<ZipCodeDto> getZipCodeByKecamatanAndKelurahanAndZipCodeWithRowbounds(
					@Param("kecamatan") String kecamatan, @Param("kelurahan") String kelurahan, @Param("zipCode") String zipCode, 
					@Param("provinceCode") String provinceCode, @Param("cityCode") String cityCode, @Param("kecamatanCode") String kecamatanCode,
					@Param("kelurahanCode") String kelurahanCode, RowBounds rowBounds);
	public int getCountZipCodeByKecamatanAndKelurahanAndZipCode(
					@Param("kecamatan") String kecamatan, @Param("kelurahan") String kelurahan, @Param("zipCode") String zipCode, 
					@Param("provinceCode") String provinceCode, @Param("cityCode") String cityCode, @Param("kecamatanCode") String kecamatanCode,
					@Param("kelurahanCode") String kelurahanCode);
	
}
