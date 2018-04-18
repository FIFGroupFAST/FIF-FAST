package co.id.fifgroup.workstructure.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.workstructure.dto.BranchSplitMasterDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public interface BranchSplitGeneratorService {
	
	public List<KeyValue> getOrganizationGeneratorType(Long companyId) throws RuntimeException;
	public void save(BranchSplitMasterDTO subject);
	public BranchSplitMasterDTO getBranchSplitMasterByType(String type, Long companyId);
	public BranchSplitMasterDTO getTemplateByType(String type, Long companyId);
	public List<KeyValue> getCostCenter(String criteria, int limit, int offset);
	public Integer countCostCenter(String criteria);
	public List<KeyValue> getGlCodes(String criteria, int limit, int offset);
	public Integer countGlCodes(String criteria);
	public void download(List<OrganizationDTO> organizationDTOs);
	public void process(List<OrganizationDTO> organizationDTOs, Date effectiveDate, Long parentOrgId) throws Exception;
}
