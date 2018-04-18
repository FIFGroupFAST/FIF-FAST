package co.id.fifgroup.systemadmin.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
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

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

import co.id.fifgroup.audit.TrxType;
import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.systemadmin.domain.User;
import co.id.fifgroup.systemadmin.domain.UserExample;
import co.id.fifgroup.systemadmin.dto.AuditTrailDTO;
import co.id.fifgroup.systemadmin.service.AuditTrailService;
import co.id.fifgroup.systemadmin.service.UserService;

@VariableResolver(DelegatingVariableResolver.class)
public class AuditTrailComposer extends SelectorComposer<Window> {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(FunctionAccessControlComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient ModuleService moduleServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient AuditTrailService auditTrailService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient UserService userService;
	
	@Wire
	private Datebox dtbDateFrom;
	@Wire
	private Datebox dtbDateTo;
	@Wire
	private Listbox lstModule;
	@Wire
	private Listbox lstTransactionType;
	@Wire
	private CommonPopupBandbox txtUser;
	@Wire
	private Listbox lstAuditTrail;
	@Wire
	private Paging pgAuditTrail;
	
	private int totalSize = 0;
	private ListModelList<AuditTrailDTO> listModelAudit;
	private List<AuditTrailDTO> listAudit;
	private Date dateFrom;
	private Date dateTo;
	private String userName;
	private BigDecimal moduleId;
	private String trxType;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateModule();
		init();
	}
	
	private void init() {
		dtbDateFrom.setFormat(FormatDateUI.getDateFormat());
		dtbDateTo.setFormat(FormatDateUI.getDateFormat());
		listModelAudit = new ListModelList<AuditTrailDTO>();
		lstAuditTrail.setModel(listModelAudit);
	}
	
	@Listen("onDownloadUserManual = #winAuditTrail")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen("onCreate=#txtUser")
	public void loadUserData(){
		txtUser.setTitle("List of User");
		txtUser.setSearchText("User Name");
		txtUser.setHeaderLabel("User");
		txtUser.setSearchDelegate(new SerializableSearchDelegate<User>() {

			private static final long serialVersionUID = -9008913765332633929L;

			@Override
			public List<User> findBySearchCriteria(String searchCriteria, int limit, int offset) {				
				UserExample example = new UserExample();
				example.createCriteria().andUserNameLikeInsensitive(searchCriteria);
				example.setOrderByClause("UPPER(USER_NAME) ASC");
				return userService.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				UserExample example = new UserExample();
				example.createCriteria().andUserNameLikeInsensitive(searchCriteria);
				return userService.countUserByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, User data) {
				keyValue.setKey(data.getUserName());
				keyValue.setValue(data.getUserName());
				keyValue.setDescription(data.getUserName());
			}
		});
	}
	
	private void populateModule() {		
		Module module = new Module();
		module.setModuleName("-Select-");
		module.setModuleId(-1L);
		ModuleExample example = new ModuleExample();
		example.setOrderByClause("MODULE_NAME");
		List<Module> modules = moduleServiceImpl.getModuleByExample(example);
		modules.add(0, module);
		ListModelList<Module> model = new ListModelList<Module>(modules);
		lstModule.setModel(model);
		lstModule.renderAll();
		lstModule.setSelectedIndex(0);
	}
	
	@Listen("onCreate=#lstTransactionType")
	public void loadTransactionType(){
		ListModelList<TrxType> model = new ListModelList<TrxType>(TrxType.values());
		lstTransactionType.setModel(model);
		lstTransactionType.renderAll();
		lstTransactionType.setSelectedIndex(0);
	}
		
	@Listen ("onClick = button#btnView")
	public void onView() {
		dateFrom = dtbDateFrom.getValue();
		dateTo = dtbDateTo.getValue();
		if(txtUser.getKeyValue() != null) {
			userName = (String) txtUser.getKeyValue().getKey();
		} else {
			userName = null;
		}
		
		if(dateFrom != null)
			dateFrom = DateUtil.truncate(dateFrom);
		
		if(dateTo != null)
			dateTo = DateUtil.truncate(dateTo);
		
		
		if(lstModule.getSelectedIndex() > 0) {
			moduleId = BigDecimal.valueOf(((Long)lstModule.getSelectedItem().getValue()));
		} else {
			moduleId = null;
		}
		
		if(lstTransactionType.getSelectedIndex() > 0) {
			trxType = lstTransactionType.getSelectedItem().getValue();
		} else {
			trxType = null;
		}
		
		
		
		if(dateFrom == null && dateTo == null && lstModule.getSelectedIndex() == 0 && userName == null && lstTransactionType.getSelectedIndex() == 0) {
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
			AuditTrailDTO auditTrailDTO = new AuditTrailDTO();
			
			auditTrailDTO.setModuleId(moduleId);
			auditTrailDTO.setTransactionType(trxType);
			auditTrailDTO.setUserName(userName);
			if(dateFrom != null && dateTo == null)
				dateTo = DateUtil.MAX_DATE;
			
			totalSize = auditTrailService.countAuditByExample(auditTrailDTO, dateFrom, dateTo);
			pgAuditTrail.setTotalSize(totalSize);
			pgAuditTrail.setPageSize(GlobalVariable.getMaxRowPerPage());
			pgAuditTrail.setActivePage(0);
			generateDataAndPaging();
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	@Listen("onPaging = #winAuditTrail")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging();
	}
	
	private void generateDataAndPaging() {
		AuditTrailDTO auditTrailDTO = new AuditTrailDTO();
		
		auditTrailDTO.setModuleId(moduleId == null ? null : moduleId);
		auditTrailDTO.setTransactionType(trxType);
		auditTrailDTO.setUserName(userName);
		
		listAudit = auditTrailService.getAuditByExample(auditTrailDTO, dateFrom, dateTo, pgAuditTrail.getPageSize(), pgAuditTrail.getActivePage() * pgAuditTrail.getPageSize());
		listModelAudit.clear();
		listModelAudit.addAll(listAudit);
	}
	
	@Listen ("onClick = button#btnGenerate")
	public void onGenerate() {
		if(totalSize > 0) {
			auditTrailService.generatePDF(listModelAudit);
		} else {
			Messagebox.show(Labels.getLabel("sam.dataEmpty"), "Information", Messagebox.YES, Messagebox.INFORMATION);
		}
	}
}
