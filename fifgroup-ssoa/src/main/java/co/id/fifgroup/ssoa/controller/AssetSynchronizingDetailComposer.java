package co.id.fifgroup.ssoa.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDTO;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDetailDTO;
import co.id.fifgroup.ssoa.service.AssetSynchronizingService;
import co.id.fifgroup.ssoa.service.SystemPropertyService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetSynchronizingDetailComposer extends SelectorComposer<Component> {
    //omit codes to get components
	//ClientService clientServices = new ClientService();
	
	private static Logger log = LoggerFactory.getLogger(AssetSynchronizingDetailComposer.class);
	
	private static final long serialVersionUID = -1185799743876477737L;

	@WireVariable(rewireOnActivate = true)
	private transient AssetSynchronizingService assetSynchronizingService;
	
	@Wire
    private Datebox dtSearchProcessDateFrom;
	@Wire
    private Datebox dtSearchProcessDateTo;
	
	@Wire
    private Datebox dtProcessDate;
	@Wire
    private Textbox txtDescription;
	@Wire
    private Textbox txtSynchconizedType;
	
	@Wire
    private Listbox lbxAssetSynchronizings;
	@Wire
	private Listbox lbxFormatDownload;
	
	@WireVariable(rewireOnActivate = true)
	private transient SystemPropertyService systemPropertyServiceImpl;
	
	@Wire
    private Groupbox gbAssetSynchronizing;
	@Wire
    private Groupbox gbAssetSynchronizingDetail;
    @Wire
    private Groupbox gbAssetDataReceive;
    @Wire
    private Groupbox gbAssetDataSend;
    
    @Wire
    private Button searchButton;
    @Wire
    private Button backButton;
    @Wire
	private Listbox assetSynchronizingDetailListbox;
    @WireVariable("arg")
	private Map<String, Object> arg;
    @Wire
    private String mode;
    
   // private ListModelList<AssetSynchronizingDTO> listModelAssetSynchronizingDto;
    
    private FunctionPermission functionPermission;
    
    private AssetSynchronizingDTO assetSynchronizingDTO;
    private List<AssetSynchronizingDetailDTO> assetSynchronizingDetailDTOList;
    
    private ListModelList<AssetSynchronizingDetailDTO> listModelAssetSynchronizingDetailDto;
   
    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
    	super.doAfterCompose(comp);
    	
    	init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
		loadAssetSynchronzingDetail();
		
  	//panelQuotations.setVisible(false);
    }
    
    private void loadAssetSynchronzingDetail() {
		//dtbDateFrom.setFormat(FormatDateUI.getDateFormat());
		//dtbDateTo.setFormat(FormatDateUI.getDateFormat());
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    	assetSynchronizingDTO = (AssetSynchronizingDTO)arg.get("assetSynchronizingDTO");
		if(assetSynchronizingDTO != null) {
			
			assetSynchronizingDTO = assetSynchronizingService.getAssetSynchronizingDtoById(assetSynchronizingDTO.getId());
			
			assetSynchronizingDetailDTOList = assetSynchronizingService.getAssetSynchronizingDetailDtoByAssetSynchronizingId(assetSynchronizingDTO.getId());
			
			dtProcessDate.setValue(assetSynchronizingDTO.getProcessDate());
			dtProcessDate.setDisabled(true);
			txtDescription.setValue("Asset Synchronizing "+ sdf.format(assetSynchronizingDTO.getProcessDate()));
			txtDescription.setDisabled(true);
			txtSynchconizedType.setValue(assetSynchronizingDTO.getSyncTypeName());
			txtSynchconizedType.setDisabled(true);
			listModelAssetSynchronizingDetailDto = new ListModelList<AssetSynchronizingDetailDTO>();
	    	
			listModelAssetSynchronizingDetailDto.clear();
			listModelAssetSynchronizingDetailDto.addAll(assetSynchronizingDetailDTOList);
			
			assetSynchronizingDetailListbox.setModel(listModelAssetSynchronizingDetailDto);
		}
	}
    
    private void initFunctionSecurity(){
		if(!functionPermission.isCreateable()){}
			//btnNew.setDisabled(true);
	}
    
    private void init() {
    	
	}
    
   
    /* backButton AssetRetirement */
    @Listen ("onClick= #backButton")
    public void backButton(){
    	Messagebox.show("Are you sure want to back ?", "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {	
				int status = (int) event.getData();
				if(status == 16) {
			    	Map<String, Object> params = new HashMap<String, Object>();
					params.put("assetSynchronizingDTO", assetSynchronizingDTO);
					params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
			    	Executions.createComponents("~./hcms/ssoa/asset_synchronizing.zul", getSelf().getParent(), params);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
    }
    
}

	

