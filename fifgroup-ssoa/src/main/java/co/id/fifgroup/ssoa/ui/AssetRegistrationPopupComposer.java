package co.id.fifgroup.ssoa.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.domain.AssetRegistrationDetailExample;
import co.id.fifgroup.ssoa.domain.AssetRegistrationDetailExample.Criteria;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDTO;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDetailDTO;
import co.id.fifgroup.ssoa.service.AssetRegistrationService;
import co.id.fifgroup.ssoa.service.StockOpnameService;
import co.id.fifgroup.workstructure.service.LocationSetupService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetRegistrationPopupComposer extends SelectorComposer<Window> {
	private static Logger log = LoggerFactory.getLogger(AssetRegistrationPopupComposer.class);
	@Wire
	private Label lblErrAssetRegistrationInfo;
	@Wire
	private Listbox lbxAssetPopup;
	@Wire
	private Window winAssetRegistrationPopup;
	@Wire
	private Listbox cbRecommendation;

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
	private transient AssetRegistrationService assetRegistrationService;
	@WireVariable(rewireOnActivate = true)
	private transient LocationSetupService locationSetupService;
	@Wire
	private SSOACommonPopupBandbox bnbLocation;
	private AssetRegistrationDTO selected;
	private Listbox source;
	@WireVariable("arg")
	private Map<String, Object> arg;

	private Map<Long, AssetRegistrationDetailDTO> listAssetRegistrationDtlTmp = new HashMap<Long, AssetRegistrationDetailDTO>();
	@Wire
	private Textbox txtDescription;
	@Wire
	private Paging pgListOfValue;
	private String action;
	private String orderBy = "A.STOCK_OPNAME_UNREG_ASSET_ID ASC";
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		try {
			super.doAfterCompose(comp);
		}catch (Exception e) {
			e.printStackTrace();
		}

		init();
		sortAction();
	}
	
	private void init() {
		source = arg.get("source") == null ? null : (Listbox) arg.get("source");
		action = arg.get("action") == null ? null : (String) arg.get("action");
		initListModelAsset();
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
		lbxAssetPopup.addEventListener(Events.ON_SELECT, new EventListener<SelectEvent<Listitem, AssetRegistrationDetailDTO>>() {
			
			public void onEvent(SelectEvent<Listitem, AssetRegistrationDetailDTO> event) {
		    	System.out.println("uselected=="+event.getUnselectedItems().size());
		    	
		    	int i=0;
				for(Iterator iterator=event.getSelectedItems().iterator(); event.getSelectedItems().iterator().hasNext();){
					Listitem itemAssetRegistrationDetailDTO = (Listitem)iterator.next();
					
					AssetRegistrationDetailDTO assetRegistrationDetailDTO = (AssetRegistrationDetailDTO)itemAssetRegistrationDetailDTO.getValue();
					listAssetRegistrationDtlTmp.put(assetRegistrationDetailDTO.getStockOpnameUnregAssetId(),assetRegistrationDetailDTO);
					i++;
					if(i == event.getSelectedItems().size()){
						break;
					}
				}
				int x=0;
				for(Iterator iterator=event.getUnselectedItems().iterator(); event.getUnselectedItems().iterator().hasNext();){
					Listitem itemAssetRegistrationDetailDTO = (Listitem)iterator.next();
					
					AssetRegistrationDetailDTO assetTransferDetailDTO = (AssetRegistrationDetailDTO)itemAssetRegistrationDetailDTO.getValue();
					listAssetRegistrationDtlTmp.remove(assetTransferDetailDTO.getStockOpnameUnregAssetId());
					x++;
					if(x == event.getUnselectedItems().size()){
						break;
					}
				}
				System.out.println(listAssetRegistrationDtlTmp.size());
		    }
		});
	}
	
	
	
	@Listen("onClick = #btnFindPopup")
	public void searchAsset(){
		pgListOfValue.setTotalSize(assetRegistrationService.countAssetSODtlByCriteria(searchCriteria()));
		pgListOfValue.setPageSize(5);
		pgListOfValue.setActivePage(0);
		generateDataAndPaging();
	}
	
	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		generateDataAndPaging();
	}
	
	private AssetRegistrationDetailExample searchCriteria(){
		AssetRegistrationDTO assetRegistrationDTO = (AssetRegistrationDTO) arg.get("branchId");
		System.out.println("branch Id..................." + assetRegistrationDTO.getBranchId());
		AssetRegistrationDetailExample example = new AssetRegistrationDetailExample();
    	Criteria criteria = example.createCriteria();    	
		if(!txtDescription.getValue().isEmpty()){
			criteria = criteria.andDescriptionLike("%"+ txtDescription.getValue() +"%");
		}

		criteria = criteria.andBranchIdLike(securityServiceImpl.getSecurityProfile().getBranchId());
		
		criteria = criteria.andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		example.setOrderByClause(orderBy);
		return example;
    }
	
	private void generateDataAndPaging() {
		List listData = assetRegistrationService.getAssetSODtlByCriteria(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize(),action);
		for(int i=0;i<listData.size();i++){
			AssetRegistrationDetailDTO assetRegistrationDetailDTO = (AssetRegistrationDetailDTO)listData.get(i);
			AssetRegistrationDetailDTO assetRegistrationDetailDTOTemp = assetRegistrationService.getSOResultByAssetId(assetRegistrationDetailDTO.getAssetId());
			if(assetRegistrationDetailDTOTemp!=null){
			assetRegistrationDetailDTO.setStockOpnameSugest(assetRegistrationDetailDTOTemp.getStockOpnameSugest());
			assetRegistrationDetailDTO.setStockOpnameResult(assetRegistrationDetailDTOTemp.getStockOpnameResult());
			assetRegistrationDetailDTO.setStockOpnameHoSugest(assetRegistrationDetailDTOTemp.getStockOpnameHoSugest());
			assetRegistrationDetailDTO.setStockOpnameDtlId(assetRegistrationDetailDTOTemp.getStockOpnameDtlId());
			}
		}
		
		ListModelList<AssetRegistrationDetailDTO> model = new ListModelList<AssetRegistrationDetailDTO>(listData);
		List list = new ArrayList<AssetRegistrationDetailDTO>();
		for(int i=0;i<listData.size();i++){
			AssetRegistrationDetailDTO data = (AssetRegistrationDetailDTO)listData.get(i);
			if(listAssetRegistrationDtlTmp!=null && listAssetRegistrationDtlTmp.size()>0&& listAssetRegistrationDtlTmp.get(data.getAssetId())!=null){
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
		winAssetRegistrationPopup.detach();
		List<AssetRegistrationDetailDTO>	data = new ArrayList<AssetRegistrationDetailDTO>();
		if(source!=null){
		
			List<AssetRegistrationDetailDTO> list = new ArrayList<AssetRegistrationDetailDTO>(listAssetRegistrationDtlTmp.values());
			Events.postEvent(new Event(Events.ON_CLOSE,source,list));
		}
		getSelf().detach();
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