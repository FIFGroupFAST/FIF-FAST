package co.id.fifgroup.ssoa.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.domain.AssetTransferDetailExample;
import co.id.fifgroup.ssoa.domain.AssetTransferDetailExample.Criteria;
import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.ssoa.dto.AssetLabelingDetailDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.service.AssetTransferNonEBSService;
import co.id.fifgroup.ssoa.service.StockOpnameService;
import co.id.fifgroup.workstructure.service.LocationSetupService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetTransferNonEBSPopupComposer extends SelectorComposer<Window> {
	private static Logger log = LoggerFactory.getLogger(AssetTransferNonEBSPopupComposer.class);
	@Wire
	private Label lblErrAssetTransferInfo;
	@Wire
	private Listbox lbxAssetPopup;
	@Wire
	private Window winAssetTransferPopup;
	@Wire
	private Listbox cbRecommendation;
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
	private Listbox lstAssetPopup;
	@WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;
	private FunctionPermission functionPermission;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	private static final long serialVersionUID = 1L;
	@WireVariable(rewireOnActivate = true)
	private transient AssetTransferNonEBSService assetTransferNonEBSService;
	@WireVariable(rewireOnActivate = true)
	private transient LocationSetupService locationSetupService;
	@Wire
	private SSOACommonPopupBandbox bnbLocation;
	private AssetTransferDTO selected;
	private Listbox source;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Textbox txtAssetNumber;
	private Map<Long, AssetTransferDetailDTO> listAssetTransferDtlTmp = new HashMap<Long, AssetTransferDetailDTO>();
	@Wire
	private Textbox txtDescription;
	@Wire
	private Paging pgListOfValue;
	private String action;
	private String orderBy = "A.ASSET_ID ASC";
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		try {
			super.doAfterCompose(comp);
		}catch (Exception e) {
			e.printStackTrace();
		}

		init();
		sortAction();
		loadLocation(bnbLocation);
	}
	
	private void init() {
		source = arg.get("source") == null ? null : (Listbox) arg.get("source");
		action = arg.get("action") == null ? null : (String) arg.get("action");
		initListModelAsset();
		setComboBoxList();
	}
	
	private void sortAction(){
		
		hdrAssetType.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "A.ASSET_TYPE_CODE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrCategoryName.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(select segment3 from SOC_EBS_CATEGORIES where category_id =A.CATEGORY_ID)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrBranchCode.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(case when A.BRANCH_ID = -1 then 'HEADOFFICE' else (SELECT ORGANIZATION_CODE from WOS_ORGANIZATIONS where ORGANIZATION_ID = A.BRANCH_ID and rownum = 1) end )"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrBranchName.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(case when A.BRANCH_ID = -1 then 'Head Office' else (SELECT ORGANIZATION_NAME from WOS_ORGANIZATIONS where ORGANIZATION_ID = A.BRANCH_ID and rownum = 1) end )"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLocationCode.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(SELECT LOCATION_CODE from WOS_LOCATIONS where LOCATION_ID = A.LOCATION_ID and rownum =1)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLocationName.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(SELECT LOCATION_NAME from WOS_LOCATIONS where LOCATION_ID = A.LOCATION_ID and rownum =1)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrDatePlaceInService.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "A.DATE_PLACED_IN_SERVICE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrDescription.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "A.DESCRIPTION"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrSerialNumber.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "A.SERIAL_NUMBER"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
	}
	
	private void initListModelAsset() {
		lbxAssetPopup.addEventListener(Events.ON_SELECT, new EventListener<SelectEvent<Listitem, AssetTransferDetailDTO>>() {
			
			public void onEvent(SelectEvent<Listitem, AssetTransferDetailDTO> event) {
		        // Now you can access the details of the selection event..
		    	//listAssetTransferDtlTmp = new ArrayList<AssetTransferDetailDTO>();
		    	System.out.println("uselected=="+event.getUnselectedItems().size());
		    	
		    	int i=0;
				for(Iterator iterator=event.getSelectedItems().iterator(); event.getSelectedItems().iterator().hasNext();){
					Listitem itemAssetTransferDetailDTO = (Listitem)iterator.next();
					
					AssetTransferDetailDTO assetTransferDetailDTO = (AssetTransferDetailDTO)itemAssetTransferDetailDTO.getValue();
					listAssetTransferDtlTmp.put(assetTransferDetailDTO.getAssetId(),assetTransferDetailDTO);
					i++;
					if(i == event.getSelectedItems().size()){
						break;
					}
				}
				int x=0;
				for(Iterator iterator=event.getUnselectedItems().iterator(); event.getUnselectedItems().iterator().hasNext();){
					Listitem itemAssetTransferDetailDTO = (Listitem)iterator.next();
					
					AssetTransferDetailDTO assetTransferDetailDTO = (AssetTransferDetailDTO)itemAssetTransferDetailDTO.getValue();
					listAssetTransferDtlTmp.remove(assetTransferDetailDTO.getAssetId());
					x++;
					if(x == event.getUnselectedItems().size()){
						break;
					}
				}
				System.out.println(listAssetTransferDtlTmp.size());
		    }
		});
	}
	
	private void setComboBoxList(){
		
		List<KeyValue> data2 = new ArrayList<KeyValue>();
		KeyValue all = new KeyValue();
		all.setKey(null);
		all.setValue(null);
		all.setDescription("--Select--");
		data2.add(all);
		data2.addAll(stockOpnameService.getSORecommendList());
		ListModelList<KeyValue> model2 = new ListModelList<KeyValue>(data2);
		cbRecommendation.setModel(model2);
		cbRecommendation.renderAll();
		cbRecommendation.setSelectedIndex(0);
	}
	
	
	@Listen("onClick = #btnFindPopup")
	public void searchAsset(){
		pgListOfValue.setTotalSize(assetTransferNonEBSService.countAssetSODtlByCriteria(searchCriteria()));
		pgListOfValue.setPageSize(5);
		pgListOfValue.setActivePage(0);
		generateDataAndPaging();
	}
	
	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		generateDataAndPaging();
	}
	
	private AssetTransferDetailExample searchCriteria(){
		AssetTransferDTO assetTransferDTO = (AssetTransferDTO) arg.get("branchId");
		System.out.println("branch Id..................." + assetTransferDTO.getBranchOrigin());
		AssetTransferDetailExample example = new AssetTransferDetailExample();
    	Criteria criteria = example.createCriteria();
		if(!txtDescription.getValue().isEmpty()){
			criteria = criteria.andDescriptionLike("%"+ txtDescription.getValue() +"%");
		}
		if(!bnbLocation.getValue().isEmpty()){
			criteria = criteria.andLocationIdLike(new Long(bnbLocation.getKeyValue().getKey().toString()));
		}
		if(cbRecommendation.getSelectedIndex() >0){
			KeyValue keyResult = (KeyValue)cbRecommendation.getModel().getElementAt(cbRecommendation.getSelectedIndex());
			criteria = criteria.andRecommendationLike("%"+keyResult.getDescription()+"%");
		}
		criteria = criteria.andBranchIdLike(assetTransferDTO.getBranchOrigin());
		criteria = criteria.andRetiredFlagLike("N");
		
		criteria = criteria.andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		example.setOrderByClause(orderBy);
		return example;
    }
	
	private void generateDataAndPaging() {
		List listData = assetTransferNonEBSService.getAssetSODtlByCriteria(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize(),action);
		for(int i=0;i<listData.size();i++){
			AssetTransferDetailDTO assetTransferDetailDTO = (AssetTransferDetailDTO)listData.get(i);
			AssetTransferDetailDTO assetTransferDetailDTOTemp = assetTransferNonEBSService.getSOResultByAssetId(assetTransferDetailDTO.getAssetId());
			if(assetTransferDetailDTOTemp!=null){
			assetTransferDetailDTO.setStockOpnameSugest(assetTransferDetailDTOTemp.getStockOpnameSugest());
			assetTransferDetailDTO.setStockOpnameResult(assetTransferDetailDTOTemp.getStockOpnameResult());
			assetTransferDetailDTO.setStockOpnameHoSugest(assetTransferDetailDTOTemp.getStockOpnameHoSugest());
			assetTransferDetailDTO.setStockOpnameDtlId(assetTransferDetailDTOTemp.getStockOpnameDtlId());
			}
		}
		
		ListModelList<AssetTransferDetailDTO> model = new ListModelList<AssetTransferDetailDTO>(listData);
		List list = new ArrayList<AssetTransferDetailDTO>();
		for(int i=0;i<listData.size();i++){
			AssetTransferDetailDTO data = (AssetTransferDetailDTO)listData.get(i);
			if(listAssetTransferDtlTmp!=null && listAssetTransferDtlTmp.size()>0&& listAssetTransferDtlTmp.get(data.getAssetId())!=null){
				list.add(data);
			}
		}
		model.setMultiple(true);
		model.setSelection(list);
		lbxAssetPopup.setModel(model);
		lbxAssetPopup.renderAll();
			
		}
	
	@Listen("onClick = #btnSave")
	public void saveAsset() {
		//clear();
		winAssetTransferPopup.detach();
		List<AssetTransferDetailDTO>	data = new ArrayList<AssetTransferDetailDTO>();
		if(source!=null){
			/*
			ListModel<AssetTransferDetailDTO> model = lbxAssetPopup.getListModel();
			ListModelList<AssetTransferDetailDTO> list = (ListModelList<AssetTransferDetailDTO>)model;
			int i=0;
			for(Iterator iterator=list.getSelection().iterator(); list.getSelection().iterator().hasNext();){
				AssetTransferDetailDTO asset = (AssetTransferDetailDTO)iterator.next();
				data.add(asset);
				i++;
				if(i == list.getSelection().size()){
					break;
				}
			}
			*/
			List<AssetTransferDetailDTO> list = new ArrayList<AssetTransferDetailDTO>(listAssetTransferDtlTmp.values());
			Events.postEvent(new Event(Events.ON_CLOSE,source,list));
		}
		getSelf().detach();
	}
	

	private void loadLocation(SSOACommonPopupBandbox bandbox) {
		bandbox.setSearchDelegate(new DelegateSearchLocation());
	}
	
	class DelegateSearchLocation implements SerializableSearchDelegateDoubleCriteria<SSOALocation> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<SSOALocation> findBySearchCriteria(String searchCriteriaCode,String searchCriteriaName, int limit, int offset) {
			AssetTransferDTO assetTransferDTO = (AssetTransferDTO) arg.get("branchId");
			
			System.out.println("branch id" + assetTransferDTO.getBranchOrigin());
			SSOALocationExample example = new SSOALocationExample();
			example.setBranchId(assetTransferDTO.getBranchOrigin());
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andLocationNameLike(searchCriteriaName).andLocationCodeLike(searchCriteriaCode);
			
			List<SSOALocation> listLocation = new ArrayList<SSOALocation>();
			listLocation = assetTransferNonEBSService.getLocationByExample(example, limit, offset);
			return listLocation;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			AssetTransferDTO assetTransferDTO = (AssetTransferDTO) arg.get("branchId");
			SSOALocationExample example = new SSOALocationExample();
			example.setBranchId(assetTransferDTO.getBranchId());
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andLocationNameLike(searchCriteriaName).andLocationCodeLike(searchCriteriaCode);
			return assetTransferNonEBSService.countLocationByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, SSOALocation data) {
			keyValue.setKey(data.getLocationId());
			keyValue.setValue(data.getLocationName());
			keyValue.setDescription(data.getLocationCode());
		}
	}
	
	@Listen("onClick = #btnCancel")
	public void modalResult(Event e) {
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
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
}