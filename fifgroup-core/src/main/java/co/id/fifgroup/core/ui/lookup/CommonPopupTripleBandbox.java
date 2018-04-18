package co.id.fifgroup.core.ui.lookup;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;


public class CommonPopupTripleBandbox extends Bandbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7266841144076628484L;

	private String title;
	private String searchText1;
	private String searchText2;
	private String searchText3;
	private String headerLabel1;
	private String headerLabel2;
	private String headerLabel3;
	private CommonPopupTripleBandbox source = this;
	private SerializableSearchDelegateTripleCriteria<?> searchDelegate;
	private Object value;
	private boolean concatValueDescription;
	private boolean concatValueDescription2;
	private boolean hideSearchText1;
	private boolean hideSearchText2;
	private boolean hideSearchText3;
	private boolean showValue;
	
	// start added by WLY, for phase 2
	private boolean concatDescriptionDescription2;
	private boolean concatValueDescriptionDescription2;
	private boolean concatDescription2Value;
	
	public boolean isConcatDescription2Value() {
		return concatDescription2Value;
	}

	public void setConcatDescription2Value(boolean concatDescription2Value) {
		if (concatDescription2Value) setAllConcatFalse();
		this.concatDescription2Value = concatDescription2Value;
	}

	public boolean isConcatDescriptionDescription2() {
		return concatDescriptionDescription2;
	}

	public void setConcatDescriptionDescription2(
			boolean concatDescriptionDescription2) {
		if (concatDescriptionDescription2) setAllConcatFalse();
		this.concatDescriptionDescription2 = concatDescriptionDescription2;
	}

	public boolean isConcatValueDescriptionDescription2() {
		return concatValueDescriptionDescription2;
	}

	public void setConcatValueDescriptionDescription2(
			boolean concatValueDescriptionDescription2) {
		if (concatValueDescriptionDescription2) setAllConcatFalse();
		this.concatValueDescriptionDescription2 = concatValueDescriptionDescription2;
	}
	
	private void setAllConcatFalse(){
		concatDescriptionDescription2 = false;
		concatValueDescriptionDescription2 = false;
		concatDescription2Value = false;
	}
	// end added by WLY, for phase 2

	public CommonPopupTripleBandbox() {
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
				params.put("searchText3", searchText3);
				params.put("headerLabel1", headerLabel1);
				params.put("headerLabel2", headerLabel2);
				params.put("headerLabel3", headerLabel3);
				params.put("source", source);
				params.put("searchDelegate", searchDelegate);
				params.put("hideSearchText1", hideSearchText1);
				params.put("hideSearchText2", hideSearchText2);
				params.put("hideSearchText3", hideSearchText3);
				Window win = (Window) Executions.createComponents("~./common/generic_of_value_triple.zul", null, params);
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

	public SerializableSearchDelegateTripleCriteria<?> getSearchDelegate() {
		return searchDelegate;
	}

	public void setSearchDelegate(
			SerializableSearchDelegateTripleCriteria<?> searchDelegate) {
		this.searchDelegate = searchDelegate;
	}

	public CommonPopupTripleBandbox getSource() {
		return source;
	}

	public void setSource(CommonPopupTripleBandbox source) {
		this.source = source;
	}
	
	public String getSearchText3() {
		return searchText3;
	}

	public void setSearchText3(String searchText3) {
		this.searchText3 = searchText3;
	}

	public String getHeaderLabel3() {
		return headerLabel3;
	}

	public void setHeaderLabel3(String headerLabel3) {
		this.headerLabel3 = headerLabel3;
	}

	@Override
	public void setRawValue(Object value) {
		TripleBandKeyValue selectedData = (TripleBandKeyValue) value;
		if(selectedData != null) {
			if (isConcatValueDescription() && selectedData.getValue() != null) {
				super.setValue(String.valueOf(selectedData.getValue()) + " - " + String.valueOf(selectedData.getDescription()));
			} else if(isConcatValueDescription2() && selectedData.getValue() != null && selectedData.getDescription2() != null){
				super.setValue(String.valueOf(selectedData.getValue()) + " - " + String.valueOf(selectedData.getDescription2()));
			}else if(isShowValue()){
				super.setValue(String.valueOf(selectedData.getValue()));	
			// start added by WLY, for phase 2
			} else if(isConcatDescriptionDescription2()){
				super.setValue(String.valueOf(selectedData.getDescription()) + " - " + String.valueOf(selectedData.getDescription2()));
			} else if(isConcatValueDescriptionDescription2()){
				super.setValue(String.valueOf(selectedData.getValue()) + " - " + String.valueOf(selectedData.getDescription()) 
						+ " - " + String.valueOf(selectedData.getDescription2()));
			} else if(isConcatDescription2Value()){
				super.setValue(String.valueOf(selectedData.getDescription2()) + " - " + String.valueOf(selectedData.getValue()));
			// end added by WLY, for phase 2
			}else {
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
	
	public TripleBandKeyValue getTripleKeyValue(){
		return (TripleBandKeyValue) this.value;
	}

	public boolean isConcatValueDescription() {
		return concatValueDescription;
	}

	public void setConcatValueDescription(boolean concatValueDescription) {
		this.concatValueDescription = concatValueDescription;
	}
	
	
	public boolean isConcatValueDescription2() {
		return concatValueDescription2;
	}

	public void setConcatValueDescription2(boolean concatValueDescription2) {
		this.concatValueDescription2 = concatValueDescription2;
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

	public boolean isHideSearchText1() {
		return hideSearchText1;
	}

	public void setHideSearchText1(boolean hideSearchText1) {
		this.hideSearchText1 = hideSearchText1;
	}

	public boolean isHideSearchText2() {
		return hideSearchText2;
	}

	public void setHideSearchText2(boolean hideSearchText2) {
		this.hideSearchText2 = hideSearchText2;
	}

	public boolean isHideSearchText3() {
		return hideSearchText3;
	}

	public void setHideSearchText3(boolean hideSearchText3) {
		this.hideSearchText3 = hideSearchText3;
	}

	public boolean isShowValue() {
		return showValue;
	}

	public void setShowValue(boolean showValue) {
		this.showValue = showValue;
	}
	
	
	
}
