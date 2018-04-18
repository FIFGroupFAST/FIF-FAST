package co.id.fifgroup.workstructure.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobExample;
import co.id.fifgroup.workstructure.service.JobSetupService;

import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.CommonPopupTripleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateTripleCriteria;
import co.id.fifgroup.core.ui.lookup.TripleBandKeyValue;
import co.id.fifgroup.workstructure.dao.JobMapper;
import co.id.fifgroup.workstructure.dto.JobDTO;

public class CommonJobCompanyBandbox extends CommonPopupTripleBandbox {

	private static final long serialVersionUID = 1L;
	
	private Long companyId;
	
	private String jobGroupCode;
	
	private boolean isJobGroupMustBeNotNull;
	
	private boolean isCompanyMustBeNotNull;
	
	public CommonJobCompanyBandbox() {
		this.setTitle(Labels.getLabel("common.listOfJob"));
		this.setSearchText1(Labels.getLabel("wos.companyName"));
		this.setSearchText2(Labels.getLabel("common.jobCode"));
		this.setSearchText3(Labels.getLabel("common.jobName"));
		this.setHeaderLabel1(Labels.getLabel("wos.companyName"));
		this.setHeaderLabel2(Labels.getLabel("common.jobCode"));
		this.setHeaderLabel3(Labels.getLabel("common.jobName"));
		this.setConcatValueDescriptionDescription2(true);
		this.setCompanyMustBeNotNull(false);
		this.setJobGroupMustBeNotNull(false);
		
		setSearchDelegate(new SerializableSearchDelegateTripleCriteria<JobDTO>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<JobDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, String searchCriteria3, int limit,
					int offset) {
				if((isJobGroupMustBeNotNull() && jobGroupCode == null)
						|| (isCompanyMustBeNotNull() && companyId == null)){
					return new ArrayList<JobDTO>();
				}
				else {
					return getJobService().findJobByCompanyName(searchCriteria2, searchCriteria3, searchCriteria1, 
							companyId, jobGroupCode, limit, offset);
				}
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2, String searchCriteria3) {
				if((isJobGroupMustBeNotNull() && jobGroupCode == null)
						|| (isCompanyMustBeNotNull() && companyId == null)){
					return 0;
				}
				else {
					return getJobService().countJobByCompanyName(searchCriteria2, searchCriteria3, searchCriteria1, 
							companyId, jobGroupCode);
				}
			}

			@Override
			public void mapper(TripleBandKeyValue keyValue, JobDTO data) {
				if (null != data) {
					CompanyDTO company = getCompanyService().findCompanyById(
							data.getCompanyId(), new Date());
					keyValue.setKey(data.getId());
					keyValue.setValue(company.getCompanyName());
					keyValue.setDescription(data.getJobCode());
					keyValue.setDescription2(data.getJobName());
				}
			}
		});
		
	}
	
	public void clearSelection() {
		setRawValue(null);
	}
	
	public JobSetupService getJobService() {
		return (JobSetupService) SpringUtil.getBean("jobSetupServiceImpl");
	}
	
	private CompanyService getCompanyService() {
		return (CompanyService) SpringUtil.getBean("companyServiceImpl");
	}
	
	public SecurityService getSecurityService() {
		return (SecurityService) SpringUtil.getBean("securityServiceImpl");
	}
	
	public JobMapper getJobMapper() {
		return (JobMapper) SpringUtil.getBean("jobMapper");
	}
	
	public void setRawValue(Long jobId, String companyName, String jobCode, String jobName){
		TripleBandKeyValue keyValue = new TripleBandKeyValue();
		keyValue.setKey(jobId);
		keyValue.setValue(companyName);
		keyValue.setDescription(jobCode);
		keyValue.setDescription2(jobName);
		this.setRawValue(keyValue);
	}
	
	public void setRawValue(Long jobId, Long companyId, String jobCode, String jobName){
		TripleBandKeyValue keyValue = new TripleBandKeyValue();
		keyValue.setKey(jobId);
		keyValue.setValue(getCompanyService().getCompanyNameById(companyId));
		keyValue.setDescription(jobCode);
		keyValue.setDescription2(jobName);
		this.setRawValue(keyValue);
	}
	
	public void setRawValue(Long jobId, String jobCode, String jobName){
		TripleBandKeyValue keyValue = new TripleBandKeyValue();
		keyValue.setKey(jobId);
		Job job = getJobMapper().selectByPrimaryKey(jobId);
		if (job != null)
			keyValue.setValue(getCompanyService().getCompanyNameById(job.getCompanyId()));
		keyValue.setDescription(jobCode);
		keyValue.setDescription2(jobName);
		this.setRawValue(keyValue);
	}
	
	public void setRawValue(Long jobId){
		if (jobId != null) {
			TripleBandKeyValue keyValue = new TripleBandKeyValue();
			keyValue.setKey(jobId);
			Job job = getJobMapper().selectByPrimaryKey(jobId);
			if (job != null)
				keyValue.setValue(getCompanyService().getCompanyNameById(job.getCompanyId()));
			keyValue.setDescription(job.getJobCode());
			keyValue.setDescription2(job.getJobName());
			this.setRawValue(keyValue);
		}
		else {
			super.setRawValue(null);
		}
	}
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getJobGroupCode() {
		return jobGroupCode;
	}

	public void setJobGroupCode(String jobGroupCode) {
		this.jobGroupCode = jobGroupCode;
	}

	public boolean isJobGroupMustBeNotNull() {
		return isJobGroupMustBeNotNull;
	}

	public void setJobGroupMustBeNotNull(boolean isJobGroupMustBeNotNull) {
		this.isJobGroupMustBeNotNull = isJobGroupMustBeNotNull;
	}

	public boolean isCompanyMustBeNotNull() {
		return isCompanyMustBeNotNull;
	}

	public void setCompanyMustBeNotNull(boolean isCompanyMustBeNotNull) {
		this.isCompanyMustBeNotNull = isCompanyMustBeNotNull;
	}
}
