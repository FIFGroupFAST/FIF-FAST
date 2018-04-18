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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.constant.GlobalSettingDataType;
import co.id.fifgroup.basicsetup.domain.GlobalSetting;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.basicsetup.service.GlobalSettingService;
import co.id.fifgroup.basicsetup.validation.GlobalSettingValidator;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;

@VariableResolver(DelegatingVariableResolver.class)
public class GlobalSettingDetailComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(GlobalSettingDetailComposer.class);
	
	@WireVariable
	private Map<String, Object> arg;
	
	@Wire
	private Textbox txtCode;
	@Wire
	private Textbox txtDesc;
	@Wire
	private Listbox lstDataType;
	@Wire
	private Textbox txtValue;
	@Wire
	private Button btnSave;
	
	@WireVariable(rewireOnActivate=true)
	private transient GlobalSettingService globalSettingServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private GlobalSetting globalSetting;
	private ListModelList<GlobalSettingDataType> listModelDataType;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		loadDataType();
		if(arg.containsKey(GlobalSettingDTO.class.getName())) {
			globalSetting = ((GlobalSettingDTO) arg.get(GlobalSettingDTO.class.getName())).getGlobalSetting();
			txtCode.setValue(globalSetting.getCode());
			txtDesc.setValue(globalSetting.getDescription());
			ListModelList<Object> selectedDataType = (ListModelList<Object>) lstDataType.getModel();
			selectedDataType.addToSelection(globalSetting.getDataType());
			for(int i = 0; i < listModelDataType.getSize(); i++) {
				if(listModelDataType.get(i).toString().equals(globalSetting.getDataType())) {
					lstDataType.setSelectedIndex(i);
					break;
				}
			}
			txtValue.setValue(globalSetting.getValue());
			txtCode.setDisabled(true);
			lstDataType.setDisabled(true);
		}
		
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			txtCode.setDisabled(true);
			txtDesc.setDisabled(true);
			lstDataType.setDisabled(true);
			txtValue.setDisabled(true);
		}
			
	}
	
	private void loadDataType() {
		listModelDataType = new ListModelList<GlobalSettingDataType>(GlobalSettingDataType.values());
		lstDataType.setModel(listModelDataType);
		lstDataType.renderAll();
		listModelDataType.addToSelection(GlobalSettingDataType.SELECT);
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -9031575440160630594L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						setGlobalSetting();
						globalSettingServiceImpl.save(globalSetting);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						Map<String, Object> param = new HashMap<String, Object>();
						param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
						Executions.createComponents("~./hcms/basic-setup/global_setting_inquiry.zul", getSelf().getParent(), param);
						getSelf().detach();
					} catch (ValidationException e) {
						showErrorMessage(e);
						log.error(e.getMessage());
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
		ErrorMessageUtil.clearErrorMessage(txtCode);
		ErrorMessageUtil.clearErrorMessage(txtDesc);
		ErrorMessageUtil.clearErrorMessage(lstDataType);
		ErrorMessageUtil.clearErrorMessage(txtValue);
		if(errors.containsKey(GlobalSettingValidator.CODE)) {
			ErrorMessageUtil.setErrorMessage(txtCode, vex.getMessage(GlobalSettingValidator.CODE));
		}
		if(errors.containsKey(GlobalSettingValidator.DESCRIPTION)) {
			ErrorMessageUtil.setErrorMessage(txtDesc, vex.getMessage(GlobalSettingValidator.DESCRIPTION));
		}
		if(errors.containsKey(GlobalSettingValidator.DATA_TYPE)) {
			ErrorMessageUtil.setErrorMessage(lstDataType, vex.getMessage(GlobalSettingValidator.DATA_TYPE));
		}
		if(errors.containsKey(GlobalSettingValidator.VALUE)) {
			ErrorMessageUtil.setErrorMessage(txtValue, vex.getMessage(GlobalSettingValidator.VALUE));
		}
	}
	
	private void setGlobalSetting() {
		if(globalSetting == null) {
			globalSetting = new GlobalSetting();
		}
		globalSetting.setCode(txtCode.getValue().trim());
		globalSetting.setDescription(txtDesc.getValue().trim());
		GlobalSettingDataType dataType = lstDataType.getSelectedItem().getValue().equals(GlobalSettingDataType.SELECT) ? null : (GlobalSettingDataType) lstDataType.getSelectedItem().getValue();
		globalSetting.setDataType(dataType == null ? null : dataType.toString());
		globalSetting.setValue(txtValue.getValue().trim());
		if(globalSetting.getGlobalSettingId() == null) {
			globalSetting.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			globalSetting.setCreationDate(new Date());			
		}
		globalSetting.setLastUpdateDate(new Date());
		globalSetting.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
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
					Executions.createComponents("~./hcms/basic-setup/global_setting_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				}
			}
		});
	}

}
