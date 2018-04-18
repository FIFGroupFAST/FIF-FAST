package co.id.fifgroup.systemadmin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.domain.LookupHeader;
import co.id.fifgroup.basicsetup.domain.LookupHeaderExample;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.tabularentry.BandboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.systemadmin.constant.TaskDataType;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.domain.TaskParameter;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class LookupTaskParameterComposer extends SelectorComposer<Window>{
	
	private static final long serialVersionUID = 1L;
	@WireVariable(rewireOnActivate = true)
	private transient LookupService lookupServiceImpl;
	@Wire
	private TabularEntry<TaskParameterDTO> parameterListbox;
	@Wire
	private Label lblErrorTask;
	@Wire
	private Button btnAddRow;
	@Wire
	private Button btnDelete;
	@Wire
	private Button btnOk;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	private List<TaskParameterDTO> taskParameterList;
	private TaskDetailComposer composer;
	private FunctionPermission functionPermission;
	
	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		taskParameterList = (List<TaskParameterDTO>) arg.get("taskParameters");
		if(taskParameterList == null)
			taskParameterList = new ArrayList<TaskParameterDTO>();
		composer = (TaskDetailComposer) arg.get("composer");
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable()){
			btnAddRow.setDisabled(true);
			btnDelete.setDisabled(true);
			btnOk.setDisabled(true);
		}
	}
	
	@Listen("onCreate=listbox#parameterListbox")
	public void buildListbox() {
		parameterListbox.setDataType(TaskParameterDTO.class);
		parameterListbox.setModel(getModel());
		parameterListbox.setItemRenderer(getListitemRenderer());
		parameterListbox.setValidationRule(getTaskParameterValidation());
	}
	
	@Listen("onClick=button#btnAddRow")
	public void addRow() {
		parameterListbox.addRow();
	}
	
	@Listen("onClick=button#btnDelete")
	public void deleteRow() {
		parameterListbox.deleteRow();
	}
	
	private ListModelList<TaskParameter> getModel() {
		ListModelList<TaskParameter> model = new ListModelList<TaskParameter>(taskParameterList);
		model.setMultiple(true);
		return model;
	}
	
	private TabularValidationRule<TaskParameterDTO> getTaskParameterValidation() {
		return new TabularValidationRule<TaskParameterDTO>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean validate(TaskParameterDTO data, List<String> errorRow) {
				if(data.getKey() == null || data.getKey().equals(""))
					errorRow.add("Key cannot be empty");
				if(data.getDisplayName() == null || data.getDisplayName().equals(""))
					errorRow.add("Display name cannot be empty");
				if(data.getDataType() == null || data.getDataType().equals(""))
					errorRow.add("Data type cannot be empty");
				else{
					boolean match = false;
					if(data.getDataType().equals(TaskDataType.LOOKUP.toString())){
						if(data.getLookupId() == null){
							for(StaticParameterKey staticParam : StaticParameterKey.values()){
								if(data.getKey().equals(staticParam.toString())){
									match = true;
								}
							}
							if(!match)
								errorRow.add("Lookup Code cannot be empty");
						}
					}
				}
												
				if(errorRow.size() > 0)	
					return false;
				return true;
			}			
		};
	}
	
	private SerializableListItemRenderer<TaskParameterDTO> getListitemRenderer() {
		return new SerializableListItemRenderer<TaskParameterDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final TaskParameterDTO data, int index) throws Exception {
				new Listcell().setParent(item);
								
				new TextboxListcell<TaskParameterDTO>(data, data.getKey(), "key", false, 50, "75%").setParent(item);
				new TextboxListcell<TaskParameterDTO>(data, data.getDisplayName(), "displayName", false, 50, "75%").setParent(item);
				
				//Lookup Code
				SerializableSearchDelegate<LookupHeader> lookupSearchDelegate = new SerializableSearchDelegate<LookupHeader>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public List<LookupHeader> findBySearchCriteria(String searchCriteria, int limit, int offset) {
						LookupHeaderExample example = new LookupHeaderExample();
						example.createCriteria().andNameLikeInsensitive(searchCriteria);
						
						return lookupServiceImpl.getLookupHeaderByExample(example, limit, offset);
					}

					@Override
					public int countBySearchCriteria(String searchCriteria) {
						LookupHeaderExample example = new LookupHeaderExample();
						example.createCriteria().andNameLikeInsensitive(searchCriteria);
						return lookupServiceImpl.getCountLookupHeaderByExample(example);
					}

					@Override
					public void mapper(KeyValue keyValue, LookupHeader data) {
						keyValue.setKey(data.getLookupId());
						keyValue.setDescription(data.getName());
						keyValue.setValue(data.getName());
					}
				};
				
				final BandboxListcell<TaskParameterDTO, Long> bandboxListcell = new BandboxListcell<TaskParameterDTO, Long>(data.getLookupCode(), data, "lookupId", lookupSearchDelegate);
				bandboxListcell.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					
					@Override
					public void onEvent(Event event) throws Exception {
						KeyValue keyValue = (KeyValue) event.getData();
						data.setLookupCode((String) keyValue.getValue());
					}
				});
				
				bandboxListcell.getBandbox().setDisabled(true);
				
				//Data Type
