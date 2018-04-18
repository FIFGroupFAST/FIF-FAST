package co.id.fifgroup.systemworkflow.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.A;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vbox;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.systemworkflow.service.RuleLookupService;

import co.id.fifgroup.avm.constants.ConjunctionType;
import co.id.fifgroup.avm.domain.AVMRuleExpression;
import co.id.fifgroup.avm.domain.AVMRuleLookupDetail;
import co.id.fifgroup.avm.domain.AVMRuleMapping;
import co.id.fifgroup.avm.domain.AVMRuleStatement;
import co.id.fifgroup.avm.manager.AVMRuleEvaluationManager;
import co.id.fifgroup.avm.manager.AVMSetupManager;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.SerializableRowRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;

public class RuleRenderer {

	private static Logger logger = LoggerFactory.getLogger(RuleRenderer.class);
	
	private int indexForGridRule;
	private static final int FIRST_INDEX = 0;
	private static final String NUMBER_VALUE_DOMAIN = "NUMBER";
	private static final String STRING_VALUE_DOMAIN = "STRING";
//	private static final String ERR_INVALID_RULE = "INVALID RULE";
	
	public SerializableRowRenderer<AVMRuleStatement> createGridRuleRowRenderer(final ListModelList<AVMRuleStatement> ruleModelList, final Grid gridRule, final int ruleType, final boolean disabled) {
		return new SerializableRowRenderer<AVMRuleStatement>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Row row, AVMRuleStatement data, int index)
					throws Exception {
				renderRow(row, data);
			}
			
