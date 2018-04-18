package co.id.fifgroup.ssoa.service.impl;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetail;
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.domain.ParameterMessage;
import co.id.fifgroup.systemworkflow.domain.TemplateMessageMapping;
import co.id.fifgroup.ssoa.dao.SOPeriodMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameDetailMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameMapper;
import co.id.fifgroup.systemworkflow.constants.ApproverType;
import co.id.fifgroup.avm.AVMEngine;
import co.id.fifgroup.avm.constants.AVMActionType;
import co.id.fifgroup.avm.constants.AVMApproverPriorityLevel;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.constants.TransactionStatusType;
import co.id.fifgroup.avm.dao.AVMLevelDAO;
import co.id.fifgroup.avm.dao.AVMTransactionDAO;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMRejectionDoer;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.avm.manager.AVMSetupManager;
import co.id.fifgroup.avm.manager.AVMTransactionManager;
import co.id.fifgroup.avm.util.Serialization;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.notification.constant.MessageType;
import co.id.fifgroup.notification.domain.Message;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.domain.TemplateMessage;
import co.id.fifgroup.notification.manager.NotificationManager;
import co.id.fifgroup.notification.manager.SendEmailManager;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.finder.PersonFinder;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.ssoa.constants.SOApprovalStatus;
import co.id.fifgroup.ssoa.constants.SOPeriodStatus;
import co.id.fifgroup.ssoa.dao.AssetsMapper;
import co.id.fifgroup.ssoa.dao.BranchMapper;
import co.id.fifgroup.ssoa.dao.SOPeriodDetailMapper;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.SOPeriod;
import co.id.fifgroup.ssoa.domain.SOPeriodExample;
import co.id.fifgroup.ssoa.domain.StockOpname;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.SOPeriodDetail;

import co.id.fifgroup.ssoa.domain.SOPeriodDetailExample;
import co.id.fifgroup.ssoa.dto.SOPeriodDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.finder.SOPeriodFinder;
import co.id.fifgroup.ssoa.service.SOPeriodService;
import co.id.fifgroup.systemworkflow.service.ApproverMappingService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;
import co.id.fifgroup.systemworkflow.service.WorkflowResolverService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;
import groovy.util.logging.Log;
import java.math.BigDecimal;


@Transactional
@Service("soPeriodService")
public class SOPeriodServiceImpl implements SOPeriodService{

	//Task Runner Entity
	@Autowired
	private SOPeriodMapper soPeriodMapper;
	@Autowired
	private BranchMapper branchMapper;
	
	@Autowired
	private NotificationManager notificationManager;
	@Autowired
	private SOPeriodDetailMapper soPeriodDetailMapper;

