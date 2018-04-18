package co.id.fifgroup.eligibility.controller;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Window;

@VariableResolver(DelegatingVariableResolver.class)
public class DecisionTableInquiryTestComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	
	
	@Listen("onClick = #btnRefresh")
	public void refresh() {
		
	}
	
	@Listen("onClick = #btnCreate")
	public void create() {
		
	}
	

}
