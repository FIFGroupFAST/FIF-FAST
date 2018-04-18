package co.id.fifgroup.ssoa.controller;


import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.ssoa.domain.Parameter;
import co.id.fifgroup.ssoa.domain.ParameterDetail;

import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.dto.ParameterDetailDTO;
import co.id.fifgroup.ssoa.service.ParameterService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


@VariableResolver(DelegatingVariableResolver.class)
public class ParameterDetailComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ParameterDetailComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient ParameterService parameterService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private ParameterDetailComposer composer = this;
	
	@Wire
	private TabularEntry<ParameterDetailDTO> parameterDetail;
	
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtCode;
	@Wire
	private Datebox dtbDateFrom;
	@Wire
	private DateBoxMax dtbDateTo;
	@Wire
	private Textbox txtDesc;
	@Wire
	private Radiogroup rdType;
	@Wire
	private Label lblErrorParameter;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnAddRow;
	@Wire
	private Button btnDelete;
	
	
	private ParameterDTO parameterDto;
	private ParameterDTO selected;
	private Parameter parameter;
	private List<ParameterDetailDTO> parameterDetailList;
	private FunctionPermission functionPermission;
	private boolean isEdit = false;
	
	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if(arg.containsKey("edit")){
			isEdit = true;
		}
		loadParameters();
		buildParameterDetail();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if (functionPermission.isCreateable()) {
			disableComponent(false);
		}else if (functionPermission.isEditable()) {
			disableComponent(false);
		}else{
			disableComponent(true);
		}
		
		if(isEdit){
			if(functionPermission.isEditable())
				disableComponent(false);
			else
				disableComponent(true);
		}
	}
	
	
	private void disableComponent(boolean disabled){
		/*btnSave.setDisabled(disabled);
		txtName.setDisabled(disabled);
		txtDesc.setDisabled(disabled);
		dtbDateFrom.setDisabled(disabled);
		dtbDateTo.setDisabled(disabled);
		for (Component comp : rdType.getChildren()) {
			Radio rd = (Radio) comp;
			rd.setDisabled(disabled);
		}
		btnAddRow.setDisabled(disabled);
		btnDelete.setDisabled(disabled);*/
	}
	
	
	private void loadParameters() {
		//dtbDateFrom.setFormat(FormatDateUI.getDateFormat());
		//dtbDateTo.setFormat(FormatDateUI.getDateFormat());
		selected = (ParameterDTO) arg.get("parameterDto");
		if(selected != null) {
			Messagebox.show("Change will affected after you restart your system","Attention!",1,org.zkoss.zul.Messagebox.EXCLAMATION);
			parameter = parameterService.getParameterById(selected.getParameterCode());
			parameterDto = modelMapper.map(parameter, ParameterDTO.class);
			parameterDetailList = parameterDto.getParameterDetailDto();
			parameterDetailList = parameterService.getParameterDetailDtoByParameterId(parameter.getParameterCode());
			txtName.setValue(parameter.getParameterName());
			txtName.setDisabled(true);
			txtCode.setValue(parameter.getParameterCode());
			txtCode.setDisabled(true);
			//dtbDateFrom.setDisabled(true);
		}
	}
	
	private void buildParameterDetail() {
		parameterDetail.setModel(getParameterDetailModel());
		parameterDetail.setItemRenderer(getListitemRenderer());
		parameterDetail.setValidationRule(getTabularValidationRule());
		parameterDetail.renderAll();
	}
	
	@Listen("onClick=radiogroup#rdType")
	public void onChangeExecutionType(){
		
		
	}
	
	@Listen("onClick=button#btnAddRow")
	public void addRow() {
		parameterDetail.setDataType(ParameterDetailDTO.class);
		parameterDetail.addRow();
	}
	
	@Listen("onClick=button#btnDelete")
	public void deleteRow() {
		/*if(isEdit){
			Messagebox.show(Labels.getLabel("sam.dataCanNotBeDeleted"), "Information", Messagebox.YES, Messagebox.INFORMATION);
			
		}else{*/
		parameterDetail.deleteRow();
		//}
		rebuildTabularEntryOnChangeTaskCollection();
	}
	
	private TabularValidationRule<ParameterDetailDTO> getTabularValidationRule() {
		return new TabularValidationRule<ParameterDetailDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(ParameterDetailDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	
	private ListModelList<ParameterDetailDTO> getParameterDetailModel() {
		if(parameterDetailList == null || parameterDetailList.size() < 1) {
			parameterDetailList = new ArrayList<ParameterDetailDTO>();
			parameterDetailList.add(new ParameterDetailDTO());
		}
		ListModelList<ParameterDetailDTO> model = new ListModelList<ParameterDetailDTO>(parameterDetailList);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<ParameterDetailDTO> getListitemRenderer() {
		return new SerializableListItemRenderer<ParameterDetailDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final ParameterDetailDTO data, int index)
					throws Exception {
				
				System.out.println("msk render");
				if(data == null){
					data.setMainTask(new ParameterDTO());
					data.setErrorTask(new ParameterDTO());
					data.setSuccessTask(new ParameterDTO());
				}
				
				ParameterDetail parameterDetail = data;
				
				new Listcell().setParent(item);
				//new IntboxListcell<ParameterDetail>(parameterDetail, parameterDetail.getSequence() == null ? 0 : parameterDetail.getSequence(), "sequence", false, 2, "75%").setParent(item);
				new TextboxListcell<ParameterDetail>(data, data.getLineNo(),"lineNo", false).setParent(item);
				new TextboxListcell<ParameterDetail>(data, data.getParameterDtlCode(), "parameterDtlCode", false).setParent(item);
				new TextboxListcell<ParameterDetail>(data, data.getParameterDtlName(), "parameterDtlName", false).setParent(item);
				
				/*Textbox code = new Textbox();
				code.setWidth("200%");
				createParamDetailCode(item, data, code);
				Textbox name = new Textbox();
				name.setWidth("200%");
				createParamDetailName(item, data, name);*/
				
				
				//createSuccessTaskListbox(item, data);
				//createErrorTaskListbox(item, data);				
			}
		};
	}
	
	private void createParamDetailCode(Listitem item, final ParameterDetailDTO data, Textbox code) {
		Listcell cell = new Listcell();
		/*if(data.getMainTask() != null)
			code.setValue(data.getMainTask().getParameterCode());*/
		code.setValue(data.getParameterDtlCode());
		code.setReadonly(false);
		code.setParent(cell);
		cell.setParent(item);
	}
	
	private void createParamDetailName(Listitem item, final ParameterDetailDTO data, Textbox name) {
		Listcell cell = new Listcell();
		/*if(data.getMainTask() != null)
			name.setValue(data.getMainTask().getParameterCode());*/
		name.setValue(data.getParameterDtlName());
		name.setReadonly(false);
		name.setParent(cell);
		cell.setParent(item);
	}
	
	private void createLineNo(Listitem item, final ParameterDetailDTO data, Textbox lineNo) {
		Listcell cell = new Listcell();
		/*if(data.getMainTask() != null)
			name.setValue(data.getMainTask().getParameterCode());*/
		lineNo.setValue(data.getLineNo());
		lineNo.setReadonly(false);
		lineNo.setParent(cell);
		cell.setParent(item);
	}
	
	
	public void rebuildTabularEntryOnChangeTaskCollection() {
		ListModelList<ParameterDetailDTO> model = new ListModelList<ParameterDetailDTO>(parameterDetail.getValue());
		model.setMultiple(true);
		parameterDetail.setModel(model);
	}
	
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		lblErrorParameter.setValue("");
		if(parameterDto == null){
			parameterDto = new ParameterDTO();
			parameter = new Parameter();
		}
		
		parameterDto.setParameterCode(txtCode.getValue());
		parameterDto.setParameterName(txtName.getValue());
		
		
		if(selected == null) {
			parameterDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			parameterDto.setCreationDate(new Date());
		} else {
			parameterDto.setCreatedBy(selected.getCreatedBy());
			parameterDto.setCreationDate(selected.getCreationDate());
		}
		parameterDto.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
		parameterDto.setLastUpdateDate(new Date());
		
		parameterDto.setParameterDetailDto(parameterDetail.getValue());
		
		
			clearErrorMessage();
//			if(parameterDetail.validate()) {
				Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {	
						int status = (int) event.getData();
						if(status == 16) {
							try {
								/*ParameterValidator parameterValidator = new ParameterValidator();
								parameterValidator.validate(parameterDto);
								
								if(validationTabularParameter() == false) {
									return;
								}*/
								parameterService.save(parameterDto);
								Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES, Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/ssoa/parameter_inquiry.zul", getSelf().getParent(), param);
								getSelf().detach();
							} catch (ValidationException e) {
								log.error(e.getMessage());
								showErrorMessage(e);
							}
						} else {
							return;
						}
					}
				});
