package co.id.fifgroup.ssoa.ui;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.tabularentry.DateboxListcell;
import co.id.fifgroup.core.ui.tabularentry.IntboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.ParameterDetail;
import co.id.fifgroup.ssoa.domain.SOPeriod;
import co.id.fifgroup.ssoa.domain.SOPeriodDetail;
import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.dto.ParameterDetailDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDetailDTO;
import co.id.fifgroup.ssoa.service.ParameterService;
import co.id.fifgroup.ssoa.service.SOPeriodService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Window;

@VariableResolver(DelegatingVariableResolver.class)
public class SOPeriodPopupBranchComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(SOPeriodPopupBranchComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient SOPeriodService soPeriodService;
	@WireVariable(rewireOnActivate = true)
	private transient ParameterService parameterService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	private SOPeriodPopupBranchComposer composer = this;
	@Wire
	private Window winStatusPopup;
    @Wire
    private Listbox lbxBranch;
    @Wire
    private Listbox statusListbox;
	@Wire
	private Textbox txtDesc;
	@Wire
	private Textbox txtBranchCode;
	@Wire
	private Textbox txtBranchName;
	@Wire
	private Textbox status;
	@Wire
	private Datebox dbStart;
	@Wire
	private Datebox dbEnd;
	@Wire
	private Label lblErrorSOPeriod;
	
	@Wire
	private Button btnSave;	
	@Wire
	private Panel panelBranch;
	@Wire
	private Panel panelDetailSOPeriod;	
	

	private String branchCode;
	private String branchName;
	private Date startDate;
	private Date endDate;
	private SOPeriodDTO soPeriodDto;
	private SOPeriodDTO selected;
	private SOPeriodDetail selectedStatus;
	private SOPeriod soPeriod;
	
	private List<SOPeriodDetailDTO> listSOPeriodDto;
	private ListModelList<SOPeriodDetailDTO> listModelSOPeriodDetailDto;
	
	private List<SOPeriodDetailDTO> listSOPeriodDetailDto;
	private FunctionPermission functionPermission;
	private boolean isDetail = false;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		loadSOPeriods();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	
	}
	
	private void init() {
	
    
	}
	
	private void initFunctionSecurity(){
		if (functionPermission.isCreateable()) {
			disableComponent(false);
		}else if (functionPermission.isEditable()) {
			disableComponent(false);
		}else{
			disableComponent(true);
		}
		
		if(isDetail){
			if(functionPermission.isEditable())
				disableComponent(false);
			else
				disableComponent(true);
		}
	}

	private void disableComponent(boolean disabled){}

	private void loadSOPeriods() {
		
		selectedStatus = (SOPeriodDetail) arg.get("status");
		selected = (SOPeriodDTO) arg.get("soPeriodDto");
		if(selected != null) {
			
			List<SOPeriodDetail> listBranch = soPeriodService.getDetailBranchBySOPeriodId(selected.getSoPeriodId(), selectedStatus.getStatusCode());
			lbxBranch.setModel(new ListModelList<SOPeriodDetail>(listBranch));
			
		}
	}
	
	
	private ListModelList<SOPeriodDetailDTO> getSOPeriodDetailModel() {
		if(listSOPeriodDetailDto == null || listSOPeriodDetailDto.size() < 1) {
			listSOPeriodDetailDto = new ArrayList<SOPeriodDetailDTO>();
			listSOPeriodDetailDto.add(new SOPeriodDetailDTO());
		}
		ListModelList<SOPeriodDetailDTO> model = new ListModelList<SOPeriodDetailDTO>(listSOPeriodDetailDto);
		model.setMultiple(true);
		return model;
	}	
	
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtDesc);
		ErrorMessageUtil.clearErrorMessage(dbStart);
		ErrorMessageUtil.clearErrorMessage(dbEnd);
		//ErrorMessageUtil.clearErrorMessage(selectedBranchListbox.getPreviousSibling());
	}
	
	

	
	@Listen ("onClick = button#backButton")
	public void back() {
		Messagebox.show(("Are You Sure Want To Back"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {	
				int status = (int) event.getData();
				if(status == 16) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/ssoa/so_period.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
	
 
	@Listen ("onClick = button#btnFindDetail")
	public void find() {
		
		branchCode = txtBranchCode.getValue();

		if(branchCode.equals("%%")) {
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
		}
		else {
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
	
	private void generateDataAndPaging(ForwardEvent evt) {
		listModelSOPeriodDetailDto = new ListModelList<SOPeriodDetailDTO>();
		
		SOPeriodDetail soPeriodDetail = new SOPeriodDetail();
		if (statusListbox.getSelectedIndex() > 0) {
			soPeriodDetail.setStatusCode(statusListbox.getSelectedItem().getValue().toString());
		}
		soPeriodDetail.setBranchId(Long.parseLong(txtBranchCode.getValue()));
		soPeriodDetail.setSoPeriodId(selected.getSoPeriodId());
		//soPeriodDetail.setBranchName(txtBranchName.getValue());
		listSOPeriodDetailDto = soPeriodService.getSOPeriodDtoDetailByExample(soPeriodDetail, Integer.MAX_VALUE, 0, null);
		//listSOPeriodDto = soPeriodService.getSOPeriodByExample(soPeriod, 0, GlobalVariable.getMaxRowPerPage());
		listModelSOPeriodDetailDto.clear();
		//lbxSOPeriodDetail.setModel(listModelSOPeriodDetailDto);
		listModelSOPeriodDetailDto.addAll(listSOPeriodDetailDto);
		//lbxSOPeriodDetail.setModel(listModelSOPeriodDetailDto);
	}
	@Listen("onClick = #btnClose")
	public void modalStatus(Event e) {
		winStatusPopup.detach();
	}
   
}