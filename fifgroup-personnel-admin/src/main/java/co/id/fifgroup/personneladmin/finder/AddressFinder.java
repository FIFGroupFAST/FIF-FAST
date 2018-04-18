package co.id.fifgroup.personneladmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.AddressDTO;

public interface AddressFinder {

	public List<AddressDTO> getAddressByPersonIdAndCompanyId(@Param("personId") Long personId, @Param("companyId") Long companyId);
	public List<AddressDTO> getAddressByCriteria(@Param("personId") Long personId, @Param("companyId") Long companyId, 
			@Param("ktp") boolean ktp, @Param("physical") boolean physical);
}
