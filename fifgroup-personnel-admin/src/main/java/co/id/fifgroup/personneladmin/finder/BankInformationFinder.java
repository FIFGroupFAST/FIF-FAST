package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.BankInformationDTO;

public interface BankInformationFinder {

	public BankInformationDTO getBankInformationByEffectiveOnDate(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	public List<BankInformationDTO> getBankInformationByPerson(@Param("listPersons") List<Long> listPersons, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
}
