package co.id.fifgroup.basicsetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
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

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.dto.TransactionTypeDTO;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.basicsetup.service.TransactionTypeService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class TransactionTypeInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	@WireVariable
	private Map<String, Object> arg;
	@Wire
	private Listbox lstModule;
	@Wire
	private Searchtextbox txtTransactionType;
	@Wire
	private Listbox lstTransactionType;
	@Wire
	private Button btnNew;
	
	private ListModelList<TransactionTypeDTO> listModelTransactionTypeDto;
	private ListModelList<Module> listModelModule;
	
	@WireVariable(rewireOnActivate=true)
	private transient TransactionTypeService transactionTypeServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient ModuleService moduleServiceImpl;
	
	private List<TransactionTypeDTO> transactionTypesDto;
	
	private Module selectedModule;
	private String transactionType;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	@Listen("onDownloadUserManual = #winTransactionTypeInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	private void init() {
		initListModelTransactionType();
		initListModelModule();
	}
	
	private void initListModelTransactionType() {
		listModelTransactionTypeDto = new ListModelList<TransactionTypeDTO>();
		lstTransactionType.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstTransactionType.setModel(listModelTransactionTypeDto);
	}
	
	private void initListModelModule() {
		Module moduleSelect = new Module();
		moduleSelect.setModuleName(Labels.getLabel("bse.select"));
		List<Module> listModule = new ArrayList<Module>();
		listModule.add(moduleSelect);
		
		ModuleExample example= new ModuleExample(); 
		example.setOrderByClause("MODULE_NAME ASC");
		
		listModule.addAll(moduleServiceImpl.getModuleByExample(example));
		listModelModule = new ListModelList<Module>(listModule);
		lstModule.setModel(listModelModule);
		lstModule.renderAll();
		listModelModule.addToSelection(moduleSelect);
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/transaction_type.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = #btnFind; onOK = #txtTransactionType")
	public void onFind() {
		selectedModule = (Module) lstModule.getSelectedItem().getValue();
		transactionType = txtTransactionType.getValue();
		if(selectedModule.getModuleId() == null && transactionType.equals("%%")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 5933959927551946935L;

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
		transactionTypesDto = transactionTypeServiceImpl.getTransactionTypeDTOByModuleAndTransactionType(selectedModule, transactionType);
		listModelTransactionTypeDto.clear();
		listModelTransactionTypeDto.addAll(transactionTypesDto);
	}
	
}
