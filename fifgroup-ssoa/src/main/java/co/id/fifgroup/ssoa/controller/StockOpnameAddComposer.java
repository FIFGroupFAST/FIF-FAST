package co.id.fifgroup.ssoa.controller;


import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.exporter.excel.ExcelExporter;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.constants.SOResult;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.StockOpname;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.StockOpnameDetailExample;
import co.id.fifgroup.ssoa.domain.StockOpnameUnregAssets;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameImgDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.service.StockOpnameService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;


@VariableResolver(DelegatingVariableResolver.class)
public class StockOpnameAddComposer extends  SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(StockOpnameAddComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	
	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Listheader hdrAssetNumber;
	@Wire
	private Listheader hdrAssetType;
	@Wire
	private Listheader hdrBranchCode;
	@Wire
	private Listheader hdrBranchName;
	@Wire
	private Listheader hdrLocationCode;
	@Wire
	private Listheader hdrLocationName;
	@Wire
	private Listheader hdrCategoryName;
	@Wire
	private Listheader hdrDatePlaceInService;
	@Wire
	private Listheader hdrDescription;
	@Wire
	private Listheader hdrSerialNumber;
	@Wire
	private Listheader hdrStockOpnameResult;
	@Wire
	private Listheader hdrStockOpnameSugest;
	@Wire
	private Listheader hdrStockOpnameHOSugest;
	@Wire
	private Listheader hdrStockOpnameBy;
	@Wire
	private Listheader hdrStockOpnameDate;
	@Wire
	private Listheader hdrLastUpdateBy;
	@Wire
	private Listheader hdrLastUpdateDate;
	@Wire
	private Listheader hdrNetBookValue;
	
	@Wire
	private TabularEntry<StockOpnameDetailDTO> lstStockOpnameDetail;
	@Wire
	private Listbox lstStockOpnameDetailForDownload;
	@Wire
	private TabularEntry<StockOpnameUnregAssetsDTO> lstStockOpnameUnregAsset;
	@Wire
	private Listbox lstStockOpnameUnregAssetForDownload;
	@Wire
	private Textbox txtPeriodName;
	@Wire
	private Datebox dtPeriodStartDate;
	@Wire
	private Datebox dtPeriodEndDate;
	/*@Wire
	private CommonPopupBandbox bnbBranch;*/
	@Wire
	private SSOACommonPopupBandbox bnbBranch;
	@Wire
	private Label lblErrorStockOpname;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnAddRow;
	@Wire
	private Button btnDelete;
	@Wire
	private Window winStockOpnameAdd;
	/*@Wire
	private Listbox lstStockOpnameDetail;*/
	/*@Wire
	private Listbox lstStockOpnameUnregAsset;*/
	@Wire
	private Groupbox gbResult;
	@Wire
	private Groupbox gbUnreg;
	@Wire
	private Textbox txtAssetNoSearch;
	@Wire
	private Textbox txtDescriptionSearch;
	
	@Wire
	private Listbox cbResultSearch;
	@Wire
	private Listbox cbRecommendationSearch;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Caption cptHeader;
	@Wire
	private Combobox cmbDownloadAs;
	@Wire
	private Combobox cmbDownloadAs2;
	
	
	private StockOpnameDTO stockOpnameDto;
	private StockOpnameDTO selected;
	private StockOpname stockOpname;
	private List<StockOpnameDetailDTO> lstStockOpnameDetailList;
	private List<StockOpnameDetailDTO> lstStockOpnameDetailListTemp;
	private List<StockOpnameUnregAssetsDTO> stockOpnameUnregAssetsList;
	private FunctionPermission functionPermission;
	private boolean isEdit = false;
	private OrganizationDTO organizationDTO;
	@WireVariable(rewireOnActivate = true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	private Map<Long, StockOpnameDetailDTO> editStockOpnameDtl = new HashMap<Long, StockOpnameDetailDTO>();
	private String orderBy = "ASSET_NUMBER ASC";
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		loadBranch(bnbBranch);
		setComboBoxList();
		buildStockOpnameDetail();
		sortAction();
		buildStockOpnameUnregAsset();
		if(arg.containsKey("edit")){
			isEdit = true;
		}
		
		
		loadStockOpnames();
		
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		
		//bnbBranch.setDisabled(true);
		
		if(arg.get("action")!=null){
		String action = (String) arg.get("action");
		    if(action.equals("addNew")){
			//KeyValue value = (KeyValue) arg.get("Branch");
			//bnbBranch.setRawValue(value);
		    	txtPeriodName.setDisabled(false);
		    	dtPeriodStartDate.setDisabled(false);
		    	dtPeriodEndDate.setDisabled(false);
		    	bnbBranch.setDisabled(true);
		    	
		    	Branch branch = (Branch)stockOpnameService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			    KeyValue value = new KeyValue();
			    value.setKey(branch.getBranchId());
			    value.setValue(branch.getBranchName());
			    value.setDescription(branch.getBranchCode());
			    bnbBranch.setRawValue(value);
			    if(securityServiceImpl.getSecurityProfile().getBranchId()!=null && securityServiceImpl.getSecurityProfile().getBranchId().longValue() == -1){
			    	bnbBranch.setDisabled(true);
			    }else{
			    	bnbBranch.setDisabled(true);
			    }
		    }else{
		    
				txtPeriodName.setDisabled(true);
				dtPeriodStartDate.setDisabled(true);
				dtPeriodEndDate.setDisabled(true);
				bnbBranch.setDisabled(true);
				
		    Branch branch = (Branch)stockOpnameService.getBranchById(stockOpnameDto.getBranchId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		    KeyValue value = new KeyValue();
		    value.setKey(branch.getBranchId());
		    value.setValue(branch.getBranchName());
		    value.setDescription(branch.getBranchCode());
		    bnbBranch.setRawValue(value);
		    }
		     if(action.equals("detail")){
		    	cptHeader.setLabel("Stock Opname - Add");
				btnSave.setVisible(false);
				
				Listhead lHead = (Listhead) lstStockOpnameDetail.getListhead();
				for(int i=0;i<lHead.getChildren().size();i++){
					Listheader lh =(Listheader)lHead.getChildren().get(i);
					if(i==6){
						lh.setVisible(false);
					}
				}
				
				lHead = (Listhead) lstStockOpnameUnregAsset.getListhead();
				for(int i=0;i<lHead.getChildren().size();i++){
					Listheader lh =(Listheader)lHead.getChildren().get(i);
					if(i==6){
						lh.setVisible(false);
					}
				}
				
				
				
			}
			else if(action.equals("result")){
				
				cptHeader.setLabel("Stock Opname - Result");
				btnSave.setVisible(true);
				
				/*Listhead lHead = (Listhead) lstStockOpnameDetail.getListhead();
				for(int i=0;i<lHead.getChildren().size();i++){
					Listheader lh =(Listheader)lHead.getChildren().get(i);
					if(i==6){
						lh.setVisible(false);
					}
				}
				
				Listhead lHeadUnreg = (Listhead) lstStockOpnameUnregAsset.getListhead();
				for(int i=0;i<lHeadUnreg.getChildren().size();i++){
					Listheader lh =(Listheader)lHeadUnreg.getChildren().get(i);
					if(i==6){
						lh.setVisible(false);
					}
				}*/
			}
			else if(action.equals("edit")){
				
				cptHeader.setLabel("Stock Opname - Edit Recommendation");
				btnSave.setVisible(true);
				
				/*Listhead lHead = (Listhead) lstStockOpnameDetail.getListhead();
				for(int i=0;i<lHead.getChildren().size();i++){
					Listheader lh =(Listheader)lHead.getChildren().get(i);
					if(i==6){
						lh.setVisible(true);
					}
				}
				
				Listhead lHeadUnreg = (Listhead) lstStockOpnameUnregAsset.getListhead();
				for(int i=0;i<lHeadUnreg.getChildren().size();i++){
					Listheader lh =(Listheader)lHeadUnreg.getChildren().get(i);
					if(i==6){
						lh.setVisible(true);
					}
				}*/
			}
		}
		initFunctionSecurity();
		
		lstStockOpnameDetail.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				
				if(event.getData()!=null){
					
					ListModel<StockOpnameDetailDTO> list = (ListModel<StockOpnameDetailDTO>)event.getData();
					StockOpnameDetailDTO lstStockOpnameDetailDTO = (StockOpnameDetailDTO)list.getElementAt(0);
					editStockOpnameDtl.put(lstStockOpnameDetailDTO.getId(), lstStockOpnameDetailDTO);
					
					int row =lstStockOpnameDetailList.lastIndexOf(lstStockOpnameDetailDTO);
					
					lstStockOpnameDetailList.get(row).setOpnameResultId(lstStockOpnameDetailDTO.getOpnameResultId());
					lstStockOpnameDetailList.get(row).setOpnameResultCode(lstStockOpnameDetailDTO.getOpnameResultCode());
					lstStockOpnameDetailList.get(row).setOpnameResult(lstStockOpnameDetailDTO.getOpnameResult());
					lstStockOpnameDetailList.get(row).setOpnameSugestId(lstStockOpnameDetailDTO.getOpnameSugestId());
					lstStockOpnameDetailList.get(row).setOpnameSugestCode(lstStockOpnameDetailDTO.getOpnameSugestCode());
					lstStockOpnameDetailList.get(row).setOpnameSugest(lstStockOpnameDetailDTO.getOpnameSugest());
					lstStockOpnameDetailList.get(row).setStockOpnameImg(lstStockOpnameDetailDTO.getStockOpnameImg());
					
					ListModelList<StockOpnameDetail> model = new ListModelList<StockOpnameDetail>(lstStockOpnameDetailList);
					model.setMultiple(true);
					lstStockOpnameDetail.setModel(model);
					//searchStockOpnameDetail();
				}
			}
		});
		
		lstStockOpnameUnregAsset.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				
				if(event.getData()!=null){
					
					ListModel<StockOpnameUnregAssetsDTO> list = (ListModel<StockOpnameUnregAssetsDTO>)event.getData();
					StockOpnameUnregAssetsDTO stockOpnameUnregAssets = (StockOpnameUnregAssetsDTO)list.getElementAt(0);
					int row = 0;
					if(stockOpnameUnregAssetsList == null || stockOpnameUnregAssetsList.size()== 0){
						stockOpnameUnregAssetsList.add(stockOpnameUnregAssets);
					}else{
						if(stockOpnameUnregAssets.getAction()!=null && stockOpnameUnregAssets.getAction().equals("EDIT")){
							row= stockOpnameUnregAssetsList.lastIndexOf(stockOpnameUnregAssets);
						}else{
						stockOpnameUnregAssetsList.add(stockOpnameUnregAssets);
						}
					}
					if(row>0){
					stockOpnameUnregAssetsList.get(row).setOpnameResultId(stockOpnameUnregAssets.getOpnameResultId());
					stockOpnameUnregAssetsList.get(row).setOpnameResultCode(stockOpnameUnregAssets.getOpnameResultCode());
					stockOpnameUnregAssetsList.get(row).setOpnameSugestId(stockOpnameUnregAssets.getOpnameSugestId());
					stockOpnameUnregAssetsList.get(row).setOpnameSugestCode(stockOpnameUnregAssets.getOpnameSugestCode());
					stockOpnameUnregAssetsList.get(row).setStockOpnameUnregAssetImg(stockOpnameUnregAssets.getStockOpnameUnregAssetImg());
					}
					
					ListModelList<StockOpnameUnregAssetsDTO> model = new ListModelList<StockOpnameUnregAssetsDTO>(stockOpnameUnregAssetsList);
					model.setMultiple(true);
					lstStockOpnameUnregAsset.setModel(model);
					
				}
			}
		});
	}
	
	private void sortAction(){
		hdrAssetNumber.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "SA.ASSET_NUMBER"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrAssetType.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "SA.ASSET_TYPE_CODE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrCategoryName.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(select segment1 from SOC_EBS_CATEGORIES where category_id =SA.CATEGORY_ID)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrBranchCode.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(case when SA.BRANCH_ID = -1 then 'HEADOFFICE' else (SELECT ORGANIZATION_CODE from WOS_ORGANIZATIONS where ORGANIZATION_ID = SA.BRANCH_ID and rownum = 1) end )"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrBranchName.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(case when SA.BRANCH_ID = -1 then 'Head Office' else (SELECT ORGANIZATION_NAME from WOS_ORGANIZATIONS where ORGANIZATION_ID = SA.BRANCH_ID and rownum = 1) end )"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLocationCode.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(SELECT LOCATION_CODE from WOS_LOCATIONS where LOCATION_ID = SA.LOCATION_ID and rownum =1)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLocationName.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(SELECT LOCATION_NAME from WOS_LOCATIONS where LOCATION_ID = SA.LOCATION_ID and rownum =1)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrDatePlaceInService.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "SA.DATE_PLACED_IN_SERVICE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrDescription.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "SA.DESCRIPTION"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrSerialNumber.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "SA.SERIAL_NUMBER"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStockOpnameResult.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(select DISTINCT DESCRIPTION from BSE_LOOKUP_DEPENDENTS LD inner join SOC_STOCK_OPNAME_DTL SO on SO.OPNAME_RESULT_CODE= LD.DETAIL_CODE where  SO.ASSET_ID = SA.ASSET_ID)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStockOpnameSugest.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(select DISTINCT DESCRIPTION from BSE_LOOKUP_DEPENDENTS LD inner join SOC_STOCK_OPNAME_DTL SO on SO.OPNAME_SUGEST_CODE= LD.DETAIL_CODE where  SO.ASSET_ID = SA.ASSET_ID)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStockOpnameHOSugest.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(select DISTINCT DESCRIPTION from BSE_LOOKUP_DEPENDENTS LD inner join SOC_STOCK_OPNAME_DTL SO on SO.OPNAME_SUGEST_CODE= LD.DETAIL_CODE where  SO.ASSET_ID = SA.ASSET_ID)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStockOpnameBy.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "SU.USER_NAME"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStockOpnameDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "SOD.CREATION_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLastUpdateBy.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "U.USER_NAME"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLastUpdateDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "SOD.LAST_UPDATE_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrNetBookValue.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "SA.NET_BOOK_VALUE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
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
			listBranch = stockOpnameService.getBranchByExample(example, limit, offset);

			return listBranch;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			return stockOpnameService.countBranchByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Branch data) {
			keyValue.setKey(data.getBranchId());
			keyValue.setValue(data.getBranchName());
			keyValue.setDescription(data.getBranchCode());
		}
	}
	
	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue all = new KeyValue();
		all.setKey("0");
		all.setValue("ALL");
		all.setDescription("--Select--");
		data.add(all);
		// data.addAll(stockOpnameService.getSOResultList());
		List<KeyValue> dataFromDb = new ArrayList<KeyValue>();
		for (int i = 0; i < stockOpnameService.getSOResultList().size(); i++) {
			KeyValue keyVal = (KeyValue) stockOpnameService.getSOResultList().get(i);
			if (keyVal.getValue() != null && !keyVal.getValue().equals(SOResult.NOT_RECORDED.getCode())) {
				dataFromDb.add(keyVal);
			}
		}
		data.addAll(dataFromDb);
		
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);
		cbResultSearch.setModel(model);
		cbResultSearch.renderAll();
		cbResultSearch.setSelectedIndex(0);
		
		List<KeyValue> data2 = new ArrayList<KeyValue>();
		data2.add(all);
		data2.addAll(stockOpnameService.getSORecommendList());
		ListModelList<KeyValue> model2 = new ListModelList<KeyValue>(data2);
		cbRecommendationSearch.setModel(model2);
		cbRecommendationSearch.renderAll();
		cbRecommendationSearch.setSelectedIndex(0);
		
		
		
	}
	
	private void buildStockOpnameDetail() {
		
		lstStockOpnameDetail.setModel(getStockOpnameDetailModel());
		lstStockOpnameDetail.setItemRenderer(getListitemRenderer());
		lstStockOpnameDetail.setValidationRule(getTabularValidationRule());
		lstStockOpnameDetail.renderAll();
	}
	
	private void buildStockOpnameUnregAsset() {
		
		lstStockOpnameUnregAsset.setModel(getStockOpnameUnregAssetModel());
		lstStockOpnameUnregAsset.setItemRenderer(getListitemRendererUnregAsset());
		lstStockOpnameUnregAsset.setValidationRule(getTabularValidationRuleUnreg());
		lstStockOpnameUnregAsset.renderAll();
	}
	
	private TabularValidationRule<StockOpnameDetailDTO> getTabularValidationRule() {
		return new TabularValidationRule<StockOpnameDetailDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(StockOpnameDetailDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	private TabularValidationRule<StockOpnameUnregAssetsDTO> getTabularValidationRuleUnreg() {
		return new TabularValidationRule<StockOpnameUnregAssetsDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(StockOpnameUnregAssetsDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	
	private ListModelList<StockOpnameDetailDTO> getStockOpnameDetailModel() {
		if(lstStockOpnameDetailList == null || lstStockOpnameDetailList.size() < 1) {
			lstStockOpnameDetailList = new ArrayList<StockOpnameDetailDTO>();
			lstStockOpnameDetailList.add(new StockOpnameDetailDTO());
		}
		ListModelList<StockOpnameDetailDTO> model = new ListModelList<StockOpnameDetailDTO>(lstStockOpnameDetailList);
		//model.setMultiple(true);
		//model.setSelection(lstStockOpnameDetailList);
		return model;
	}
	
	private ListModelList<StockOpnameUnregAssetsDTO> getStockOpnameUnregAssetModel() {
		if(stockOpnameUnregAssetsList == null || stockOpnameUnregAssetsList.size() < 1) {
			stockOpnameUnregAssetsList = new ArrayList<StockOpnameUnregAssetsDTO>();
			stockOpnameUnregAssetsList.add(new StockOpnameUnregAssetsDTO());
		}
		ListModelList<StockOpnameUnregAssetsDTO> model = new ListModelList<StockOpnameUnregAssetsDTO>(stockOpnameUnregAssetsList);
		model.setMultiple(true);
		model.setSelection(stockOpnameUnregAssetsList);
		return model;
	}
	
	private SerializableListItemRenderer<StockOpnameDetailDTO> getListitemRenderer() {
		return new SerializableListItemRenderer<StockOpnameDetailDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final StockOpnameDetailDTO data, int index)
					throws Exception {
				
				
				
				StockOpnameDetail lstStockOpnameDetail = data;
				
				new Listcell().setParent(item);
				A result = new A();
				A image = new A();
				
				createLinkResult(item,data,result);
				Label assetNo = new Label();
				createAssetNo(item,data,assetNo);
				Label desc = new Label();
				createDescription(item,data,desc);
				Label results = new Label();
				createResult(item,data,results);
				//TextboxListcell<StockOpnameDetailDTO> textBoxResult = new TextboxListcell<StockOpnameDetailDTO>(data, data.getOpnameResult(),"opnameResult", false);
				Label recommendation = new Label();
				createRecommendation(item,data,recommendation);
				
				
				
				Listbox hoRecommendation = new Listbox();
				
				List<KeyValue> data2 = new ArrayList<KeyValue>();
			
				data2.addAll(stockOpnameService.getSORecommendList());
				ListModelList<KeyValue> model2 = new ListModelList<KeyValue>(data2);
				//hoRecommendation.setRows(0);
			    //hoRecommendation.setMultiple(false);
				hoRecommendation.setMold("select");
				hoRecommendation.setWidth("220px");
				//hoRecommendation.setCheckmark(true);
				KeyValue all = new KeyValue();
				all.setKey("0");
				all.setValue("ALL");
				all.setDescription("--Select--");
				hoRecommendation.appendItem((String)all.getDescription(), (String)all.getValue());
				for(int i=0;i<model2.getSize();i++){
					KeyValue key = (KeyValue)model2.get(i);
					hoRecommendation.appendItem((String)key.getDescription(), (String)key.getValue());
				}
				
				
				hoRecommendation.renderAll();
				hoRecommendation.setSelectedIndex(0);
				
				createHORecommendation(item,data,hoRecommendation,index);
				
				createLinkImageReg(item,data,image);
				Label bookTypeCode = new Label();
				createBookTypeCode(item,data,bookTypeCode);
				Doublebox netBookValue = new Doublebox();
				createNetBookValue(item,data,netBookValue);
				Label branchcode = new Label();
				createBranchCode(item,data,branchcode);
				Label branchName = new Label();
				createBranchName(item,data,branchName);
				Label locationCode = new Label();
				createLocationCode(item,data,locationCode);
				Label locationName = new Label();
				createLocationName(item,data,locationName);
				Label assetCategory = new Label();
				createAssetCategory(item,data,assetCategory);
				Label dateOfservice = new Label();
				createDateOfService(item,data,dateOfservice);
				Label serialNumber = new Label();
				createSerialNumber(item,data,serialNumber);
				Label stockOpnameBy = new Label();
				createSTockOpnameBy(item,data,stockOpnameBy);
				Label stockOpnameDate = new Label();
				createSTockOpnameDate(item,data,stockOpnameDate);
				Label lastUpdateBy = new Label();
				createLastUpdateBy(item,data,lastUpdateBy);
				Label lastUpdateDate = new Label();
				createLastUpdateDate(item,data,lastUpdateDate);
				
				
							
			}
		};
	}
	
	private void createLinkResult(Listitem item, final StockOpnameDetailDTO data, A result) {
		Listcell cell = new Listcell();
		result.setLabel("Result");
		result.addEventListener("onClick", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				StockOpnameDetail sod = (StockOpnameDetail) data;
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("stockOpnameDetail", sod);
				param.put("source", lstStockOpnameDetail);
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_result.zul", getSelf().getParent(), param);
				win.doModal();
			}
		});
		result.setParent(cell);
		cell.setParent(item);
	}
	
	private void createAssetNo(Listitem item, final StockOpnameDetailDTO data, Label assetNo) {
		Listcell cell = new Listcell();
		assetNo.setValue(data.getAssetNo());
		assetNo.setParent(cell);
		cell.setParent(item);
	}
	
	private void createDescription(Listitem item, final StockOpnameDetailDTO data, Label desc) {
		Listcell cell = new Listcell();
		desc.setValue(data.getDescription());
		desc.setParent(cell);
		cell.setParent(item);
	}
	
	private void createResult(Listitem item, final StockOpnameDetailDTO data, Label result) {
		Listcell cell = new Listcell();
		result.setValue(data.getOpnameResult());
		result.setParent(cell);
		cell.setParent(item);
	}
	
	private void createRecommendation(Listitem item, final StockOpnameDetailDTO data, Label recommendation) {
		Listcell cell = new Listcell();
		recommendation.setValue(data.getOpnameSugest());
		recommendation.setParent(cell);
		cell.setParent(item);
	}
	
	private void createHORecommendation(Listitem item, final StockOpnameDetailDTO data, final Listbox HOrecommendation, final int index) {
		Listcell cell = new Listcell();
		
		for(int i=0;i<HOrecommendation.getItemCount();i++){
			Listitem val = (Listitem)HOrecommendation.getItems().get(i);
			if(val.getValue().toString().equals(data.getOpnameHOSugestCode())){
				HOrecommendation.setSelectedIndex(i);
			}
		}
		HOrecommendation.addEventListener("onSelect", new SerializableEventListener<Event>() {
			//final KeyValue keyResult = (KeyValue)HOrecommendation.getModel().getElementAt(HOrecommendation.getSelectedIndex());
			private static final long serialVersionUID = 1L;
            //Listitem item = HOrecommendation.getSelectedItem().getLabel();
            
			@Override
			public void onEvent(Event arg0) throws Exception {
				System.out.println("HOrecommendation.getSelectedItem().getValue()=="+HOrecommendation.getSelectedItem().getValue());
				data.setOpnameHOSugestCode((String)HOrecommendation.getSelectedItem().getValue());
				if(data.getStockOpnameImg()!=null&&data.getStockOpnameImg().size()>0){
					
				}else{
				List listStockOpnameImg = (List<StockOpnameImgDTO>)stockOpnameService.getStockOpnameImgByStockOpnameDtlId(data.getId());
				data.setStockOpnameImg(listStockOpnameImg);
				}
				editStockOpnameDtl.put(data.getId(), data);
			}
		});
		
		HOrecommendation.addEventListener("onFocus", new SerializableEventListener<Event>() {
			//final KeyValue keyResult = (KeyValue)HOrecommendation.getModel().getElementAt(HOrecommendation.getSelectedIndex());
			private static final long serialVersionUID = 1L;
            //Listitem item = HOrecommendation.getSelectedItem().getLabel();
            
			@Override
			public void onEvent(Event arg0) throws Exception {
					lstStockOpnameDetail.setSelectedIndex(index);
				
				
			}
		});
		
		if(securityServiceImpl.getSecurityProfile().getBranchId()!=null && securityServiceImpl.getSecurityProfile().getBranchId()== -1){
			HOrecommendation.setDisabled(false);
		}else{
			HOrecommendation.setDisabled(true);
		}
		
		HOrecommendation.setParent(cell);
		cell.setParent(item);
	}
	
	private void createLinkImageReg(Listitem item, final StockOpnameDetailDTO data, A image) {
		Listcell cell = new Listcell();
		image.setLabel("Photo");
		image.setStyle("align:center");
		image.addEventListener("onClick", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				StockOpnameDetail sod = (StockOpnameDetail) data;
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("stockOpnameDetail", sod);
				param.put("source", lstStockOpnameDetail);
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_result_image.zul", getSelf().getParent(), param);
				win.doModal();
			}
		});
		image.setParent(cell);
		cell.setParent(item);
	}
	
	private void createBookTypeCode(Listitem item, final StockOpnameDetailDTO data, Label bookTypeCode) {
		Listcell cell = new Listcell();
		bookTypeCode.setValue(data.getAssetType());
		bookTypeCode.setParent(cell);
		cell.setParent(item);
	}
	
	private void createNetBookValue(Listitem item, final StockOpnameDetailDTO data, final Doublebox netBookValue) {
		Listcell cell = new Listcell();
		netBookValue.setFormat("###,###.###");
		netBookValue.setStyle("border:none; background-color:transparent");
		netBookValue.setValue(data.getNetBookValue()==null?new Double(0):(data.getNetBookValue()));
		//netBookValue.setValue(data.getNetBookValue()!=null?data.getNetBookValue().toString():null);
		netBookValue.setParent(cell);
		cell.setParent(item);
	}
	
	public static String formatNumber(double number){
		final NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
		return nf.format(number);
	}

	private void createBranchCode(Listitem item, final StockOpnameDetailDTO data, Label branchCode) {
		Listcell cell = new Listcell();
		branchCode.setValue(data.getBranchCode()!=null?data.getBranchCode():null);
		branchCode.setParent(cell);
		cell.setParent(item);
	}
	
	private void createBranchName(Listitem item, final StockOpnameDetailDTO data, Label branchName) {
		Listcell cell = new Listcell();
		branchName.setValue(data.getBranchName());
		branchName.setParent(cell);
		cell.setParent(item);
	}
	
	private void createLocationCode(Listitem item, final StockOpnameDetailDTO data, Label locationCode) {
		Listcell cell = new Listcell();
		locationCode.setValue(data.getLocationCode()!=null?data.getLocationCode().toString():null);
		locationCode.setParent(cell);
		cell.setParent(item);
	}
	
	private void createLocationName(Listitem item, final StockOpnameDetailDTO data, Label locationName) {
		Listcell cell = new Listcell();
		locationName.setValue(data.getLocationName());
		locationName.setParent(cell);
		cell.setParent(item);
	}
	
	private void createAssetCategory(Listitem item, final StockOpnameDetailDTO data, Label assetCategory) {
		Listcell cell = new Listcell();
		assetCategory.setValue(data.getCategoryName());
		assetCategory.setParent(cell);
		cell.setParent(item);
	}
	
	private void createLastUpdateBy(Listitem item, final StockOpnameDetailDTO data, Label lastUpdateBy) {
		 Listcell cell = new Listcell();
		 lastUpdateBy.setValue(data.getLastUpdateByName()!=null?data.getLastUpdateByName().toString():null);
		 lastUpdateBy.setParent(cell);
		 cell.setParent(item);
		 }
		      
	private void createLastUpdateDate(Listitem item, final StockOpnameDetailDTO data, Label lastUpdateDate) {
		 Listcell cell = new Listcell();
		 lastUpdateDate.setValue(data.getLastUpdateDate()!=null?sdf.format(data.getLastUpdateDate()):null);
		 lastUpdateDate.setParent(cell);
		 cell.setParent(item);
		 }
	private void createDateOfService(Listitem item, final StockOpnameDetailDTO data, Label dateOfService) {
		Listcell cell = new Listcell();
		dateOfService.setValue(data.getDateOfService()!=null?sdf.format(data.getDateOfService()):null);
		dateOfService.setParent(cell);
		cell.setParent(item);
	}
	
	private void createSerialNumber(Listitem item, final StockOpnameDetailDTO data, Label serialNumber) {
		Listcell cell = new Listcell();
		serialNumber.setValue(data.getSerialNumber());
		serialNumber.setParent(cell);
		cell.setParent(item);
	}
	
	private void createSTockOpnameBy(Listitem item, final StockOpnameDetailDTO data, Label stockOpnameBy) {
		Listcell cell = new Listcell();
		stockOpnameBy.setValue(""+data.getCreatedByName());
		stockOpnameBy.setParent(cell);
		cell.setParent(item);
	}
	
	private void createSTockOpnameDate(Listitem item, final StockOpnameDetailDTO data, Label stockOpnameDate) {
		Listcell cell = new Listcell();
		stockOpnameDate.setValue(data.getCreationDate()!=null?sdf.format(data.getCreationDate()):null);
		stockOpnameDate.setParent(cell);
		cell.setParent(item);
	}
	
	
	
	private SerializableListItemRenderer<StockOpnameUnregAssetsDTO> getListitemRendererUnregAsset() {
		return new SerializableListItemRenderer<StockOpnameUnregAssetsDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final StockOpnameUnregAssetsDTO data, int index)
					throws Exception {
				
				
				
				StockOpnameUnregAssets lstStockOpnameDetail = data;
				
				new Listcell().setParent(item);
				A result = new A();
				A delete = new A();
				A image = new A();
				
				createLinkResult(item,data,result,delete);
				Label assetNo = new Label();
				createAssetNo(item,data,assetNo);
				Label desc = new Label();
				createDescription(item,data,desc);
				Label results = new Label();
				createResult(item,data,results);
				Label recommendation = new Label();
				createRecommendation(item,data,recommendation);
				Listbox hoRecommendation = new Listbox();
				/*List<String> listHoRecommendation = new ArrayList<String>();
				
				listHoRecommendation.add("--Select--");
				listHoRecommendation.add("Retirement - Penjualan");
				listHoRecommendation.add("Retirement - Asuransi");
				listHoRecommendation.add("Retirement - Hibah");
				listHoRecommendation.add("Retirement - Tidak Ditemukan");
				listHoRecommendation.add("Mutasi");
				hoRecommendation.setModel(new ListModelList<String>(listHoRecommendation));*/
				List<KeyValue> data2 = new ArrayList<KeyValue>();
				
				data2.addAll(stockOpnameService.getSORecommendList());
				ListModelList<KeyValue> model2 = new ListModelList<KeyValue>(data2);
				//hoRecommendation.setTemplate("model", "model");
				
				//hoRecommendation.setRows(0);
			    //hoRecommendation.setMultiple(false);
				//hoRecommendation.setCheckmark(true);
				hoRecommendation.setMold("select");
				hoRecommendation.setWidth("220px");
				//hoRecommendation.setModel(model2);
				KeyValue all = new KeyValue();
				all.setKey("0");
				all.setValue("ALL");
				all.setDescription("--Select--");
				hoRecommendation.appendItem((String)all.getDescription(), (String)all.getValue());
				for(int i=0;i<model2.getSize();i++){
					KeyValue key = (KeyValue)model2.get(i);
					hoRecommendation.appendItem((String)key.getDescription(), (String)key.getValue());
				}
				hoRecommendation.renderAll();
				hoRecommendation.setSelectedIndex(0);
				createHORecommendation(item,data,hoRecommendation,index);
				
				createLinkImageUnreg(item,data,image);
				
				Label bookTypeCode = new Label();
				createBookTypeCode(item,data,bookTypeCode);
				Label branchcode = new Label();
				createBranchCode(item,data,branchcode);
				Label branchName = new Label();
				createBranchName(item,data,branchName);
				Label locationCode = new Label();
				createLocationCode(item,data,locationCode);
				Label locationName = new Label();
				createLocationName(item,data,locationName);
				Label assetCategory = new Label();
				createAssetCategory(item,data,assetCategory);
				Label dateOfservice = new Label();
				createDateOfService(item,data,dateOfservice);
				Label serialNumber = new Label();
				createSerialNumber(item,data,serialNumber);
				/*Label stockOpnameBy = new Label();
				createSTockOpnameBy(item,data,stockOpnameBy);
				Label stockOpnameDate = new Label();
				createSTockOpnameDate(item,data,stockOpnameDate);*/
				
							
			}
		};
	}
	
	
	private void createLinkImageUnreg(Listitem item, final StockOpnameUnregAssetsDTO data, A image) {
		Listcell cell = new Listcell();
		image.setLabel("Photo");
		image.setStyle("align:left");
		image.addEventListener("onClick", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				StockOpnameUnregAssetsDTO stockOpnameUnregAssets = (StockOpnameUnregAssetsDTO) data;
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("stockOpnameUnregAssets", stockOpnameUnregAssets);
				param.put("source", lstStockOpnameUnregAsset);
				param.put("Branch", bnbBranch.getKeyValue());
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_edit_image.zul",
						getSelf().getParent(), param);
				win.doModal();

			}
		});
		image.setParent(cell);
		cell.setParent(item);
	}


	private void createLinkResult(Listitem item, final StockOpnameUnregAssetsDTO data, A edit, A delete) {
		Listcell cell = new Listcell();
		edit.setLabel("Edit");
		edit.addEventListener("onClick", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				StockOpnameUnregAssetsDTO stockOpnameUnregAssets = (StockOpnameUnregAssetsDTO) data;
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("stockOpnameUnregAssets", stockOpnameUnregAssets);
				param.put("source", lstStockOpnameUnregAsset);
				param.put("Branch", bnbBranch.getKeyValue());
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_edit.zul",
						getSelf().getParent(), param);
				win.doModal();

			}
		});
		edit.setParent(cell);

		Space space = new Space();
		space.setParent(cell);
		Label separator = new Label();
		separator.setValue("||");
		separator.setParent(cell);
		space.setParent(cell);
		space.setParent(cell);

		delete.setLabel("Delete");
		delete.addEventListener("onClick", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Are you sure delete this data ?", "Information", Messagebox.YES | Messagebox.NO,
						Messagebox.QUESTION, new SerializableEventListener<Event>() {

							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							@Override
							public void onEvent(Event event) throws Exception {
								int status = (int) event.getData();
								if(status == 16) {
								StockOpnameUnregAssetsDTO stockOpnameUnregAssets = (StockOpnameUnregAssetsDTO) data;
								int row = stockOpnameUnregAssetsList.lastIndexOf(stockOpnameUnregAssets);
								lstStockOpnameUnregAsset.removeItemAt(row);
								stockOpnameUnregAssetsList.remove(row);
								}
							}
						});
			}
		});
		delete.setParent(cell);

		cell.setParent(item);
	}

	private void createAssetNo(Listitem item, final StockOpnameUnregAssetsDTO data, Label assetNo) {
		Listcell cell = new Listcell();
		assetNo.setValue(data.getAssetNo());
		assetNo.setParent(cell);
		cell.setParent(item);
	}
	
	private void createDescription(Listitem item, final StockOpnameUnregAssetsDTO data, Label desc) {
		Listcell cell = new Listcell();
		desc.setValue(data.getDescription());
		desc.setParent(cell);
		cell.setParent(item);
	}
	
	private void createResult(Listitem item, final StockOpnameUnregAssetsDTO data, Label result) {
		Listcell cell = new Listcell();
		result.setValue(data.getOpnameResult());
		result.setParent(cell);
		cell.setParent(item);
	}
	
	private void createRecommendation(Listitem item, final StockOpnameUnregAssetsDTO data, Label recommendation) {
		Listcell cell = new Listcell();
		recommendation.setValue(data.getOpnameSugest());
		recommendation.setParent(cell);
		cell.setParent(item);
	}
	
	private void createHORecommendation(Listitem item, final StockOpnameUnregAssetsDTO data, final Listbox HOrecommendation, final int index) {
		Listcell cell = new Listcell();
		
		for(int i=0;i<HOrecommendation.getItemCount();i++){
			Listitem val = (Listitem)HOrecommendation.getItems().get(i);
			if(val.getValue().toString().equals(data.getOpnameHOSugestCode())){
				HOrecommendation.setSelectedIndex(i);
			}
		}
		
		HOrecommendation.addEventListener("onSelect", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				System.out.println("HOrecommendation.getSelectedItem().getValue()=="+HOrecommendation.getSelectedItem().getValue());
				data.setOpnameHOSugestCode((String)HOrecommendation.getSelectedItem().getValue());
				
				
			}
		});
		
		HOrecommendation.addEventListener("onFocus", new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;
            //Listitem item = HOrecommendation.getSelectedItem().getLabel();
            
			@Override
			public void onEvent(Event arg0) throws Exception {
				lstStockOpnameUnregAsset.setSelectedIndex(index);
				
			}
		});
		if(securityServiceImpl.getSecurityProfile().getBranchId()!=null && securityServiceImpl.getSecurityProfile().getBranchId()== -1){
			HOrecommendation.setDisabled(false);
		}else{
			HOrecommendation.setDisabled(true);
		}
		HOrecommendation.setParent(cell);
		cell.setParent(item);
	}
	
	private void createImage(Listitem item, final StockOpnameUnregAssetsDTO data, Label Image) {
		Listcell cell = new Listcell();
		Image.setValue("");
		Image.setParent(cell);
		cell.setParent(item);
	}
	
	private void createBookTypeCode(Listitem item, final StockOpnameUnregAssetsDTO data, Label bookTypeCode) {
		Listcell cell = new Listcell();
		bookTypeCode.setValue(data.getAssetType());
		bookTypeCode.setParent(cell);
		cell.setParent(item);
	}
	
	private void createBranchCode(Listitem item, final StockOpnameUnregAssetsDTO data, Label branchCode) {
		Listcell cell = new Listcell();
		branchCode.setValue(data.getBranchCode());
		branchCode.setParent(cell);
		cell.setParent(item);
	}
	
	private void createBranchName(Listitem item, final StockOpnameUnregAssetsDTO data, Label branchName) {
		Listcell cell = new Listcell();
		branchName.setValue(data.getBranchName());
		branchName.setParent(cell);
		cell.setParent(item);
	}
	
	private void createLocationCode(Listitem item, final StockOpnameUnregAssetsDTO data, Label locationCode) {
		Listcell cell = new Listcell();
		locationCode.setValue(data.getLocationCode()!=null?data.getLocationCode().toString():null);
		locationCode.setParent(cell);
		cell.setParent(item);
	}
	
	private void createLocationName(Listitem item, final StockOpnameUnregAssetsDTO data, Label locationName) {
		Listcell cell = new Listcell();
		locationName.setValue(data.getLocationName());
		locationName.setParent(cell);
		cell.setParent(item);
	}
	
	private void createAssetCategory(Listitem item, final StockOpnameUnregAssetsDTO data, Label assetCategory) {
		Listcell cell = new Listcell();
		assetCategory.setValue(data.getCategoryName()!=null?data.getCategoryName().toString():null);
		assetCategory.setParent(cell);
		cell.setParent(item);
	}
	
	private void createDateOfService(Listitem item, final StockOpnameUnregAssetsDTO data, Label dateOfService) {
		Listcell cell = new Listcell();
		dateOfService.setValue(data.getDatePlacedInService()!=null?sdf.format(data.getDatePlacedInService()):null);
		dateOfService.setParent(cell);
		cell.setParent(item);
	}
	
	private void createSerialNumber(Listitem item, final StockOpnameUnregAssetsDTO data, Label serialNumber) {
		Listcell cell = new Listcell();
		serialNumber.setValue(data.getSerialNumber());
		serialNumber.setParent(cell);
		cell.setParent(item);
	}
	
	private void createSTockOpnameBy(Listitem item, final StockOpnameUnregAssetsDTO data, Label stockOpnameBy) {
		Listcell cell = new Listcell();
		stockOpnameBy.setValue(data.getCreatedBy()!=null?data.getCreatedBy().toString():null);
		stockOpnameBy.setParent(cell);
		cell.setParent(item);
	}
	
	private void createSTockOpnameDate(Listitem item, final StockOpnameUnregAssetsDTO data, Label stockOpnameDate) {
		Listcell cell = new Listcell();
		stockOpnameDate.setValue(data.getCreationDate()!=null?sdf.format(data.getCreationDate()):null);
		stockOpnameDate.setParent(cell);
		cell.setParent(item);
	}
	
	
	
	private void loadStockOpnames() {
		selected = (StockOpnameDTO) arg.get("stockOpnameDTO");
		if(selected != null) {
			stockOpnameDto = stockOpnameService.getStockOpnameById(selected.getId());
			//stockOpnameDto = modelMapper.map(stockOpname, StockOpnameDTO.class);
			//lstStockOpnameDetailList = stockOpnameDto.getStockOpnameDetail();
			stockOpnameUnregAssetsList = stockOpnameDto.getStockOpnameUnregAssets();
			//lstStockOpnameDetail.setModel((ListModel<StockOpnameDetail>) lstStockOpnameDetailList);
			/*ListModelList<StockOpnameDetail> model = new ListModelList<StockOpnameDetail>(lstStockOpnameDetailList);
			model.setMultiple(true);
			lstStockOpnameDetail.setModel(model);*/
			searchSoDtl();
			
			ListModelList<StockOpnameUnregAssetsDTO> model2 = new ListModelList<StockOpnameUnregAssetsDTO>(stockOpnameUnregAssetsList);
			model2.setMultiple(true);
			lstStockOpnameUnregAsset.setModel(model2);
			lstStockOpnameUnregAssetForDownload.setModel(model2);
			
			txtPeriodName.setValue(stockOpnameDto.getDescription());
			dtPeriodStartDate.setValue(stockOpnameDto.getStartDate());
			dtPeriodEndDate.setValue(stockOpnameDto.getEndDate());
			
			gbResult.setVisible(true);
			gbUnreg.setVisible(true);
		}else{
			gbUnreg.setVisible(false);
			gbResult.setVisible(false);
			
		}
	}
	
	private void initFunctionSecurity(){
		/*if (functionPermission.isCreateable()) {
			disableComponent(false);
		}else if (functionPermission.isEditable()) {
			disableComponent(false);
		}else{
			disableComponent(true);
		}
		
		if(isEdit){
			if(functionPermission.isEditable())
				disableComponent(false);
			else
				disableComponent(true);
		}*/
	}
	
	
	private void disableComponent(boolean disabled){
		/*btnSave.setDisabled(disabled);
		txtName.setDisabled(disabled);
		txtDesc.setDisabled(disabled);
		dtbDateFrom.setDisabled(disabled);
		dtbDateTo.setDisabled(disabled);
		for (Component comp : rdType.getChildren()) {
			Radio rd = (Radio) comp;
			rd.setDisabled(disabled);
		}
		btnAddRow.setDisabled(disabled);
		btnDelete.setDisabled(disabled);*/
	}
	
	
		
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		clearErrorMessage();
		if(!validate()) {
		if(stockOpnameDto == null){
			stockOpnameDto = new StockOpnameDTO();
			stockOpname = new StockOpname();
		}
		
		stockOpnameDto.setDescription(txtPeriodName.getValue());
		stockOpnameDto.setStartDate(dtPeriodStartDate.getValue());
		stockOpnameDto.setEndDate(dtPeriodEndDate.getValue());
		stockOpnameDto.setBranchId((Long)bnbBranch.getKeyValue().getKey());
		stockOpnameDto.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		
		if(selected == null) {
			stockOpnameDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			stockOpnameDto.setCreationDate(new Date());
		} else {
			stockOpnameDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			stockOpnameDto.setCreationDate(selected.getCreationDate());
		}
		stockOpnameDto.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
		stockOpnameDto.setLastUpdateDate(new Date());
		
		
		//stockOpnameDto.setStockOpnameDetail(lstStockOpnameDetailList);
		List<StockOpnameDetailDTO> list = new ArrayList<StockOpnameDetailDTO>(editStockOpnameDtl.values());
		stockOpnameDto.setStockOpnameDetail(list);
		stockOpnameDto.setStockOpnameUnregAssets(stockOpnameUnregAssetsList);
		
		if(securityServiceImpl.getSecurityProfile().getBranchId()!=null && securityServiceImpl.getSecurityProfile().getBranchId()!= -1){
			stockOpnameDto.setIsBranchSubmitter(true);
		}else{
			stockOpnameDto.setIsBranchSubmitter(false);
		}
		
		stockOpnameDto.setBranchNameSubmitter(securityServiceImpl.getSecurityProfile().getBranchName());
		
		stockOpnameDto.setPersonUUID(securityServiceImpl.getSecurityProfile().getPersonUUID());
		
				Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {	
						int status = (int) event.getData();
						if(status == 16) {
							
								Thread thread = new Thread() {
									public void run() {
										try {
											stockOpnameService.save(stockOpnameDto);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
									}
								};
								thread.start();
								// stockOpnameService.save(stockOpnameDto);
								Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show("Data aset stock opname sedang disimpan, proses ini memerlukan waktu agak lama, silahkan lakukan pencarian beberapa kali untuk memeriksa apakah data sudah selesai disimpan.", "Information", Messagebox.YES,
										Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/ssoa/stock_opname.zul", getSelf().getParent(),
										param);
								getSelf().detach();
							
						} else {
							return;
						}
					}
				});
			}
		
	}
	
	@Listen("onClick = button#btnSubmit")
	public void onSubmit() {
		clearErrorMessage();
		// if(lstStockOpnameDetail.validate()) {
		Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							try {

								StockOpnameDTO stockOpnameDTO = new StockOpnameDTO();
								stockOpnameDTO.setId(selected.getId());
								/*stockOpnameDTO.setBranchId(securityServiceImpl.getSecurityProfile().getBranchId());
								stockOpnameDTO.setBusinessGroupId(
										securityServiceImpl.getSecurityProfile().getBusinessGroupId());*/
								stockOpnameDTO.setBranchId(securityServiceImpl.getSecurityProfile().getBranchId());
								stockOpnameDTO
										.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
								stockOpnameDTO.setJobId(securityServiceImpl.getSecurityProfile().getJobId());

								organizationDTO = organizationSetupServiceImpl
										.findById(securityServiceImpl.getSecurityProfile().getBranchId());

								if (organizationDTO == null
										&& securityServiceImpl.getSecurityProfile().getBranchId().equals(-1L)) {
									organizationDTO = organizationSetupServiceImpl.getHeadOffice();
									stockOpnameDTO.setSoBranchType("SO_BRANCH_TYPE_HO");
								}else{
									stockOpnameDTO.setSoBranchType("SO_BRANCH_TYPE_NON_HO");
								}

								stockOpnameDTO.setOrganizationId(organizationDTO.getId());

								/*stockOpnameService.submit(stockOpnameDTO,
										securityServiceImpl.getSecurityProfile().getPersonUUID());*/
								/*
								 * avmAdapterServiceImpl.submitAvmTransaction(
								 * UUID.randomUUID(), securityServiceImpl
								 * .getSecurityProfile().getPersonUUID(),
								 * stockOpnameDTO,
								 * co.id.fifgroup.systemworkflow.constants.
								 * TrxType.STOCK_OPNAME, 1L);
								 */

								Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES,
										Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/ssoa/stock_opname.zul", getSelf().getParent(),
										param);
								getSelf().detach();

							}  catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							return;
						}
					}
				});
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtPeriodName);
		ErrorMessageUtil.clearErrorMessage(bnbBranch);
		ErrorMessageUtil.clearErrorMessage(dtPeriodStartDate);
		ErrorMessageUtil.clearErrorMessage(dtPeriodEndDate);
		//ErrorMessageUtil.clearErrorMessage(lstStockOpnameDetail.getPreviousSibling());
	}
	
	private void showErrorMessage(ValidationException vex){
		Map<String, String> errors = vex.getConstraintViolations();
		
		if(txtPeriodName.getValue().equalsIgnoreCase("")) {
			ErrorMessageUtil.setErrorMessage(txtPeriodName, "Period Name can't empty");
		}
		if(dtPeriodStartDate.getValue() == null) {
			ErrorMessageUtil.setErrorMessage(dtPeriodStartDate, "Period Start Date can't empty");
		}
		if(dtPeriodEndDate.getValue() == null) {
			ErrorMessageUtil.setErrorMessage(dtPeriodEndDate, "Period End Date can't empty");
		}
		
	}	
	
	private Boolean validate(){
		Boolean flag = false;
		Date date = new Date();
		String action = (String) arg.get("action");
		if(txtPeriodName.getValue().equalsIgnoreCase("")) {
			ErrorMessageUtil.setErrorMessage(txtPeriodName, "Period Name must be filled");
			flag = true;
		}
		if(dtPeriodStartDate.getValue() == null) {
			ErrorMessageUtil.setErrorMessage(dtPeriodStartDate, "Period Start Date must be filled");
			flag = true;
		}
		if(action!=null && action.equals("addNew")){
			if(dtPeriodStartDate.getValue() != null)
			{
				if(dtPeriodStartDate.getValue().getDate() < date.getDate() ) {
				    ErrorMessageUtil.setErrorMessage(dtPeriodStartDate, "Period Start Date should not be less then current date");
				    flag = true;
				}
			}
		}

		if(dtPeriodEndDate.getValue() == null) {
			ErrorMessageUtil.setErrorMessage(dtPeriodEndDate, "Period End Date must be filled");
			flag = true;
		}
		
		if(dtPeriodStartDate.getValue()!= null && dtPeriodEndDate.getValue() != null)
		{
			if(dtPeriodStartDate.getValue().getTime() > dtPeriodEndDate.getValue().getTime()) {
				ErrorMessageUtil.setErrorMessage(dtPeriodStartDate, "Period Start Date should be less than Period End Date");
				flag = true;
			}
			
			if(dtPeriodEndDate.getValue().getTime() < dtPeriodStartDate.getValue().getTime()) {
				ErrorMessageUtil.setErrorMessage(dtPeriodEndDate, "Period End Date should be greater than Period Start Date");
				flag = true;
			}
		}
		if(bnbBranch.getValue().isEmpty()) {
			ErrorMessageUtil.setErrorMessage(bnbBranch, "Branch must be filled");
			flag = true;
		}
		else{
			if(action!=null && action.equals("addNew")&&stockOpnameService.countSONotClosed((Long)bnbBranch.getKeyValue().getKey(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId())>0){
				Messagebox.show("Proses Simpan Gagal, Pada cabang "+bnbBranch.getKeyValue().getValue()+" Masih terdapat SO yang statusnya belum Closed " , "Information", Messagebox.YES,
						Messagebox.ERROR);
				flag = true;
			}
			if(action!=null && action.equals("addNew")&&stockOpnameService.countRetirementNotClosed((Long)bnbBranch.getKeyValue().getKey(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId())>0){
				Messagebox.show("Proses Simpan Gagal, Pada cabang "+bnbBranch.getKeyValue().getValue()+" Masih terdapat Asset Retirement yang statusnya masih OnApproval " , "Information", Messagebox.YES,
						Messagebox.ERROR);
				flag = true;
			}
			if(action!=null && action.equals("addNew")&&stockOpnameService.countTransferNotClosed((Long)bnbBranch.getKeyValue().getKey(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId())>0){
				Messagebox.show("Proses Simpan Gagal, Pada cabang "+bnbBranch.getKeyValue().getValue()+" Masih terdapat Asset Transfer yang statusnya masih OnApproval " , "Information", Messagebox.YES,
						Messagebox.ERROR);
				flag = true;
			}
			
			if(action!=null && action.equals("addNew") && stockOpnameService.countAssetByBranchCode((Long)bnbBranch.getKeyValue().getKey(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId()) == 0){
				Messagebox.show("Proses Simpan Gagal, Cabang "+bnbBranch.getKeyValue().getValue()+" sudah tidak memiliki asset " , "Information", Messagebox.YES,
						Messagebox.ERROR);
				flag = true;
			}
		}
		
		return flag;
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
					Executions.createComponents("~./hcms/ssoa/stock_opname.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
	@Listen("onResult= #winStockOpnameAdd")
	public void onResult(ForwardEvent event){
		StockOpnameDetail lstStockOpnameDetail = (StockOpnameDetail) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDetail", lstStockOpnameDetail);
		param.put("source", lstStockOpnameDetail);
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_result.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	
	
	@Listen ("onClick = button#btnAddUnregisteredAsset")
	public void onshowInput() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("source", lstStockOpnameUnregAsset);
		param.put("Branch", bnbBranch.getKeyValue());
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_edit.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onEdit= #winStockOpnameAdd")
	public void onEdit(ForwardEvent event){
		StockOpnameUnregAssetsDTO stockOpnameUnregAssets = (StockOpnameUnregAssetsDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameUnregAssets", stockOpnameUnregAssets);
		param.put("source", lstStockOpnameUnregAsset);
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_edit.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onDelete= #winStockOpnameAdd")
	public void onDelete(ForwardEvent event){
		StockOpnameUnregAssetsDTO stockOpnameUnregAssets = (StockOpnameUnregAssetsDTO) event.getData();
		int row =stockOpnameUnregAssetsList.lastIndexOf(stockOpnameUnregAssets);
		lstStockOpnameUnregAsset.removeItemAt(row);
		stockOpnameUnregAssetsList.remove(row);
	}
	
    private StockOpnameDetailExample searchCriteria(){
    	StockOpnameDetailExample example = new StockOpnameDetailExample();
    	co.id.fifgroup.ssoa.domain.StockOpnameDetailExample.Criteria criteria = example.createCriteria();
		if(txtAssetNoSearch.getValue() != null && !txtAssetNoSearch.getValue().toString().isEmpty()){
			criteria = criteria.andAssetNoLike("%"+txtAssetNoSearch.getValue()+"%");
		}
		
		if(cbResultSearch.getSelectedIndex() != 0){
			KeyValue keyResult = (KeyValue)cbResultSearch.getModel().getElementAt(cbResultSearch.getSelectedIndex());
			criteria = criteria.andResultLike((String)keyResult.getValue());
		}
		
		if(txtDescriptionSearch.getValue() != null && !txtDescriptionSearch.getValue().toString().isEmpty()){
			criteria = criteria.andDescriptionLike("%"+txtDescriptionSearch.getValue()+"%");
		}
		
		if(cbRecommendationSearch.getSelectedIndex() != 0){
			KeyValue keyResult = (KeyValue)cbRecommendationSearch.getModel().getElementAt(cbRecommendationSearch.getSelectedIndex());
			criteria = criteria.andRecomendationLike((String)keyResult.getValue());
		}
		
		criteria = criteria.andStockOpnameIdLike(stockOpnameDto.getId());
		
		example.setOrderByClause(orderBy);
		
		return example;
    }
    
    @Listen ("onClick = button#btnSearch")
    public void searchSoDtl() {
		try {
			
			
			pgListOfValue.setTotalSize(stockOpnameService.countSODtlByExample(searchCriteria()));
			pgListOfValue.setPageSize(10);
			pgListOfValue.setActivePage(0);
			generateDataAndPaging();
		} catch (Exception e) {
			log.error("error", e);
		}
	}
    
    @Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		generateDataAndPaging();
	}
    
    

	private void generateDataAndPaging() {

		
		lstStockOpnameDetailList = stockOpnameService.getStockOpnameDtlByExampleWithRowbounds(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
		
		for(int i=0;i<lstStockOpnameDetailList.size();i++){
			StockOpnameDetailDTO sod=(StockOpnameDetailDTO)lstStockOpnameDetailList.get(i);
			StockOpnameDetailDTO sodEdit=editStockOpnameDtl.get(sod.getId());
			if(sodEdit!=null){
				//sod = sodEdit;
				lstStockOpnameDetailList.remove(i);
				lstStockOpnameDetailList.add(i, sodEdit);
			}
		
		}
		ListModelList<StockOpnameDetail> model = new ListModelList<StockOpnameDetail>(lstStockOpnameDetailList);
		model.setMultiple(true);
		lstStockOpnameDetail.setModel(model);
		lstStockOpnameDetail.renderAll();
		
	}
	
	private void generateDataForDownload(){
		List list = stockOpnameService.getStockOpnameDtlByExample(searchCriteria());
		ListModelList<StockOpnameDetailDTO> model2 = new ListModelList<StockOpnameDetailDTO>(list);
		lstStockOpnameDetailForDownload.setModel(model2);
		lstStockOpnameDetailForDownload.renderAll();
    }

	@Listen("onClick = #btnDownload")
	public void export() throws Exception {
    	generateDataForDownload();
		Listbox listbox = lstStockOpnameDetailForDownload;
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    if(cmbDownloadAs.getValue()!=null && cmbDownloadAs.getValue().equals("XLS")){
	    ExcelExporter exporter = new ExcelExporter();
	    exporter.export(listbox, out);
	    AMedia amedia = new AMedia("Report.xls", "xls", "application/file", out.toByteArray());
	    Filedownload.save(amedia);
	    out.close();
	    }else{
	    PdfExporter exporter = new PdfExporter();
	    exporter.export(listbox, out);
	    AMedia amedia = new AMedia("Report.pdf", "pdf", "application/pdf", out.toByteArray());
	    Filedownload.save(amedia);
	    out.close();
	    }
	}
	
	@Listen("onClick = #btnDownload2")
	public void export2() throws Exception {
    	
		Listbox listbox = lstStockOpnameUnregAssetForDownload;
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    if(cmbDownloadAs2.getValue()!=null && cmbDownloadAs2.getValue().equals("XLS")){
	    ExcelExporter exporter = new ExcelExporter();
	    exporter.export(listbox, out);
	    AMedia amedia = new AMedia("Report.xls", "xls", "application/file", out.toByteArray());
	    Filedownload.save(amedia);
	    out.close();
	    }else{
	    PdfExporter exporter = new PdfExporter();
	    exporter.export(listbox, out);
	    AMedia amedia = new AMedia("Report.pdf", "pdf", "application/pdf", out.toByteArray());
	    Filedownload.save(amedia);
	    out.close();
	    }
	}

	
}
