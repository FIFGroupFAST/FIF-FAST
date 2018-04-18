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

import co.id.fifgroup.basicsetup.dto.ModuleDTO;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class ModuleInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	private Map<String, Object> arg;

	private Map<String, Object> params = new HashMap<String, Object>();

	@WireVariable(rewireOnActivate = true)
	private transient ModuleService moduleServiceImpl;

	@Wire
	private Searchtextbox txtModuleName;
	@Wire
	private Listbox lstModule;
	@Wire
	private Button btnNew;

	private List<ModuleDTO> modules;

	private ListModelList<ModuleDTO> listModelModuleDto;
	
	private String moduleName;
	
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

	private void init() {
		initListModelModuleDto();
	}
	
	private void initListModelModuleDto() {
		listModelModuleDto = new ListModelList<ModuleDTO>();
		lstModule.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstModule.setModel(listModelModuleDto);
	}
	
	@Listen("onDownloadUserManual = #winModuleInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/module_detail.zul",
				getSelf().getParent(), params);
		getSelf().detach();
	}

	@Listen("onClick = button#btnFind; onOK = #txtModuleName")
	public void onFind() {
		moduleName = txtModuleName.getValue();
		if(moduleName.equals("%%")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 5439527390796683627L;

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
		modules = moduleServiceImpl.getModuleDtoByModuleName(moduleName);
		listModelModuleDto.clear();
		listModelModuleDto.addAll(modules);
	}
	
	@Listen("onDetail = #lstModule")
	public void onDetail(ForwardEvent evt){
		ModuleDTO selectedData = (ModuleDTO)evt.getData();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ModuleDTO.class.getName(), selectedData);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/module_detail.zul", getSelf().getParent(), params);
		getSelf().detach();
	}

}
