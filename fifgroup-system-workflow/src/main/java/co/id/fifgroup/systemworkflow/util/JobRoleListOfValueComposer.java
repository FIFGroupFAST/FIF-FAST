package co.id.fifgroup.systemworkflow.util;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.systemworkflow.constants.WorkflowReference;

import co.id.fifgroup.core.service.WorkflowLookupServiceAdapter;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.tabularentry.BandboxListcell;

@VariableResolver(DelegatingVariableResolver.class)
public class JobRoleListOfValueComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Listbox listbox;
	private Bandbox source;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Listbox cmbJobRole;
	@Wire
	private Textbox txtSearchCriteria;
	
	@WireVariable(rewireOnActivate=true)
	private transient WorkflowLookupServiceAdapter workflowLookupServiceAdapterImpl;
	
	private BandboxListcell<?, ?> bandboxListcell;
	
	private String criteria;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		source = arg.get("source") == null ? null : (Bandbox) arg.get("source");
		bandboxListcell = arg.get("bandBoxListcell") == null ? null : (BandboxListcell<?,?>) arg.get("bandBoxListcell");
	}

	@Listen("onSelect = #listbox")
	public void select(){
		KeyValue selectedData = (KeyValue) listbox.getSelectedItem().getValue();
		if(source != null) {
			source.setRawValue(selectedData);
			Events.postEvent(Events.ON_CLOSE, source, selectedData);
		}else if(bandboxListcell != null){
			bandboxListcell.setValue(selectedData.getDescription().toString());
			bandboxListcell.setRawValue(selectedData);
		}
		getSelf().detach();
	}
	
	private void loadListbox() {
		String lookupName;
		String criteria = txtSearchCriteria.getValue();
		List<KeyValue> keyValues;
		if (cmbJobRole.getSelectedIndex() == -1) {
			lookupName = WorkflowReference.JOB.name();
			cmbJobRole.setSelectedIndex(0);
		} else {
			lookupName = cmbJobRole.getSelectedItem().getValue().toString();
		}
		if (lookupName.equalsIgnoreCase(WorkflowReference.JOB.name())) {
			keyValues = workflowLookupServiceAdapterImpl.getAllJob(null, criteria, pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
		} else {
			keyValues = workflowLookupServiceAdapterImpl.getLookupKeyValue(lookupName, criteria, pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
		}
		ListModel<KeyValue> listModel = new ListModelList<KeyValue>(keyValues);
		listbox.setModel(listModel);
		System.err.println(listModel.getSize() + "----> size list model");
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		String lookupName;
		String criteria = txtSearchCriteria.getValue();
		int totalSize;
		if (cmbJobRole.getSelectedIndex() == -1) {
			lookupName = WorkflowReference.JOB.name();
			cmbJobRole.setSelectedIndex(0);
		} else {
			lookupName = cmbJobRole.getSelectedItem().getValue().toString();
		}
		if (lookupName.equalsIgnoreCase(WorkflowReference.JOB.name())) {
			totalSize = workflowLookupServiceAdapterImpl.countLookupJob(criteria);
		} else {
			totalSize = workflowLookupServiceAdapterImpl.countLookupKeyValue(lookupName, criteria);
		}
		pgListOfValue.setTotalSize(totalSize);
		pgListOfValue.setPageSize(GlobalVariable.getMaxRowPerPage());
		pgListOfValue.setActivePage(0);
		System.err.println(pgListOfValue.getTotalSize() + "----> total size");
		loadListbox();
	}
	
	@Listen("onClick=#btnDeselect")
	public void deselect() {
		if(source != null) {
			source.setRawValue(null);
			Events.postEvent(Events.ON_CLOSE, source, null);
		}else if(bandboxListcell != null){
			bandboxListcell.setValue(null);
			bandboxListcell.setRawValue(null);
		}
		getSelf().detach();
	}

	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		loadListbox();
	}
	
	@Listen("onSelect = #cmbJobRole") 
	public void onSelect() {
		onFind();
	}
}
