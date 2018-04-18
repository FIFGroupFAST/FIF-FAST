package co.id.fifgroup.workstructure.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.workstructure.domain.OrganizationLevel;
import co.id.fifgroup.workstructure.domain.OrganizationLevelExample;
import co.id.fifgroup.workstructure.service.OrganizationLevelSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;

@VariableResolver(DelegatingVariableResolver.class)
public class OrganizationLevelSetupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(OrganizationLevelSetupInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire private CommonPopupDoubleBandbox bnbOrgLevel;
	@Wire private Listbox lstOrganizationLevel;
	@Wire private Datebox dtbEffectiveOnDate;
	
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationLevelSetupService organizationLevelSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private OrganizationLevelDto example;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		lstOrganizationLevel.setMold("paging");
		lstOrganizationLevel.setPageSize(GlobalVariable.getMaxRowPerPage());
		dtbEffectiveOnDate.setValue(new Date());
		populateLevelName();
	}
	
	@Listen("onDownloadUserManual = #winOrganizationLevelSetupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen("onClick = #btnFind")	
	public void findOrganizationLevel() {
		example = new OrganizationLevelDto();
		example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		example.setName(bnbOrgLevel.getKeyValue() != null ? (String) bnbOrgLevel.getKeyValue().getDescription() : null);
		example.setEffectiveOnDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : DateUtils.truncate(new Date(), Calendar.DATE));
		
		if(example.getName() == null && example.getEffectiveOnDate() == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {						
						List<OrganizationLevelDto> result = organizationLevelSetupServiceImpl.findByExample(example);
						lstOrganizationLevel.setModel(new ListModelList<OrganizationLevelDto>(result));
					}
				}				
			});
		} else {
			List<OrganizationLevelDto> result = organizationLevelSetupServiceImpl.findByExample(example);
			lstOrganizationLevel.setModel(new ListModelList<OrganizationLevelDto>(result));
		}
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Executions.createComponents("~./hcms/workstructure/organization_level_setup.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	@Listen("onLevelDetail = #winOrganizationLevelSetupInquiry")
	public void onLevelDetail(ForwardEvent event) {
		Map<String, Object> params = new HashMap<>();
		params.put(OrganizationLevelDto.class.getName(), event.getData());
		Executions.createComponents("~./hcms/workstructure/organization_level_setup.zul", getSelf().getParent(), params);
		getSelf().detach();
	}
	
	private void populateLevelName() {
		bnbOrgLevel.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<OrganizationLevel>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<OrganizationLevel> findBySearchCriteria(
					String searchCriteria1, String searchCriteria2, int limit,
					int offset) {
				OrganizationLevelExample example = new OrganizationLevelExample();
				example.createCriteria().andLevelCodeLikeInsensitive(searchCriteria1)
					.andLevelNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return organizationLevelSetupServiceImpl.findByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				OrganizationLevelExample example = new OrganizationLevelExample();
				example.createCriteria().andLevelCodeLikeInsensitive(searchCriteria1)
					.andLevelNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return organizationLevelSetupServiceImpl.countByExample(example);
			}
			
			@Override
			public void mapper(KeyValue keyValue, OrganizationLevel data) {
				keyValue.setKey(data.getLevelId());
				keyValue.setValue(data.getLevelCode());
				keyValue.setDescription(data.getLevelName());
			}
		});
	}
}
