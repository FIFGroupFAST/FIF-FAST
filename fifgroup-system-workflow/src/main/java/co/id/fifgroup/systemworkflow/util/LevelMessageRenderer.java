package co.id.fifgroup.systemworkflow.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vbox;

import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.LevelMessageMapping;
import co.id.fifgroup.systemworkflow.dto.LevelMessageDTO;
import co.id.fifgroup.systemworkflow.dto.TemplateMessageDTO;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;

import co.id.fifgroup.core.ui.SerializableRowRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;

public class LevelMessageRenderer {
	
	private static final Logger logger = LoggerFactory.getLogger(LevelMessageRenderer.class);
	
	private List<TemplateMessageDTO> listTemplateMessage = new ArrayList<TemplateMessageDTO>();
	private int indexForGridLevelMessage = 0;
	private TemplateMessageService templateMessageServiceImpl = (TemplateMessageService) SpringUtil.getBean("templateMessageServiceImpl");	
	
	public SerializableRowRenderer<LevelMessageDTO> createGridLevelMessageRenderer(final Grid gridLevelMessage, final ListModelList<LevelMessageDTO> levelMessageModelList, final TrxType trxType) {
		return new SerializableRowRenderer<LevelMessageDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(final Row row, final LevelMessageDTO data, int index)
					throws Exception {
				row.setId(indexForGridLevelMessage + "L");
				data.setLevel((long) index);
				row.setValue(data);
				KeyValue keyValueSubmit = null;
				KeyValue keyValueApprove = null;
				KeyValue keyValueReject = null;
				for (LevelMessageMapping levelMessageMapping : data.getListLevelMessage()) {
					if (levelMessageMapping.getTemplateMappingId() != null) {						
						TemplateMessageDTO templateMessage = templateMessageServiceImpl.getTemplateMessageDtoById(levelMessageMapping.getTemplateMappingId());
						if (levelMessageMapping.getActionType().equals(ActionType.SUBMIT.toString())) {
							keyValueSubmit = new KeyValue(levelMessageMapping.getTemplateMappingId(), 
									templateMessage.getTemplateMessage().getTemplateName(), 
									templateMessage.getTemplateMessage().getTemplateName());
						} else if (levelMessageMapping.getActionType().equals(ActionType.APPROVE.toString())) {
							keyValueApprove = new KeyValue(levelMessageMapping.getTemplateMappingId(), 
									templateMessage.getTemplateMessage().getTemplateName(), 
									templateMessage.getTemplateMessage().getTemplateName());
						} else if (levelMessageMapping.getActionType().equals(ActionType.REJECT.toString())) {
							keyValueReject = new KeyValue(levelMessageMapping.getTemplateMappingId(), 
									templateMessage.getTemplateMessage().getTemplateName(), 
									templateMessage.getTemplateMessage().getTemplateName());
						}
					}
				}
				Hbox hbox = new Hbox();
				Vbox vboxLevel = new Vbox();
				
				Label lblLevel = new Label();
				lblLevel.setValue("Level " + (index + 1));
				lblLevel.setParent(vboxLevel);
				
				Hbox hboxSubmit = new Hbox();
				Div divSubmit = new Div();
				divSubmit.setWidth("165px");
				Label lblSubmit = new Label();
				lblSubmit.setValue("Template Message Submit");
				lblSubmit.setParent(divSubmit);
				divSubmit.setParent(hboxSubmit);
				CommonPopupBandbox cnbSubmit = new CommonPopupBandbox();
				cnbSubmit.setWidth("300px");
				cnbSubmit.setId("bnbSubmit-" + data.getLevelMessageUUID());
				cnbSubmit.setTitle("Template Message Submit");
				cnbSubmit.setSearchText("Template Message Name");
				cnbSubmit.setHeaderLabel("Template Message Name");
				loadBandbox(cnbSubmit, ActionType.SUBMIT, trxType.getCode());
				if (keyValueSubmit != null)
					cnbSubmit.setRawValue(keyValueSubmit);
				cnbSubmit.setParent(hboxSubmit);
				hboxSubmit.setParent(vboxLevel);
				
				Hbox hboxApprove = new Hbox();
				Div divApprove = new Div();
				divApprove.setWidth("165px");
				Label lblApprove = new Label();
				lblApprove.setValue("Template Message Approve");
				lblApprove.setParent(divApprove);
				divApprove.setParent(hboxApprove);
				CommonPopupBandbox cnbApprove = new CommonPopupBandbox();
				cnbApprove.setWidth("300px");
				cnbApprove.setId("bnbApprove-" + data.getLevelMessageUUID());
				cnbApprove.setTitle("Template Message Approve");
				cnbApprove.setSearchText("Template Message Name");
				cnbApprove.setHeaderLabel("Template Message Name");
				loadBandbox(cnbApprove, ActionType.APPROVE, trxType.getCode());
				if (keyValueApprove != null)
					cnbApprove.setRawValue(keyValueApprove);
				cnbApprove.setParent(hboxApprove);
				hboxApprove.setParent(vboxLevel);
				
				Hbox hboxReject = new Hbox();
				Div divReject = new Div();
				divReject.setWidth("165px");
				Label lblReject = new Label();
				lblReject.setValue("Template Message Reject");
				lblReject.setParent(divReject);
				divReject.setParent(hboxReject);
				CommonPopupBandbox cnbReject = new CommonPopupBandbox();
				cnbReject.setWidth("300px");
				cnbReject.setId("bnbReject-" + data.getLevelMessageUUID());
				cnbReject.setTitle("Template Message Reject");
				cnbReject.setSearchText("Template Message Name");
				cnbReject.setHeaderLabel("Template Message Name");
				loadBandbox(cnbReject, ActionType.REJECT, trxType.getCode());
				if (keyValueReject != null)
					cnbReject.setRawValue(keyValueReject);
				cnbReject.setParent(hboxReject);
				hboxReject.setParent(vboxLevel);
				
				vboxLevel.setParent(hbox);
				
				if (indexForGridLevelMessage > 0) {
					A deletelink = new A("delete");
					deletelink.addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {
							String eventName = event.getName();
							if (eventName.equals(Events.ON_CLICK)) {
								levelMessageModelList.remove(data);
								row.detach();
								List<Component> rows = gridLevelMessage.getRows().getChildren();
								int index = 0;
								for (Component row : rows) {
									row.setId(index + "L");
									index++;
								}
							}
						}
					});
					deletelink.setParent(hbox);				
				}
				