			private void renderRow(final Row row, final AVMRuleStatement data) {
				row.setId(indexForGridRule + "");
				row.setValue(data);
				Vbox vbox = new Vbox();
				
				Hbox hboxConjuction = new Hbox();
				if (indexForGridRule != FIRST_INDEX){
						Listbox cmbConjuction = new Listbox();
						Listitem listitemDummyConjuction = new Listitem();
						listitemDummyConjuction.setLabel(Labels.getLabel("common.pleaseSelect"));
						listitemDummyConjuction.setParent(cmbConjuction);
						
						cmbConjuction.setId("cmbConjunction-" + data.getAvmRuleStatementId());
						for (ConjunctionType conjunction : ConjunctionType.values()) {
							Listitem listitem = new Listitem();
							listitem.setLabel(conjunction.toString());
							listitem.setValue(conjunction);
							listitem.setParent(cmbConjuction);
							if (data.getConjunctionType() != null) {
								if (conjunction.getCode() == data.getConjunctionType().getCode()) {
									cmbConjuction.setSelectedItem(listitem);
								}
							}
						}
						cmbConjuction.setMold("select");
						//disabled component
						cmbConjuction.setDisabled(disabled);
						
						cmbConjuction.setParent(hboxConjuction);
						
						A deletelink = new A("delete");
						deletelink.addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {

							private static final long serialVersionUID = 1L;

							@Override
							public void onEvent(Event event) throws Exception {
								String eventName = event.getName();
								if (eventName.equals(Events.ON_CLICK)) {
									ruleModelList.remove(data);
									row.detach();
									List<Component> rows = gridRule.getRows().getChildren();
									int index = 0;
									for (Component row : rows) {
										row.setId(index + "");
										index++;
									}
								}
							}
						});
						//disabled component
						deletelink.setDisabled(disabled);
						deletelink.setParent(hboxConjuction);
						
						Label notSelected = new Label("Conjunction empty");
						notSelected.setId("errConjunction-" + data.getAvmRuleStatementId());
						notSelected.setStyle("color:red");
						notSelected.setVisible(false);
						notSelected.setParent(hboxConjuction);
						hboxConjuction.setParent(vbox);
					}	
				Hbox hboxRuleStatement = new Hbox();
				hboxRuleStatement.setWidth("500px");
				final Listbox cmbRuleAttribute = new Listbox();
				cmbRuleAttribute.setId("cmbRuleAttribute-" + data.getAvmRuleStatementId());
				cmbRuleAttribute.setMold("select");
				cmbRuleAttribute.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						Listitem selectedListItem = cmbRuleAttribute.getSelectedItem();
						if (selectedListItem.getValue() != null) {
							AVMRuleMapping ruleMapping = (AVMRuleMapping) selectedListItem.getValue();
							
							Listbox cmbOperator = (Listbox) gridRule.getFellowIfAny("cmbOperator-" + data.getAvmRuleStatementId());
							Decimalbox decValue = (Decimalbox) gridRule.getFellowIfAny("decValue-" + data.getAvmRuleStatementId());
							CommonPopupBandbox lookupbox = (CommonPopupBandbox) gridRule.getFellowIfAny("lookupValue-" + data.getAvmRuleStatementId());
							lookupbox.setWidth("300px");
							if (ruleMapping.getLookupHeaderId().equals(NUMBER_VALUE_DOMAIN)) {
								getDefaultOperators(cmbOperator, NUMBER_VALUE_DOMAIN);
								decValue.setDisabled(false);
								decValue.setVisible(true);
								lookupbox.setDisabled(true);
								lookupbox.setVisible(false);
							} else if (ruleMapping.getLookupHeaderId().equals(STRING_VALUE_DOMAIN)) {
								getDefaultOperators(cmbOperator, STRING_VALUE_DOMAIN);
								decValue.setDisabled(true);
								decValue.setVisible(false);
								lookupbox.setDisabled(false);
								lookupbox.setVisible(true);
								lookupbox.setRawValue(null);
								String lookupName = (ruleMapping.getAttributeLabel().replaceAll(" ", "_")).toUpperCase();
								lookupbox.setSearchDelegate(getSearchDelegate(lookupName));
							} else {
								try {
									AVMSetupManager avmSetupManager = (AVMSetupManager) ApplicationContextUtil.getApplicationContext().getBean("avmSetupManager");
									List<AVMRuleLookupDetail> ruleLookupDetails = new ArrayList<AVMRuleLookupDetail>();
									ruleLookupDetails = avmSetupManager.getRuleLookupDetails(ruleMapping.getLookupHeaderId());
									
									if (ruleLookupDetails != null && !ruleLookupDetails.isEmpty()) {
										List<AVMRuleLookupDetail> listLookup = new ArrayList<AVMRuleLookupDetail>();
										AVMRuleLookupDetail select = new AVMRuleLookupDetail();
										select.setDescription(Labels.getLabel("common.pleaseSelect"));
										listLookup.add(select);
										try {
											listLookup.addAll(ruleLookupDetails);
											ListModelList<AVMRuleLookupDetail> listModel = new ListModelList<AVMRuleLookupDetail>(listLookup);
											cmbOperator.setModel(listModel);											
											cmbOperator.setItemRenderer(new SerializableListItemRenderer<AVMRuleLookupDetail>() {
												
												private static final long serialVersionUID = 1L;

												@Override
												public void render(Listitem item, AVMRuleLookupDetail data, int index)
														throws Exception {
													item.setValue(data);
													item.appendChild(new Listcell(data.getDescription()));													
												}
											});
											for (AVMRuleLookupDetail ruleLookup : listModel) {
												if (data.getAvmRuleLookupDetail() != null) {
													if (ruleLookup.getLookupDetailId() == data.getAvmRuleLookupDetail().getLookupDetailId()) {
														listModel.addToSelection(ruleLookup);
													}
												}
											}											
											if(listModel.getSelection().size() < 1) {
												listModel.addToSelection(select);
											}
											cmbOperator.renderAll();
										} catch (Exception e) {
											logger.error(e.getMessage(), e);
										}
										decValue.setDisabled(true);
										decValue.setVisible(false);
										lookupbox.setDisabled(true);
										lookupbox.setVisible(false);
									}
								} catch (Exception e) {
									logger.error(e.getMessage(), e);
								}
							}
						}
					}					
				});
				Listitem listitemDummy = new Listitem();
				listitemDummy.setLabel(Labels.getLabel("common.pleaseSelect"));
				listitemDummy.setParent(cmbRuleAttribute);
				
				try {
					AVMSetupManager avmSetupManager = (AVMSetupManager) ApplicationContextUtil.getApplicationContext().getBean("avmSetupManager");
					List<AVMRuleMapping> ruleMappings = avmSetupManager.getRuleMappingsByRuleType(ruleType);
					for (AVMRuleMapping ruleMapping : ruleMappings) {
						Listitem listitem = new Listitem();
						listitem.setLabel(ruleMapping.getAttributeLabel());
						listitem.setValue(ruleMapping);
						listitem.setParent(cmbRuleAttribute);
						if (data.getAvmRuleMapping() != null) {
							if (ruleMapping.getMappingId() == data.getAvmRuleMapping().getMappingId()) {
								cmbRuleAttribute.setSelectedItem(listitem);
							}
						}
					}
					cmbRuleAttribute.renderAll();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

				Listbox cmbOperator = new Listbox();
				cmbOperator.setId("cmbOperator-" + data.getAvmRuleStatementId());
				cmbOperator.setMold("select");
				List<AVMRuleLookupDetail> listLookup = new ArrayList<AVMRuleLookupDetail>();
				AVMRuleLookupDetail select = new AVMRuleLookupDetail();
				select.setDescription(Labels.getLabel("common.pleaseSelect"));
				listLookup.add(select);
				try {
					AVMSetupManager avmSetupManager = (AVMSetupManager) ApplicationContextUtil.getApplicationContext().getBean("avmSetupManager");
					List<AVMRuleLookupDetail> lookupDetails;
					if (data.getAvmRuleMapping() != null) {
						lookupDetails = avmSetupManager.getRuleLookupDetails(data.getAvmRuleMapping().getLookupHeaderId());
					} else {
						lookupDetails = avmSetupManager.getRuleLookupDetails(NUMBER_VALUE_DOMAIN);
					}
					
					listLookup.addAll(lookupDetails);
					ListModelList<AVMRuleLookupDetail> listModel = new ListModelList<AVMRuleLookupDetail>(listLookup);
					cmbOperator.setModel(listModel);											
					cmbOperator.setItemRenderer(new SerializableListItemRenderer<AVMRuleLookupDetail>() {
						
						private static final long serialVersionUID = 1L;

						@Override
						public void render(Listitem item, AVMRuleLookupDetail data, int index)
								throws Exception {
							item.setValue(data);
							item.appendChild(new Listcell(data.getDescription()));													
						}
					});
					for (AVMRuleLookupDetail ruleLookup : listModel) {
						if (data.getAvmRuleLookupDetail() != null) {
							if (ruleLookup.getLookupDetailId() == data.getAvmRuleLookupDetail().getLookupDetailId()) {
								listModel.addToSelection(ruleLookup);
							}
						}
					}											
					if(listModel.getSelection().size() < 1) {
						listModel.addToSelection(select);
					}
					cmbOperator.renderAll();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				
				Decimalbox decValue = new Decimalbox();
				decValue.setId("decValue-" + data.getAvmRuleStatementId());
				decValue.setWidth("200px");
				decValue.setFormat("");
				
				CommonPopupBandbox lookupbox = new CommonPopupBandbox();
				lookupbox.setWidth("300px");
				lookupbox.setSearchDelegate(getSearchDelegate(""));
				lookupbox.setId("lookupValue-" + data.getAvmRuleStatementId());
				
				//disabled component
				cmbRuleAttribute.setDisabled(disabled);
				cmbOperator.setDisabled(disabled);
				if (disabled) {
					decValue.setDisabled(disabled);
					lookupbox.setDisabled(disabled);
					decValue.setVisible(false);
					lookupbox.setVisible(false);
				}
				//end
				
				cmbRuleAttribute.setParent(hboxRuleStatement);
				cmbOperator.setParent(hboxRuleStatement);
				decValue.setParent(hboxRuleStatement);
				lookupbox.setParent(hboxRuleStatement);				
				
				if (data.getValue() != null) {
					if (data.getValue().getClass().equals(java.lang.String.class)) {
						if (data.getAvmRuleMapping().getLookupHeaderId().equals(NUMBER_VALUE_DOMAIN)) {
							decValue.setValue(BigDecimal.valueOf(Double.valueOf(data.getValue().toString())));
							decValue.setDisabled(false);
							decValue.setVisible(true);
							lookupbox.setDisabled(true);
							lookupbox.setVisible(false);
						} else {
							String lookupName = (data.getAvmRuleMapping().getAttributeLabel().replaceAll(" ", "_")).toUpperCase();
							lookupbox.setRawValue(getLookupValue(lookupName, (String)data.getValue()));
							lookupbox.setSearchDelegate(getSearchDelegate(lookupName));
							decValue.setDisabled(true);
							decValue.setVisible(false);
							lookupbox.setDisabled(false);
							lookupbox.setVisible(true);
						}
					} else {
						decValue.setValue(BigDecimal.valueOf((double) data.getValue()));						
					}
				} else if (data.getAvmRuleMapping() != null) {
					decValue.setDisabled(true);
					decValue.setVisible(false);
					lookupbox.setDisabled(true);
					lookupbox.setVisible(false);
				} else {
					decValue.setDisabled(true);
					decValue.setVisible(false);
					lookupbox.setDisabled(true);
					lookupbox.setVisible(false);
				}
				Label errInvalidRule = new Label();
				errInvalidRule.setId("errInvalidRule-" + data.getAvmRuleStatementId());
				errInvalidRule.setValue("error");
				errInvalidRule.setParent(hboxRuleStatement);
				errInvalidRule.setVisible(false);
				errInvalidRule.setStyle("color:red");
				
				hboxRuleStatement.setParent(vbox);
				
				vbox.setParent(row);
				indexForGridRule++;
			}
		};
	}
	
	private SerializableSearchDelegate<KeyValue> getSearchDelegate(final String attributeLabel) {
		SerializableSearchDelegate<KeyValue> searchDelegate = new SerializableSearchDelegate<KeyValue>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				return getRuleLookupService().getLookups(searchCriteria, attributeLabel, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return getRuleLookupService().countLookups(searchCriteria, attributeLabel);
			}

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				keyValue.setKey(data.getKey());
				keyValue.setValue(data.getValue());
				keyValue.setDescription(data.getDescription());
			}			
		};
		return searchDelegate;
	}
	
	private KeyValue getLookupValue(String attributeLabel, String key) {
		return getRuleLookupService().getLookupValue(attributeLabel, key);
	}
	
	private void getDefaultOperators(Listbox cmbOperator, String valueDomain) {
		
		List<AVMRuleLookupDetail> listLookup = new ArrayList<AVMRuleLookupDetail>();
		AVMRuleLookupDetail select = new AVMRuleLookupDetail();
		select.setDescription(Labels.getLabel("common.pleaseSelect"));
		listLookup.add(select);
		try {
			AVMSetupManager avmSetupManager = (AVMSetupManager) ApplicationContextUtil.getApplicationContext().getBean("avmSetupManager");
			List<AVMRuleLookupDetail> lookupDetails = avmSetupManager.getRuleLookupDetails(valueDomain);
			listLookup.addAll(lookupDetails);
			ListModelList<AVMRuleLookupDetail> listModel = new ListModelList<AVMRuleLookupDetail>(listLookup);
			cmbOperator.setModel(listModel);
			cmbOperator.renderAll();
			cmbOperator.setItemRenderer(new SerializableListItemRenderer<AVMRuleLookupDetail>() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void render(Listitem item, AVMRuleLookupDetail data, int index)
						throws Exception {
					item.setValue(data);
					item.appendChild(new Listcell(data.getDescription()));					
				}
			});
			if(listModel.getSelection().size() < 1) {
				listModel.addToSelection(select);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public String getRuleExpression(ListModelList<AVMRuleStatement> ruleModelList, Grid gridRule) {
		String ruleExpression = "";
		AVMRuleExpression completeRuleStatement = new AVMRuleExpression();
		int indexRule = 0;
		for (Object object : ruleModelList) {
			AVMRuleStatement ruleStatement = (AVMRuleStatement) object;
			UUID ruleStatementId = ruleStatement.getAvmRuleStatementId();
			Listbox cmbConjunction = (Listbox) gridRule.getFellowIfAny("cmbConjunction-" + ruleStatementId);
			Listbox cmbRuleAttribute = (Listbox) gridRule.getFellowIfAny("cmbRuleAttribute-" + ruleStatementId);
			Listbox cmbOperator = (Listbox) gridRule.getFellowIfAny("cmbOperator-" + ruleStatementId);
			Decimalbox decValue = (Decimalbox) gridRule.getFellowIfAny("decValue-" + ruleStatementId);
			CommonPopupBandbox lookupWindow = (CommonPopupBandbox) gridRule.getFellowIfAny("lookupValue-" + ruleStatementId);
			
			ConjunctionType conjunctionType = null;
			if ((cmbConjunction != null && cmbConjunction.getSelectedIndex() > 0) && indexRule != FIRST_INDEX) {
				conjunctionType = (ConjunctionType) cmbConjunction.getSelectedItem().getValue();
				ruleExpression += "\'" + conjunctionType.getConjunction() + "\'";
				completeRuleStatement.getConjunctionTypeList().add(conjunctionType);
				ruleStatement.setConjunctionType(conjunctionType);
			}
			
			AVMRuleMapping avmRuleMapping = null;
			if (cmbRuleAttribute.getSelectedIndex() > 0) {
				avmRuleMapping = (AVMRuleMapping) cmbRuleAttribute.getSelectedItem().getValue();
				ruleExpression += "\'" + avmRuleMapping.getAttributeLabel() + "\'";
				completeRuleStatement.getAvmRuleMappingList().add(avmRuleMapping);
				ruleStatement.setAvmRuleMapping(avmRuleMapping);
			}
			
			AVMRuleLookupDetail lookupDetail = null;
			if (cmbOperator.getSelectedIndex() > 0) {
				lookupDetail = (AVMRuleLookupDetail) cmbOperator.getSelectedItem().getValue();
				ruleExpression += lookupDetail.getLookupDetailValue();
				completeRuleStatement.getAvmRuleLookupDetailList().add(lookupDetail);
				ruleStatement.setAvmRuleLookupDetail(lookupDetail);
			}
			
			String value = "";
			if (decValue.getValue() != null) {
				value = decValue.getValue().toPlainString();
				
				if (!decValue.isDisabled()) {
					completeRuleStatement.getValueList().add(decValue.getValue());
					ruleExpression += "\'" + value + "\'";
					ruleStatement.setValue(decValue.getValue());
				} else {
					completeRuleStatement.getValueList().add(null);
				}
			} else if (lookupWindow.getKeyValue() != null) {
				KeyValue keyValue = lookupWindow.getKeyValue();
				value = keyValue != null ? keyValue.getKey().toString() : null;
				
				if (!lookupWindow.isDisabled()) {
					completeRuleStatement.getValueList().add(keyValue.getKey());
					ruleExpression += "\'" + value + "\'";
					ruleStatement.setValue(keyValue.getKey());
				}
			}
			
			if ((cmbConjunction != null && cmbConjunction.getSelectedIndex() > 0) && (indexRule == FIRST_INDEX)) { //&& action.equals(ActionControl.UPDATE))) {
				conjunctionType = (ConjunctionType) cmbConjunction.getSelectedItem().getValue();
				ruleExpression += "\'" + conjunctionType.getConjunction() + "\'";
				completeRuleStatement.getConjunctionTypeList().add(conjunctionType);
				ruleStatement.setConjunctionType(conjunctionType);
			}
			completeRuleStatement.getAvmRuleStatementList().add(ruleStatement);
			indexRule++;
		}
		return ruleExpression;
	}
	
	public boolean validationRule(ListModelList<AVMRuleStatement> ruleModelList, Grid gridRule) { //, Label errRuleEmpty) {
		boolean validationResult = true;
		AVMRuleStatement ruleStatement = (AVMRuleStatement) ruleModelList.get(FIRST_INDEX);
		
		Listbox cmbRuleAttributeFirst = (Listbox) 
			gridRule.getFellowIfAny("cmbRuleAttribute-" + ruleStatement.getAvmRuleStatementId());
		Listbox cmbOperatorFirst = (Listbox)
			gridRule.getFellowIfAny("cmbOperator-" + ruleStatement.getAvmRuleStatementId());
		Decimalbox decValueFirst = (Decimalbox)
			gridRule.getFellowIfAny("decValue-" + ruleStatement.getAvmRuleStatementId());
		Label errInvalidRuleFirst = (Label)
			gridRule.getFellowIfAny("errInvalidRule-" + ruleStatement.getAvmRuleStatementId());
		CommonPopupBandbox lookupBoxFirst = (CommonPopupBandbox) gridRule.getFellowIfAny("lookupValue-" + ruleStatement.getAvmRuleStatementId());
		StringBuffer buffer = new StringBuffer();
		if (cmbRuleAttributeFirst.getSelectedIndex() > 0 && cmbOperatorFirst.getSelectedIndex() > 0)
		{
			
//			errRuleEmpty.setVisible(false);
			String ruleAttribute = ((AVMRuleMapping)cmbRuleAttributeFirst.getSelectedItem().getValue()).getAttributeLabel();
			String operator = ((AVMRuleLookupDetail)cmbOperatorFirst.getSelectedItem().getValue()).getLookupDetailValue();
			String value = "";

			AVMRuleEvaluationManager evaluator = (AVMRuleEvaluationManager) ApplicationContextUtil.getApplicationContext()
					.getBean("avmRuleEvaluationManager");

//			evaluator.setActiontype(AVMRuleEvaluationManager.RULE_VALIDATION);
			String ruleExpression = "\'" + ruleAttribute + "\'"
				+ operator;
			if (!decValueFirst.isDisabled()) {
				if (decValueFirst.getValue() == null) {
//					errRuleEmpty.setVisible(true);
					validationResult = false;
				}
				else {
					value = decValueFirst.getValue().toPlainString();
					ruleExpression += "\'" + value + "\'";

//					evaluator.setRuleexpression(ruleExpression);
					boolean ruleValidation = evaluator.evaluateRule(AVMRuleEvaluationManager.RULE_VALIDATION, ruleExpression, null);
					logger.debug("rule Validation : " + ruleValidation);
					errInvalidRuleFirst.setVisible(!ruleValidation);
					if (ruleValidation == false) {
						validationResult = ruleValidation;
					}
//					errRuleEmpty.setVisible(false);
				}
			}
			
			if (!lookupBoxFirst.isDisabled()) {
				if (lookupBoxFirst.getKeyValue() == null) {
//					errRuleEmpty.setVisible(true);
					validationResult = false;
				}
				else {
					KeyValue keyValue = lookupBoxFirst.getKeyValue();
					value = keyValue.getKey().toString();
					ruleExpression += "\'" + value + "\'";

//					evaluator.setRuleexpression(ruleExpression);
					boolean ruleValidation = evaluator.evaluateRule(AVMRuleEvaluationManager.RULE_VALIDATION, ruleExpression, null);
					logger.debug("rule Validation : " + ruleValidation);
					errInvalidRuleFirst.setVisible(!ruleValidation);
					if (ruleValidation == false) {
						validationResult = ruleValidation;
					}
//					errRuleEmpty.setVisible(false);
				}
			}
			
			buffer.append(ruleExpression);
			for (int i = 1; i < ruleModelList.getSize(); i++) {
				AVMRuleStatement avmRuleStatement = (AVMRuleStatement) ruleModelList.get(i);
				Listbox cmbConjunction = (Listbox)
							gridRule.getFellowIfAny("cmbConjunction-" + avmRuleStatement.getAvmRuleStatementId());
				
				Label errConjunction = (Label)
					gridRule.getFellowIfAny("errConjunction-" + avmRuleStatement.getAvmRuleStatementId());
				errConjunction.setVisible(false);
				if (cmbConjunction != null && cmbConjunction.getSelectedIndex() > 0) {
					Listbox cmbRuleAttributeSecond = (Listbox) 
						gridRule.getFellowIfAny("cmbRuleAttribute-" + avmRuleStatement.getAvmRuleStatementId());
					Listbox cmbOperatorSecond = (Listbox)
						gridRule.getFellowIfAny("cmbOperator-" + avmRuleStatement.getAvmRuleStatementId());
					Decimalbox decValueSecond = (Decimalbox)
						gridRule.getFellowIfAny("decValue-" + avmRuleStatement.getAvmRuleStatementId());
					Label errInvalidRuleSecond = (Label)
						gridRule.getFellowIfAny("errInvalidRule-" + avmRuleStatement.getAvmRuleStatementId());
					CommonPopupBandbox lookupBoxSecond = (CommonPopupBandbox) gridRule.getFellowIfAny("lookupValue-" + avmRuleStatement.getAvmRuleStatementId());
					
					String conjunctionSecond = ((ConjunctionType)cmbConjunction.getSelectedItem().getValue()).getConjunction();
					buffer.append("\'" + conjunctionSecond + "\'");

					if (cmbRuleAttributeSecond != null && cmbRuleAttributeSecond.getSelectedIndex() > 0
							&& cmbOperatorSecond != null && cmbOperatorSecond.getSelectedIndex() > 0) {
						String ruleAttributeSecond = ((AVMRuleMapping)cmbRuleAttributeSecond.getSelectedItem().getValue()).getAttributeLabel();
						String operatorSecond = ((AVMRuleLookupDetail)cmbOperatorSecond.getSelectedItem().getValue()).getLookupDetailValue();
						
						String valueSecond = "";
						buffer.append("\'" + ruleAttributeSecond + "\'");
						buffer.append(operatorSecond);
						
						if (!decValueSecond.isDisabled()) {
							if (decValueSecond.getValue() == null) {
//								errRuleEmpty.setVisible(true);
								validationResult = false;
							}
							else {
								valueSecond = decValueSecond.getValue().toPlainString();
								buffer.append("\'" + valueSecond + "\'");

//								evaluator.setRuleexpression(buffer.toString());
								boolean ruleValidation = evaluator.evaluateRule(AVMRuleEvaluationManager.RULE_VALIDATION, buffer.toString(), null);
								logger.debug("rule Validation : " + ruleValidation);
								errInvalidRuleSecond.setVisible(!ruleValidation);
								if (ruleValidation == false) {
									validationResult = ruleValidation;
								}
//								errRuleEmpty.setVisible(false);
							}
						}
						
						if (!lookupBoxSecond.isDisabled()) {
							if (lookupBoxSecond.getKeyValue() == null) {
//								errRuleEmpty.setVisible(true);
								validationResult = false;
							}
							else {
								KeyValue keyValue = lookupBoxSecond.getKeyValue();
								valueSecond = keyValue.getKey().toString();
								buffer.append("\'" + valueSecond + "\'");

//								evaluator.setRuleexpression(buffer.toString());
								boolean ruleValidation = evaluator.evaluateRule(AVMRuleEvaluationManager.RULE_VALIDATION, buffer.toString(), null);
								logger.debug("rule Validation : " + ruleValidation);
								errInvalidRuleFirst.setVisible(!ruleValidation);
								if (ruleValidation == false) {
									validationResult = ruleValidation;
								}
//								errRuleEmpty.setVisible(false);
							}
						}
					} else {
//						errRuleEmpty.setVisible(true);
						errInvalidRuleSecond.setVisible(false);
						validationResult = false;
					}					
				} else if(cmbConjunction.getSelectedIndex() <= 0) {
					errConjunction.setVisible(true);
					validationResult = false;
				}				
			}		
		} else if ((cmbRuleAttributeFirst.getSelectedIndex() <= 0 && cmbOperatorFirst.getSelectedIndex() <= 0
				&& (decValueFirst.getValue()==null))) {
//			errRuleEmpty.setVisible(false);
			errInvalidRuleFirst.setVisible(false);
		} else {
//			errRuleEmpty.setVisible(true);
			errInvalidRuleFirst.setVisible(false);
			validationResult = false;
		}
		AVMRuleEvaluationManager evaluator = (AVMRuleEvaluationManager) ApplicationContextUtil.getApplicationContext().getBean("avmRuleEvaluationManager");
//		evaluator.setRuleexpression(buffer.toString());
		if (!evaluator.evaluateRule(AVMRuleEvaluationManager.RULE_VALIDATION, buffer.toString(), null)) {
//			errRuleEmpty.setVisible(true);
			validationResult = false;
		}
		return validationResult;
	}
	
	public static RuleLookupService getRuleLookupService() {
		return (RuleLookupService) ApplicationContextUtil.getApplicationContext().getBean("ruleLookupServiceImpl");
	}
}
