package co.id.fifgroup.systemworkflow.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.systemworkflow.constants.LevelMethod;
import co.id.fifgroup.systemworkflow.dto.LevelApproverDTO;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.RuleLookupService;

import co.id.fifgroup.avm.constants.ConjunctionType;
import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMRuleExpression;
import co.id.fifgroup.avm.domain.AVMRuleLookupDetail;
import co.id.fifgroup.avm.domain.AVMRuleMapping;
import co.id.fifgroup.avm.domain.AVMRuleStatement;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMRuleEvaluationManager;
import co.id.fifgroup.avm.manager.AVMSetupManager;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@VariableResolver(DelegatingVariableResolver.class)
public class ApprovalModelDetailComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(ApprovalModelDetailComposer.class);
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient RuleLookupService ruleLookupServiceImpl;
	
	@Wire
	private Listbox lbxApprovalLevel;
	@Wire
	private Label lblAVMName;
	@Wire
	private Label lblVersion;
	@Wire
	private Label lblEffectiveFrom;
	@Wire
	private Label lblEffectiveTo;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception{
		super.doAfterCompose(comp);
		populateData();
	}
	
	public void populateData() {
		try {
			AVM avm = (AVM) arg.get("avm");
			AVMVersions avmVersion = avmAdapterServiceImpl.getCurrentAVMVersions(avm.getAvmId(), new Date());
			if (avmVersion == null) {
				int lastVersionNumber = avmAdapterServiceImpl.getLastVersionNumber(avm.getAvmId());
				avmVersion = avmAdapterServiceImpl.getAVMVersionsByNumberVersion(avm.getAvmId(), lastVersionNumber);
			}
			lblAVMName.setValue(avm.getAvmName());
			lblVersion.setValue(avmVersion.getVersionNumber()+"");
			lblEffectiveFrom.setValue(FormatDateUI.formatDate(avmVersion.getEffectiveStartDate()));
			lblEffectiveTo.setValue(FormatDateUI.formatDate(avmVersion.getEffectiveEndDate()));
			getSelectedAvmLevelApprover(avm, avmVersion);
		} catch (FifException fe) {
			logger.error(fe.getMessage(), fe);
		}		
	}
	
	private void getSelectedAvmLevelApprover(AVM avm, AVMVersions avmVersion) throws FifException {
		List<AVMLevel>listAvmLevels = new ArrayList<AVMLevel>(avmAdapterServiceImpl.getAVMLevels(avm, avmVersion));
		ListModelList<LevelApproverDTO> listAVMLevelApprovers =  new ListModelList<LevelApproverDTO>();		
		for (AVMLevel level : listAvmLevels) {
			LevelApproverDTO avmLevelApprover = new LevelApproverDTO();
			List<AVMApprover> approvers = avmAdapterServiceImpl.getAVMApproversByLevel(level, avmVersion);
			avmLevelApprover.setLevel(level);
			avmLevelApprover.setApprovers(approvers);
			listAVMLevelApprovers.add(avmLevelApprover);
		}
		ListModelList<LevelApproverDTO> listModelAvmLevelApprover = new ListModelList<LevelApproverDTO>(listAVMLevelApprovers);
		listModelAvmLevelApprover.setMultiple(true);
		lbxApprovalLevel.setModel(listModelAvmLevelApprover);
		lbxApprovalLevel.setItemRenderer(getItemRenderer());
	}
	
	public ListitemRenderer<LevelApproverDTO> getItemRenderer() {
		ListitemRenderer<LevelApproverDTO> listitemRenderer = new SerializableListItemRenderer<LevelApproverDTO>() {

			private static final long serialVersionUID = 450073560107346063L;

			@Override
			public void render(Listitem item, final LevelApproverDTO level, final int index)
					throws Exception {
				final AVMRuleExpression completeRuleStatement = new AVMRuleExpression();
				new Listcell((index+1) +"").setParent(item);
				new Listcell(level.getLevel().getLevelName()).setParent(item);
				new Listcell((LevelMethod.fromCode(level.getLevel().getLevelMethod())).getMessage()).setParent(item);
				String ruleExpression = "";
				List<String> parsedRule = AVMRuleEvaluationManager.unwrapSingleQouteMark(level.getLevel().getRule());
				for (int i = 0; i < parsedRule.size(); i++) {
					int j = i;
					AVMRuleStatement ruleStatement = new AVMRuleStatement();
					ruleStatement.setAvmRuleStatementId(UUID.randomUUID());
					AVMRuleMapping avmRuleMapping = null;
					AVMSetupManager avmSetupManager = (AVMSetupManager) SpringUtil.getBean("avmSetupManager");
					
					String conjunction = "";
					if (parsedRule.size() > 3 && i < parsedRule.size() - 1) {
						if (j > 0) {
							ConjunctionType conjunctionType = ConjunctionType.getConjunctionTypeFromString(parsedRule.get((++i % 4) + j));
							conjunction = conjunctionType.toString();
							ruleStatement.setConjunctionType(conjunctionType);
							j = i;
						}						
					}
					
					try {
						avmRuleMapping = avmSetupManager.getAVMRuleMapping(parsedRule.get((i % 4) + j));
						ruleStatement.setAvmRuleMapping(avmRuleMapping);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					String lookupHeaderId = avmRuleMapping.getLookupHeaderId();
					short withValue = avmRuleMapping.getWithValue();
					String lookupValue = "";
					String operator = "";
					String value = "";
					if (withValue == 1) {
						lookupValue = "\'" + parsedRule.get((++i % 4) + j) + "\'";
						if (avmRuleMapping.getLookupHeaderId().equals("Number")) {
							DecimalFormat format = new DecimalFormat("#,##0.00");
							double doubleValue = Double.parseDouble(parsedRule.get((++i % 4) + j));
							value = format.format(doubleValue);
							ruleStatement.setValue(doubleValue);
						} else {
							String lookupName = (avmRuleMapping.getAttributeLabel().replaceAll(" ", "_")).toUpperCase();
							value = parsedRule.get((++i % 4) + j);
							KeyValue keyValue = ruleLookupServiceImpl.getLookupValue(lookupName, value);
							ruleStatement.setValue(value);
							value = (String) keyValue.getDescription();
						}
					} else {
						lookupValue = "\'" + parsedRule.get((++i % 4) + j) + "\'" +
								"\'" + parsedRule.get((++i % 4) + j) + "\'";
					}
					AVMRuleLookupDetail lookupDetail = null;
					operator = lookupValue;
					
					lookupDetail = avmSetupManager.getAVMRuleLookupDetail(lookupHeaderId, lookupValue);
					ruleStatement.setAvmRuleLookupDetail(lookupDetail);
					if (lookupDetail != null) {
						operator = lookupDetail.getDescription();
					}
										
					ruleExpression += conjunction + " " + avmRuleMapping.getAttributeLabel() + " "
								+ operator + " " 
								+ value + " ";
					
					completeRuleStatement.getAvmRuleStatementList().add(ruleStatement);
		 		}
				new Listcell(ruleExpression).setParent(item);
				
				String listApprover = getListApproverByLevel(level.getApprovers());
				new Listcell(listApprover).setParent(item);							
			}
		};
		
		return listitemRenderer;
	}
	
	private String getListApproverByLevel(List<AVMApprover> approvers) throws FifException {
		List<String> approverNames = new ArrayList<>();
		if (approvers != null) {
			for (AVMApprover approver : approvers) {
				approverNames.add(avmAdapterServiceImpl.getApproverName(approver));
			}			
		}
		return StringUtils.join(approverNames, ", ");
	}
	
	@Listen("onClick = #btnClose")
	public void onClose() {
		getSelf().onClose();
	}
}