				hbox.setParent(row);
				indexForGridLevelMessage++;				
			}
		};
	}
	
	private void loadBandbox(CommonPopupBandbox bandbox, final ActionType actionType, final Long transactionId) {
		bandbox.setSearchDelegate(new SerializableSearchDelegate<TemplateMessageDTO>() {

			private static final long serialVersionUID = -3390151775044854377L;

			@Override
			public List<TemplateMessageDTO> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				try {
					listTemplateMessage = templateMessageServiceImpl.getTemplateMessageByTransactionIdAndActionType(transactionId, actionType);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				return listTemplateMessage;
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return listTemplateMessage.size();
			}

			@Override
			public void mapper(KeyValue keyValue, TemplateMessageDTO data) {
				keyValue.setKey(data.getTemplateMessageMappingDTO().getTemplateMappingId());
				keyValue.setValue(data.getTemplateMessage().getTemplateName());
				keyValue.setDescription(data.getTemplateMessage().getTemplateName());
			}
		});
		bandbox.setReadonly(true);
	}
	
	public List<LevelMessageDTO> getListLevelMessage(final Grid gridLevelMessage, final ListModelList<LevelMessageDTO> levelMessageModelList) {
		int indexRule = 0;
		List<LevelMessageDTO> newListLevelMessage = new ArrayList<LevelMessageDTO>();
		for (LevelMessageDTO levelMessage : levelMessageModelList) {
			LevelMessageDTO newLevelMessage = new LevelMessageDTO();
			List<LevelMessageMapping> listLevelMessageMappings = new ArrayList<LevelMessageMapping>();
			
			CommonPopupBandbox bnbSubmit = (CommonPopupBandbox) gridLevelMessage.getFellowIfAny("bnbSubmit-" + levelMessage.getLevelMessageUUID());
			CommonPopupBandbox bnbApprove = (CommonPopupBandbox) gridLevelMessage.getFellowIfAny("bnbApprove-" + levelMessage.getLevelMessageUUID());
			CommonPopupBandbox bnbReject = (CommonPopupBandbox) gridLevelMessage.getFellowIfAny("bnbReject-" + levelMessage.getLevelMessageUUID());
			KeyValue keyValueSubmit = bnbSubmit.getKeyValue();
			KeyValue keyValueApprove = bnbApprove.getKeyValue();
			KeyValue keyValueReject = bnbReject.getKeyValue();
					
			LevelMessageMapping levelMessageSubmit = new LevelMessageMapping();
			levelMessageSubmit.setActionType(ActionType.SUBMIT.toString());
			levelMessageSubmit.setLevelApproval((long) indexRule);
			if (keyValueSubmit != null)
				levelMessageSubmit.setTemplateMappingId((long) keyValueSubmit.getKey());
			listLevelMessageMappings.add(levelMessageSubmit);
			
			LevelMessageMapping levelMessageApprove = new LevelMessageMapping();
			levelMessageApprove.setActionType(ActionType.APPROVE.toString());
			levelMessageApprove.setLevelApproval((long) indexRule);
			if (keyValueApprove != null)
				levelMessageApprove.setTemplateMappingId((long) keyValueApprove.getKey());
			listLevelMessageMappings.add(levelMessageApprove);
			
			LevelMessageMapping levelMessageReject = new LevelMessageMapping();
			levelMessageReject.setActionType(ActionType.REJECT.toString());
			levelMessageReject.setLevelApproval((long) indexRule);
			if (keyValueReject != null)
				levelMessageReject.setTemplateMappingId((long) keyValueReject.getKey());
			listLevelMessageMappings.add(levelMessageReject);
			
			newLevelMessage.setLevel(levelMessage.getLevel());
			newLevelMessage.setLevelMessageUUID(levelMessage.getLevelMessageUUID());
			newLevelMessage.setListLevelMessage(listLevelMessageMappings);
			indexRule++;
			newListLevelMessage.add(newLevelMessage);
		}
		return newListLevelMessage;
	}
}
