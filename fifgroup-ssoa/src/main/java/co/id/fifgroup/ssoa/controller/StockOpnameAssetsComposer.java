package co.id.fifgroup.ssoa.controller;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.exolab.castor.dsml.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.exporter.excel.ExcelExporter;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.ssoa.domain.StockOpnameDetailExample;
import co.id.fifgroup.ssoa.domain.StockOpnameUnregAssetsExample;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.service.StockOpnameService;


@VariableResolver(DelegatingVariableResolver.class)
public class StockOpnameAssetsComposer extends  SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(StockOpnameAssetsComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@Wire
	private Window winStockOpnameAsset;
	
	private ListModelList<StockOpnameDetailDTO> lstStockOpnameDetail;
	private ListModelList<StockOpnameUnregAssetsDTO> lstStockOpnameUnreg;
	@Wire
	private Listbox assetsListbox;
	@Wire
	private Listbox assetsListboxForDownload;
	@Wire
	private Combobox cmbDownloadAs;
	@Wire
	private Paging pgListOfValue;
	private StockOpnameDTO selected;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		loadData(false);
	}
	
	private void loadData(Boolean isPaging) {
		
		
		
		selected = (StockOpnameDTO) arg.get("stockOpnameDTO");
		Boolean isReg = (Boolean) arg.get("isReg");
		Boolean isUnReg = (Boolean) arg.get("isUnReg");
		Boolean isNumberOfAssetReg = (Boolean) arg.get("isNumberOfAssetReg");
		Boolean isNumberOfProcessAssetReg = (Boolean) arg.get("isNumberOfProcessAssetReg");
		Boolean isNumberOfUnProcessAssetReg = (Boolean) arg.get("isNumberOfUnProcessAssetReg");
		Boolean isAssetStatusA = (Boolean) arg.get("isAssetStatusA");
		Boolean isAssetStatusB = (Boolean) arg.get("isAssetStatusB");
		Boolean isAssetStatusC = (Boolean) arg.get("isAssetStatusC");
		Boolean isAssetStatusD = (Boolean) arg.get("isAssetStatusD");
		List<StockOpnameDetailDTO> listData = new ArrayList<StockOpnameDetailDTO>();
		List<StockOpnameUnregAssetsDTO> listDataUnreg = new ArrayList<StockOpnameUnregAssetsDTO>();
		if (selected != null) {

			StockOpnameDetailExample example = new StockOpnameDetailExample();
			StockOpnameUnregAssetsExample exampleUnreg = new StockOpnameUnregAssetsExample();
			
			if (!isUnReg) {
				System.out.println("msk if");
				if (isReg && isNumberOfAssetReg != null && isNumberOfAssetReg) {
					example.createCriteria().andStockOpnameIdIn(selected.getId());
					listData = stockOpnameService.getStockOpnameDtlByExampleWithRowbounds(example,pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
				} else if (isReg && isNumberOfProcessAssetReg != null && isNumberOfProcessAssetReg) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultIsNotNull();

					listData = stockOpnameService.getStockOpnameDtlByExampleWithRowbounds(example,pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
				} else if (isReg && isNumberOfUnProcessAssetReg != null && isNumberOfUnProcessAssetReg) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultIsNull();
					listData = stockOpnameService.getStockOpnameDtlByExampleWithRowbounds(example,pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
				} else if (isReg && isAssetStatusA != null && isAssetStatusA) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultA();
					listData = stockOpnameService.getStockOpnameDtlByExampleWithRowbounds(example,pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
				} else if (isReg && isAssetStatusB != null && isAssetStatusB) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultB();
					listData = stockOpnameService.getStockOpnameDtlByExampleWithRowbounds(example,pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
				} else if (isReg && isAssetStatusC != null && isAssetStatusC) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultC();
					listData = stockOpnameService.getStockOpnameDtlByExampleWithRowbounds(example,pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
				} else if (isReg && isAssetStatusD != null && isAssetStatusD) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultD();
					listData = stockOpnameService.getStockOpnameDtlByExampleWithRowbounds(example,pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
				}
				if(!isPaging){
				pgListOfValue.setTotalSize(stockOpnameService.countSODtlByExample(example));
				pgListOfValue.setPageSize(20);
				pgListOfValue.setActivePage(0);
				}

				lstStockOpnameDetail = new ListModelList<StockOpnameDetailDTO>();
				lstStockOpnameDetail.clear();
				lstStockOpnameDetail.addAll(listData);
				assetsListbox.setModel(lstStockOpnameDetail);
			} else {
				System.out.println("msk else");
				exampleUnreg.createCriteria().andStockOpnameIdIn(selected.getId());
				listDataUnreg = stockOpnameService.getStockOpnameUnregAssetsByExample(exampleUnreg);
				
				if(!isPaging){
					pgListOfValue.setTotalSize(listDataUnreg.size());
					pgListOfValue.setPageSize(20);
					pgListOfValue.setActivePage(0);
				}
				
				lstStockOpnameUnreg = new ListModelList<StockOpnameUnregAssetsDTO>();
				lstStockOpnameUnreg.clear();
				lstStockOpnameUnreg.addAll(listDataUnreg);
				assetsListbox.setModel(lstStockOpnameUnreg);
			}

			System.out.println("end");
		}

	}
	
	private void loadDataForDownload() {
		selected = (StockOpnameDTO) arg.get("stockOpnameDTO");
		Boolean isReg = (Boolean) arg.get("isReg");
		Boolean isUnReg = (Boolean) arg.get("isUnReg");
		Boolean isNumberOfAssetReg = (Boolean) arg.get("isNumberOfAssetReg");
		Boolean isNumberOfProcessAssetReg = (Boolean) arg.get("isNumberOfProcessAssetReg");
		Boolean isNumberOfUnProcessAssetReg = (Boolean) arg.get("isNumberOfUnProcessAssetReg");
		Boolean isAssetStatusA = (Boolean) arg.get("isAssetStatusA");
		Boolean isAssetStatusB = (Boolean) arg.get("isAssetStatusB");
		Boolean isAssetStatusC = (Boolean) arg.get("isAssetStatusC");
		Boolean isAssetStatusD = (Boolean) arg.get("isAssetStatusD");
		List<StockOpnameDetailDTO> listData = new ArrayList<StockOpnameDetailDTO>();
		List<StockOpnameUnregAssetsDTO> listDataUnreg = new ArrayList<StockOpnameUnregAssetsDTO>();
		if (selected != null) {

			StockOpnameDetailExample example = new StockOpnameDetailExample();
			StockOpnameUnregAssetsExample exampleUnreg = new StockOpnameUnregAssetsExample();
			
			if (!isUnReg) {
				System.out.println("msk if");
				if (isReg && isNumberOfAssetReg != null && isNumberOfAssetReg) {
					example.createCriteria().andStockOpnameIdIn(selected.getId());
					listData = stockOpnameService.getStockOpnameDtlByExample(example);
				} else if (isReg && isNumberOfProcessAssetReg != null && isNumberOfProcessAssetReg) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultIsNotNull();

					listData = stockOpnameService.getStockOpnameDtlByExample(example);
				} else if (isReg && isNumberOfUnProcessAssetReg != null && isNumberOfUnProcessAssetReg) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultIsNull();
					listData = stockOpnameService.getStockOpnameDtlByExample(example);
				} else if (isReg && isAssetStatusA != null && isAssetStatusA) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultA();
					listData = stockOpnameService.getStockOpnameDtlByExample(example);
				} else if (isReg && isAssetStatusB != null && isAssetStatusB) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultB();
					listData = stockOpnameService.getStockOpnameDtlByExample(example);
				} else if (isReg && isAssetStatusC != null && isAssetStatusC) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultC();
					listData = stockOpnameService.getStockOpnameDtlByExample(example);
				} else if (isReg && isAssetStatusD != null && isAssetStatusD) {
					example.createCriteria().andStockOpnameIdIn(selected.getId()).andOpnameResultD();
					listData = stockOpnameService.getStockOpnameDtlByExample(example);
				}

				ListModelList<StockOpnameDetailDTO> lstStockOpnameDetail = new ListModelList<StockOpnameDetailDTO>();
				lstStockOpnameDetail.clear();
				lstStockOpnameDetail.addAll(listData);
				assetsListboxForDownload.setModel(lstStockOpnameDetail);
				assetsListboxForDownload.renderAll();
			} else {
				System.out.println("msk else");
				exampleUnreg.createCriteria().andStockOpnameIdIn(selected.getId());
				listDataUnreg = stockOpnameService.getStockOpnameUnregAssetsByExample(exampleUnreg);

				ListModelList<StockOpnameUnregAssetsDTO> lstStockOpnameUnreg = new ListModelList<StockOpnameUnregAssetsDTO>();
				lstStockOpnameUnreg.clear();
				lstStockOpnameUnreg.addAll(listDataUnreg);
				assetsListboxForDownload.setModel(lstStockOpnameUnreg);
				assetsListboxForDownload.renderAll();
			}

			System.out.println("end");
		}

	}
	
	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		loadData(true);
	}
	
	@Listen("onClick = #btnClose")
	public void close(Event e) {
		winStockOpnameAsset.detach();
	}
	
	@Listen("onClick = #btnDownload")
	public void export() throws Exception {
		loadDataForDownload();
		Listbox listbox = assetsListboxForDownload;
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
