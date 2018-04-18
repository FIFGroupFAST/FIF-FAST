package co.id.fifgroup.workstructure.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.IdGenerator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.version.AbstractVersioningService;
import co.id.fifgroup.workstructure.constant.HierarchyType;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.domain.OrganizationContactExample;
import co.id.fifgroup.workstructure.domain.OrganizationExample;
import co.id.fifgroup.workstructure.domain.OrganizationInfo;
import co.id.fifgroup.workstructure.domain.OrganizationInfoExample;
import co.id.fifgroup.workstructure.domain.OrganizationVersion;
import co.id.fifgroup.workstructure.domain.OrganizationVersionExample;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.workstructure.dao.OrganizationContactMapper;
import co.id.fifgroup.workstructure.dao.OrganizationInfoMapper;
import co.id.fifgroup.workstructure.dao.OrganizationMapper;
import co.id.fifgroup.workstructure.dao.OrganizationVersionMapper;
import co.id.fifgroup.workstructure.dto.HeadOfOrganizationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationContactDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.dto.OtherInfoDTO;
import co.id.fifgroup.workstructure.finder.OrganizationFinder;
import co.id.fifgroup.workstructure.validation.OrganizationValidator;

@Transactional
@Service
public class OrganizationSetupServiceImpl extends AbstractVersioningService<OrganizationDTO>
	implements OrganizationSetupService{
	
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private OrganizationVersionMapper versionMapper;
	@Autowired
	private OrganizationContactMapper contactMapper;
	@Autowired
	private OrganizationInfoMapper infoMapper;
	@Autowired
	private OrganizationFinder finder;
	@Autowired
	private OrganizationValidator validator;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional
	public void save(OrganizationDTO subject) throws Exception {
		super.save(subject);
	}

	@Override
	@Transactional
	public void delete(OrganizationDTO subject) {
		super.delete(subject);
	}

	@Override
	public void insertHeader(OrganizationDTO subject) {
		
//		Organization organization = modelMapper.map(subject, Organization.class);
		Organization organization = new Organization();
		organization.setCompanyId(subject.getCompanyId());
		organization.setCreatedBy(subject.getCreatedBy());
		organization.setCreationDate(subject.getCreationDate());
		organization.setId(subject.getId());
		organization.setLastUpdateDate(subject.getLastUpdateDate());
		organization.setLastUpdatedBy(subject.getLastUpdatedBy());
		organization.setOrganizationUuid(subject.getOrganizationUuid());
		organization.setOrganizationCode(subject.getCode());
		organization.setOrganizationName(subject.getName());
		organizationMapper.insert(organization);
		subject.setId(organization.getId());		
	}
	
	@Override
	public void updateHeader(OrganizationDTO subject) {
		
//		Organization organization = modelMapper.map(subject, Organization.class);
		Organization organization = new Organization();
		organization.setCompanyId(subject.getCompanyId());
		organization.setCreatedBy(subject.getCreatedBy());
		organization.setCreationDate(subject.getCreationDate());
		organization.setId(subject.getId());
		organization.setLastUpdateDate(subject.getLastUpdateDate());
		organization.setLastUpdatedBy(subject.getLastUpdatedBy());
		organization.setOrganizationUuid(subject.getOrganizationUuid());
		organization.setOrganizationCode(subject.getCode());
		organization.setOrganizationName(subject.getName());
		organizationMapper.updateByPrimaryKey(organization);
	}

	@Override
	public void insertDetail(OrganizationDTO subject) {
		
//		OrganizationVersion version = modelMapper.map(subject, OrganizationVersion.class);
		OrganizationVersion version = new OrganizationVersion();
		version.setAddress(subject.getAddress());
		version.setBranchCode(subject.getBranchCode());
		version.setCostCenterCode(subject.getCostCenterCode());
		version.setCreatedBy(subject.getCreatedBy());
		version.setCreationDate(subject.getCreationDate());
		version.setDateFrom(subject.getDateFrom());
		version.setDateTo(subject.getDateTo());
		version.setDescription(subject.getDescription());
		version.setIsHeadOffice(subject.getIsHeadOffice());
		version.setKecamatanCode(subject.getKecamatanCode());
		version.setKelurahanCode(subject.getKelurahanCode());
		version.setKppCode(subject.getKppCode());
		version.setLastUpdateDate(subject.getLastUpdateDate());
		version.setLastUpdatedBy(subject.getLastUpdatedBy());
		version.setLevelCode(subject.getLevelCode());
		version.setNpwp(subject.getNpwp());
		version.setOrganizationSpvId(subject.getOrganizationSpvId());
		version.setPicId(subject.getPicId());
		version.setSubZipCode(subject.getSubZipCode());
		version.setVersionNumber(subject.getVersionNumber());
		version.setWorkingScheduleId(subject.getWorkingScheduleId());
		version.setZipCode(subject.getZipCode());
		
		version.setId(subject.getId());
		version.setLocationId(subject.getLocation().getId());
		version.setHeadOfOrganization(subject.getOrganizationHeadId());
		versionMapper.insert(version);
		subject.setVersionId(version.getVersionId());
		if (null != subject.getContacts()) {
			for (OrganizationContactDTO contactDto : subject.getContacts()) {
//				OrganizationContact contact =  modelMapper.map(contactDto, OrganizationContact.class);
				contactDto.setVersionId(subject.getVersionId());
				contactDto.setCreatedBy(subject.getCreatedBy());
				contactDto.setCreationDate(subject.getCreationDate());
				contactDto.setLastUpdatedBy(subject.getLastUpdatedBy());
				contactDto.setLastUpdateDate(subject.getLastUpdateDate());
				contactMapper.insert(contactDto);
			}
		}
		if(subject.getOrganizationInfos() != null) {
			for(OtherInfoDTO infoDto : subject.getOrganizationInfos()) {
//				OrganizationInfo info = modelMapper.map(infoDto, OrganizationInfo.class);
				OrganizationInfo info = new OrganizationInfo();
				info.setInfoId(infoDto.getInfoId());
				info.setInfoValue(infoDto.getInfoValue());
				info.setVersionId(subject.getVersionId());
				info.setCreatedBy(subject.getCreatedBy());
				info.setCreationDate(subject.getCreationDate());
				info.setLastUpdatedBy(subject.getLastUpdatedBy());
				info.setLastUpdateDate(subject.getLastUpdateDate());
				infoMapper.insert(info);
			}
		}
	}

	@Override
	public void deleteHeader(OrganizationDTO subject) {
		organizationMapper.deleteByPrimaryKey(subject.getId());
	}

	@Override
	public void deleteDetail(OrganizationDTO subject) {
		
		// delete all contacts.
		OrganizationContactExample contactExample = new OrganizationContactExample();
		contactExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		contactMapper.deleteByExample(contactExample);
		
		//delete all infos
		OrganizationInfoExample infoExample = new OrganizationInfoExample();
		infoExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		infoMapper.deleteByExample(infoExample);
		
		//delete version.
		versionMapper.deleteByPrimaryKey(subject.getVersionId());
		
	}
	
	@Transactional(readOnly = true)
	private OrganizationVersion convertDomain(OrganizationDTO subject){
		OrganizationVersion version = new OrganizationVersion();
		version.setAddress(subject.getAddress());
		version.setBranchCode(subject.getBranchCode());
		version.setCostCenterCode(subject.getCostCenterCode());
		version.setCreatedBy(subject.getCreatedBy());
		version.setCreationDate(subject.getCreationDate());
		version.setDateFrom(subject.getDateFrom());
		version.setDateTo(subject.getDateTo());
		version.setDescription(subject.getDescription());
		version.setHeadOfOrganization(subject.getOrganizationHeadId());
		version.setId(subject.getId());
		version.setIsHeadOffice(subject.getIsHeadOffice());
		version.setKecamatanCode(subject.getKecamatanCode());
		version.setKelurahanCode(subject.getKelurahanCode());
		version.setKppCode(subject.getKppCode());
		version.setLastUpdateDate(subject.getLastUpdateDate());
		version.setLastUpdatedBy(subject.getLastUpdatedBy());
		version.setLevelCode(subject.getLevelCode());
		version.setLocationId(subject.getLocation().getId());
		version.setNpwp(subject.getNpwp());
		version.setOrganizationSpvId(subject.getOrganizationSpvId());
		version.setPicId(subject.getPicId());
		version.setSubZipCode(subject.getSubZipCode());
		version.setVersionId(subject.getVersionId());
		version.setVersionNumber(subject.getVersionNumber());
		version.setWorkingScheduleId(subject.getWorkingScheduleId());
		version.setZipCode(subject.getZipCode());
		return version;
	}

	@Override
	public void updateDetail(OrganizationDTO subject) {
		
		OrganizationVersion version = convertDomain(subject);
		version.setLocationId(subject.getLocation().getId());
		version.setHeadOfOrganization(subject.getOrganizationHeadId());
		
		// delete all contacts.
		OrganizationContactExample contactExample = new OrganizationContactExample();
		contactExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		contactMapper.deleteByExample(contactExample);
		
		// insert new contacts.
		if (null != subject.getContacts()) {
			for (OrganizationContactDTO contactDto : subject.getContacts()) {
//				OrganizationContact contact =  modelMapper.map(contactDto, OrganizationContact.class);
				contactDto.setVersionId(subject.getVersionId());
				contactDto.setCreatedBy(subject.getCreatedBy());
				contactDto.setCreationDate(subject.getCreationDate());
				contactDto.setLastUpdatedBy(subject.getLastUpdatedBy());
				contactDto.setLastUpdateDate(subject.getLastUpdateDate());
				contactMapper.insert(contactDto);
			}
		}
		
		//delete all infos
		OrganizationInfoExample infoExample = new OrganizationInfoExample();
		infoExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		infoMapper.deleteByExample(infoExample);
		
		//insert new infos
		if(subject.getOrganizationInfos() != null) {
			for(OtherInfoDTO infoDto : subject.getOrganizationInfos()) {
				if(infoDto.getInfoId() != null) {
//					OrganizationInfo info = modelMapper.map(infoDto, OrganizationInfo.class);
					OrganizationInfo info = new OrganizationInfo();
					info.setInfoId(infoDto.getInfoId());
					info.setInfoValue(infoDto.getInfoValue());
					info.setVersionId(subject.getVersionId());
					info.setCreatedBy(subject.getCreatedBy());
					info.setCreationDate(subject.getCreationDate());
					info.setLastUpdatedBy(subject.getLastUpdatedBy());
					info.setLastUpdateDate(subject.getLastUpdateDate());
					infoMapper.insert(info);
				}
			}
		}
		
		versionMapper.updateByPrimaryKey(version);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getNextHeaderId() {
		return idGenerator.getNextHeaderId("WOS_ORGANIZATIONS_S");
	}

	@Override
	@Transactional(readOnly = true)
	public OrganizationDTO findByIdAndVersionNumber(Long id,
			Integer versionNumber) {
		return finder.findByIdAndVersionNumber(id, versionNumber);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Integer> findVersionsById(Long id) {
		return finder.findVersionsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public void validate(OrganizationDTO subject) throws ValidationException {
		validator.validate(subject);		
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrganizationDTO> findByExample(OrganizationDTO subject) {
		return finder.findByExample(subject);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrganizationDTO> findByLevelIdAndOrgName(Long levelId, String orgName, Long companyId, int limit, int offset) {
		return finder.findByLevelIdAndOrgName(levelId, orgName, companyId, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public OrganizationDTO findOrgParentByHierarchyIdAndOrgId(Long hierarchyId, Long organizationId) {
		OrganizationDTO org = finder.findOrgParentByHierarchyIdAndOrgId(hierarchyId, organizationId);
			return org;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> findByExample(OrganizationExample example, int limit, int offset) {
		return organizationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Organization> findByExample(OrganizationExample example) {
		return organizationMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrganizationDTO> findOrganizationBySequenceLevel(OrganizationDTO example, int limit, int offset) {
		return finder.findOrganizationBySequenceLevel(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public OrganizationDTO findById(Long id) {
		return finder.findById(id, null, new Date());
	}

	@Override
	@Transactional(readOnly = true)
	public Organization findNameById(Long id) {
		return finder.findNameById(id);
	}

//	@Override
//	public List<Organization> findBranches() {
//		List<Organization> orgs = finder.findBranches(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), null, null, null, null, null, null);
//		Organization headOffice = new Organization();
//		headOffice.setId(-1L);
//		headOffice.setOrganizationCode("Head Office");
//		headOffice.setOrganizationName("Head Office");
//		orgs.add(headOffice);
//		
//		return orgs;
//	}
	
//	@Override
//	public Integer countBranches(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, Date effeDate) {
//		return finder.countBranches(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), inOrganizationId, notInOrganizationId, organizationCode, organizationName, null) + 1;
//	}

	@Override
	@Transactional(readOnly = true)
	public Integer countByExample(OrganizationExample example) {
		return organizationMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrganizationDTO> findByInquiry(OrganizationDTO subject) {
		return finder.findByInquiry(subject);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isHaveFuture(Long id) {
		return finder.isHaveFuture(id) == 1;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> getBranchOrganization(Long organizationId) {
		return finder.findBranchOrganization(organizationId, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	@Transactional(readOnly = true)
	public String getBranchCode(Long id) {
		return finder.findBranchCode(id, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	@Transactional(readOnly = true)
	public OrganizationDTO getBranch(Long id, Date date, Long companyId) {
		Long branchId = finder.getBranchId(id, companyId);
		if(branchId != null) {
			if(branchId.equals(-1L)) {
				return getHeadOffice();
			} else {
				return finder.findBranch(branchId, date != null ? date : new Date());
			}
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public OrganizationDTO findParentOrganization(Long id, Date date) {
		return finder.findParentOrganization(id, date != null ? date : new Date(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), HierarchyType.STRUCTURAL.toString());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> getOrganizationByBranch(Long branchId, String organizationCode, String organizationName, Date effectiveDate) {
		return finder.findOrganizationByBranch(branchId, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), organizationCode, organizationName, effectiveDate);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Organization> getOrganizationByBranch(Long branchId, String organizationCode,
			String organizationName, Date effectiveDate, int limit, int offset) {
		return finder.findOrganizationByBranch(branchId, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), organizationCode, organizationName, effectiveDate, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countOrganizationByBranch(Long branchId, String organizationCode, String organizationName, Date effectiveDate) {
		return finder.countOrganizationByBranch(branchId, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), organizationCode, organizationName, effectiveDate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> findByHierarchyId(Long hierarchyId, String organizationCode,
			String organizationName, Long versionId, int limit, int offset) {
		return finder.findByHierarchyId(hierarchyId, organizationCode, organizationName, versionId, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countByHierarchyId(Long hierarchyId, String organizationCode, String organizationName, Long versionId) {
		return finder.countByHierarchyId(hierarchyId, organizationCode, organizationName, versionId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<HeadOfOrganizationDTO> findHooHistoryByOrganizationId(
			Long organizationId) {
		return finder.findHooHistoryByOrganizationId(organizationId);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isHeadOffice(Long organizationId) {
		return finder.isHeadOffice(organizationId, new Date()) == 1;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Organization> findOrganizationBySecurityFilter(
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String organizationCode, String organizationName, Date effectiveDate) {
		return finder.findOrganizationBySecurityFilter(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), inOrganizationId, notInOrganizationId, organizationCode, organizationName, effectiveDate);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countOrganizationBySecurityFilter(
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String organizationCode, String organizationName, Date effectiveDate) {
		return finder.countOrganizationBySecurityFilter(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), inOrganizationId, notInOrganizationId, organizationCode, organizationName, effectiveDate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> findOrganizationBySecurityFilter(
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String organizationCode, String organizationName, Integer limit,
			Integer offset, Date effectiveDate) {
		return finder.findOrganizationBySecurityFilter(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), inOrganizationId, notInOrganizationId, organizationCode, organizationName, new RowBounds(offset, limit), effectiveDate);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer findSequenceOfOrganization(Long id) {
		return finder.findSequenceOfOrganization(id, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	@Transactional(readOnly = true)
	public Organization findByCode(String organizationCode, Long companyId) {
		return finder.findByCode(organizationCode, companyId);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countOrganizationBySequenceLevel(OrganizationDTO example) {
		return finder.countOrganizationBySequenceLevel(example);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrganizationDTO> findByExample(OrganizationDTO subject,
			int limit, int offset) {
		return finder.findByExample(subject, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countByExample(OrganizationDTO subject) {
		return finder.countByExample(subject);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> findBranches(List<Long> inOrganizationId,
			List<Long> notInOrganizationId, String organizationCode,
			String organizationName, Date effectiveDate) {
		List<Organization> orgs = finder.findBranches(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), inOrganizationId, notInOrganizationId, organizationCode, organizationName, effectiveDate);
						
		
		return orgs;
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrganizationDTO> findOrganizationByHierarchy(Long companyId) {
		return finder.findOrganizationByHierarchy(companyId);
	}

//	@Override
//	public List<OrganizationDTO> findOrganizationByCompany(OrganizationDTO example, int limit, int offset) {
//		return finder.findOrganizationsByCompany(example, new RowBounds(offset, limit));
//	}
//
//	@Override
//	public Integer countOrganizationByCompany(OrganizationDTO example) {
//		return finder.countOrganizationByCompany(example);
//	}

	@Override
	@Transactional(readOnly = true)
	public Organization findOrganizationById(Long id) {
		if(id.equals(-1L)) {
			OrganizationDTO orgDto = getHeadOffice();
			if(orgDto != null) {
				//Organization org = modelMapper.map(orgDto, Organization.class);
				Organization org = new Organization();
				org.setId(orgDto.getId());
				org.setOrganizationCode(orgDto.getCode());
				org.setOrganizationName(orgDto.getName());
				return org;
			}
			return null;
		} else
			return organizationMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = true)
	public OrganizationDTO findById(Long id, Long companyId) {
		return finder.findById(id, companyId, new Date());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> getOrganizationByBranchAndSecurityFilter(Long companyId,
			Long branchId, String organizationCode, String organizationName,
			List<Long> inOrganizationId, List<Long> notInOrganizationId, Date effectiveOnDate,
			int limit, int offset) {
		
		return finder.findOrganizationByBranchAndSecurityFilter(companyId,branchId, organizationCode, organizationName, inOrganizationId, notInOrganizationId, effectiveOnDate, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int countOrganizationByBranchAndSecurityFilter(Long companyId,Long branchId,
			String organizationCode, String organizationName,
			List<Long> inOrganizationId, List<Long> notInOrganizationId, Date effectiveOnDate) {
		return finder.countOrganizationByBranchAndSecurityFilter(companyId,branchId, organizationCode, organizationName, inOrganizationId, notInOrganizationId, effectiveOnDate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> findBranchBySecurityFilterAndExample(
			OrganizationDTO example, List<Long> inOrganizationId,
			List<Long> notInOrganizationId, int limit, int offset) {
		return finder.findBranchBySecurityFilterAndExample(example, inOrganizationId, notInOrganizationId, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countBranchBySecurityFilterAndExample(
			OrganizationDTO example, List<Long> inOrganizationId,
			List<Long> notInOrganizationId) {
		return finder.countBranchBySecurityFilterAndExample(example, inOrganizationId, notInOrganizationId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> getActiveOrganizationByBranch(Long branchId,
			String organizationCode, String organizationName,
			Date effectiveDate, int limit, int offset) {
		return finder.findOrganizationByBranch(branchId, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), organizationCode, organizationName, effectiveDate, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countActiveOrganizationByBranch(Long branchId,
			String organizationCode, String organizationName, Date effectiveDate) {
		return finder.countOrganizationByBranch(branchId, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(),
				organizationCode, organizationName, effectiveDate);
	}

	@Override
	@Transactional(readOnly = true)
	public String getHeadOfficeGlCodeByCompanyId(Long companyId) {
		return finder.getHeadOfficeGlCodeByCompanyId(companyId);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean isFutureExist(Long id) {
		return finder.isFutureExist(id) > 0;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countBranches(List<Long> inOrganizationId,
			List<Long> notInOrganizationId, String organizationCode,
			String organizationName, Date effectiveDate) {
		return finder.countBranches(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), inOrganizationId, notInOrganizationId, organizationCode, organizationName, effectiveDate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> findBranches(List<Long> inOrganizationId,
			List<Long> notInOrganizationId, String organizationCode,
			String organizationName, int limit, int offset, Date effectiveDate) {
		List<Organization> orgs = finder.findBranches(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), inOrganizationId, notInOrganizationId, organizationCode, organizationName, new RowBounds(offset, limit), effectiveDate);		
		return orgs;
	}

	
	
	@Override
	@Transactional(readOnly = true)
	public List<OrganizationDTO> findOrganizationByCompany(
			OrganizationDTO example, int limit, int offset) {
		return finder.findOrganizationsByCompany(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countOrganizationByCompany(OrganizationDTO example) {
		return finder.countOrganizationByCompany(example);
	}

	@Override
	@Transactional(readOnly = true)
	public String getOrgGlCodeByOrgId(Long orgId, Long companyId) {
		if (orgId.equals(-1L) || isHeadOffice(orgId))
			return getHeadOffice().getBranchCode();
		return finder.getOrgGlCodeByOrgId(orgId, companyId, DateUtils.truncate(new Date(), Calendar.DATE));
	}

	@Override
	@Transactional(readOnly = true)
	public List<KeyValue> getGlCodes(String code, int limit, int offset) {
		return finder.getGlCodes(code, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public List<KeyValue> getCostCenter(String code, int limit, int offset) {
		return finder.getCostCenter(code, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countGlCodes(String code) {
		return finder.countGlCodes(code);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countCostCenter(String code) {
		return finder.countCostCenter(code);
	}

	@Override
	@Transactional(readOnly = true)
	public OrganizationDTO findByIdAndDate(Long id, Date effectiveDate) {
		return finder.findByIdAndDate(id, effectiveDate);
	}

	@Override
	@Transactional(readOnly = true)
	public OrganizationDTO getHeadOffice() {
		OrganizationDTO org = new OrganizationDTO();
		org.setId(-1L);
		org.setCode("HEADOFFICE");
		org.setName("Head Office");
		org.setBranchCode("00001");
		org.setIsHeadOffice(true);
		return org;
	}

	@Override
	@Transactional(readOnly = true)
	public String getOrgGlCodeByBranchId(Long branchId, Long companyId) {
		return finder.getOrgGlCodeByBranchId(branchId, companyId);
	}

	@Override
	@Transactional(readOnly = true)
	public String findProviceCodeByExample(OrganizationDTO subject) {
		return finder.findProviceCodeByExample(subject);
	}

	@Override
	@Transactional(readOnly=true)
	public List<OrganizationDTO> findBranchByDeptOwner(List<Long> versionId,
			List<Long> organizationId, String organizationCode,
			String organizationName, int limit, int offset) {
		return finder.findBranchByDeptOwner(versionId, organizationId, organizationCode, organizationName, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int countBranchByDeptOwner(List<Long> versionId,
			List<Long> organizationId, String organizationCode,
			String organizationName) {
		return finder.countBranchByDeptOwner(versionId, organizationId, organizationCode, organizationName);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Organization> findBranches(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, String kppCode, int limit, int offset, Date effectiveDate) {
		return finder.findBranchesWithKpp(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), inOrganizationId, notInOrganizationId, organizationCode, organizationName, kppCode, new RowBounds(offset, limit), effectiveDate);
	}

	@Override
	@Transactional(readOnly=true)
	public Integer countBranches(List<Long> inOrganizationId, List<Long> notInOrganizationId, String organizationCode, String organizationName, String kppCode, Date effectiveDate) {
		return finder.countBranchesWithKpp(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), inOrganizationId, notInOrganizationId, organizationCode, organizationName, kppCode, effectiveDate);
	}

	@Override
	public List<Organization> findBranchesBySecurityFilter(Long companyId,
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String organizationCode, String organizationName, int limit,
			int offset, Date effectiveDate) {
		return finder.findBranchesBySecurityFilter(companyId, inOrganizationId, notInOrganizationId, organizationCode, organizationName, effectiveDate, new RowBounds(offset, limit));
	}

	@Override
	public Integer countBranchesBySecurityFilter(Long companyId, List<Long> inOrganizationId,
			List<Long> notInOrganizationId, String organizationCode,
			String organizationName, Date effectiveDate) {
		return finder.countBranchesBySecurityFilter(companyId, inOrganizationId, notInOrganizationId, organizationCode, organizationName, effectiveDate);
	}

	@Override
	public Long getBranchOwnerByOrganizationId(Long organizationId) {
		return finder.getBranchOwnerByOrganizationId(organizationId);
	}

	@Override
	public OrganizationDTO findLastVersionByCode(String organizationCode,
			Long companyId) {
		return finder.findLastVersionByCode(organizationCode, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	public Integer countByLevelIdAndOrgName(Long levelId,
			String organizationName, Long companyId) {
		return finder.countByLevelIdAndOrgName(levelId, organizationName, companyId);
	}

	@Override
	public Organization findByPrimaryKey(Long organizationId) {
		return organizationMapper.selectByPrimaryKey(organizationId);
	}

	@Override
	public String getCostCenterByPersonId(Long personId, Long companyId) {
		return finder.getCostCenterByPersonId(personId, companyId);
	}

	@Override
	public String getCostCenterByOrganizationId(Long organizationId) {
		return finder.getCostCenterByOrganizationId(organizationId);
	}

	@Override
	public List<OrganizationDTO> findBranchesWithCompanyName(Long companyId, List<Long> inOrganizationId,
			List<Long> notInOrganizationId, Date effectiveDate) {
		return finder.findBranchesWithCompanyName(companyId, inOrganizationId, notInOrganizationId, effectiveDate);
	}

	@Override
	public OrganizationDTO findById(Long id, Long companyId, Date processDate) {
		return finder.findById(id, companyId, processDate);
	}

	@Override
	public List<Organization> findOrganizationActive(Date effectiveOnDate,
			Long companyId) {
		return finder.findOrganizationActive(effectiveOnDate, companyId);
	}

	@Override
	public boolean isActiveOrganization(Long organizationId,
			Date effectiveOnDate) {
		OrganizationVersionExample example = new OrganizationVersionExample();
		example.createCriteria().andIdEqualTo(organizationId)
			.andDateFromLessThanOrEqualTo(effectiveOnDate)
			.andDateToGreaterThanOrEqualTo(effectiveOnDate);
		return versionMapper.countByExample(example) > 0;
	}

	@Override
	public boolean isHeadOffice(Long organizationId, Date processDate) {
		return finder.isHeadOffice(organizationId, processDate) == 1;
	}

	//14040715181325_CR HCMS - MPP_JAR
	@Override
	public List<Organization> findOrganizationAvailableInMpp(Long companyId,
			Long branchId, String organizationCode, String organizationName,
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			Date effectiveOnDate, int limit, int offset) {
		return finder.findOrganizationAvailableInMpp(companyId, branchId, organizationCode, 
				organizationName, inOrganizationId, notInOrganizationId, effectiveOnDate, new RowBounds(offset, limit));
	}

	@Override
	public Integer countOrganizationAvailableInMpp(Long companyId,
			Long branchId, String organizationCode, String organizationName,
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			Date effectiveOnDate) {
		return finder.countOrganizationAvailableInMpp(companyId, branchId, organizationCode, 
				organizationName, inOrganizationId, notInOrganizationId, effectiveOnDate);
	}

	@Override
	public OrganizationDTO getDepartmentHead(Long organizationId) {
		return finder.getDepartmentHead(organizationId);
	}

	@Override
	public List<Integer> findOrganizationIdByCompanyAndHierTypeAndPersonId(Long companyId, String type, Long personId) {
		return finder.findOrganizationIdByCompanyAndHierTypeAndPersonId(companyId, type, personId);
	}
	
	//End Add 14040715181325_CR HCMS - MPP_JAR

	
	/*Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
	@Override
	public List<Organization> findBranchesCommon(String organizationCode,
			String organizationName, int limit, int offset, Date effectiveDate) {
		return finder.findBranchesCommon(organizationCode, organizationName, effectiveDate, new RowBounds(offset, limit));
		
	}

	@Override
	public Integer countBranchesCommon(String organizationCode,
			String organizationName, Date effectiveDate) {
		// TODO Auto-generated method stub
		return finder.countBranchesCommon(organizationCode, organizationName, effectiveDate);
	}
	/*end Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/

	
	@Override
	@Transactional(readOnly = true)
	public Integer countBranchesByCompany(Long companyId, List<Long> inOrganizationId,
			List<Long> notInOrganizationId, String organizationCode,
			String organizationName, Date effectiveDate) {
		return finder.countBranches(companyId, inOrganizationId, notInOrganizationId, organizationCode, organizationName, effectiveDate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> findBranchesByCompany(Long companyId, List<Long> inOrganizationId,
			List<Long> notInOrganizationId, String organizationCode,
			String organizationName, int limit, int offset, Date effectiveDate) {
		List<Organization> orgs = finder.findBranches(companyId, inOrganizationId, notInOrganizationId, organizationCode, organizationName, new RowBounds(offset, limit), effectiveDate);		
		return orgs;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Organization> getActiveOrganizationByBranchAndCompany(Long companyId, Long branchId,
			String organizationCode, String organizationName,
			Date effectiveDate, int limit, int offset) {
		return finder.findOrganizationByBranch(branchId, companyId, organizationCode, organizationName, effectiveDate, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countActiveOrganizationByBranchAndCompany(Long companyId, Long branchId,
			String organizationCode, String organizationName, Date effectiveDate) {
		return finder.countOrganizationByBranch(branchId, companyId,
				organizationCode, organizationName, effectiveDate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> findBranchesCompanyandBranchId(Long companyId,
			Long organizationId, List<Long> inOrganizationId,
			List<Long> notInOrganizationId, String organizationCode,
			String organizationName, int limit, int offset, Date effectiveDate) {
		List<Organization> orgs = finder.findBranchesCompanyandBranchId(companyId, organizationId, inOrganizationId, notInOrganizationId, organizationCode, organizationName, new RowBounds(offset, limit), effectiveDate);
		return orgs;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Organization> findBranchesCompany(Long companyId,
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String organizationCode, String organizationName, int limit,
			int offset, Date effectiveDate) {
		List<Organization> orgs = finder.findBranchesCompany(companyId, inOrganizationId, notInOrganizationId, organizationCode, organizationName, new RowBounds(offset, limit), effectiveDate);
		return orgs;
	}

	@Override
	public Integer countBranchesCompany(Long companyId,
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String organizationCode, String organizationName, Date effectiveDate) {
		return finder.countBranchesCompany(companyId, inOrganizationId, notInOrganizationId, organizationCode, organizationName, effectiveDate);
	}

	@Override
	public Integer countBranchesCompanyandBranch(Long companyId,
			Long organizationId, List<Long> inOrganizationId,
			List<Long> notInOrganizationId, String organizationCode,
			String organizationName, Date effectiveDate) {
		return finder.countBranchesCompanyandBranch(companyId, organizationId, inOrganizationId, notInOrganizationId, organizationCode, organizationName, effectiveDate);
	}

	@Override
	public List<OrganizationDTO> findBranchesCompanyDTO(Long companyId,
			String code, String name, int limit, int offset, Date effectiveDate) {
		return finder.findBranchesCompanyDTO(companyId,  code, name, new RowBounds(offset, limit), effectiveDate);
	}

	@Override
	public Integer countBranchesCompanyDTO(Long companyId,
			String code, String name, Date effectiveDate) {
		return finder.countBranchesCompanyDTO(companyId, code, name, effectiveDate);
	}


	//add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
	@Override
	public OrganizationDTO findByIdWithoutProcessDate(Long id) {
		return finder.findByIdWithoutDate(id);
	}
	//end add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
	
	// start added for CAM, by WLY
	@Override
	public List<Organization> findBranchesWithAvailableMppByJob(
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String branchCode, String branchName, Long companyId, Long jobId,
			Date effectiveDate, int limit, int offset) {
		return finder.findBranchesWithAvailableMppByJob(inOrganizationId, 
				notInOrganizationId, branchCode, branchName, companyId, 
				jobId, effectiveDate, new RowBounds(offset, limit));
	}

	@Override
	public Integer countBranchesWithAvailableMppByJob(
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String branchCode, String branchName, Long companyId, Long jobId,
			Date effectiveDate) {
		return finder.countBranchesWithAvailableMppByJob(inOrganizationId, 
				notInOrganizationId, branchCode, branchName, companyId, 
				jobId, effectiveDate);
				
	}

	@Override
	public List<Organization> findOrganizationsWithAvailableMppByJobAndBranch(
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String organizationCode, String organizationName, Long companyId,
			Long branchId, Long jobId, Date effectiveDate, int limit, int offset) {
		return finder.findOrganizationsWithAvailableMppByJobAndBranch(inOrganizationId, 
				notInOrganizationId, organizationCode, organizationName, companyId, 
				branchId, jobId, effectiveDate, new RowBounds(offset, limit));
	}

	@Override
	public Integer countOrganizationsWithAvailableMppByJobAndBranch(
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String organizationCode, String organizationName, Long companyId,
			Long branchId, Long jobId, Date effectiveDate) {
		return finder.countOrganizationsWithAvailableMppByJobAndBranch(inOrganizationId, 
				notInOrganizationId, organizationCode, organizationName, companyId, 
				branchId, jobId, effectiveDate);
	}
	// end added for CAM by WLY
	
	// start added by WLY, Phase 2 Training Admin
	@Override
	public List<OrganizationDTO> findBranchesByCompanyNameAndBranchName(Long companyIdInLocation,
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String companyName, String organizationCode, String organizationName, int limit,
			int offset, Date effectiveDate, Long areaId) {
		List<OrganizationDTO> orgs = finder.findBranchesByCompanyNameAndBranchName(companyIdInLocation, inOrganizationId, 
				notInOrganizationId, companyName, organizationCode, organizationName, 
				new RowBounds(offset, limit), effectiveDate, areaId);		
		return orgs;
	}

	@Override
	public Integer countBranchesByCompanyNameAndBranchName(Long companyIdInLocation,
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			String companyName, String organizationCode, String organizationName, Date effectiveDate, Long areaId) {
		return finder.countBranchesByCompanyNameAndBranchName(companyIdInLocation, inOrganizationId, 
				notInOrganizationId, companyName, organizationCode, organizationName, effectiveDate, areaId);	
	}

	@Override
	public List<OrganizationDTO> getOrganizationByBranchAndCompanyNameAndSecurityFilter(
			Long companyId, Long branchId, String companyName,
			String organizationCode, String organizationName,
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			Date effectiveOnDate, int limit, int offset) {
		return finder.getOrganizationByBranchAndCompanyNameAndSecurityFilter(
				companyId, branchId, companyName, organizationCode, organizationName,
				inOrganizationId, notInOrganizationId, effectiveOnDate, new RowBounds(offset, limit));
	}

	@Override
	public int countOrganizationByBranchAndCompanyNameAndSecurityFilter(
			Long companyId, Long branchId, String companyName,
			String organizationCode, String organizationName,
			List<Long> inOrganizationId, List<Long> notInOrganizationId,
			Date effectiveOnDate) {
		return finder.countOrganizationByBranchAndCompanyNameAndSecurityFilter(
				companyId, branchId, companyName, organizationCode, organizationName,
				inOrganizationId, notInOrganizationId, effectiveOnDate);
	}
	
	// end added by WLY, Phase 2 Training Admin
}
