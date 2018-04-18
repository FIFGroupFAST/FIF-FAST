package co.id.fifgroup.eligibility.ui;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableRowDTO;

public class Decisiontable extends Listbox {

	private static final long serialVersionUID = 1L;
	
	private DecisionTableModelDTO dtModel;
	private DecisionTableDTO data;
	
	public Decisiontable() {
		super();
		this.setCheckmark(true);
		this.setNonselectableTags("*");
		this.setMold("paging");
		this.setPageSize(GlobalVariable.getMaxRowPerPage());
		
	}
	
	
	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		for(Listitem item : getItems()) {
			item.setDisabled(disabled);
			for(Component c : item.getChildren()){
				for (Component child : c.getChildren()){
					if(child instanceof Bandbox){
						((Bandbox) child).setReadonly(disabled);
						((Bandbox) child).setButtonVisible(!disabled);
					}else if (child instanceof Textbox){
						((Textbox) child).setReadonly(disabled);
					}else if (child instanceof InputElement ) {
						((InputElement) child).setDisabled(disabled);
					}
				}
			}
		}
	}

	public void setData(DecisionTableDTO decisionTable) {
		setData(decisionTable, false);
	}
	
	
	public void setData(DecisionTableDTO decisionTable, boolean disabled) {
		ListModelList<DecisionTableRowDTO> listModel = null;
		if (decisionTable != null) {
			this.data = decisionTable;
			this.dtModel = decisionTable.getModel();
			this.setItemRenderer(new DecisionTableRowRenderer(dtModel, disabled));
			renderHeader(dtModel);
			listModel = new ListModelList<>(decisionTable.getRows());
			listModel.setMultiple(true);
		} else {
			this.data = null;
			this.dtModel = null;
			renderHeader(null);
		}
		setModel(listModel);
		renderAll();
	}
	
	
	public DecisionTableModelDTO getDecisionTableModel() {
		return this.dtModel;
	}

	protected void renderHeader(DecisionTableModelDTO dtModel) {
		List<Component> removedComponents = new ArrayList<>();
		for (Component comp : this.getChildren()) {
			if (comp instanceof Listhead || comp instanceof Listitem) {
				removedComponents.add(comp);
			}
		}
		for (Component comp : removedComponents) {
			comp.setParent(null);
		}
		removedComponents.clear();
		
		
		if (dtModel != null) {
			Listhead listhead = new Listhead();
			listhead.appendChild(new Listheader("", "", "30px"));
			for (DecisionTableColumnDTO column : dtModel.getConditions()) {
				Listheader listheader = new Listheader(column.getName());
				listhead.appendChild(listheader);
			}
			for (DecisionTableColumnDTO column : dtModel.getResults()) {
				Listheader listheader = new Listheader(column.getName());
				listhead.appendChild(listheader);
			}
			this.appendChild(listhead);
		}
	}
	
	public ListModelList<Object> getListModel() {
		return (ListModelList<Object>) getModel();
	}
	
	public DecisionTableDTO getData() {
		if (null != data) {
			for (DecisionTableRowDTO row : data.getRows()) {
				row.setModel(data.getModel());
				row.refresh();
			}
		}
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public void addRow(DecisionTableRowDTO row) {
		if (getModel() != null) {
			data.getRows().add(row);
			((List<DecisionTableRowDTO>) getModel()).add(row);
			renderAll();
		}
	}
	
	public void removeSelectedRows() {
		if(data != null) {
			data.getRows().removeAll(getListModel().getSelection());
			getListModel().removeAll(getListModel().getSelection());
		}
	}
	
}
