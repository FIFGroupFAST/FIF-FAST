package co.id.fifgroup.ssoa.ui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;


public class AssetPopUpComposer extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	@Listen("onClick = #orderBtn")
	public void showModal(Event e) {
		//create a window programmatically and use it as a modal dialog.
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_popup.zul", null, null);
		win.doModal();
	}
}