package co.id.fifgroup.workstructure.ui;

import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobExample;
import co.id.fifgroup.workstructure.service.JobSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;

public class CommonJobBandbox extends CommonPopupDoubleBandbox {

	private static final long serialVersionUID = 1L;
	
	public CommonJobBandbox() {
		setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<Job>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2478200183937714478L;

			@Override
			public List<Job> findBySearchCriteria(String searchCriteria1, String searchCriteria2,
					int limit, int offset) {
				JobExample jobExample = new JobExample();
				jobExample.or()
					.andJobCodeLike(searchCriteria1)
					.andJobNameLike(searchCriteria2)
					.andCompanyIdEqualTo(getSecurityService().getSecurityProfile().getWorkspaceCompanyId());
				
				return getJobService().findByExample(jobExample, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1, String searchCriteria2) {
				JobExample jobExample = new JobExample();
				jobExample.or()
					.andJobCodeLike(searchCriteria1)
					.andJobNameLike(searchCriteria2)
					.andCompanyIdEqualTo(getSecurityService().getSecurityProfile().getWorkspaceCompanyId());
				return getJobService().countByExample(jobExample);
			}

			@Override
			public void mapper(KeyValue keyValue, Job data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getJobCode());
				keyValue.setDescription(data.getJobName());
			}
		});
		
	}
	
	public JobSetupService getJobService() {
		return (JobSetupService) SpringUtil.getBean("jobSetupServiceImpl");
	}
	
	public SecurityService getSecurityService() {
		return (SecurityService) SpringUtil.getBean("securityServiceImpl");
	}

}
