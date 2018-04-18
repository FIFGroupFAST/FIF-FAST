package co.id.fifgroup.basicsetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.dto.FormulaDTO;
import co.id.fifgroup.basicsetup.service.FormulaService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class FormulaInquiryComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6559402290103849688L;
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable
	private Map<String, Object> arg;
	private ListModelList<FormulaDTO> listModelFormulaDto;
	private List<FormulaDTO> listFormulaDto;
	
	private String formulaName;
	private String formulaDesc;
	
	@Wire
	private Searchtextbox txtFormulaName;
	@Wire
	private Searchtextbox txtFormulaDesc;
	@Wire
	private Listbox lstFormula;
	@Wire Button btnNew;
	
	@WireVariable
	private FormulaService formulaServiceImpl;
	
	FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception{
		super.doAfterCompose(comp);
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}

	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	@Listen("onDownloadUserManual = #winFormulaInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}

	private void init() {
		initListModelFormula();
	}

	private void initListModelFormula() {
		listModelFormulaDto = new ListModelList<FormulaDTO>();
		lstFormula.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstFormula.setModel(listModelFormulaDto);
	}
	
	@Listen("onClick = button#btnFind; onOK = #txtFormulaName, #txtFormulaDesc")
	public void onFind() {
		formulaName = txtFormulaName.getValue();
		formulaDesc = txtFormulaDesc.getValue();
		if(formulaName.equals("%%") && formulaDesc.equals("%%")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -7016837579614863971L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						search();
					}
				}
				
			});
		} else {
			search();
		}
	}
	
	private void search() {
		listFormulaDto = formulaServiceImpl.getFormulaDtoByFormulaNameAndDescription(formulaName, formulaDesc);
		listModelFormulaDto.clear();
		listModelFormulaDto.addAll(listFormulaDto);
	}
	
	@Listen("onDetail = #lstFormula")
	public void onDetail(ForwardEvent evt) {
		FormulaDTO selectedData = (FormulaDTO) evt.getData();
		Map<String, Object> params = new HashMap<String,Object>();
		params.put(FormulaDTO.class.getName(), selectedData);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/formula_setup.zul", getSelf().getParent(), params);
		getSelf().detach();
	}

	@Listen("onClick = button#btnNew")
	public void addNew() {
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/formula_setup.zul", getSelf().getParent(), params);
		getSelf().detach();
	}
	
}
