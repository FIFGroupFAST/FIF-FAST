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
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobExample;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.LobSetupService;
import co.id.fifgroup.workstructure.ui.CommonBranchBandbox;
import co.id.fifgroup.workstructure.ui.CommonOrganizationBandbox;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.LobDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class LobSetupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(LobSetupInquiryComposer.class);
	private JobDTO selectedJob;
	private LobDTO selectedLob;
	private List<LobDTO> lobs;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient LobSetupService lobSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient JobSetupService jobSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	@Wire
	private Listbox lstLob;
	@Wire
	private CommonBranchBandbox bnbBranch;
	@Wire
	private CommonOrganizationBandbox bnbOrganization;
	@Wire
	private CommonPopupDoubleBandbox bnbJob;
	@Wire
	private Datebox dtbEffectiveOnDate;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		lstLob.setMold("paging");
		lstLob.setPageSize(GlobalVariable.getMaxRowPerPage());
		bnbOrganization.setBnbBranch(bnbBranch);
		readParameter();
	}
	
	private void readParameter() {
		//selectedJob = (JobDTO) arg.get(JobDTO.class.getName());
		if(selectedJob != null) {
			LobDTO jobLobDto = new LobDTO();
			jobLobDto.setId(selectedJob.getId());
			lobs = lobSetupServiceImpl.findByExample(jobLobDto);
			ListModelList<LobDTO> model = new ListModelList<LobDTO>(lobs);
			lstLob.setModel(model);
			bnbJob.setDisabled(true);
		} else {
			populateJobName();
		}
	}
	

	@Listen("onDownloadUserManual = #winLobInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void populateJobName() {
		bnbJob.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<Job>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Job> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				JobExample example = new JobExample();
				example.createCriteria().andJobCodeLikeInsensitive(searchCriteria1)
					.andJobNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return jobSetupServiceImpl.findByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				JobExample example = new JobExample();
				example.createCriteria().andJobCodeLikeInsensitive(searchCriteria1)
					.andJobNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return jobSetupServiceImpl.countByExample(example);
			}
			
			@Override
			public void mapper(KeyValue keyValue, Job data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getJobCode());
				keyValue.setDescription(data.getJobName());
			}
		});
	}
	
	@Listen("onClick = #btnNew")
	public void addNew() {
//		try {
//			clearErrorMessage();
//			populateLob();
//			lobSetupServiceImpl.validate(selectedLob);
		
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put(LobDTO.class.getName(), selectedLob);
			Executions.createComponents("~./hcms/workstructure/lob_setup_detail.zul", getSelf().getParent(), null);
			getSelf().detach();
//		} catch (ValidationException ve) {
//			showErrorMessage(ve.getConstraintViolations());
//		}			
	}
	
	private void populateLob() {
		selectedLob = new LobDTO();
		selectedLob.setOrganizationId(bnbOrganization.getKeyValue() != null ? (Long)bnbOrganization.getKeyValue().getKey() : null);
		selectedLob.setJobId(bnbJob.getKeyValue() != null ? (Long)bnbJob.getKeyValue().getKey() : null);
		selectedLob.setOrganizationCode(bnbOrganization.getKeyValue() != null ? (String)bnbOrganization.getKeyValue().getValue() : null);
		selectedLob.setOrganizationName(bnbOrganization.getKeyValue() != null ? (String)bnbOrganization.getKeyValue().getDescription() : null);
		selectedLob.setJobCode(bnbJob.getKeyValue() != null ? (String)bnbJob.getKeyValue().getValue() : null);
		selectedLob.setJobName(bnbJob.getKeyValue() != null ? (String)bnbJob.getKeyValue().getDescription() : null);
		selectedLob.setEffectiveOnDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : DateUtil.truncate(new Date()));
		selectedLob.setEffectiveStartDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : DateUtil.truncate(new Date()));
		selectedLob.setBranchId(bnbBranch.getKeyValue() != null ? (Long)bnbBranch.getKeyValue().getKey() : null);
	}

	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(dtbEffectiveOnDate);
		ErrorMessageUtil.clearErrorMessage(bnbOrganization);
		ErrorMessageUtil.clearErrorMessage(bnbJob);
		ErrorMessageUtil.clearErrorMessage(dtbEffectiveOnDate);
	}
	
	@Listen("onClick = #btnFind")
	public void onFind(){
		clearErrorMessage();
		populateLob();
		if(selectedLob.getOrganizationId() == null
				&& selectedLob.getJobId() == null
				&& selectedLob.getEffectiveOnDate() == null
				&& selectedLob.getBranchId() == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

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
		lobs = lobSetupServiceImpl.findByInquiry(selectedLob);
		lstLob.setModel(new ListModelList<LobDTO>(lobs));
	}
	
	@Listen("onDetail= #winLobInquiry")
	public void onDetail(ForwardEvent event){
		selectedLob = (LobDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(LobDTO.class.getName(), selectedLob);
		Executions.createComponents("~./hcms/workstructure/lob_setup_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
		logger.info(selectedLob.toString());
	}
	
	@Listen("onClick = button#btnUpload")
	public void onUpload()
	{
		Executions.createComponents("~./hcms/workstructure/upload_lob.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
}