//				createDataParameterListbox(item, data);
				
				Listcell typeCell = new Listcell();
				final Listbox listType = new Listbox();
				listType.setMold("select");
				listType.setModel(new ListModelList<TaskDataType>(TaskDataType.values()));
				listType.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						TaskDataType value = listType.getSelectedItem().getValue();
						data.setDataType(value.toString());
						if(listType.getSelectedItem().getValue().toString().equals(TaskDataType.LOOKUP.toString())){
							bandboxListcell.getBandbox().setDisabled(false);
						}else{
							bandboxListcell.getBandbox().setDisabled(true);
						}
					}
				});
				listType.renderAll();
				int idx = 0;
				for (TaskDataType dataType : TaskDataType.values()) {
					if(dataType.toString().equals(data.getDataType()))
						listType.setSelectedIndex(idx);
					idx++;
				}
//				listType.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {
//		
//					@Override
//					public void onEvent(Event arg0) throws Exception {
//						Object ob = listType.getSelectedItem().getValue();
//						String dataType = TaskDataType.LOOKUP.toString();
//						
//						
//					}
//					
//				});
				listType.setParent(typeCell);
				typeCell.setParent(item);
				
				
				
				bandboxListcell.setParent(item);
				
				//Mandatory Flag
				createMandatoryCheckbox(item, data);
				
			}
		};
	}
	
	private void createDataParameterListbox(Listitem item, final TaskParameter data) {
		Listcell typeCell = new Listcell();
		final Listbox listType = new Listbox();
		listType.setMold("select");
		listType.setModel(new ListModelList<TaskDataType>(TaskDataType.values()));
		listType.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				TaskDataType value = listType.getSelectedItem().getValue();
				data.setDataType(value.toString());
			}
		});
		listType.renderAll();
		int idx = 0;
		for (TaskDataType dataType : TaskDataType.values()) {
			if(dataType.toString().equals(data.getDataType()))
				listType.setSelectedIndex(idx);
			idx++;
		}
//		listType.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {
//
//			@Override
//			public void onEvent(Event arg0) throws Exception {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
		listType.setParent(typeCell);
		typeCell.setParent(item);
	}
	
	private void createMandatoryCheckbox(Listitem item, final TaskParameter data) {
		Listcell mandatoryCell = new Listcell();
		final Checkbox cbMandatory = new Checkbox();
		cbMandatory.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if(cbMandatory.isChecked())
					data.setMandatory(true);
				else
					data.setMandatory(false);
			}
		});
		cbMandatory.setChecked(data.isMandatory());
		cbMandatory.setParent(mandatoryCell);
		mandatoryCell.setParent(item);
	}
	
	private boolean validationTabularTask() {
		int i;
		for(i = 0; i < parameterListbox.getItemCount(); i++) {
			if(parameterListbox.getValue().get(i).getKey().toString().length() > 50) {
				lblErrorTask.setValue("Key at row " + (i+1) + " more than 50 character");					
				return false;
			}
			if(parameterListbox.getValue().get(i).getDisplayName().length() > 50) {
				lblErrorTask.setValue("Display name at row " + (i+1) + " more than 50 character");					
				return false;
			}
		}
		return true;
	}
	
	@Listen("onClick=button#btnOk")
	public void onClickOk() {
		lblErrorTask.setValue("");
		if(parameterListbox.validate()) {
			if(validationTabularTask() == false) {
				return;
			}
			
			taskParameterList = parameterListbox.getValue();
			composer.setTaskParameterValue(taskParameterList);
			getSelf().detach();
		}
	}
}
