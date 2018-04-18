package co.id.fifgroup.ssoa.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.*;
import org.zkoss.zul.Image;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.BindingParam;


public class StockOpnamePeriodComposer extends SelectorComposer<Component> {
    //omit codes to get components
	//ClientService clientServices = new ClientService();
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
    private Textbox txtSearchPeriodName;	
	@Wire
    private Listbox lbxStockOpnamePeriod;
	@Wire
	private Listbox lbxStockOpnamePeriodDetail;
	@Wire
	Listbox lbxFormatDownload;
    @Wire
    private Datebox dtPeriodEndDate;
    @Wire
    private Datebox dtPeriodStartDate;
    @Wire
    private Image previewImage;
    @Wire
    private Panel panelView;
    @Wire
    private Panel panelInput;
    @Wire
    private Panel panelDetail;
    @Wire
    private Checkbox checkActive;
    @Wire
    private String mode;

    @Wire
    private Panel panelDetailStockOpnamePeriod;
    @Wire
    private Panel panelAddStockOpnamePeriod;
    
    @Wire
    private Window modalBranch;
    @Wire
    private Listbox lbxAddStockOpnamePeriod;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
    	super.doAfterCompose(comp);
    	//search();
    }
/*    
    @Listen("onSelect = #clientListbox")
    public void showDetail(){
        Client client= new Client();
    	Client selected = clientListbox.getSelectedItem().getValue();
        
    	client.setClientId(selected.getClientId());
     
    }
*/    
  
 /*   public void search(){
    
        List<StockOpnamePeriod> result = new ArrayList<StockOpnamePeriod>();
        StockOpnamePeriod sop = new StockOpnamePeriod();
        sop.setPeriodName("Stock Opname Wilayah 2 Periode 2016");
    	sop.setPeriodStartDate("1-Feb-2016");
    	sop.setPeriodEndDate("29-April-2016");
    	sop.setLocationNumber("12");
    	sop.setNotification("Send");
    	sop.setStatus("Start");
    	sop.setLocationName("Palembang");
    	sop.setLocationType("Cabang");
    	sop.setUpdateBy("system.admin");
    	sop.setUpdateOn("22-Feb-2016 10:00");
    	result.add(sop);
    	
    	sop = new StockOpnamePeriod();
    	sop.setPeriodName("Stock Opname Wilayah 1 Periode 2016");
    	sop.setPeriodStartDate("1-Feb-2016");
    	sop.setPeriodEndDate("29-April-2016");
    	sop.setLocationNumber("12");
    	sop.setNotification("Failed");
    	sop.setStatus("New");
    	sop.setUpdateBy("system.admin");
    	sop.setUpdateOn("22-Feb-2016 10:00");
    	sop.setLocationName("Jakarta");
    	sop.setLocationType("Cabang");
        result.add(sop);
        
        sop = new StockOpnamePeriod();
    	sop.setPeriodName("Stock Opname Nasional Periode 2016");
    	sop.setPeriodStartDate("1-Aug-2016");
    	sop.setPeriodEndDate("30-Sep-2016");
    	sop.setLocationNumber("12");
    	sop.setNotification("Send");
    	sop.setStatus("On Progress");
    	sop.setUpdateBy("system.admin");
    	sop.setUpdateOn("22-Des-2016 10:00");
    	sop.setLocationName("Bandung");
    	sop.setLocationType("Cabang");
        result.add(sop);
        
        sop = new StockOpnamePeriod();
    	sop.setPeriodName("Stock Opname Nasional Periode 2015");
    	sop.setPeriodStartDate("1-Aug-2016");
    	sop.setPeriodEndDate("30-Sep-2016");
    	sop.setLocationNumber("12");
    	sop.setStatus("Closed");
    	sop.setUpdateBy("system.admin");
    	sop.setUpdateOn("22-Des-2015 10:00");
    	sop.setLocationName("Palembang");
    	sop.setNotification("Failed");
    	sop.setLocationType("Cabang");
        result.add(sop);
      
        List<ReportType> reportType = new ArrayList<ReportType>();
        ReportType type = new ReportType();
        type.setValue("PDF");
        reportType.add(type);
        
        type= new ReportType();
        type.setValue("XLS");
        reportType.add(type);
        		
        
        
        lbxStockOpnamePeriod.setModel(new ListModelList<StockOpnamePeriod>(result));    	
      //  lbxStockOpnamePeriodDetail.setModel(new ListModelList<StockOpnamePeriod>(result));
        
		lbxFormatDownload.setModel(new ListModelList<ReportType>(reportType));
    }
 /*   
    function deleteRow(row)
    {
        var i=row.parentNode.parentNode.rowIndex;
        document.getElementById('tblSystemDtl').deleteRow(i);
        if ($(row).attr("flag-data") == "old") {
            systemCd = $(row).parent().parent().children('td').eq(COL_IDX_DETAIL.SystemCd).children('input').val();
            gSystemCdList.push(systemCd);
        }
    }

    function btnAddRow_onClick(flagData){
        $('#tblSystemDtl tbody').append(
        
            <row>
                <checkbox></checkbox>
                <combobox model="@load(vm.contributorTitles)"
                    selectedItem="@bind(vm.selectedContributor.title)" width="50%" />
                <textbox value="@bind(vm.selectedContributor.firstName)" width="70%" readonly="true"/>
                
            </row>
        s
        
        '<tr><td><input type="text" id="txtAddEditSystemCd' + $("tr:last")[0].rowIndex + '" class="form-control" /></td>'+
        '<td><input type="text" id="txtAddEditValueTxt' + $("tr:last")[0].rowIndex + '" class="form-control" /></td>'+
        '<td><input type="text" id="txtAddEditValueNum' + $("tr:last")[0].rowIndex + '" class="form-control" /></td>'+
        '<td><input type="text" id="txtAddEditValueDt' + $("tr:last")[0].rowIndex + '" class="form-control date-picker" /></td>'+
        '<td align="center"><button ' + (flagData != null && flagData != "undefined" ? 'flag-data="' + flagData + '"' : "") + 'type="button" class="btn btn-sm btn-delete" title="Delete" onclick="deleteRow(this)"><i class="fa fa-trash"></i></button></td></tr>'
        );

*/
/*    
    @Listen("onClick = #cleanButton")
    public void clean(){
    	clear();
    	
    }
*/
/*    
    @Listen("onClick = #clearButton")
    public void clear()
    {
    	txtSearchClientCode.setText("");
    	txtSearchClientName.setText("");
    	txtSearchAddress.setText("");
    	
    	txtClientCode.setText("");
    	txtClientName.setText("");
    	txtAddress.setText("");
    	checkActive.setChecked(false);
        search();        
    }
    */
    
	@Listen("a[label='Submit']")
	public void popupSubmit(Event e) {
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_status.zul", null, null);
		win.doModal();
	}

	@Listen("a[label='Approve']")
	public void popupApproved(Event e) {
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_status.zul", null, null);
		win.doModal();
	}
	
    @Listen("onClick = #cancelButton")
    public void cancel()
    {
    	panelAddStockOpnamePeriod.setVisible(false);
    	panelView.setVisible(true);
    }
    
    @Listen("onClick = #saveButton")
    public void save()
    {
    	panelAddStockOpnamePeriod.setVisible(false);
    	panelView.setVisible(true);
    }
    
    @Listen("onClick = #backButton")
    public void back()
    {
    	panelDetailStockOpnamePeriod.setVisible(false);
    	panelView.setVisible(true);
    }
    
    @Listen("onClick = #newButton")
    public void newButton()
    {
    	panelAddStockOpnamePeriod.setVisible(true);
    	panelView.setVisible(false);
    }

    
    /*@Listen("onClick = #saveButton")
    public void save(){
    	
    	ClientService clientService= new ClientService();
    	
    		Client client = new Client();
	        client.setClientCode(txtClientCode.getValue());
	        client.setClientName(txtClientName.getValue());
	        client.setAddress(txtAddress.getValue());
	        if (checkActive.isChecked())
	        {
	        	client.setActiveFlag("Y");
	        }
	        else
	        {
	        	client.setActiveFlag("N");
	        }
			
	            
	        Client newClient = (Client)clientService.findById(txtClientCode.getValue());
			
			if(newClient == null)
			{
				clientService.insert(client);
		        alert("Prossess Save successfully");
			}
			else
			{
				clientService.update(client);
	            alert("Prossess Update successfully");
			}
			 search(); 
			 panelInput.setVisible(false);
			 clear();
    }
*/
/*    @Command
    @NotifyChange("client")
    public void delete(@BindingParam("clientId") Long clientId){

      clientServices.delete(clientId);
      alert("Delete Successfully");
	  search();
    }
*/
/*    @Listen("onClick = #searchButton")
    public void searchKey(){
    	Client client= new Client();
    	client.setClientCode(txtSearchClientCode.getValue());
    	client.setClientName(txtSearchClientName.getValue());
    	client.setAddress(txtSearchAddress.getValue());
        List<Client> result = clientServices.searchCriteria(client);
        
        clientListbox.setModel(new ListModelList<Client>(result));
    }

*/	
    /* linkEdit AssetRetirement */
    @Listen ("a[label='Edit']")
    public void linkEdit(){
    	panelAddStockOpnamePeriod.setVisible(true);
    	panelDetailStockOpnamePeriod.setVisible(false);
    	panelView.setVisible(false);
    }
    /* linkDetail AssetRetirement  */
    @Listen ("a[label='Detail']")
    public void linkDetail(){
    	panelDetailStockOpnamePeriod.setVisible(true);
    	panelView.setVisible(false);
    }
    
    /* linkDelete AssetRetirement */
    @Listen ("a[label='Delete']")
    public void linkDelete(){
		Messagebox.show("Do you really want to delete this data?", "Confirmation", new Messagebox.Button[]{
		Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, null);
		}
    
    /* linkResendNotification AssetRetirement*/
    @Listen ("a[label='Resend Notification']")
    public void linkResendNotification(){
		Messagebox.show("Do you really want to resend notification for this Stock Opname Period?", "Confirmation", new Messagebox.Button[]{
		Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, null);
		}
     
    /* linkResendNotification AssetRetirement */
    @Listen ("a[label='Send Notification']")
    public void linkSendNotification(){
		Messagebox.show("Do you really want to send notification for this Stock Opname Period?", "Confirmation", new Messagebox.Button[]{
		Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, null);
		}
    
    @Listen("onClick = #addRowStockOpnamePeriod")
	public void addRowStockOpnamePeriod() {
	Listitem baseItem = lbxAddStockOpnamePeriod.getItemAtIndex(lbxAddStockOpnamePeriod.getItemCount()-1);
			Listitem listitem = (Listitem)baseItem.clone();
			listitem.setVisible(true);
			lbxAddStockOpnamePeriod.insertBefore(listitem, baseItem);
		}
	
    /* open modal dialog 
	@Listen("onClick = #branchLookup")
    public void showModalBranch(Event e) {
        //create a window programmatically and use it as a modal dialog.
		modalBranch = (Window)Executions.createComponents(
                "/modalBranch.zul", null, null);
		modalBranch.doModal();
    }
	
	/* close modal dialog */
	 @Listen("onClick = #closeBtn")
	 public void showModal(Event e) {
		 modalBranch.detach();
	    }
}
	

