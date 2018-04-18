package co.id.fifgroup.systemworkflow.adapter;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.systemworkflow.constants.TrxType;

import co.id.fifgroup.core.service.WorkflowService;

public class WorkflowServiceFactory {

	public static WorkflowService getInstances(Long transactionId) {
		if (transactionId.equals(TrxType.PRO_IRREGULAR_REQUEST.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"irregularPromotionServiceImpl");
		} else if (transactionId.equals(TrxType.LEA_REQUEST.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("leaveTrnServiceImpl");
		} else if (transactionId.equals(TrxType.LEA_CANCELATION_REQUEST
				.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"cancelLeaveTrnServiceImpl");
		} else if (transactionId.equals(TrxType.TMS_OVERTIME_REQUEST.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"overtimeRequestServiceImpl");
		} else if (transactionId.equals(TrxType.BTR_REQUEST.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("requestServiceImpl");
		} else if (transactionId.equals(TrxType.BTR_SETTLEMENT.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("settlementServiceImpl");
		} else if (transactionId.equals(TrxType.BTR_TICKETING_BOOKING_INVOICE
				.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext()
					.getBean("ticketInvoiceServiceImpl");
		} else if (transactionId.equals(TrxType.BTR_VOUCHER_BOOKING_INVOICE
				.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("voucherServiceImpl");
		} else if (transactionId.equals(TrxType.TRF_SINGLE.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"transferTransactionServiceImpl");
		} else if (transactionId.equals(TrxType.MPP_EMPLOYEE_REQUISITION
				.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"mppEmpRequisitionServiceImpl");
		} else if (transactionId.equals(TrxType.MPP_OPTIMUM_REQUEST.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"mppOptimumRequestServiceImpl");
		} else if (transactionId.equals(TrxType.BEN_PREPAYMENT.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"benefitRequestServiceImpl");
		} else if (transactionId.equals(TrxType.BEN_NON_PREPAYMENT.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"benefitRequestServiceImpl");
		} else if (transactionId
				.equals(TrxType.REC_DOCUMENT_CHECKING.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("applicantServiceImpl");
		} else if (transactionId.equals(TrxType.TER_REQUEST.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"terminationRequestServiceImpl");
		} else if (transactionId.equals(TrxType.PAY_UPLOAD_ADDITIONAL_PAYMENT
				.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"additionalPaymentUploadByPicServiceImpl");
		} else if (transactionId.equals(TrxType.LOA_REQUEST.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"loanTransactionServiceImpl");
		} else if (transactionId.equals(TrxType.BEN_SETTLEMENT.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"benefitSettlementServiceImpl");
		} else if (transactionId.equals(TrxType.PRO_ACTING_REVIEW.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean(
							"actingReviewPromotionServiceImpl");
		} else if (transactionId.equals(TrxType.PSI_PARTICIPANT_LOGIN_REQUEST
				.getCode())) {// Rachmad Agus D. untuk module E-Psikotest
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("requestLoginService");
		} else if (transactionId.equals(TrxType.VIP_ACTIVITY
				.getCode())) {// joshua arif - out274 -  Value Internalization Point
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("activityHdrServiceImpl");
		} else if (transactionId.equals(TrxType.DIL_SP_CANCELLATION
				.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("disciplinaryCancellationServiceImpl");
		} else if (transactionId.equals(TrxType.DIL_SP_EVALUATION
				.getCode())) {
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("disciplinaryEvaluationServiceImpl");
		}else if (transactionId.equals(TrxType.OCOP_REQUEST
				.getCode())) {// jatis
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("ocopTransactionServiceImpl");
		}else if (transactionId.equals(TrxType.HOU_PLACEMENT_REQUEST
				.getCode())) {// jatis
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("housingPlacementContractServiceImpl");
		}else if (transactionId.equals(TrxType.HOU_BOARDING_REQUEST
				.getCode())) {// jatis
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("boardingRequestServiceImpl");
		}else if(transactionId.equals(TrxType.HOU_CONTRACT_REQUEST.getCode())){
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("housingPlacementContractServiceImpl");
		}else if(transactionId.equals(TrxType.CWK_HIRE_NEW_CWK.getCode())){
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("applicantCwkServiceImpl");
		}else if(transactionId.equals(TrxType.CWK_PKWT_EXTENTIONS.getCode())){
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("PKWTHistoryCwkServiceImpl");
		}else if(transactionId.equals(TrxType.CWK_TRANSFER_WITHIN_GROUP.getCode())){
			return (WorkflowService) ApplicationContextUtil
					.getApplicationContext().getBean("transferWithinGroupServiceImpl");
		}else if(transactionId.equals(TrxType.UPLOAD_MODULE_FILE.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("moduleUploadServiceImpl");
		}else if(transactionId.equals(TrxType.COURSE_REQUEST.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("courseRequestServiceImpl");
		}else if(transactionId.equals(TrxType.ILE_POINT_REDEMPTION.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("pointRedemptionServiceImpl");
		}else if (transactionId.equals(TrxType.CAM_CAREER_ICP_SUBMISSIONS.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("icpSubmissionServiceImpl");
		}else if(transactionId.equals(TrxType.CWK_TERMINATION_REQUEST.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("cwkTerminationRequestServiceImpl");
		}else if (transactionId.equals(TrxType.PEM_PERFORMANCE_DEVELOPMENT_FEEDBACK.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("performanceDevelopmentFeedbackServiceImpl");
		}else if (transactionId.equals(TrxType.PEM_PERFORMANCE_IPP_BUSINESS_RESULTS.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("ippBusinessResultServiceImpl");
		}else if(transactionId.equals(TrxType.CWK_TERMINATION_REVERSE.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("cwkTerminationRequestServiceImpl");
		}
		else if(transactionId.equals(TrxType.CWK_TRANSFER_REQUEST.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("transferRequestCwkServiceImpl");
		}
		else if(transactionId.equals(TrxType.WEQ_ORDER_TRN.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("equipmentOrderServiceImpl");
		}
		else if(transactionId.equals(TrxType.WEQ_INVENTORY_TRN.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("equipmentInventoryServiceImpl");
		}
		else if (transactionId.equals(TrxType.CAM_CAREER_IDP_EMPLOYEE.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("idpEmployeeServiceImpl");
		}
		else if (transactionId.equals(TrxType.CAM_CAREER_IDP_EMPLOYEE_NEW_DEVELOPMENT_REQUEST.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("idpEmployeeWithDevelopmentRequestServiceImpl");
		}
		else if (transactionId.equals(TrxType.CAM_CAREER_IDP_EMPLOYEE_NEW_COE_REQUEST.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("idpEmployeeWithCoeRequestServiceImpl");
		}
		else if (transactionId.equals(TrxType.CAM_CAREER_IDP_EMPLOYEE_UPDATE.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("idpEmployeeServiceImpl");
		}
		// added by WLY for Training admin
		else if (transactionId.equals(TrxType.TRA_DEV_PROPOSAL_SUBMIT.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("developmentProposalServiceImpl");
		}
		else if (transactionId.equals(TrxType.TRA_PREPAYMENT_ENTRY.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("prepaymentServiceImpl");
		}
		else if (transactionId.equals(TrxType.TRA_COE_REQUEST.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("traRequestCoeServiceImpl");
		}
		else if (transactionId.equals(TrxType.TRA_PROJECT_ASSIGNMENT.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("traPostActivityServiceImpl");
		}
		else if (transactionId.equals(TrxType.TRA_LECTURING.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("traPostActivityServiceImpl");
		}
		else if (transactionId.equals(TrxType.TRA_KNOWLEDGE_SHARING.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("traPostActivityServiceImpl");
		}
		else if (transactionId.equals(TrxType.TRA_CLAIM_SETTLEMENT.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("claimSettlementServiceImpl");
		}
		else if (transactionId.equals(TrxType.TRA_DEVELOPMENT_RESULT.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("developmentResultServiceImpl");
		}
		else if (transactionId.equals(TrxType.TRA_EEVALUATION_AFTER.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("eEvaluationService");
		}
		// end added by WLY for training admin
		else if (transactionId.equals(TrxType.CAM_CAREER_ASSESSMENT_PAYMENT.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("assessmentPaymentServiceImpl");
		}
		else if (transactionId.equals(TrxType.PEM_COE_REQUEST.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("performanceCoeRequestServiceImpl");
		}
		else if (transactionId.equals(TrxType.CAM_CAREER_DROP_IDP.getCode())){
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("idpDropServiceImpl");
		}
		else if (transactionId.equals(TrxType.BTR_PLANNING_REQUEST.getCode())) { //Add By HBP Planning Request Business Trip
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("planningRequestServiceImpl");
		} //End Add By HBP Planning Request Business Trip
		else if (transactionId.equals(TrxType.SB_INFORMATION.getCode())) {
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("informationServiceImpl");
		}
		else if(transactionId.equals(TrxType.STOCK_OPNAME.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("stockOpnameService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("stockOpnameService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_SELL.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_GRANT.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_INSURANCE.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_LOSE.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_BAST.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementBastService");
		}
		else if(transactionId.equals(TrxType.ASSET_TRANSFER.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("assetTransferService");
		}
		else if(transactionId.equals(TrxType.ASSET_TRANSFER_NON_EBS.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("assetTransferNonEBSService");
		}
		else if(transactionId.equals(TrxType.ASSET_REGISTRATION.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("assetRegistrationService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_NON_EBS.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementNonEBSService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_SELL_NON_EBS.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementNonEBSService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_GRANT_NON_EBS.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementNonEBSService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_INSURANCE_NON_EBS.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementNonEBSService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_LOSE_NON_EBS.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementNonEBSService");
		}
		else if(transactionId.equals(TrxType.RETIREMENT_BAST_NON_EBS.getCode())){
			//WorkflowService WorkflowService =(WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementService");
			return (WorkflowService)ApplicationContextUtil.getApplicationContext().getBean("retirementNonEBSBastService");
		}
		
		return null;
	}
}
