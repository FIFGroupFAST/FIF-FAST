package co.id.fifgroup.basicsetup.controller;

import java.util.Date;
import java.util.HashMap;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.domain.BusinessGroup;
import co.id.fifgroup.basicsetup.dto.BusinessGroupDTO;
import co.id.fifgroup.basicsetup.service.BusinessGroupService;
import co.id.fifgroup.basicsetup.validation.BusinessGroupValidator;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;

@VariableResolver(DelegatingVariableResolver.class)
public class BusinessGroupSetupComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(BusinessGroupSetupComposer.class);
	
	@WireVariable
	private Map<String, Object> arg;
	
	@Wire
	private Textbox txtGroupCode;
	@Wire
	private Textbox txtGroupName;
	@Wire
	private Textbox txtDesc;
	@Wire
	private Button btnSave;
	
	private BusinessGroup businessGroup;
	
	@WireVariable(rewireOnActivate=true)
	private transient BusinessGroupService businessGroupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if(arg.containsKey(BusinessGroupDTO.class.getName())) {
			businessGroup = ((BusinessGroupDTO) arg.get(BusinessGroupDTO.class.getName())).getBusinessGroup();
			txtGroupCode.setValue(businessGroup.getGroupCode());
			txtGroupName.setValue(businessGroup.getGroupName());
			txtDesc.setValue(businessGroup.getDescription());
			txtGroupCode.setDisabled(true);
		}
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			txtGroupName.setDisabled(true);
			txtDesc.setDisabled(true);
			txtGroupCode.setDisabled(true);
		}
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8651474911836451531L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						setBusinessGroup();
						businessGroupServiceImpl.save(businessGroup);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						Map<String, Object> param = new HashMap<String, Object>();
						param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
						Executions.createComponents("~./hcms/basic-setup/business_group_inquiry.zul", getSelf().getParent(), param);
						getSelf().detach();
					} catch (ValidationException vex) {
						showErrorMessage(vex);
					} catch (Exception e) {
						Messagebox.show("System Error");
						log.error(e.getMessage(), e);
					}
				}
			}
			
		});
	}
	
	private void showErrorMessage(ValidationException vex) {
		Map<String, String> errors = vex.getConstraintViolations();
		ErrorMessageUtil.clearErrorMessage(txtGroupCode);
		ErrorMessageUtil.clearErrorMessage(txtGroupName);
		if(errors.containsKey(BusinessGroupValidator.GROUP_CODE)) {
			ErrorMessageUtil.setErrorMessage(txtGroupCode, vex.getMessage(BusinessGroupValidator.GROUP_CODE));				
		}
		if(errors.containsKey(BusinessGroupValidator.GROUP_NAME)) {
			ErrorMessageUtil.setErrorMessage(txtGroupName, vex.getMessage(BusinessGroupValidator.GROUP_NAME));
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
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/basic-setup/business_group_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				}
			}
			
		});
	}

	private void setBusinessGroup() {
		if(businessGroup == null) {
			businessGroup = new BusinessGroup();
		}
		businessGroup.setGroupCode(txtGroupCode.getValue().trim());
		businessGroup.setGroupName(txtGroupName.getValue().trim());
		businessGroup.setDescription(txtDesc.getValue().trim());
		if(businessGroup.getGroupId() == null) {
			businessGroup.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			businessGroup.setCreationDate(new Date());
		}
		businessGroup.setLastUpdateDate(new Date());
		businessGroup.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
	}
	
}
