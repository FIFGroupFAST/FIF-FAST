package co.id.fifgroup.eligibility.controller;

import java.util.Map;

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

import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.ui.DefaultSetupComposer;
import co.id.fifgroup.core.ui.EnumListbox;
import co.id.fifgroup.eligibility.constant.FieldType;
import co.id.fifgroup.eligibility.constant.LookupValueFrom;
import co.id.fifgroup.eligibility.dto.FactTypeFieldDTO;
import co.id.fifgroup.eligibility.validation.FactTypeFieldValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class FactTypeFieldDetailComposer extends SelectorComposer<Window> 
	implements DefaultSetupComposer<FactTypeFieldDTO>{

	private static final long serialVersionUID = 3300617076290450343L;
	
	@Wire private Textbox txtFieldName;
	@Wire private EnumListbox<FieldType> lstFieldType;
	@Wire private Listbox lstLookupName;
	@Wire private Row rowLookupName;
	@Wire private EnumListbox<LookupValueFrom> lstLookupValueFrom;
	@Wire private Row rowLookupValueFrom;
	
	
	@WireVariable(rewireOnActivate = true)
	private transient FactTypeFieldValidator factTypeFieldValidator;
	
	@WireVariable 
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate = true)
	private transient LookupService lookupServiceImpl;
	
	private ListModelList<FactTypeFieldDTO> fields;
	private FactTypeFieldDTO field;

	@Override
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		lstLookupValueFrom.setEnum(LookupValueFrom.CODE);
		
		lstFieldType.setEnum(FieldType.TEXT);
		
		lstLookupName.setModel(new ListModelList<>(lookupServiceImpl.getLookupHeaderByExample(null)));
		lstLookupName.renderAll();
		
		fields = (ListModelList<FactTypeFieldDTO>) arg.get("fields");
		
		if (null != arg.get("field")) {
			field = (FactTypeFieldDTO) arg.get("field");
		}
		render(field);
	}
	
	@Listen("onClick = #btnSave")
	public void onSave() {
		clearErrorMessages();
		try {
			if (null != field) {
				int index = fields.indexOf(field);
				populate(field);
				factTypeFieldValidator.validate(field);
				fields.set(index, field);
			} else {
				FactTypeFieldDTO subject = new FactTypeFieldDTO();
				populate(subject);
				factTypeFieldValidator.validate(subject);
				fields.add(subject);
			}
			getSelf().onClose();
		} catch (ValidationException vex) {
			showErrorMessages(vex);
		} catch (Exception ex) {
			throw ex;
		}
		
	}

	@Override
	public void populate(FactTypeFieldDTO subject) {
		subject.setName(txtFieldName.getValue());
		subject.setFieldType(lstFieldType.getSelectedValue());
		subject.setLookupValueFrom(lstLookupValueFrom.getSelectedValue());
		subject.setLookupName(
				null != lstLookupName.getSelectedItem() 
					? lstLookupName.getSelectedItem().getLabel()
					: null);
	}

	@Override
	public void render(FactTypeFieldDTO subject) {
		if (field != null) {
			txtFieldName.setValue(subject.getName());
			lstFieldType.setSelectedValue(subject.getFieldType());
			lstLookupValueFrom.setSelectedValue(subject.getLookupValueFrom());
			for (Listitem item : lstLookupName.getItems()) {
				if (item.getValue().equals(subject.getLookupName())) {
					item.setSelected(true);
					break;
				}
			}
		}
		
		onRetrievalModeChange();
	}
	
	@Listen ("onSelect = #lstFieldType")
	public void onRetrievalModeChange() {
		if (FieldType.LOOKUP == lstFieldType.getSelectedValue()) {
			rowLookupName.setVisible(true);
			rowLookupValueFrom.setVisible(true);
		} else {
			rowLookupName.setVisible(false);
			lstLookupName.setSelectedItem(null);
			rowLookupValueFrom.setVisible(false);
			lstLookupValueFrom.setSelectedItem(null);
		}
	}

	@Override
	public void showErrorMessages(ValidationException ex) {
		ErrorMessageUtil.setErrorMessage(txtFieldName, ex.getMessage(FactTypeFieldValidator.FIELD_NAME));
		ErrorMessageUtil.setErrorMessage(lstFieldType, ex.getMessage(FactTypeFieldValidator.FIELD_TYPE));
		ErrorMessageUtil.setErrorMessage(lstLookupName, ex.getMessage(FactTypeFieldValidator.LOOKUP));
		
	}

	@Override
	public void clearErrorMessages() {
		ErrorMessageUtil.clearErrorMessage(txtFieldName);
		ErrorMessageUtil.clearErrorMessage(lstFieldType);
		ErrorMessageUtil.clearErrorMessage(lstLookupName);
	}


}
