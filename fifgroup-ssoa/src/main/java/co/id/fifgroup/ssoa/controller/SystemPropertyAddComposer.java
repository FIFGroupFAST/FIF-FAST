package co.id.fifgroup.ssoa.controller;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.ssoa.domain.SystemProperty;
import co.id.fifgroup.ssoa.dto.SystemPropertyDTO;
import co.id.fifgroup.ssoa.service.SystemPropertyService;
import co.id.fifgroup.ssoa.validation.SystemPropertyValidator;
import java.util.Calendar;
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


@VariableResolver(DelegatingVariableResolver.class)
public class SystemPropertyAddComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(SystemPropertyAddComposer.class);
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Textbox txtSystemPropertyCode;
	@Wire
	private Textbox txtSystemPropertyName;
	@Wire
	private Textbox txtSystemPropertyValue;
	@Wire
	private Button btnSave;
	@WireVariable(rewireOnActivate = true)
	private transient SystemPropertyService systemPropertyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private SystemProperty systemProperty;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if(arg.containsKey(SystemPropertyDTO.class.getName())) {
			SystemPropertyDTO systemPropertyDto = (SystemPropertyDTO) arg.get(SystemPropertyDTO.class.getName());
			systemProperty = systemPropertyServiceImpl.getSystemPropertyById(systemPropertyDto.getSystemPropertyCode());
			txtSystemPropertyCode.setValue(systemProperty.getSystemPropertyCode());
			txtSystemPropertyName.setValue(systemProperty.getSystemPropertyName());
			txtSystemPropertyValue.setValue(systemProperty.getSystemPropertyValue());
			txtSystemPropertyCode.setReadonly(true);
			txtSystemPropertyName.setDisabled(false);
			txtSystemPropertyValue.setDisabled(false);
		}
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			txtSystemPropertyCode.setDisabled(true);
			txtSystemPropertyName.setDisabled(true);
			txtSystemPropertyValue.setDisabled(true);
		}
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 3775551198508445205L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					System.out.println("msk Save");
					try {
						setSystemProperty();
						systemPropertyServiceImpl.save(systemProperty);
						params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						Executions.createComponents("~./hcms/ssoa/system_property.zul", getSelf().getParent(), params);
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
		ErrorMessageUtil.clearErrorMessage(txtSystemPropertyCode);
		ErrorMessageUtil.clearErrorMessage(txtSystemPropertyName);
		ErrorMessageUtil.clearErrorMessage(txtSystemPropertyValue);
		/*if(errors.containsKey(SystemPropertyValidator.SYSTEM_PROPERTY_CODE)) {
			ErrorMessageUtil.setErrorMessage(txtSystemPropertyCode, vex.getMessage(SystemPropertyValidator.SYSTEM_PROPERTY_CODE));				
		}
		if(errors.containsKey(SystemPropertyValidator.SYSTEM_PROPERTY_NAME)) {
			ErrorMessageUtil.setErrorMessage(txtSystemPropertyName, vex.getMessage(SystemPropertyValidator.SYSTEM_PROPERTY_NAME));
		}
		if(errors.containsKey(SystemPropertyValidator.SYSTEM_PROPERTY_VALUE)) {
			ErrorMessageUtil.setErrorMessage(txtSystemPropertyValue, vex.getMessage(SystemPropertyValidator.SYSTEM_PROPERTY_VALUE));
		}
		*/
	}

	@Listen ("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 5799520384125775408L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/ssoa/system_property.zul", getSelf().getParent(), params);
					getSelf().detach();
				}
			}
		});
	}
	
	private void setSystemProperty(){
		if(systemProperty == null) {
			systemProperty = new SystemProperty();
		}
		systemProperty.setSystemPropertyCode(txtSystemPropertyCode.getValue());
		systemProperty.setSystemPropertyName(txtSystemPropertyName.getValue());
		systemProperty.setSystemPropertyValue(txtSystemPropertyValue.getValue());
		
		Long userId =securityServiceImpl.getSecurityProfile().getUserId();
		
		systemProperty.setCreatedBy(userId);
		systemProperty.setCreationDate(new Date());
		
		systemProperty.setLastUpdateBy(userId);
		systemProperty.setLastUpdateDate(new Date());
		
		System.out.println("setCreatedBy=="+systemProperty.getCreatedBy());
		System.out.println("setUpdateBy=="+systemProperty.getLastUpdateBy());
	}
}