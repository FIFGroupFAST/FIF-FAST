package co.id.fifgroup.core.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.ui.tabularentry.BandboxListcell;


public class ListOfValueComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private SerializableSearchDelegate<Object> searchDelegate;
	@Wire
	private Listbox listbox;
	private Bandbox source;
	@Wire
	private Window winGenericLov;
	@Wire
	private Label lblSearchText;
	@Wire
	private Listheader listHeadLabel;
	@Wire
	private Searchtextbox txtSearchCriteria;
	@Wire
	private Paging pgListOfValue;
	
	private String title;
	private String searchText;
	private String headerLabel;
	
	private String searchCriteria;
	private BandboxListcell<?, ?> bandboxListcell;
	
	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		searchDelegate = arg.get("searchDelegate") == null ? null : (SerializableSearchDelegate<Object>) arg.get("searchDelegate");
		bandboxListcell = arg.get("bandBoxListcell") == null ? null : (BandboxListcell<?,?>) arg.get("bandBoxListcell");
		source = arg.get("source") == null ? null : (Bandbox) arg.get("source");
		title = arg.get("title") == null ? "" : arg.get("title").toString();
		searchText = arg.get("searchText") == null ? "" : arg.get("searchText").toString();
		headerLabel = arg.get("headerLabel") == null ? "" : arg.get("headerLabel").toString();
		if(title == null || title.equals(""))
			winGenericLov.setTitle("List of Value");
		else
			winGenericLov.setTitle(title);
		
		if(searchText == null || searchText.equals(""))
			lblSearchText.setValue("Value");
		else
			lblSearchText.setValue(searchText);
		
		if(headerLabel == null || headerLabel.equals(""))
			listHeadLabel.setLabel("Value");
		else
			listHeadLabel.setLabel(headerLabel);
	}

	@Listen("onSelect=#listbox")
	public void select() {
		KeyValue selectedData = (KeyValue) listbox.getSelectedItem().getValue();
		if(source != null) {
			source.setRawValue(selectedData);
			Events.postEvent(Events.ON_CLOSE, source, selectedData);
		}else if(bandboxListcell != null){
			bandboxListcell.setValue(selectedData.getDescription().toString());
			bandboxListcell.setRawValue(selectedData);
			Events.postEvent(Events.ON_CLOSE, bandboxListcell, selectedData);
		}
		getSelf().detach();
	}
	
	@Listen("onClick=#btnDeselect")
	public void deselect() {
		if(source != null) {
			source.setRawValue(null);
			Events.postEvent(Events.ON_CLOSE, source, null);
		}else if(bandboxListcell != null){
			bandboxListcell.setValue(null);
			bandboxListcell.setRawValue(null);
			Events.postEvent(Events.ON_CLOSE, bandboxListcell, null);
		}
		getSelf().detach();
	}
	
	private void loadListbox() {
		if(searchDelegate != null) {
			List<Object> list = (List<Object>) searchDelegate.findBySearchCriteria(searchCriteria, pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
			List<KeyValue> kvList = new ArrayList<KeyValue>();
			if(list != null) {
				for(Object data : list) {
					KeyValue keyValue = new KeyValue();
					searchDelegate.mapper(keyValue, data);
					kvList.add(keyValue);
				}
				ListModelList<KeyValue> model = new ListModelList<KeyValue>(kvList);
				listbox.setModel(model);
				listbox.renderAll();
			}
		}
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		searchCriteria = txtSearchCriteria.getValue();
		pgListOfValue.setTotalSize(searchDelegate.countBySearchCriteria(searchCriteria));
		pgListOfValue.setPageSize(GlobalVariable.getMaxRowPerPage());
		pgListOfValue.setActivePage(0);
		loadListbox();
	}

	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		loadListbox();
	}
}
