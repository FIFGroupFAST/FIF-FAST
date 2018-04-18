package co.id.fifgroup.core.ui.lookup;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;


public class CommonPopupDoubleBandbox extends Bandbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7266841144076628484L;

	private String title;
	private String searchText1;
	private String searchText2;
	private String headerLabel1;
	private String headerLabel2;
	private CommonPopupDoubleBandbox source = this;
	private SerializableSearchDelegateDoubleCriteria<?> searchDelegate;
	private Object value;
	private boolean concatValueDescription;
	private String windowWidth;
	private String windowHeight;
	private String listHeadLabel1Width;
	private String listHeadLabel2Width;
	
	
	public CommonPopupDoubleBandbox() {
		setReadonly(true);
		addEventListener(Events.ON_OPEN, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -91355005227901153L;

			@Override
			public void onEvent(Event event) throws Exception {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("title", title);
				params.put("searchText1", searchText1);
				params.put("searchText2", searchText2);
				params.put("headerLabel1", headerLabel1);
				params.put("headerLabel2", headerLabel2);
				params.put("source", source);
				params.put("searchDelegate", searchDelegate);
				params.put("windowWidth",windowWidth);
				params.put("windowHeight",windowHeight);
				params.put("listHeadLabel1Width",listHeadLabel1Width);
				params.put("listHeadLabel2Width",listHeadLabel2Width);
				Window win = (Window) Executions.createComponents("~./common/generic_of_value_double.zul", null, params);
				win.doModal();
			}
		});
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSearchText1() {
		return searchText1;
	}

	public void setSearchText1(String searchText1) {
		this.searchText1 = searchText1;
	}

	public String getSearchText2() {
		return searchText2;
	}

	public void setSearchText2(String searchText2) {
		this.searchText2 = searchText2;
	}

	public String getHeaderLabel1() {
		return headerLabel1;
	}

	public void setHeaderLabel1(String headerLabel1) {
		this.headerLabel1 = headerLabel1;
	}

	public String getHeaderLabel2() {
		return headerLabel2;
	}

	public void setHeaderLabel2(String headerLabel2) {
		this.headerLabel2 = headerLabel2;
	}

	public SerializableSearchDelegateDoubleCriteria<?> getSearchDelegate() {
		return searchDelegate;
	}

	public void setSearchDelegate(
			SerializableSearchDelegateDoubleCriteria<?> searchDelegate) {
		this.searchDelegate = searchDelegate;
	}

	public CommonPopupDoubleBandbox getSource() {
		return source;
	}

	public void setSource(CommonPopupDoubleBandbox source) {
		this.source = source;
	}

	@Override
	public void setRawValue(Object value) {
		KeyValue selectedData = (KeyValue) value;
		if(selectedData != null) {
			if (isConcatValueDescription() && selectedData.getValue() != null) {
				super.setValue(String.valueOf(selectedData.getValue()) + " - " + String.valueOf(selectedData.getDescription()));
			} else {
				super.setValue(String.valueOf(selectedData.getDescription()));				
			}
		} else {
			super.setValue(null);
		}
		this.value = value;
	}
	
	public KeyValue getKeyValue() {
		return (KeyValue)  this.value;
	}

	public boolean isConcatValueDescription() {
		return concatValueDescription;
	}

	public void setConcatValueDescription(boolean concatValueDescription) {
		this.concatValueDescription = concatValueDescription;
	}
	
	
	public void setBandboxValue(Object value) {
		KeyValue selectedData = (KeyValue) value;
		if(selectedData != null) {
			if ( selectedData.getValue() != null) {
				super.setValue(String.valueOf(selectedData.getValue()));
			} 
		} else {
			super.setValue(null);
		}
		this.value = value;
	}

	public String getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(String windowWidth) {
		this.windowWidth = windowWidth;
	}

	public String getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(String windowHeight) {
		this.windowHeight = windowHeight;
	}

	public String getListHeadLabel1Width() {
		return listHeadLabel1Width;
	}

	public void setListHeadLabel1Width(String listHeadLabel1Width) {
		this.listHeadLabel1Width = listHeadLabel1Width;
	}

	public String getListHeadLabel2Width() {
		return listHeadLabel2Width;
	}

	public void setListHeadLabel2Width(String listHeadLabel2Width) {
		this.listHeadLabel2Width = listHeadLabel2Width;
	}
	
	
	
}
