package co.id.fifgroup.ssoa.ui;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;


public class CommonPopupDoubleBandbox extends Bandbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7266841144076628484L;

	private String title;
	
	private CommonPopupDoubleBandbox source = this;
	private SerializableSearchDelegateDoubleCriteria<?> searchDelegate;
	
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
				params.put("title", "");
				params.put("searchText1", "");
				params.put("searchText2", "");
				params.put("headerLabel1", "");
				params.put("headerLabel2", "");
				params.put("source", source);
				params.put("searchDelegate", searchDelegate);
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_popup.zul", null, params);
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






	
	
	
}
