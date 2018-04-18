package co.id.fifgroup.ssoa.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.exporter.excel.ExcelExporter;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.poi.ss.usermodel.Cell;
import org.zkoss.poi.ss.usermodel.Row;
import org.zkoss.poi.xssf.usermodel.XSSFSheet;
import org.zkoss.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.SOPeriodExample;
import co.id.fifgroup.ssoa.domain.AssetSyncronizingExample;
import co.id.fifgroup.ssoa.domain.AssetSyncronizingExample.Criteria;
import co.id.fifgroup.ssoa.dto.AssetDTO;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDTO;
import co.id.fifgroup.ssoa.service.AssetSynchronizingService;
import co.id.fifgroup.ssoa.service.EbsToSsoaService;
import co.id.fifgroup.ssoa.service.SystemPropertyService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetSynchronizingComposer extends SelectorComposer<Component> {
	// omit codes to get components
	// ClientService clientServices = new ClientService();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@WireVariable(rewireOnActivate = true)
	private transient AssetSynchronizingService assetSynchronizingService;
	
	@WireVariable(rewireOnActivate = true)
	private transient EbsToSsoaService ebsToSsoaService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@Wire
	private Datebox dtSearchProcessDateFrom;
	@Wire
	private Datebox dtSearchProcessDateTo;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Paging pgListOfValueTop;
	@Wire
	private Textbox txtSearchDescription;
	@Wire
	private Listbox cmbStatus;
	@Wire
	private Listbox lbxAssetSynchronizings;
	@Wire
	private Listbox lbxAssetSyncDownload;
	@Wire
	private Listbox lbxFormatDownload;
	@Wire
	private Combobox cmbDownloadAs;

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
	private Listheader hdrSyncDate;
	@Wire
	private Listheader hdrSyncType;
	@Wire
	private Listheader hdrDesc;
	@Wire
	private Listheader hdrStatus;
	@Wire
	private Listheader hdrErrLog;
	@Wire
	private Listheader hdrLastUpdateBy;
	@Wire
	private Listheader hdrLastUpdateDate;
	

	@Wire
	private Button searchButton;
	@Wire
	private Button backButton;
	@Wire
	private Listbox assetSynchronizingListbox;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private String mode;

	private List<AssetSynchronizingDTO> listAssetSynchronizingDto;
	private List<AssetSynchronizingDTO> listAssetSycnForDownload;
	private ListModelList<AssetSynchronizingDTO> listModelAssetSynchronizingDto;

	private FunctionPermission functionPermission;
	private String orderBy = "LAST_UPDATE_DATE DESC";

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
		setComboBoxList();
	}
	
	private void sortAction(){
		hdrSyncDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "PROCESS_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrSyncType.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(SELECT DESCRIPTION from BSE_LOOKUP_DEPENDENTS WHERE LOOKUP_ID = SYNC_TYPE_ID  and DETAIL_CODE = SYNC_TYPE_CODE )"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrDesc.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "PROCESS_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStatus.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(SELECT DESCRIPTION from BSE_LOOKUP_DEPENDENTS WHERE LOOKUP_ID = STATUS_ID  and DETAIL_CODE = STATUS_CODE )"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrErrLog.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "PROCESS_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLastUpdateBy.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "LAST_UPDATE_BY"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLastUpdateDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "LAST_UPDATE_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
	}

	private void initFunctionSecurity() {
		if (!functionPermission.isCreateable()) {
		}
	}

	private void init() {
		listModelAssetSynchronizingDto = new ListModelList<AssetSynchronizingDTO>();
		assetSynchronizingListbox.setModel(listModelAssetSynchronizingDto);
	}

	@Listen("onClick = button#btnFind")
	public void onFind() {
		System.out.println("msk onFind");
		Date startDate = dtSearchProcessDateFrom.getValue();
		Date endDate = dtSearchProcessDateTo.getValue();
		int status = cmbStatus.getSelectedIndex();
		
		if (startDate == null && endDate == null && status == 0) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"),
					Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 5439527390796683627L;

						@Override
						public void onEvent(Event event) throws Exception {
							int resultButton = (int) event.getData();
							if (resultButton == Messagebox.YES) {
								search();
								sortAction();
							}
						}
					});
		} else {
			search();
		}
	}

	@Listen("onDetail=#winAssetSynchronizing")
	public void onDetail(ForwardEvent event) {
		System.out.println("msk onDetail" + assetSynchronizingService);
		AssetSynchronizingDTO assetSynchronizingDTO = (AssetSynchronizingDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetSynchronizingDTO", assetSynchronizingDTO);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/ssoa/asset_synchronizing_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

	@Listen("onOpenErrorLog=#winAssetSynchronizing")
	public void openErrorLog(ForwardEvent event) {
		
		try {
			AssetSynchronizingDTO assetSynchronizingDTO = 
				assetSynchronizingService.getAssetSynchronizingDtoById(((AssetSynchronizingDTO) event.getData()).getId());
			
			if(assetSynchronizingDTO.getErrorLog() != null) {        	
				byte[] xls = assetSynchronizingDTO.getErrorLog();	            
				Filedownload.save(xls, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "AssetSync.xlsx");      
			}
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
		
	}
	
	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue all = new KeyValue();
		all.setKey("");
		all.setValue("%%");
		all.setDescription("--Select--");
		data.add(all);
		data.addAll(assetSynchronizingService.getStatusAssetSync());
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);
		cmbStatus.setModel(model);
		cmbStatus.renderAll();
		cmbStatus.setSelectedIndex(0);
	}
	
	 private AssetSyncronizingExample searchCriteria(){
		 	AssetSyncronizingExample example = new AssetSyncronizingExample();
	    	Criteria criteria = example.createCriteria();
			if(dtSearchProcessDateFrom.getValue() != null){
				criteria = criteria.andStartDateGreaterThanOrEqualTo(dtSearchProcessDateFrom.getValue());
			}
			
			if(dtSearchProcessDateTo.getValue() != null){
				criteria = criteria.andStartDateLessThanOrEqualTo(dtSearchProcessDateTo.getValue());
			}
			
			if(cmbStatus.getSelectedCount() > 0){
				KeyValue keyResult = (KeyValue)cmbStatus.getModel().getElementAt(cmbStatus.getSelectedIndex());
				criteria = criteria.andStatusLike(keyResult.getValue().toString());
			}
			
			example.setOrderByClause(orderBy);
			return example;
	    }
	    
	    private void search() {
			try {
				
				pgListOfValue.setTotalSize(assetSynchronizingService.getCountAssetSynchronizingDto(searchCriteria()));
				pgListOfValue.setPageSize(20);
				pgListOfValue.setActivePage(0);
				
				pgListOfValueTop.setTotalSize(pgListOfValue.getTotalSize());
				pgListOfValueTop.setPageSize(20);
				pgListOfValueTop.setActivePage(0);
				
				generateDataAndPaging();
				//generateDataForDownload();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	    
	    @Listen("onPaging = #pgListOfValue")
		public void onPaging() {
			generateDataAndPaging();
		}
	    
	    private void generateDataAndPaging() {
	    	pgListOfValueTop.setActivePage(pgListOfValue.getActivePage());
	    	List<AssetSynchronizingDTO> listAssetSynchronizingDto = assetSynchronizingService.getAssetSynchronizingDto(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());		
			ListModelList<AssetSynchronizingDTO> model = new ListModelList<AssetSynchronizingDTO>(listAssetSynchronizingDto);
			assetSynchronizingListbox.setModel(model);
			assetSynchronizingListbox.renderAll();
		}
	    
	    @Listen("onPaging = #pgListOfValueTop")
		public void onPagingTop() {
			generateDataAndPagingTop();
		}
	    
	    private void generateDataAndPagingTop() {
	    	pgListOfValue.setActivePage(pgListOfValueTop.getActivePage());
	    	List<AssetSynchronizingDTO> listAssetSynchronizingDto = assetSynchronizingService.getAssetSynchronizingDto(searchCriteria(),pgListOfValueTop.getPageSize(), pgListOfValueTop.getActivePage() * pgListOfValueTop.getPageSize());		
			ListModelList<AssetSynchronizingDTO> model = new ListModelList<AssetSynchronizingDTO>(listAssetSynchronizingDto);
			assetSynchronizingListbox.setModel(model);
			assetSynchronizingListbox.renderAll();
		}
	    
	    private void generateDataForDownload(){
	    	listAssetSycnForDownload = assetSynchronizingService.getAssetSynchronizingDto(searchCriteria());
			ListModelList<AssetSynchronizingDTO> model2 = new ListModelList<AssetSynchronizingDTO>(listAssetSycnForDownload);
			lbxAssetSyncDownload.setModel(model2);
			lbxAssetSyncDownload.renderAll();
	    }
	    
	    @Listen("onClick = #btnDownload")
		public void export() throws Exception {
	    	generateDataForDownload();
			Listbox listbox = lbxAssetSyncDownload;
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
}
