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

import co.id.fifgroup.basicsetup.dto.FormulaParameterDTO;
import co.id.fifgroup.basicsetup.service.FormulaParameterService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class FormulaParameterInquiryComposer extends SelectorComposer<Window> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8154042111345730853L;

	@WireVariable
	private Map<String, Object> arg;

	private Map<String, Object> params = new HashMap<String, Object>();

	@Wire
	private Searchtextbox txtParameterName;
	@Wire
	private Searchtextbox txtDescription;
	@Wire
	private Listbox lstFormulaParameter;
	@Wire
	private Button btnNew;
	
	private List<FormulaParameterDTO> listFormulaParameterDto;
	
	private ListModelList<FormulaParameterDTO> listModelFormulaParameterDto;
	
	@WireVariable
	private FormulaParameterService formulaParameterServiceImpl;
	
	private String parameterName;
	private String description;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}

	private void init() {
		initListModelFormulaParameter();
	}
	

	@Listen("onDownloadUserManual = #winFormulaParameterInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	
	private void initListModelFormulaParameter() {
		listModelFormulaParameterDto = new ListModelList<FormulaParameterDTO>();
		lstFormulaParameter.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstFormulaParameter.setModel(listModelFormulaParameterDto);
		
	}

	@Listen("onClick = button#btnFind; onOK = #txtParameterName, #txtDescription")
	public void onFind(){
		parameterName= txtParameterName.getValue().trim();
		description = txtDescription.getValue().trim();
		if(parameterName.equals("%%") && description.equals("%%")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 7225053009731752279L;

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
		if(description.equals("%%")){
			description = null;
		}
		listFormulaParameterDto = formulaParameterServiceImpl.getFormulaDtoByFormulaParameterNameAndDescription(parameterName, description);
		listModelFormulaParameterDto.clear();
		listModelFormulaParameterDto.addAll(listFormulaParameterDto);
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/formula_parameter_setup.zul", getSelf().getParent(), params);
		getSelf().detach();
	}
	
	@Listen("onDetail = #lstFormulaParameter")
	public void onDetail(ForwardEvent evt){
		FormulaParameterDTO selectedData = (FormulaParameterDTO) evt.getData();
		Map<String, Object> params = new HashMap<String,Object>();
		params.put(FormulaParameterDTO.class.getName(), selectedData);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/formula_parameter_setup.zul", getSelf().getParent(), params);
		getSelf().detach();
	}
	
	

}
