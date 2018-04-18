package co.id.fifgroup.workstructure.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.workstructure.domain.Organization;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.workstructure.dto.HeadOfOrganizationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public interface OrganizationFinder {

	/**
	 * This method will query a search based on this example properties:
	 * companyId, name, levelCode, kppCode, location, and effectiveOnDate
	 * @param example
	 * @return list of OrganizationDTO that matches the specified example. 
	 */
	List<OrganizationDTO> findByExample(OrganizationDTO example);
	
	/**
	 * @param example
	 * @param rowBounds
	 * @return list of OrganizationDTO that matches the specified example and rowBounds
	 */
	List<OrganizationDTO> findByExample(OrganizationDTO example, RowBounds rowBounds);
	
	/**
	 * This method called by Organization inquiry composer.
	 * @param example
	 * @return list of OrganizationDTO
	 */
	List<OrganizationDTO> findByInquiry(OrganizationDTO example);
	
	/**
	 * 
	 * @param example
	 * @return count of organization that matches example.
	 */
	Integer countByExample(OrganizationDTO example);
	
	/**
	 * Find an Organization with specified id and version number.
	 * @param id
	 * @param versionNumber
	 * @return matched organization if found, else null.
	 */
	OrganizationDTO findByIdAndVersionNumber(@Param("id") Long id, @Param("versionNumber") Integer versionNumber);
	
	/**
	 * Find single organization with specified id and effectiveDate
	 * @param id
	 * @param effectiveDate
	 * @return matched organization if found, else null.
	 */
	OrganizationDTO findByIdAndDate(@Param("id") Long id, @Param("effectiveDate") Date effectiveDate);
	
	/**
	 * Find all versions in organization with specified id.
	 * @param id
	 * @return list of organization version number
	 */
	List<Integer> findVersionsById(@Param("id") Long id);
	
	List<OrganizationDTO> findByLevelIdAndOrgName(@Param("levelId") Long levelId, @Param("orgName") String orgName, @Param("companyId") Long companyId, RowBounds rowBounds);
	
	List<OrganizationDTO> findOrganizationBySequenceLevel(OrganizationDTO example, RowBounds rowBounds);
	
	Integer countOrganizationBySequenceLevel(OrganizationDTO example);
	
	OrganizationDTO findOrgParentByHierarchyIdAndOrgId(@Param("hierarchyId") Long hierarchyId, @Param("organizationId") Long organizationId);
	
	OrganizationDTO findById(@Param("id") Long id, @Param("companyId") Long companyId,  @Param("processDate") Date processDate);
	
	//add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
	OrganizationDTO findByIdWithoutDate(@Param("id") Long id);
	//end add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
	
	Organization findNameById(@Param("id") Long id);
	
	List<Organization> findBranches(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate);
	
	/*Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/ 
	List<Organization> findBranchesCommon(@Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate, RowBounds rowBounds);
	Integer countBranchesCommon(@Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate);
	/*end add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
	
	Integer countBranches(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate);
	
	Integer isHaveFuture(@Param("id") Long id);
	
	List<Organization> findBranchOrganization(@Param("id") Long id, @Param("companyId") Long companyId);
	
	String findBranchCode(@Param("organizationId") Long id, @Param("companyId") Long companyId);
	
	OrganizationDTO findBranch(@Param("organizationId") Long organizationId, @Param("date") Date date);
	
	OrganizationDTO findParentOrganization(@Param("id") Long id, @Param("date") Date date, @Param("companyId") Long companyId, @Param("type") String type);
	
	OrganizationDTO findLastVersionByCode(@Param("orgCode") String orgCode, @Param("companyId") Long companyId);
	
	List<Organization> findOrganizationByBranch(@Param("branchId") Long id, @Param("companyId") Long companyId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate, RowBounds rowBounds);
	
	List<Organization> findOrganizationByBranch(@Param("branchId") Long id, @Param("companyId") Long companyId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate);
	
	Integer countOrganizationByBranch(@Param("branchId") Long id, @Param("companyId") Long companyId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate);
	
	List<OrganizationDTO> findOrganizationsByCompany(OrganizationDTO example, RowBounds rowBounds);
	
	Integer countOrganizationByCompany(OrganizationDTO example);
	
	List<Organization> findByHierarchyId(@Param("hierarchyId") Long hierarchyId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("versionId") Long versionId, RowBounds rowBounds);
	
	Integer countByHierarchyId(@Param("hierarchyId") Long hierarchyId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("versionId") Long versionId);
	
	List<HeadOfOrganizationDTO> findHooHistoryByOrganizationId(@Param("organizationId") Long organizationId);
	
	List<Organization> findBranches(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, RowBounds rowBounds, @Param("effectiveDate") Date effectiveDate);
	
//	/*Add By RIM - 4060510275024_Perbaikan Report HCMS Fase 1 - Time Service - By RIM*/
//	List<Organization> findBranchesCommon(@Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, RowBounds rowBounds, @Param("effectiveDate") Date effectiveDate);
//	/*End Add By RIM - 4060510275024_Perbaikan Report HCMS Fase 1 - Time Service - By RIM*/
	
//	List<Organization> findOrganizationByBranch(@Param("branchId") Long id, @Param("companyId") Long companyId, RowBounds rowBounds);
	
	Integer isHeadOffice(@Param("organizationId") Long organizationId, @Param("processDate") Date processDate);
	
	List<Organization> findOrganizationBySecurityFilter(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, RowBounds rowBounds, @Param("effectiveDate") Date effectiveDate);
	
	List<Organization> findOrganizationBySecurityFilter(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate);
	
	Integer countOrganizationBySecurityFilter(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate);
	
	Integer findSequenceOfOrganization(@Param("id") Long id, @Param("companyId") Long companyId);
	
	Organization findByCode(@Param("organizationCode") String organizationCode, @Param("companyId") Long companyId);
	Organization findByCodeEffectiveOrganizationHierarchyDate(@Param("organizationCode") String organizationCode, @Param("companyId") Long companyId, @Param("effectiveDate") Date effectiveDate);
	
	List<OrganizationDTO> findOrganizationByHierarchy(@Param("companyId") Long companyId);
	
	List<Long> findOrganizationIdByHierarchy(@Param("companyId") Long companyId);
	
	List<Organization> findOrganizationByBranchAndSecurityFilter(@Param("companyId") Long companyId,
			@Param("branchId") Long branchId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName,
			@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("effectiveOnDate") Date effectiveOnDate, RowBounds rowBounds);
	
	int countOrganizationByBranchAndSecurityFilter(@Param("companyId") Long companyId,
			@Param("branchId") Long branchId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName,
			@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("effectiveOnDate") Date effectiveOnDate);
	
	List<Organization> findBranchBySecurityFilterAndExample(@Param("org") OrganizationDTO example, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, RowBounds rowBounds);
	
	Integer countBranchBySecurityFilterAndExample(@Param("org") OrganizationDTO example, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId);
	
	String getHeadOfficeGlCodeByCompanyId(Long companyId);
	
	Integer isFutureExist(Long id);
	
	String getOrgGlCodeByOrgId(@Param("orgId") Long orgId, @Param("companyId")Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	
	List<KeyValue> getGlCodes(@Param("code") String code, RowBounds rowBounds);
	
	List<KeyValue> getCostCenter(@Param("code") String code, RowBounds rowBounds);
	
	Integer countGlCodes(@Param("code") String code);
	
	Integer countCostCenter(@Param("code") String code);
	
	String getOrgGlCodeByBranchId(@Param("branchId") Long branchId, @Param("companyId")Long companyId);
	
	public String findProviceCodeByExample(OrganizationDTO subject);
	
	List<OrganizationDTO> findBranchByDeptOwner(@Param("versionId") List<Long> versionId, @Param("organizationId") List<Long> organizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, RowBounds rowBounds);
	int countBranchByDeptOwner(@Param("versionId") List<Long> versionId, @Param("organizationId") List<Long> organizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName);
	
	List<Organization> findBranchesWithKpp(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("kppCode") String kppCode, RowBounds rowBounds, @Param("effectiveDate") Date effectiveDate);
	Integer countBranchesWithKpp(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("kppCode") String kppCode, @Param("effectiveDate") Date effectiveDate);
	
	Long getBranchId(@Param("id")Long organizationId, @Param("companyId") Long companyId);
	
	List<Organization> findBranchesBySecurityFilter(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate, RowBounds rowBounds);
	Integer countBranchesBySecurityFilter(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate);
	
	Long getBranchOwnerByOrganizationId(@Param("organizationId") Long organizationId);
	
	Integer countByLevelIdAndOrgName(@Param("levelId") Long levelId, @Param("orgName") String orgName, @Param("companyId") Long companyId);
	
	String getCostCenterByPersonId(@Param("personId") Long personId, @Param("companyId") Long companyId);
	
	String getCostCenterByOrganizationId(@Param("organizationId") Long organizationId);
	
	List<OrganizationDTO> findBranchesWithCompanyName(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("effectiveDate") Date effectiveDate);
	
	List<Organization> findOrganizationActive(@Param("effectiveOnDate") Date effectiveOnDate, @Param("companyId") Long companyId);
	//14040715181325_CR HCMS - MPP_JAR
	List<Organization> findOrganizationAvailableInMpp(@Param("companyId") Long companyId,
			@Param("branchId") Long branchId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName,
			@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("effectiveOnDate") Date effectiveOnDate, RowBounds rowBounds);
	
	Integer countOrganizationAvailableInMpp(@Param("companyId") Long companyId,
			@Param("branchId") Long branchId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName,
			@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("effectiveOnDate") Date effectiveOnDate);
	
	public OrganizationDTO getDepartmentHead(Long organizationId);
	
	//add by jar ticket MPP list organization_id by person id
	List<Integer> findOrganizationIdByCompanyAndHierTypeAndPersonId(@Param("companyId") Long companyId, @Param("type") String type, @Param("personId") Long personId);
	//end add 14040715181325_CR HCMS - MPP_JAR
	/*to filter branch by company */
	List<Organization> findBranchesCompany(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, RowBounds rowBounds, @Param("effectiveDate") Date effectiveDate);
	List<Organization> findBranchesCompanyandBranchId(@Param("companyId") Long companyId,@Param("organizationId")Long organizationId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, RowBounds rowBounds, @Param("effectiveDate") Date effectiveDate);
	Integer countBranchesCompany(@Param("companyId") Long companyId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate);
	Integer countBranchesCompanyandBranch(@Param("companyId") Long companyId,@Param("organizationId")Long organizationId, @Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("organizationCode") String organizationCode, @Param("organizationName") String organizationName, @Param("effectiveDate") Date effectiveDate);
	List<OrganizationDTO> findBranchesCompanyDTO(@Param("companyId") Long companyId,  @Param("code") String code, @Param("name") String name, RowBounds rowBounds, @Param("effectiveDate") Date effectiveDate);
	Integer countBranchesCompanyDTO(@Param("companyId") Long companyId,  @Param("code") String code, @Param("name") String name, @Param("effectiveDate") Date effectiveDate);

	List<Organization> findBranchesWithAvailableMppByJob(
			@Param("inOrganizationId") List<Long> inOrganizationId, 
			@Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("branchCode") String branchCode, 
			@Param("branchName") String branchName, 
			@Param("companyId") Long companyId, 
			@Param("jobId") Long jobId,
			@Param("effectiveDate") Date effectiveDate, 
			RowBounds rowBounds);

	Integer countBranchesWithAvailableMppByJob(
			@Param("inOrganizationId") List<Long> inOrganizationId,
			@Param("notInOrganizationId") List<Long> notInOrganizationId, 
			@Param("branchCode") String branchCode,
			@Param("branchName") String branchName, 
			@Param("companyId") Long companyId, 
			@Param("jobId") Long jobId, 
			@Param("effectiveDate") Date effectiveDate);

	List<Organization> findOrganizationsWithAvailableMppByJobAndBranch(
			@Param("inOrganizationId") List<Long> inOrganizationId, 
			@Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("organizationCode") String organizationCode, 
			@Param("organizationName") String organizationName, 
			@Param("companyId") Long companyId,
			@Param("branchId") Long branchId,
			@Param("jobId") Long jobId, 
			@Param("effectiveDate") Date effectiveDate, 
			RowBounds rowBounds);

	Integer countOrganizationsWithAvailableMppByJobAndBranch(
			@Param("inOrganizationId") List<Long> inOrganizationId, 
			@Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("organizationCode") String organizationCode, 
			@Param("organizationName") String organizationName, 
			@Param("companyId") Long companyId,
			@Param("branchId") Long branchId,
			@Param("jobId") Long jobId, 
			@Param("effectiveDate") Date effectiveDate);
	
	// start added by WLY, Phase 2 Training Admin
	List<OrganizationDTO> findBranchesByCompanyNameAndBranchName(
			@Param("companyIdInLocation") Long companyIdInLocation, 
			@Param("inOrganizationId") List<Long> inOrganizationId, 
			@Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("companyName") String companyName,
			@Param("organizationCode") String organizationCode, 
			@Param("organizationName") String organizationName, 
			RowBounds rowBounds, 
			@Param("effectiveDate") Date effectiveDate, 
			@Param("areaId") Long areaId);

	Integer countBranchesByCompanyNameAndBranchName(
			@Param("companyIdInLocation") Long companyIdInLocation, 
			@Param("inOrganizationId") List<Long> inOrganizationId, 
			@Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("companyName") String companyName,
			@Param("organizationCode") String organizationCode, 
			@Param("organizationName") String organizationName, 
			@Param("effectiveDate") Date effectiveDate,
			@Param("areaId") Long areaId);

	List<OrganizationDTO> getOrganizationByBranchAndCompanyNameAndSecurityFilter(
			@Param("companyId") Long companyId, 
			@Param("branchId") Long branchId, 
			@Param("companyName") String companyName,
			@Param("organizationCode") String organizationCode, 
			@Param("organizationName") String organizationName,
			@Param("inOrganizationId") List<Long> inOrganizationId, 
			@Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("effectiveOnDate") Date effectiveOnDate, 
			RowBounds rowBounds);

	int countOrganizationByBranchAndCompanyNameAndSecurityFilter(
			@Param("companyId") Long companyId, 
			@Param("branchId") Long branchId, 
			@Param("companyName") String companyName,
			@Param("organizationCode") String organizationCode, 
			@Param("organizationName") String organizationName,
			@Param("inOrganizationId") List<Long> inOrganizationId, 
			@Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("effectiveOnDate") Date effectiveOnDate);
	// end added by WLY, Phase 2 Training Admin
	
}
