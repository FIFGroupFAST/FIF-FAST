package co.id.fifgroup.workstructure.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.workstructure.dto.LocationDTO;

public interface LocationFinder {
	
	List<LocationDTO> findByInquiry(LocationDTO example);
	List<LocationDTO> findByExample(LocationDTO example);
	List<LocationDTO> findByExample(LocationDTO example, RowBounds rowBounds);
	Integer countByExample(LocationDTO example);
	LocationDTO findByIdAndVersionNumber(@Param("id") Long id, @Param("versionNumber") Integer versionNumber);
	LocationDTO findByIdAndDate(@Param("id") Long id, @Param("effectiveDate") Date effectiveDate);
	List<Integer> findVersionsById(@Param("id") Long id);
	LocationDTO findLastVersionByCode(@Param("locationCode") String locationCode, @Param("companyId") Long companyId);
	LocationDTO findById(@Param("id") Long id);
	Integer isHaveFuture(@Param("id") Long id);
	LocationDTO findByCode(@Param("locationCode") String locationCode, @Param("companyId") Long companyId,  @Param("effectiveDate") Date effectiveDate);
	String findIndexColaOnOtherInfo(@Param("locationId") Long locationId, @Param("effectiveOnDate") Date effectiveOnDate);
	Integer isFutureExist(@Param("id") Long id);
	public Long findVersionIdByIdAndDate(@Param("id") Long id, @Param("effectiveDate") Date effectiveDate);
	public LocationDTO getLocationHomeBase(@Param("companyId") Long companyId, @Param("locationCode") String locationCode, @Param("effectiveOnDate") Date effectiveOnDate);
	public List<LocationDTO> findActiveLocationForLov(LocationDTO example, RowBounds rowBounds);
	public Integer countActiveLocationForLov(LocationDTO example);
	public LocationDTO findLocationByOrganizationId(@Param("organizationId") Long organizationId,@Param("companyId") Long companyId,
			@Param("effectiveDate") Date effectiveDate);
}
