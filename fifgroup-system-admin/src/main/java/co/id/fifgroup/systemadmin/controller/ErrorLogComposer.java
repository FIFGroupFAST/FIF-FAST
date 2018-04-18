package co.id.fifgroup.systemadmin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.systemadmin.domain.ModuleErrorLog;
import co.id.fifgroup.systemadmin.domain.ModuleErrorLogExample;
import co.id.fifgroup.systemadmin.service.ErrorLogService;

@VariableResolver(DelegatingVariableResolver.class)
public class ErrorLogComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ErrorLogComposer.class);

	@WireVariable(rewireOnActivate = true)
	private transient ModuleService moduleServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient ErrorLogService errorLogService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Datebox dtbDateFrom;
	@Wire
	private Datebox dtbDateTo;
	@Wire
	private Listbox lstModule;
	@Wire
	private Listbox lstErrorLog;
	@Wire
	private Paging pgErrorLog;

	private ListModelList<ModuleErrorLog> listModelErrorLog;
	private List<ModuleErrorLog> listErrorLogs;
	private Date dateFrom;
	private Date dateTo;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateModule();
		init();
	}

	private void init() {
		dtbDateFrom.setFormat(FormatDateUI.getDateFormat());
		dtbDateTo.setFormat(FormatDateUI.getDateFormat());
		listModelErrorLog = new ListModelList<ModuleErrorLog>();
		lstErrorLog.setModel(listModelErrorLog);
	}
	
	@Listen("onDownloadUserManual = #winErrorLog")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void populateModule() {
		Module module = new Module();
		module.setModuleName("-Select-");
		ModuleExample example = new ModuleExample();
		example.setOrderByClause("MODULE_NAME");
		List<Module> modules = moduleServiceImpl.getModuleByExample(example);
		modules.add(0, module);
		ListModelList<Module> model = new ListModelList<Module>(modules);
		lstModule.setModel(model);
		lstModule.renderAll();
		lstModule.setSelectedIndex(0);
	}

	@Listen("onClick = button#btnFind")
	public void onFind() {
		dateFrom = dtbDateFrom.getValue();
		dateTo = dtbDateTo.getValue();
		if(dateFrom == null && dateTo == null && lstModule.getSelectedIndex() == 0) {
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
			ModuleErrorLogExample example = new ModuleErrorLogExample();
			if (lstModule.getSelectedItem() != null) {
				Module module = lstModule.getSelectedItem().getValue();
				if (module.getModuleId() != null) {
					if (dtbDateFrom.getValue() != null && dtbDateTo.getValue() != null) {
						example.createCriteria().andLogTimeBetween(dtbDateFrom.getValue(), dtbDateTo.getValue())
								.andModuleNameLikeInsensitive("%"+module.getModuleName()+"%");
					} else if (dtbDateFrom.getValue() != null) {
						example.createCriteria().andLogTimeGreaterThanOrEqualTo(dtbDateFrom.getValue())
								.andModuleNameLikeInsensitive("%"+module.getModuleName()+"%");
					} else if (dtbDateTo.getValue() != null) {
						example.createCriteria().andLogTimeLessThanOrEqualTo(dtbDateTo.getValue())
								.andModuleNameLikeInsensitive("%"+module.getModuleName()+"%");
					}
					example.createCriteria().andModuleNameLikeInsensitive("%"+module.getModuleName()+"%");
				}
			}
			if (dtbDateFrom.getValue() != null && dtbDateTo.getValue() != null) {
				example.createCriteria().andLogTimeBetween(dtbDateFrom.getValue(), dtbDateTo.getValue());
			} else if (dtbDateFrom.getValue() != null) {
				example.createCriteria().andLogTimeGreaterThanOrEqualTo(dtbDateFrom.getValue());
			} else if (dtbDateTo.getValue() != null) {
				example.createCriteria().andLogTimeLessThanOrEqualTo(dtbDateTo.getValue());
			}
	
			/*int totalSize = errorLogService.countErrorLogByExample(example);
			pgErrorLog.setTotalSize(totalSize);
			pgErrorLog.setPageSize(GlobalVariable.getMaxRowPerPage());
			pgErrorLog.setActivePage(0);*/
			generateDataAndPaging();
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	private void generateDataAndPaging() {
		ModuleErrorLogExample example = new ModuleErrorLogExample();
		if (lstModule.getSelectedItem() != null) {
			Module module = lstModule.getSelectedItem().getValue();
			if (module.getModuleId() != null) {
				if (dtbDateFrom.getValue() != null && dtbDateTo.getValue() != null) {
					example.createCriteria().andLogTimeBetween(dtbDateFrom.getValue(), dtbDateTo.getValue())
							.andModuleNameLikeInsensitive("%"+module.getModuleName()+"%");
				} else if (dtbDateFrom.getValue() != null) {
					example.createCriteria().andLogTimeGreaterThanOrEqualTo(dtbDateFrom.getValue())
							.andModuleNameLikeInsensitive("%"+module.getModuleName()+"%");
				} else if (dtbDateTo.getValue() != null) {
					example.createCriteria().andLogTimeLessThanOrEqualTo(dtbDateTo.getValue())
							.andModuleNameLikeInsensitive("%"+module.getModuleName()+"%");
				}
				example.createCriteria().andModuleNameLikeInsensitive("%"+module.getModuleName()+"%");
			}
		}
		if (dtbDateFrom.getValue() != null && dtbDateTo.getValue() != null) {
			example.createCriteria().andLogTimeBetween(dtbDateFrom.getValue(), dtbDateTo.getValue());
		} else if (dtbDateFrom.getValue() != null) {
			example.createCriteria().andLogTimeGreaterThanOrEqualTo(dtbDateFrom.getValue());
		} else if (dtbDateTo.getValue() != null) {
			example.createCriteria().andLogTimeLessThanOrEqualTo(dtbDateTo.getValue());
		}
		
		example.setOrderByClause("LOG_TIME desc");

		listErrorLogs = errorLogService.getErrorLogByExample(example);
		listModelErrorLog.clear();
		listModelErrorLog.addAll(listErrorLogs);
	}

	/*private void setErrorLog() {
		ModuleErrorLog errorLog = new ModuleErrorLog();
		Long trxId = null;
		Date logTime = null;
		String moduleName = "";
		String errorMessage = "";
		errorLog.setErrorMessage(errorMessage);
		errorLog.setLogTime(logTime);
		errorLog.setModuleName(moduleName);
		errorLog.setTrxId(trxId);
	}*/
	
	@Listen("onPaging = #winErrorLog")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging();
	}
}