//			}
		
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtName);
		ErrorMessageUtil.clearErrorMessage(txtCode);
		ErrorMessageUtil.clearErrorMessage(parameterDetail.getPreviousSibling());
	}
	
	private void showErrorMessage(ValidationException vex){
		Map<String, String> errors = vex.getConstraintViolations();
		/*if(errors.containsKey(ParameterValidator.TASK)) {
			if (parameterDetail.getPreviousSibling() instanceof Vlayout) {
				parameterDetail.getParent().removeChild(parameterDetail.getPreviousSibling());
			}
			Vlayout vlayout = new Vlayout();
			Label label = new Label();
			label.setValue(vex.getMessage(ParameterValidator.TASK));
			label.setStyle("color:red");
			vlayout.appendChild(label);
			parameterDetail.getParent().insertBefore(vlayout, parameterDetail);
		}
		
		if(errors.containsKey(ParameterValidator.CYCLIC_ERROR)) {
			if (parameterDetail.getPreviousSibling() instanceof Vlayout) {
				parameterDetail.getParent().removeChild(parameterDetail.getPreviousSibling());
			}
			Vlayout vlayout = new Vlayout();
			Label label = new Label();
			label.setValue("This set of Tasks will cause cyclic path.");
			label.setStyle("color:red");
			vlayout.appendChild(label);
			parameterDetail.getParent().insertBefore(vlayout, parameterDetail);
		}*/
		
		if(txtName.getValue().equalsIgnoreCase("")) {
			ErrorMessageUtil.setErrorMessage(txtName, "Task Runner name can't empty");
		}
		if(dtbDateFrom.getValue() == null) {
			ErrorMessageUtil.setErrorMessage(dtbDateFrom, "Date From can't empty");
		}
		if(txtDesc.getValue().equalsIgnoreCase("")) {
			ErrorMessageUtil.setErrorMessage(txtDesc, "Description can't empty");
		}
		if(rdType.getSelectedIndex() <= -1) {
			ErrorMessageUtil.setErrorMessage(rdType, "Type must be selected");
		}
		if(dtbDateTo.getValue() != null && dtbDateFrom.getValue() != null) {
			if(DateUtil.compareDate(dtbDateFrom.getValue(), dtbDateTo.getValue())) {
				ErrorMessageUtil.setErrorMessage(dtbDateTo, "Date To less than Date From");
				return;
			}
		}
	}	
	
	@Listen ("onClick = button#btnCancel")
	public void cancel() {
		Messagebox.show(Labels.getLabel("sam.cancelMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {	
				int status = (int) event.getData();
				if(status == 16) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/ssoa/parameter_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
	
}
