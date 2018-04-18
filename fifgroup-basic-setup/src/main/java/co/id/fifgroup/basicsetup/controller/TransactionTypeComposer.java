package co.id.fifgroup.basicsetup.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.domain.TransactionType;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.basicsetup.service.TransactionTypeService;
import co.id.fifgroup.basicsetup.validation.TransactionTypeValidator;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.core.validation.ValidationException;

@VariableResolver(DelegatingVariableResolver.class)
public class TransactionTypeComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(TransactionTypeComposer.class);
	@WireVariable
	private Map<String, Object> arg;
	@Wire
	private Listbox lstModule;
	@Wire
	private Textbox txtTransactionTypeCode;
	@Wire
	private Textbox txtTransactionType;
	@Wire
	private Button btnSave;
	
	@WireVariable(rewireOnActivate=true)
	private transient ModuleService moduleServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient TransactionTypeService transactionTypeServiceImpl;
	
	private ListModelList<Module> listModelModule;
	
	private TransactionType transactionType;
	
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			lstModule.setDisabled(true);
			txtTransactionType.setDisabled(true);
			txtTransactionTypeCode.setDisabled(true);
		}
			
	}
	
	private void init() {
		initListModelModule();
	}
	
	@Listen("onDownloadUserManual = #winTransactionTypeInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
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
	
	@Listen("onClick = button#btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4612205118676350895L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try{
						setTransactionType();
						transactionTypeServiceImpl.save(transactionType);
						Map<String, Object> param = new HashMap<String, Object>();
						param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						Executions.createComponents("~./hcms/basic-setup/transaction_type_inquiry.zul", getSelf().getParent(), param);
						getSelf().detach();
					} catch (ValidationException vex){
						showErrorMessage(vex);
						logger.error(vex.getMessage(), vex);
					} catch (Exception e) {
						Messagebox.show("System Error");
						logger.error(e.getMessage(), e);
					}
				}
			}
			
		});
	}
	
	private void showErrorMessage(ValidationException vex) {
		Map<String, String> errors = vex.getConstraintViolations();
		ErrorMessageUtil.clearErrorMessage(lstModule);
		ErrorMessageUtil.clearErrorMessage(txtTransactionType);
		ErrorMessageUtil.clearErrorMessage(txtTransactionTypeCode);
		if(errors.containsKey(TransactionTypeValidator.TRANSACTION_TYPE_MODULE_ID)) {
			ErrorMessageUtil.setErrorMessage(lstModule, vex.getMessage(TransactionTypeValidator.TRANSACTION_TYPE_MODULE_ID));
		}
		if(errors.containsKey(TransactionTypeValidator.TRANSACTION_TYPE_TRX_CODE)) {
			ErrorMessageUtil.setErrorMessage(txtTransactionTypeCode, vex.getMessage(TransactionTypeValidator.TRANSACTION_TYPE_TRX_CODE));				
		}
		if(errors.containsKey(TransactionTypeValidator.TRANSACTION_TYPE_TRX_TYPE)) {
			ErrorMessageUtil.setErrorMessage(txtTransactionType, vex.getMessage(TransactionTypeValidator.TRANSACTION_TYPE_TRX_TYPE));
		}
		
	}

	@Listen("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5799520384125775408L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/basic-setup/transaction_type_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				}
			}
		});
	}
	
	private void setTransactionType() {
		if(transactionType == null) {
			transactionType = new TransactionType();
		}
		Module selectedModule = (Module) lstModule.getSelectedItem().getValue();
		transactionType.setTrxCode(txtTransactionTypeCode.getValue());
		transactionType.setTrxType(txtTransactionType.getValue());
		transactionType.setModuleId(selectedModule.getModuleId());
		if(transactionType.getTrxTypeId() == null) {
			transactionType.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			transactionType.setCreationDate(new Date());
		}
		transactionType.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		transactionType.setLastUpdateDate(new Date());
	}
}
