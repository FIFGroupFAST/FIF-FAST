package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.constants.ApproverType;
import co.id.fifgroup.systemworkflow.constants.BasedOn;
import co.id.fifgroup.systemworkflow.constants.LevelMethod;
import co.id.fifgroup.systemworkflow.constants.WorkflowReference;
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.dto.LevelApproverDTO;
import co.id.fifgroup.systemworkflow.service.ApproverMappingService;
import co.id.fifgroup.systemworkflow.util.RuleRenderer;
import co.id.fifgroup.systemworkflow.validation.LevelApproverValidator;

import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMRuleExpression;
import co.id.fifgroup.avm.domain.AVMRuleStatement;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.service.WorkflowLookupServiceAdapter;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowApprovalModelAddLevelComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(WorkflowApprovalModelAddLevelComposer.class);
		
	@WireVariable("arg")
	Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient ApproverMappingService approverMappingServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient WorkflowLookupServiceAdapter workflowLookupServiceAdapterImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LevelApproverValidator levelApproverValidator;
	
	@Wire
	private Textbox txtLevelName;
	@Wire
	private Grid gridRule;
	@Wire
	private Radiogroup rdgApproveMethod;
	@Wire
	private Intbox intMinimunRequired;
	@Wire
	private TabularEntry<AVMApprover> lbxApprover;
	@Wire
	private Div errListApprover;
	@Wire
	private Label errRuleValidate;
	@Wire
	private Window winWorkflowApprovalModelAddLevel;
	
	private List<AVMApprover> listAvmApprovers;
	private LevelApproverDTO levelApproverDto = new LevelApproverDTO();
		
	private ListModelList<AVMRuleStatement> ruleModelList;
		
	private WorkflowApprovalModelComposer parent;
	private int index = -1;
	
	private RuleRenderer ruleRenderer = new RuleRenderer();	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		parent = (WorkflowApprovalModelComposer) arg.get("parent");
		
		populateData();
		getGridRule();
		buildApprover();
	}
	
	private void populateData() {
		if (arg.containsKey("level")) {
			levelApproverDto = (LevelApproverDTO) arg.get("level");
			index = (int) arg.get("index"); 
			txtLevelName.setValue(levelApproverDto.getLevel().getLevelName());
			rdgApproveMethod.setSelectedIndex(levelApproverDto.getLevel().getLevelMethod());
			if (rdgApproveMethod.getSelectedIndex() == LevelMethod.AND_MINIMUM.getCode()) {
				intMinimunRequired.setDisabled(false);
				intMinimunRequired.setValue(levelApproverDto.getLevel().getNumberOfApprovals());
			}
			listAvmApprovers = levelApproverDto.getApprovers();
		}
	}
	
	@Listen("onCheck = radiogroup#rdgApproveMethod")
	public void checkRadioGroup() {
		int selected = Integer.parseInt((String) rdgApproveMethod.getSelectedItem().getValue());
		if (selected == LevelMethod.AND_MINIMUM.getCode()) {
			intMinimunRequired.setDisabled(false);
		} else {
			intMinimunRequired.setDisabled(true);
		}
	}
	
	private void buildApprover(){
		lbxApprover.setDataType(AVMApprover.class);
		lbxApprover.setModel(getAVMApprover());
		lbxApprover.setItemRenderer(getListItemRenderer());
		lbxApprover.setValidationRule(approverValidationRule());
	}
	
	private ListModelList<AVMApprover> getAVMApprover(){
		if(listAvmApprovers == null){
			listAvmApprovers = new ArrayList<AVMApprover>();
			listAvmApprovers.add(new AVMApprover());
		}
		ListModelList<AVMApprover> model = new ListModelList<AVMApprover>(listAvmApprovers);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<AVMApprover> getListItemRenderer() {
		return new SerializableListItemRenderer<AVMApprover>() {

			private static final long serialVersionUID = -3618637865122691732L;

			@Override
			public void render(Listitem item, final AVMApprover data, int index)
					throws Exception {
				new Listcell("").setParent(item);
				
				Listcell typeCell = new Listcell();
				Hbox hboxApprover = new Hbox();
				final Listbox type = createApproverType(data.getApproverType());
				final Listbox approver = new Listbox();
				final CommonPopupDoubleBandbox bandbox = new CommonPopupDoubleBandbox();
				
				bandbox.setWidth("250px");
				bandbox.setSearchText1("Code");
				bandbox.setSearchText2("Name");
				bandbox.setHeaderLabel1("Code");
				bandbox.setHeaderLabel2("Name");
				data.setApproverType(((ApproverType)type.getSelectedItem().getValue()).getCode());
				
				final ApproverMapping select = new ApproverMapping();
				select.setApproverId(null);
				select.setApproverName(Labels.getLabel("common.pleaseSelect"));
				
				final EventListener<Event> approverListener = new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						ApproverMapping mapping = (ApproverMapping) approver.getSelectedItem().getValue();
						if (mapping != null && mapping.getApproverId() != null) {
							data.setApproverId(mapping.getApproverId());
						} else {
							data.setApproverId(null);
						}
					}
				};
				
				type.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						data.setApproverType(((ApproverType)type.getSelectedItem().getValue()).getCode());
						data.setApproverId(null);
						
						if (data.getApproverType() == ApproverType.DepartmentOwner.getCode() ||
								data.getApproverType() == ApproverType.Supervisor.getCode() ||
								data.getApproverType() == ApproverType.Manager.getCode() ||
								data.getApproverType() == ApproverType.JobRole.getCode() ||
								data.getApproverType() == ApproverType.DepartmentHead.getCode() ||
								data.getApproverType() == ApproverType.JobOwner.getCode() 
								) {
							approver.setVisible(true);
							bandbox.setVisible(false);
							List<ApproverMapping> listApproverMappings = approverMappingServiceImpl.getApproverMappingByApproverType((ApproverType)type.getSelectedItem().getValue());
							listApproverMappings.add(0, select);
							ListModelList<ApproverMapping> listModelApprover = new ListModelList<>(listApproverMappings);
							listModelApprover.addToSelection(select);
							approver.setModel(listModelApprover);
						} else if (data.getApproverType() == ApproverType.Employee.getCode()) {
							approver.setVisible(false);
							bandbox.setVisible(true);
							bandbox.setRawValue(null);
						} else if (data.getApproverType() == ApproverType.Role.getCode()) {
							approver.setVisible(true);
							bandbox.setVisible(false);
							List<ApproverMapping> listApproverMappings = new ArrayList<ApproverMapping>();
							listApproverMappings.add(select);
							List<KeyValue> keyValues = lookupServiceImpl.getLookupKeyValues(WorkflowReference.PEA_ROLE.name(), null);
							for (KeyValue keyValue : keyValues) {
								if (!keyValue.getKey().toString().equalsIgnoreCase("PICDO")
										&& !keyValue.getKey().toString().equalsIgnoreCase("HCAdm")
										&& !keyValue.getKey().toString().equalsIgnoreCase("OSSH")) {
									ApproverMapping approverMapping = approverMappingServiceImpl.getApproverMappingByName(keyValue.getKey().toString());
									if (approverMapping == null) {
										approverMapping = new ApproverMapping();
										approverMapping.setApproverId(UUID.randomUUID());
										approverMapping.setApproverName(keyValue.getKey().toString());
										approverMapping.setApproverType(ApproverType.Role.name());
										approverMappingServiceImpl.insertNewApproverMapping(approverMapping);
									}
									approverMapping.setApproverName(keyValue.getDescription().toString());
									listApproverMappings.add(approverMapping);
								}
							}
							ListModelList<ApproverMapping> listModelApprover = new ListModelList<>(listApproverMappings);
							listModelApprover.addToSelection(select);
							approver.setModel(listModelApprover);
						} else if (data.getApproverType() == ApproverType.Job.getCode()) {
							approver.setVisible(false);
							bandbox.setVisible(true);
							bandbox.setRawValue(null);
						} else if (data.getApproverType() == ApproverType.JobGroup.getCode()) {
							approver.setVisible(true);
							bandbox.setVisible(false);
							List<ApproverMapping> listApproverMappings = new ArrayList<ApproverMapping>();
							listApproverMappings.add(select);
							List<KeyValue> keyValues = lookupServiceImpl.getLookupKeyValues(WorkflowReference.WSU_JOB_GROUP.name(), null);
							for (KeyValue keyValue : keyValues) {
								ApproverMapping approverMapping = approverMappingServiceImpl.getApproverMappingByName(keyValue.getKey().toString());
								if (approverMapping == null) {
									approverMapping = new ApproverMapping();
									approverMapping.setApproverId(UUID.randomUUID());
									approverMapping.setApproverName(keyValue.getKey().toString());
									approverMapping.setApproverType(ApproverType.JobGroup.name());
									approverMappingServiceImpl.insertNewApproverMapping(approverMapping);
								}
								approverMapping.setApproverName(keyValue.getDescription().toString());
								listApproverMappings.add(approverMapping);					
							}
							ListModelList<ApproverMapping> listModelApprover = new ListModelList<>(listApproverMappings);
							listModelApprover.addToSelection(select);
							approver.setModel(listModelApprover);
						} 
						approver.removeEventListener(Events.ON_SELECT, approverListener);
						approver.addEventListener(Events.ON_SELECT, approverListener);
						approver.renderAll();
						((ListModelList<?>) approver.getListModel()).setMultiple(false);
//						((ListModelList<?>) approver.getListModel()).clearSelection();
					}
				});
				typeCell.appendChild(type);
				typeCell.setParent(item);
				
				Listcell approverCell = new Listcell();
				approver.setParent(hboxApprover);
				bandbox.setParent(hboxApprover);
				approverCell.appendChild(hboxApprover);
				approverCell.setParent(item);
				approver.setMold("select");
				if (data.getApproverType() == ApproverType.DepartmentOwner.getCode() ||
						data.getApproverType() == ApproverType.Supervisor.getCode() ||
						data.getApproverType() == ApproverType.Manager.getCode() ||
						data.getApproverType() == ApproverType.JobRole.getCode()) {
					approver.setVisible(true);
					bandbox.setVisible(false);
					List<ApproverMapping> listApproverMappings = approverMappingServiceImpl.getApproverMappingByApproverType((ApproverType)type.getSelectedItem().getValue());
					listApproverMappings.add(0, select);
					ListModelList<ApproverMapping> listModelApprover = new ListModelList<>(listApproverMappings);
					listModelApprover.addToSelection(select);
					for (ApproverMapping approverMapping : listApproverMappings) {
						if (data.getApproverId() != null && approverMapping.getApproverId() != null) {
							if (approverMapping.getApproverId().equals(data.getApproverId())) {
								listModelApprover.addToSelection(approverMapping);
							}								
						}
					}
					approver.setModel(listModelApprover);
					
				} else if (data.getApproverType() == ApproverType.Employee.getCode()) {
					approver.setVisible(false);
					bandbox.setVisible(true);
					KeyValue employee = workflowLookupServiceAdapterImpl.getValueEmployee(data.getApproverId());
					bandbox.setRawValue(employee);
				} else if (data.getApproverType() == ApproverType.Role.getCode()) {
					approver.setVisible(true);
					bandbox.setVisible(false);
					List<ApproverMapping> listApproverMappings = new ArrayList<ApproverMapping>();
					listApproverMappings.add(select);
					List<KeyValue> keyValues = lookupServiceImpl.getLookupKeyValues(WorkflowReference.PEA_ROLE.name(), null);
					for (KeyValue keyValue : keyValues) {
						if (!keyValue.getKey().toString().equalsIgnoreCase("PICDO")
								&& !keyValue.getKey().toString().equalsIgnoreCase("HCAdm")
								&& !keyValue.getKey().toString().equalsIgnoreCase("OSSH")) {
							ApproverMapping approverMapping = approverMappingServiceImpl.getApproverMappingByName(keyValue.getKey().toString());
							approverMapping.setApproverName(keyValue.getDescription().toString());
							listApproverMappings.add(approverMapping);		
						}
					}
					ListModelList<ApproverMapping> listModelApprover = new ListModelList<>(listApproverMappings);
					listModelApprover.addToSelection(select);
					for (ApproverMapping mapping : listModelApprover) {
						if (data.getApproverId() != null && mapping.getApproverId() != null) {
							if (mapping.getApproverId().equals(data.getApproverId())) {
								listModelApprover.addToSelection(mapping);
								break;
							}								
						}
					}
					approver.setModel(listModelApprover);
				} else if (data.getApproverType() == ApproverType.Job.getCode()) {
					approver.setVisible(false);
					bandbox.setVisible(true);
					KeyValue job = workflowLookupServiceAdapterImpl.getJobByUUID(data.getApproverId());
					bandbox.setRawValue(job);
				} else if (data.getApproverType() == ApproverType.JobGroup.getCode()) {
					approver.setVisible(true);
					bandbox.setVisible(false);
					List<ApproverMapping> listApproverMappings = new ArrayList<ApproverMapping>();
					listApproverMappings.add(select);
					List<KeyValue> keyValues = lookupServiceImpl.getLookupKeyValues(WorkflowReference.WSU_JOB_GROUP.name(), null);
					for (KeyValue keyValue : keyValues) {
						ApproverMapping approverMapping = approverMappingServiceImpl.getApproverMappingByName(keyValue.getKey().toString());
						approverMapping.setApproverName(keyValue.getDescription().toString());
						listApproverMappings.add(approverMapping);		
					}
					ListModelList<ApproverMapping> listModelApprover = new ListModelList<>(listApproverMappings);
					listModelApprover.addToSelection(select);
					for (ApproverMapping mapping : listModelApprover) {
						if (data.getApproverId() != null && mapping.getApproverId() != null) {
							if (mapping.getApproverId().equals(data.getApproverId())) {
								listModelApprover.addToSelection(mapping);
								break;
							}								
						}
					}
					approver.setModel(listModelApprover);
				} 		

				approver.setItemRenderer(new SerializableListItemRenderer<ApproverMapping>() {
					
					private static final long serialVersionUID = 1L;

					@Override
					public void render(Listitem item, ApproverMapping data, int index)
							throws Exception {
						item.setValue(data);
						item.appendChild(new Listcell(data.getApproverName()));													
					}
				});
				
				approver.removeEventListener(Events.ON_SELECT, approverListener);
				approver.addEventListener(Events.ON_SELECT, approverListener);
				
				
				bandbox.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

					private static final long serialVersionUID = 1L;

					@Override
					public List<KeyValue> findBySearchCriteria(String searchCriteria1,
							String searchCriteria2, int limit, int offset) {
						if (data.getApproverType() == ApproverType.Job.getCode()) {
							return workflowLookupServiceAdapterImpl.getAllJob(searchCriteria1, searchCriteria2, limit, offset);
						} else {
							return workflowLookupServiceAdapterImpl.getAllEmployeeCriteria(searchCriteria1, searchCriteria2, limit, offset);							
						}
					}

					@Override
					public int countBySearchCriteria(String searchCriteria1,
							String searchCriteria2) {
						if (data.getApproverType() == ApproverType.Job.getCode()) {
							return workflowLookupServiceAdapterImpl.countAllJob(searchCriteria1, searchCriteria2);
						} else {
							return workflowLookupServiceAdapterImpl.countAllEmployeeCriteria(searchCriteria1, searchCriteria2);							
						}
					}

					@Override
					public void mapper(KeyValue keyValue, KeyValue data) {
						keyValue.setKey((UUID) data.getKey());
						keyValue.setValue(data.getValue().toString());
						keyValue.setDescription(data.getDescription().toString());
					}
				});
				
				bandbox.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -3811961348386980411L;

					@Override
					public void onEvent(Event event) throws Exception {
						KeyValue selected = (KeyValue) bandbox.getKeyValue();
						if (selected != null) {
							data.setApproverId(selected.getKey() == null ? null : (UUID)selected.getKey());							
						} else {
							data.setApproverId(null);
						}
					}
				});
				
				approver.setParent(hboxApprover);
				bandbox.setParent(hboxApprover);
				approverCell.appendChild(hboxApprover);
				approverCell.setParent(item);
				
				Listcell basedOnCell = new Listcell();
				final Listbox basedOn = createBasedOn(data.getBasedOn());
				data.setBasedOn(((BasedOn)basedOn.getSelectedItem().getValue()).getCode());
				basedOn.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						data.setBasedOn(((BasedOn)basedOn.getSelectedItem().getValue()).getCode());
					}
				});
				basedOnCell.appendChild(basedOn);
				basedOnCell.setParent(item);	
				
				approver.renderAll();
			}
		};
	}
	
	private TabularValidationRule<AVMApprover> approverValidationRule() {
		return new TabularValidationRule<AVMApprover>() {
			
			private static final long serialVersionUID = -2432318677443380704L;

			@Override
			public boolean validate(AVMApprover data, List<String> errorRow) {
				if(data.getApproverType() == 0)
					errorRow.add(Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.approverType")}));
				if(data.getApproverId() == null)
					errorRow.add(Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.approver")}));
				if(data.getBasedOn() == 0)
					errorRow.add(Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.basedOn")}));
				
				if(errorRow.size() > 0)	
					return false;
				return true;
			}			
		};
	}
	
	private Listbox createApproverType(short selected) {
		Listbox type = new Listbox();
		type.setMold("select");
		ListModelList<ApproverType> model = new ListModelList<ApproverType>(ApproverType.values());
		type.setModel(model);
		type.renderAll();
		
		if(selected != 0) {
			int idx = 0;
			for (ApproverType approverType : model) {
				if(approverType.getCode() == selected)
					type.setSelectedItem(type.getItemAtIndex(idx));
				idx++;
			}
		} else
			type.setSelectedIndex(0);
		
		return type;
	}
	
	private Listbox createBasedOn(int selected) {
		Listbox type = new Listbox();
		type.setMold("select");
		ListModelList<BasedOn> model = new ListModelList<BasedOn>(BasedOn.values());
		type.setModel(model);
		type.renderAll();
		
		if(selected != 0) {
			int idx = 0;
			for (BasedOn basedOn : model) {
				if(basedOn.getCode() == selected)
					type.setSelectedItem(type.getItemAtIndex(idx));
				idx++;
			}
		} else
			type.setSelectedIndex(0);
		
		return type;
	}
	
	@Listen("onClick=#btnAddRow")
	public void addRow(){
		lbxApprover.addRow();
	}
	
	@Listen("onClick=#btnDelete")
	public void deleteRow(){
		lbxApprover.deleteRow();
	}
	
	public void getGridRule() {
		if (arg.containsKey("level")) {
			AVMRuleExpression avmRuleExpression = (AVMRuleExpression) arg.get("completeRuleStatement");
			if (avmRuleExpression.getAvmRuleStatementList().size() == 0) {
				AVMRuleStatement avmRuleStatement = new AVMRuleStatement();
				avmRuleStatement.setAvmRuleStatementId(UUID.randomUUID());
				avmRuleExpression.getAvmRuleStatementList().add(avmRuleStatement);
			}
			ruleModelList = new ListModelList<AVMRuleStatement>(avmRuleExpression.getAvmRuleStatementList());
			gridRule.setModel(ruleModelList);
			gridRule.setRowRenderer(ruleRenderer.createGridRuleRowRenderer(ruleModelList, gridRule, 0, false));
		} else {
			AVMRuleStatement avmRuleStatement = new AVMRuleStatement();
			avmRuleStatement.setAvmRuleStatementId(UUID.randomUUID());
			ruleModelList = new ListModelList<>();
			ruleModelList.add(avmRuleStatement);
			gridRule.setModel(ruleModelList);
			gridRule.setRowRenderer(ruleRenderer.createGridRuleRowRenderer(ruleModelList, gridRule, 0, false));
			rdgApproveMethod.setSelectedIndex(0);
		} 
	}	
	
	@Listen("onClick = button#btnAddRuleStatement")
	public void addRuleStatement() {
		AVMRuleStatement avmRuleStatement = new AVMRuleStatement();
		avmRuleStatement.setAvmRuleStatementId(UUID.randomUUID());
		ruleModelList.add(avmRuleStatement);
	}
	
	@Listen ("onClick = button#btnAdd")
	public void onAdd(Event e) {
		logger.info("onAdd called");
		try {
			clearErrorMessage();
							
			AVMLevel avmLevel = new AVMLevel();
			avmLevel.setLevelName(txtLevelName.getValue());
			avmLevel.setRule(ruleRenderer.getRuleExpression(ruleModelList, gridRule));
			avmLevel.setLevelMethod(Integer.parseInt(rdgApproveMethod.getSelectedItem().getValue().toString()));
			
			int numberofapprovals = 1;
			int selectedMethod = Integer.parseInt((String) rdgApproveMethod.getSelectedItem().getValue());
			if (selectedMethod == LevelMethod.AND_MINIMUM.getCode()) {
				numberofapprovals = intMinimunRequired.getValue() != null ? intMinimunRequired.getValue() : 1;
			} else if (selectedMethod == LevelMethod.AND_ALL.getCode()) {
				numberofapprovals = lbxApprover.getValue().size();
			} 
			avmLevel.setNumberOfApprovals(numberofapprovals);
			
			levelApproverDto.setLevel(avmLevel);
			listAvmApprovers = lbxApprover.getValue();
			levelApproverDto.setApprovers(listAvmApprovers);
			levelApproverDto.setValidateTabular(lbxApprover.validate());
			levelApproverDto.setValidateRule(ruleRenderer.validationRule(ruleModelList, gridRule));
			
			levelApproverValidator.validate(levelApproverDto);
			parent.doAfterAddApprovalLevel(levelApproverDto, index);		
			getSelf().onClose();
		} catch (ValidationException ve) {
			showErrorMessage(ve.getConstraintViolations());
		}		
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtLevelName);
		ErrorMessageUtil.clearErrorMessage(intMinimunRequired);
		ErrorMessageUtil.clearErrorMessage(errListApprover);
		ErrorMessageUtil.clearErrorMessage(errRuleValidate);
	}
	
	private void showErrorMessage(Map<String, String> maps) {
		if(maps.get(LevelApproverValidator.LEVEL_NAME_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(txtLevelName, 
				maps.get(LevelApproverValidator.LEVEL_NAME_VALIDATE));
		}
		if(maps.get(LevelApproverValidator.MINIMUM_REQUIRED_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(intMinimunRequired, 
				maps.get(LevelApproverValidator.MINIMUM_REQUIRED_VALIDATE));
		}
		if(maps.get(LevelApproverValidator.LIST_LEVEL_APPROVER_VALIDATE) != null) {
			ErrorMessageUtil.setErrorMessage(errListApprover, 
				maps.get(LevelApproverValidator.LIST_LEVEL_APPROVER_VALIDATE));
		}
		if(maps.get(LevelApproverValidator.LEVEL_APPROVER_VALIDATE) != null) {
			ErrorMessageUtil.setErrorMessage(errListApprover, 
				maps.get(LevelApproverValidator.LEVEL_APPROVER_VALIDATE));
		}
		if(maps.get(LevelApproverValidator.RULE_VALIDATE) != null) {
			ErrorMessageUtil.setErrorMessage(errRuleValidate, 
				maps.get(LevelApproverValidator.RULE_VALIDATE));
		}
	}
	
	@Listen ("onClick = button#btnCancel")
	public void onCancel(Event e) {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			
			private static final long serialVersionUID = -507364048291526698L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					winWorkflowApprovalModelAddLevel.onClose();
				}
			}
		});	
	}
}
