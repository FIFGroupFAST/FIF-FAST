package co.id.fifgroup.eligibility.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import co.id.fifgroup.eligibility.constant.ValueType;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.FactTypeDTO;
import co.id.fifgroup.eligibility.dto.FactTypeFieldDTO;
import co.id.fifgroup.eligibility.validation.DecisionTableColumnValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class DecisionTableResultDetailComposer extends SelectorComposer<Window>
	implements DefaultSetupComposer<DecisionTableColumnDTO>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DelegatingVariableResolver.class);
	
	@Wire private Listbox lstFieldName;
	@Wire private Textbox txtColumnName;
	@Wire private EnumListbox<ValueType> lstValueType;
	@Wire private Textbox txtValue;
	@Wire private Row rowValue;
	
	@WireVariable
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient DecisionTableColumnValidator decisionTableColumnValidator;
	
	private ListModelList<DecisionTableColumnDTO> results;
	private DecisionTableColumnDTO result;
	
	private FactTypeDTO factType;
	
	@Override
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		factType = (FactTypeDTO) arg.get("factType");
		result = (DecisionTableColumnDTO) arg.get("result");
		results = (ListModelList<DecisionTableColumnDTO>) arg.get("results");
		
		lstValueType.setEnum(ValueType.SCALAR);
		lstFieldName.setModel(new ListModelList<>(factType.getFields()));
		lstFieldName.renderAll();
		
		render(result);
	}

	@Override
	public void populate(DecisionTableColumnDTO subject) {
		subject.setName(txtColumnName.getValue());
		subject.setColumnType(ColumnType.RESULT);
		subject.setFactType(factType);
		if (null != lstFieldName.getSelectedItem() && null != lstFieldName.getSelectedItem().getValue())
			subject.setField((FactTypeFieldDTO) lstFieldName.getSelectedItem().getValue());
		subject.setValueType(lstValueType.getSelectedValue());
		subject.setValueList(txtValue.getValue());
	}

	@Override
	public void render(DecisionTableColumnDTO subject) {
		if (null != result) {
			txtColumnName.setValue(subject.getName());
			lstValueType.setSelectedValue(subject.getValueType());
			onSelectValueType();
			for (Listitem item : lstFieldName.getItems()) {
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
		ErrorMessageUtil.setErrorMessage(lstFieldName, ex.getMessage(DecisionTableColumnValidator.COLUMN_FIELD));
		ErrorMessageUtil.setErrorMessage(lstValueType, ex.getMessage(DecisionTableColumnValidator.VALUE_TYPE));
		ErrorMessageUtil.setErrorMessage(txtValue, ex.getMessage(DecisionTableColumnValidator.VALUE_LIST));
		
	}

	@Override
	public void clearErrorMessages() {
		ErrorMessageUtil.clearErrorMessage(txtColumnName);
		ErrorMessageUtil.clearErrorMessage(lstFieldName);
		ErrorMessageUtil.clearErrorMessage(lstValueType);
		ErrorMessageUtil.clearErrorMessage(txtValue);
		
	}
	
	@Listen("onClick = #btnSave")
	public void onSave() {
		clearErrorMessages();
		try {
			if (null != result) {
				int index = results.indexOf(result);
				populate(result);
				decisionTableColumnValidator.validate(result);
				results.set(index, result);
				
			} else {
				DecisionTableColumnDTO subject = new DecisionTableColumnDTO();
				populate(subject);
				decisionTableColumnValidator.validate(subject);
				results.add(subject);
			}
			getSelf().onClose();
		} catch (ValidationException vex) {
			showErrorMessages(vex);
			logger.error(vex.getMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
	
	@Listen("onClick = #btnCancel")
	public void onCancel() {
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
