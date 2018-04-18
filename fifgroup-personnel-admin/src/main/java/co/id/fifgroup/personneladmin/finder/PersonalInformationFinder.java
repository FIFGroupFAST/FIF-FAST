package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.EquipmentAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonalInformationDTO;

import co.id.fifgroup.workstructure.dto.LocationDTO;

public interface PersonalInformationFinder {

	public PersonalInformationDTO getPersonalInformationByEffectiveOnDate (@Param("personId") Long personId,
			@Param("companyId") Long companyId, @Param("employeeNumber") String employeeNumber, @Param("effectiveOnDate") Date effectiveOnDate);
	
	public String getMaritalStatusByEffectiveOnDate(@Param("personId") Long personId,
			@Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	
	public Date getMaxHireDate(@Param("personId") Long personId, @Param("companyId") Long companyId);
	
	public LocationDTO getLocationByHomebase(@Param("personId") Long personId, @Param("companyId") Long companyId,
			@Param("effectiveOnDate") Date effectiveOnDate);
	
	public List<EquipmentAssignmentDTO> getWorkingEquipmentList(
			@Param("personId") Long personId,@Param("companyId") Long companyId);

}