	//@Autowired
	//private WorkflowResolverService workflowResolverServiceImpl;
	public static WorkflowResolverService getWorkflowResolverService() {
		return (WorkflowResolverService) ApplicationContextUtil.getApplicationContext().getBean("workflowResolverServiceImpl");
	}
	
	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}
	
	@Autowired
	private AVMApprovalManager avmApprovalManagerHcms;
	
	//@Autowired
	//private AvmAdapterService avmAdapterServiceImpl;
	public static AvmAdapterService getAvmAdapterService() {
		return (AvmAdapterService) ApplicationContextUtil.getApplicationContext().getBean("avmAdapterServiceImpl");
	}
	//Finder
	@Autowired
	private SOPeriodFinder soPeriodFinder;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	
	private AVMLevelDAO avmLevelDAO;
	private AVMSetupManager avmSetupManager;
	private AVMTransactionDAO avmTransactionDAO;
	
	@Autowired
	private AVMTransactionManager avmTransactionManager;
	
	//@Autowired
	//private TemplateMessageService templateMessageServiceImpl;
	
	public static TemplateMessageService getTemplateMessageService() {
		return (TemplateMessageService) ApplicationContextUtil.getApplicationContext().getBean("templateMessageServiceImpl");
	}

	@Autowired
	private SendEmailManager sendEmailManager;

	@Autowired
	private PersonFinder personFinder;

	//@Autowired
	//private ApproverMappingService approverMappingServiceImpl;
	public static ApproverMappingService getApproverMappingService() {
		return (ApproverMappingService) ApplicationContextUtil.getApplicationContext().getBean("approverMappingServiceImpl");
	}
	
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Autowired
	private StockOpnameDetailMapper stockOpnameDetailMapper;
	
	@Autowired
	private StockOpnameMapper stockOpnameMapper;
	
	
	@Override
	@Transactional
	public void save(SOPeriodDTO soPeriodDto) throws Exception {
		
		SOPeriod soPeriod = soPeriodMapper.selectByPrimaryKey(soPeriodDto.getSoPeriodId());
		if(soPeriod == null || soPeriod.getSoPeriodId() == null) {
			for(int i=0; i <= soPeriodDto.getSOPeriodDetailDto().size(); i++)
			{
				soPeriodDto.setInvolveBranch(i);
				soPeriodDto.setNotStartedBranch(i);
			}
			soPeriodDto.setInProcessBranch(0);
			soPeriodDto.setSubmitBranch(0);
			soPeriodDto.setApproveBranch(0);
			soPeriodDto.setClosedBranch(0);
			soPeriodDto.setStatusCode(SOPeriodStatus.NOT_STARTED.getCode());
			soPeriodMapper.insert(soPeriodDto);	
			
			for(SOPeriodDetailDTO soPeriodDetailDto : soPeriodDto.getSOPeriodDetailDto()) {
				SOPeriodDetail soPeriodDetail = modelMapper.map(soPeriodDetailDto, SOPeriodDetail.class);
				soPeriodDetail.setSoPeriodId(soPeriodDto.getSoPeriodId());
				soPeriodDetail.setCreatedBy(soPeriodDto.getCreatedBy());
				//soPeriodDetail.setStatusId(soPeriodDto.getStatusId());
				soPeriodDetail.setStatusCode(SOApprovalStatus.NOT_STARTED.getCode());
				soPeriodDetail.setNotificationStatusId(soPeriodDto.getStatusId());
				soPeriodDetail.setNotificationStatusCode(SOPeriodStatus.SUCESS.getCode());
				soPeriodDetail.setCreationDate(soPeriodDto.getCreationDate());
				soPeriodDetail.setLastUpdateDate(soPeriodDto.getLastUpdateDate());
				soPeriodDetail.setLastUpdateBy(soPeriodDto.getLastUpdateBy());
				soPeriodDetailMapper.insert(soPeriodDetail);
				
				
				List<PersonAssignmentDTO> personApproverBranchHead = new ArrayList<PersonAssignmentDTO>();
				personApproverBranchHead = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "19");

				//Branch Head Notification
				for(int x=0;x<personApproverBranchHead.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverBranchHead.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//Branch Manager Notification
				List<PersonAssignmentDTO> personApproverBranchManager = new ArrayList<PersonAssignmentDTO>();
				personApproverBranchManager = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "1007");
				
				for(int x=0;x<personApproverBranchManager.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverBranchManager.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
					
				//GS Coordinator Notification
				List<PersonAssignmentDTO> personApproverGSCoordinator = new ArrayList<PersonAssignmentDTO>();
				personApproverGSCoordinator = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "239");
				
				for(int x=0;x<personApproverGSCoordinator.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverGSCoordinator.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION,soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				personApproverGSCoordinator = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "111");
				
				for(int x=0;x<personApproverGSCoordinator.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverGSCoordinator.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION,soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
					
				//Representative Head Notification
				List<PersonAssignmentDTO> personApproverRHCoordinator = new ArrayList<PersonAssignmentDTO>();
				personApproverRHCoordinator = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "201");
				
				for(int x=0;x<personApproverRHCoordinator.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverRHCoordinator.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				personApproverRHCoordinator = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "1008");
				
				for(int x=0;x<personApproverRHCoordinator.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverRHCoordinator.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				personApproverRHCoordinator = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "202");
				
				for(int x=0;x<personApproverRHCoordinator.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverRHCoordinator.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
					
				//Section Head Notification
				List<PersonAssignmentDTO> personApproverSHCoordinator = new ArrayList<PersonAssignmentDTO>();
				personApproverSHCoordinator = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "110");
				
				for(int x=0;x<personApproverSHCoordinator.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverSHCoordinator.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				personApproverSHCoordinator = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "144");
				
				for(int x=0;x<personApproverSHCoordinator.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverSHCoordinator.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
				//CAC Notification
				List<PersonAssignmentDTO> personApproverCAC = new ArrayList<PersonAssignmentDTO>();
				personApproverCAC = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "50");
				
				for(int x=0;x<personApproverCAC.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverCAC.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				personApproverCAC = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "51");
				
				for(int x=0;x<personApproverCAC.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverCAC.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				personApproverCAC = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "266");
				
				for(int x=0;x<personApproverCAC.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverCAC.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//Parenting Notification
				List<PersonAssignmentDTO> personApproverParenting = new ArrayList<PersonAssignmentDTO>();
				personApproverParenting = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "147");
				
				for(int x=0;x<personApproverParenting.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverParenting.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//Kios Unit Head Notification
				List<PersonAssignmentDTO> personApproverKUH = new ArrayList<PersonAssignmentDTO>();
				personApproverKUH = getPersonService().selectPersonByAssignment(null, null,
						soPeriodDetailDto.getBranchId(), null, null, soPeriodDto.getCompanyId(), "1042");
				
				for(int x=0;x<personApproverKUH.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverKUH.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationMessageByName(),soPeriodDto.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDto.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_NOTIFICATION, soPeriodDto.getSoPeriodId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		
				StockOpname stockOpname=  new StockOpname();
				stockOpname.setCompanyId(soPeriodDto.getCompanyId());
				stockOpname.setBranchId(soPeriodDetailDto.getBranchId());
				stockOpname.setDescription(soPeriodDto.getDescription());	
				List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(SOApprovalStatus.CODE.getCode(),SOApprovalStatus.NOT_STARTED.getCode());
				
				if(listLookup.size()>0){
					stockOpname.setStatusId(((BigDecimal)listLookup.get(0).getKey()).longValue());
					stockOpname.setStatusCode((String)listLookup.get(0).getValue());
				}
				stockOpname.setStartDate(soPeriodDto.getStartDate());
				stockOpname.setEndDate(soPeriodDto.getEndDate());
				stockOpname.setSoPeriodDtlId(soPeriodDetailDto.getSoPeriodDtlId());

				stockOpname.setCreatedBy(soPeriodDto.getCreatedBy());
				stockOpname.setCreationDate(soPeriodDto.getCreationDate());
				stockOpname.setLastUpdateDate(soPeriodDto.getLastUpdateDate());
				stockOpname.setLastUpdateBy(soPeriodDto.getLastUpdateBy());
				stockOpnameMapper.insert(stockOpname);
				
				List<Assets> assetsList = soPeriodMapper.selectByBranchId(soPeriodDetail.getBranchId(),soPeriodDto.getCompanyId());
				for (Assets assets : assetsList) {
					StockOpnameDetail stockOpnameDetail = new StockOpnameDetail();
					stockOpnameDetail.setAssetId(assets.getId());
					stockOpnameDetail.setStockOpnameId(stockOpname.getId());
					stockOpnameDetail.setCreatedBy(soPeriodDto.getCreatedBy());
					stockOpnameDetail.setCreationDate(soPeriodDto.getCreationDate());
					stockOpnameDetail.setLastUpdateDate(soPeriodDto.getLastUpdateDate());
					stockOpnameDetail.setLastUpdateBy(soPeriodDto.getLastUpdateBy());
					stockOpnameDetailMapper.insert(stockOpnameDetail);
				}
				
			}
		}			
	}
	
	@Override
	@Transactional
	public void resendNotification(SOPeriodDTO soPeriod) throws Exception{
		System.out.println("soPeriod.getSoPeriodId().."+ soPeriod.getSoPeriodId());
		List<SOPeriodDTO> listSoPeriod = soPeriodMapper.getNotStartedBranch(soPeriod.getSoPeriodId());
		for(int i=0;i<listSoPeriod.size();i++){
			
			SOPeriodDTO soPeriodDTO = (SOPeriodDTO)listSoPeriod.get(i);
			List<PersonAssignmentDTO> personApproverBranchHead = new ArrayList<PersonAssignmentDTO>();
			personApproverBranchHead = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "19");
			
			//Branch Head Notification
			for(int x=0;x<personApproverBranchHead.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverBranchHead.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//Branch Manager Notification
			List<PersonAssignmentDTO> personApproverBranchManager = new ArrayList<PersonAssignmentDTO>();
			personApproverBranchManager = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "1007");
			
			for(int x=0;x<personApproverBranchManager.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverBranchManager.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION,soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
			//GS Coordinator Notification
			List<PersonAssignmentDTO> personApproverGSCoordinator = new ArrayList<PersonAssignmentDTO>();
			personApproverGSCoordinator = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "239");
			
			for(int x=0;x<personApproverGSCoordinator.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverGSCoordinator.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			personApproverGSCoordinator = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "111");
			
			for(int x=0;x<personApproverGSCoordinator.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverGSCoordinator.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
			//Representative Head Notification
			List<PersonAssignmentDTO> personApproverRHCoordinator = new ArrayList<PersonAssignmentDTO>();
			personApproverRHCoordinator = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "201");
			
			for(int x=0;x<personApproverRHCoordinator.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverRHCoordinator.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			personApproverRHCoordinator = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "1008");
			
			for(int x=0;x<personApproverRHCoordinator.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverRHCoordinator.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			personApproverRHCoordinator = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "202");
			
			for(int x=0;x<personApproverRHCoordinator.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverRHCoordinator.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
			//Section Head Notification
			List<PersonAssignmentDTO> personApproverSHCoordinator = new ArrayList<PersonAssignmentDTO>();
			personApproverSHCoordinator = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "110");
			
			for(int x=0;x<personApproverSHCoordinator.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverSHCoordinator.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			personApproverSHCoordinator = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "144");
			
			for(int x=0;x<personApproverSHCoordinator.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverSHCoordinator.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//CAC Notification
			List<PersonAssignmentDTO> personApproverCAC = new ArrayList<PersonAssignmentDTO>();
			personApproverCAC = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "50");
			
			for(int x=0;x<personApproverCAC.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverCAC.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			personApproverCAC = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "51");
			
			for(int x=0;x<personApproverCAC.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverCAC.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			personApproverCAC = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "266");
			
			for(int x=0;x<personApproverCAC.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverCAC.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//Parenting Notification
			List<PersonAssignmentDTO> personApproverParenting = new ArrayList<PersonAssignmentDTO>();
			personApproverParenting = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "147");
			
			for(int x=0;x<personApproverParenting.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverParenting.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//Kios Unit Head Notification
			List<PersonAssignmentDTO> personApproverKUH = new ArrayList<PersonAssignmentDTO>();
			personApproverKUH = getPersonService().selectPersonByAssignment(null, null,
					soPeriodDTO.getBranchId(), null, null, soPeriodDTO.getCompanyId(), "1042");
			
			for(int x=0;x<personApproverKUH.size();x++){
				
				PersonAssignmentDTO personAssignmentDTO = personApproverKUH.get(x);
				try {
					sendNotification(soPeriodMapper.getResendNotificationMessageByName(),soPeriod.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), soPeriodDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.SO_PERIOD_RESEND_NOTIFICATION, soPeriod.getSoPeriodId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		}
	}
	

	public void sendNotification(Long templateId, UUID submitterId,UUID approverId, Long companyId,TrxType trxType, Long soPeriodId) throws Exception {
		
		System.out.println("soPeriodId.longValue()-------------------" + soPeriodId.longValue());
		SOPeriod soPeriod = soPeriodMapper.selectByPrimaryKey(soPeriodId.longValue());
		
		TemplateMessage templateMessage = getTemplateMessageService()
				.getTemplateMessageById(templateId);
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setReceivedTime(new Date());
		notificationMessage.setToId(approverId);
		notificationMessage.setFromId(submitterId);
		Map<String, Object> map = new HashMap<String, Object>();
		String approverName = getAvmAdapterService().getName(approverId, companyId);
		map.put("approverName", approverName);
		map.put("dateFrom", soPeriod.getStartDateTostring());
		map.put("dateTo", soPeriod.getEndDateTostring());
		map.put("periodDate", soPeriod.getPeriodDate());
		map.put("periode",soPeriod.getStartDateTostring()+" - "+soPeriod.getEndDateTostring());
		String requestorName = getAvmAdapterService().getName(submitterId, companyId);
		//String employeeNumber = personFinder.selectPersonByPersonUUID(submitterId, null).getEmployeeNumber();
		map.put("requestorName", requestorName);
		List<ParameterMessage> parameterMessages = getTemplateMessageService()
				.getParameterListByTransactionId(trxType.getCode());
		String contentMessage = templateMessage.getTemplateContent();

		if (parameterMessages.size() > 0) {
			for (ParameterMessage parameter : parameterMessages) {
				contentMessage = contentMessage.replaceAll(Pattern.quote(parameter.getAliasName()),
						Matcher.quoteReplacement(parameter.getParameterKey()));
			}

			templateMessage.setTemplateContent(contentMessage);
		}

		Message message = getTemplateMessageService().templateMessageResolver(templateMessage, map);
		notificationMessage.setSubject(message.getSubject());
		notificationMessage.setContentMessage(message.getContent());
		/*notificationMessage.setUrlDetail(templateMessageMapping.getUrlDetail() != null
				? templateMessageMapping.getUrlDetail() + "?avmTrxId=" + avmApprovalHistory.getAvmTrxId() : null);*/
		notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
		insertNewMessage(notificationMessage);
		PersonAssignmentDTO personAssignmentDTO =getPersonService().getAssignmentByPersonUUID(approverId);
		sendEmailManager.sendEmail(notificationMessage, personAssignmentDTO.getEmployeeNumber());
	
	}
	
	public void insertNewMessage(NotificationMessage notificationMessage)
			throws Exception {
		notificationManager.insertNewMessage(notificationMessage);
	}


	
	@Override
	@Transactional(readOnly=true)
	public SOPeriodDTO getSOPeriodById(Long id) {
		SOPeriodDTO soPeriodDto = new SOPeriodDTO();
		SOPeriod soPeriod = soPeriodMapper.selectByPrimaryKey(id);
		soPeriodDto = modelMapper.map(soPeriod, SOPeriodDTO.class);
		soPeriodDto.setSOPeriodDetailDto(getSOPeriodDetailDtoBySOPeriodId(soPeriodDto.getSoPeriodId()));
		return soPeriodDto;
	}

	@Override
	@Transactional(readOnly=true)
	public List<SOPeriod> getSOPeriodByExample(SOPeriodExample example, int limit, int offset) {
		return soPeriodMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int countSOPeriodByExample(SOPeriodExample example, Long taskGroupId) {
		return soPeriodMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<SOPeriodDetail> getSOPeriodDetailBySOPeriodId(Long id) {
		SOPeriodDetailExample example = new SOPeriodDetailExample();
		example.createCriteria().andSOPeriodHdrIdEqualTo(id);
		return soPeriodDetailMapper.selectByExample(example);
	}
	
	//test
	@Override
	@Transactional(readOnly=true)
	public List<SOPeriodDetail> getDetailBranchBySOPeriodId(Long id, String status) {
		SOPeriodDetail detailBranch = new SOPeriodDetail();
		detailBranch.setSoPeriodId(id);
		detailBranch.setStatusCode(status);
		return soPeriodDetailMapper.selectDetailBranch(detailBranch);
	}
	

	@Override
	@Transactional(readOnly=true)
	public List<SOPeriodDetailDTO> getSOPeriodDetailDtoBySOPeriodId(Long id) {
		SOPeriodDetailExample example = new SOPeriodDetailExample();
		example.createCriteria().andSOPeriodHdrIdEqualTo(id);
		List<SOPeriodDetailDTO> returnList = new ArrayList<>();

		for(SOPeriodDetail trd : soPeriodDetailMapper.selectByExample(example)) {
			SOPeriodDetailDTO trdDto = new SOPeriodDetailDTO();
			trdDto = modelMapper.map(trd, SOPeriodDetailDTO.class);
			/*trdDto.setMainTask(taskServiceImpl.getTaskById(trdDto.getTaskId()));
			if(trdDto.getSccuessTaskId() != null) {
				if(trdDto.getSccuessTaskId() != -1) {
					trdDto.setSuccessTask(taskServiceImpl.getTaskById(trdDto.getSccuessTaskId()));
				} if(trdDto.getErrorTask()!= null) {
					if(trdDto.getErrorTaskId() != -1) {
						trdDto.setErrorTask(taskServiceImpl.getTaskById(trdDto.getErrorTaskId()));
					}
				}
			}*/
			returnList.add(trdDto);
		}
		return returnList;
	}

	@Override
	@Transactional(readOnly=true)
	public List<SOPeriodDTO> getSOPeriodDtoByExample(SOPeriodExample example,  int limit, int offset) {
		return soPeriodFinder.selectByExample(example, new RowBounds(offset, limit));
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public List<SOPeriodDetailDTO> getSOPeriodDtoDetailByExample(SOPeriodDetail example,  int limit, int offset, Long taskGroupId) {
		return soPeriodFinder.selectDetailByExample(example, new RowBounds(offset, limit), taskGroupId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<KeyValue> getStatusStockOpname() {
		return soPeriodFinder.getLookupKeyValues(SOApprovalStatus.CODE.getCode(), null);
	
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<KeyValue> getStatusSOPeriod() {
		return soPeriodFinder.getLookupKeyValues(SOPeriodStatus.CODE.getCode(), null);
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<SOPeriodDTO> getSOPeriodByExample(SOPeriodExample example) {
		return soPeriodFinder.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(SOPeriodExample example) {
		return soPeriodFinder.countByExample(example);
	}
	
	@Override
	@Transactional(readOnly=true)
	public int countDetailByExample(SOPeriodDetail example, Long taskGroupId) {
		return soPeriodFinder.countDetailByExample(example, taskGroupId);
	}

	@Override
	public List<SOPeriodDetail> getSOPeriodDetailByExample(SOPeriodDetailExample example) {
		return soPeriodDetailMapper.selectByExample(example);
	}

	public Branch getBranchById(Long id,Long companyId) {
		return branchMapper.selectByPrimaryKey(id,companyId);
	}

	public List<Branch> getBranchAll(Branch example) {
		List <Branch> soPeriod = soPeriodMapper.getUnClosedBranchStatus();
		for (int i=0; i < soPeriod.size(); i++ )
		{
			System.out.println(soPeriod.get(i).getBranchId());
			example.setBranchId(soPeriod.get(i).getBranchId());
		}
		return soPeriodMapper.selectAllBranch(example);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Branch> getBranchByExample(BranchExample example, int limit, int offset) {
		return branchMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int countBranchByExample(BranchExample example) {
		return branchMapper.countByExample(example);
	}


	@Override
	public void doValidateBeforeReject(Object transaction) throws ValidationException {
	
		
	}



	@Override
	public List<KeyValue> getLookupKeyValues(String lookupName, String key) {
		return soPeriodMapper.getLookupKeyValues(lookupName, key);
	}
	
	@Override
	public void doValidateBeforeApprove(Object transaction) throws ValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doApprove(Object transaction, UUID avmTrxId, UUID approverId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void doAfterApproved(Object transaction) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void doAfterRejected(Object transaction) throws Exception {
	
	}

	@Override
	public void doAfterCanceled(Object transaction) throws Exception {

	}

	public AVMReturnCode doApproval(AVMTransaction currentAVMTransaction, AVMApprovalHistory approversHistory,
			String remarks, UUID actualApproverId) throws FifException {

		int currentRemainingApproval = currentAVMTransaction.getRemainingApproval();

		UUID trxId = currentAVMTransaction.getAvmTrxId();
		int levelSequence = approversHistory.getLevelSequence();
		UUID avmId = currentAVMTransaction.getAvmId();
		int avmVersionId = currentAVMTransaction.getAvmVersionId();
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;

		try {
			int priorityOfApprover = 0;
			currentRemainingApproval = currentRemainingApproval - 1;
			currentAVMTransaction.setRemainingApproval(currentRemainingApproval);

			if (currentRemainingApproval == 0) {
				if (isNowTheLastLevel(avmId, levelSequence, avmVersionId)) {
					returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
				} else {
					returnCode = moveOnToNextLevel(currentAVMTransaction);
				}
			} else {
				if (avmApprovalManagerHcms.countApproversStillDoNothing(trxId, levelSequence) == 0
						&& priorityOfApprover != AVMApproverPriorityLevel.EQUAL.getCode()) {
					int nextLowerPriority = priorityOfApprover;// + 1;

					List<AVMApprover> approverList = avmSetupManager.getAVMApproversByLevelAndPriority(avmId,
							levelSequence, nextLowerPriority, avmVersionId);

					returnCode = notifyApproversNew(approverList, currentAVMTransaction, nextLowerPriority);
				}
			}
		} catch (FifException e) {
			throw e;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return returnCode;
	}

	public AVMReturnCode doAutoapprove(List<AVMApprovalHistory> avmApprovalHistories,
			AVMTransaction currentAVMTransaction) throws FifException {
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
		for (AVMApprovalHistory avmApprovalHistory : avmApprovalHistories) {
			if (avmApprovalHistory.getApproverId().equals(UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM))) {
				String remarks = "Autoapprove by system";
				returnCode = doApproval(currentAVMTransaction, avmApprovalHistory, remarks,
						UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM));
			}
		}
		return returnCode;
	}

	public AVMReturnCode doRejection(AVMTransaction avmTransaction, AVMApprovalHistory avmApprovalHistory,
			String remarks, UUID actualApproverId) throws FifException {
		AVMReturnCode returnCode = null;

		try {
			returnCode = AVMReturnCode.TRANSACTION_IS_REJECTED;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return returnCode;
	}

	public void doSendMessage(UUID avmId, UUID avmTrxId, AVMReturnCode returnCode, UUID submitterId, Object application,
			TrxType trxType, Long companyId, Date transactionDate) throws Exception {
		ApprovalModelMappingDetail approvalModelMappingDetail = getAvmAdapterService().getApplicableApprovalModelMapping(
				trxType.getCode(), companyId, application, DateUtil.truncate(new Date()));

		if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_COMPLETED)) {
			AVMRejectionDoer avmDoer = avmTransactionManager.getLastApproverResponseTransaction(avmTrxId,
					AVMActionType.APPROVE_TRX.getCode());
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setApproverId(avmDoer.getApproverId());
			avmApprovalHistory.setSequenceNumber(avmDoer.getSequenceNumber());
			avmApprovalHistory.setLevelSequence(avmDoer.getLevelSequence());

			sendNotificationEmail(avmId, application, avmApprovalHistory, submitterId,
					AVMActionType.APPROVE_TRX.getCode(), trxType, companyId, transactionDate,
					approvalModelMappingDetail);
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_IN_PROGRESS)) {
			List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
			approvalHistories = avmApprovalManagerHcms.getApproverReadyByAVMTrxId(avmTrxId);

			sendMultipleNotificationEmail(approvalHistories, companyId, avmId, trxType,
					AVMActionType.SUBMIT_TRX.getCode(), transactionDate, approvalModelMappingDetail, submitterId,
					application);
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_REJECTED)) {
			AVMRejectionDoer avmDoer = avmTransactionManager.getLastApproverResponseTransaction(avmTrxId,
					AVMActionType.REJECT_TRX.getCode());
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setApproverId(avmDoer.getApproverId());
			avmApprovalHistory.setSequenceNumber(avmDoer.getSequenceNumber());
			avmApprovalHistory.setLevelSequence(avmDoer.getLevelSequence());

			sendNotificationEmail(avmId, application, avmApprovalHistory, submitterId,
					AVMActionType.REJECT_TRX.getCode(), trxType, companyId, transactionDate,
					approvalModelMappingDetail);
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_CANCELLED)) {
			AVMRejectionDoer avmDoer = avmTransactionManager.getLastApproverResponseTransaction(avmTrxId,
					AVMActionType.CANCEL_TRX.getCode());
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setApproverId(avmDoer.getApproverId());
			avmApprovalHistory.setSequenceNumber(avmDoer.getSequenceNumber());
			avmApprovalHistory.setLevelSequence(avmDoer.getLevelSequence());

			sendNotificationEmail(avmId, application, avmApprovalHistory, submitterId,
					AVMActionType.REJECT_TRX.getCode(), trxType, companyId, transactionDate,
					approvalModelMappingDetail);
		}
	}
	

	public AVMReturnCode approveTransaction(UUID trxId, UUID approverId, String remarks, UUID actualApproverId)
			throws FifException {
		try {
			return giveReponseToTransaction(trxId, approverId, remarks, AVMActionType.APPROVE_TRX, actualApproverId);
		} catch (Exception ex) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), ex);
		}
	}

	public AVMReturnCode giveReponseToTransaction(UUID trxId, UUID approverId, String remarks, AVMActionType actionType,
			UUID actualApproverId) throws FifException {
		return giveResponseToTransaction(trxId, approverId, remarks, actionType, actualApproverId);
	}

	public AVMReturnCode giveResponseToTransaction(UUID avmTrxId, UUID approverId, String remarks,
			AVMActionType actionType, UUID actualApproverId) throws FifException {
		AVMReturnCode returnCode = null;

		try {
			AVMApprovalHistory approversApprovalHistory = avmApprovalManagerHcms
					.getRecentApproversApprovalHistory(avmTrxId, approverId);

			if (approversApprovalHistory != null) {
				AVMTransaction currentAVMTransaction = avmTransactionDAO.getAVMTransactionById(avmTrxId);

				if (actionType.equals(AVMActionType.APPROVE_TRX)) {
					returnCode = doApproval(currentAVMTransaction, approversApprovalHistory, remarks, actualApproverId);
				} else if (actionType.equals(AVMActionType.REJECT_TRX)) {
					returnCode = doRejection(currentAVMTransaction, approversApprovalHistory, remarks,
							actualApproverId);
				}
			}
		} catch (FifException e) {
			throw e;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}

		return returnCode;
	}

	private boolean isNowTheLastLevel(UUID avmId, int levelSequence, int avmVersionId) throws FifException {
		boolean isTheLastLevel = false;
		try {
			int lastLevelSequence = avmLevelDAO.getTheLastLevelSequence(avmId, avmVersionId);
			isTheLastLevel = levelSequence == lastLevelSequence ? true : false;
		} catch (FifException e) {
			throw e;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return isTheLastLevel;
	}

	private AVMReturnCode moveOnToNextLevel(AVMTransaction avmTransaction) throws FifException {
		UUID avmId = avmTransaction.getAvmId();
		int levelSequence = avmTransaction.getLevelSequence();
		AVMVersions currentVersion = new AVMVersions();
		currentVersion.setAvmId(avmId);
		currentVersion.setAvmVersionId(avmTransaction.getAvmVersionId());
		AVMLevel nextLevel;
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;

		try {
			Object application = Serialization.deserializedObject(avmTransaction.getSerializedData());
			int nextLevelSequence = levelSequence + 1;
			nextLevel = avmApprovalManagerHcms.getApplicableLevelSequence(currentVersion, nextLevelSequence,
					application);

			if (nextLevel != null) {
				avmTransaction.setLevelSequence(nextLevel.getLevelSequence());

				List<AVMApprover> approverList = avmSetupManager.getTopPriorityApproverList(avmId,
						nextLevel.getLevelSequence(), avmTransaction.getAvmVersionId());
				int priority = approverList.get(0).getPriority();

				int numberApproval = 1;

				if (nextLevel.getLevelMethod() == 1) {
					numberApproval = approverList.size();
				} else if (nextLevel.getLevelMethod() == 2) {
					numberApproval = nextLevel.getNumberOfApprovals();
				}

				nextLevel.setNumberOfApprovals(numberApproval);
				avmTransaction.setRemainingApproval(nextLevel.getNumberOfApprovals());

				returnCode = notifyApproversNew(approverList, avmTransaction, priority);
			} else {
				avmTransaction.setAvmTrxStatus(TransactionStatusType.COMPLETED.getCode());
				returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
			}
		} catch (FifException e) {
			throw e;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}

		return returnCode;
	}

	public AVMReturnCode notifyApproversNew(List<AVMApprover> approverList, AVMTransaction avmTransaction, int priority)
			throws FifException {
		try {
			// add returnCode for handling autoapprove
			AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;

			// List<UUID> avmApproverList = resolveAVMApproverList(approverList,
			// avmTransaction);
			List<AVMApprovalHistory> historyList = resolveAVMApproverList(approverList, avmTransaction);

			// check same approver
			List<AVMApprovalHistory> removeIndex = new ArrayList<AVMApprovalHistory>();
			int k = 1;

			for (int i = 0; i < approverList.size(); i++) {
				for (int j = k; j < approverList.size(); j++) {
					if (approverList.get(i).getApproverId().equals(approverList.get(j).getApproverId())
							&& approverList.get(i).getApproverId().equals(approverList.get(j).getApproverId())) {
						if (historyList.get(i).getApproverId().equals(historyList.get(j).getApproverId())
								&& (historyList.get(i).getOrganizationId() == historyList.get(j).getOrganizationId()
										|| (historyList.get(i).getOrganizationId() != null
												&& historyList.get(j).getOrganizationId() != null
												&& historyList.get(i).getOrganizationId()
														.equals(historyList.get(j).getOrganizationId())))
								&& (historyList.get(i).getBranchId() == historyList.get(j).getBranchId()
										|| (historyList.get(i).getBranchId() != null
												&& historyList.get(j).getBranchId() != null && historyList.get(i)
														.getBranchId().equals(historyList.get(j).getBranchId())))) {
							removeIndex.add(historyList.get(j));
						}
					}
					k++;
				}
			}

			AVMLevel level = avmLevelDAO.getAVMLevelByAVMIdAndLevelSequence(avmTransaction.getAvmId(),
					avmTransaction.getLevelSequence(), avmTransaction.getAvmVersionId());

			if (level != null && removeIndex.size() > 0) {
				Integer remainingApproval = null;

				if (level.getLevelMethod() == 1) {
					remainingApproval = avmTransaction.getRemainingApproval() - removeIndex.size();
				} else if (level.getLevelMethod() == 2) {
					remainingApproval = approverList.size() - removeIndex.size();
					remainingApproval = remainingApproval < avmTransaction.getRemainingApproval() ? remainingApproval
							: avmTransaction.getRemainingApproval();
				}

				historyList.removeAll(removeIndex);
			}

			// check autoapprove
			returnCode = doAutoapprove(historyList, avmTransaction);

			return returnCode;
		} catch (FifException e) {
			throw e;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}

	
	private void prepareNotificationEmail(UUID avmTrxId, UUID submitterId, Object application, TrxType trxType,
			Long companyId) throws FifException, Exception {
		AVMReturnCode returnCode = null;
		Long transactionId = trxType.getCode();

		ApprovalModelMappingDetail approvalModelMappingDetail = getAvmAdapterService().getApplicableApprovalModelMapping(
				transactionId, companyId, application, DateUtil.truncate(new Date()));

		if (approvalModelMappingDetail != null && approvalModelMappingDetail.getApprovalModel() != null) {
			List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
			approvalHistories = avmApprovalManagerHcms.getApproverReadyByAVMTrxId(avmTrxId);

			sendMultipleNotificationEmail(approvalHistories, companyId, approvalModelMappingDetail.getApprovalModel(),
					trxType, AVMActionType.SUBMIT_TRX.getCode(), DateUtil.truncate(new Date()),
					approvalModelMappingDetail, submitterId, application);
			// }
		} else {
			// ketika tidak ada mapping approval yang ditemukan
			returnCode = AVMReturnCode.NO_APPLICABLE_AVM_FOUND;
			getAvmAdapterService().sendNotificationErrorToAdmin(returnCode, trxType.getMessage());
			throw new FifException(returnCode.getCode(), returnCode.getMessage());
		}

	}
	

	public AVMReturnCode rejectTransaction(UUID trxId, UUID approverId, String remarks, UUID actualApproverId)
			throws FifException {
		try {
			return giveReponseToTransaction(trxId, approverId, remarks, AVMActionType.REJECT_TRX, actualApproverId);
		} catch (Exception ex) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), ex);
		}
	}

	protected List<AVMApprovalHistory> resolveAVMApproverList(List<AVMApprover> rawApproverList,
			AVMTransaction avmTransaction) throws FifException {
		List<AVMApprovalHistory> historyList = new ArrayList<AVMApprovalHistory>();
		UUID submitterId = avmApprovalManagerHcms.getTransactionSubmitter(avmTransaction.getAvmTrxId());
		historyList = getWorkflowResolverService().getResolvingApproverList(rawApproverList, avmTransaction,
				submitterId);
		return historyList;
	}

	private void sendMultipleNotificationEmail(List<AVMApprovalHistory> approvalHistories, Long companyId, UUID avmId,
			TrxType trxType, int actionType, Date transactionDate,
			ApprovalModelMappingDetail approvalModelMappingDetail, UUID submitterId, Object application)
			throws Exception {
		ActionType action = ActionType.fromCode(actionType);

		TemplateMessageMapping templateMessageMapping = getTemplateMessageService().getTemplateMessageMappingByCriteria(
				companyId, avmId, trxType.getCode(), (long) approvalHistories.get(0).getLevelSequence(), action,
				DateUtil.truncate(transactionDate), approvalModelMappingDetail.getPriority());

		if (templateMessageMapping == null) {
			templateMessageMapping = getTemplateMessageService().getTemplateMessageMappingByCriteria(companyId, avmId,
					trxType.getCode(), (long) 0, action, transactionDate, approvalModelMappingDetail.getPriority());
		}

		if (templateMessageMapping != null) {
			TemplateMessage templateMessage = getTemplateMessageService()
					.getTemplateMessageById(templateMessageMapping.getTemplateId());

			for (AVMApprovalHistory avmApprovalHistory : approvalHistories) {
				NotificationMessage notificationMessage = new NotificationMessage();
				notificationMessage.setReceivedTime(new Date());
				notificationMessage.setToId(avmApprovalHistory.getApproverId());
				notificationMessage.setFromId(submitterId);

				List<PersonAssignmentDTO> personAssignmentDTOs = getMember(avmApprovalHistory.getApproverId(),
						(SOPeriodDTO) application);

				if (!personAssignmentDTOs.isEmpty()) {
					for (PersonAssignmentDTO personAssignmentDTO : personAssignmentDTOs) {
						Map<String, Object> map = new HashMap<String, Object>();
						String approverName = personAssignmentDTO.getFullName();
						map.put("approverName", approverName);
						String requestorName = getAvmAdapterService().getName(submitterId, companyId);
						map.put("requestorName", requestorName);
						map.put("object", application);

						List<ParameterMessage> parameterMessages = getTemplateMessageService()
								.getParameterListByTransactionId(trxType.getCode());
						String contentMessage = templateMessage.getTemplateContent();

						if (parameterMessages.size() > 0) {
							for (ParameterMessage parameter : parameterMessages) {
								contentMessage = contentMessage.replaceAll(Pattern.quote(parameter.getAliasName()),
										Matcher.quoteReplacement(parameter.getParameterKey()));
							}

							templateMessage.setTemplateContent(contentMessage);
						}

						Message message = getTemplateMessageService().templateMessageResolver(templateMessage, map);
						notificationMessage.setSubject(message.getSubject());
						notificationMessage.setContentMessage(message.getContent());
						notificationMessage.setUrlDetail(templateMessageMapping.getUrlDetail());
						notificationMessage.setMessageType(MessageType.APPROVAL_MESSAGE.getCode());
						sendEmailManager.sendEmail(notificationMessage, "luthfi.noviandi");
						// sendEmailManager.sendEmail(notificationMessage,
						// personAssignmentDTO.getEmployeeNumber());
					}
				}
			}
		}
	}

	public void sendNotificationEmail(UUID avmId, Object application, AVMApprovalHistory avmApprovalHistory,
			UUID submitterId, int actionType, TrxType trxType, Long companyId, Date transactionDate,
			ApprovalModelMappingDetail approvalModelMappingDetail) throws Exception {
		ActionType action = ActionType.fromCode(actionType);
		TemplateMessageMapping templateMessageMapping = getTemplateMessageService().getTemplateMessageMappingByCriteria(
				companyId, avmId, trxType.getCode(), (long) avmApprovalHistory.getLevelSequence(), action,
				DateUtil.truncate(transactionDate), approvalModelMappingDetail.getPriority());

		if (templateMessageMapping == null) {
			templateMessageMapping = getTemplateMessageService().getTemplateMessageMappingByCriteria(companyId, avmId,
					trxType.getCode(), (long) 0, action, DateUtil.truncate(transactionDate),
					approvalModelMappingDetail.getPriority());
		}

		if (templateMessageMapping != null) {
			TemplateMessage templateMessage = getTemplateMessageService()
					.getTemplateMessageById(templateMessageMapping.getTemplateId());
			NotificationMessage notificationMessage = new NotificationMessage();
			notificationMessage.setReceivedTime(new Date());
			notificationMessage.setToId(submitterId);
			notificationMessage.setFromId(avmApprovalHistory.getApproverId());
			Map<String, Object> map = new HashMap<String, Object>();
			String approverName = getAvmAdapterService().getName(avmApprovalHistory.getApproverId(), companyId);
			map.put("approverName", approverName);
			String requestorName = getAvmAdapterService().getName(submitterId, companyId);
			//String employeeNumber = personFinder.selectPersonByPersonUUID(submitterId, null).getEmployeeNumber();
			map.put("requestorName", requestorName);
			map.put("object", application);
			List<ParameterMessage> parameterMessages = getTemplateMessageService()
					.getParameterListByTransactionId(trxType.getCode());
			String contentMessage = templateMessage.getTemplateContent();

			if (parameterMessages.size() > 0) {
				for (ParameterMessage parameter : parameterMessages) {
					contentMessage = contentMessage.replaceAll(Pattern.quote(parameter.getAliasName()),
							Matcher.quoteReplacement(parameter.getParameterKey()));
				}

				templateMessage.setTemplateContent(contentMessage);
			}

			Message message = getTemplateMessageService().templateMessageResolver(templateMessage, map);
			notificationMessage.setSubject(message.getSubject());
			notificationMessage.setContentMessage(message.getContent());
			notificationMessage.setUrlDetail(templateMessageMapping.getUrlDetail() != null
					? templateMessageMapping.getUrlDetail() + "?avmTrxId=" + avmApprovalHistory.getAvmTrxId() : null);
			notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
			PersonAssignmentDTO personAssignmentDTO =getPersonService().getAssignmentByPersonUUID(avmApprovalHistory.getApproverId());
			sendEmailManager.sendEmail(notificationMessage, personAssignmentDTO.getEmployeeNumber());
		}
	}
	

	private List<PersonAssignmentDTO> getMember(UUID personUUID, SOPeriodDTO application) {
		ApproverMapping approverMapping = getApproverMappingService().getApproverMappingByApproverId(personUUID);

		if (approverMapping != null) {
			if (approverMapping.getApproverType().equalsIgnoreCase(ApproverType.Role.name())
					|| approverMapping.getApproverType().equalsIgnoreCase(ApproverType.JobRole.name())
					|| approverMapping.getApproverType().equalsIgnoreCase(ApproverType.DepartmentOwner.name())) {
				List<PersonAssignmentDTO> personAssignmentDTOs = personFinder.selectPersonByAssignment(null, null,
						application.getOrganizationId(), approverMapping.getApproverName(), null, null, null);

				return personAssignmentDTOs;
			} else if (approverMapping.getApproverType().equalsIgnoreCase(ApproverType.JobGroup.name())) {
				List<PersonAssignmentDTO> personAssignmentDTOs = personFinder.selectPersonByAssignment(null, null,
						application.getOrganizationId(), approverMapping.getApproverName(), null, null, null);
				// List<KeyValue> listJobGroup = lookupServiceImpl
				// .getLookupKeyValues(
				// WorkflowReference.WSU_JOB_GROUP.name(),
				// approverMapping.getApproverName());

				return personAssignmentDTOs;
			}

			return null;
		}

		return null;
	}
}