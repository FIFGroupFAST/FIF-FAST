package co.id.fifgroup.workstructure.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchy;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchyExample;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class OrganizationHierarchySetupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(OrganizationHierarchySetupInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private CommonPopupBandbox bnbHierName;
	@Wire
	private Datebox dtbEffectiveOnDate;
	@Wire
	private Listbox lstHierarchy;
	@Wire
	private Button btnNew;
	
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationHierarchySetupService organizationHierarchySetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
		
	private OrgHierarchyDTO selectedHierarchy;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		lstHierarchy.setMold("paging");
		lstHierarchy.setPageSize(GlobalVariable.getMaxRowPerPage());
		dtbEffectiveOnDate.setValue(new Date());
		populateHierName();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	@Listen("onDownloadUserManual = #winOrganizationHierarchySetupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	private void populateHierName() {
		bnbHierName.setSearchDelegate(new SerializableSearchDelegate<OrganizationHierarchy>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<OrganizationHierarchy> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				OrganizationHierarchyExample example = new OrganizationHierarchyExample();
				example.createCriteria().andOrgHierNameLikeInsensitive(searchCriteria)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return organizationHierarchySetupServiceImpl.findByExampleWithRowBounds(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				OrganizationHierarchyExample example = new OrganizationHierarchyExample();
				example.createCriteria().andOrgHierNameLikeInsensitive(searchCriteria)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return organizationHierarchySetupServiceImpl.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, OrganizationHierarchy data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getOrgHierName());
				keyValue.setDescription(data.getOrgHierName());
			}
		});
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/organization_hierarchy_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		populateHierarchy();
		if(selectedHierarchy.getOrgHierName() == null && selectedHierarchy.getEffectiveOnDate() == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						lstHierarchy.setModel(new ListModelList<OrgHierarchyDTO>(organizationHierarchySetupServiceImpl.findByInquiry(selectedHierarchy)));
					}
				}				
			});						
		} else {
			lstHierarchy.setModel(new ListModelList<OrgHierarchyDTO>(organizationHierarchySetupServiceImpl.findByInquiry(selectedHierarchy)));			
		}
	}
	
	private void populateHierarchy() {
		selectedHierarchy = new OrgHierarchyDTO();
		selectedHierarchy.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		selectedHierarchy.setOrgHierName(bnbHierName.getKeyValue() != null ? (String) bnbHierName.getKeyValue().getDescription() : null);
		selectedHierarchy.setEffectiveOnDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : DateUtil.truncate(new Date()));
	}
	
	@Listen("onDetail= #winOrganizationHierarchySetupInquiry")
	public void onDetail(ForwardEvent event){
		selectedHierarchy = (OrgHierarchyDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(OrgHierarchyDTO.class.getName(), selectedHierarchy);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/organization_hierarchy_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDiagram= #winOrganizationHierarchySetupInquiry")
	public void onDiagram(ForwardEvent event){
		selectedHierarchy = (OrgHierarchyDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(OrgHierarchyDTO.class.getName(), selectedHierarchy);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/organization_diagram.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = #btnUpload")
	public void onNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uriOrigin", "~./hcms/workstructure/organization_hierarchy_setup_inquiry.zul");
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/upload_organization_hierarchy.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
}
