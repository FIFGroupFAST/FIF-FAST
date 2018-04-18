package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.OtherInfoDTO;

public interface OtherInfoPersonnelFinder {

	public List<OtherInfoDTO> getOtherInfoByEffectiveOnDate(@Param("personId") Long personId, 
			@Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
}
