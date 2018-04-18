package co.id.fifgroup.workstructure.controller;

import java.util.Date;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.workstructure.domain.GradeSet;
import co.id.fifgroup.workstructure.domain.GradeSetExample;
import co.id.fifgroup.workstructure.service.GradeSetSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.workstructure.dto.GradeSetDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class GradeSetSetupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(GradeSetSetupInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient GradeSetSetupService gradeSetSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	@Wire
	private CommonPopupBandbox bnbGradeSet;
	@Wire
	private Datebox dtbEffectiveOnDate;
	@Wire
	private Listbox lstGradeSet;
	
	private GradeSetDTO selectedGradeSet;
	private List<GradeSetDTO> gradeSets;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		lstGradeSet.setMold("paging");
		lstGradeSet.setPageSize(GlobalVariable.getMaxRowPerPage());
		dtbEffectiveOnDate.setValue(new Date());
		populateGradeSet();
	}
	

	@Listen("onDownloadUserManual = #winGradeSetSetupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}

	private void populateGradeSet() {
		bnbGradeSet.setSearchDelegate(new SerializableSearchDelegate<GradeSet>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<GradeSet> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				GradeSetExample example = new GradeSetExample();
					example.createCriteria().andGradeSetNameLikeInsensitive(searchCriteria)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return gradeSetSetupServiceImpl.findByExample(example);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				GradeSetExample example = new GradeSetExample();
					example.createCriteria().andGradeSetNameLikeInsensitive(searchCriteria)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return gradeSetSetupServiceImpl.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, GradeSet data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getGradeSetName());
				keyValue.setDescription(data.getGradeSetName());
			}
		});
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Executions.createComponents("~./hcms/workstructure/grade_set_setup.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		selectedGradeSet = new GradeSetDTO();
		selectedGradeSet.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		selectedGradeSet.setGradeSetName(bnbGradeSet.getKeyValue() != null ? (String)bnbGradeSet.getKeyValue().getDescription() : null);
		selectedGradeSet.setEffectiveOnDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : DateUtil.truncate(new Date()));		
		
		if(selectedGradeSet.getGradeSetName() == null && selectedGradeSet.getEffectiveOnDate() == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						gradeSets = gradeSetSetupServiceImpl.findByInquiry(selectedGradeSet);
						lstGradeSet.setModel(new ListModelList<>(gradeSets));
					}
				}				
			});
		} else {
			gradeSets = gradeSetSetupServiceImpl.findByInquiry(selectedGradeSet);
			lstGradeSet.setModel(new ListModelList<>(gradeSets));
		}
	}
	
	@Listen("onDetail= #winGradeSetSetupInquiry")
	public void onDetail(ForwardEvent event){
		selectedGradeSet = (GradeSetDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GradeSetDTO.class.getName(), selectedGradeSet);
		Executions.createComponents("~./hcms/workstructure/grade_set_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

}
