package co.id.fifgroup.systemworkflow.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.constants.LevelMethod;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelDTO;
import co.id.fifgroup.systemworkflow.dto.LevelApproverDTO;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.RuleLookupService;
import co.id.fifgroup.systemworkflow.validation.ApprovalModelValidator;

import co.id.fifgroup.avm.constants.ConjunctionType;
import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMRuleExpression;
import co.id.fifgroup.avm.domain.AVMRuleLookupDetail;
import co.id.fifgroup.avm.domain.AVMRuleMapping;
import co.id.fifgroup.avm.domain.AVMRuleStatement;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMRuleEvaluationManager;
import co.id.fifgroup.avm.manager.AVMSetupManager;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowApprovalModelComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(WorkflowApprovalModelComposer.class);

	private WorkflowApprovalModelComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient RuleLookupService ruleLookupServiceImpl;
	
	@Wire
	private Textbox txtAVMName;
	@Wire
	private Listbox cmbVersion;
	@Wire
	private Datebox dtbEffectiveFrom;
	@Wire
	private Datebox dtbEffectiveTo;
	@Wire
	private Listbox lbxApprovalLevel;
	@Wire
	private Div errListApprovalLevel;
	@Wire
	private Button btnAddApprovalLevel;
	@Wire
	private Button btnDeleteApprovalLevel;
	@Wire
	private Button btnTop;
	@Wire
	private Button btnUp;
	@Wire
	private Button btnDown;
	@Wire
	private Button btnBottom;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnDelete;
		
	private ApprovalModelDTO avmDto = new ApprovalModelDTO();
	private List<String> listVersions = new ArrayList<String>();
	private ListModelList<String> listModelVersions = new ListModelList<String>();
	private List<AVMLevel> listAvmLevels = new ArrayList<AVMLevel>();
	private List<AVMApprover> listAvmApprovers = new ArrayList<AVMApprover>();
	private List<LevelApproverDTO> listAVMLevelApprovers = new ArrayList<LevelApproverDTO>();
	private ListModelList<LevelApproverDTO> listModelAvmLevelApprover = new ListModelList<LevelApproverDTO>();
	
	private int lastVersionNumber = 1;
	private boolean disabledHyperlinkDetail = true;
	private AVMVersions currentVersions;
	
	private static final String NEW_VERSION = "new";
	Calendar dateFromBefore = Calendar.getInstance();
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();
		populateData();
	}
	
	public void initComponent() {
		dtbEffectiveFrom.setFormat(FormatDateUI.getDateFormat());
		dtbEffectiveTo.setFormat(FormatDateUI.getDateFormat());
	}
	
	public void populateData() throws FifException {
		if (arg.containsKey(AVM.class.getName())) {
			avmDto.setAvm((AVM) arg.get(AVM.class.getName()));
			txtAVMName.setValue(avmDto.getAvm().getAvmName());
			txtAVMName.setDisabled(true);
			lastVersionNumber = avmAdapterServiceImpl.getLastVersionNumber(avmDto.getAvm().getAvmId());
			getVersion(lastVersionNumber);
		} else {
			avmDto.setAvm(new AVM());
			avmDto.setAvmVersions(new AVMVersions());
			initValueData();			
		}		
	}
	
	public void initValueData() {
		dateFromBefore = null;
		
		cmbVersion.setDisabled(true);
		listVersions.add(NEW_VERSION);
		listModelVersions = new ListModelList<>(listVersions);
		cmbVersion.setModel(listModelVersions);
		cmbVersion.setSelectedIndex(0);
		
		btnSave.setDisabled(false);
		btnDelete.setDisabled(true);
		disabledButton(false);
	}
	
	private void disabledButton(boolean disabled) {
		btnAddApprovalLevel.setDisabled(disabled);
		btnDeleteApprovalLevel.setDisabled(disabled);
		btnTop.setDisabled(disabled);
		btnUp.setDisabled(disabled);
		btnDown.setDisabled(disabled);
		btnBottom.setDisabled(disabled);
		disabledHyperlinkDetail = disabled;
	}

	public void getVersion(int versionNumber) throws FifException {
		// get version
		logger.info("version number get : " + versionNumber);

		cmbVersion.setDisabled(false);
		listVersions.clear();
		for (int i = 1; i <= versionNumber; i++) {
			listVersions.add(String.valueOf(i));
		}
		
		currentVersions = avmAdapterServiceImpl.getCurrentAVMVersions(avmDto.getAvm().getAvmId(), new Date());
		if (currentVersions == null) {
			currentVersions = getSelectedVersion(versionNumber);			
		} 
		
		listVersions.add(NEW_VERSION);
		listModelVersions = new ListModelList<>(listVersions);
		cmbVersion.setModel(listModelVersions);
		cmbVersion.setSelectedIndex(currentVersions.getVersionNumber() - 1);
		
		setValueData(currentVersions);
	}
	
	private AVMVersions getSelectedVersion(int versionNumber) throws FifException {
		AVMVersions currentVersions = avmAdapterServiceImpl.getAVMVersionsByNumberVersion(avmDto.getAvm().getAvmId(), versionNumber);
		return currentVersions;
	}
	
	private void setValueData(AVMVersions currentVersions) throws FifException {
		if (avmAdapterServiceImpl.isFuture(currentVersions)) {
			dtbEffectiveFrom.setDisabled(false);
			dtbEffectiveTo.setDisabled(false);
			btnSave.setDisabled(false);
			btnDelete.setDisabled(false);
			disabledButton(false);
		} else if (avmAdapterServiceImpl.isCurrent(currentVersions)) {
			if (currentVersions.getVersionNumber() < lastVersionNumber) {
				dtbEffectiveTo.setDisabled(true);	
				btnSave.setDisabled(true);
				btnDelete.setDisabled(true);
			} else {
				dtbEffectiveTo.setDisabled(false);
				btnSave.setDisabled(false);
				btnDelete.setDisabled(true);
			}
			dtbEffectiveFrom.setDisabled(true);
			disabledButton(true);
		} else {
			dtbEffectiveFrom.setDisabled(true);
			dtbEffectiveTo.setDisabled(true);
			btnSave.setDisabled(true);
			btnDelete.setDisabled(true);
			disabledButton(true);
		}
		
		avmDto.setAvmVersions(currentVersions);
		dtbEffectiveFrom.setValue(avmDto.getAvmVersions().getEffectiveStartDate());
		dtbEffectiveTo.setValue(avmDto.getAvmVersions().getEffectiveEndDate());
		dateFromBefore.setTime(avmDto.getAvmVersions().getEffectiveStartDate());
		getSelectedAvmLevelApprover();
	}
	
	private void getSelectedAvmLevelApprover() throws FifException {
		listAvmLevels = new ArrayList<AVMLevel>(avmAdapterServiceImpl.getAVMLevels(avmDto.getAvm(), avmDto.getAvmVersions()));
		listAVMLevelApprovers =  new ListModelList<LevelApproverDTO>();		
		for (AVMLevel level : listAvmLevels) {
			LevelApproverDTO avmLevelApprover = new LevelApproverDTO();
			List<AVMApprover> approvers = avmAdapterServiceImpl.getAVMApproversByLevel(level, avmDto.getAvmVersions());
			avmLevelApprover.setLevel(level);
			avmLevelApprover.setApprovers(approvers);
			listAVMLevelApprovers.add(avmLevelApprover);
		}
		listModelAvmLevelApprover = new ListModelList<LevelApproverDTO>(listAVMLevelApprovers);
		listModelAvmLevelApprover.setMultiple(true);
		lbxApprovalLevel.setModel(listModelAvmLevelApprover);
		lbxApprovalLevel.setItemRenderer(getItemRenderer());
	}
	
	public ListitemRenderer<LevelApproverDTO> getItemRenderer() {
		ListitemRenderer<LevelApproverDTO> listitemRenderer = new SerializableListItemRenderer<LevelApproverDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final LevelApproverDTO level, final int index)
					throws Exception {
				final AVMRuleExpression completeRuleStatement = new AVMRuleExpression();
				new Listcell("").setParent(item);
				new Listcell((index+1) +"").setParent(item);
				new Listcell(level.getLevel().getLevelName()).setParent(item);
				new Listcell((LevelMethod.fromCode(level.getLevel().getLevelMethod())).getMessage()).setParent(item);
				String ruleExpression = "";
				List<String> parsedRule = AVMRuleEvaluationManager.unwrapSingleQouteMark(level.getLevel().getRule());
				for (int i = 0; i < parsedRule.size(); i++) {
					int j = i;
					AVMRuleStatement ruleStatement = new AVMRuleStatement();
					ruleStatement.setAvmRuleStatementId(UUID.randomUUID());
					AVMRuleMapping avmRuleMapping = null;
					AVMSetupManager avmSetupManager = (AVMSetupManager) SpringUtil.getBean("avmSetupManager");
					
					String conjunction = "";
					if (parsedRule.size() > 3 && i < parsedRule.size() - 1) {
						if (j > 0) {
							ConjunctionType conjunctionType = ConjunctionType.getConjunctionTypeFromString(parsedRule.get((++i % 4) + j));
							conjunction = conjunctionType.toString();
							ruleStatement.setConjunctionType(conjunctionType);
							j = i;
						}						
					}
					
					try {
						avmRuleMapping = avmSetupManager.getAVMRuleMapping(parsedRule.get((i % 4) + j));
						ruleStatement.setAvmRuleMapping(avmRuleMapping);
					} catch (Exception e) {
						logger.error(e.getMessage());
						logger.error(e.getMessage(), e);
					}
					String lookupHeaderId = avmRuleMapping.getLookupHeaderId();
					short withValue = avmRuleMapping.getWithValue();
					String lookupValue = "";
					String operator = "";
					String value = "";
					if (withValue == 1) {
						lookupValue = "\'" + parsedRule.get((++i % 4) + j) + "\'";
						if (avmRuleMapping.getLookupHeaderId().equals("Number")) {
							DecimalFormat format = new DecimalFormat("#,##0.00");
							double doubleValue = Double.parseDouble(parsedRule.get((++i % 4) + j));
							value = format.format(doubleValue);
							ruleStatement.setValue(doubleValue);
						} else {
							String lookupName = (avmRuleMapping.getAttributeLabel().replaceAll(" ", "_")).toUpperCase();
							value = parsedRule.get((++i % 4) + j);
							KeyValue keyValue = ruleLookupServiceImpl.getLookupValue(lookupName, value);
							ruleStatement.setValue(value);
							value = (String) keyValue.getDescription();
						}
					} else {
						lookupValue = "\'" + parsedRule.get((++i % 4) + j) + "\'" +
								"\'" + parsedRule.get((++i % 4) + j) + "\'";
					}
					AVMRuleLookupDetail lookupDetail = null;
					operator = lookupValue;
					
					lookupDetail = avmSetupManager.getAVMRuleLookupDetail(lookupHeaderId, lookupValue);
					ruleStatement.setAvmRuleLookupDetail(lookupDetail);
					if (lookupDetail != null) {
						operator = lookupDetail.getDescription();
					}
										
					ruleExpression += conjunction + " " + avmRuleMapping.getAttributeLabel() + " "
								+ operator + " " 
								+ value + " ";
					
					completeRuleStatement.getAvmRuleStatementList().add(ruleStatement);
		 		}
				new Listcell(ruleExpression).setParent(item);
				
				String listApprover = getListApproverByLevel(level.getApprovers());
				new Listcell(listApprover).setParent(item);
				
				A link = new A(Labels.getLabel("common.detail"));
				link.setStyle("color:blue");
				link.setDisabled(disabledHyperlinkDetail);
				link.addEventListener("onClick", new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event arg0) throws Exception {
						params = new HashMap<String, Object>();
						params.put("parent", thisComposer);
						params.put("level", level);
						params.put("index", index);
						params.put("completeRuleStatement", completeRuleStatement);
						Window window = (Window)Executions.createComponents(
								"~./hcms/workflow/workflow_approval_model_add_level.zul", getSelf().getParent(), params);
						window.setClosable(true);
						window.setMaximized(false);
						window.setWidth("70%");
						window.doModal();
					}
				});
				Listcell lc = new Listcell();
				link.setParent(lc);
				lc.setParent(item);				
			}
		};
		
		return listitemRenderer;
	}
	
	private String getListApproverByLevel(List<AVMApprover> approvers) throws FifException {
		List<String> approversName = new ArrayList<String>();
		String name = "";
		if (approvers != null) {
			for (AVMApprover approver : approvers) {
				name = avmAdapterServiceImpl.getApproverName(approver);
				approversName.add(name);
			}			
		}
		return StringUtils.join(approversName, ", ");
	}
	
	@Listen("onSelect = listbox#cmbVersion") 
	public void onSelect() throws FifException {
		logger.info("onSelect");
		if (cmbVersion.getSelectedItem().getValue().toString().equals(NEW_VERSION)) {
			lastVersionNumber = avmAdapterServiceImpl.getLastVersionNumber(avmDto.getAvm().getAvmId());
			currentVersions = getSelectedVersion(lastVersionNumber);
			if (!avmAdapterServiceImpl.isFuture(currentVersions)) {
				setValueData(currentVersions);
				avmDto.getAvmVersions().setAvmVersionId(0);
				lastVersionNumber++;
				dtbEffectiveFrom.setDisabled(false);
				dtbEffectiveTo.setDisabled(false);
				btnSave.setDisabled(false);
				btnDelete.setDisabled(true);
				dtbEffectiveFrom.setValue(DateUtil.add(DateUtil.truncate(new Date()), Calendar.DATE, 1));
				disabledButton(false);				
			} else {
				Messagebox.show(Labels.getLabel("swf.err.hasFutureVersion"));
			}
		} else {
			int selectedVersionNumber = Integer.parseInt(cmbVersion.getSelectedItem().getValue().toString());
			lastVersionNumber = selectedVersionNumber;
			currentVersions = getSelectedVersion(selectedVersionNumber);
			setValueData(currentVersions);
		}		
		
	}
	
	@Listen("onClick = button#btnAddApprovalLevel")
	public void onAddApprovalLevel(Event e) {
		params = new HashMap<String, Object>();
		params.put("parent", thisComposer);
		
		Window window = (Window)Executions.createComponents(
				"~./hcms/workflow/workflow_approval_model_add_level.zul", getSelf().getParent(), params);
		window.setClosable(true);
		window.setMaximized(false);
		window.setWidth("70%");
		window.doModal();
	}

	@Listen("onClick = button#btnTop")
	public void top() {
		if (listModelAvmLevelApprover.getSelection().size() == 1) {
			int i = 0;
			Iterator<LevelApproverDTO> iterator = new LinkedHashSet<LevelApproverDTO>(listModelAvmLevelApprover.getSelection()).iterator();
			while (iterator.hasNext()) {
				LevelApproverDTO selectedItem = iterator.next();
				listModelAvmLevelApprover.remove(selectedItem);
				listModelAvmLevelApprover.add(i++, selectedItem);
				listModelAvmLevelApprover.addToSelection(selectedItem);
			}
			lbxApprovalLevel.setItemRenderer(getItemRenderer());			
		}
	}
	
	@Listen("onClick = button#btnUp")
	public void up() {
		Set<LevelApproverDTO> selected = listModelAvmLevelApprover.getSelection();
		if (selected.isEmpty()) {
			return;
		}
		if (selected.size() == 1) {
			int index = listModelAvmLevelApprover.indexOf(selected.iterator().next());
			if (index == 0 || index < 0) {
				return;
			}
			LevelApproverDTO selectedItem = listModelAvmLevelApprover.get(index);
			listModelAvmLevelApprover.remove(selectedItem);
			listModelAvmLevelApprover.add(--index, selectedItem);
			listModelAvmLevelApprover.addToSelection(selectedItem);
			lbxApprovalLevel.setItemRenderer(getItemRenderer());			
		}
	}
	
	@Listen("onClick = button#btnDown")
	public void down() {
		Set<LevelApproverDTO> selected = listModelAvmLevelApprover.getSelection();
		if (selected.isEmpty()) {
			return;
		}
		if (selected.size() == 1) {
			int index = listModelAvmLevelApprover.indexOf(selected.iterator().next());
			if (index == listModelAvmLevelApprover.size() - 1 || index < 0) {
				return;
			}
			LevelApproverDTO selectedItem = listModelAvmLevelApprover.get(index);
			listModelAvmLevelApprover.remove(selectedItem);
			listModelAvmLevelApprover.add(++index, selectedItem);
			listModelAvmLevelApprover.addToSelection(selectedItem);
			lbxApprovalLevel.setItemRenderer(getItemRenderer());			
		}
	}
	
	@Listen("onClick = button#btnBottom")
	public void bottom() {
		if (listModelAvmLevelApprover.getSelection().size() == 1) {
			Iterator<LevelApproverDTO> iterator = new LinkedHashSet<LevelApproverDTO>(listModelAvmLevelApprover.getSelection()).iterator();
			while (iterator.hasNext()) {
				LevelApproverDTO selectedItem = iterator.next();
				listModelAvmLevelApprover.remove(selectedItem);
				listModelAvmLevelApprover.add(selectedItem);
				listModelAvmLevelApprover.addToSelection(selectedItem);
			}
			lbxApprovalLevel.setItemRenderer(getItemRenderer());
		}
	}
	
	@Listen("onClick = button#btnDeleteApprovalLevel")
	public void deleteLevel() {
		Set<LevelApproverDTO> selected = listModelAvmLevelApprover.getSelection();
		if (selected.isEmpty()) {
			return;
		}
		while (selected.iterator().hasNext()) {
			LevelApproverDTO selectedItem = selected.iterator().next();
			listModelAvmLevelApprover.remove(selectedItem);			
		}
		lbxApprovalLevel.setItemRenderer(getItemRenderer());
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtAVMName);
		ErrorMessageUtil.clearErrorMessage(dtbEffectiveFrom);
		ErrorMessageUtil.clearErrorMessage(errListApprovalLevel);
	}
	
	@Listen("onClick = button#btnSave")
	public void onSave() throws FifException {
		String confirmation = avmDto.getAvm().getAvmId() == null ? Labels.getLabel("common.confirmationSave") : Labels.getLabel("common.confirmationSaveVersion");
		Messagebox.show(confirmation, Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			
			private static final long serialVersionUID = 8263493569194777769L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						clearErrorMessage();
						avmDto.getAvm().setAvmName(txtAVMName.getValue());
						avmDto.getAvmVersions().setEffectiveStartDate(dtbEffectiveFrom.getValue() != null ? DateUtil.truncate(dtbEffectiveFrom.getValue()) : null);
						avmDto.getAvmVersions().setEffectiveEndDate(DateUtils.truncate(dtbEffectiveTo.getValue(), Calendar.DATE));
						avmDto.getAvmVersions().setVersionNumber(lastVersionNumber);
						renderListApproval();
						avmAdapterServiceImpl.saveAVMTemplate(avmDto, dateFromBefore);
						Messagebox.show(Labels.getLabel("common.saveSuccessfully"));
						Executions.createComponents("~./hcms/workflow/workflow_approval_model_inquiry.zul", getSelf().getParent(), null);
						getSelf().detach();
					} catch (ValidationException ve) {
						showErrorMessage(ve.getConstraintViolations());
					}		
				}
			}
		});			
	}
	
	private void showErrorMessage(Map<String, String> maps) {		
		if(maps.get(ApprovalModelValidator.APPROVAL_MODEL_NAME_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(txtAVMName, 
				maps.get(ApprovalModelValidator.APPROVAL_MODEL_NAME_VALIDATE));
		}
		if(maps.get(ApprovalModelValidator.EFFECTIVE_START_DATE_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(dtbEffectiveFrom, 
				maps.get(ApprovalModelValidator.EFFECTIVE_START_DATE_VALIDATE));
		}
		if(maps.get(ApprovalModelValidator.LIST_LEVEL_APPROVAL_VALIDATE) != null) {
			ErrorMessageUtil.setErrorMessage(errListApprovalLevel, 
				maps.get(ApprovalModelValidator.LIST_LEVEL_APPROVAL_VALIDATE));
		}		
	}
	
	public void renderListApproval() {
		listAvmLevels.clear();
		listAvmApprovers.clear();
		int index = 0;
		for (LevelApproverDTO avmLevelApprover : listModelAvmLevelApprover) {
			avmLevelApprover.getLevel().setLevelSequence(index);
			listAvmLevels.add(avmLevelApprover.getLevel());
			if (avmLevelApprover.getApprovers() != null) {
				for (AVMApprover approver : avmLevelApprover.getApprovers()) {
					approver.setLevelSequence(index);
					listAvmApprovers.add(approver);
				}
			}
			index++;
		}
		avmDto.setListLevel(listAvmLevels);
		avmDto.setListApprovers(listAvmApprovers);
	}
	
	@Listen("onClick = button#btnDelete")
	public void onDelete() throws FifException {
		Messagebox.show(Labels.getLabel("common.confirmationDelete"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			
			private static final long serialVersionUID = 8478465328123300993L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					avmAdapterServiceImpl.deleteAVMTemplate(avmDto.getAvmVersions());
					lastVersionNumber = avmAdapterServiceImpl.getLastVersionNumber(avmDto.getAvm().getAvmId());
					Messagebox.show(Labels.getLabel("common.dataHasBeenDeleted"));
					if (lastVersionNumber > 0) {
						getVersion(lastVersionNumber);			
					} else {
						Executions.createComponents("~./hcms/workflow/workflow_approval_model_inquiry.zul", getSelf().getParent(), null);
						getSelf().detach();
					}
					
				}
			}
		});		
	}
	
	@Listen("onClick = button#btnCancel")
	public void onCancel() throws FifException {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			
			private static final long serialVersionUID = -498815426536858448L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					Executions.createComponents("~./hcms/workflow/workflow_approval_model_inquiry.zul", getSelf().getParent(), null);
					getSelf().detach();
				}
			}
		});		
	}
	
	public void doAfterAddApprovalLevel(LevelApproverDTO insertedAvmLevelApprover, int index) {
		if (index >= 0) {
			listModelAvmLevelApprover.remove(index);
			listModelAvmLevelApprover.add(index, insertedAvmLevelApprover);
		} else {
			listModelAvmLevelApprover.add(insertedAvmLevelApprover);
		}
		listModelAvmLevelApprover.setMultiple(true);
		lbxApprovalLevel.setModel(listModelAvmLevelApprover);
		lbxApprovalLevel.setItemRenderer(getItemRenderer());
	}	
}
