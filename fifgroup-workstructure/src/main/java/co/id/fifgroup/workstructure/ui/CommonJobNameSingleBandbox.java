package co.id.fifgroup.workstructure.ui;

import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.workstructure.service.JobSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.workstructure.dto.JobDTO;

public class CommonJobNameSingleBandbox extends CommonPopupBandbox {

	private static final long serialVersionUID = 1L;
	
	public CommonJobNameSingleBandbox() {
		this.setTitle(Labels.getLabel("common.listOfJob"));
		this.setSearchText(Labels.getLabel("common.jobName"));
		this.setHeaderLabel(Labels.getLabel("common.jobName"));
		
		setSearchDelegate(new SerializableSearchDelegate<JobDTO>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public List<JobDTO> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				return getJobService().findJobByJobName(searchCriteria, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return  getJobService().countJobByJobName(searchCriteria);
			}

			@Override
			public void mapper(KeyValue keyValue, JobDTO data) {
				keyValue.setKey(data.getJobName());
				keyValue.setValue(data.getJobName());
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
