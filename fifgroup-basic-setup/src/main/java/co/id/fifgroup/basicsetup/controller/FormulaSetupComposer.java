package co.id.fifgroup.basicsetup.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.common.BoyerMoore;
import co.id.fifgroup.basicsetup.common.FormulaParameterNavigationHandler;
import co.id.fifgroup.basicsetup.constant.FormulaReturnType;
import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.basicsetup.dto.FormulaDTO;
import co.id.fifgroup.basicsetup.service.FormulaParameterService;
import co.id.fifgroup.basicsetup.service.FormulaService;
import co.id.fifgroup.basicsetup.validation.FormulaValidator;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.EndDateValidator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.VersionValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class FormulaSetupComposer extends SelectorComposer<Window> implements FormulaParameterNavigationHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8201581223644158357L;

	private static Logger log = LoggerFactory
			.getLogger(FormulaInquiryComposer.class);

	@WireVariable
	private Map<String, Object> arg;
	@Wire
	private Textbox txtFormulaName;
	@Wire
	private Textbox txtDescription;
	@Wire
	private Datebox dtbDateFrom;
	@Wire
	private DateBoxMax dtbDateTo;
	@Wire
	private Listbox lstReturnType;
	@Wire
	private Listbox lstFormulaVersion;
	@Wire
	private Textbox txtFormula;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnDelete;
	@Wire
	private Button btnGetParameter;

	@WireVariable(rewireOnActivate = true)
	private transient FormulaService formulaServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient FormulaParameterService formulaParameterServiceImpl;

	private ListModelList<FormulaReturnType> listModelFormulaReturnTypes;
	private ListModelList<String> listModelNewFormulaVersion;
	private FormulaDTO formulaDto;
	private FunctionPermission functionPermission;

	private int currentPositionFormulaText;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		txtFormula.setWidgetListener(Events.ON_CLICK, "zAu.send(new zk.Event(this, \"onCursorPosition\", zk(this.$n()).getSelectionRange()[1]));");
		init();
		if (arg.containsKey(FormulaDTO.class.getName())) {
			setComponentValue();
		}
		
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();

	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			txtFormulaName.setDisabled(true);
			lstReturnType.setDisabled(true);
			txtDescription.setDisabled(true);
			txtFormula.setDisabled(true);
			lstFormulaVersion.setDisabled(true);
			dtbDateFrom.setDisabled(true);
			dtbDateTo.setDisabled(true);
			btnGetParameter.setDisabled(true);
		}
			
	}


	@Listen("onCursorPosition = #txtFormula")
	public void onCursorPosition(Event evt) {
		currentPositionFormulaText = (int) evt.getData();
	}
	
	private void setComponentValue() {
		formulaDto = ((FormulaDTO) arg.get(FormulaDTO.class.getName()));
		
		FormulaDTO formulaDtoCurrent = formulaServiceImpl.getCurrentFormulaDto(formulaDto.getId());
		
		if(formulaDtoCurrent == null) {
			formulaDto = formulaServiceImpl.getLastFormulaDto(formulaDto.getId());
		} else {
			formulaDto = formulaDtoCurrent;
		}
		txtFormulaName.setValue(formulaDto.getFormulaName());
		for(FormulaReturnType formulaReturnType : listModelFormulaReturnTypes) {
			if(formulaDto.getReturnType().equals(formulaReturnType.toString())) {
				listModelFormulaReturnTypes.addToSelection(formulaReturnType);
				break;
			}
		}
		txtDescription.setValue(formulaDto.getDescription());
		txtFormula.setValue(formulaDto.getFormula());
		List<Integer> versionsNumber = formulaServiceImpl.findVersionsById(formulaDto.getId());
		for(int i = 0; i < versionsNumber.size(); i++) {
			listModelNewFormulaVersion.add(i, String.valueOf(versionsNumber.get(i)));
		}
		for(String version : listModelNewFormulaVersion) {
			if(version.equals(String.valueOf(formulaDto.getVersionNumber()))) {
				listModelNewFormulaVersion.addToSelection(version);
				break;
			}
		}
		dtbDateFrom.setValue(formulaDto.getDateFrom());
		dtbDateTo.setValue(formulaDto.getDateTo());
		txtFormulaName.setDisabled(true);
		lstReturnType.setDisabled(true);
		txtDescription.setDisabled(true);
		if(formulaDto.isFuture()) {
			btnDelete.setDisabled(false);
		} else if(formulaDto.isCurrent()) {
			if(formulaServiceImpl.isHaveFuture(formulaDto.getId())) {
				disableCurrentVersionHavingFutureVersion();
			} else {
				disableCurrentVersion();
			}
		} else {
			disablePastVersion();
		}
	}

	private void disableCurrentVersionHavingFutureVersion() {
		txtFormula.setDisabled(true);
		dtbDateFrom.setDisabled(true);
		btnGetParameter.setDisabled(true);
		dtbDateFrom.setDisabled(true);
		dtbDateTo.setDisabled(true);
		btnSave.setDisabled(true);
		btnDelete.setDisabled(true);
	}
	
	private void disableCurrentVersion() {
		txtFormula.setDisabled(true);
		dtbDateFrom.setDisabled(true);
		btnGetParameter.setDisabled(true);
		dtbDateFrom.setDisabled(true);
		dtbDateTo.setDisabled(false);
		btnDelete.setDisabled(true);
		btnSave.setDisabled(false);
	}
	
	private void disablePastVersion() {
		txtFormula.setDisabled(true);
		dtbDateFrom.setDisabled(true);
		btnGetParameter.setDisabled(true);
		dtbDateFrom.setDisabled(true);
		dtbDateTo.setDisabled(true);
		btnSave.setDisabled(true);
		btnDelete.setDisabled(true);
	}
	
	private void init() {
		btnDelete.setDisabled(true);
		initReturnType();
		initFormulaVersion();
	}

	private void initFormulaVersion() {
		listModelNewFormulaVersion = new ListModelList<String>();
		listModelNewFormulaVersion.add(Labels.getLabel("common.create"));
		lstFormulaVersion.setModel(listModelNewFormulaVersion);
		lstFormulaVersion.renderAll();
		listModelNewFormulaVersion.addToSelection(Labels.getLabel("common.create"));
	}

	private void initReturnType() {
		listModelFormulaReturnTypes = new ListModelList<FormulaReturnType>(FormulaReturnType.values());
		lstReturnType.setModel(listModelFormulaReturnTypes);
		lstReturnType.renderAll();
		listModelFormulaReturnTypes.addToSelection(FormulaReturnType.SELECT);

	}

	@Listen("onSelect = #lstFormulaVersion")
	public void onSelectFormulaVersion() {
		clearErrorMessage();
		if (lstFormulaVersion.getSelectedItem().getValue().toString().equalsIgnoreCase(Labels.getLabel("common.create"))) {
			if(formulaServiceImpl.getLastFormulaDto(formulaDto.getId()).isFuture()) {
				Messagebox.show(Labels.getLabel("common.cannotCreateFutureExist"));
				init();
				setComponentValue();
				return;
			}
			formulaDto = formulaServiceImpl.getFormulaDtoByIdAndVersionNumber(formulaDto.getId(), Integer.parseInt(listModelNewFormulaVersion.get(listModelNewFormulaVersion.getSize() - 2)));
			txtFormula.setValue(formulaDto.getFormula());
			txtFormula.setDisabled(false);
			dtbDateFrom.setDisabled(false);
			dtbDateTo.setDisabled(false);
			dtbDateFrom.setValue(DateUtil.truncate(DateUtil.add(new Date(), Calendar.DATE, 1)));
			dtbDateTo.setValue(DateUtil.truncate(DateUtil.MAX_DATE));
			btnDelete.setDisabled(true);
			btnGetParameter.setDisabled(false);
			btnSave.setDisabled(false);
			formulaDto.setVersionId(null);
		} else{
			int selectedVersion = Integer.parseInt(lstFormulaVersion.getSelectedItem().getValue().toString());
			formulaDto = formulaServiceImpl.getFormulaDtoByIdAndVersionNumber(formulaDto.getId(), selectedVersion);
			txtFormulaName.setValue(formulaDto.getFormulaName());
			for(FormulaReturnType formulaReturnType : listModelFormulaReturnTypes) {
				if(formulaDto.getReturnType().equals(formulaReturnType.toString())) {
					listModelFormulaReturnTypes.addToSelection(formulaReturnType);
					break;
				}
			}
			txtDescription.setValue(formulaDto.getDescription());
			txtFormula.setValue(formulaDto.getFormula());
			dtbDateFrom.setValue(formulaDto.getDateFrom());
			dtbDateTo.setValue(formulaDto.getDateTo());
			if(formulaDto.isCurrent()) {
				if(formulaServiceImpl.isHaveFuture(formulaDto.getId())) {
					disableCurrentVersionHavingFutureVersion();
				} else {
					disableCurrentVersion();
				}
			} else if(formulaDto.isFuture()) {
				txtFormula.setDisabled(false);
				btnGetParameter.setDisabled(false);
				dtbDateFrom.setDisabled(false);
				dtbDateTo.setDisabled(false);
				btnSave.setDisabled(false);
				btnDelete.setDisabled(false);
			} else {
				disablePastVersion();
			}
		}

	}

	@Listen("onClick = button#btnGetParameter")
	public void onGetParameter() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(FormulaParameterNavigationHandler.class.getName(), this);
		Window winFormulaGetParameter = (Window) Executions.createComponents("~./hcms/basic-setup/formula_get_parameter.zul", null, params);
		winFormulaGetParameter.doModal();
	}

	@Listen("onClick = button#btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2455060340739016313L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						setFormulaDto();
						formulaServiceImpl.save(formulaDto);
						Map<String, Object> param = new HashMap<String, Object>();
						param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						Executions.createComponents("~./hcms/basic-setup/formula_inquiry.zul", getSelf().getParent(), param);
						getSelf().detach();

					} catch (ValidationException vex) {
						showErrorMessage(vex);
						log.error(vex.getAllMessages());
					} catch (Exception e) {
						Messagebox.show("System Error");
						log.error(e.getMessage(), e);
					}
				}
			}
			
		});
	}

	private void setFormulaDto() {
		if (formulaDto == null) {
			formulaDto = new FormulaDTO();
		}
		FormulaReturnType returnType = lstReturnType.getSelectedItem().getValue();
		formulaDto.setReturnType(returnType.equals(FormulaReturnType.SELECT) ? null : returnType.toString());
		formulaDto.setFormulaName(txtFormulaName.getValue());
		formulaDto.setFormula(txtFormula.getValue());
		formulaDto.setDescription(txtDescription.getValue());
		formulaDto.setDateFrom(dtbDateFrom.getValue() == null ? null : DateUtils.truncate(dtbDateFrom.getValue(), Calendar.DATE));
		formulaDto.setDateTo(DateUtils.truncate(dtbDateTo.getValue(), Calendar.DATE));
		formulaDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		formulaDto.setCreationDate(new Date());
		formulaDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile()
				.getUserId());
		formulaDto.setLastUpdateDate(new Date());
		setFormulaVersionParam();
	}
	
	private void setFormulaVersionParam() {
		List<FormulaParameter> formulaParameters = formulaParameterServiceImpl.getFormulaParameterByExample(null);
		List<FormulaParameter> listFormulaParameter = new ArrayList<FormulaParameter>();
		BoyerMoore boyerMoore = new BoyerMoore();
		for(FormulaParameter formulaParameter : formulaParameters) {
			if(boyerMoore.isPatternInText(formulaParameter.getParameterName(), txtFormula.getValue())) {
				listFormulaParameter.add(formulaParameterServiceImpl.getFormulaParameterByPrimaryKey(formulaParameter.getFormulaParameterId()));
			}
		}
		formulaDto.setListFormulaParameter(listFormulaParameter);
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtFormulaName);
		ErrorMessageUtil.clearErrorMessage(dtbDateFrom);
		ErrorMessageUtil.clearErrorMessage(dtbDateTo);
		ErrorMessageUtil.clearErrorMessage(lstReturnType);
		ErrorMessageUtil.clearErrorMessage(lstFormulaVersion);
		ErrorMessageUtil.clearErrorMessage(txtFormula);
	}
	
	private void showErrorMessage(ValidationException vex) {
		Map<String, String> errors = vex.getConstraintViolations();
		clearErrorMessage();
		
		if (errors.containsKey(FormulaValidator.FORMULA_NAME)) {
			ErrorMessageUtil.setErrorMessage(txtFormulaName,
					vex.getMessage(FormulaValidator.FORMULA_NAME));
		}
		if (errors.containsKey(FormulaValidator.RETURN_TYPE)) {
			ErrorMessageUtil.setErrorMessage(lstReturnType,
					vex.getMessage(FormulaValidator.RETURN_TYPE));
		}
		if(errors.containsKey(VersionValidator.DATE_FROM)) {
			ErrorMessageUtil.setErrorMessage(dtbDateFrom, vex.getMessage(VersionValidator.DATE_FROM));
		}
		if(errors.containsKey(EndDateValidator.DATE_TO)) {
			ErrorMessageUtil.setErrorMessage(dtbDateTo, vex.getMessage(EndDateValidator.DATE_TO));
		}
		if (errors.containsKey(FormulaValidator.VERSION_NUMBER)) {
			ErrorMessageUtil.setErrorMessage(lstFormulaVersion,
					vex.getMessage(FormulaValidator.VERSION_NUMBER));
		}
		if (errors.containsKey(FormulaValidator.FORMULA)) {
			ErrorMessageUtil.setErrorMessage(txtFormula,
					vex.getMessage(FormulaValidator.FORMULA));
		}

		if(errors.containsKey(FormulaValidator.GROOVY_ERROR)) {
			Messagebox.show(vex.getMessage(FormulaValidator.GROOVY_ERROR));
		}
	}

	@Listen("onClick = button#btnDelete")
	public void onDelete() {
		Messagebox.show(Labels.getLabel("common.confirmationDelete"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5375442560596126347L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					formulaServiceImpl.delete(formulaDto);
					Messagebox.show(Labels.getLabel("common.dataHasBeenDeleted"));
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/basic-setup/formula_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				}
			}
			
		});
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
					Executions.createComponents("~./hcms/basic-setup/formula_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				}
			}
		});
	}

	@Override
	public void setFormulaParameter(FormulaParameter formulaParameter) {
		String begin = txtFormula.getValue().substring(0, currentPositionFormulaText);
		String end = "";
		if(currentPositionFormulaText < txtFormula.getValue().length()) {
			end = txtFormula.getValue().substring(currentPositionFormulaText, txtFormula.getValue().length());
		}
		String newStr = begin + formulaParameter.getParameterName() + end;
		txtFormula.setValue(newStr);
	}

}
