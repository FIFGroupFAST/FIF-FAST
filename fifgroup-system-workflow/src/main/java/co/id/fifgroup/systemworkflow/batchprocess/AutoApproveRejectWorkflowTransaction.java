package co.id.fifgroup.systemworkflow.batchprocess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.TaskExecutionException;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.TrxStatus;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.dto.AVMOutgoingReportDTO;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.task.ExecutableTask;

public class AutoApproveRejectWorkflowTransaction extends ExecutableTask {

	private static final Logger logger = LoggerFactory.getLogger(AutoApproveRejectWorkflowTransaction.class);
	
	private AvmAdapterService avmAdapterService;
	private List<AVMOutgoingReportDTO> dataList = new ArrayList<AVMOutgoingReportDTO>();
	
	@Override
	public void execute() throws TaskExecutionException {
		avmAdapterService = (AvmAdapterService) getApplicationContext().getBean("avmAdapterServiceImpl");
		int actionType = getParameter().containsKey("actionType") ? ActionType.fromMessage(getParameter().get("actionType").toString()).getCode() : -3;
		int trxType = getParameter().containsKey("trxType") ? (TrxType.valueOf(getParameter().get("trxType").toString())).getCode().intValue() : 0;
		Long companyId = Long.parseLong(getParameter().get(StaticParameterKey.COMPANY_ID.name()).toString());		
		int trxStatus = TrxStatus.IN_PROGRESS.getCode();
		int pendingDays = getParameter().containsKey("pendingDays") ? ((Integer)getParameter().get("pendingDays")) : 30;
		debug("action type : " +  actionType);
		debug("transaction type : " + trxType);
		debug("company id : " + companyId);
		debug("pending days : " + pendingDays);
		try {
			dataList = avmAdapterService.getAVMOutgoingReport(null, trxType, "", companyId.intValue(), trxStatus, null, DateUtil.add(DateUtil.truncate(new Date()), Calendar.DATE, -pendingDays));
			debug("data to process : " + dataList.size());
			if (dataList.size() > 0) {
				if (actionType == ActionType.APPROVE.getCode()) {
					avmAdapterService.doMultipleApproveTransactionBySystem(dataList, UUID.fromString(AVMApprovalManager.APPROVER_SWF_ADMIN), Labels.getLabel("swf.autoApproveBySystem"));
				} else if (actionType == ActionType.REJECT.getCode()) {
					Set<AVMOutgoingReport> setData = new HashSet<AVMOutgoingReport>(dataList);
					avmAdapterService.doMultipleCancelTransaction(setData, UUID.fromString(AVMApprovalManager.APPROVER_SWF_ADMIN), Labels.getLabel("swf.autoRejectBySystem"));
				}
			}
			debug("finished...");			
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
