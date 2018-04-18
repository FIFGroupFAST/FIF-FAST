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
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.basicsetup.service.GlobalSettingService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class GlobalSettingInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	@WireVariable
	private Map<String, Object> arg;
	@Wire
	private Searchtextbox txtCode;
	@Wire
	private Listbox lstGlobalSetting;
	@Wire
	private Button btnNew;
	@Wire
	private Searchtextbox txtDescription;
	
	@WireVariable(rewireOnActivate=true)
	private transient GlobalSettingService globalSettingServiceImpl;
	
	private List<GlobalSettingDTO> globalSettings;
	
	private ListModelList<GlobalSettingDTO> listModelGlobalSetting;
	
	private String code;
	
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
		initListModelLstGlobalSetting();
		lstGlobalSetting.setPageSize(GlobalVariable.getMaxRowPerPage());
	}
	
	private void initListModelLstGlobalSetting() {
		listModelGlobalSetting = new ListModelList<GlobalSettingDTO>();
		lstGlobalSetting.setModel(listModelGlobalSetting);
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/global_setting_detail.zul", getSelf().getParent(), params);
		getSelf().detach();
	}

	/*private void generateDataAndPaging() {
		globalSettings = globalSettingServiceImpl.getGlobalSettingDtoByCode(code, pgGlobalSetting.getPageSize(), pgGlobalSetting.getPageSize() * pgGlobalSetting.getActivePage());
		listModelGlobalSetting.clear();
		listModelGlobalSetting.addAll(globalSettings);
	}*/
	
	@Listen("onClick = button#btnFind; onOK = #txtCode")
	public void onFind() {
		code = txtCode.getValue();
		description = txtDescription.getValue();
		if(code.equals("%%") && description.equals("%%")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1327000264261145664L;

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
		globalSettings = globalSettingServiceImpl.getGlobalSettingDtoByCode(txtCode.getValue(), txtDescription.getValue());
		listModelGlobalSetting.clear();
		listModelGlobalSetting.addAll(globalSettings);
	}
	
	@Listen("onDownloadUserManual = #winGlobalSettingInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen("onDetail = #lstGlobalSetting")
	public void onDetail(ForwardEvent evt) {
		GlobalSettingDTO selectedData = (GlobalSettingDTO) evt.getData();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(GlobalSettingDTO.class.getName(), selectedData);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/global_setting_detail.zul", getSelf().getParent(), params);
		getSelf().detach();
	}

	
	
}
