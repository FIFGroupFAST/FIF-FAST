package co.id.fifgroup.personneladmin.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;

public class ContainerImageComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	/*private Logger logger = LoggerFactory.getLogger(AccountInformationComposer.class);*/
	
	@WireVariable("arg")
	private Map<String, Object> arg;

	@Wire
	private Image imgPhoto;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		AImage image = (AImage) arg.get("image");
		imgPhoto.setContent(image);	
	}
}
