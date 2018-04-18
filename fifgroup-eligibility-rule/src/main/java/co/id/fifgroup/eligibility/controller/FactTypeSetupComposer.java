package co.id.fifgroup.eligibility.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.core.ui.DefaultSetupComposer;
import co.id.fifgroup.core.ui.EnumListbox;
import co.id.fifgroup.core.ui.MessageBoxUtil;
import co.id.fifgroup.eligibility.constant.RetrievalMode;
import co.id.fifgroup.eligibility.dto.FactTypeDTO;
import co.id.fifgroup.eligibility.dto.FactTypeFieldDTO;
import co.id.fifgroup.eligibility.service.FactTypeSetupService;
import co.id.fifgroup.eligibility.validation.FactTypeValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class FactTypeSetupComposer extends SelectorComposer<Window>
	implements DefaultSetupComposer<FactTypeDTO>{

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(FactTypeSetupComposer.class);
	
	@Wire private Textbox txtFactTypeName;
	@Wire private Listbox lstFields;
	@Wire private EnumListbox<RetrievalMode> lstRetrievalMode;
	@Wire private Textbox txtSqlQuery;
	@Wire private Grid gridSqlQuery;
	
	
	@WireVariable
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate = true)
	private transient FactTypeSetupService factTypeSetupServiceImpl;
	
	private FactTypeDTO subject;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		lstRetrievalMode.setEnum(RetrievalMode.SUPPLIED);
		if (arg.get("subject") != null) {
			subject = (FactTypeDTO) arg.get("subject");
		} else {
			subject = new FactTypeDTO();
		}
		render(subject);
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		MessageBoxUtil.saveConfirmation(new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				populate(subject);
				try {
					clearErrorMessages();
					factTypeSetupServiceImpl.save(subject);
					doBack();
				} catch (ValidationException vex) {
					logger.error(vex.getAllMessages());
					showErrorMessages(vex);
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
		Executions.createComponents("~./hcms/eligibility-rule/fact_type_inquiry.zul", getSelf().getParent(), null);
		getSelf().detach();		
	}

	@Listen ("onSelect = #lstRetrievalMode")
	public void onRetrievalModeChange() {
		if (RetrievalMode.QUERY == lstRetrievalMode.getSelectedValue()) {
			gridSqlQuery.setVisible(true);
			txtSqlQuery.setValue(subject.getSqlQuery());
		} else {
			gridSqlQuery.setVisible(false);
			txtSqlQuery.setValue("");
		}
	}
	
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void populate(FactTypeDTO subject) {
		subject.setName(txtFactTypeName.getValue());
		subject.setRetrievalMode(lstRetrievalMode.getSelectedValue());
		subject.setSqlQuery(txtSqlQuery.getValue());
		subject.setFields(((ListModelList) lstFields.getModel()).getInnerList());
		logger.debug(subject.toString());
	}

	@Override
	public void render(FactTypeDTO subject) {
		txtFactTypeName.setValue(subject.getName());
		lstRetrievalMode.setSelectedValue(subject.getRetrievalMode());
		onRetrievalModeChange();
		txtSqlQuery.setValue(subject.getSqlQuery());
		lstFields.setModel(new ListModelList<>(subject.getFields()));
		((ListModelList<?>) lstFields.getModel()).setMultiple(true);
	}

	@Override
	public void showErrorMessages(ValidationException ex) {
		ErrorMessageUtil.setErrorMessage(txtFactTypeName, ex.getMessage(FactTypeValidator.NAME));
		ErrorMessageUtil.setErrorMessage(lstFields, ex.getMessage(FactTypeValidator.FIELDS), true);
		ErrorMessageUtil.setErrorMessage(lstRetrievalMode, ex.getMessage(FactTypeValidator.RETRIEVAL_MODE));
		ErrorMessageUtil.setErrorMessage(txtSqlQuery, ex.getMessage(FactTypeValidator.QUERY));
	}

	@Override
	public void clearErrorMessages() {
		ErrorMessageUtil.clearErrorMessage(txtFactTypeName);
		ErrorMessageUtil.clearErrorMessage(lstFields);
		ErrorMessageUtil.clearErrorMessage(lstRetrievalMode);
		ErrorMessageUtil.clearErrorMessage(txtSqlQuery);
	}
	
	@Listen("onClick = #btnAddField") 
	public void addField(){
		Map<String, Object> arg = new HashMap<>();
		arg.put("fields", lstFields.getModel());
		Executions.createComponents("~./hcms/eligibility-rule/popup_fact_type_field_detail.zul", getSelf().getParent(), arg);
	}
	
	@Listen("onClick = #btnRemoveField")
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void removeField() {
		ListModelList<FactTypeFieldDTO> model = (ListModelList) lstFields.getModel();
		Set<FactTypeFieldDTO> deleted = new HashSet<>();
		
		for(FactTypeFieldDTO field : model.getSelection()) {
			if (field.getEditable())
				deleted.add(field);
		}
		
		for(FactTypeFieldDTO field : deleted) {
			model.remove(field);
		}
		
		
	}
	
	@Listen("onFieldDetail = #winFactTypeSetup")
	public void showFieldDetail(ForwardEvent event) {
		Map<String, Object> arg = new HashMap<>();
		arg.put("fields", lstFields.getModel());
		arg.put("field", event.getData());
		Executions.createComponents("~./hcms/eligibility-rule/popup_fact_type_field_detail.zul", getSelf().getParent(), arg);
	}
	
	@Listen("onClick = #btnTest")
	public void testQuery() {
		Map<String, Object> arg = new HashMap<>();
		arg.put("query", txtSqlQuery.getValue());
		Executions.createComponents("~./hcms/eligibility-rule/popup_fact_type_test_query.zul", getSelf().getParent(), arg);
	}
	

}
