package co.id.fifgroup.ssoa.controller;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.dto.SystemPropertyDTO;
import co.id.fifgroup.ssoa.service.SystemPropertyService;

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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;


@VariableResolver(DelegatingVariableResolver.class)
public class SystemPropertyComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	private Map<String, Object> arg;

	private Map<String, Object> params = new HashMap<String, Object>();

	@WireVariable(rewireOnActivate = true)
	private transient SystemPropertyService systemPropertyServiceImpl;

	@Wire
	private Searchtextbox txtSystemPropertyCode;
	@Wire
	private Searchtextbox txtSystemPropertyName;
	@Wire
	private Listbox lstSystemProperty;
	@Wire
	private Button btnNew;

	private List<SystemPropertyDTO> systemProperties;

	private ListModelList<SystemPropertyDTO> listModelSystemPropertyDto;
	
	private String systemPropertyCode;
	
	private String systemPropertyName;

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
		initListModelSystemPropertyDto();
	}
	
	private void initListModelSystemPropertyDto() {
		listModelSystemPropertyDto = new ListModelList<SystemPropertyDTO>();
		//lstSystemProperty.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstSystemProperty.setModel(listModelSystemPropertyDto);
	}
	
	/*@Listen("onDownloadUserManual = #winSystemProperty")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}*/
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/ssoa/system_property_add.zul",
				getSelf().getParent(), params);
		getSelf().detach();
	}

	@Listen("onClick = button#btnFind; onOK = #txtSystemPropertyCode")
	public void onFind() {
		System.out.println("msk onFind");
		systemPropertyCode = txtSystemPropertyCode.getValue();
		if(systemPropertyCode!=null && systemPropertyCode.equals("%%")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 5439527390796683627L;

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
		System.out.println("msk search"+systemPropertyServiceImpl);
		systemProperties = systemPropertyServiceImpl.getSystemPropertyDtoBySystemPropertyCodeName(systemPropertyCode, systemPropertyName);
		listModelSystemPropertyDto.clear();
		listModelSystemPropertyDto.addAll(systemProperties);
	}
	
	@Listen("onEdit = #lstSystemProperty")
	public void onEdit(ForwardEvent evt){
		SystemPropertyDTO selectedData = (SystemPropertyDTO)evt.getData();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SystemPropertyDTO.class.getName(), selectedData);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/ssoa/system_property_add.zul",getSelf().getParent(), params);
		getSelf().detach();
	}

	@Listen("onDelete= #lstSystemProperty")
	public void onDelete(ForwardEvent event){
		final SystemPropertyDTO systemPropertyDto = (SystemPropertyDTO) event.getData();
		Messagebox.show("Are you sure want to delete this data?", "Confirmation", Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = -8756250972566999901L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						systemPropertyServiceImpl.delete(systemPropertyDto);
						search();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	/*@Listen("onDelete = #lstSystemProperty")
	public void onDelete(ForwardEvent evt){
		SystemPropertyDTO selectedData = (SystemPropertyDTO)evt.getData();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SystemPropertyDTO.class.getName(), selectedData);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/ssoa/system_property.zul", getSelf().getParent(), params);
		getSelf().detach();
	}*/
}