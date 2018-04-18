package co.id.fifgroup.ssoa.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
//import org.zkoss.bind.BindUtils;
//import org.zkoss.bind.Binder;
//import org.zkoss.bind.annotation.AfterCompose;
//import org.zkoss.bind.annotation.ContextParam;
//import org.zkoss.bind.annotation.ContextType;
//import org.zkoss.bind.annotation.ExecutionArgParam;
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.ssoa.domain.AssetLabelingDetailExample;
import co.id.fifgroup.ssoa.domain.AssetLabelingDetailExample.Criteria;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.domain.AssetTransferDetailExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.dto.AssetDTO;
import co.id.fifgroup.ssoa.dto.AssetLabelingDTO;
import co.id.fifgroup.ssoa.dto.AssetLabelingDetailDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.AssetLabelingDetailDTO;
import co.id.fifgroup.ssoa.service.AssetLabelingService;
import co.id.fifgroup.ssoa.service.AssetsService;
import co.id.fifgroup.ssoa.ui.AssetTransferPopupComposer.DelegateSearchLocation;
import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.workstructure.service.LocationSetupService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetLabelingPopupComposer extends SelectorComposer<Window> {
	private static Logger log = LoggerFactory.getLogger(AssetLabelingPopupComposer.class);

	private static final long serialVersionUID = 1L;
	
	@WireVariable(rewireOnActivate = true)
	private transient LocationSetupService locationSetupService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	private FunctionPermission functionPermission;
	@Wire
	private Listbox lbxAssetSearch;
	@Wire
	private SSOACommonPopupBandbox bnbLocation;
	@Wire
	private Datebox dtDateStart;
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
	private Datebox dtDateEnd;
	@Wire
	private Paging pgListOfValue;
	
	@Wire
	private Listbox cmbHasPrinted;
	
	@Wire
	private Window winAssetLabelingPopup;
	
	@Wire
	private Textbox assetNumber;
	
	private Listbox source;
	
	private Map<Long, AssetLabelingDetailDTO> listAssetLabelingDtlTmp = new HashMap<Long, AssetLabelingDetailDTO>();
	
	@WireVariable(rewireOnActivate = true)
	private transient AssetsService assetsService;
	@WireVariable(rewireOnActivate = true)
	private transient AssetLabelingService assetLabelingService;
	@WireVariable
	private Map<String, Object> arg;
	
	private ListModelList<AssetDTO> listModelAssetDto;
    private List<AssetDTO> assetDto;
    
    private String orderBy = "ASSET_NUMBER ASC";
	

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
		loadLocation(bnbLocation);
		setComboBoxList();
		sortAction();
	}

	private void init() {
		source = arg.get("source") == null ? null : (Listbox) arg.get("source");
		initListModelAsset();
	}
	
	private void sortAction(){
		hdrAssetNumber.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "A.ASSET_NUMBER"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
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

	private void initListModelAsset() {
		lbxAssetSearch.addEventListener(Events.ON_SELECT, new EventListener<SelectEvent<Listitem, AssetLabelingDetailDTO>>() {
			
			public void onEvent(SelectEvent<Listitem, AssetLabelingDetailDTO> event) {
		        // Now you can access the details of the selection event..
		    	//listAssetTransferDtlTmp = new ArrayList<AssetTransferDetailDTO>();
		    	System.out.println("uselected=="+event.getUnselectedItems().size());
		    	
		    	int i=0;
				for(Iterator iterator=event.getSelectedItems().iterator(); event.getSelectedItems().iterator().hasNext();){
					Listitem itemAssetLabelingDetailDTO = (Listitem)iterator.next();
					
					AssetLabelingDetailDTO assetLabelingDetailDTO = (AssetLabelingDetailDTO)itemAssetLabelingDetailDTO.getValue();
					listAssetLabelingDtlTmp.put(assetLabelingDetailDTO.getAssetId(),assetLabelingDetailDTO);
					i++;
					if(i == event.getSelectedItems().size()){
						break;
					}
				}
				int x=0;
				for(Iterator iterator=event.getUnselectedItems().iterator(); event.getUnselectedItems().iterator().hasNext();){
					Listitem itemAssetLabelingDetailDTO = (Listitem)iterator.next();
					
					AssetLabelingDetailDTO assetlabelingDetailDTO = (AssetLabelingDetailDTO)itemAssetLabelingDetailDTO.getValue();
					listAssetLabelingDtlTmp.remove(assetlabelingDetailDTO.getAssetId());
					x++;
					if(x == event.getUnselectedItems().size()){
						break;
					}
				}
				System.out.println(listAssetLabelingDtlTmp.size());
		    }
		});
	}
	
	@Listen("onClick = #btnSaveAsset")
	public void saveAsset() {
		winAssetLabelingPopup.detach();
		List<AssetLabelingDetailDTO>	data = new ArrayList<AssetLabelingDetailDTO>();
		if(source!=null){
			/*ListModel<AssetLabelingDetailDTO> model = lbxAssetSearch.getListModel();
			ListModelList<AssetLabelingDetailDTO> list = (ListModelList<AssetLabelingDetailDTO>)model;
			int i=0;
			for(Iterator iterator=list.getSelection().iterator(); list.getSelection().iterator().hasNext();){
				AssetLabelingDetailDTO asset = (AssetLabelingDetailDTO)iterator.next();
				data.add(asset);
				i++;
				if(i == list.getSelection().size()){
					break;
				}
			}
			*/
			List<AssetLabelingDetailDTO> list = new ArrayList<AssetLabelingDetailDTO>(listAssetLabelingDtlTmp.values());
			Events.postEvent(new Event(Events.ON_CLOSE,source,list));
			//Events.postEvent(new Event(Events.ON_CLOSE,source,data));
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
			AssetLabelingDTO selected = (AssetLabelingDTO) arg.get("branchId");
			System.out.println("branch Id" + selected);
			SSOALocationExample example = new SSOALocationExample();
			example.setBranchId(selected.getBranchId());
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andLocationNameLike(searchCriteriaName).andLocationCodeLike(searchCriteriaCode);
			
			List<SSOALocation> listLocation = new ArrayList<SSOALocation>();
			listLocation = assetLabelingService.getLocationByExample(example, limit, offset);
			return listLocation;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			AssetLabelingDTO selected = (AssetLabelingDTO) arg.get("branchId");
			
			System.out.println("branch Id" + selected);
			SSOALocationExample example = new SSOALocationExample();
			example.setBranchId(selected.getBranchId());
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andLocationNameLike(searchCriteriaName).andLocationCodeLike(searchCriteriaCode);
			return assetLabelingService.countLocationByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, SSOALocation data) {
			keyValue.setKey(data.getLocationId());
			keyValue.setValue(data.getLocationName());
			keyValue.setDescription(data.getLocationCode());
		}
	}
	
	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue all = new KeyValue();
			all.setKey("");
			all.setValue("");
			all.setDescription("--Select--");
		KeyValue all1 = new KeyValue();
			all1.setKey("0");
			all1.setValue("0");
			all1.setDescription("Yes");
		KeyValue all2 = new KeyValue();
			all2.setKey("0");
			all2.setValue("0");
			all2.setDescription("No");
		data.add(all);
		data.add(all1);
		data.add(all2);
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);
		cmbHasPrinted.setModel(model);
		cmbHasPrinted.renderAll();
		cmbHasPrinted.setSelectedIndex(0);
	}
	
	@Listen("onClick = #btnFindAdd")
	public void searchAsset(){
		pgListOfValue.setTotalSize(assetLabelingService.countAssetByCriteria(searchCriteria()));
		pgListOfValue.setPageSize(5);
		pgListOfValue.setActivePage(0);
		generateDataAndPaging();
	}
	
	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		generateDataAndPaging();
	}
	
	private AssetLabelingDetailExample searchCriteria(){
		AssetLabelingDTO assetLabelingDTO = (AssetLabelingDTO) arg.get("branchId");
		AssetLabelingDetailExample example = new AssetLabelingDetailExample();
    	Criteria criteria = example.createCriteria();
    	if(!assetNumber.getValue().isEmpty()){
			criteria = criteria.andAssetNumberLike("%"+ assetNumber.getValue() +"%");
		}
		if(dtDateStart.getValue()!= null ){
			criteria = criteria.andDatePlaceInServiceGreaterThan(dtDateStart.getValue());
		}
		if(dtDateEnd.getValue()!= null){
			criteria = criteria.andDatePlaceInServiceLessThan(dtDateEnd.getValue());
		}
		if(!bnbLocation.getValue().isEmpty()){
			criteria = criteria.andLocationIdLike(new Long(bnbLocation.getKeyValue().getKey().toString()));
		}
		
		if(cmbHasPrinted.getSelectedIndex() == 0){
		}
		else if(cmbHasPrinted.getSelectedIndex() == 1){
			KeyValue keyResult = (KeyValue)cmbHasPrinted.getModel().getElementAt(cmbHasPrinted.getSelectedIndex());
			criteria = criteria.andPrintedYesLike(new Long (keyResult.getValue().toString()));
		}
		else if(cmbHasPrinted.getSelectedIndex() == 2){
			KeyValue keyResult = (KeyValue)cmbHasPrinted.getModel().getElementAt(cmbHasPrinted.getSelectedIndex());
			criteria = criteria.andPrintedNoLike(new Long (keyResult.getValue().toString()));
		}
		criteria = criteria.andBranchIdLike(assetLabelingDTO.getBranchId());
		criteria = criteria.andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		criteria = criteria.andRetiredFlagLike("N");
		example.setOrderByClause(orderBy);
		return example;
    }
	
	private void generateDataAndPaging() {
		List listData = assetLabelingService.getAssetByCriteria(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
		ListModelList<AssetLabelingDetailDTO> model = new ListModelList<AssetLabelingDetailDTO>(listData);
		List list = new ArrayList<AssetLabelingDetailDTO>();
		for(int i=0;i<listData.size();i++){
			AssetLabelingDetailDTO data = (AssetLabelingDetailDTO)listData.get(i);
			if(listAssetLabelingDtlTmp!=null && listAssetLabelingDtlTmp.size()>0&& listAssetLabelingDtlTmp.get(data.getAssetId())!=null){
				list.add(data);
			}
		}
		model.setMultiple(true);
		model.setSelection(list);
		lbxAssetSearch.setModel(model);
		lbxAssetSearch.renderAll();
			
		}
	
}