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

import co.id.fifgroup.basicsetup.constant.FormulaParameterReturnType;
import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.basicsetup.dto.FormulaParameterDTO;
import co.id.fifgroup.basicsetup.service.FormulaParameterService;
import co.id.fifgroup.basicsetup.validation.FormulaParameterValidator;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;

@VariableResolver(DelegatingVariableResolver.class)
public class FormulaParameterSetupComposer extends SelectorComposer<Window> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5932838440890678918L;

	private static Logger log = LoggerFactory
			.getLogger(FormulaInquiryComposer.class);

	@WireVariable
	private Map<String, Object> arg;
	@Wire
	private Textbox txtParameterName;
	@Wire
	private Listbox lstReturnType;
	@Wire
	private Textbox txtDescription;
	@Wire
	private Textbox txtParameterFunction;
	@Wire
	private Button btnSave;
	
	@WireVariable(rewireOnActivate=true)
	private transient FormulaParameterService formulaParameterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private FormulaParameter formulaParameter;

	private ListModelList<FormulaParameterReturnType> listModelFormulaParameterReturnTypes;
	private FunctionPermission functionPermission;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		setParameter();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	

	private void initFunctionSecurity(){
		if(!functionPermission.isDeletable()){
			btnSave.setDisabled(true);
			txtParameterName.setDisabled(true);
			txtDescription.setDisabled(true);
			txtParameterFunction.setDisabled(true);
			lstReturnType.setDisabled(true);
		}
			
	}
	
	private void setParameter() {
		if(arg.containsKey(FormulaParameterDTO.class.getName())) {
			FormulaParameterDTO formulaParameterDto = (FormulaParameterDTO) arg.get(FormulaParameterDTO.class.getName());
			formulaParameter = formulaParameterServiceImpl.getFormulaParameterByPrimaryKey(formulaParameterDto.getFormulaParameterId());
			txtParameterName.setValue(formulaParameter.getParameterName());
			for(FormulaParameterReturnType formulaParameterReturnType : listModelFormulaParameterReturnTypes) {
				if(formulaParameterReturnType.toString().equals(formulaParameter.getReturnType())) {
					listModelFormulaParameterReturnTypes.addToSelection(formulaParameterReturnType);
					break;
				}
			}
			txtDescription.setValue(formulaParameter.getDescription());
			txtParameterFunction.setValue(formulaParameter.getParameterFunction());
			txtParameterName.setDisabled(true);
			lstReturnType.setDisabled(true);
		}
	}
	
	private void init() {
		initFormulaParameterReturnType();
		
	}

	private void initFormulaParameterReturnType() {
		listModelFormulaParameterReturnTypes = new ListModelList<FormulaParameterReturnType>(FormulaParameterReturnType.values());
		lstReturnType.setModel(listModelFormulaParameterReturnTypes);
		lstReturnType.renderAll();
		listModelFormulaParameterReturnTypes.addToSelection(FormulaParameterReturnType.SELECT);
	}

	@Listen("onClick = button#btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4224735423653200522L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						clearErrorMessage();
						setFormulaParameter();
						formulaParameterServiceImpl.save(formulaParameter);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						Map<String, Object> param = new HashMap<String, Object>();
						param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
						Executions.createComponents("~./hcms/basic-setup/formula_parameter_inquiry.zul", getSelf().getParent(), param);
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
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtParameterName);
		ErrorMessageUtil.clearErrorMessage(lstReturnType);
		ErrorMessageUtil.clearErrorMessage(txtParameterFunction);
	}
	
	private void showErrorMessage(ValidationException vex) {
		Map<String, String> errors = vex.getConstraintViolations();
		if(errors.containsKey(FormulaParameterValidator.PARAMETER_NAME)) {
			ErrorMessageUtil.setErrorMessage(txtParameterName, vex.getMessage(FormulaParameterValidator.PARAMETER_NAME));
		}
		if(errors.containsKey(FormulaParameterValidator.RETURN_TYPE)) {
			ErrorMessageUtil.setErrorMessage(lstReturnType, vex.getMessage(FormulaParameterValidator.RETURN_TYPE));
		}
		if(errors.containsKey(FormulaParameterValidator.PARAMETER_FUNCTION)) {
			ErrorMessageUtil.setErrorMessage(txtParameterFunction, vex.getMessage(FormulaParameterValidator.PARAMETER_FUNCTION));
		}
		if(errors.containsKey(FormulaParameterValidator.GROOVY_ERROR)) {
			Messagebox.show(vex.getMessage(FormulaParameterValidator.GROOVY_ERROR));
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
					Executions.createComponents("~./hcms/basic-setup/formula_parameter_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				}
			}
		});
	}

	private void setFormulaParameter() {
		if(formulaParameter == null) {
			formulaParameter = new FormulaParameter();
		}
		formulaParameter.setParameterName(txtParameterName.getValue());
		FormulaParameterReturnType formulaParameterReturnType = (FormulaParameterReturnType) lstReturnType.getSelectedItem().getValue();
		formulaParameter.setReturnType(formulaParameterReturnType.equals(FormulaParameterReturnType.SELECT) ? null : formulaParameterReturnType.toString());
		formulaParameter.setDescription(txtDescription.getValue());
		formulaParameter.setParameterFunction(txtParameterFunction.getValue());
		if(formulaParameter.getFormulaParameterId() == null) {
			formulaParameter.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			formulaParameter.setCreationDate(new Date());
		}
		formulaParameter.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		formulaParameter.setLastUpdateDate(new Date());
	}
	
}
