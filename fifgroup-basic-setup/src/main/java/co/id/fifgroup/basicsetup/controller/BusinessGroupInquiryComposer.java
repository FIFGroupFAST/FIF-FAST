package co.id.fifgroup.basicsetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.dto.BusinessGroupDTO;
import co.id.fifgroup.basicsetup.service.BusinessGroupService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class BusinessGroupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	
	private List<BusinessGroupDTO> businessGroups;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Searchtextbox txtGroupName;
	@Wire
	private Listbox lstBusinessGroup;
	@Wire
	private Button btnNew;
	
	@WireVariable(rewireOnActivate=true)
	private transient BusinessGroupService businessGroupServiceImpl;
	
	private ListModelList<BusinessGroupDTO> listModelBusinessGroup;
	
	private String groupName;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	@Listen("onDownloadUserManual = #winBusinessGroupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void init() {
		initListModelBusinessGroup();
	}
	
	private void initListModelBusinessGroup() {
		listModelBusinessGroup = new ListModelList<BusinessGroupDTO>();
		lstBusinessGroup.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstBusinessGroup.setModel(listModelBusinessGroup);
	}
	
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/business_group_setup.zul", getSelf().getParent(), params);
		getSelf().detach();
	}

	@Listen("onClick = button#btnFind; onOK = #txtGroupName")
	public void onFind() {
		groupName = txtGroupName.getValue();
		if(groupName.equals("%%")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -8756250972566999901L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						search();
					}
				}
				
			});
		} else {
			search();
		}
	}
	
	private void search() {
		businessGroups = businessGroupServiceImpl.getBusinessGroupDtoByGroupName(groupName);
		listModelBusinessGroup.clear();
		listModelBusinessGroup.addAll(businessGroups);
	}
	
	@Listen("onDetail = #lstBusinessGroup")
	public void onDetail(ForwardEvent evt) {
		BusinessGroupDTO selectedData = (BusinessGroupDTO) evt.getData();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(BusinessGroupDTO.class.getName(), selectedData);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/business_group_setup.zul", getSelf().getParent(), params);
		getSelf().detach();
	}
	
}
