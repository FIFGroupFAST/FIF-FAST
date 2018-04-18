package co.id.fifgroup.basicsetup.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.dto.ModuleDTO;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.basicsetup.validation.ModuleValidator;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxFrom;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;


@VariableResolver(DelegatingVariableResolver.class)
public class ModuleDetailComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(ModuleDetailComposer.class);
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Textbox txtModuleCode;
	@Wire
	private Textbox txtModuleName;
	@Wire
	private DateBoxFrom dtbDateFrom;
	@Wire
	private DateBoxMax dtbDateTo;
	@Wire
	private Button btnSave;
	@WireVariable(rewireOnActivate = true)
	private transient ModuleService moduleServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private Module module;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if(arg.containsKey(ModuleDTO.class.getName())) {
			ModuleDTO moduleDto = (ModuleDTO) arg.get(ModuleDTO.class.getName());
			module = moduleServiceImpl.getModuleById(moduleDto.getModuleId());
			txtModuleCode.setValue(module.getModuleCode());
			txtModuleName.setValue(module.getModuleName());
			dtbDateFrom.setValue(module.getDateFrom());
			dtbDateTo.setValue(module.getDateTo());
			txtModuleCode.setReadonly(true);
			txtModuleCode.setDisabled(true);
		}
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			txtModuleCode.setDisabled(true);
			txtModuleName.setDisabled(true);
			dtbDateFrom.setDisabled(true);
			dtbDateTo.setDisabled(true);
		}
			
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3775551198508445205L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						setModule();
						moduleServiceImpl.save(module);
						params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						Executions.createComponents("~./hcms/basic-setup/module_inquiry.zul", getSelf().getParent(), params);
						getSelf().detach();
					} catch (ValidationException vex){
						log.error(vex.getMessage());
						showErrorMessage(vex);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
						Messagebox.show(e.getMessage());
					}
				}
			}
			
		});
	}
	
	private void showErrorMessage(ValidationException vex) {
		Map<String, String> errors = vex.getConstraintViolations();
		ErrorMessageUtil.clearErrorMessage(txtModuleCode);
		ErrorMessageUtil.clearErrorMessage(txtModuleName);
		ErrorMessageUtil.clearErrorMessage(dtbDateFrom);
		if(errors.containsKey(ModuleValidator.MODULE_CODE)) {
			ErrorMessageUtil.setErrorMessage(txtModuleCode, vex.getMessage(ModuleValidator.MODULE_CODE));				
		}
		if(errors.containsKey(ModuleValidator.MODULE_NAME)) {
			ErrorMessageUtil.setErrorMessage(txtModuleName, vex.getMessage(ModuleValidator.MODULE_NAME));
		}
		if(errors.containsKey(ModuleValidator.MODULE_DATE_FROM)) {
			ErrorMessageUtil.setErrorMessage(dtbDateFrom, vex.getMessage(ModuleValidator.MODULE_DATE_FROM));
		}
		
		
	}

	@Listen ("onClick = button#btnCancel")
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
					params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/basic-setup/module_inquiry.zul", getSelf().getParent(), params);
					getSelf().detach();
				}
			}
		});
	}
	
	private void setModule(){
		if(module == null) {
			module = new Module();
		}
		module.setModuleCode(txtModuleCode.getValue());
		module.setModuleName(txtModuleName.getValue());
		module.setDateFrom(dtbDateFrom.getValue() == null ? null : DateUtils.truncate(dtbDateFrom.getValue(), Calendar.DATE));
		module.setDateTo(DateUtils.truncate(dtbDateTo.getValue(), Calendar.DATE));
		if(module.getModuleId() == null) {
			module.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			module.setCreationDate(new Date());
		}
		module.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		module.setLastUpdateDate(new Date());
	}

}
