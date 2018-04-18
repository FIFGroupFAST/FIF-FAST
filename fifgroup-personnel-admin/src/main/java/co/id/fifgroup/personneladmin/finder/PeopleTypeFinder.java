package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.PeopleTypeDTO;

public interface PeopleTypeFinder {

	public PeopleTypeDTO getPeopleTypeByEffectiveOnDate(@Param("personId") Long personId, @Param("companyId") Long companyId,
			@Param("effectiveOnDate") Date effectiveOnDate);
	public Date getMaxEffectiveStartDate(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("peopleType") co.id.fifgroup.personneladmin.constant.PeopleType peopleType);
	
	public Date getMinEffectiveStartDate(@Param("personId") Long personId, @Param("companyId") Long companyId);
	
	public List<PeopleTypeDTO> getPeopleTypeEmployee(@Param("personId") Long personId, @Param("companyId") Long companyId);
	
	public int countPeopleInCompany(@Param("personId") Long personId, @Param("companyId") Long companyId);
	
	public List<PeopleTypeDTO> getPeopleTypeByUUID(@Param("personUUID") UUID personUUID);
	
	public PeopleTypeDTO getFuturePeopleType(@Param("personId") Long personId, @Param("companyId") Long companyId);
}
