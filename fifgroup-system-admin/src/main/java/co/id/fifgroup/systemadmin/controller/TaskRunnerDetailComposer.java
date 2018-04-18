package co.id.fifgroup.systemadmin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.tabularentry.IntboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.systemadmin.constant.TaskExecutionType;
import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.domain.TaskExample;
import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.domain.TaskRunnerDetail;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDetailDTO;
import co.id.fifgroup.systemadmin.service.TaskRunnerService;
import co.id.fifgroup.systemadmin.service.TaskService;
import co.id.fifgroup.systemadmin.validation.TaskRunnerValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskRunnerDetailComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TaskRunnerDetailComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient TaskService taskServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient TaskRunnerService taskRunnerService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private TaskRunnerDetailComposer composer = this;
	
	@Wire
	private TabularEntry<TaskRunnerDetailDTO> taskRunnerDetail;
	
	@Wire
	private Textbox txtName;
	@Wire
	private Datebox dtbDateFrom;
	@Wire
	private DateBoxMax dtbDateTo;
	@Wire
	private Textbox txtDesc;
	@Wire
	private Radiogroup rdType;
	@Wire
	private Label lblErrorTaskRunner;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnAddRow;
	@Wire
	private Button btnDelete;
	
	
	private TaskRunnerDTO taskRunnerDto;
	private TaskRunnerDTO selected;
	private TaskRunner taskRunner;
	private List<TaskRunnerDetailDTO> taskRunnerDetailList;
	private FunctionPermission functionPermission;
	private boolean isEdit = false;
	
	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if(arg.containsKey("edit")){
			isEdit = true;
		}
		loadParameters();
		buildTaskrunnerDetail();
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
		btnSave.setDisabled(disabled);
		txtName.setDisabled(disabled);
		txtDesc.setDisabled(disabled);
		dtbDateFrom.setDisabled(disabled);
		dtbDateTo.setDisabled(disabled);
		for (Component comp : rdType.getChildren()) {
			Radio rd = (Radio) comp;
			rd.setDisabled(disabled);
		}
		btnAddRow.setDisabled(disabled);
		btnDelete.setDisabled(disabled);
	}
	
	
	private void loadParameters() {
		dtbDateFrom.setFormat(FormatDateUI.getDateFormat());
		dtbDateTo.setFormat(FormatDateUI.getDateFormat());
		selected = (TaskRunnerDTO) arg.get("taskRunnerDto");
		if(selected != null) {
//			Messagebox.show("Please note that updating Task Runner Detail won't make effect to the task(s) which is already run, until it is shuts down.","Attention!",1,org.zkoss.zul.Messagebox.EXCLAMATION);
			Messagebox.show("Change will affected after you restart your system","Attention!",1,org.zkoss.zul.Messagebox.EXCLAMATION);
			taskRunner = taskRunnerService.getTaskRunnerById(selected.getId());
			taskRunnerDto = modelMapper.map(taskRunner, TaskRunnerDTO.class);
			taskRunnerDetailList = taskRunnerDto.getTaskRunnerDetailDto();
			taskRunnerDetailList = taskRunnerService.getTaskRunnerDetailDtoByTaskRunnerId(taskRunner.getId());
			txtName.setValue(taskRunner.getTaskRunnerName());
			txtName.setDisabled(true);
			txtDesc.setValue(taskRunner.getDescription());
			rdType.setSelectedIndex(TaskExecutionType.getKey(taskRunner.getExecutionType()));
			dtbDateFrom.setValue(taskRunner.getDateFrom());
			dtbDateTo.setValue(taskRunner.getDateTo());
			dtbDateFrom.setDisabled(true);
		}
	}
	
	private void buildTaskrunnerDetail() {
		taskRunnerDetail.setModel(getTaskRunnerDetailModel());
		taskRunnerDetail.setItemRenderer(getListitemRenderer());
		taskRunnerDetail.setValidationRule(getTabularValidationRule());
		taskRunnerDetail.renderAll();
	}
	
	@Listen("onClick=radiogroup#rdType")
	public void onChangeExecutionType(){
		
		if(rdType.getSelectedIndex() == TaskExecutionType.PARALLEL.getKey()) {
			for (Component comp : taskRunnerDetail.getChildren()) {
				if(comp instanceof Listitem){
					
					Listcell scCell = (Listcell) comp.getChildren().get(4);
					Listbox successListbox = (Listbox) scCell.getFirstChild();
					successListbox.setDisabled(true);
					
					Listcell erCell = (Listcell) comp.getChildren().get(5);
					Listbox errorListbox = (Listbox) erCell.getFirstChild();
					errorListbox.setDisabled(true);
				}
			}
		} else {
			for (Component comp : taskRunnerDetail.getChildren()) {
				if(comp instanceof Listitem){
					
					Listcell scCell = (Listcell) comp.getChildren().get(4);
					Listbox successListbox = (Listbox) scCell.getFirstChild();
					successListbox.setDisabled(false);
					
					Listcell erCell = (Listcell) comp.getChildren().get(5);
					Listbox errorListbox = (Listbox) erCell.getFirstChild();
					errorListbox.setDisabled(false);
				}
			}
		}
	}
	
	@Listen("onClick=button#btnAddRow")
	public void addRow() {
		taskRunnerDetail.setDataType(TaskRunnerDetailDTO.class);
		taskRunnerDetail.addRow();
	}
	
	@Listen("onClick=button#btnDelete")
	public void deleteRow() {
		if(isEdit){
			Messagebox.show(Labels.getLabel("sam.dataCanNotBeDeleted"), "Information", Messagebox.YES, Messagebox.INFORMATION);
			
		}else{
		taskRunnerDetail.deleteRow();
		}
		rebuildTabularEntryOnChangeTaskCollection();
	}
	
	private TabularValidationRule<TaskRunnerDetailDTO> getTabularValidationRule() {
		return new TabularValidationRule<TaskRunnerDetailDTO>() {

			@Override
			public boolean validate(TaskRunnerDetailDTO data, List<String> errorRow) {
				if(data.getTaskId() == null)
					errorRow.add("Task cannot be empty");
				
				if(errorRow.size() > 0)	
					return false;
				return true;
			}
		};
	}
	
	//ok
	private List<TaskDTO> getSelectedTasks() {
		List<TaskDTO> tasks = new ArrayList<TaskDTO>();
		for (TaskRunnerDetailDTO taskRunnerDetailDto : taskRunnerDetail.getValue()) {
			if(taskRunnerDetailDto.getTaskId() != null){
				TaskDTO task = new TaskDTO();
				task.setId(taskRunnerDetailDto.getTaskId());
				task.setTaskName(taskRunnerDetailDto.getMainTask().getTaskName());
				tasks.add(task);
			}
		}
		TaskDTO endOfTask = new TaskDTO();
		endOfTask.setTaskName("End of Task");
		endOfTask.setId(-1L);
		tasks.add(endOfTask);
		return tasks;
	}
	
	private ListModelList<TaskRunnerDetailDTO> getTaskRunnerDetailModel() {
		if(taskRunnerDetailList == null || taskRunnerDetailList.size() < 1) {
			taskRunnerDetailList = new ArrayList<TaskRunnerDetailDTO>();
			taskRunnerDetailList.add(new TaskRunnerDetailDTO());
		}
		ListModelList<TaskRunnerDetailDTO> model = new ListModelList<TaskRunnerDetailDTO>(taskRunnerDetailList);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<TaskRunnerDetailDTO> getListitemRenderer() {
		return new SerializableListItemRenderer<TaskRunnerDetailDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final TaskRunnerDetailDTO data, int index)
					throws Exception {
				
				if(data == null){
					data.setMainTask(new TaskDTO());
					data.setErrorTask(new TaskDTO());
					data.setSuccessTask(new TaskDTO());
				}
				
				TaskRunnerDetail taskRunnerDetail = data;
				
				new Listcell().setParent(item);
				new IntboxListcell<TaskRunnerDetail>(taskRunnerDetail, taskRunnerDetail.getSequence() == null ? 0 : taskRunnerDetail.getSequence(), "sequence", false, 2, "75%").setParent(item);
				Textbox desc = new Textbox();
				desc.setWidth("90%");
				createTaskBandbox(item, data, desc);
				createTaskDescription(item, data, desc);
				createSuccessTaskListbox(item, data);
				createErrorTaskListbox(item, data);				
			}
		};
	}
	
	private void createTaskBandbox(Listitem item, final TaskRunnerDetailDTO data, final Textbox desc) {
		
		final SerializableSearchDelegate<Task> searchDelegate = new SerializableSearchDelegate<Task>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Task> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				TaskExample example = new TaskExample();
				example.createCriteria().andTaskNameLikeInsensitive(searchCriteria);
				return taskServiceImpl.getTaskByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				TaskExample example = new TaskExample();
				example.createCriteria().andTaskNameLikeInsensitive(searchCriteria);
				return taskServiceImpl.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, Task data) {
				keyValue.setKey(data);
				keyValue.setValue(data.getTaskName());
				keyValue.setDescription(data.getTaskName());
			}
		};
		
		Listcell cell = new Listcell();
		final CommonPopupBandbox task = new CommonPopupBandbox();
		task.setSearchDelegate(searchDelegate);
		task.setWidth("90%");
		task.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = -3811961348386980411L;

			@Override
			public void onEvent(Event event) throws Exception {
				KeyValue selected = (KeyValue) task.getKeyValue();
				if (selected != null) {
					Task task = (Task) selected.getKey();
					if(data.getMainTask() == null){
						data.setMainTask(new TaskDTO());
					}
					data.getMainTask().setTaskName(task.getTaskName());
					data.setTaskId(task.getId());
					data.getMainTask().setDescription(task.getDescription());
					desc.setValue(task.getDescription());							
				} else {
					data.setMainTask(null);
					data.setTaskId(null);
					desc.setValue(null);
				}
			}
		});
		
		if(data.getMainTask() != null) {
			task.setValue(data.getMainTask().getTaskName());
			task.setRawValue(new KeyValue(data.getMainTask(), data.getMainTask().getTaskName(), data.getMainTask().getTaskName()));
		}
		task.setReadonly(true);
		task.setParent(cell);
		cell.setParent(item);
	}
	
	public void rebuildTabularEntryOnChangeTaskCollection() {
		ListModelList<TaskRunnerDetailDTO> model = new ListModelList<TaskRunnerDetailDTO>(taskRunnerDetail.getValue());
		model.setMultiple(true);
		taskRunnerDetail.setModel(model);
	}
	
	private SerializableListItemRenderer<Task> getTaskItemRenderer() {
		return new SerializableListItemRenderer<Task>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, Task data, int index)
					throws Exception {
				item.setLabel(data.getTaskName());
				item.setValue(data);
			}
		};
	}
	//ok
	private void createTaskDescription(Listitem item, final TaskRunnerDetailDTO data, Textbox desc) {
		Listcell cell = new Listcell();
		if(data.getMainTask() != null)
			desc.setValue(data.getMainTask().getDescription());
		desc.setReadonly(true);
		desc.setParent(cell);
		cell.setParent(item);
	}
	
	//ok
	private void createSuccessTaskListbox(Listitem item, final TaskRunnerDetailDTO data) {
		Listcell successCell = new Listcell();
		final Listbox successTask = new Listbox();
		successTask.setWidth("90%");
		successTask.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				TaskRunnerDetail taskRunnerDetail = data;
				Task selected = successTask.getSelectedItem().getValue();
				taskRunnerDetail.setSccuessTaskId(selected.getId());
				
			}
		});
		successTask.setItemRenderer(getTaskItemRenderer());
		successTask.setModel(new ListModelList<Task>(getSelectedTasks()));
		successTask.renderAll();
		if(data.getSccuessTaskId() != null){
			for (Listitem listitem : successTask.getItems()) {
				Task task = listitem.getValue();
				if(task.getId().equals(data.getSccuessTaskId()))
					listitem.setSelected(true);
			}
		}
		
		if(rdType.getSelectedIndex() == 1)
			successTask.setDisabled(true);
		successTask.setMold("select");
		successTask.setParent(successCell);
		successCell.setParent(item);
		
	}
	//ok
	private void createErrorTaskListbox(Listitem item, final TaskRunnerDetailDTO data){
		Listcell errorCell = new Listcell();
		final Listbox errorTask = new Listbox();
		errorTask.setWidth("90%");
		errorTask.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				TaskRunnerDetail taskRunnerDetail = data;
				Task selected = errorTask.getSelectedItem().getValue();
				taskRunnerDetail.setErrorTaskId(selected.getId());
				
			}
		});
		
		errorTask.setItemRenderer(getTaskItemRenderer());
		errorTask.setModel(new ListModelList<Task>(getSelectedTasks()));
		errorTask.renderAll();
		if(data.getErrorTaskId() != null){
			for (Listitem listitem : errorTask.getItems()) {
				Task task = listitem.getValue();
				if(task.getId() == data.getErrorTaskId())
					listitem.setSelected(true);
			}
		}
		
		if(rdType.getSelectedIndex() == 1)
			errorTask.setDisabled(true);
		errorTask.setMold("select");
		errorTask.setParent(errorCell);
		errorCell.setParent(item);
	}
	
	/*private boolean validate() {
		if(txtName.getValue().equalsIgnoreCase("")) {
			return false;
		}
		if(dtbDateFrom.getValue() == null) {
			return false;
		}
		if(txtDesc.getValue().equalsIgnoreCase("")) {
			return false;
		}
		if(rdType.getSelectedIndex() <= -1) {
			return false;
		}
		if(dtbDateTo.getValue() != null) {
			if(DateUtil.compareDate(dtbDateFrom.getValue(), dtbDateTo.getValue())) {
				return false;
			}
		}
		return true;
	}*/
	
	/*private void validation() {
		ErrorMessageUtil.clearErrorMessage(txtName);
		ErrorMessageUtil.clearErrorMessage(dtbDateFrom);
		ErrorMessageUtil.clearErrorMessage(dtbDateTo);
		ErrorMessageUtil.clearErrorMessage(txtDesc);
		ErrorMessageUtil.clearErrorMessage(rdType);
		
		Vlayout vlayout = new Vlayout();
		Label label = new Label();
		label.setValue("This set of Tasks will cause cyclic path.");
		label.setStyle("color:red");
		vlayout.appendChild(label);
		taskRunnerDetail.getParent().insertBefore(vlayout, taskRunnerDetail);
		
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
	}*/
	
	private boolean validationTabularTaskRunner() {
		int i, j;
		for(i = 0; i < taskRunnerDetail.getItemCount(); i++) {
			if(taskRunnerDetail.getValue().get(i).getSequence() != null) {
				if(taskRunnerDetail.getValue().get(i).getSequence().toString().length() > 5) {
					lblErrorTaskRunner.setValue("Sequence at row " + (i+1) + " more than 5 digit");		
					return false;
				}
			}
			
			if(taskRunnerDetail.getValue().get(i).getMainTask() == null) {
				lblErrorTaskRunner.setValue("Task at row " + (i+1) + " can't empty");
				return false;
			}
			
			for(j = 0; j < taskRunnerDetail.getItemCount(); j++) {
				if(i == j) {
					continue;
				}
				if(taskRunnerDetail.getValue().get(i).getTaskId() == taskRunnerDetail.getValue().get(j).getTaskId()) {
					lblErrorTaskRunner.setValue("Task name can't double at row " + (i+1) + " and row " + (j+1));			
					return false;
				}
			}
		}
		return true;
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		lblErrorTaskRunner.setValue("");
		if(taskRunnerDto == null){
			taskRunnerDto = new TaskRunnerDTO();
			taskRunner = new TaskRunner();
		}
			
		taskRunnerDto.setTaskRunnerName(txtName.getValue());
		taskRunnerDto.setDescription(txtDesc.getValue());
		taskRunnerDto.setDateFrom(dtbDateFrom.getValue());
		taskRunnerDto.setDateTo(dtbDateTo.getValue());
		taskRunnerDto.setExecutionType(TaskExecutionType.getValue(rdType.getSelectedIndex()));
		
		if(selected == null) {
			taskRunnerDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			taskRunnerDto.setCreationDate(new Date());
		} else {
			taskRunnerDto.setCreatedBy(selected.getCreatedBy());
			taskRunnerDto.setCreationDate(selected.getCreationDate());
		}
		taskRunnerDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		taskRunnerDto.setLastUpdateDate(new Date());
		
		taskRunnerDto.setTaskRunnerDetailDto(taskRunnerDetail.getValue());
		
		
			clearErrorMessage();
//			if(taskRunnerDetail.validate()) {
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
								TaskRunnerValidator taskRunnerValidator = new TaskRunnerValidator();
								taskRunnerValidator.validate(taskRunnerDto);
								
								if(validationTabularTaskRunner() == false) {
									return;
								}
								taskRunnerService.save(taskRunnerDto);
								Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES, Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/systemadmin/task_runner_inquiry.zul", getSelf().getParent(), param);
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
		ErrorMessageUtil.clearErrorMessage(txtDesc);
		ErrorMessageUtil.clearErrorMessage(dtbDateFrom);
		ErrorMessageUtil.clearErrorMessage(dtbDateTo);
		ErrorMessageUtil.clearErrorMessage(taskRunnerDetail.getPreviousSibling());
	}
	
	private void showErrorMessage(ValidationException vex){
		Map<String, String> errors = vex.getConstraintViolations();
		if(errors.containsKey(TaskRunnerValidator.TASK)) {
			if (taskRunnerDetail.getPreviousSibling() instanceof Vlayout) {
				taskRunnerDetail.getParent().removeChild(taskRunnerDetail.getPreviousSibling());
			}
			Vlayout vlayout = new Vlayout();
			Label label = new Label();
			label.setValue(vex.getMessage(TaskRunnerValidator.TASK));
			label.setStyle("color:red");
			vlayout.appendChild(label);
			taskRunnerDetail.getParent().insertBefore(vlayout, taskRunnerDetail);
		}
		
		if(errors.containsKey(TaskRunnerValidator.CYCLIC_ERROR)) {
			if (taskRunnerDetail.getPreviousSibling() instanceof Vlayout) {
				taskRunnerDetail.getParent().removeChild(taskRunnerDetail.getPreviousSibling());
			}
			Vlayout vlayout = new Vlayout();
			Label label = new Label();
			label.setValue("This set of Tasks will cause cyclic path.");
			label.setStyle("color:red");
			vlayout.appendChild(label);
			taskRunnerDetail.getParent().insertBefore(vlayout, taskRunnerDetail);
		}
		
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
					Executions.createComponents("~./hcms/systemadmin/task_runner_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
}
