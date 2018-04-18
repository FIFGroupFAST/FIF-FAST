package co.id.fifgroup.ssoa.controller;


import co.id.fifgroup.audit.TrxType;
import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.ssoa.domain.AssetLabeling;
import co.id.fifgroup.ssoa.domain.AssetMutation;
import co.id.fifgroup.ssoa.service.AssetMutationService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;


@VariableResolver(DelegatingVariableResolver.class)
public class AssetMutationController extends SelectorComposer<Component> {
	
	/**
	 * 
	 */
	 @Wire
	 private Listbox lstTimesheet;
	 @Wire
	 private Listbox lstsearchasset;
	 @Wire
	 private Listbox lstdetail;
	 @Wire
	 private Listbox lstmutationasset;
	 @Wire
	 private Listbox listbox;
	 @Wire
	 private Button addRecord;
	 @Wire
	 private Button btnDelete;
	 @Wire
	 private Button btnFindasset;
	 @Wire
	 private Button addtobemutation;
	 @Wire
	 private Button download;
	 @Wire
	 private Bandbox bd;
	 @Wire
	 private Label lbldelete;
	 @Wire
	 private Label lbldedit;
	 @Wire
	 private Label lblresend;
	 
	 
	 private static final long serialVersionUID = 1L;
	 private AssetMutationService assetModelingInterfacet=new AssetMutationService(); 
	 
	 
	 @Override
	 public void doAfterCompose(Component comp) throws Exception {
	     super.doAfterCompose(comp);
	     List<AssetMutation> asset=assetModelingInterfacet.findAll();
		 lstdetail.setModel(new ListModelList<AssetMutation>(asset));
	 
	    }
	 @Listen("onClick = #lbldelete")
		public void lbldelete(){
		 msgOkCancel("Do you really want to delete?", "Confirmation");
	 }
	 
	 @Listen("onClick = #lblresend")
		public void lblresend(){
		 msgOkCancel("Do you really want to send Asset Mutation for approval??", "Confirmation");
	 }
	 
	 
	 @Listen("onClick = #addRecord")
		public void add(){
		/*List<AssetLabeling> asset=assetModelingInterfacet.findAll();
		asset.add(new AssetLabeling(true, "", "", "", "", "", "", "", "", "", ""));
		lstTimesheet.setModel(new ListModelList<AssetLabeling>(asset));*/
		
		/*Listitem baseItem = lstTimesheet.getItemAtIndex(lstTimesheet.getItemCount()-1);
		Listitem listitem = (Listitem)baseItem.clone();
		listitem.setVisible(true);
		lstTimesheet.insertBefore(listitem, baseItem);*/
	 }
	 
	 @Listen("onClick = #addtobemutation")
		public void addtobemutation(){
		List<AssetMutation> asset=assetModelingInterfacet.findAll1();
		lstmutationasset.setModel(new ListModelList<AssetMutation>(asset));
		
		List<AssetMutation> asset1=assetModelingInterfacet.findAll2();
		lstsearchasset.setModel(new ListModelList<AssetMutation>(asset1));
	 }
	 
	 @Listen("onClick = #btnFindasset")
		public void btnFindasset(){
		List<AssetMutation> asset=assetModelingInterfacet.findAll();
		lstsearchasset.setModel(new ListModelList<AssetMutation>(asset));

	 }
	 
	 
	 @Listen("onClick = #btnDelete")
		public void remove(){
		
		List<AssetMutation> asset=assetModelingInterfacet.remove(true);
		lstTimesheet.setModel(new ListModelList<AssetMutation>(asset));
		
	 }
	 
	 @Listen("onClick = #bd")
		public void bandbox1(){
		alert("bandung");
		
		
	 }
	 
	 void findAll(){
		 List<AssetMutation> asset=assetModelingInterfacet.findAll();
		 lstTimesheet.setModel(new ListModelList<AssetMutation>(asset));
	 }
	 
	 @SuppressWarnings({ "unchecked", "rawtypes"})
		private void msgOkCancel(String question, String title){
			Messagebox.show(question, 
				    title, Messagebox.OK | Messagebox.CANCEL,
				    Messagebox.QUESTION,
				        new EventListener(){
				            public void onEvent(Event e){
				                if(Messagebox.ON_OK.equals(e.getName())){
				                    //alert("Click Ok");
				                }else if(Messagebox.ON_CANCEL.equals(e.getName())){
				                	 //alert("Click Cancel");
				                }
				            }
				        }
				    );
		}
	
		@Listen ("onClick = button#download")
		public void onGenerate() throws Exception {
			PdfExporter exporter = new PdfExporter();
			
			/*
			AssetMutation obj1 = new AssetMutation();
			obj1.setBranch_code("001");
			asset.add(obj1);
			*/
			
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			exporter.export(listbox, out);
			
			AMedia amedia = new AMedia("FirstReport.pdf", "pdf", "application/pdf", out.toByteArray());
			Filedownload.save(amedia);
			
			out.close();
			
		}
		
	 
}
