package co.id.fifgroup.ssoa.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import co.id.fifgroup.ssoa.domain.AssetsExample.Criteria;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.exporter.excel.ExcelExporter;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
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
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.controller.AssetTransferComposer.DelegateSearch;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.ssoa.dto.AssetDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.service.AssetTransferService;
import co.id.fifgroup.ssoa.service.AssetsService;
import co.id.fifgroup.ssoa.service.RetirementService;
import co.id.fifgroup.workstructure.service.LocationSetupService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetComposer extends SelectorComposer<Window> {
	private static Logger log = LoggerFactory.getLogger(AssetComposer.class);
	
	@Wire
	private Window winAsset;
	@WireVariable(rewireOnActivate = true)
	private transient AssetTransferService assetTransferService;
	@Wire
	private SSOACommonPopupBandbox bnbBranch;
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
	private Listbox lbxAsset;
	@Wire
	private Listbox lbxAssetDownload;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Paging pgListOfValueTop;
	
	private List<AssetDTO> listAssetDtoForDownload;
	
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	private static final long serialVersionUID = 1L;
	@WireVariable(rewireOnActivate = true)
	private transient AssetsService assetsService;
	@WireVariable(rewireOnActivate = true)
	private transient RetirementService retirementService;
	
	@Wire
	private ListModelList<Assets> listModelAsset;
	@Wire
	private Combobox cmbDownloadAs;

	private List<Assets> listAssets;
	private Listbox source;
	//private StockOpnameUnregAssetsDTO selected;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Textbox txtAssetNumber;
	@Wire
	private Textbox txtDescription;
	private FunctionPermission functionPermission;
	
	private String orderBy = "ASSET_NUMBER ASC";
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		
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
				orderBy = "(select segment1 from SOC_EBS_CATEGORIES where category_id =A.CATEGORY_ID)"+(se.isAscending()?" ASC":" DESC");
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
		
		hdrStockOpnameResult.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(select DISTINCT DESCRIPTION from BSE_LOOKUP_DEPENDENTS LD inner join SOC_STOCK_OPNAME_DTL SO on SO.OPNAME_RESULT_CODE= LD.DETAIL_CODE where  SO.ASSET_ID = A.ASSET_ID)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStockOpnameSugest.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(select DISTINCT DESCRIPTION from BSE_LOOKUP_DEPENDENTS LD inner join SOC_STOCK_OPNAME_DTL SO on SO.OPNAME_SUGEST_CODE= LD.DETAIL_CODE where  SO.ASSET_ID = A.ASSET_ID)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		
		
	}
	
	
	@Listen("onClick = #btnFind")
	public void searchAsset(){
		
		String assetNumber = txtAssetNumber.getValue();
		String description = txtDescription.getValue();
		String branch = bnbBranch.getValue();
		if((assetNumber == null || assetNumber.isEmpty() || assetNumber.equals("%%")) && (description == null || description.isEmpty()|| description.equals("%%")) && (branch == null || branch.isEmpty())) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = -8756250972566999901L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						search();
						sortAction();
					}
				}
			});
		} else {
			search();
		}
	}
	
	private void search(){
		try {
			pgListOfValue.setTotalSize(assetsService.countAssetByCriteria(searchCriteria()));
			pgListOfValue.setPageSize(20);
			pgListOfValue.setActivePage(0);
			
			pgListOfValueTop.setTotalSize(pgListOfValue.getTotalSize());
			pgListOfValueTop.setPageSize(20);
			pgListOfValueTop.setActivePage(0);
			
			generateDataAndPaging();
		//generateDataForDownload();
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		generateDataAndPaging();
	}
	
	private AssetsExample searchCriteria(){
		AssetsExample example = new AssetsExample();
    	Criteria criteria = example.createCriteria();
    	if(!txtAssetNumber.getValue().isEmpty()){
			criteria = criteria.andAssetNumberLike("%"+ txtAssetNumber.getValue() +"%");
		}
		if(!txtDescription.getValue().isEmpty()){
			criteria = criteria.andDescriptionLike("%"+ txtDescription.getValue() +"%");
		}
		
		if(bnbBranch.getKeyValue()!=null && bnbBranch.getKeyValue().getKey() != null){
			criteria = criteria.andBranchIdLike(bnbBranch.getKeyValue().getKey().toString());
		}else{
			criteria = criteria.andBranchIdLike("%");
		}
		
		criteria = criteria.andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		criteria = criteria.andRetiredFlagLike("N");
		example.setOrderByClause(orderBy);
		return example;
    }
	
	private void generateDataAndPaging() {
		pgListOfValueTop.setActivePage(pgListOfValue.getActivePage());
		List listData = assetsService.getAssetByCriteria(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
		for(int i=0;i<listData.size();i++){
			Assets assetDTO = (Assets)listData.get(i);
			RetirementDetailDTO retirementDetailDTOTmp = retirementService.getSOResultByAssetId(assetDTO.getId());
			if(retirementDetailDTOTmp!=null){
				assetDTO.setStockOpnameSugest(retirementDetailDTOTmp.getStockOpnameSugest());
				assetDTO.setStockOpnameResult(retirementDetailDTOTmp.getStockOpnameResult());
				assetDTO.setStockOpnameHoSugest(retirementDetailDTOTmp.getStockOpnameHoSugest());
				assetDTO.setStockOpnameDtlId(retirementDetailDTOTmp.getStockOpnameDtlId());
			}
		}
		ListModelList<AssetDTO> model = new ListModelList<AssetDTO>(listData);
		lbxAsset.setModel(model);
		lbxAsset.renderAll();
		}
	
	@Listen("onPaging = #pgListOfValueTop")
	public void onPagingTop() {
		generateDataAndPagingTop();
	}
	
	private void generateDataAndPagingTop() {
		pgListOfValue.setActivePage(pgListOfValueTop.getActivePage());
		List listData = assetsService.getAssetByCriteria(searchCriteria(),pgListOfValueTop.getPageSize(), pgListOfValueTop.getActivePage() * pgListOfValueTop.getPageSize());
		for(int i=0;i<listData.size();i++){
			Assets assetDTO = (Assets)listData.get(i);
			RetirementDetailDTO retirementDetailDTOTmp = retirementService.getSOResultByAssetId(assetDTO.getId());
			if(retirementDetailDTOTmp!=null){
				assetDTO.setStockOpnameSugest(retirementDetailDTOTmp.getStockOpnameSugest());
				assetDTO.setStockOpnameResult(retirementDetailDTOTmp.getStockOpnameResult());
				assetDTO.setStockOpnameHoSugest(retirementDetailDTOTmp.getStockOpnameHoSugest());
				assetDTO.setStockOpnameDtlId(retirementDetailDTOTmp.getStockOpnameDtlId());
			}
		}
		ListModelList<AssetDTO> model = new ListModelList<AssetDTO>(listData);
		lbxAsset.setModel(model);
		lbxAsset.renderAll();
	}
	
	private void generateDataForDownload(){
		listAssetDtoForDownload = assetsService.getAssetByCriteria(searchCriteria());
		for(int i=0;i<listAssetDtoForDownload.size();i++){
			Assets assetDTO = (Assets)listAssetDtoForDownload.get(i);
			RetirementDetailDTO retirementDetailDTOTmp = retirementService.getSOResultByAssetId(assetDTO.getId());
			if(retirementDetailDTOTmp!=null){
				assetDTO.setStockOpnameSugest(retirementDetailDTOTmp.getStockOpnameSugest());
				assetDTO.setStockOpnameResult(retirementDetailDTOTmp.getStockOpnameResult());
				assetDTO.setStockOpnameHoSugest(retirementDetailDTOTmp.getStockOpnameHoSugest());
				assetDTO.setStockOpnameDtlId(retirementDetailDTOTmp.getStockOpnameDtlId());
			}
		}
		ListModelList<AssetDTO> model2 = new ListModelList<AssetDTO>(listAssetDtoForDownload);
		lbxAssetDownload.setModel(model2);
		lbxAssetDownload.renderAll();
    }
	
	@Listen("onClick = #btnDownload")
	public void export() throws Exception {
    	generateDataForDownload();
		Listbox listbox = lbxAssetDownload;
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
	
	private void init() {
		loadBranch(bnbBranch);
		Branch branch = (Branch) assetTransferService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(securityServiceImpl.getSecurityProfile().getBranchId().longValue() != -1)
		{
			if(branch!=null && branch.getBranchCode()!=null){
				KeyValue keyValue = new KeyValue();
				keyValue.setKey(branch.getBranchId());
				keyValue.setValue(branch.getBranchName());
				keyValue.setDescription(branch.getBranchCode());
				bnbBranch.setRawValue(keyValue);
			}
			bnbBranch.setDisabled(true);
		}else{		
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(branch.getBranchId());
			keyValue.setValue(branch.getBranchName());
			keyValue.setDescription(branch.getBranchCode());
			bnbBranch.setRawValue(keyValue);
		}
		try{
			bnbBranch.addEventListener(Events.ON_FOCUS, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					if(!bnbBranch.getValue().isEmpty()){
						KeyValue keyValue = (KeyValue)bnbBranch.getKeyValue();
						Long  branchIdLogin= securityServiceImpl.getSecurityProfile().getBranchId().longValue();
						System.out.println("bnbBranch.getKeyValue().getValue()" + bnbBranch.getKeyValue().getValue());
						if(branchIdLogin == bnbBranch.getKeyValue().getKey()){
							}
						}
					else
					{
						Branch branch = (Branch) assetTransferService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
						
					}
				}
			});
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			listBranch = assetTransferService.getBranchByExample(example, limit, offset);

			return listBranch;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			return assetTransferService.countBranchByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Branch data) {
			keyValue.setKey(data.getBranchId());
			keyValue.setValue(data.getBranchName());
			keyValue.setDescription(data.getBranchCode());
		}
	}
	
}