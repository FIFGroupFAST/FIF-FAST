package co.id.fifgroup.basicsetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.domain.TransactionType;
import co.id.fifgroup.basicsetup.domain.TransactionTypeExample;
import co.id.fifgroup.basicsetup.dto.SequenceGeneratorDTO;
import co.id.fifgroup.basicsetup.service.SequenceGeneratorService;
import co.id.fifgroup.basicsetup.service.TransactionTypeService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class SequenceGeneratorInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(SequenceGeneratorInquiryComposer.class);

	private Map<String, Object> params = new HashMap<String, Object>();
	@WireVariable
	private Map<String, Object> arg;
	private ListModelList<SequenceGeneratorDTO> listModelSequenceGeneratorDto;
	private List<SequenceGeneratorDTO> listSequenceGeneratorDto;

	@Wire
	private Listbox lstTrxType;
	@Wire
	private Listbox lstSequenceGenerator;
	@Wire
	private Button btnNew;
	
	private TransactionType selectedTransactionType;

	private ListModelList<TransactionType> listModelTransactionType;

	@WireVariable(rewireOnActivate = true)
	private transient SequenceGeneratorService sequenceGeneratorServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient TransactionTypeService transactionTypeServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	

	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	@Listen("onDownloadUserManual = #winSequenceGeneratorInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}

	private void init() {
		initListModelSequenceGenerator();
		initListModelTransactionType();
	}

	private void initListModelSequenceGenerator() {
		listModelSequenceGeneratorDto = new ListModelList<SequenceGeneratorDTO>();
		lstSequenceGenerator.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstSequenceGenerator.setModel(listModelSequenceGeneratorDto);
	}

	private void initListModelTransactionType() {
		TransactionTypeExample trxTypeExample = new TransactionTypeExample();
		trxTypeExample.setOrderByClause("UPPER(TRX_TYPE) ASC");
		
		TransactionType transactionTypeSelect = new TransactionType();
		transactionTypeSelect.setTrxType(Labels.getLabel("bse.select"));
		List<TransactionType> listTransactionType = new ArrayList<TransactionType>();
		listTransactionType.add(transactionTypeSelect);
		listTransactionType.addAll(transactionTypeServiceImpl.getTransactionTypeByExample(trxTypeExample));
		listModelTransactionType = new ListModelList<TransactionType>(listTransactionType);
		lstTrxType.setModel(listModelTransactionType);
		lstTrxType.renderAll();
		listModelTransactionType.addToSelection(transactionTypeSelect);

	}

	@Listen("onClick = button#btnNew")
	public void addNew() {
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/sequence_generator.zul", getSelf().getParent(), params);getSelf().detach();
	}
	
	@Listen("onDetail = #lstTrxType")
	public void onDetail(ForwardEvent evt) {
		SequenceGeneratorDTO selectedData = (SequenceGeneratorDTO) evt.getData();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SequenceGeneratorDTO.class.getName(), selectedData);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/sequence_generator.zul", getSelf().getParent(), params);
		getSelf().detach();
	}
	
	@Listen("onClick = #btnFind")
	public void onFind() {
		selectedTransactionType = (TransactionType) lstTrxType.getSelectedItem().getValue();
		if(selectedTransactionType.getTrxTypeId() == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -8214963402048095662L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						search();
					}
				}
			});
		} else {
			search();
		}
	}

	private void search() {
		listSequenceGeneratorDto = sequenceGeneratorServiceImpl.getSequenceGeneratorDtoByTrxCode(selectedTransactionType.getTrxCode(), securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
		listModelSequenceGeneratorDto.clear();
		listModelSequenceGeneratorDto.addAll(listSequenceGeneratorDto);
		
	}
}
