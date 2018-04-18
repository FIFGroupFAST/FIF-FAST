package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.HousingAllowanceDTO;

public interface HousingAllowanceFinder {
	 HousingAllowanceDTO selectHousingAllowanceByEffectiveOnDate(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	 HousingAllowanceDTO selectHousingAllowanceByEffectiveOnDateByMaxId(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	 public int countHousingAllowanceByEffectiveOnDate(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	 public int countStatusFlagHousing(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	 public int countFutureVersionHousingAllowance(@Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate,@Param("personId") Long personId);
	 List<HousingAllowanceDTO> selectHousingAllowanceByEffectiveOnDateList(@Param("personId") Long personId, @Param("companyId") Long companyId);

}
