package co.id.fifgroup.systemadmin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.systemadmin.domain.Function;
import co.id.fifgroup.systemadmin.domain.FunctionExample;
import co.id.fifgroup.systemadmin.dto.FunctionDTO;
import co.id.fifgroup.systemadmin.service.FunctionService;

@VariableResolver(DelegatingVariableResolver.class)
public class FunctionInquiryComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(FunctionInquiryComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient FunctionService functionService;
	@WireVariable(rewireOnActivate = true)
	private transient ModuleService moduleServiceImpl;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Searchtextbox txtFunctionName;
	@Wire
	private Searchtextbox txtDescription;
	@Wire
	private Listbox lstModule;
	@Wire
	private Listbox lstFunction;
	@Wire
	private Listbox lstFunctionDownload;
	@Wire
	private Paging pgFunction;
	@Wire
	private Paging pgFunctionTop;
	@Wire
	private Button btnNew;
	
	private Function function;
	private ListModelList<FunctionDTO> listModelFunction;
	private ListModelList<FunctionDTO> listModelDownload;
	private List<FunctionDTO> listFunction;
	private int totalSize;
	private String functionName;
	private String description;
	
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		populateModule();
		init();
		setFunctionSecurity();
	}
	
	private void setFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	private void init() {
		listModelFunction = new ListModelList<FunctionDTO>();
		lstFunction.setModel(listModelFunction);
		listModelDownload = new ListModelList<FunctionDTO>();
		lstFunctionDownload.setModel(listModelDownload);
	}
	
	@Listen("onDownloadUserManual = #winFunctionInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void populateModule() {
		Module module = new Module();
		module.setModuleName("-Select-");
		ModuleExample example = new ModuleExample();
		example.createCriteria().andDateFromLessThanOrEqualTo(DateUtil.truncate(new Date()))
		.andDateToGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
		example.setOrderByClause("MODULE_NAME");
		List<Module> modules = moduleServiceImpl.getModuleByExample(example);
		modules.add(0, module);
		ListModelList<Module> model = new ListModelList<Module>(modules);
		lstModule.setModel(model);
		lstModule.renderAll();
		lstModule.setSelectedIndex(0);
	}
	
	@Listen ("onClick = button#btnFind")
	public void find() {
		functionName = txtFunctionName.getValue();
		description = txtDescription.getValue();
		if(functionName.equals("%%") && description.equals("%%") && lstModule.getSelectedIndex() == 0) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {
				
				private static final long serialVersionUID = -8756250972566999901L;
				
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
		try {
			FunctionExample example = new FunctionExample();
			if(lstModule.getSelectedItem() != null) {
				Module module = lstModule.getSelectedItem().getValue();
				if(module.getModuleId() != null) {
					example.createCriteria().andFunctionNameLikeInsensitive(txtFunctionName.getValue())
						.andDescriptionLikeInsensitive(txtDescription.getValue())
						.andModuleIdEqualTo(module.getModuleId());				
				}
			}
			
			example.createCriteria().andFunctionNameLikeInsensitive(txtFunctionName.getValue())
				.andDescriptionLikeInsensitive(txtDescription.getValue());
			
			totalSize = functionService.countByExample(example);
			pgFunction.setTotalSize(totalSize);
			pgFunction.setPageSize(GlobalVariable.getMaxRowPerPage());
			pgFunction.setActivePage(0);
			pgFunctionTop.setTotalSize(totalSize);
			pgFunctionTop.setPageSize(GlobalVariable.getMaxRowPerPage());
			pgFunctionTop.setActivePage(0);
			generateDownloadableData();
			generateDataAndPaging(null);
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	private void generateDownloadableData() {
		FunctionDTO example = new FunctionDTO();
		if(lstModule.getSelectedItem() != null) {
			Module module = lstModule.getSelectedItem().getValue();
			if(module.getModuleId() != null) {
				example.setModuleId(module.getModuleId());
			}
		}
		
		example.setFunctionName(txtFunctionName.getValue());
		example.setDescription(txtDescription.getValue());		
		List<FunctionDTO> listFunction = functionService.findByExample(example);
		listModelDownload.clear();
		listModelDownload.addAll(listFunction);
	}

	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/function_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDetail= #winFunctionInquiry")
	public void onDetail(ForwardEvent event){
		function = (Function) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("function", function);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/function_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

	@Listen("onPaging = #winFunctionInquiry")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging(evt);
	}
	
	private void generateDataAndPaging(ForwardEvent evt) {
		if (evt != null && evt.getOrigin().getTarget().getId().equals("pgFunction")) {
			pgFunctionTop.setActivePage(pgFunction.getActivePage());
		} else {
			pgFunction.setActivePage(pgFunctionTop.getActivePage());
		}
		FunctionDTO example = new FunctionDTO();
		if(lstModule.getSelectedItem() != null) {
			Module module = lstModule.getSelectedItem().getValue();
			if(module.getModuleId() != null) {
				example.setModuleId(module.getModuleId());
			}
		}
		
		example.setFunctionName(txtFunctionName.getValue());
		example.setDescription(txtDescription.getValue());		
		listFunction = functionService.findByExample(example, pgFunction.getPageSize(), pgFunction.getActivePage() * pgFunction.getPageSize());
		listModelFunction.clear();
		listModelFunction.addAll(listFunction);
	}
}
