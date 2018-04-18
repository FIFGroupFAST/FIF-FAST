package co.id.fifgroup.workstructure.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.version.VersioningService;
import co.id.fifgroup.workstructure.domain.Location;
import co.id.fifgroup.workstructure.domain.LocationExample;

import co.id.fifgroup.workstructure.dto.LocationDTO;

public interface LocationSetupService extends VersioningService<LocationDTO>{

	void save(LocationDTO selectedLocation) throws Exception;
	void delete(LocationDTO selectedLocation);	
	List<LocationDTO> findByInquiry(LocationDTO example);
	List<LocationDTO> findByExample(LocationDTO example);
	List<LocationDTO> findByExample(LocationDTO example, int limit, int offset);
	Integer countByExample(LocationDTO example);
	LocationDTO findById(Long id);
	LocationDTO findByIdAndDate(Long id, Date effectiveDate);
	List<Location> findByExample(LocationExample example);
	List<Location> findByExample(LocationExample example, int limit, int offset);
	Integer countByExample(LocationExample example);
	LocationDTO findByCode(String locationCode);
	LocationDTO findByCode(String locationCode, Date effectiveStartDate);
	String findIndexColaOnOtherInfo(Long locationId);
	String findIndexColaOnOtherInfo(Long locationId, Date effectiveOnDate); 
	Boolean isFutureExist(Long id);
	Long findVersionIdByIdAndDate(Long id, Date effectiveDate);
	public LocationDTO getLocationHomeBase(Long companyId, String locationCode, Date effectiveOnDate);
	public List<LocationDTO> findActiveLocationForLov(LocationDTO example, int limit, int offset);
	public Integer countActiveLocationForLov(LocationDTO example);
	Location findByPrimaryKey(Long locationId);
	Location findByName(String locationName, Long companyId);
	LocationDTO findLocationByOrganizationId(Long organizationId,Long companyId, Date effectiveDate);
}
