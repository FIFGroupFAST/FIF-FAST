package co.id.fifgroup.personneladmin.finder;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.AccountInformationDTO;

public interface AccountInformationFinder {

	public AccountInformationDTO getAccountInformationByEffectiveOnDate(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
}
