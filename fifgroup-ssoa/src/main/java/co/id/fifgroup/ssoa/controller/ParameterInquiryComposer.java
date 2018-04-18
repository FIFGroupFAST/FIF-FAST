package co.id.fifgroup.ssoa.controller;


import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.ssoa.domain.Parameter;
import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.service.ParameterService;

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


@VariableResolver(DelegatingVariableResolver.class)
public class ParameterInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ParameterInquiryComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient ParameterService parameterService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Searchtextbox txtName;
	@Wire
	private Searchtextbox txtCode;
	@Wire
	private Listbox parameterListbox;
	@Wire
	private Paging pgParameter;
	@Wire
	private Paging pgParameterTop;
	@Wire
	private Button btnNew;
	
	private List<ParameterDTO> listParameterDto;
	private ListModelList<ParameterDTO> listModelParameterDto;
	private int totalSize;
	private String parameterName;
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
		listModelParameterDto = new ListModelList<ParameterDTO>();
		parameterListbox.setModel(listModelParameterDto);
	}
	
	@Listen("onDownloadUserManual = #winParameterInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen ("onClick = button#btnFind")
	public void find() {
		parameterName = txtName.getValue();
		if(parameterName.equals("%%")) {
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
			generateDataAndPaging(null);
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/ssoa/parameter_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDetail= #winParameterInquiry")
	public void onDetail(ForwardEvent event){
		ParameterDTO parameterDto = (ParameterDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parameterDto", parameterDto);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("edit", true);
		Executions.createComponents("~./hcms/ssoa/parameter_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDelete= #winParameterInquiry")
	public void onDelete(ForwardEvent event){
		final ParameterDTO parameterDto = (ParameterDTO) event.getData();
		Messagebox.show("Are you sure want to delete this data?", "Confirmation", Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = -8756250972566999901L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						parameterService.delete(parameterDto);
						search();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
	}

	@Listen("onPaging = #winParameterInquiry")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging(evt);
	}

	private void generateDataAndPaging(ForwardEvent evt) {

		Parameter parameter = new Parameter();
		parameter.setParameterCode(txtCode.getValue());
		parameter.setParameterName(txtName.getValue());
		listParameterDto = parameterService.getParameterDtoByExample(parameter, Integer.MAX_VALUE, 0, null);
//		listParameterDto = parameterService.getParameterByExample(parameter, 0, GlobalVariable.getMaxRowPerPage());
		listModelParameterDto.clear();
		listModelParameterDto.addAll(listParameterDto);
	}
}
