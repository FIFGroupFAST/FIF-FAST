package co.id.fifgroup.personneladmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.AwardDTO;

public interface AwardFinder {

	public List<AwardDTO> getAwardByPersonIdAndCompanyId(@Param("personId") Long personId, @Param("companyId") Long companyId); 
}
