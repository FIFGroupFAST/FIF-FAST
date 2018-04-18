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
import co.id.fifgroup.ssoa.domain.RV;
import co.id.fifgroup.ssoa.domain.RVExample;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample.Criteria;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementRVDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetImgDTO;
import co.id.fifgroup.ssoa.service.RetirementService;
import co.id.fifgroup.ssoa.service.StockOpnameService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetRetirementRVPopupComposer extends SelectorComposer<Window> {

	@Wire
	private Button btnSavePopup;
	@Wire
	private Textbox txtRvNumber;
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
	@WireVariable
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient RetirementService retirementService;
	@WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;
	@Wire
	private Paging pgListOfValue;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	private Map<String, RV> listRVTmp = new HashMap<String, RV>();
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
		//sortAction();
		clear();
	}

	private void init() {
		source = arg.get("source") == null ? null : (Listbox) arg.get("source");
		action = arg.get("action") == null ? null : (String) arg.get("action"); 
		//data = arg.get("data") == null ? null : (List<Assets>) arg.get("data");
		initListModelPerson();
	}
	
	
	
	

	private void initListModelPerson() {
		lstAssetPopup.addEventListener(Events.ON_SELECT, new EventListener<SelectEvent<Listitem, RV>>() {
			
		    public void onEvent(SelectEvent<Listitem, RV> event) {
		        // Now you can access the details of the selection event..
		    	//listRetirementDtlTmp = new ArrayList<RetirementDetailDTO>();
		    	System.out.println("uselected=="+event.getUnselectedItems().size());
		    	
		    	int i=0;
				for(Iterator iterator=event.getSelectedItems().iterator(); event.getSelectedItems().iterator().hasNext();){
					Listitem itemRetirementDetailDTO = (Listitem)iterator.next();
					
					RV rv = (RV)itemRetirementDetailDTO.getValue();
					listRVTmp.put(rv.getRvNo(),rv);
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
					RV retirementDetailDTO = (RV)itemRetirementDetailDTO.getValue();
					listRVTmp.remove(retirementDetailDTO.getRvNo());
					x++;
					if(x == event.getUnselectedItems().size()){
						break;
					}
				}
				System.out.println(listRVTmp.size());
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
		
		//pgListOfValue.setTotalSize(retirementService.countRVByCriteria(searchCriteria(),action));
		pgListOfValue.setTotalSize(1000);
		pgListOfValue.setPageSize(10);
		pgListOfValue.setActivePage(0);
		generateDataAndPaging();
	}
	
	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		generateDataAndPaging();
	}
	
	private RVExample searchCriteria(){
		RetirementDTO retirementDTO = (RetirementDTO) arg.get("Branch");
		RVExample example = new RVExample();
    	co.id.fifgroup.ssoa.domain.RVExample.Criteria criteria = example.createCriteria();
    	if(!txtRvNumber.getValue().isEmpty()){
			criteria = criteria.andRVNoLike("%"+txtRvNumber.getValue().toUpperCase()+"%");
		}else{
			criteria = criteria.andRVNoLike("%");
		}
    	example.setBranchId(retirementDTO.getBranchId());
    	example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		example.setOrderByClause(orderBy);
		
		return example;
    }
	private void generateDataAndPaging() {	
	List listData = retirementService.getRVByCriteria(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize(),action);
	ListModelList<RV> model = new ListModelList<RV>(listData);
	List list = new ArrayList<RV>();
	for(int i=0;i<listData.size();i++){
		RV data = (RV)listData.get(i);
		if(listRVTmp!=null && listRVTmp.size()>0&& listRVTmp.get(data.getRvNo())!=null){
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
		List<RV>	data = new ArrayList<RV>();
		if(source!=null){
			
			List<RV> list = new ArrayList<RV>(listRVTmp.values());
			List<RetirementRVDTO> listNew = new ArrayList<RetirementRVDTO>();
			for(int i=0;i<list.size();i++){
				RetirementRVDTO retirementRVDTO = new RetirementRVDTO();
				RV rv = (RV)list.get(i);
				retirementRVDTO.setRvNo(rv.getRvNo());
				retirementRVDTO.setDate(rv.getDate());
				retirementRVDTO.setDescription(rv.getDescription());
				retirementRVDTO.setTotal(rv.getTotal());
				listNew.add(retirementRVDTO);
			}
			Events.postEvent(new Event(Events.ON_CLOSE,source,listNew));
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