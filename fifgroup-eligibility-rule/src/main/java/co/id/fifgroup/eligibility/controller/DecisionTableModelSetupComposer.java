package co.id.fifgroup.eligibility.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
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
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import com.google.common.collect.Maps;

import co.id.fifgroup.core.ui.DefaultSetupComposer;
import co.id.fifgroup.core.ui.MessageBoxUtil;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.eligibility.constant.RetrievalMode;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.dto.FactTypeDTO;
import co.id.fifgroup.eligibility.service.DecisionTableModelSetupService;
import co.id.fifgroup.eligibility.service.FactTypeSetupService;
import co.id.fifgroup.eligibility.validation.DecisionTableModelValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class DecisionTableModelSetupComposer extends SelectorComposer<Window>
	implements DefaultSetupComposer<DecisionTableModelDTO>{

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(DecisionTableModelSetupComposer.class);
	
	@Wire private Textbox txtDecisionTableModelName;
	@Wire private Listbox lstVersion;
	@Wire private Listbox lstResultType;
	
	@Wire private Listbox lstConditions;
	@Wire private Listbox lstResults;
	
	@Wire private Button btnAddCondition;
	@Wire private Button btnRemoveCondition;
	
	@Wire private Button btnAddResult;
	@Wire private Button btnRemoveResult;
	@Wire private Button btnSave;
	
	
	@WireVariable
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate = true)
	private transient DecisionTableModelSetupService decisionTableModelSetupServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient FactTypeSetupService factTypeSetupServiceImpl;
	
	private DecisionTableModelDTO subject;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		// set renderer
		lstVersion.setItemRenderer(getVersionRenderer());
		
		// populate resultType List
		FactTypeDTO example = new FactTypeDTO();
		example.setRetrievalMode(RetrievalMode.SUPPLIED);
		lstResultType.setModel(new ListModelList<>(factTypeSetupServiceImpl.findByExample(example)));
		lstResultType.renderAll();
		if (arg.get("subject") != null) {
			subject = (DecisionTableModelDTO) arg.get("subject");
			List<Integer> versions = decisionTableModelSetupServiceImpl.getAvailableVersions(subject.getId());
			subject = decisionTableModelSetupServiceImpl.findByIdAndVersionNumber(subject.getId(), versions.get(versions.size() - 1));
			txtDecisionTableModelName.setDisabled(true);
			lstResultType.setDisabled(true);
			lstVersion.setModel(new ListModelList<Entry<String, Integer>>(getVersionEntry(subject.getId())));
			lstVersion.renderAll();
		} else {
			subject = new DecisionTableModelDTO();
			lstVersion.setModel(new ListModelList<Entry<String, Integer>>(getVersionEntry(subject.getId())));
			lstVersion.setSelectedIndex(0);
			subject.setVersionNumber(null);
		}
		render(subject);
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		MessageBoxUtil.saveConfirmation(new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				clearErrorMessages();
				populate(subject);
				try {
					decisionTableModelSetupServiceImpl.save(subject);
					doBack();
				} catch(ValidationException vex) {
					showErrorMessages(vex);
					logger.error(vex.getAllMessages());
				} catch (Exception ex) {
					throw ex;
				}
				
			}
		});
		
	}
	
	@Listen ("onClick = button#btnCancel")
	public void onCancel() {
		MessageBoxUtil.cancelConfirmation(new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				doBack();

			}
		});
	}
	
	public void doBack() {		
		logger.debug("cancel decision table model setup");
		Component parent = getSelf().getParent();
		getSelf().detach();
		Executions.createComponents("~./hcms/eligibility-rule/decision_table_model_inquiry.zul", parent, null);
		
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void populate(DecisionTableModelDTO subject) {
		subject.setName(txtDecisionTableModelName.getValue().trim());
		
		subject.setConditions(((ListModelList) lstConditions.getModel()).getInnerList());
		
		if (lstResultType.getSelectedItem() != null)
			subject.setResultType((FactTypeDTO)lstResultType.getSelectedItem().getValue());
		
		subject.setResults(((ListModelList) lstResults.getModel()).getInnerList());
		
		logger.debug(subject.toString());
	}

	@Override
	public void render(DecisionTableModelDTO subject) {
		txtDecisionTableModelName.setValue(subject.getName());
			
		if (subject.getVersionNumber() != null) {
			disableComponents();
			for (Listitem item : lstVersion.getItems()) {
				Integer value = item.getValue();
				if (subject.getVersionNumber().equals(value)){
					item.setSelected(true);
					break;
				}
			}
		}
		
		lstConditions.setModel(new ListModelList<>(subject.getConditions()));
		((ListModelList<?>) lstConditions.getModel()).setMultiple(true);
		lstConditions.renderAll();
		
		// render FactType List
		for (Listitem item : lstResultType.getItems()) {
			if (item.getValue() != null 
					&& item.getValue().equals(subject.getResultType())){
				item.setSelected(true);
				Events.sendEvent(lstResultType, new Event(Events.ON_SELECT, lstResultType));
				break;
			}
		}
		
		lstResults.setModel(new ListModelList<>(subject.getResults()));
		((ListModelList<?>) lstResults.getModel()).setMultiple(true);
		
	}

	@Override
	public void showErrorMessages(ValidationException ex) {
		ErrorMessageUtil.setErrorMessage(txtDecisionTableModelName, ex.getMessage(DecisionTableModelValidator.NAME));
		ErrorMessageUtil.setErrorMessage(lstConditions, ex.getMessage(DecisionTableModelValidator.COLUMNS), true);
		ErrorMessageUtil.setErrorMessage(lstResultType, ex.getMessage(DecisionTableModelValidator.RESULT_TYPE));
		ErrorMessageUtil.setErrorMessage(lstResults, ex.getMessage(DecisionTableModelValidator.RESULTS), true);
	}

	@Override
	public void clearErrorMessages() {
		ErrorMessageUtil.clearErrorMessage(txtDecisionTableModelName);
		ErrorMessageUtil.clearErrorMessage(lstConditions);
		ErrorMessageUtil.clearErrorMessage(lstResultType);
		ErrorMessageUtil.clearErrorMessage(lstResults);
	}
	
	@Listen("onClick = #btnAddCondition")
	public void addCondition() {
		Map<String, Object> arg = new HashMap<>();
		arg.put("conditions", lstConditions.getModel());
		Executions.createComponents("~./hcms/eligibility-rule/popup_decision_table_column_detail.zul", getSelf().getParent(), arg);
	}
	
	@Listen("onConditionDetail = #winDecisionTableModelSetup")
	public void onConditionDetail(ForwardEvent event) {
		Map<String, Object> arg = new HashMap<>();
		arg.put("conditions", lstConditions.getModel());
		arg.put("condition", event.getData());
		Executions.createComponents("~./hcms/eligibility-rule/popup_decision_table_column_detail.zul", getSelf().getParent(), arg);
	}
	
	@Listen("onClick = #btnRemoveCondition")
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void removeCondition() {
		ListModelList<DecisionTableColumnDTO> model = (ListModelList) lstConditions.getModel();
		Set<DecisionTableColumnDTO> deleted = new HashSet<>();
		
		for(DecisionTableColumnDTO column : model.getSelection()) {
			if (column.getEditable())
				deleted.add(column);
		}
		
		for(DecisionTableColumnDTO field : deleted) {
			model.remove(field);
		}
	}
	
	@Listen("onClick = #btnAddResult")
	public void addResult() {
		if (null != lstResultType.getSelectedItem()) {
			Map<String, Object> arg = new HashMap<>();
			arg.put("results", lstResults.getModel());
			arg.put("factType", lstResultType.getSelectedItem().getValue());
			Executions.createComponents("~./hcms/eligibility-rule/popup_decision_table_result_detail.zul", getSelf().getParent(), arg);
		}
	}
	
	@Listen("onClick = #btnRemoveResult")
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void removeResult() {
		ListModelList<DecisionTableColumnDTO> model = (ListModelList) lstResults.getModel();
		Set<DecisionTableColumnDTO> deleted = new HashSet<>();
		
		for(DecisionTableColumnDTO column : model.getSelection()) {
			if (column.getEditable())
				deleted.add(column);
		}
		
		for(DecisionTableColumnDTO field : deleted) {
			model.remove(field);
		}
	}
	
	@Listen("onResultDetail = #winDecisionTableModelSetup")
	public void onResultDetail(ForwardEvent event) {
		Map<String, Object> arg = new HashMap<>();
		arg.put("results", lstResults.getModel());
		arg.put("result", event.getData());
		arg.put("factType", lstResultType.getSelectedItem().getValue());
		Executions.createComponents("~./hcms/eligibility-rule/popup_decision_table_result_detail.zul", getSelf().getParent(), arg);
	}
	
	@Listen("onSelect = #lstVersion")
	public void onVersionChange() {
		Integer version = lstVersion.getSelectedItem().getValue();
		if (version.equals(0)) {
			subject.setVersionNumber(null);
			enableComponents();
			for (DecisionTableColumnDTO condition : subject.getConditions()) {
				condition.setEditable(true);
			}
			for (DecisionTableColumnDTO result : subject.getResults()) {
				result.setEditable(true);
			}
		} else {
			subject = decisionTableModelSetupServiceImpl.findByIdAndVersionNumber(subject.getId(), version);
			disableComponents();
		}
		render(subject);
	}

	private List<Entry<String, Integer>> getVersionEntry(String id) {
		List<Entry<String, Integer>> result = new ArrayList<>();
		
		List<Integer> versions = decisionTableModelSetupServiceImpl.getAvailableVersions(id);
		for (Integer version : versions) {
			result.add(Maps.immutableEntry(version.toString(), version));
		}
		result.add(Maps.immutableEntry("New", 0));
		
		return result;
	}

	private SerializableListItemRenderer<Entry<String, Integer>> getVersionRenderer() {
		return new SerializableListItemRenderer<Map.Entry<String,Integer>>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, Entry<String, Integer> data,
					int index) throws Exception {
				item.setValue(data.getValue());
				item.setLabel(data.getKey());
			}
		};
	}
	
	private void disableComponents() {
		lstResultType.setDisabled(true);
		lstResultType.setDisabled(true);
		btnAddCondition.setDisabled(true);
		btnRemoveCondition.setDisabled(true);
		btnAddResult.setDisabled(true);
		btnRemoveResult.setDisabled(true);
		btnSave.setDisabled(true);
	}
	
	private void enableComponents() {
		lstResultType.setDisabled(false);
		btnAddCondition.setDisabled(false);
		btnRemoveCondition.setDisabled(false);
		btnAddResult.setDisabled(false);
		btnRemoveResult.setDisabled(false);
		btnSave.setDisabled(false);
	}
	
	
	
}