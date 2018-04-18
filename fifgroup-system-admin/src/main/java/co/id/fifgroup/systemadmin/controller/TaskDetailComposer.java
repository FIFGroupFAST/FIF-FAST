package co.id.fifgroup.systemadmin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.audit.AuditPerformer;
import co.id.fifgroup.audit.TrxType;
import co.id.fifgroup.audit.ActionType.Action;
import co.id.fifgroup.audit.ControlType.Control;
import co.id.fifgroup.audit.objectcopy.DeepCopy;
import co.id.fifgroup.basicsetup.domain.Printer;
import co.id.fifgroup.basicsetup.domain.PrinterExample;
import co.id.fifgroup.basicsetup.service.PrinterService;
import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.systemadmin.constant.OutputFileFormat;
import co.id.fifgroup.systemadmin.constant.TaskAfterCompleted;
import co.id.fifgroup.systemadmin.constant.TaskDataType;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.domain.ExecutableFile;
import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.dto.ExecutableFileDTO;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;
import co.id.fifgroup.systemadmin.service.TaskService;
import co.id.fifgroup.systemadmin.validation.TaskValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskDetailComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(TaskDetailComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient TaskService taskServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient PrinterService printerServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient AuditPerformer auditPerformer;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Textbox txtName;
	@Wire
	private Textbox txtDesc;
	@Wire
	private Radiogroup rdType;
	@Wire
	private CommonPopupBandbox bdnFileName;
	@Wire
	private CommonPopupBandbox bdnPrinter;
	@Wire
	private Textbox txtClass;
	@Wire
	private Listbox formatListbox;
	@Wire
	private Row classRow;
	@Wire
	private Row reportRow;
	@Wire
	private Button btnSave;

	@Wire
	private Radiogroup afterComplted;
	@Wire
	private Checkbox ckbStaticParam;
	private List<TaskParameterDTO> taskParameterList;
	
	private TaskDTO taskDto;
	private Task selectedTask;
	private FunctionPermission functionPermission;
	private TaskDTO previousObject;
	private Control control;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		loadParameters();
		setRadioTypeListener();
		buildExecutableFilePopup();
		buildOutputFileFormatListbox();
		buildPrinterPopup();
		//setIfEdit(arg.containsKey("edit"));
		disableOutput();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();

	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			txtName.setDisabled(true);
			txtDesc.setDisabled(true);
			Hbox hb = (Hbox) rdType.getChildren().get(0);
			for (Component comp : hb.getChildren()) {
				Radio rd = (Radio) comp;
				rd.setDisabled(true);
			}
			bdnFileName.setDisabled(true);
			txtClass.setDisabled(true);
			ckbStaticParam.setDisabled(true);
			formatListbox.setDisabled(true);
			bdnPrinter.setDisabled(true);
			btnSave.setDisabled(true);
		}
			
	}
	
	private void setIfEdit(boolean containsKey) {
		if(arg.containsKey("edit")){
			for(Radio rad : rdType.getItems()){
				rad.setDisabled(true);
			}
			bdnFileName.setDisabled(true);
			txtClass.setDisabled(true);
			ckbStaticParam.setDisabled(true);
			if(taskDto.getPrinterId() != null){
				formatListbox.setSelectedIndex(1);
				PrinterExample example = new PrinterExample();
				example.createCriteria().andPrinterIdEqualTo(taskDto.getPrinterId());
				bdnPrinter.setValue(printerServiceImpl.getPrinterByExample(example).get(0).getPrinterName());
			}
		}
	}

	private void disableOutput() {
		if (selectedTask == null || selectedTask.getTaskType().equals(TaskType.BATCH.toString())) {
			formatListbox.setDisabled(true);
			for (Radio radio : afterComplted.getItems()) {
				radio.setDisabled(true);
			}
			bdnPrinter.setDisabled(true);
		}
	}

	private void loadParameters() {
		selectedTask = (Task) arg.get("task");
		if(selectedTask != null) {
			taskDto = taskServiceImpl.getTaskById(selectedTask.getId());
			taskParameterList = taskDto.getTaskParameters();
			txtName.setValue(taskDto.getTaskName());
			txtDesc.setValue(taskDto.getDescription());
			rdType.setSelectedIndex(TaskType.getKey(taskDto.getTaskType()));
			if(bdnFileName.getValue().isEmpty()) {
				KeyValue keyValue = new KeyValue(taskDto.getExecutableFileId(), new File(taskDto.getExecutableFile().getExecutableFileName()).getName());
				bdnFileName.setRawValue(keyValue);
				bdnFileName.setValue(new File(taskDto.getExecutableFile().getExecutableFileName()).getName());
			} else {
//				taskDto.setExecutableFileId(executableFileId)
			}
			txtClass.setValue(taskDto.getClassName());
			ckbStaticParam.setChecked(taskDto.isHasStaticParams());
			//System.out.println("TaskAfterCompleted.valueOf(taskDto.getAfterCompleted()).getValue() " +TaskAfterCompleted.valueOf(taskDto.getAfterCompleted()).getValue());
			afterComplted.setSelectedIndex(TaskAfterCompleted.getKey(taskDto.getAfterCompleted()));
			//bdnPrinter.setValue(value);
			previousObject = (TaskDTO) DeepCopy.copy(taskDto);
			control = Control.UPDATE;
		}
	}
	
	@Listen("onClick=button#btnDefineParam")
	public void showDefineParametersPopup() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskParameters", taskParameterList);
		param.put("composer", this);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Window win = (Window) Executions.createComponents("~./hcms/systemadmin/lookup_define_task_paramerters.zul", null, param);
		win.doModal();
	}
	
	private void buildOutputFileFormatListbox() {
		ListModelList<OutputFileFormat> model = new ListModelList<OutputFileFormat>(OutputFileFormat.values());
		formatListbox.setModel(model);
		formatListbox.renderAll();
		formatListbox.setSelectedIndex(0);
		if(selectedTask != null){
			int idx = 0;
			for (OutputFileFormat outputFileFormat : model) {
				if(outputFileFormat.toString().equals(selectedTask.getOutputFormat()))
					formatListbox.setSelectedIndex(idx);
				idx++;
			}
		}
	}
	
	private void setRadioTypeListener() {
		if(rdType.getSelectedIndex() == 0){
			classRow.setVisible(true);
			reportRow.setVisible(false);
		}else{
			classRow.setVisible(false);
			reportRow.setVisible(true);
		}
			rdType.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

			/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {

				if(rdType.getSelectedIndex() == 1) {
					ckbStaticParam.setChecked(false);
					bdnFileName.setRawValue(null);
					classRow.setVisible(false);
					formatListbox.setDisabled(false);
					for (Radio radio : afterComplted.getItems()) {
						radio.setDisabled(false);
					}
					bdnPrinter.setDisabled(false);
					reportRow.setVisible(true);
					for (Component comp : afterComplted.getChildren()) {
						Radio rd = (Radio)comp;
						rd.setDisabled(false);
					}
					clearErrorMessage();
				} else {
					bdnFileName.setRawValue(null);
					classRow.setVisible(true);
					formatListbox.setDisabled(true);
					bdnPrinter.setDisabled(true);
					reportRow.setVisible(false);
					for (Component comp : afterComplted.getChildren()) {
						Radio rd = (Radio)comp;
						rd.setDisabled(true);
					}
					clearErrorMessage();

					boolean staticExist = true;
					int index = 0;
					while(staticExist){
						index = 0;
						staticExist=false;
						if (taskDto != null && taskDto.getTaskParameters() != null && !taskDto.getTaskParameters().isEmpty()){
							for(TaskParameterDTO taskParam : taskDto.getTaskParameters()){
								if(taskParam.getKey().contains("STATIC_")){
									taskDto.getTaskParameters().remove(index);
									staticExist= true;
									break;
								}
								index++;
							}
						}
					}
				
				}
			}
		});
	}
	
	private void buildExecutableFilePopup() {
		SerializableSearchDelegate<ExecutableFile> executableSearchDelegate = new SerializableSearchDelegate<ExecutableFile>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<ExecutableFile> findBySearchCriteria(
					String searchCriteria, int limit, int offset) {
				ExecutableFileDTO example = new ExecutableFileDTO();
				example.setExecutableFileName(searchCriteria);
				example.setTaskType(TaskType.getValue(rdType.getSelectedIndex()));
				return taskServiceImpl.getExecutableFileByExample(example, 0, GlobalVariable.getMaxRowPerPage());
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return 0;
			}

			@Override
			public void mapper(KeyValue keyValue, ExecutableFile data) {
				keyValue.setKey(data.getId());
				File file = new File(data.getExecutableFileName());
				String fileName = file.getName();
				keyValue.setValue(data.getExecutableFileName());
				keyValue.setDescription(fileName);
			}
		};
		bdnFileName.setSearchText("File Name");
		bdnFileName.setTitle("List of Executable Files");
		bdnFileName.setSearchDelegate(executableSearchDelegate);
	}
	
	private void buildPrinterPopup(){
		SerializableSearchDelegate<Printer> printerSearchDelegate = new SerializableSearchDelegate<Printer>() {

			

			private static final long serialVersionUID = 1L;

			@Override
			public List<Printer> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				PrinterExample example = new PrinterExample();
				example.createCriteria().andPrinterNameLikeInsensitive(searchCriteria);
				example.setOrderByClause("PRINTER_NAME ASC");
				return printerServiceImpl.getPrinterByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				PrinterExample example = new PrinterExample();
				example.createCriteria().andPrinterNameLikeInsensitive(searchCriteria);
				return printerServiceImpl.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, Printer data) {
				keyValue.setKey(data.getPrinterId());
				keyValue.setValue(data.getPrinterName());
				keyValue.setDescription(data.getPrinterName());
			}
		};
		bdnPrinter.setSearchText("Printer Name");
		bdnPrinter.setTitle("List of Printer");
		bdnPrinter.setSearchDelegate(printerSearchDelegate);
	}
	
	private void populateTask(){
		if(taskDto == null) {
			taskDto = new TaskDTO();
			control = Control.CREATE;
		}else
			control = Control.UPDATE;
		
		taskDto.setTaskName(txtName.getValue());
		taskDto.setDescription(txtDesc.getValue());
		taskDto.setTaskType(TaskType.getValue(rdType.getSelectedIndex()));
		KeyValue keyValueFilename = bdnFileName.getKeyValue();
		Object exFileName = bdnFileName.getValue();
		
		if(keyValueFilename !=null){
			List<ExecutableFile> executableFiles= taskServiceImpl.getExecutableFileByExecutableFileName("%"+exFileName+"%");
			taskDto.setExecutableFileId((Long) keyValueFilename.getKey());
			if(!executableFiles.isEmpty()){
				ExecutableFile exFile =  executableFiles.get(0);
			}
		}
		
		KeyValue fileNameKv = bdnFileName.getKeyValue();
		if(fileNameKv != null){
			taskDto.setExecutableFileId((Long)fileNameKv.getKey());
		}else
			taskDto.setExecutableFileId(null);
		
		if(rdType.getSelectedIndex() == 0){
			taskDto.setClassName(txtClass.getValue());
		}else{
			taskDto.setHasStaticParams(ckbStaticParam.isChecked());
			if(formatListbox.getSelectedItem() != null) {
				OutputFileFormat val = formatListbox.getSelectedItem().getValue();
				if(val.equals(OutputFileFormat.Select))
					taskDto.setOutputFormat(null);
				else
					taskDto.setOutputFormat(val.toString());
			}
			taskDto.setAfterCompleted(TaskAfterCompleted.getValue(afterComplted.getSelectedIndex()));
			if(bdnPrinter.getKeyValue() != null) {
				KeyValue keyValue = bdnPrinter.getKeyValue();
				taskDto.setPrinterId((Long) keyValue.getKey());
			}
			
			
		}
		
		if(selectedTask == null) { //new task
			
			if(ckbStaticParam.isChecked() && rdType.getSelectedIndex() == 1){
				//create static parameters
				if(taskParameterList == null)
					taskParameterList = new ArrayList<TaskParameterDTO>();
				taskParameterList.addAll(createStaticParameters());
			}
			
			taskDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			taskDto.setCreationDate(new Date());
		} else { //edit task
			
			if(!selectedTask.isHasStaticParams() && ckbStaticParam.isChecked() && rdType.getSelectedIndex() == 1){
				
				//create static parameters
				if(taskParameterList == null)
					taskParameterList = new ArrayList<TaskParameterDTO>();
				taskParameterList.addAll(createStaticParameters());
			}
			
			taskDto.setCreatedBy(selectedTask.getCreatedBy());
			taskDto.setCreationDate(selectedTask.getCreationDate());
		}

		taskDto.setLastUpdateDate(new Date());
		taskDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		
		
		
		taskDto.setTaskParameters(taskParameterList);
	}
	
	@Listen("onCheck = checkbox#ckbStaticParam")
	public void onCheck(){
		if(taskDto == null) {
			taskDto = new TaskDTO();
		}
		if(ckbStaticParam.isChecked() && rdType.getSelectedIndex() == 1){
			//create static parameters
			if(taskParameterList == null)
				taskParameterList = new ArrayList<TaskParameterDTO>();
			taskParameterList.addAll(createStaticParameters());
			taskDto.setTaskParameters(taskParameterList);
		}else if (!ckbStaticParam.isChecked() && rdType.getSelectedIndex() == 1){
			boolean staticExist = true;
			int index = 0;
			while(staticExist){
				index = 0;
				staticExist=false;
				for(TaskParameterDTO taskParam : taskDto.getTaskParameters()){
					if(taskParam.getKey().contains("STATIC_")){
						taskDto.getTaskParameters().remove(index);
						staticExist= true;
						break;
					}
					index++;
				}
			}
		}
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		populateTask();
			clearErrorMessage();

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
							taskServiceImpl.save(taskDto);
							auditPerformer.doAudit(UUID.randomUUID(), UUID.randomUUID(), previousObject, taskDto, 1, control, Action.SUBMIT, securityServiceImpl.getSecurityProfile().getUserId(), TrxType.TASK_SETUP.getValue(), taskDto.getId());
							Map<String, Object> param = new HashMap<String, Object>();
							param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
							Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES, Messagebox.INFORMATION);
							Executions.createComponents("~./hcms/systemadmin/task_inquiry.zul", getSelf().getParent(), param);
							getSelf().detach();
						} catch (ValidationException vex) {
							showErrorMessage(vex);
						} catch(Exception e){
							logger.error(e.getMessage(), e);
						}
					} else {
						return;
					}
				}
			});

		
	}
	
	private List<TaskParameterDTO> createStaticParameters(){
		List<TaskParameterDTO> params = new ArrayList<TaskParameterDTO>();
		TaskParameterDTO branchParam = new TaskParameterDTO();
		branchParam.setKey(StaticParameterKey.STATIC_PARAM_BRANCH.toString());
		branchParam.setDisplayName(StaticParameterKey.STATIC_PARAM_BRANCH.getValue());
		branchParam.setDataType(TaskDataType.LOOKUP.toString());
		branchParam.setLookupId(null); //Hard Code
		branchParam.setMandatory(false);
		branchParam.setStaticParam(true);
		
		TaskParameterDTO orgParam = new TaskParameterDTO();
		orgParam.setKey(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString());
		orgParam.setDisplayName(StaticParameterKey.STATIC_PARAM_ORGANIZATION.getValue());
		orgParam.setDataType(TaskDataType.LOOKUP.toString());
		orgParam.setLookupId(null); //Hard Code
		orgParam.setMandatory(false);
		orgParam.setStaticParam(true);
		
		TaskParameterDTO employeeParam = new TaskParameterDTO();
		employeeParam.setKey(StaticParameterKey.STATIC_PARAM_EMPLOYEE.toString());
		employeeParam.setDisplayName(StaticParameterKey.STATIC_PARAM_EMPLOYEE.getValue());
		employeeParam.setDataType(TaskDataType.LOOKUP.toString());
		employeeParam.setLookupId(null); //Hard Code
		employeeParam.setMandatory(false);
		employeeParam.setStaticParam(true);
		
		/*Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
		TaskParameterDTO employeeParamCommon = new TaskParameterDTO();
		employeeParamCommon.setKey(StaticParameterKey.STATIC_PARAM_EMPLOYEE_COMMON.toString());
		employeeParamCommon.setDisplayName(StaticParameterKey.STATIC_PARAM_EMPLOYEE_COMMON.getValue());
		employeeParamCommon.setDataType(TaskDataType.LOOKUP.toString());
		employeeParamCommon.setLookupId(null); //Hard Code
		employeeParamCommon.setMandatory(false);
		employeeParamCommon.setStaticParam(true);
		
		
		TaskParameterDTO branchParamCommon = new TaskParameterDTO();
		branchParamCommon.setKey(StaticParameterKey.STATIC_PARAM_BRANCH_COMMON.toString());
		branchParamCommon.setDisplayName(StaticParameterKey.STATIC_PARAM_BRANCH_COMMON.getValue());
		branchParamCommon.setDataType(TaskDataType.LOOKUP.toString());
		branchParamCommon.setLookupId(null); //Hard Code
		branchParamCommon.setMandatory(false);
		branchParamCommon.setStaticParam(true);
		
		
		/* End Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
		
		TaskParameterDTO withoutPosParam = new TaskParameterDTO();
		withoutPosParam.setKey(StaticParameterKey.STATIC_PARAM_WITHOUT_POS.toString());
		withoutPosParam.setDisplayName(StaticParameterKey.STATIC_PARAM_WITHOUT_POS.getValue());
		withoutPosParam.setDataType(TaskDataType.BOOLEAN.toString());
		withoutPosParam.setMandatory(false);
		withoutPosParam.setStaticParam(true);
		
		params.add(branchParam);
		params.add(orgParam);
		params.add(employeeParam);
		/*Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
		params.add(employeeParamCommon);
		params.add(branchParamCommon);
		/*Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
		params.add(withoutPosParam);
		return params;
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtName);
		ErrorMessageUtil.clearErrorMessage(txtDesc);
		ErrorMessageUtil.clearErrorMessage(bdnFileName);
		ErrorMessageUtil.clearErrorMessage(txtClass);
	}
	
	private void showErrorMessage(ValidationException vex) {
		ErrorMessageUtil.setErrorMessage(txtName, vex.getConstraintViolations().get(TaskValidator.TASK_NAME_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(txtDesc, vex.getConstraintViolations().get(TaskValidator.DESCRIPTION_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(bdnFileName, vex.getConstraintViolations().get(TaskValidator.EXECUTABLE_FILE_NAME_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(txtClass, vex.getConstraintViolations().get(TaskValidator.CLASS_NAME_NOT_EMPTY));
	}
	
	@Listen ("onClick = button#btnCancel")
	public void onCancel() {
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
					Executions.createComponents("~./hcms/systemadmin/task_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});

	}
		
	public void setTaskParameterValue(List<TaskParameterDTO> taskParameters){
		this.taskParameterList= taskParameters; 
	}
}
