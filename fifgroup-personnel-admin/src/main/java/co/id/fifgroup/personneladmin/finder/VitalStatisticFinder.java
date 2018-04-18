package co.id.fifgroup.personneladmin.finder;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.VitalStatisticDTO;

public interface VitalStatisticFinder {

	public VitalStatisticDTO getVitalStatisticByPersonIdAndCompanyId(@Param("personId") Long personId, 
			@Param("companyId") Long companyId);
}
