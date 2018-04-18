package co.id.fifgroup.ssoa.ui;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.ApprovalFlow;
import co.id.fifgroup.ssoa.service.StockOpnameService;


public class StockOpnamePopupStatusComposer extends SelectorComposer<Window> {
	
	@Wire
	private Listbox lstStatusAsset;
	@Wire
	private Window winStatusPopup;

	private static final long serialVersionUID = 1L;
	@WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		findAll();
	}

	public void findAll() {
		/*List<ApprovalFlow> statusAsset = stockOpnameInterface.findAllStatusAsset();
		lstStatusAsset.setModel(new ListModelList<ApprovalFlow>(statusAsset));*/
	}

	@Listen("onClick = #btnClose")
	public void modalStatus(Event e) {
		winStatusPopup.detach();
	}
}