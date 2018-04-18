package co.id.fifgroup.ssoa.common;


import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.BandboxListcell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;


public class SSOAListOfValueComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private SerializableSearchDelegateDoubleCriteria<Object> searchDelegate;
	@Wire
	private Listbox listbox;
	@Wire
	private Listbox listbox2;
	private Bandbox source;
	@Wire
	private Window winGenericLov;
	@Wire
	private Label lblSearchTextCode;
	@Wire
	private Label lblSearchTextName;
	@Wire
	private Listheader listHeadLabel;
	@Wire
	private Listheader listHeadLabel2;
	@Wire
	private Listheader listDescLabel;
	@Wire
	private Listheader listDescLabel2;
	@Wire
	private Searchtextbox txtSearchCriteriaCode;
	@Wire
	private Searchtextbox txtSearchCriteriaName;
	@Wire
	private Paging pgListOfValue;
	
	
	private String title;
	private String searchTextCode;
	private String searchTextName;
	private String headerLabel;
	private String descLabel;
	private Boolean otherTemplate;
	
	private String searchCriteriaCode;
	private String searchCriteriaName;
	private BandboxListcell<?, ?> bandboxListcell;
	
	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		searchDelegate = arg.get("searchDelegate") == null ? null : (SerializableSearchDelegateDoubleCriteria<Object>) arg.get("searchDelegate");
		bandboxListcell = arg.get("bandBoxListcell") == null ? null : (BandboxListcell<?,?>) arg.get("bandBoxListcell");
		source = arg.get("source") == null ? null : (Bandbox) arg.get("source");
		title = arg.get("title") == null ? "" : arg.get("title").toString();
		searchTextCode = arg.get("searchTextCode") == null ? "" : arg.get("searchTextCode").toString();
		searchTextName = arg.get("searchTextName") == null ? "" : arg.get("searchTextName").toString();
		headerLabel = arg.get("headerLabel") == null ? "" : arg.get("headerLabel").toString();
		descLabel = arg.get("descLabel") == null ? "" : arg.get("descLabel").toString();
		otherTemplate = arg.get("otherTemplate") == null ? false : (Boolean)arg.get("otherTemplate");
		if(title == null || title.equals(""))
			winGenericLov.setTitle("List of Value");
		else
			winGenericLov.setTitle(title);
		
		if(searchTextCode == null || searchTextCode.equals(""))
			lblSearchTextCode.setValue("Value");
		else
			lblSearchTextCode.setValue(searchTextCode);
		
		if(searchTextName == null || searchTextName.equals(""))
			lblSearchTextName.setValue("Value");
		else
			lblSearchTextName.setValue(searchTextName);
		
		if(headerLabel == null || headerLabel.equals("")){
			listHeadLabel.setLabel("Value");
			listHeadLabel2.setLabel("Value");
		}else{
			listHeadLabel.setLabel(headerLabel);
			listHeadLabel2.setLabel(descLabel);
		}
		
		if(descLabel == null || descLabel.equals("")){
			listDescLabel.setLabel("Value");
		    listDescLabel2.setLabel("Value");
		}else{
			listDescLabel.setLabel(descLabel);
		    listDescLabel2.setLabel(headerLabel);
		}
		
		if(!otherTemplate){
			listbox.setVisible(true);
			listbox2.setVisible(false);
		}else{
			listbox.setVisible(false);
			listbox2.setVisible(true);
		}
		
	}

	@Listen("onSelect=#listbox")
	public void select() {
		KeyValue selectedData = (KeyValue) listbox.getSelectedItem().getValue();
		if(source != null) {
			source.setRawValue(selectedData);
			Events.postEvent(Events.ON_CLOSE, source, selectedData);
		}else if(bandboxListcell != null){
			bandboxListcell.setValue(selectedData.getValue().toString());
			bandboxListcell.setRawValue(selectedData);
			Events.postEvent(Events.ON_CLOSE, bandboxListcell, selectedData);
		}
		getSelf().detach();
	}
	
	@Listen("onSelect=#listbox2")
	public void select2() {
		KeyValue selectedData = (KeyValue) listbox2.getSelectedItem().getValue();
		if(source != null) {
			source.setRawValue(selectedData);
			Events.postEvent(Events.ON_CLOSE, source, selectedData);
		}else if(bandboxListcell != null){
			bandboxListcell.setValue(selectedData.getValue().toString());
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
			List<Object> list = (List<Object>) searchDelegate.findBySearchCriteria(searchCriteriaCode,searchCriteriaName, pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
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
	
	private void loadListbox2() {
		if(searchDelegate != null) {
			List<Object> list = (List<Object>) searchDelegate.findBySearchCriteria(searchCriteriaCode,searchCriteriaName, pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
			List<KeyValue> kvList = new ArrayList<KeyValue>();
			if(list != null) {
				for(Object data : list) {
					KeyValue keyValue = new KeyValue();
					searchDelegate.mapper(keyValue, data);
					kvList.add(keyValue);
				}
				ListModelList<KeyValue> model = new ListModelList<KeyValue>(kvList);
				listbox2.setModel(model);
				listbox2.renderAll();
			}
		}
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		searchCriteriaCode = txtSearchCriteriaCode.getValue();
		searchCriteriaName = txtSearchCriteriaName.getValue();
		pgListOfValue.setTotalSize(searchDelegate.countBySearchCriteria(searchCriteriaCode,searchCriteriaName));
		pgListOfValue.setPageSize(GlobalVariable.getMaxRowPerPage());
		pgListOfValue.setActivePage(0);
		if(!otherTemplate){
			loadListbox();
		}else{
			loadListbox2();
		}
	}

	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		if(!otherTemplate){
			loadListbox();
		}else{
			loadListbox2();
		}
		
	}
	
}
