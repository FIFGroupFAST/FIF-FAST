package co.id.fifgroup.ssoa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.constants.AssetRegistrationApprovalStatus;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.domain.AssetRegistration;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDTO;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.service.AssetRegistrationService;
import co.id.fifgroup.ssoa.service.SSOACommonService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import java.util.Iterator;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetRegistrationAddComposer extends  SelectorComposer<Window> {

	private static final long serialVersionUID = 4373064548488803096L;
	
	private static Logger log = LoggerFactory.getLogger(AssetRegistrationAddComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient AssetRegistrationService assetRegistrationService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;

	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient SSOACommonService ssoaCommonService;
	@Wire
	private TabularEntry<AssetRegistrationDetailDTO> assetRegistartionDetail;
	@Wire
	private TabularEntry<AssetRegistrationDetailDTO> lbxAssetAdd;
	@Wire
	private Label lblErrBASTInfo;
	@Wire
	private Textbox txtRegistrationNo;
	@Wire
	private Datebox dbRegistrationDate;
	@Wire
	private Textbox txtRemarks;
	@Wire
	private SSOACommonPopupBandbox bnbBranch;
	@Wire
	private SSOACommonPopupBandbox bnbBranchDestination;
	
	@Wire
	private Label lblErrorAssetRegistration;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnSubmit;
	@Wire
	private Button btnCancel;
	@Wire
	private Button btnAddAsset;
	@Wire
	private Button btnBack;
	@Wire
	private Button btnDelete;
	@Wire
	private Listbox lbxDetailAsset;
	
	@Wire
	private Listbox lbxAsset;
	@Wire
	private Groupbox gbAddListAsset;
	@Wire
	private Label errMsgDtl;
	@Wire
	private Groupbox gbListAssetDetail;
	
	private List<AssetRegistrationDetailDTO> assetsList;
	private ListModelList<AssetRegistrationDetailDTO> listModelAsset;
	private List<AssetRegistrationDetailDTO> listAddAsset;
	private List<AssetRegistrationDetailDTO> listDetailAssets;
	private AssetRegistrationDTO assetRegistrationDto;
	private AssetRegistrationDTO selected;
	private AssetRegistration assetRegistration;
	private FunctionPermission functionPermission;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
	private boolean isEdit = false;
	private OrganizationDTO organizationDTO;
	@WireVariable(rewireOnActivate = true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	
	private Map<Long, AssetRegistrationDetailDTO> assets = new HashMap<Long, AssetRegistrationDetailDTO>();
	
	private static String tmpFile ="E:\\Temp\\";
	private static final String imageCode ="IMAGE_PATH";
	private static String maxSize ="1000000";
	
	String action;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
			super.doAfterCompose(comp);
			setTmpFilePath();
			
			if(arg.containsKey("edit")){
				isEdit = true;
			}
			selected = (AssetRegistrationDTO) arg.get("assetRegistrationDTO");
			if(selected != null) {
				loadAssetRegistration();
			}
			else{
				init();
			}
			functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
			if(arg.get("action")!=null){
				String action = (String) arg.get("action");
			    if(action.equals("addNew")){
				    	Branch branch = (Branch)assetRegistrationService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
					    KeyValue value = new KeyValue();
					    value.setKey(branch.getBranchId());
					    value.setValue(branch.getBranchName());
					    value.setDescription(branch.getBranchCode());
					    gbListAssetDetail.setVisible(false);
					    btnBack.setVisible(false);
					    
				    }else{
				    Branch branch = (Branch)assetRegistrationService.getBranchById(assetRegistrationDto.getBranchId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				    KeyValue value = new KeyValue();
				    value.setKey(branch.getBranchId());
				    value.setValue(branch.getBranchName());
				    value.setDescription(branch.getBranchCode());
				    }
				
					if(action.equals("detail")){
						selected = (AssetRegistrationDTO) arg.get("assetRegistrationDTO");
						btnSubmit.setVisible(false);
						
						btnCancel.setVisible(false);
						gbAddListAsset.setVisible(false);
						gbListAssetDetail.setVisible(true);
						btnAddAsset.setVisible(false);
						btnDelete.setVisible(false);
						
						if (selected.getStatusCode().equals(AssetRegistrationApprovalStatus.COMPLETED.getCode()))
						{
							btnAddAsset.setVisible(false);
							btnDelete.setVisible(false);
						}
						else
						{
						}
						
						}
			}
		
			initFunctionSecurity();
			
			
			lbxAssetAdd.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
				private static final long serialVersionUID = -91355005227901153L;

			
				public void onEvent(Event event) throws Exception {
					
					if(event.getData()!=null){
						if(assetsList == null){ 
							assetsList = new ArrayList<AssetRegistrationDetailDTO>(); 
							assetsList.addAll((List<AssetRegistrationDetailDTO>)event.getData());
						}else{
								for (int i = 0; i < ((List<AssetRegistrationDetailDTO>) event.getData()).size(); i++) {
									AssetRegistrationDetailDTO asset = (AssetRegistrationDetailDTO) ((List<AssetRegistrationDetailDTO>) event.getData()).get(i);
									
									boolean flag = false;
									for(int x=0;x<assetsList.size();x++){
										AssetRegistrationDetailDTO assetTemp = assetsList.get(x);
										System.out.println(asset.getStockOpnameUnregAssetId()+"=="+assetTemp.getStockOpnameUnregAssetId());
										if(asset.getStockOpnameUnregAssetId().equals(assetTemp.getStockOpnameUnregAssetId())){
											flag = true;
										}
									}
									
									if(!flag){
										assetsList.add(asset);
									}
								}
						}
						
						ListModelList<AssetRegistrationDetailDTO> list = new ListModelList<AssetRegistrationDetailDTO>(assetsList);
						lbxAssetAdd.setModel(list);
					}
				}
				});
			
	}
	
	private void loadBranch(SSOACommonPopupBandbox bandbox) {
		bandbox.setSearchDelegate(new DelegateSearch());
	}

	class DelegateSearch implements SerializableSearchDelegateDoubleCriteria<Branch> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<Branch> findBySearchCriteria(String searchCriteriaCode,String searchCriteriaName, int limit, int offset) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			
			List<Branch> listBranch = new ArrayList<Branch>();
			listBranch = assetRegistrationService.getBranchByExample(example, limit, offset);

			return listBranch;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			return assetRegistrationService.countBranchByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Branch data) {
			keyValue.setKey(data.getBranchId());
			keyValue.setValue(data.getBranchName());
			keyValue.setDescription(data.getBranchCode());
		}
	}

	
	private void setTmpFilePath(){
		List<GlobalSettingDTO> dataImgPath = assetRegistrationService.getGlobalsettingByCode(SSOAConstants.imagePathTmp);
		if(dataImgPath!=null){
			tmpFile = dataImgPath.get(0).getGlobalSetting().getValue().toString();
		}
		List<GlobalSettingDTO> dataMaxSizeUpload = assetRegistrationService.getGlobalsettingByCode(SSOAConstants.maxSizeUploadFileTransfer);
		if(dataMaxSizeUpload!=null){
			maxSize = dataMaxSizeUpload.get(0).getGlobalSetting().getValue().toString();
		}
	}
	
	private void init() {
		
	}
	
	
	@Listen("onClick=button#btnDelete")
	public void deleteRow() {
		ListModel listData = (ListModel)lbxAssetAdd.getModel();
		ListModelList data =(ListModelList)listData;
		if(data!=null && data.getSelection().size()>0){
			
		final Set<AssetRegistrationDetailDTO> selection = data.getSelection();
		
		Messagebox.show("Are you sure want to delete this data ?", "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							int i = 0;
							for (Iterator<AssetRegistrationDetailDTO> iterator = selection.iterator(); selection.iterator().hasNext();) {
								AssetRegistrationDetailDTO asset = iterator.next();
								assetsList.remove(asset);
								assets.remove(asset.getAssetId());
								i++;
								if (i == selection.size()) {
									break;
								}
							}
							ListModelList<AssetRegistrationDetailDTO> list = new ListModelList<AssetRegistrationDetailDTO>(assetsList);
							lbxAssetAdd.setModel(list);
						}
					}
				});
			}
	}
	
	
	private void loadAssetRegistration() {
		selected = (AssetRegistrationDTO) arg.get("assetRegistrationDTO");
		//String action = (String) arg.get("action");
		if(selected != null) {
			assetRegistration = assetRegistrationService.getAssetRegistrationById(selected.getRegistrationId());
			assetRegistrationDto = modelMapper.map(assetRegistration, AssetRegistrationDTO.class);
			listAddAsset = assetRegistrationDto.getAssetRegistrationAdd();
		
			
			txtRegistrationNo.setValue(selected.getRegistrationNo());
			txtRegistrationNo.setReadonly(true);
			
			txtRemarks.setValue(selected.getRemarks());
			txtRemarks.setReadonly(true);
			
			dbRegistrationDate.setValue(selected.getRegistrationDate());
			dbRegistrationDate.setDisabled(true);
			
			listModelAsset = new ListModelList<AssetRegistrationDetailDTO>();
			AssetRegistrationDetailDTO assets = new AssetRegistrationDetailDTO();
			assets.setAssetId(selected.getRegistrationId());
			listDetailAssets = assetRegistrationService.getDetailAssetsByPrimaryKey(assets.getAssetId());
			listModelAsset.clear();
			listModelAsset.addAll(listDetailAssets);
			lbxDetailAsset.setModel(listModelAsset);
			
		}
	}
	
	private void initFunctionSecurity(){
		
	}
	
	private Boolean validate(){
		Boolean flag = false;
		
		if(dbRegistrationDate.getValue()== null) {
			ErrorMessageUtil.setErrorMessage(dbRegistrationDate, "Request Date must be filled");
			flag = true;
		}
		
		if(lbxAssetAdd == null || lbxAssetAdd.getModel() == null || lbxAssetAdd.getModel().getSize() == 0){
			ErrorMessageUtil.setErrorMessage(errMsgDtl, "Please fill asset to be registered");
			flag = true;
		}
		
		return flag;
	}
	
	@Listen("onClick = button#btnSubmit")
	public void onSubmit() {
		clearErrorMessage();
		if(!validate()){
			if(assetRegistrationDto == null){
				assetRegistrationDto = new AssetRegistrationDTO();
				assetRegistration = new AssetRegistration();
			}
			assetRegistrationDto.setRegistrationNo(txtRegistrationNo.getValue());
			assetRegistrationDto.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			assetRegistrationDto.setRegistrationDate(dbRegistrationDate.getValue());
			assetRegistrationDto.setBranchId(securityServiceImpl.getSecurityProfile().getBranchId());
			assetRegistrationDto.setRemarks(txtRemarks.getValue());
			assetRegistrationDto.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
			assetRegistrationDto.setLastUpdateDate(new Date());
			
			if(selected == null) {
				assetRegistrationDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				assetRegistrationDto.setCreationDate(new Date());
			} else {
				assetRegistrationDto.setCreatedBy(selected.getCreatedBy());
				assetRegistrationDto.setCreationDate(selected.getCreationDate());
			}
	
			if(securityServiceImpl.getSecurityProfile().getBranchId().intValue() == -1){
				assetRegistrationDto.setRaBranchType("RA_BRANCH_TYPE_HO");
			}else{
				assetRegistrationDto.setRaBranchType("RA_BRANCH_TYPE_NON_HO");
			}
			
			
			Messagebox.show("Are you sure to save this data ?", "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							try {
								UUID personUUID = null;
								if(securityServiceImpl.getSecurityProfile().getBranchId().intValue() == -1 && assetRegistrationDto.getBranchId().intValue()!= -1){
									//personUUID = UUID.fromString(stockOpnameService.getOnePersonByBranchId(selected.getBranchId()));
									StockOpnameDTO sod = ssoaCommonService.getOnePersonByBranchId(assetRegistrationDto.getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
									personUUID = sod.getPersonUUID();
									//personUUID = rd.getPersonUUID();
								
								}else{
									personUUID = securityServiceImpl.getSecurityProfile().getPersonUUID();
								}
								assetRegistrationService.save(assetRegistrationDto,personUUID, lbxAssetAdd.getValue());
								
								Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show("Data has been save", "Information", Messagebox.YES,
										Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/ssoa/asset_registration.zul", getSelf().getParent(),
										param);
								getSelf().detach();
								
							} catch (FifException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Messagebox.show(e.getMessage() , "Information", Messagebox.YES,
										Messagebox.ERROR);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Messagebox.show(e.getMessage() , "Information", Messagebox.YES,
										Messagebox.ERROR);
							}
						}
					}  
			}); 
		}
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(dbRegistrationDate);
		ErrorMessageUtil.clearErrorMessage(errMsgDtl);
	}
	
	
	@Listen ("onClick = button#btnCancel")
	public void cancel() {
		Messagebox.show("Are you sure want to cancel?", "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {	
				int status = (int) event.getData();
				if(status == 16) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/ssoa/asset_registration.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
	@Listen ("onClick = button#btnAddAsset")
	public void onshowInput() {
		Map<String, Object> param = new HashMap<String, Object>();
		AssetRegistrationDTO assetRegistrationDto = new AssetRegistrationDTO();

		String branchId= securityServiceImpl.getSecurityProfile().getBranchId().toString();
		param.put("source", lbxAssetAdd );
		param.put("branchId", assetRegistrationDto);
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_registration_popup.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	
	@Listen ("onClick = button#btnBack")
	public void back() {
		Messagebox.show("Are you sure want to back?", "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {	
				int status = (int) event.getData();
				if(status == 16) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/ssoa/asset_registration.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
	
	
	

	
	private void buildAssetAdd() {
		lbxAssetAdd.setModel(getAssetModel());
		lbxAssetAdd.setItemRenderer(getListitemRendererAsset());
		lbxAssetAdd.setValidationRule(getTabularValidationRuleAsset());
		lbxAssetAdd.renderAll();
	}
	
	private ListModelList<AssetRegistrationDetailDTO> getAssetModel() {
		if(listAddAsset == null || listAddAsset.size() < 1) {
			listAddAsset = new ArrayList<AssetRegistrationDetailDTO>();
			listAddAsset.add(new AssetRegistrationDetailDTO());
		}
		ListModelList<AssetRegistrationDetailDTO> model = new ListModelList<AssetRegistrationDetailDTO>(listAddAsset);
		model.setMultiple(true);
		model.setSelection(listAddAsset);
		return model;
	}
	
	private SerializableListItemRenderer<AssetRegistrationDetailDTO> getListitemRendererAsset() {
		return new SerializableListItemRenderer<AssetRegistrationDetailDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final AssetRegistrationDetailDTO data, int index)
					throws Exception {
				
				AssetRegistrationDetailDTO lstAsset = data;
				
			
			}
		};
	}
	
	
	private TabularValidationRule<AssetRegistrationDetailDTO> getTabularValidationRuleAsset() {
		return new TabularValidationRule<AssetRegistrationDetailDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(AssetRegistrationDetailDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	


}
