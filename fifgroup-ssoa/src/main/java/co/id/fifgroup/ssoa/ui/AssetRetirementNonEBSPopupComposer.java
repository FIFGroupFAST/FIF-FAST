package co.id.fifgroup.ssoa.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.zkoss.zul.Button;
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

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.constants.RetirementType;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample.Criteria;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetImgDTO;
import co.id.fifgroup.ssoa.service.RetirementNonEBSService;
import co.id.fifgroup.ssoa.service.StockOpnameService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetRetirementNonEBSPopupComposer extends SelectorComposer<Window> {

	@Wire
	private Button btnSavePopup;
	@Wire
	private Textbox txtDescription;
	@Wire
	private Datebox dtDateStart;
	@Wire
	private Datebox dtDateEnd;
	@Wire
	private Listbox lstAssetPopup;
	@Wire
	private Window winPopup;
	@Wire
	private Listbox cbAssetType;
	@Wire
	private Listbox cbRecommendation;
	
	private Listbox source;
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
	@WireVariable
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient RetirementNonEBSService retirementNonEBSService;
	@WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;
	@Wire
	private Paging pgListOfValue;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	private Map<Long, RetirementDetailDTO> listRetirementDtlTmp = new HashMap<Long, RetirementDetailDTO>();
	//private List<RetirementDetailDTO> listRetirementDtlTmp = new ArrayList<RetirementDetailDTO>();;
	private String action;
	
	private static final long serialVersionUID = 1L;
	
	private String orderBy = "ASSET_NUMBER ASC";
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		try {
			super.doAfterCompose(comp);
		}catch (Exception e) {
			e.printStackTrace();
		}

		init();
		sortAction();
		clear();
	}

	private void init() {
		source = arg.get("source") == null ? null : (Listbox) arg.get("source");
		action = arg.get("action") == null ? null : (String) arg.get("action"); 
		//data = arg.get("data") == null ? null : (List<Assets>) arg.get("data");
		initListModelPerson();
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
	
	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue key = new KeyValue();
		key.setKey("%");
		key.setValue("%");
		key.setDescription("--Pilih--");
		data.add(key);
		KeyValue key2 = new KeyValue();
		key2.setKey(SSOAConstants.assetTypeLVA);
		key2.setValue(SSOAConstants.assetTypeLVA);
		key2.setDescription(SSOAConstants.assetTypeLVA);
		data.add(key2);
		KeyValue key3 = new KeyValue();
		key3.setKey(SSOAConstants.assetTypeAST);
		key3.setValue(SSOAConstants.assetTypeAST);
		key3.setDescription(SSOAConstants.assetTypeAST);
		data.add(key3);
		
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);
		cbAssetType.setModel(model);
		cbAssetType.renderAll();
		cbAssetType.setSelectedIndex(0);
		
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

	private void initListModelPerson() {
		lstAssetPopup.addEventListener(Events.ON_SELECT, new EventListener<SelectEvent<Listitem, RetirementDetailDTO>>() {
			
		    public void onEvent(SelectEvent<Listitem, RetirementDetailDTO> event) {
		        // Now you can access the details of the selection event..
		    	//listRetirementDtlTmp = new ArrayList<RetirementDetailDTO>();
		    	System.out.println("uselected=="+event.getUnselectedItems().size());
		    	
		    	int i=0;
				for(Iterator iterator=event.getSelectedItems().iterator(); event.getSelectedItems().iterator().hasNext();){
					Listitem itemRetirementDetailDTO = (Listitem)iterator.next();
					/*if(i==0){
						RetirementDetailDTO retirementDetailDTO = (RetirementDetailDTO)itemRetirementDetailDTO.getValue();
						listRetirementDtlTmp.put(retirementDetailDTO.getAssetId(),retirementDetailDTO);
						System.out.println("id=="+retirementDetailDTO.getAssetId());
					}*/
					RetirementDetailDTO retirementDetailDTO = (RetirementDetailDTO)itemRetirementDetailDTO.getValue();
					listRetirementDtlTmp.put(retirementDetailDTO.getAssetId(),retirementDetailDTO);
					i++;
					if(i == event.getSelectedItems().size()){
						break;
					}
				}
				int x=0;
				for(Iterator iterator=event.getUnselectedItems().iterator(); event.getUnselectedItems().iterator().hasNext();){
					Listitem itemRetirementDetailDTO = (Listitem)iterator.next();
					/*if(x==0){
						RetirementDetailDTO retirementDetailDTO = (RetirementDetailDTO)itemRetirementDetailDTO.getValue();
						listRetirementDtlTmp.remove(retirementDetailDTO.getAssetId());
						
					}*/
					RetirementDetailDTO retirementDetailDTO = (RetirementDetailDTO)itemRetirementDetailDTO.getValue();
					listRetirementDtlTmp.remove(retirementDetailDTO.getAssetId());
					x++;
					if(x == event.getUnselectedItems().size()){
						break;
					}
				}
				System.out.println(listRetirementDtlTmp.size());
		    }
		});
		/*lstAssetPopup.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				
				if(event.getData()!=null){
					RetirementDetailDTO retirementDetailDTO = (RetirementDetailDTO)event.getData();
					listRetirementDtlTmp.put(retirementDetailDTO.getId(), retirementDetailDTO);
					
				}
			}
		});*/
	}

	public void clear() {
		//dtDateStart.setValue(null);
		//dtDateEnd.setValue(null);
	}

	@Listen("onClick = #btnFindAdd")
	public void searchAsset(){
		
		pgListOfValue.setTotalSize(retirementNonEBSService.countAssetSODtlByCriteria(searchCriteria(),action));
		pgListOfValue.setPageSize(5);
		pgListOfValue.setActivePage(0);
		generateDataAndPaging();
	}
	
	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		generateDataAndPaging();
	}
	
	private RetirementDetailExample searchCriteria(){
		RetirementDTO retirementDTO = (RetirementDTO) arg.get("Branch");
		RetirementDetailExample example = new RetirementDetailExample();
    	Criteria criteria = example.createCriteria();
		if(!txtDescription.getValue().isEmpty()){
			criteria = criteria.andDescriptionLike("%"+txtDescription.getValue()+"%");
		}
		if(cbAssetType.getSelectedIndex() >0){
			KeyValue keyResult = (KeyValue)cbAssetType.getModel().getElementAt(cbAssetType.getSelectedIndex());
			criteria = criteria.andAssetTypeLike("%"+keyResult.getValue()+"%");
		}
		if(cbRecommendation.getSelectedIndex() >0){
			KeyValue keyResult = (KeyValue)cbRecommendation.getModel().getElementAt(cbRecommendation.getSelectedIndex());
			criteria = criteria.andRecommendationLike("%"+keyResult.getDescription()+"%");
		}
		/*if(dtDateStart.getValue()!=null){
			criteria = criteria.andDatePlaceInServiceGreaterThan(dtDateStart.getValue());
		}
		if(dtDateEnd.getValue()!=null){
			criteria = criteria.andDatePlaceInServiceLowerThan(dtDateEnd.getValue());
		}*/
		
		criteria = criteria.andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId().toString());
		criteria = criteria.andBranchIdLike(retirementDTO.getBranchId().toString());
		criteria = criteria.andRetiredFlagLike("N");
		example.setOrderByClause(orderBy);
		
		return example;
    }
	private void generateDataAndPaging() {	
	List listData = retirementNonEBSService.getAssetSODtlByCriteria(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize(),action);
	for(int i=0;i<listData.size();i++){
		RetirementDetailDTO retirementDetailDTO = (RetirementDetailDTO)listData.get(i);
		RetirementDetailDTO retirementDetailDTOTmp = retirementNonEBSService.getSOResultByAssetId(retirementDetailDTO.getAssetId());
		if(retirementDetailDTOTmp!=null){
		retirementDetailDTO.setStockOpnameSugest(retirementDetailDTOTmp.getStockOpnameSugest());
		retirementDetailDTO.setStockOpnameResult(retirementDetailDTOTmp.getStockOpnameResult());
		retirementDetailDTO.setStockOpnameHoSugest(retirementDetailDTOTmp.getStockOpnameHoSugest());
		retirementDetailDTO.setStockOpnameDtlId(retirementDetailDTOTmp.getStockOpnameDtlId());
		}
	}
	ListModelList<RetirementDetailDTO> model = new ListModelList<RetirementDetailDTO>(listData);
	//model.setSelection(listRetirementDtlTmp.values());
	List list = new ArrayList<RetirementDetailDTO>();
	for(int i=0;i<listData.size();i++){
		RetirementDetailDTO data = (RetirementDetailDTO)listData.get(i);
		if(listRetirementDtlTmp!=null && listRetirementDtlTmp.size()>0&& listRetirementDtlTmp.get(data.getAssetId())!=null){
			list.add(data);
		}
	}
	model.setMultiple(true);
	model.setSelection(list);
	lstAssetPopup.setModel(model);
	lstAssetPopup.renderAll();
		
	}

	@Listen("onClick = #btnSavePopup")
	public void saveAsset() {
		clear();
		winPopup.detach();
		List<RetirementDetailDTO>	data = new ArrayList<RetirementDetailDTO>();
		if(source!=null){
			/*ListModel<RetirementDetailDTO> model = lstAssetPopup.getListModel();
			ListModelList<RetirementDetailDTO> list = (ListModelList<RetirementDetailDTO>)model;
			int i=0;
			for(Iterator iterator=list.getSelection().iterator(); list.getSelection().iterator().hasNext();){
				RetirementDetailDTO asset = (RetirementDetailDTO)iterator.next();
				data.add(asset);
				i++;
				if(i == list.getSelection().size()){
					break;
				}
			}*/
			List<RetirementDetailDTO> list = new ArrayList<RetirementDetailDTO>(listRetirementDtlTmp.values());
			Events.postEvent(new Event(Events.ON_CLOSE,source,list));
		}
		getSelf().detach();
	}
	
	@Listen("onClick = #btnCancel")
	public void modalResult(Event e) {
		Messagebox.show("Are you sure want to cancel?", "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							winPopup.detach();
						}
					}
				});

	}
}