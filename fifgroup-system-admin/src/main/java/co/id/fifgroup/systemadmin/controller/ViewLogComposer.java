package co.id.fifgroup.systemadmin.controller;

import java.util.Map;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ViewLogComposer extends SelectorComposer<Window>{
	
	private static final long serialVersionUID = 7427968868642445565L;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Textbox txtContent;
	@Wire
	
	private String content;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		content = (String) arg.get("fileContent");
		txtContent.setReadonly(true);
		txtContent.setValue(content);
	}
	
	@Listen("onClick = button#btnClose")
	public void onClose(){
		getSelf().detach();
	}
}
