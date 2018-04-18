package co.id.fifgroup.eligibility.ui;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.Textbox;

import co.id.fifgroup.core.util.DateUtil;

import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.LookupCriteria;
import co.id.fifgroup.core.ui.lookup.LookupWindow;
import co.id.fifgroup.eligibility.constant.ColumnType;
import co.id.fifgroup.eligibility.constant.LookupValueFrom;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableRowDTO;

public class DecisionTableRowRenderer implements
		ListitemRenderer<DecisionTableRowDTO>, Serializable {

	private static final long serialVersionUID = 1L;

	private DecisionTableModelDTO decisionTableModel;
	private boolean disabled = false;
	
	private static final Logger logger = LoggerFactory.getLogger(DecisionTableRowRenderer.class);
	
	public DecisionTableRowRenderer(DecisionTableModelDTO decisionTableModel ) {
		this(decisionTableModel, false);
		
	}
	
	public DecisionTableRowRenderer(DecisionTableModelDTO decisionTableModel, boolean disabled ) {
		this.decisionTableModel = decisionTableModel;
		this.disabled = disabled;
	}
	
	@Override
	public void render(Listitem item, DecisionTableRowDTO data, int index)
			throws Exception {
		
		item.setDisabled(disabled);
		item.appendChild(new Listcell());
		data.setSequence(index);
		
		int i = 0;
		for (DecisionTableColumnDTO column : decisionTableModel.getConditions()) {
			switch (column.getField().getFieldType()) {
			case NUMBER:
				item.appendChild(createNumberCell(column,data, i++));
				break;

			case LOOKUP:
				LookupValueFrom lookupValueFrom =  column.getField().getLookupValueFrom();
				item.appendChild(createLookupCell(column,data, i++, null == lookupValueFrom ? LookupValueFrom.CODE : lookupValueFrom ));
				break;
				
			case TEXT:
				item.appendChild(createTextCell(column, data, i++ ));
				break;
			
			case DATE:
				item.appendChild(createDateCell(column, data, i++ ));
				break;
			
			default:
				item.appendChild(new Listcell());
				break;
			}
		}
		
		int j = 0;
		for (DecisionTableColumnDTO column : decisionTableModel.getResults()) {
			switch (column.getField().getFieldType()) {
			case NUMBER:
				item.appendChild(createNumberCell(column,data, j++));
				break;

			case LOOKUP:
				LookupValueFrom lookupValueFrom =  column.getField().getLookupValueFrom();
				item.appendChild(createLookupCell(column,data, j++, null == lookupValueFrom ? LookupValueFrom.CODE : lookupValueFrom ));
				break;
				
			case TEXT:
				item.appendChild(createTextCell(column, data, j++ ));
				break;
			
			case DATE:
				item.appendChild(createDateCell(column, data, j++ ));
				break;
			
			default:
				item.appendChild(new Listcell());
				break;
			}
		}
		
	}
	
	public Listcell createLookupCell(final DecisionTableColumnDTO column, final DecisionTableRowDTO data, int index, final LookupValueFrom lookupValueFrom) {
		Listcell cell = new Listcell();
		String value = null;
		if (column.getColumnType() == ColumnType.CONDITION) {
			try {
				value = data.getConditionList().get(index);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		} else {
			try {
				value = data.getResultList().get(index);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		
		final LookupWindow lookup = new LookupWindow();
		
		String title = "List of "+column.getName();
		String searchText = column.getName();
		String headerText = column.getName();
		
		lookup.setTitle(title);
		lookup.setSearchText(searchText);
		lookup.setHeaderLabel(headerText);
		
		lookup.setId(UUID.randomUUID().toString());
		lookup.setLookupCriteria(new LookupCriteria() {
	
			private static final long serialVersionUID = 1L;

			@Override
			public String getParentDetailCode() {
				return null;
			}
			
			@Override
			public String getLookupName() {
				return column.getField().getLookupName();
			}
		});
		
		lookup.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				switch (lookupValueFrom){
				case CODE:
					data.put(column.getId(), (null != lookup.getKeyValue()) ?  lookup.getKeyValue().getKey() : null);
					break;
				case MEANING:
					data.put(column.getId(), (null != lookup.getKeyValue()) ?  lookup.getKeyValue().getValue() : null);
					break;
				case DESCRIPTION:
					data.put(column.getId(), (null != lookup.getKeyValue()) ?  lookup.getKeyValue().getDescription() : null);
					break;
				default:
					data.put(column.getId(), (null != lookup.getKeyValue()) ?  lookup.getKeyValue().getKey() : null);
					break;
				}
			}
			
		});
		
		cell.appendChild(lookup);
		switch (lookupValueFrom){
			case CODE:
				lookup.setValueByCode(value == null ? "" : value);
				break;
			case MEANING:
				lookup.setRawValue(new KeyValue(value, value, value));
				break;
			case DESCRIPTION:
				lookup.setRawValue(new KeyValue(value, value, value));
				break;
			default:
				lookup.setValueByCode(value == null ? "" : value);
				break;
		}
		data.put(column.getId(), null != value ? value : "");
		lookup.setHflex("1");
		
		return cell;
	}
	
	public Listcell createTextCell(final DecisionTableColumnDTO column, final DecisionTableRowDTO data, int index) {
		Listcell cell = new Listcell();
		
		String value = null;
		if (column.getColumnType() == ColumnType.CONDITION) {
			try {
				value = data.getConditionList().get(index);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		} else {
			try {
				value = data.getResultList().get(index);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		
		final Textbox textbox = new Textbox(null != value ? value : "");
		data.put(column.getId(), null != value ? value : "");
		textbox.setHflex("1");
		
		textbox.addEventListener(Events.ON_CHANGE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				data.put(column.getId(), textbox.getValue());
			}
			
		});
			
		
		cell.appendChild(textbox);
		return cell;
	}
	
	public Listcell createNumberCell(final DecisionTableColumnDTO column, final DecisionTableRowDTO data, int index) {
		Listcell cell = new Listcell();
		
		String value = null;
		if (column.getColumnType() == ColumnType.CONDITION) {
			try {
				value = data.getConditionList().get(index);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		} else {
			try {
				value = data.getResultList().get(index);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		
		final Decimalbox decimalBox = new Decimalbox();
		decimalBox.setFormat(GlobalVariable.getMoneyFormat());
		if (!Strings.isBlank(value)) {
			try {
				decimalBox.setValue(new BigDecimal(value));
			} catch (NumberFormatException nfe) {
				decimalBox.setValue(BigDecimal.ZERO);
			}
			data.put(column.getId(), decimalBox.getValue());
		}
		decimalBox.setHflex("1");
		decimalBox.setMaxlength(22);
		decimalBox.setConstraint(new SimpleConstraint(SimpleConstraint.NO_NEGATIVE));
		
		decimalBox.addEventListener(Events.ON_CHANGE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (decimalBox.isValid())
					data.put(column.getId(), decimalBox.getValue());
				else
					data.put(column.getId(), BigDecimal.ZERO);
			}
		});
		
		cell.appendChild(decimalBox);
		return cell;
	}
	
	public Listcell createDateCell(final DecisionTableColumnDTO column, final DecisionTableRowDTO data, int index) {
		Listcell cell = new Listcell();
		
		String value = null;
		Date date = null;
		if (column.getColumnType() == ColumnType.CONDITION) {
			try {
				value = data.getConditionList().get(index);
				date = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT).parse(value);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		} else {
			try {
				value = data.getResultList().get(index);
				date = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT).parse(value);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		
		
		final Datebox datebox = new Datebox();
		datebox.setFormat(GlobalVariable.getDateFormat());
		data.put(column.getId(), date);
		if (!Strings.isBlank(value))
			datebox.setValue(date);
		datebox.setHflex("1");
		
		datebox.addEventListener(Events.ON_CHANGE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				data.put(column.getId(), datebox.getValue());
				
			}
		});
		
		cell.appendChild(datebox);
		return cell;
	}

}
