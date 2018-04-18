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
import co.id.fifgroup.core.ui.lookup.QuadrupleBandKeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateQuadrupleCriteria;
import co.id.fifgroup.core.ui.tabularentry.BandboxListcell;


public class ListOfValueQuadrupleComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(ListOfValueQuadrupleComposer.class);
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	private SerializableSearchDelegateQuadrupleCriteria<Object> searchDelegate;
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
	private Label lblSearchText4;
	@Wire
	private Listheader listHeadLabel1;
	@Wire
	private Listheader listHeadLabel2;
	@Wire
	private Listheader listHeadLabel3;
	@Wire
	private Listheader listHeadLabel4;
	@Wire
	private Searchtextbox txtSearch1;
	@Wire
	private Searchtextbox txtSearch2;
	@Wire
	private Searchtextbox txtSearch3;
	@Wire
	private Searchtextbox txtSearch4;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Row rowSearch1;
	@Wire
	private Row rowSearch2;
	@Wire
	private Row rowSearch3;
	@Wire
	private Row rowSearch4;
	
	
	private String title;
	private String searchText1;
	private String searchText2;
	private String searchText3;
	private String searchText4;
	private String headerLabel1;
	private String headerLabel2;
	private String headerLabel3;
	private String headerLabel4;
	
	private String searchCriteria1;
	private String searchCriteria2;
	private String searchCriteria3;
	private String searchCriteria4;
	private BandboxListcell<?, ?> bandboxListcell;
	
	private boolean hideSearchText1;
	private boolean hideSearchText2;
	private boolean hideSearchText3;
	private boolean hideSearchText4;
	
	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		searchDelegate = arg.get("searchDelegate") == null ? null : (SerializableSearchDelegateQuadrupleCriteria<Object>) arg.get("searchDelegate");
		bandboxListcell = arg.get("bandBoxListcell") == null ? null : (BandboxListcell<?,?>) arg.get("bandBoxListcell");
		source = arg.get("source") == null ? null : (Bandbox) arg.get("source");
		title = arg.get("title") == null ? "" : arg.get("title").toString();
		searchText1 = arg.get("searchText1") == null ? "" : arg.get("searchText1").toString();
		searchText2 = arg.get("searchText2") == null ? "" : arg.get("searchText2").toString();
		searchText3 = arg.get("searchText3") == null ? "" : arg.get("searchText3").toString();
		searchText4 = arg.get("searchText4") == null ? "" : arg.get("searchText4").toString();
		headerLabel1 = arg.get("headerLabel1") == null ? "" : arg.get("headerLabel1").toString();
		headerLabel2 = arg.get("headerLabel2") == null ? "" : arg.get("headerLabel2").toString();
		headerLabel3 = arg.get("headerLabel3") == null ? "" : arg.get("headerLabel3").toString();
		headerLabel4 = arg.get("headerLabel3") == null ? "" : arg.get("headerLabel4").toString();
		hideSearchText1 =  (boolean) arg.get("hideSearchText1");
		hideSearchText2 =  (boolean) arg.get("hideSearchText2");
		hideSearchText3 =  (boolean) arg.get("hideSearchText3");
		hideSearchText4 =  (boolean) arg.get("hideSearchText4");
		
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
		
		if(searchText4 == null || searchText4.equals(""))
			lblSearchText4.setValue("Value");
		else
			lblSearchText4.setValue(searchText4);
		
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
		
		if(headerLabel4 == null || headerLabel4.equals(""))
			listHeadLabel4.setLabel("Value");
		else
			listHeadLabel4.setLabel(headerLabel4);
		
		if(hideSearchText1){
			rowSearch1.setVisible(false);
		}
		if(hideSearchText2){
			rowSearch2.setVisible(false);
				}
		if(hideSearchText3){
			rowSearch3.setVisible(false);
		}
		if(hideSearchText4){
			rowSearch4.setVisible(false);
		}
		
	}

	@Listen("onSelect=#listbox")
	public void select() {
		try {
			QuadrupleBandKeyValue selectedData = (QuadrupleBandKeyValue) listbox.getSelectedItem().getValue();
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
			List<Object> list = (List<Object>) searchDelegate.findBySearchCriteria(searchCriteria1, searchCriteria2, searchCriteria3, searchCriteria4, pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
			List<QuadrupleBandKeyValue> kvList = new ArrayList<QuadrupleBandKeyValue>();
			if(list != null) {
				for(Object data : list) {
					QuadrupleBandKeyValue keyValue = new QuadrupleBandKeyValue();
					searchDelegate.mapper(keyValue, data);
					kvList.add(keyValue);
				}
				ListModelList<QuadrupleBandKeyValue> model = new ListModelList<QuadrupleBandKeyValue>(kvList);
				listbox.setModel(model);
				listbox.renderAll();
			}
		}
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		searchCriteria1 = txtSearch1.getValue();
		searchCriteria2 = txtSearch2.getValue();
		searchCriteria3 = txtSearch3.getValue();
		searchCriteria4 = txtSearch4.getValue();
		pgListOfValue.setTotalSize(searchDelegate.countBySearchCriteria(searchCriteria1, searchCriteria2, searchCriteria3, searchCriteria4));
		pgListOfValue.setPageSize(GlobalVariable.getMaxRowPerPage());
		pgListOfValue.setActivePage(0);
		loadListbox();
	}

	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		loadListbox();
	}
	
	
}
