package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetail;
import co.id.fifgroup.systemworkflow.domain.LevelMessageMapping;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMessageMappingDTO;
import co.id.fifgroup.systemworkflow.dto.LevelMessageDTO;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.util.LevelMessageRenderer;
import co.id.fifgroup.systemworkflow.util.PopupApprovalModelBandbox;
import co.id.fifgroup.systemworkflow.util.RuleRenderer;
import co.id.fifgroup.systemworkflow.validation.ApprovalModelMessageMappingValidator;

import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMRuleExpression;
import co.id.fifgroup.avm.domain.AVMRuleStatement;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowApprovalModelMappingAddComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(WorkflowApprovalModelMappingAddComposer.class);
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient ApprovalModelMessageMappingValidator approvalModelMessageMappingValidator;
		
	@Wire
	private PopupApprovalModelBandbox bnbApprovalModel;
	@Wire
	private Grid gridRule;
	@Wire
	private Grid gridLevelMessage;
	@Wire
	private Label lblCountLevel;
	@Wire
	private Div errLevelMessage;
	@Wire
	private Button btnAddRuleStatement;
	@Wire
	private Label errRuleValidate;
	
	private int countLevel = 0;
	
	private ListModelList<AVMRuleStatement> ruleModelList;
	private ListModelList<LevelMessageDTO> levelMessageModelList;
	private List<AVM> listAVM = new ArrayList<AVM>();
	private RuleRenderer ruleRenderer = new RuleRenderer();
	private LevelMessageRenderer levelMessageRenderer = new LevelMessageRenderer();
	
	private ApprovalModelMessageMappingDTO approvalModelMessageMappingDto;
	private WorkflowApprovalModelMappingComposer parent;
	private TrxType trxType;
	
	private int indexListParent = -1;
	
	public static final UUID NO_APPROVAL_MODEL_UUID = UUID.fromString("AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA");
	private static final String NO_APPROVAL_MODEL_NAME = "No Approval";
	private AVM noAVMModel = new AVM();
		
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateData();
		disabledComponent();
	}
	
	@Listen("onClose = #bnbApprovalModel")
	public void onCloseBandbox() throws FifException {
		KeyValue keyValueAVM = bnbApprovalModel.getKeyValue();
		if (keyValueAVM != null && !((UUID) keyValueAVM.getKey()).equals(NO_APPROVAL_MODEL_UUID)) {
			countLevel = avmAdapterServiceImpl.getCountLevelApprovalModel((UUID) keyValueAVM.getKey());		
		} 
		lblCountLevel.setValue(countLevel + "");
	}
	
	public void disabledComponent() {
		if (approvalModelMessageMappingDto != null) {
			if (approvalModelMessageMappingDto.getModelMappingDetail().getModelMappingId() != null) {
				bnbApprovalModel.setDisabled(true);
				btnAddRuleStatement.setDisabled(true);
			}
		} else {
			bnbApprovalModel.setDisabled(false);
			btnAddRuleStatement.setDisabled(false);
		}
	}
	
	public void populateData() {
		noAVMModel.setAvmId(NO_APPROVAL_MODEL_UUID);
		noAVMModel.setAvmName(NO_APPROVAL_MODEL_NAME);
		
		parent = (WorkflowApprovalModelMappingComposer) arg.get("parent");
		approvalModelMessageMappingDto = (ApprovalModelMessageMappingDTO) arg.get("modelMapping");
		trxType = (TrxType) arg.get("trxType");
		if (approvalModelMessageMappingDto != null) {
			try {
				UUID avmId;
				String avmName;
				if (approvalModelMessageMappingDto.getModelMappingDetail().getApprovalModel().equals(NO_APPROVAL_MODEL_UUID)) {
					avmId = NO_APPROVAL_MODEL_UUID;
					avmName = NO_APPROVAL_MODEL_NAME;
				} else {
					AVM selectedAVM = avmAdapterServiceImpl.getAVMById(approvalModelMessageMappingDto.getModelMappingDetail().getApprovalModel());
					avmId = approvalModelMessageMappingDto.getModelMappingDetail().getApprovalModel();
					avmName = selectedAVM.getAvmName();					
				}
				KeyValue keyValueAVM = new KeyValue(avmId, avmName, avmName);
				bnbApprovalModel.setRawValue(keyValueAVM);
			} catch (FifException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			approvalModelMessageMappingDto = new ApprovalModelMessageMappingDTO();
			approvalModelMessageMappingDto.setModelMappingDetail(new ApprovalModelMappingDetail());
		}
		if (arg.containsKey("index"))
			indexListParent = (int) arg.get("index");
		
//		loadApprovalModel();
		getGridRule();
		getGridLevelMessage();
	}
	
	public void getGridRule() {
		if (arg.containsKey("completeRuleStatement")) {
			AVMRuleExpression avmRuleExpression = (AVMRuleExpression) arg.get("completeRuleStatement");
			ruleModelList = new ListModelList<AVMRuleStatement>(avmRuleExpression.getAvmRuleStatementList());
			gridRule.setModel(ruleModelList);
			boolean disabled = approvalModelMessageMappingDto.getModelMappingDetail().getModelMappingId() != null;
			gridRule.setRowRenderer(ruleRenderer.createGridRuleRowRenderer(ruleModelList, gridRule, 1, disabled));			
		} else {
			AVMRuleStatement avmRuleStatement = new AVMRuleStatement();
			avmRuleStatement.setAvmRuleStatementId(UUID.randomUUID());
			ruleModelList = new ListModelList<AVMRuleStatement>();
			ruleModelList.add(avmRuleStatement);
			gridRule.setModel(ruleModelList);
			gridRule.setRowRenderer(ruleRenderer.createGridRuleRowRenderer(ruleModelList, gridRule, 1, false));
		}
	}
	
	@Listen("onClick = button#btnAddRuleStatement")
	public void addRuleStatement() {
		AVMRuleStatement avmRuleStatement = new AVMRuleStatement();
		avmRuleStatement.setAvmRuleStatementId(UUID.randomUUID());
		ruleModelList.add(avmRuleStatement);
	}
	
	public void getGridLevelMessage() {
		if (arg.containsKey("modelMapping")) {
			levelMessageModelList = new ListModelList<LevelMessageDTO>(approvalModelMessageMappingDto.getListLevelMessage());
			gridLevelMessage.setModel(levelMessageModelList);
			gridLevelMessage.setRowRenderer(levelMessageRenderer.createGridLevelMessageRenderer(gridLevelMessage, levelMessageModelList, trxType));			
		} else {
			levelMessageModelList = new ListModelList<>();
			LevelMessageDTO levelMessageDto = new LevelMessageDTO();
			levelMessageDto.setLevelMessageUUID(UUID.randomUUID());
			levelMessageDto.setListLevelMessage(new ArrayList<LevelMessageMapping>());
			levelMessageModelList.add(levelMessageDto);
			gridLevelMessage.setModel(levelMessageModelList);
			gridLevelMessage.setRowRenderer(levelMessageRenderer.createGridLevelMessageRenderer(gridLevelMessage, levelMessageModelList, trxType));
		}
		
	}
	
	@Listen("onClick = button#btnAddLevelMessage")
	public void addLevelMessage() {
		LevelMessageDTO levelMessageDto = new LevelMessageDTO();
		levelMessageDto.setLevelMessageUUID(UUID.randomUUID());
		levelMessageDto.setListLevelMessage(new ArrayList<LevelMessageMapping>());
		levelMessageModelList.add(levelMessageDto);
	}
	
//	private void loadApprovalModel() {
//		bnbApprovalModel.setSearchDelegate(new SerializableSearchDelegate<AVM>() {
//
//			private static final long serialVersionUID = -3390151775044854377L;
//
//			@Override
//			public List<AVM> findBySearchCriteria(String searchCriteria, int limit, int offset) {
//				try {
//					searchCriteria = searchCriteria.replaceAll("%", "");
//					listAVM.add(noAVMModel);
//					listAVM.addAll(avmAdapterServiceImpl.getAVMByName(searchCriteria));
//				} catch (FifException e) {
//					logger.error(e.getMessage(), e);
//					logger.error(e.getMessage(), e);
//				}
//				return listAVM;
//			}
//
//			@Override
//			public int countBySearchCriteria(String searchCriteria) {
//				return listAVM.size();
//			}
//
//			@Override
//			public void mapper(KeyValue keyValue, AVM data) {
//				keyValue.setKey(data.getAvmId());
//				keyValue.setValue(data.getAvmName());
//				keyValue.setDescription(data.getAvmName());
//			}
//		});
//	}
	
	@Listen("onClick = button#btnAdd")
	public void onSave() {
		try {
			clearErrorMessage();
			List<LevelMessageDTO> newListLevelMessage = new ArrayList<LevelMessageDTO>();
			newListLevelMessage = levelMessageRenderer.getListLevelMessage(gridLevelMessage, levelMessageModelList);		
//			ApprovalModelMappingDetail modelMappingDetail = new ApprovalModelMappingDetail();
			KeyValue keyValueAVM = bnbApprovalModel.getKeyValue();
			if (keyValueAVM != null)
				approvalModelMessageMappingDto.getModelMappingDetail().setApprovalModel((UUID) keyValueAVM.getKey());
			approvalModelMessageMappingDto.getModelMappingDetail().setConditionRule(ruleRenderer.getRuleExpression(ruleModelList, gridRule));
			
//			ApprovalModelMessageMappingDTO approvalModelMessageMappingDto = new ApprovalModelMessageMappingDTO();
//			approvalModelMessageMappingDto.setModelMappingDetail(modelMappingDetail);
			approvalModelMessageMappingDto.setValidateRule(ruleRenderer.validationRule(ruleModelList, gridRule));
			approvalModelMessageMappingDto.setListLevelMessage(newListLevelMessage);
			approvalModelMessageMappingValidator.validate(approvalModelMessageMappingDto);
			parent.doAfterAddApprovalMapping(approvalModelMessageMappingDto, indexListParent);
			getSelf().onClose();
		} catch (ValidationException ve) {
			showErrorMessage(ve.getConstraintViolations());
		}		
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(bnbApprovalModel);
		ErrorMessageUtil.clearErrorMessage(errLevelMessage);
		ErrorMessageUtil.clearErrorMessage(errRuleValidate);
	}
	
	private void showErrorMessage(Map<String, String> maps) {
		if(maps.get(ApprovalModelMessageMappingValidator.APPROVAL_MODEL_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(bnbApprovalModel, 
				maps.get(ApprovalModelMessageMappingValidator.APPROVAL_MODEL_VALIDATE));
		}
		if(maps.get(ApprovalModelMessageMappingValidator.LEVEL_MESSAGE_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(errLevelMessage, 
				maps.get(ApprovalModelMessageMappingValidator.LEVEL_MESSAGE_VALIDATE));
		}
		if(maps.get(ApprovalModelMessageMappingValidator.RULE_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(errRuleValidate, 
				maps.get(ApprovalModelMessageMappingValidator.RULE_VALIDATE));
		}
	}
	
	@Listen("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			
			private static final long serialVersionUID = -8943078060932366016L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					getSelf().onClose();
				}
			}
		});		
		
	}
}
