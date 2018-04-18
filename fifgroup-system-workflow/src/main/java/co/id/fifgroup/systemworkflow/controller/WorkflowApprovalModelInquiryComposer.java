package co.id.fifgroup.systemworkflow.controller;

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
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowApprovalModelInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(WorkflowApprovalModelInquiryComposer.class);

	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	
	@Wire
	private Textbox txtApprovalModelName;
	@Wire
	private Listbox lbxApprovalModel;
	@Wire
	private Button btnNew;
	
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initComponent();
		setFunctionSecurity();
	}
	

	@Listen("onDownloadUserManual = #winWorkflowApprovalModelInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	public void initComponent() {
		lbxApprovalModel.setMold("paging");
		lbxApprovalModel.setPageSize(GlobalVariable.getMaxRowPerPage());
	}
	
	private void setFunctionSecurity(){
//		if(!functionPermission.isCreateable())
//			btnNew.setDisabled(true);
	}
	
	@Listen("onClick = button#btnFind; onOK = #txtApprovalModelName")
	public void doFind() {
		if (txtApprovalModelName.getValue().isEmpty()) {
			Messagebox.show(Labels.getLabel("common.searchMightBeSlow"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new SerializableEventListener<Event>() {				

				private static final long serialVersionUID = 7764066196249703298L;

				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getName().equals("onYes")) {
						doSearch();
					}
				}
			});			
		} else {
			doSearch();
		}
	}
	
	public void doSearch() {
		try {
			List<AVM> listAvm = avmAdapterServiceImpl.getAVMByName(txtApprovalModelName.getValue());
			ListModel<AVM> listModelAvm = new ListModelList<AVM>(listAvm);
			lbxApprovalModel.setModel(listModelAvm);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Executions.createComponents("~./hcms/workflow/workflow_approval_model.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	@Listen("onDetail = #lbxApprovalModel")
	public void onDetail(ForwardEvent evt) {
		AVM selectedData = (AVM) evt.getData();
		params.put(AVM.class.getName(), selectedData);
		Executions.createComponents("~./hcms/workflow/workflow_approval_model.zul", getSelf().getParent(), params);
		getSelf().detach();
	}
}
