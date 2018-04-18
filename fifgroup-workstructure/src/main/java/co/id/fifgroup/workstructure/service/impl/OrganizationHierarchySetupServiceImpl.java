
package co.id.fifgroup.workstructure.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.IdGenerator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.version.AbstractVersioningService;
import co.id.fifgroup.workstructure.domain.HierarchyElement;
import co.id.fifgroup.workstructure.domain.OrgHierarchyElement;
import co.id.fifgroup.workstructure.domain.OrgHierarchyElementExample;
import co.id.fifgroup.workstructure.domain.OrgHierarchyVersion;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchy;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchyExample;
import co.id.fifgroup.workstructure.domain.OrganizationNode;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.workstructure.dao.OrgHierarchyElementMapper;
import co.id.fifgroup.workstructure.dao.OrgHierarchyVersionMapper;
import co.id.fifgroup.workstructure.dao.OrganizationHierarchyMapper;
import co.id.fifgroup.workstructure.dto.OrgHierElementDTO;
import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.finder.HierarchyElementFinder;
import co.id.fifgroup.workstructure.finder.OrganizationHierarchyFinder;
import co.id.fifgroup.workstructure.validation.OrganizationHierarchyValidator;

@Transactional
@Service
public class OrganizationHierarchySetupServiceImpl extends AbstractVersioningService<OrgHierarchyDTO> implements OrganizationHierarchySetupService {

	private Logger logger = LoggerFactory.getLogger(OrganizationHierarchySetupServiceImpl.class);
	
	private static final Map<String, OrganizationNode> cachedOrganizationNode = Collections.synchronizedMap(new HashMap<String, OrganizationNode>());
	
	
	@Autowired
	private OrganizationHierarchyMapper hierMapper;
	@Autowired
	private OrgHierarchyVersionMapper versionMapper;
	@Autowired
	private OrgHierarchyElementMapper elementMapper;
	@Autowired
	private OrganizationHierarchyFinder hierFinder;
	@Autowired
	private HierarchyElementFinder hierarchyElementFinder;
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private OrganizationHierarchyValidator organizationHierarchyValidator;
	@Autowired
	private OrganizationSetupService organizationSetupServiceImpl;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Transactional(readOnly=true)
	private OrganizationNode getOrganizationNode(Long hierarchyId, Long versionId, Date date, Boolean isActiveOrg) {
		SimpleDateFormat sdf = new SimpleDateFormat(GlobalVariable.getDateFormat());
		String stringDate = "";
		if (date != null)
			stringDate = sdf.format(date);
		if (cachedOrganizationNode.containsKey(hierarchyId + "." + versionId + "." + stringDate)){
			logger.debug("OrganizationNode retrieved from cache: " + hierarchyId + "." + versionId + "." + stringDate);
			return cachedOrganizationNode.get(hierarchyId + "." + versionId + "." + stringDate);
		}
		List<HierarchyElement> elements = hierarchyElementFinder.findByHierarchyId(hierarchyId, versionId, date, isActiveOrg);
		if(elements.size() != 0) {
			for(HierarchyElement element : elements) {
				if(element.getParentId().equals(element.getSelf().getId())) {
					OrganizationNode organizationNode = getRoot(element.getParentId());
					logger.info("parent = " + element.getParentId());
					logger.info("data = " + organizationNode.getData().getId());
					generateTree(organizationNode, elements);
					logger.debug(organizationNode.toString());
					logger.debug("OrganizationNode cached to " + hierarchyId + "." + versionId + "." + stringDate);
					cachedOrganizationNode.put(hierarchyId + "." + versionId + "." + stringDate, organizationNode);
					return organizationNode;
				}
			}
		}
		return null;
	}
	
	private OrganizationNode getRoot(Long rootId) {
		OrganizationDTO orgRoot = organizationSetupServiceImpl.findById(rootId);
		OrganizationNode root = new OrganizationNode();
		root.setParent(null);
		root.setData(orgRoot);
		
		return root;
	}
	
	private void generateTree(OrganizationNode parent, List<HierarchyElement> hierarchyElements) {
		if (null != parent ) {
			for (HierarchyElement hierarchyElement : hierarchyElements) {
				if(parent.getData().getId().equals(hierarchyElement.getParentId()) && !parent.getData().getId().equals(hierarchyElement.getSelf().getId())) {
					OrganizationNode organizationNode = new OrganizationNode();
					organizationNode.setParent(parent);
					organizationNode.setData(hierarchyElement.getSelf());
					parent.getChildren().add(organizationNode);
				}
			}
			for (OrganizationNode child : parent.getChildren()) {
				generateTree(child, hierarchyElements);
			}
		}
	}
	
