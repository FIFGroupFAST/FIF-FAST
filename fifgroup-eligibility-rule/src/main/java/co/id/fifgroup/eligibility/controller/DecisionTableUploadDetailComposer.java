package co.id.fifgroup.eligibility.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.dto.UploadElementDTO;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.eligibility.service.DecisionTableUploadService;

@VariableResolver(DelegatingVariableResolver.class)
public class DecisionTableUploadDetailComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	@Wire private Listbox lstUploadElement;

	@WireVariable
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate = true)
	private transient DecisionTableUploadService decisionTableUploadServiceImpl;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		BatchUpload batchUpload = (BatchUpload) arg.get("batchUpload");
		UploadElementDTO uploadElementDTO = new UploadElementDTO();
		uploadElementDTO.setBatchNumber(batchUpload.getBatchNumber());
		List<UploadElementDTO> result = decisionTableUploadServiceImpl.findUploadElementByExample(uploadElementDTO);
		lstUploadElement.setModel(new ListModelList<>(result));
		lstUploadElement.renderAll();
	}
	
	@Listen("onClick=#btnBack")
	public void onBack() {
		Map<String,Object> param = new HashMap<>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY));
		Component parent = getSelf().getParent();
		getSelf().detach();
		Executions.createComponents("~./hcms/eligibility-rule/decision_table_upload_inquiry.zul", parent, param);
	}
}
