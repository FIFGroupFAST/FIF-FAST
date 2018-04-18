package co.id.fifgroup.eligibility.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.core.ui.DefaultSetupComposer;
import co.id.fifgroup.core.ui.EnumListbox;
import co.id.fifgroup.eligibility.constant.ColumnType;
import co.id.fifgroup.eligibility.constant.Operator;
import co.id.fifgroup.eligibility.constant.ValueType;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.FactTypeDTO;
import co.id.fifgroup.eligibility.dto.FactTypeFieldDTO;
import co.id.fifgroup.eligibility.service.FactTypeSetupService;
import co.id.fifgroup.eligibility.validation.DecisionTableColumnValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class DecisionTableColumnDetailComposer extends SelectorComposer<Window>
	implements DefaultSetupComposer<DecisionTableColumnDTO>{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(DecisionTableColumnDetailComposer.class);
	
	@Wire private Textbox txtColumnName;
	@Wire private Listbox lstFactType;
	@Wire private Listbox lstField;
	@Wire private EnumListbox<ValueType> lstValueType;
	@Wire private EnumListbox<Operator> lstOperator;
	@Wire private Textbox txtValue;
	@Wire private Row rowValue;
	
	@WireVariable(rewireOnActivate = true)
	private transient FactTypeSetupService factTypeSetupServiceImpl;
	
	@WireVariable
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient DecisionTableColumnValidator decisionTableColumnValidator;
	
	private ListModelList<DecisionTableColumnDTO> conditions;
	private DecisionTableColumnDTO condition;
	
	@Override
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		lstOperator.setEnum(Operator.EQUALS);
		lstValueType.setEnum(ValueType.SCALAR);
		lstFactType.setModel(new ListModelList<>(factTypeSetupServiceImpl.findByExample(new FactTypeDTO())));
		lstFactType.renderAll();
		
		conditions = (ListModelList<DecisionTableColumnDTO>) arg.get("conditions");
		condition = (DecisionTableColumnDTO) arg.get("condition");
		
		render(condition);
	}
	
	@Listen("onClick = #btnSave")
	public void onSave() {
		clearErrorMessages();
		try {
			if (null != condition) {
				int index = conditions.indexOf(condition);
				populate(condition);
				decisionTableColumnValidator.validate(condition);
				conditions.set(index, condition);
			} else {
				DecisionTableColumnDTO subject = new DecisionTableColumnDTO();
				populate(subject);
				decisionTableColumnValidator.validate(subject);
				conditions.add(subject);
			}
			getSelf().onClose();
		} catch (ValidationException vex) {
			showErrorMessages(vex);
			logger.error(vex.getMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
	}


	@Override
	public void populate(DecisionTableColumnDTO subject) {
		subject.setName(txtColumnName.getValue());
		subject.setColumnType(ColumnType.CONDITION);
		
		Listitem factTypeItem = lstFactType.getSelectedItem();
		subject.setFactType(null != factTypeItem ? (FactTypeDTO) factTypeItem.getValue() : null);
		
		Listitem fieldItem = lstField.getSelectedItem();
		subject.setField(null != fieldItem ? (FactTypeFieldDTO) fieldItem.getValue() : null);
		
		subject.setOperator(lstOperator.getSelectedValue());
		subject.setValueType(lstValueType.getSelectedValue());
		subject.setValueList(txtValue.getValue());
	}


	@Override
	public void render(DecisionTableColumnDTO subject) {
		if (condition !=  null) {
			txtColumnName.setValue(subject.getName());
			lstOperator.setSelectedValue(subject.getOperator());
			lstValueType.setSelectedValue(subject.getValueType());
			onSelectValueType();
			for (Listitem item : lstFactType.getItems()) {
				if (item.getValue().equals(subject.getFactType())){
					item.setSelected(true);
					Events.sendEvent(lstFactType, new Event(Events.ON_SELECT, lstFactType));
					break;
				}
			}
			for (Listitem item :lstField.getItems()) {
				if (item.getValue().equals(subject.getField())) {
					item.setSelected(true);
					break;
				}
			}
			txtValue.setValue(subject.getValueList());
		}
		
	}


	@Override
	public void showErrorMessages(ValidationException ex) {
		ErrorMessageUtil.setErrorMessage(txtColumnName, ex.getMessage(DecisionTableColumnValidator.COLUMN_NAME));
		ErrorMessageUtil.setErrorMessage(lstFactType, ex.getMessage(DecisionTableColumnValidator.COLUMN_FACT_TYPE));
		ErrorMessageUtil.setErrorMessage(lstField, ex.getMessage(DecisionTableColumnValidator.COLUMN_FIELD));
		ErrorMessageUtil.setErrorMessage(lstOperator, ex.getMessage(DecisionTableColumnValidator.OPERATOR));
		ErrorMessageUtil.setErrorMessage(lstValueType, ex.getMessage(DecisionTableColumnValidator.VALUE_TYPE));
		ErrorMessageUtil.setErrorMessage(txtValue, ex.getMessage(DecisionTableColumnValidator.VALUE_LIST));
	}


	@Override
	public void clearErrorMessages() {
		ErrorMessageUtil.clearErrorMessage(txtColumnName);
		ErrorMessageUtil.clearErrorMessage(lstFactType);
		ErrorMessageUtil.clearErrorMessage(lstField);
		ErrorMessageUtil.clearErrorMessage(lstOperator);
		ErrorMessageUtil.clearErrorMessage(lstValueType);
		ErrorMessageUtil.clearErrorMessage(txtValue);
	}
	
	@Listen("onSelect = #lstFactType")
	public void onSelectFactType(){
		FactTypeDTO factType = (FactTypeDTO) lstFactType.getSelectedItem().getValue();
		lstField.setModel(new ListModelList<>(factType.getFields()));
		lstField.renderAll();
	}

	@Listen("onClick = #btnCancel")
	public void onClose() {
		getSelf().onClose();
	}
	
	@Listen("onSelect = #lstValueType")
	public void onSelectValueType() {
		if (lstValueType.getSelectedValue() == ValueType.LIST) {
			rowValue.setVisible(true);
		} else {
			rowValue.setVisible(false);
		}
	}
}