	private OrganizationNode getOwnOrganizationNode(Long organizationId, OrganizationNode organizationNode) {
		if (organizationNode.getData().getId().equals(organizationId)) {
			return organizationNode;
		}
		List<OrganizationNode> children = organizationNode.getChildren();
		OrganizationNode res = null;
		for(int i = 0 ; res == null && i < children.size(); i++) {
			res = getOwnOrganizationNode(organizationId, children.get(i));
		}
		return res;
	}
	
	@Override
	public void save(OrgHierarchyDTO subject) throws Exception {
		super.save(subject);
		cachedOrganizationNode.remove(subject.getId() + "." + subject.getVersionId() + ".");
	}

	@Override
	public void delete(OrgHierarchyDTO subject) {
		super.delete(subject);
	}

	@Override
	public void insertHeader(OrgHierarchyDTO subject) {
		
		OrganizationHierarchy hier = modelMapper.map(subject, OrganizationHierarchy.class);
		hierMapper.insert(hier);
		subject.setId(hier.getId());
	}

	@Override
	public void updateHeader(OrgHierarchyDTO subject) {
		
		//OrganizationHierarchy hier = modelMapper.map(subject, OrganizationHierarchy.class);
		OrganizationHierarchy hier = new OrganizationHierarchy();
		hier.setId(subject.getId());
		hier.setCompanyId(subject.getCompanyId());
		hier.setOrgHierType(subject.getOrgHierType());
		hier.setIsDeptOwner(subject.getIsDeptOwner());
		hier.setOrgHierName(subject.getOrgHierName());
		hier.setDescription(subject.getDescription());
		hier.setCreatedBy(subject.getCreatedBy());
		hier.setCreationDate(subject.getCreationDate());
		hier.setLastUpdatedBy(subject.getLastUpdatedBy());
		hier.setLastUpdateDate(subject.getLastUpdateDate());
		hierMapper.updateByPrimaryKey(hier);
	}	

	@Override
	public void insertDetail(OrgHierarchyDTO subject) {
		
		//OrgHierarchyVersion version = modelMapper.map(subject, OrgHierarchyVersion.class);
		
		OrgHierarchyVersion version = new OrgHierarchyVersion();
		version.setVersionId(subject.getVersionId());
		version.setCompanyId(subject.getCompanyId());
		version.setId(subject.getId());
		version.setVersionNumber(subject.getVersionNumber());
		version.setDateFrom(subject.getDateFrom());
		version.setDateTo(subject.getDateTo());
		version.setCreatedBy(subject.getCreatedBy());
		version.setCreationDate(subject.getCreationDate());
		version.setLastUpdateDate(subject.getLastUpdateDate());
		version.setLastUpdatedBy(subject.getLastUpdatedBy());
		versionMapper.insert(version);
		subject.setVersionId(version.getVersionId());
		
		if(subject.getElements() != null) {
			for(OrgHierElementDTO elementDto : subject.getElements()) {
				//OrgHierarchyElement element = modelMapper.map(elementDto, OrgHierarchyElement.class);
				OrgHierarchyElement element = new OrgHierarchyElement();
				element.setOrgHierElementId(elementDto.getOrgHierElementId());
				element.setCompanyId(subject.getCompanyId());
				element.setVersionId(subject.getVersionId());
				element.setParentId(elementDto.getParentId());
				element.setOrganizationId(elementDto.getOrganizationId());
				element.setCreatedBy(subject.getCreatedBy());
				element.setCreationDate(subject.getCreationDate());
				element.setLastUpdatedBy(subject.getLastUpdatedBy());
				element.setLastUpdateDate(subject.getLastUpdateDate());
				elementMapper.insert(element);
			}
		}
	}

	@Override
	public void deleteHeader(OrgHierarchyDTO subject) {
		hierMapper.deleteByPrimaryKey(subject.getId());
	}

	@Override
	public void deleteDetail(OrgHierarchyDTO subject) {
		OrgHierarchyElementExample elementExample = new OrgHierarchyElementExample();
		elementExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		elementMapper.deleteByExample(elementExample);
		
		versionMapper.deleteByPrimaryKey(subject.getVersionId());
	}

