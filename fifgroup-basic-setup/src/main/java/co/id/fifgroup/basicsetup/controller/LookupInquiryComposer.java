package co.id.fifgroup.basicsetup.controller;

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

import co.id.fifgroup.basicsetup.constant.LookupType;
import co.id.fifgroup.basicsetup.domain.LookupHeader;
import co.id.fifgroup.basicsetup.dto.LookupDTO;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class LookupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(LookupInquiryComposer.class);
	@WireVariable
	private Map<String, Object> arg;
	@Wire
	private Searchtextbox txtLookupName;
	@Wire
	private Searchtextbox txtDesc;
	@Wire
	private Listbox lstType;
	@Wire
	private Listbox lstLookupHeader;
	@Wire
	private Button btnNew;
	
	@WireVariable(rewireOnActivate=true)
	private transient LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private ListModelList<LookupType> listModelLookupType;
	private ListModelList<LookupHeader> listModelLookupHeader;
	
	private List<LookupDTO> lookupDtos;
	
	private String lookupName;
	private String description;
	private LookupType lookupType;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		initData();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	private void init() {
		initListModelLookupType();
		initListModelLookupHeader();
	}
	
	private void initData() {
		
	}
	
	@Listen("onDownloadUserManual = #winLookupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void initListModelLookupType() {
		listModelLookupType = new ListModelList<LookupType>(LookupType.values());
		lstType.setModel(listModelLookupType);
		lstType.renderAll();
		listModelLookupType.addToSelection(LookupType.SELECT);
	}
	
	private void initListModelLookupHeader() {
		listModelLookupHeader = new ListModelList<LookupHeader>();
		lstLookupHeader.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstLookupHeader.setModel(listModelLookupHeader);
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/lookup_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = button#btnFind; onOK = #txtLookupName, #txtDesc")
	public void onFind() {
		lookupName = txtLookupName.getValue();
		description = txtDesc.getValue();
		lookupType = (LookupType) lstType.getSelectedItem().getValue();
		if(lookupType.equals(LookupType.SELECT)) lookupType = null;
		if(lookupName.equals("%%") && description.equals("%%") && lookupType == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
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
		lookupDtos = lookupServiceImpl.getLookupHeaderDtoByLookupNameAndDescriptionAndType(lookupName, description, lookupType, securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
		listModelLookupHeader.clear();
		listModelLookupHeader.addAll(lookupDtos);
	}
	
	@Listen("onDetail = #lstLookupHeader")
	public void onDetail(ForwardEvent evt) {
		LookupDTO lookupHeaderDto = (LookupDTO) evt.getData();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(LookupDTO.class.getName(), lookupHeaderDto);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/lookup_detail.zul", getSelf().getParent(), params);
		getSelf().detach();
	}
	
}
