package co.id.fifgroup.eligibility.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;

import co.id.fifgroup.eligibility.util.FactResolver;
import co.id.fifgroup.eligibility.util.ParameterExtractor;

@VariableResolver(DelegatingVariableResolver.class)
public class QueryTestComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 3004870612258328941L;
	
	@WireVariable
	private Map<String, Object> arg;
	
	private String query;
	
	@Wire Rows parameterForms;
	
	@Wire Textbox txtResult;
	
	@WireVariable
	private transient FactResolver factResolver;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		query = (String) arg.get("query");
		for (String parameter : ParameterExtractor.getParameters(query)) {
			Row row = new Row();
			row.appendChild(new Label(parameter));
			Textbox textbox = new Textbox();
			textbox.setId(parameter);
			row.appendChild(textbox);
			row.setParent(parameterForms);
		}
	}
	
	@Listen("onClick = #btnTest") 
	public void testQuery() {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("query", query);
		List<Component> params = Selectors.find(parameterForms, "textbox");
		
		for (Component param : params) {
			String value = ((Textbox) param).getValue(); 
			parameters.put(param.getId(), Strings.isNullOrEmpty(value) ? null : value) ;
		}
		renderResult(factResolver.resolveFact(parameters));
	}
	
	@Listen("onClick = #btnCancel") 
	public void close() {
		getSelf().onClose();
	}
	
	public void renderResult(List<Map<String,Object>> result) {
		if (result != null) {
			StringBuilder sb = new StringBuilder();
			int i = 1;
			for (Map<String, Object> row : result) {
				sb.append("Row ").append(i++);
				sb.append(" : \n").append(row).append("\n	");
			}
			txtResult.setValue(sb.toString());
		}
	}
	
	

}
