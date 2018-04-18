package co.id.fifgroup.basicsetup.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.common.FormulaParameterNavigationHandler;
import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.basicsetup.domain.FormulaParameterExample;
import co.id.fifgroup.basicsetup.service.FormulaParameterService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;


@VariableResolver(DelegatingVariableResolver.class)
public class FormulaGetParameterComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 564665782307687906L;
	
	
	private static Logger log = LoggerFactory
			.getLogger(FormulaInquiryComposer.class);
	
	@WireVariable
	private Map<String, Object> arg;
	
	@Wire
	private Searchtextbox txtParameterName;
	@Wire
	private Searchtextbox txtDescription;
	@Wire
	private Listbox lstFormulaParameter;
	
	@WireVariable(rewireOnActivate=true)
	private transient FormulaParameterService formulaParameterServiceImpl;
	
	private ListModelList<FormulaParameter> listModelFormulaParameter;
	
	private FormulaParameterNavigationHandler formulaParameterNavigationHandler;
	
	private String parameterName;
	private String description;
	private int currentPageNumber;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception{
		super.doAfterCompose(comp);
		init();
		if(arg.containsKey(FormulaParameterNavigationHandler.class.getName())) {
			formulaParameterNavigationHandler = (FormulaParameterNavigationHandler) arg.get(FormulaParameterNavigationHandler.class.getName());
		}
	}
	
	private void init() {
		initListModelFormulaVersionParam();
	}

	private void initListModelFormulaVersionParam() {
		listModelFormulaParameter = new ListModelList<FormulaParameter>();
		lstFormulaParameter.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstFormulaParameter.setModel(listModelFormulaParameter);
		
	}
	

	@Listen("onClick = button#btnFind")
	public void onFind(){
		parameterName = txtParameterName.getValue();
		description = txtDescription.getValue();
		currentPageNumber=0;
		generateDataAndPaging(currentPageNumber);
	}

	private void generateDataAndPaging(int pageNumber) {
		FormulaParameterExample formulaParameterExample = new FormulaParameterExample();
		formulaParameterExample.createCriteria().andParameterNameLikeInsensitive(parameterName).andDescriptionLikeInsensitive(description);
		
		listModelFormulaParameter.clear();
		listModelFormulaParameter.addAll(formulaParameterServiceImpl.getFormulaParameterByExample(formulaParameterExample));
		
		
	}
	
	@Listen("onGet = #lstFormulaParameter")
	public void onGet(ForwardEvent evt) {
		FormulaParameter formulaParameter = (FormulaParameter) evt.getData();
		if(formulaParameterNavigationHandler != null) {
			formulaParameterNavigationHandler.setFormulaParameter(formulaParameter);
			getSelf().detach();
		}
	}
	
}
