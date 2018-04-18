package co.id.fifgroup.workstructure.ui;

import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.workstructure.service.GradeSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;

public class CommonGradeBandbox extends CommonPopupBandbox {

	private static final long serialVersionUID = 1L;
	
	public CommonGradeBandbox() {
		setSearchDelegate(new SerializableSearchDelegate<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2478200183937714478L;

			@Override
			public List<String> findBySearchCriteria(String searchCriteria1, 
					int limit, int offset) {
				return getGradeService().findGradeActiveByCompanyId(getSecurityService().getSecurityProfile().getWorkspaceCompanyId()
						, searchCriteria1, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1) {
				return getGradeService().findGradeActiveByCompanyId(getSecurityService().getSecurityProfile().getWorkspaceCompanyId()
						, searchCriteria1, Integer.MAX_VALUE, 0 ).size();
			}

			@Override
			public void mapper(KeyValue keyValue, String data) {
				keyValue.setKey(data);
				keyValue.setValue(data);
				keyValue.setDescription(data);
			}
		});
		
	}
	
	public GradeSetupService getGradeService() {
		return (GradeSetupService) SpringUtil.getBean("gradeSetupServiceImpl");
	}
	
	public SecurityService getSecurityService() {
		return (SecurityService) SpringUtil.getBean("securityServiceImpl");
	}

}
