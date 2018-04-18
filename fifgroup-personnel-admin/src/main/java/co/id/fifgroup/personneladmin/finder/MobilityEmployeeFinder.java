package co.id.fifgroup.personneladmin.finder;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.MobilityEmployeeDTO;

public interface MobilityEmployeeFinder {

	public MobilityEmployeeDTO getMobilityByPersonIdAndCompanyId(@Param("personId") Long personId, 
			@Param("companyId") Long companyId);

	public String getZoneName(@Param("zoneId") Long zoneId);
}
