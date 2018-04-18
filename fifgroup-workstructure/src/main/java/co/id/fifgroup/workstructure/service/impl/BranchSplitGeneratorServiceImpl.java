package co.id.fifgroup.workstructure.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.domain.OrganizationTemplateDetail;
import co.id.fifgroup.workstructure.domain.OrganizationTemplateDetailExample;
import co.id.fifgroup.workstructure.service.BranchSplitGeneratorService;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.basicsetup.dto.OtherInfoDTO;
import co.id.fifgroup.basicsetup.dto.OtherInfoDetailDTO;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.basicsetup.service.OtherInfoService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.workstructure.dao.OrgHierarchyElementMapper;
import co.id.fifgroup.workstructure.dao.OrgHierarchyVersionMapper;
import co.id.fifgroup.workstructure.dao.OrganizationHierarchyMapper;
import co.id.fifgroup.workstructure.dao.OrganizationTemplateDetailMapper;
import co.id.fifgroup.workstructure.dao.OrganizationTemplateMapper;
import co.id.fifgroup.workstructure.dto.BranchSplitMasterDTO;
import co.id.fifgroup.workstructure.dto.OrgHierElementDTO;
import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.finder.BranchSplitGeneratorFinder;
import co.id.fifgroup.workstructure.validation.OrganizationValidator;

@Service
@Transactional
public class BranchSplitGeneratorServiceImpl implements BranchSplitGeneratorService{
	
	private Logger logger = LoggerFactory.getLogger(BranchSplitGeneratorServiceImpl.class);
	private static String STRUCTURAL = "STRUCTURAL";
	
	@Autowired
	private LookupService lookupServiceImpl;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private BranchSplitGeneratorFinder branchSplitGeneratorFinder;
	@Autowired
	private OrganizationTemplateMapper organizationTemplateMapper;
	@Autowired
	private OrganizationTemplateDetailMapper organizationTemplateDetailMapper;
	@Autowired
	private OtherInfoService otherInfoServiceImpl;
	@Autowired
	private OrganizationSetupService organizationSetupServiceImpl;
	@Autowired
	private OrganizationHierarchySetupService organizationHierarchySetupServiceImpl;
	@Autowired
	private OrganizationValidator organizationValidator;
	@Autowired
	private OrgHierarchyElementMapper orgHierarchyElementMapper;
	@Autowired
	private OrganizationHierarchyMapper organizationHierarchyMapper;
	@Autowired
	private OrgHierarchyVersionMapper orgHierarchyVersionMapper;
	
	private static String ORGANIZATION_TYPE = "WOS_ORGANIZATION_TYPE";
	
