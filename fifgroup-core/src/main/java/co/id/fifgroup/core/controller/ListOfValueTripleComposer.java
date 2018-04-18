package co.id.fifgroup.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateTripleCriteria;
import co.id.fifgroup.core.ui.lookup.TripleBandKeyValue;
import co.id.fifgroup.core.ui.tabularentry.BandboxListcell;


public class ListOfValueTripleComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(ListOfValueTripleComposer.class);
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	private SerializableSearchDelegateTripleCriteria<Object> searchDelegate;
	@Wire
	private Listbox listbox;
	private Bandbox source;
	@Wire
	private Window winGenericDoubleLov;
	@Wire
	private Label lblSearchText1;
	@Wire
	private Label lblSearchText2;
	@Wire
	private Label lblSearchText3;
	@Wire
	private Listheader listHeadLabel1;
	@Wire
	private Listheader listHeadLabel2;
	@Wire
	private Listheader listHeadLabel3;
	@Wire
	private Searchtextbox txtCode;
	@Wire
	private Searchtextbox txtName;
	@Wire
	private Searchtextbox txtCompany;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Row rowSearch1;
	@Wire
	private Row rowSearch2;
	@Wire
	private Row rowSearch3;
	
	
	private String title;
	private String searchText1;
	private String searchText2;
	private String searchText3;
	private String headerLabel1;
	private String headerLabel2;
	private String headerLabel3;
	
	private String searchCriteria1;
	private String searchCriteria2;
	private String searchCriteria3;
	private BandboxListcell<?, ?> bandboxListcell;
	
	private boolean hideSearchText1;
	private boolean hideSearchText2;
	private boolean hideSearchText3;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		searchDelegate = arg.get("searchDelegate") == null ? null : (SerializableSearchDelegateTripleCriteria<Object>) arg.get("searchDelegate");
		bandboxListcell = arg.get("bandBoxListcell") == null ? null : (BandboxListcell<?,?>) arg.get("bandBoxListcell");
		source = arg.get("source") == null ? null : (Bandbox) arg.get("source");
		title = arg.get("title") == null ? "" : arg.get("title").toString();
		searchText1 = arg.get("searchText1") == null ? "" : arg.get("searchText1").toString();
		searchText2 = arg.get("searchText2") == null ? "" : arg.get("searchText2").toString();
		searchText3 = arg.get("searchText3") == null ? "" : arg.get("searchText3").toString();
		headerLabel1 = arg.get("headerLabel1") == null ? "" : arg.get("headerLabel1").toString();
		headerLabel2 = arg.get("headerLabel2") == null ? "" : arg.get("headerLabel2").toString();
		headerLabel3 = arg.get("headerLabel3") == null ? "" : arg.get("headerLabel3").toString();
		hideSearchText1 =  (boolean) arg.get("hideSearchText1");
		hideSearchText2 =  (boolean) arg.get("hideSearchText2");
		hideSearchText3 =  (boolean) arg.get("hideSearchText3");
		
		if(title == null || title.equals(""))
			winGenericDoubleLov.setTitle("List of Value");
		else
			winGenericDoubleLov.setTitle(title);
		
		if(searchText1 == null || searchText1.equals(""))
			lblSearchText1.setValue("Value");
		else
			lblSearchText1.setValue(searchText1);
		
		if(searchText2 == null || searchText2.equals(""))
			lblSearchText2.setValue("Value");
		else
			lblSearchText2.setValue(searchText2);
		
		if(searchText3 == null || searchText3.equals(""))
			lblSearchText3.setValue("Value");
		else
			lblSearchText3.setValue(searchText3);
		
		if(headerLabel1 == null || headerLabel1.equals(""))
			listHeadLabel1.setLabel("Value");
		else
			listHeadLabel1.setLabel(headerLabel1);
		
		if(headerLabel2 == null || headerLabel2.equals(""))
			listHeadLabel2.setLabel("Value");
		else
			listHeadLabel2.setLabel(headerLabel2);
		
		if(headerLabel3 == null || headerLabel3.equals(""))
			listHeadLabel3.setLabel("Value");
		else
			listHeadLabel3.setLabel(headerLabel3);
		
		if(hideSearchText1){
			rowSearch1.setVisible(false);
		}
		if(hideSearchText2){
			rowSearch2.setVisible(false);
				}
		if(hideSearchText3){
			rowSearch3.setVisible(false);
		}

		
	}

	@Listen("onSelect=#listbox")
	public void select() {
		try {
			TripleBandKeyValue selectedData = (TripleBandKeyValue) listbox.getSelectedItem().getValue();
			if(source != null) {
				source.setRawValue(selectedData);
				Events.postEvent(Events.ON_CLOSE, source, selectedData);
			}else if(bandboxListcell != null){
				bandboxListcell.setValue(selectedData.getDescription().toString());
				bandboxListcell.setRawValue(selectedData);
			}
			getSelf().detach();			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
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
	
	private void loadListbox() {
		if(searchDelegate != null) {
			List<Object> list = (List<Object>) searchDelegate.findBySearchCriteria(searchCriteria1, searchCriteria2, searchCriteria3, pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
			List<TripleBandKeyValue> kvList = new ArrayList<TripleBandKeyValue>();
			if(list != null) {
				for(Object data : list) {
					TripleBandKeyValue keyValue = new TripleBandKeyValue();
					searchDelegate.mapper(keyValue, data);
					kvList.add(keyValue);
				}
				ListModelList<TripleBandKeyValue> model = new ListModelList<TripleBandKeyValue>(kvList);
				listbox.setModel(model);
				listbox.renderAll();
			}
		}
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		searchCriteria1 = txtCode.getValue();
		searchCriteria2 = txtName.getValue();
		searchCriteria3 = txtCompany.getValue();
		pgListOfValue.setTotalSize(searchDelegate.countBySearchCriteria(searchCriteria1, searchCriteria2, searchCriteria3));
		pgListOfValue.setPageSize(GlobalVariable.getMaxRowPerPage());
		pgListOfValue.setActivePage(0);
		loadListbox();
	}

	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		loadListbox();
	}
	
	
}
