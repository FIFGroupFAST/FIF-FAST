package co.id.fifgroup.ssoa.ui;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;
import co.id.fifgroup.ssoa.service.StockOpnameService;

@VariableResolver(DelegatingVariableResolver.class)
public class StockOpnamePopupImageComposer extends SelectorComposer<Window> {
	
	@Wire
	private Window winImagePopup;
	@Wire
	private Image image;

	private static final long serialVersionUID = 1L;
	@WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;
	@WireVariable("arg")
	private Map<String, Object> arg;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		try {
			
			super.doAfterCompose(comp);
			//byte[] images = (byte[])arg.get("images");
			String filePath = (String)arg.get("images");
			File file = new File(filePath);
			//byte[] bFile = new byte[(int) file.length()];
			AImage aimagen = null;
			try {
				aimagen = new AImage(file);
				image.setContent(aimagen);
			} catch (IOException e) {
				System.err.println(e.getCause());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick = #btnClose")
	public void modalImage(Event e) {
		winImagePopup.detach();
	}
}