package co.id.fifgroup.systemworkflow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.tabularentry.BandboxListcell;

@VariableResolver(DelegatingVariableResolver.class)
public class ApprovalModelListOfValueComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ApprovalModelListOfValueComposer.class);
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	
	@Wire
	private Listbox listbox;
	@Wire
	private Textbox txtSearchCriteria;
	
	private Bandbox source;
	
	private BandboxListcell<?, ?> bandboxListcell;
	
	private List<AVM> listAVM = new ArrayList<>();
	
	public static final UUID NO_APPROVAL_MODEL_UUID = UUID.fromString("AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA");
	private static final String NO_APPROVAL_MODEL_NAME = "No Approval";
	private AVM noAVMModel = new AVM();
		
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		source = arg.get("source") == null ? null : (Bandbox) arg.get("source");
		bandboxListcell = arg.get("bandBoxListcell") == null ? null : (BandboxListcell<?,?>) arg.get("bandBoxListcell");
	}

	@Listen("onSelect = #listbox")
	public void select(){
		AVM avm = (AVM) listbox.getSelectedItem().getValue();
		KeyValue selectedData = new KeyValue();
		selectedData.setKey(avm.getAvmId());
		selectedData.setValue(avm);
		selectedData.setDescription(avm.getAvmName());
		if(source != null) {
			source.setRawValue(selectedData);
			Events.postEvent(Events.ON_CLOSE, source, selectedData);
		}else if(bandboxListcell != null){
			bandboxListcell.setValue(selectedData.getDescription().toString());
			bandboxListcell.setRawValue(selectedData);
		}
		getSelf().detach();
	}
	
	private void loadListbox() {
		 try {
			listAVM.clear();
			String searchCriteria = txtSearchCriteria.getValue().replaceAll("%", "");
			noAVMModel.setAvmId(NO_APPROVAL_MODEL_UUID);
			noAVMModel.setAvmName(NO_APPROVAL_MODEL_NAME);
			listAVM.add(noAVMModel);
			listAVM.addAll(avmAdapterServiceImpl.getAVMByName(searchCriteria));
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
		};
		ListModel<AVM> listModel = new ListModelList<AVM>(listAVM);
		listbox.setModel(listModel);
		listbox.setMold("paging");
		listbox.setPageSize(GlobalVariable.getMaxRowPerPage());
		System.err.println(listModel.getSize() + "----> size list model");
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		loadListbox();
	}
	
	@Listen("onClick=#btnDeselect")
	public void deselect() {
		if(source != null) {
			source.setRawValue(null);
			Events.postEvent(Events.ON_CLOSE, source, null);
		}else if(bandboxListcell != null){
			bandboxListcell.setValue(null);
			bandboxListcell.setRawValue(null);
		}
		getSelf().detach();
	}
	
	@Listen("onDetail = #listbox")
	public void onDetail(ForwardEvent evt) {
		AVM selectedData = (AVM) evt.getData();
		if (!selectedData.getAvmId().equals(NO_APPROVAL_MODEL_UUID)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("avm", selectedData);
			Window win = (Window) Executions.createComponents("~./hcms/workflow/approval_model_detail.zul", getSelf().getParent(), params);
			win.setClosable(true);
			win.setMaximized(false);
			win.setWidth("50%");
			win.doModal();			
		}
	}

}