	@Override
	public List<KeyValue> getOrganizationGeneratorType(Long companyId) throws RuntimeException{
		
		try {
			return lookupServiceImpl.getLookups(ORGANIZATION_TYPE, securityServiceImpl.getSecurityProfile().getBusinessGroupId(), securityServiceImpl.getSecurityProfile().getCompanyId());
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Override
	public void save(BranchSplitMasterDTO subject) {
		
		if(subject.getTemplateId() != null){
			OrganizationTemplateDetailExample example = new OrganizationTemplateDetailExample();
			example.createCriteria().andTemplateIdEqualTo(subject.getTemplateId());
			organizationTemplateDetailMapper.deleteByExample(example);
			organizationTemplateMapper.deleteByPrimaryKey(subject.getTemplateId());
		}
		
		organizationTemplateMapper.insert(subject);
		for (OrganizationTemplateDetail detail : subject.getOrganizationDetails()) {
			detail.setTemplateId(subject.getTemplateId());
			detail.setCreatedBy(subject.getCreatedBy());
			detail.setCreationDate(subject.getCreationDate());
			detail.setLastUpdatedBy(subject.getLastUpdatedBy());
			detail.setLastUpdateDate(subject.getLastUpdateDate());
			organizationTemplateDetailMapper.insert(detail);
		}
		
	}

	@Override
	public BranchSplitMasterDTO getBranchSplitMasterByType(String type,
			Long companyId) {
		return null;
	}

	@Override
	public BranchSplitMasterDTO getTemplateByType(String type, Long companyId) {
		return branchSplitGeneratorFinder.getTemplateByType(type, companyId);
	}

	@Override
	public List<KeyValue> getCostCenter(String criteria, int limit, int offset) {
		return branchSplitGeneratorFinder.getCostCenter(criteria, new RowBounds(offset, limit));
	}

	@Override
	public Integer countCostCenter(String criteria) {
		return branchSplitGeneratorFinder.countCostCenter(criteria);
	}

	@Override
	public List<KeyValue> getGlCodes(String criteria, int limit, int offset) {
		return branchSplitGeneratorFinder.getGlCodes(criteria, new RowBounds(offset, limit));
	}

	@Override
	public Integer countGlCodes(String criteria) {
		return branchSplitGeneratorFinder.countGlCodes(criteria);
	}

	@Override
	public void download(List<OrganizationDTO> organizationDTOs) {
		SimpleDateFormat sdf = new SimpleDateFormat("ddmmYYYYhhMMS");
		ICsvMapWriter mapWriter = null;
		String fileName = File.separator + "writeWithCsvMapWriter" + sdf.format(new Date()) + ".csv";
		try {
			mapWriter = new CsvMapWriter(new FileWriter(GlobalVariable.getAppsRootFolder() + fileName), CsvPreference.STANDARD_PREFERENCE);
			List<String> fieldMapping = new ArrayList<String>();
			fieldMapping.add("Effective Date From");
			fieldMapping.add("Organization Code");
			fieldMapping.add("Organization Name");
			fieldMapping.add("Organization Description");
			fieldMapping.add("Organization Level");
			fieldMapping.add("NPWP");
			fieldMapping.add("KPP (Code)");
			fieldMapping.add("Location Code");
			fieldMapping.add("Head of Organization (Job Code)");
			fieldMapping.add("Branch Code");
			fieldMapping.add("Cost Center Code");
			fieldMapping.add("Zip Code");
			fieldMapping.add("Address");
			fieldMapping.add("Working Schedule");
			OtherInfoDTO otherInfoDto = otherInfoServiceImpl.getEnableOtherInfoDtoByFormNameAndGroupId("ORGANIZATION", securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
			List<OtherInfoDetailDTO> otherInfoDetailDTOs = otherInfoDto.getOtherInfoDetail();
			for (OtherInfoDetailDTO otherInfoDetailDTO : otherInfoDetailDTOs) {
				fieldMapping.add(otherInfoDetailDTO.getPromptName());
			}

			String[] header = fieldMapping.toArray(new String[] {});

			List<CellProcessor> cellProcessors = new ArrayList<CellProcessor>();
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			for (OtherInfoDetailDTO otherInfoDetailDTO : otherInfoDetailDTOs) {
				cellProcessors.add(new Optional());
			}
			CellProcessor[] processors = cellProcessors.toArray(new CellProcessor[] {});

			mapWriter.writeHeader(header);
			
			for (OrganizationDTO organizationDTO : organizationDTOs) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put(header[0], DateUtil.format(organizationDTO.getEffectiveOnDate(), GlobalVariable.getDateFormat()));
				map.put(header[1], organizationDTO.getCode());
				map.put(header[2], organizationDTO.getName());
				map.put(header[3], organizationDTO.getDescription());
				map.put(header[4], organizationDTO.getLevelCode());
				map.put(header[5], organizationDTO.getNpwp());
				map.put(header[6], organizationDTO.getKppCode());
				map.put(header[7], organizationDTO.getLocation().getLocationCode());
				map.put(header[8], organizationDTO.getOrganizationHeadCode());
				map.put(header[9], organizationDTO.getBranchCode());
				map.put(header[10], organizationDTO.getCostCenterCode());
				map.put(header[11], organizationDTO.getZipCode());
				map.put(header[12], organizationDTO.getAddress());
				map.put(header[13], organizationDTO.getWorkingScheduleId());
				int i = 1;
				for (co.id.fifgroup.workstructure.dto.OtherInfoDTO info : organizationDTO.getOrganizationInfos()) {
					map.put(header[13+i], info.getInfoValue());
					i++;
				}
				mapWriter.write(map, header, processors);
			}
			
			File fileCsv = new File(GlobalVariable.getAppsRootFolder() + fileName);
			InputStream is = new FileInputStream(fileCsv);
			AMedia amedia = new AMedia("Organization.csv", "xls", "application/file", is);
			Filedownload.save(amedia);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (mapWriter != null) {
				try {
					mapWriter.close();
					File file = new File(GlobalVariable.getAppsRootFolder() + fileName);
					file.delete();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
	}

	@Override
	@Transactional(readOnly=false, rollbackFor={Exception.class, ValidationException.class})
	public void process(List<OrganizationDTO> organizationDTOs, Date effectiveDate, Long parentOrgId) throws Exception {
		
		String error = "";
		List<OrgHierElementDTO> elements = new ArrayList<OrgHierElementDTO>();
		OrgHierarchyDTO orgHierarchyDTO = null;
		
		if(parentOrgId != null){
			
			List<OrgHierarchyDTO> curOrgHierDTO = organizationHierarchySetupServiceImpl.findHierarchy(STRUCTURAL, DateUtil.truncate(new Date()), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			
			if(curOrgHierDTO.isEmpty())
				throw new Exception("Can't find structural hierarchy");
			else{
				
				int countNode = branchSplitGeneratorFinder.countOrganizationNodeByHierarchyId(curOrgHierDTO.get(0).getId(), parentOrgId);
				
				if(countNode == 0)
					throw new Exception(" Can't find parent organization on the strucutural hierarchy");
				
				if(organizationHierarchySetupServiceImpl.isHaveFuture(curOrgHierDTO.get(0).getId())){
					Integer lastVersion = branchSplitGeneratorFinder.getLastVerionHierarcyById(curOrgHierDTO.get(0).getId());
					orgHierarchyDTO = organizationHierarchySetupServiceImpl.findByIdAndVersionNumber(curOrgHierDTO.get(0).getId(), lastVersion);
					
					branchSplitGeneratorFinder.deleteOrgHierElementByVersionId(orgHierarchyDTO.getVersionId());
					orgHierarchyVersionMapper.deleteByPrimaryKey(orgHierarchyDTO.getVersionId());
					
					orgHierarchyDTO.setVersionNumber(orgHierarchyDTO.getVersionNumber() - 1);
				
				}else{
					orgHierarchyDTO = organizationHierarchySetupServiceImpl.findByIdAndVersionNumber(curOrgHierDTO.get(0).getId(), curOrgHierDTO.get(0).getVersionNumber());
				}
			}
			
			/*List<OrgHierarchyDTO> orgHierarchyDTOs = organizationHierarchySetupServiceImpl.findHierarchy(STRUCTURAL, effectiveDate, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			
			if(orgHierarchyDTOs.isEmpty())
				throw new Exception("Cannot find Structural Hierarchy as of "+DateUtil.format(effectiveDate, GlobalVariable.getDateFormat()));
			*/
			/*if(organizationHierarchySetupServiceImpl.isHaveFuture(orgHierarchyDTOs.get(0).getId()))
				throw new Exception("Cannot process this request because the Structural Hierarchy have future version");*/
			
		}
		
		
		//Save Organization
		String orgName = "";
		Long parentId = null;
		try {
			for (OrganizationDTO organizationDTO : organizationDTOs) {
				orgName = organizationDTO.getName();
				organizationDTO.setId(null);
				organizationDTO.setOrganizationUuid(UUID.randomUUID());
				organizationDTO.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				organizationDTO.setCreationDate(new Date());
				organizationDTO.setLastUpdateDate(new Date());
				organizationDTO.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				organizationSetupServiceImpl.save(organizationDTO);
				if(organizationDTO.isParent())
					parentId = organizationDTO.getId();
			}
			
			if(parentOrgId != null){
				
				if(parentId != null){
					for (OrganizationDTO organizationDTO : organizationDTOs) {
						if(organizationDTO.isParent()){
							OrgHierElementDTO element = new OrgHierElementDTO();
							element.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
							element.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
							element.setCreationDate(new Date());
							element.setLastUpdateDate(new Date());
							element.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
							element.setOrganizationId(organizationDTO.getId());
							element.setParentId(parentOrgId);
							elements.add(element);	
						}else{
							OrgHierElementDTO element = new OrgHierElementDTO();
							element.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
							element.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
							element.setCreationDate(new Date());
							element.setLastUpdateDate(new Date());
							element.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
							element.setOrganizationId(organizationDTO.getId());
							element.setParentId(parentId);
							elements.add(element);	
						}
					}
				}else{
					for (OrganizationDTO organizationDTO : organizationDTOs) {
						OrgHierElementDTO element = new OrgHierElementDTO();
						element.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
						element.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						element.setCreationDate(new Date());
						element.setLastUpdateDate(new Date());
						element.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						element.setOrganizationId(organizationDTO.getId());
						element.setParentId(parentOrgId);
						elements.add(element);	
					}
				}
				
				//Create Hierarchy
				orgHierarchyDTO.setDateFrom(effectiveDate);
				orgHierarchyDTO.setDateTo(DateUtil.MAX_DATE);
				orgHierarchyDTO.getElements().addAll(elements);
				orgHierarchyDTO.setVersionId(null);
				
				organizationHierarchySetupServiceImpl.save(orgHierarchyDTO);
					
				
			}
			
	
		} catch (ValidationException e) {
			error = error+"Error on "+orgName+" : "+e.getAllMessages()+"\n";
			throw new Exception(error);
		} catch (Exception e) {
			error = error+"Error on "+orgName+" : "+e.getMessage()+"\n";
			throw new Exception(error);
		}
		
		
	}
	


}
