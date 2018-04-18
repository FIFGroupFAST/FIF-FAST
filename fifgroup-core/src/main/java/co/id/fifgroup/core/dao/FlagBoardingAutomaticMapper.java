package co.id.fifgroup.core.dao;

import org.apache.ibatis.annotations.Param;

public interface FlagBoardingAutomaticMapper {
	/* add by PMW, ticket 15111616041796, 15-Feb-2016 */
	public String getUpdateFlagBoarding(
			@Param("originLocation") Long originLocation,
			@Param("destinationLocation") Long destinationLocation,
			@Param("assignmentStatus") String assignmentStatus,
			@Param("employeeCategory") String employeeCategory,
			@Param("transferBy") String transferBy,
			@Param("isKeyJob") int isKeyJob,
			@Param("flagHousing") String flagHousing,
			@Param("isPlacmentStatus") int isPlacmentStatus,
			@Param("companyId") int companyId);

	public String getPlacementStatus(@Param("personId") Long personId,@Param("companyId") int companyId);
	
	public String getIdLocation(@Param("locationName") String locationName,@Param("companyId") int companyId);
	
	public String getFlagBoarding(@Param("personId") Long personId,@Param("companyId") int companyId);
	
	public String getEmployementCategory(@Param("personId") Long personId,@Param("companyId") int companyId);
	/* end add */
}
