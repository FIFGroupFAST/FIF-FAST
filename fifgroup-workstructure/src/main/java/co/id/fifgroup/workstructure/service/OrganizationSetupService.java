package co.id.fifgroup.workstructure.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.version.VersioningService;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.domain.OrganizationExample;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.workstructure.dto.HeadOfOrganizationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public interface OrganizationSetupService extends VersioningService<OrganizationDTO>{

	List<OrganizationDTO> findByInquiry(OrganizationDTO subject);
	List<OrganizationDTO> findByExample(OrganizationDTO subject);
	List<OrganizationDTO> findByExample(OrganizationDTO subject, int limit, int offset);
	Integer countByExample(OrganizationDTO subject);
	List<OrganizationDTO> findByLevelIdAndOrgName(Long levelId, String orgName, Long companyId, int limit, int offset);
	OrganizationDTO findOrgParentByHierarchyIdAndOrgId(Long hierarchyId, Long organizationId);
	List<Organization> findByExample(OrganizationExample example, int limit, int offset);
	List<Organization> findByExample(OrganizationExample example);
	List<OrganizationDTO> findOrganizationBySequenceLevel(OrganizationDTO example, int limit, int offset);
	Integer countOrganizationBySequenceLevel(OrganizationDTO example);
	OrganizationDTO findById(Long id);
	OrganizationDTO findById(Long id, Long companyId);
	OrganizationDTO findById(Long id, Long companyId, Date processDate);
	//add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
	OrganizationDTO findByIdWithoutProcessDate(Long id);
	//end add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
	Organization findOrganizationById(Long id);
	Organization findNameById(Long id);
//	List<Organization> findBranches();
	Integer countBranches(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, Date effectiveDate);
	Integer countByExample(OrganizationExample example);
	List<Organization> getBranchOrganization(Long organizationId);
	List<Organization> getOrganizationByBranch(Long branchId, String organizationCode, String organizationName, Date effectiveDate);
	List<Organization> getOrganizationByBranch(Long branchId, String organizationCode, String organizationName, Date effectiveDate, int limit, int offset);
	Integer countOrganizationByBranch(Long branchId, String organizationCode, String organizationName, Date effectiveDate);
	List<Organization> getActiveOrganizationByBranch(Long branchId, String organizationCode, String organizationName, Date effectiveDate, int limit, int offset);
	Integer countActiveOrganizationByBranch(Long branchId, String organizationCode, String organizationName, Date effectiveDate);
	List<Organization> getOrganizationByBranchAndSecurityFilter(Long companyId,Long branchId, String organizationCode, String organizationName, List<Long> inOrganizationId, List<Long> notInOrganizationId, Date effectiveOnDate, int limit, int offset);
	int countOrganizationByBranchAndSecurityFilter(Long companyId,Long branchId, String organizationCode, String organizationName, List<Long> inOrganizationId, List<Long> notInOrganizationId, Date effectiveOnDate);
	String getBranchCode(Long id);
	OrganizationDTO getBranch(Long id, Date date, Long companyId);
	OrganizationDTO findParentOrganization(Long id, Date date);
	List<Organization> findByHierarchyId(Long hierarchyId, String organizationCode, String organizationName, Long versionId, int limit, int offset);
	Integer countByHierarchyId(Long hierarchyId, String organizationCode, String organizationName, Long versionId);
	List<HeadOfOrganizationDTO> findHooHistoryByOrganizationId(Long organizationId);
	List<Organization> findBranches(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, int limit, int offset, Date effectiveDate);
	List<Organization> findBranches(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, Date effectiveDate);

	/*add by RIM - 4060510275024_Perbaikan Report HCMS Fase 1 - Time Service - By RIM*/
	List<Organization> findBranchesCommon(String organizationCode, String organizationName, int limit, int offset, Date effectiveDate);
	Integer countBranchesCommon(String organizationCode, String organizationName, Date effectiveDate);
	/*end add by RIM - 4060510275024_Perbaikan Report HCMS Fase 1 - Time Service - By RIM*/
	
	// start added by WLY, Phase 2 Training Admin
	List<OrganizationDTO> findBranchesByCompanyNameAndBranchName(Long companyIdInLocation, List<Long> inOrganizationId, List<Long> notInOrganizationId, String companyName, String organizationCode, String organizationName, int limit, int offset, Date effectiveDate, Long areaId);
	Integer countBranchesByCompanyNameAndBranchName(Long companyIdInLocation, List<Long> inOrganizationId, List<Long> notInOrganizationId, String companyName, String organizationCode, String organizationName, Date effectiveDate, Long areaId);
	List<OrganizationDTO> getOrganizationByBranchAndCompanyNameAndSecurityFilter(Long companyId,Long branchId, String companyName, String organizationCode, String organizationName, List<Long> inOrganizationId, List<Long> notInOrganizationId, Date effectiveOnDate, int limit, int offset);
	int countOrganizationByBranchAndCompanyNameAndSecurityFilter(Long companyId,Long branchId, String companyName, String organizationCode, String organizationName, List<Long> inOrganizationId, List<Long> notInOrganizationId, Date effectiveOnDate);
	// end added by WLY, Phase 2 Training Admin
	
	boolean isHeadOffice(Long organizationId);
	List<Organization> findOrganizationBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, Date effectiveDate);
	List<Organization> findOrganizationBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, Integer limit, Integer offset, Date effectiveDate);
	Integer countOrganizationBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, Date effectiveDate);
	Integer findSequenceOfOrganization(Long id);
	Organization findByCode(String organizationCode, Long companyId);
	List<OrganizationDTO> findOrganizationByHierarchy(Long companyId);
	List<Organization> findBranchBySecurityFilterAndExample(OrganizationDTO example, List<Long> inOrganizationId, List<Long> notInOrganizationId, int limit, int offset);
	Integer countBranchBySecurityFilterAndExample(OrganizationDTO example, List<Long> inOrganizationId, List<Long> notInOrganizationId);
	String getHeadOfficeGlCodeByCompanyId(Long companyId);
	Boolean isFutureExist(Long id);
	List<OrganizationDTO> findOrganizationByCompany(OrganizationDTO example, int limit, int offset);
	Integer countOrganizationByCompany(OrganizationDTO example);
	String getOrgGlCodeByOrgId(Long orgId, Long companyId);
	List<KeyValue> getGlCodes(String code, int limit, int offset);
	Integer countGlCodes(String code);
	List<KeyValue> getCostCenter(String code, int limit, int offset);
	Integer countCostCenter(String code);
	OrganizationDTO findByIdAndDate(Long id, Date effectiveDate);
	OrganizationDTO getHeadOffice();
	String getOrgGlCodeByBranchId(Long branchId, Long companyId);
	String findProviceCodeByExample(OrganizationDTO subject);
	List<OrganizationDTO> findBranchByDeptOwner(List<Long> versionId, List<Long> organizationId, String organizationCode, String organizationName, int limit, int offset);
	int countBranchByDeptOwner(List<Long> versionId, List<Long> organizationId, String organizationCode, String organizationName);
	List<Organization> findBranches(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, String kppCode, int limit, int offset, Date effectiveDate);
	Integer countBranches(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, String kppCode, Date effectiveDate);
	
	List<Organization> findBranchesBySecurityFilter(Long companyId, List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, int limit, int offset, Date effectiveDate);
	Integer countBranchesBySecurityFilter(Long companyId, List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, Date effectiveDate);
	
	Long getBranchOwnerByOrganizationId(Long organizationId);
	OrganizationDTO findLastVersionByCode(String organizationCode, Long companyId);
	Integer countByLevelIdAndOrgName(Long levelId, String organizationName, Long companyId);
	Organization findByPrimaryKey(Long organizationId);
	String getCostCenterByPersonId(Long personId, Long companyId);
	String getCostCenterByOrganizationId(Long organizationId);
	List<OrganizationDTO> findBranchesWithCompanyName(Long companyId, List<Long> inOrganizationId, List<Long> notInOrganizationId, Date effectiveDate);
	
	List<Organization> findOrganizationActive(Date effectiveOnDate, Long companyId);
	boolean isActiveOrganization(Long organizationId, Date effectiveOnDate);
	boolean isHeadOffice(Long organizationId, Date processDate);
	
	List<Organization> findOrganizationAvailableInMpp(Long companyId, Long branchId, String organizationCode, 
			String organizationName, List<Long> inOrganizationId, List<Long> notInOrganizationId,
			Date effectiveOnDate, int limit, int offset);
	
	Integer countOrganizationAvailableInMpp(Long companyId,	Long branchId, String organizationCode, 
			String organizationName, List<Long> inOrganizationId, List<Long> notInOrganizationId, 
			Date effectiveOnDate);
	
	public OrganizationDTO getDepartmentHead(Long organizationId);
	
	//add by jar ticket MPP list organization_id by person id
	//14040715181325_CR HCMS - MPP_JAR
	List<Integer> findOrganizationIdByCompanyAndHierTypeAndPersonId(Long companyId, String type, Long personId);
	
	public List<Organization> findBranchesByCompany(Long companyId, List<Long> inOrganizationId,
			List<Long> notInOrganizationId, String organizationCode,
			String organizationName, int limit, int offset, Date effectiveDate);
	
	public Integer countBranchesByCompany(Long companyId, List<Long> inOrganizationId,
			List<Long> notInOrganizationId, String organizationCode,
			String organizationName, Date effectiveDate);
	
	public List<Organization> getActiveOrganizationByBranchAndCompany(Long companyId, Long branchId,
			String organizationCode, String organizationName,
			Date effectiveDate, int limit, int offset);
	
	public Integer countActiveOrganizationByBranchAndCompany(Long companyId, Long branchId,
			String organizationCode, String organizationName,
			Date effectiveDate);
	List<Organization> findBranchesCompanyandBranchId(Long companyId,Long organizationId,List<Long> inOrganizationId,List<Long> notInOrganizationId,String organizationCode,String organizationName, int limit, int offset,Date effectiveDate);
	List<Organization> findBranchesCompany(Long companyId,List<Long> inOrganizationId,List<Long> notInOrganizationId,String organizationCode,String organizationName,int limit, int offset,Date effectiveDate);
	Integer countBranchesCompany(Long companyId,List<Long> inOrganizationId,List<Long> notInOrganizationId,String organizationCode,String organizationName,Date effectiveDate);
	Integer countBranchesCompanyandBranch(Long companyId,Long organizationId,List<Long> inOrganizationId,List<Long> notInOrganizationId,String organizationCode,String organizationName,Date effectiveDate);
	List<OrganizationDTO> findBranchesCompanyDTO(Long companyId,String code,String name,int limit, int offset,Date effectiveDate);
	Integer countBranchesCompanyDTO(Long companyId,String code,String name,Date effectiveDate);
	
	// Added for CAM, by WLY
	List<Organization> findBranchesWithAvailableMppByJob(List<Long> inOrganizationId, List<Long> notInOrganizationId, String branchCode, String branchName, Long companyId, Long jobId, Date effectiveDate, int limit, int offset);
	Integer countBranchesWithAvailableMppByJob(List<Long> inOrganizationId, List<Long> notInOrganizationId, String branchCode, String branchName, Long companyId, Long jobId, Date effectiveDate);
	List<Organization> findOrganizationsWithAvailableMppByJobAndBranch(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, Long companyId, Long branchId, Long jobId, Date effectiveDate, int limit, int offset);
	Integer countOrganizationsWithAvailableMppByJobAndBranch(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, Long companyId, Long branchId, Long jobId, Date effectiveDate);
	// End added for CAM, by WLY
}