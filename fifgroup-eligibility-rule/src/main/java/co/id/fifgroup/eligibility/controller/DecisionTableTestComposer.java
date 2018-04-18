package co.id.fifgroup.eligibility.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import akka.actor.ActorSystem;
import co.id.fifgroup.eligibility.actor.ActorBuilder;
import co.id.fifgroup.eligibility.dto.DecisionTableDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableRowDTO;
import co.id.fifgroup.eligibility.dto.Results;
import co.id.fifgroup.eligibility.service.DecisionTableModelSetupService;
import co.id.fifgroup.eligibility.service.DecisionTableService;
import co.id.fifgroup.eligibility.ui.Decisiontable;
import co.id.fifgroup.eligibility.util.FactResolver;

import com.google.common.base.Strings;

@VariableResolver(DelegatingVariableResolver.class)
public class DecisionTableTestComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = -6741936114747774702L;
	
	private static final Logger logger = LoggerFactory.getLogger(DecisionTableTestComposer.class);
	
	@Wire private Listbox lstModel;
	@Wire private Listbox lstVersion;
	
	@Wire private Decisiontable decisionTable;
	
	
	@Wire private Textbox txtPersonId;
	@Wire private Datebox dtbEffectiveOnDate;
	
	@Wire private Textbox txtDecisionTableId;
	
	
	@WireVariable(rewireOnActivate = true)
	private transient DecisionTableModelSetupService decisionTableModelSetupServiceImpl;
	
	@WireVariable(rewireOnActivate = true)
	private transient DecisionTableService decisionTableServiceImpl;
	
	@WireVariable(rewireOnActivate = true)
	private transient FactResolver factResolver;
	
	@WireVariable(rewireOnActivate = true)
	private transient ActorBuilder eligibilityActorBuilder;
	
	@WireVariable(rewireOnActivate = true)
	private transient ActorSystem actorSystem;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		logger.debug("initialize DecisionTableTestComposer");
		lstModel.setModel(new ListModelList<>(decisionTableModelSetupServiceImpl.findByExample(null)));
		lstModel.renderAll();
	}
	
	@Listen("onSelect = #lstModel")
	public void onSelectModel() {
		decisionTable.setData(null);
		if (lstModel.getSelectedItem() != null) {
			DecisionTableModelDTO dtModel = (DecisionTableModelDTO) lstModel.getSelectedItem().getValue();
			lstVersion.setModel(new ListModelList<>(decisionTableModelSetupServiceImpl.getAvailableVersions(dtModel.getId())));
			lstVersion.renderAll();
		}	
	}
	
	@Listen("onSelect = #lstVersion")
	public void onSelectVersion() {
		if (lstVersion.getSelectedItem() != null && lstModel.getSelectedItem() != null) {
			DecisionTableDTO dt = new DecisionTableDTO();
			DecisionTableModelDTO dtModel = (DecisionTableModelDTO) lstModel.getSelectedItem().getValue();
			Integer version = lstVersion.getSelectedItem().getValue();
			dt.setModel(decisionTableModelSetupServiceImpl.findByIdAndVersionNumber(dtModel.getId(), version));
			decisionTable.setData(dt);
		}
	}
	
	@Listen("onClick = #btnAddRow")
	public void addRow() {
		decisionTable.addRow(new DecisionTableRowDTO());
	}
	
	@Listen("onClick = #btnRemoveRow")
	public void removeRow() {
		decisionTable.removeSelectedRows();
	}
	
	@Listen("onClick = #btnSave")
	public void save() {
		DecisionTableDTO subject = decisionTable.getData();
		subject.setModulePrefix("ELR");
		logger.debug(String.valueOf(subject));
		decisionTableServiceImpl.save(subject);
	}

	@Listen("onClick = #btnTest")
	public void test() {
		String decTableId = txtDecisionTableId.getValue();
		Long decTable = null;
		if(Strings.isNullOrEmpty(decTableId))
			decTable = decisionTable.getData().getId();
		else
			decTable = Long.valueOf(decTableId);
		decisionTableServiceImpl.execute("ELR", decTable, Long.valueOf(txtPersonId.getValue()), dtbEffectiveOnDate.getValue(), null);
	}
	
	@Listen("onClick = #btnTestBatch")
	public void testBatch() {
		long time = System.currentTimeMillis();
		String query = "select * from (select person_id from per_all_people_f@hrms.us.oracle.com papf where sysdate between papf.effective_start_date and papf.effective_end_date order by person_id) s ";
		//String query = "select 1 as person_id from dual";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("query", query);
		List<Map<String, Object>> persons = factResolver.resolveFact(params);
		for (Map<String, Object> person : persons) {
			long detailTime = System.currentTimeMillis();
//			ActorRef ref = actorSystem.actorOf(new Props(eligibilityActorBuilder));
//			ref.tell(new Message(decisionTable.getData().getId(), Long.valueOf(person.get("PERSON_ID").toString()), new Date(), null), ref);
			Results result = decisionTableServiceImpl.execute("ELR", decisionTable.getData().getId(), Long.valueOf(person.get("PERSON_ID").toString()), new Date(), null);
			logger.debug("result " + person.get("PERSON_ID") +  " " + ((result.getElements().size() > 0) ? "eligible" : " not eligible") );
			logger.debug("Time elapsed: " + (System.currentTimeMillis() - detailTime) + " ms");
		}
		logger.debug("Time elapsed: " + (System.currentTimeMillis() - time) + " ms");
	}
	
	@Listen("onClick = #btnSearch")
	public void searchDecisionTable() {
		Long id = Long.valueOf(txtDecisionTableId.getValue());
		DecisionTableDTO result = decisionTableServiceImpl.findDecisionTableById("ELR", id);
		logger.debug(String.valueOf(result));
	}
	

}