	@Override
	public void updateDetail(OrgHierarchyDTO subject) {
		
		//OrgHierarchyVersion version = modelMapper.map(subject, OrgHierarchyVersion.class);
		
		OrgHierarchyVersion version = new OrgHierarchyVersion();
		version.setVersionId(subject.getVersionId());
		version.setCompanyId(subject.getCompanyId());
		version.setId(subject.getId());
		version.setVersionNumber(subject.getVersionNumber());
		version.setDateFrom(subject.getDateFrom());
		version.setDateTo(subject.getDateTo());
		version.setCreatedBy(subject.getCreatedBy());
		version.setCreationDate(subject.getCreationDate());
		version.setLastUpdateDate(subject.getLastUpdateDate());
		version.setLastUpdatedBy(subject.getLastUpdatedBy());
		
		OrgHierarchyElementExample elementExample = new OrgHierarchyElementExample();
		elementExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		elementMapper.deleteByExample(elementExample);
		
		if(subject.getElements() != null) {
			for(OrgHierElementDTO elementDto : subject.getElements()) {
				/*OrgHierarchyElement element = modelMapper.map(elementDto, OrgHierarchyElement.class);
				element.setCompanyId(subject.getCompanyId());
				element.setVersionId(subject.getVersionId());
				element.setCreatedBy(subject.getCreatedBy());
				element.setCreationDate(subject.getCreationDate());
				element.setLastUpdatedBy(subject.getLastUpdatedBy());
				element.setLastUpdateDate(subject.getLastUpdateDate());*/
				
				OrgHierarchyElement element = new OrgHierarchyElement();
				element.setOrgHierElementId(elementDto.getOrgHierElementId());
				element.setCompanyId(subject.getCompanyId());
				element.setVersionId(subject.getVersionId());
				element.setParentId(elementDto.getParentId());
				element.setOrganizationId(elementDto.getOrganizationId());
				element.setCreatedBy(subject.getCreatedBy());
				element.setCreationDate(subject.getCreationDate());
				element.setLastUpdatedBy(subject.getLastUpdatedBy());
				element.setLastUpdateDate(subject.getLastUpdateDate());
				elementMapper.insert(element);
			}
		}		
		versionMapper.updateByPrimaryKey(version);
	}

	@Override
	public Long getNextHeaderId() {
		return idGenerator.getNextHeaderId("WOS_ORGANIZATION_HIERS_S");
	}

	@Override
	public OrgHierarchyDTO findByIdAndVersionNumber(Long id,
			Integer versionNumber) {
		return hierFinder.findByIdAndVersionNumber(id, versionNumber);
	}

	@Override
	public List<Integer> findVersionsById(Long id) {
		return hierFinder.findVersionsById(id);
	}

	@Override
	public void validate(OrgHierarchyDTO subject) throws ValidationException {
		organizationHierarchyValidator.validate(subject);
	}

	@Override
	public List<OrgHierarchyDTO> findByExample(OrgHierarchyDTO subject) {
		return hierFinder.findByExample(subject);
	}

	@Override
	public List<OrgHierarchyDTO> findAll() {
		return hierFinder.findAll(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	public List<OrganizationHierarchy> findByExample(
			OrganizationHierarchyExample example) {
		return hierMapper.selectByExample(example);
	}

	@Override
	public List<OrgHierarchyDTO> findHierarchyIsDeptOwnerByOrgId(Long organizationId) {
		return hierFinder.findHierarchyIsDeptOwnerByOrgId(organizationId);
	}

	@Override
	public OrganizationNode findOrganizationNode(Long hierarchyId, Long organizationId, Long versionId, Date date, Boolean isActiveOrg) {
		OrganizationNode organizationNode = getOrganizationNode(hierarchyId, versionId, date, isActiveOrg);
		return getOwnOrganizationNode(organizationId, organizationNode);
		
	}
	
	@Override
	public OrganizationNode findOrganizationNode(Long hierarchyId,
			Long organizationId, Boolean isActiveOrg) {
		return findOrganizationNode(hierarchyId, organizationId, null, new Date(), isActiveOrg);
	}

	@Override
	public Integer countByExample(OrganizationHierarchyExample example) {
		example.createCriteria().andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		return hierMapper.countByExample(example);
	}

	@Override
	public List<OrgHierarchyDTO> findByInquiry(OrgHierarchyDTO subject) {
		return hierFinder.findByInquiry(subject);
	}

	@Override
	public OrgHierarchyDTO findById(Long id) {
		return hierFinder.findById(id);
	}

	@Override
	public boolean isHaveFuture(Long id) {
		return hierFinder.isHaveFuture(id) == 1;
	}

	@Override
	public List<OrgHierarchyDTO> findHierarchy(
			String organizationHierarchyType, Date date, Long companyId) {
		return hierFinder.findHierarchy(organizationHierarchyType, date, companyId);
	}

	@Override
	public List<Long> findOrganizationInHierarchy(Long companyId) {
		return hierFinder.findOrganizationInHierarchy(companyId);
	}

	@Override
	public Boolean isFutureExist(Long id) {
		return hierFinder.isFutureExist(id) > 0;
	}

	@Override
	public List<OrganizationHierarchy> findByExampleWithRowBounds(
			OrganizationHierarchyExample example, int limit, int offset) {
		return hierMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}	

}
