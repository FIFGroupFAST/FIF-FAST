package co.id.fifgroup.ssoa.service;

import co.id.fifgroup.core.service.WorkflowService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.ParameterDetail;
import co.id.fifgroup.ssoa.domain.SOPeriod;
import co.id.fifgroup.ssoa.domain.SOPeriodExample;
import co.id.fifgroup.ssoa.domain.SOPeriodDetail;
import co.id.fifgroup.ssoa.domain.SOPeriodDetailExample;
import co.id.fifgroup.ssoa.dto.SOPeriodDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDetailDTO;

import java.util.List;
import java.util.UUID;

public interface SOPeriodService extends WorkflowService {
	
	public void save(SOPeriodDTO soPeriodDto) throws Exception;
	public List<SOPeriodDTO> getSOPeriodDtoByExample(SOPeriodExample example, int limit, int offset);
	public List<SOPeriodDetailDTO> getSOPeriodDtoDetailByExample(SOPeriodDetail example, int limit, int offset, Long taskGroupId);
	public SOPeriodDTO getSOPeriodById(Long id);
	public List<SOPeriod> getSOPeriodByExample(SOPeriodExample example, int limit, int offset);
	public List<SOPeriodDTO> getSOPeriodByExample(SOPeriodExample example);
	public int countSOPeriodByExample(SOPeriodExample example, Long taskGroupId);
	public int countByExample(SOPeriodExample example);
	public List<SOPeriodDetail> getSOPeriodDetailBySOPeriodId(Long id);
	public List<SOPeriodDetailDTO> getSOPeriodDetailDtoBySOPeriodId(Long id);
	public List<SOPeriodDetail> getSOPeriodDetailByExample(SOPeriodDetailExample example);
	
	public List<Branch> getBranchByExample(BranchExample example, int limit, int offset);
	public int countBranchByExample(BranchExample example);
	public Branch getBranchById(Long id,Long companyId);
	public List<Branch> getBranchAll(Branch example);
	int countDetailByExample(SOPeriodDetail example, Long taskGroupId);
	public List<SOPeriodDetail> getDetailBranchBySOPeriodId(Long id, String statusCode);
	public List<KeyValue> getStatusStockOpname();
	public List<KeyValue> getStatusSOPeriod();
	List<KeyValue> getLookupKeyValues(String lookupName, String key);
	//public void updateCountStatus (SOPeriod soPeriod);
	public void resendNotification(SOPeriodDTO soPeriodDTO)throws Exception;
}