package co.id.fifgroup.systemadmin.controller;

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

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.systemadmin.domain.Function;
import co.id.fifgroup.systemadmin.domain.FunctionExample;
import co.id.fifgroup.systemadmin.domain.User;
import co.id.fifgroup.systemadmin.domain.UserExample;
import co.id.fifgroup.systemadmin.dto.UserAccessRecordDTO;
import co.id.fifgroup.systemadmin.service.FunctionService;
import co.id.fifgroup.systemadmin.service.UserAccessRecordService;
import co.id.fifgroup.systemadmin.service.UserService;

@VariableResolver(DelegatingVariableResolver.class)
public class UserAccessRecordComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(UserAccessRecordComposer.class);

	@WireVariable(rewireOnActivate = true)
	private transient ModuleService moduleServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient UserAccessRecordService userAccessRecordService;
	@WireVariable(rewireOnActivate = true)
	private transient FunctionService functionService;
	@WireVariable(rewireOnActivate = true)
	private transient UserService userService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Listbox lstModule;
	@Wire
	private CommonPopupBandbox lsFunctionName;
	@Wire
	private CommonPopupBandbox lsUserName;
	@Wire
	private Datebox dtbDateFrom;
	@Wire
	private Datebox dtbDateTo;
	@Wire
	private Listbox lstUserAccessRecord;
	@Wire
	private Paging pgUserAccessRecord;

	private ListModelList<UserAccessRecordDTO> listModelUserAccessRecordDto;
	private List<UserAccessRecordDTO> listUserAccessRecordsDto;
	private String moduleName = null;
	private String functionName;
	private String userName;
	private Date dateFrom;
	private Date dateTo;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateModule();
		populateFunction();
		populateUsername();
		init();
	}

	private void init() {
		dtbDateFrom.setFormat(FormatDateUI.getDateFormat());
		dtbDateTo.setFormat(FormatDateUI.getDateFormat());
		listModelUserAccessRecordDto = new ListModelList<UserAccessRecordDTO>();
		lstUserAccessRecord.setModel(listModelUserAccessRecordDto);
	}
	
	private void populateFunction(){
		lsFunctionName.setTitle("List of Function");
		lsFunctionName.setSearchText("Function Name");
		lsFunctionName.setHeaderLabel("Function");
		lsFunctionName.setSearchDelegate(new SerializableSearchDelegate<Function>() {

			private static final long serialVersionUID = -9008913765332633929L;

			@Override
			public List<Function> findBySearchCriteria(String searchCriteria, int limit, int offset) {		
				FunctionExample example = new FunctionExample();
				example.createCriteria().andFunctionNameLikeInsensitive(searchCriteria);
				example.setOrderByClause("UPPER(FUNCTION_NAME) ASC");
				return functionService.selectByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {			
				FunctionExample example = new FunctionExample();
				example.createCriteria().andFunctionNameLikeInsensitive(searchCriteria);
				return functionService.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, Function data) {
				keyValue.setKey(data.getFunctionName());
				keyValue.setValue(data.getFunctionName());
				keyValue.setDescription(data.getFunctionName());
			}
		});
	}
	
	private void populateUsername(){
		lsUserName.setTitle("List of User");
		lsUserName.setSearchText("User Name");
		lsUserName.setHeaderLabel("User");
		lsUserName.setSearchDelegate(new SerializableSearchDelegate<User>() {

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
	
	@Listen("onDownloadUserManual = #winUserAccessRecord")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void populateModule() {
		ModuleExample example = new ModuleExample();
		example.setOrderByClause("MODULE_NAME");
		List<Module> modules = moduleServiceImpl.getModuleByExample(example);
		Module module = new Module();
		module.setModuleName("-Select-");
		modules.add(0, module);
		ListModelList<Module> model = new ListModelList<Module>(modules);
		lstModule.setModel(model);
		lstModule.renderAll();
		lstModule.setSelectedIndex(0);
	}

	@Listen("onClick = button#btnFind")
	public void onFind() {
		if(lsFunctionName.getKeyValue() != null)
			functionName = "%"+(String) ((KeyValue)lsFunctionName.getKeyValue()).getValue()+"%";
		else
			functionName = "%%";
//		if(lsUserName.getKeyValue() != null)
//			userName = "%"+(String) ((KeyValue)lsUserName.getKeyValue()).getValue()+"%";

		if(lsUserName.getKeyValue() != null)
			userName = (String) ((KeyValue)lsUserName.getKeyValue()).getValue();
		else
			userName = "%%";

		dateFrom = dtbDateFrom.getValue();
		dateTo = dtbDateTo.getValue();
		if(lstModule.getSelectedIndex() == 0 
				&& functionName.equals("%%") 
				&& userName.equals("%%") 
				&& dateFrom == null && dateTo == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), 
					Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {
				
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
			if (lstModule.getSelectedItem() != null) {
				Module module = lstModule.getSelectedItem().getValue();
				if (module.getModuleId() != null) {
					moduleName = module.getModuleName();
				}
			}
			/*
			if(dtbDateFrom.getValue() == null && dtbDateTo.getValue() != null) {
				dateTo = dtbDateTo.getValue();
				dateFrom = null;
			}*/
			
			dateTo = dtbDateTo.getValue();
			dateFrom = dtbDateFrom.getValue();
			
			generateDataAndPaging();
		} catch (Exception e) {
			log.error("error", e);
		}
	}


	private void generateDataAndPaging() {
		if (lstModule.getSelectedItem() != null) {
			Module module = lstModule.getSelectedItem().getValue();
			if (module.getModuleId() != null) {
				moduleName = module.getModuleName();
			}
		}
		
		listUserAccessRecordsDto = userAccessRecordService.getUserAccessRecordDtoByAllParameter(moduleName,
						functionName, userName, dateFrom, dateTo);
		listModelUserAccessRecordDto.clear();
		listModelUserAccessRecordDto.addAll(listUserAccessRecordsDto);
	}
	
	@Listen("onPaging = #winUserAccessRecord")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging();
	}
}
