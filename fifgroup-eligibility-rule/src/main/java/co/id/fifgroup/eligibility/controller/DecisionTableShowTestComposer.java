package co.id.fifgroup.eligibility.controller;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.eligibility.service.DecisionTableService;
import co.id.fifgroup.eligibility.ui.DecisionTableWrapper;

@VariableResolver(DelegatingVariableResolver.class)
public class DecisionTableShowTestComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	@Wire
	private Longbox txtDecisionTableId;
	@Wire
	private Textbox txtModule;
	
	@Wire
	private DecisionTableWrapper dtWrapper;
	
	@WireVariable(rewireOnActivate = true)
	private transient DecisionTableService decisionTableServiceImpl;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	@Listen("onClick = #btnRender")
	public void onRender() {
		dtWrapper.setDecisionTable(decisionTableServiceImpl.findDecisionTableById(txtModule.getValue(), txtDecisionTableId.getValue()));
	}
	
	@Listen("onClick = #btnSave")
	public void onSave() {
		decisionTableServiceImpl.save(txtModule.getValue().toUpperCase(), dtWrapper.getDecisionTable());
	}

}
