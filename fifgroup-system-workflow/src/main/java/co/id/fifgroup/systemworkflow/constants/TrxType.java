package co.id.fifgroup.systemworkflow.constants;

public enum TrxType {

	SELECT(null, "- Select -"), 
	DEFAULT(0L, "Default"), 
	BEN_NON_PREPAYMENT(10L, "BEN Benefit Non Prepayment"), 
	BEN_PREPAYMENT(11L, "BEN Benefit Prepayment"), 
	BEN_SETTLEMENT(12L, "BEN Benefit Settlement"), 
	BTR_PLANNING_REQUEST(600L, "BTR Business Trip Planning Request"),
	BTR_REQUEST(14L, "BTR Business Trip Request"), 
	BTR_SETTLEMENT(15L, "BTR Business Trip Settlement"), 
	BTR_TICKETING_BOOKING_INVOICE(16L, "BTR Business Trip Ticketing Booking Invoice"), 
	BTR_VOUCHER_BOOKING_INVOICE(17L, "BTR Business Trip Voucher Booking Invoice"), 
	CAM_CAREER_ASSESSMENT_PAYMENT(470L,"CAM Career Assessment Payment"),
	CAM_CAREER_IDP_EMPLOYEE(430L,"CAM Career IDP Employee"),
	CAM_CAREER_IDP_EMPLOYEE_NEW_COE_REQUEST(450L,"CAM Career IDP Employee with New Calendar of Event Request"),
	CAM_CAREER_IDP_EMPLOYEE_NEW_DEVELOPMENT_REQUEST(440L,"CAM Career IDP Employee With New Development Name Request"),
	CAM_CAREER_IDP_EMPLOYEE_UPDATE(460L,"CAM Career IDP Employee Update"),
	CAM_CAREER_ICP_SUBMISSIONS(410L,"CAM Career ICP Submissions"),
	CAM_CAREER_DROP_IDP(480L,"CAM Career Drop IDP"),
	// Rachmad Agus D. untuk module E-Psikotest
	CWK_PKWT_EXTENTIONS(130L,"CWK Contingent Worker PKWT Extentions"),
	CWK_TERMINATION_REQUEST(121L,"CWK Contingent worker termination request"),
	CWK_TERMINATION_REVERSE(123L,"CWK Contingent worker termination reverse"),
	CWK_TRANSFER_WITHIN_GROUP(151L,"CWK Contingent Worker Transfer Within Group"),
	CWK_TRANSFER_REQUEST(171L,"CWK Contingent Worker Transfer"),
	CWK_HIRE_NEW_CWK(120L,"CWK Contingent working hire new cwk"),
	DIL_SP_CANCELLATION(112L, "DIL Disciplinary Letter Cancellation"),
	DIL_SP_EVALUATION(111L, "DIL Disciplinary Letter Evaluation"),
	HOU_BOARDING_REQUEST(110L,"HOU Boarding Request"),
	HOU_CONTRACT_REQUEST(122L, "HOU Housing Contract Request"),
	HOU_PLACEMENT_REQUEST(102L,"HOU Housing Placement Request"),
	COURSE_REQUEST(214L,"ILE Course Request"),
	ILE_POINT_REDEMPTION(172L,"ILE Point Redemption Request"),
	UPLOAD_MODULE_FILE(160L,"ILE Upload Module File"),
	LEA_CANCELATION_REQUEST(9L,	"LEA Leave Cancelation Request"), 
	LEA_REQUEST(8L, "LEA Leave Request"), 
	LOA_REQUEST(4L, "LOA Loan Request"), 
	MPP_EMPLOYEE_REQUISITION(6L, "MPP Employee Requisition"), 
	MPP_OPTIMUM_REQUEST(5L, "MPP Optimum Request"), 
	OCOP_REQUEST(101L,"OCOP Request"),	
	PAY_UPLOAD_ADDITIONAL_PAYMENT(19L, "PAY Upload Additional Payment"),
	PEM_PERFORMANCE_DEVELOPMENT_FEEDBACK(420L, "PEM Performance Development Feedback"), 
	PEM_PERFORMANCE_IPP_BUSINESS_RESULTS(421L, "PEM Performance Ipp Business Results"),
	PEM_COE_REQUEST(115L, "PEM Performance COE Request"),
	PRO_ACTING_REVIEW(18L,"PRO Acting Review"), 
	PRO_IRREGULAR_REQUEST(1L, "PRO Irregular Promotion Request"), 
	PSI_PARTICIPANT_LOGIN_REQUEST(100L, "PSI E-Psikotest Participant Login Request"),
	REC_DOCUMENT_CHECKING(13L, "REC Document Checking"), 
	TER_REQUEST(3L, "TER Termination Request"), 
	TMS_OVERTIME_REQUEST(7L, "TMS Overtime Request"), 
	TRA_CLAIM_SETTLEMENT(570L, "TRA Claim and Settlement"),
	TRA_COE_REQUEST(530L, "TRA COE Request"), 
	TRA_DEV_PROPOSAL_SUBMIT(510L, "TRA Development Proposal Submit"),
	TRA_DEVELOPMENT_RESULT(580L, "TRA Development Result"),
	TRA_EEVALUATION_AFTER(590L, "TRA E-Evaluation After"),
	TRA_KNOWLEDGE_SHARING(550L, "TRA Knowledge Sharing"), 
	TRA_LECTURING(560L, "TRA Lecturing"), 
	TRA_PREPAYMENT_ENTRY(520L, "TRA Prepayment Entry"),
	TRA_PROJECT_ASSIGNMENT(540L, "TRA Project Assignment"), 
	TRF_SINGLE(	2L, "TRF Transfer Single"), 
	VIP_ACTIVITY(87L, "VIP Value Internalization Point Activity"),
	WEQ_INVENTORY_TRN(113L,"WEQ Working Equipment Inventory Transaction"),
	WEQ_ORDER_TRN(114L,"WEQ Working Equipment Order Transaction"),
	
	//Sandbox
	SB_INFORMATION(700L,"Sandbox Information"),
	//SSOA
	STOCK_OPNAME(180L,"Stock Opname"),
	ASSET_TRANSFER(181L,"Asset Transfer"),
	SO_PERIOD_NOTIFICATION(182L,"SO Period Notification"),
	RETIREMENT(190L,"Retirement"),
	RETIREMENT_BAST(200L,"Retirement BAST"),
	SO_PERIOD_RESEND_NOTIFICATION(220L,"SO Period Resend Notification"),
	ASSET_TRANSFER_NOTIFICATION(280L,"Asset Transfer Notification"),
	NOTIFICATION_SO_INPUT(210L,"Notification SO Input"),
	NOTIFICATION_SO_BRANCH(230L,"Notification SO Branch"),
	RETIREMENT_SELL(240L,"Retirement Penjualan"),
	RETIREMENT_GRANT(250L,"Retirement Hibah"),
	RETIREMENT_INSURANCE(260L,"Retirement Asuransi"),
	RETIREMENT_LOSE(270L,"Retirement Tidak Ditemukan"),
	ASSET_REGISTRATION(300L,"Asset Registration"),
	ASSET_TRANSFER_NON_EBS(310L,"Asset Non EBS Transfer"),
	RETIREMENT_NON_EBS(360L,"Retirement Non EBS"),
	RETIREMENT_BAST_NON_EBS(370L,"Retirement Non EBS BAST"),
	RETIREMENT_SELL_NON_EBS(320L,"Retirement Non EBS Penjualan"),
	RETIREMENT_GRANT_NON_EBS(330L,"Retirement Non EBS Hibah"),
	RETIREMENT_INSURANCE_NON_EBS(340L,"Retirement Non EBS Asuransi"),
	RETIREMENT_LOSE_NON_EBS(350L,"Retirement Non EBS Tidak Ditemukan");
	
	
	private Long code;
	private String message;

	private TrxType(Long code, String message) {
		this.code = code;
		this.message = message;
	}

	public Long getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static TrxType fromCode(Long code) {
		for (TrxType trxType : TrxType.values()) {
			if (trxType.getCode() != null && trxType.getCode().equals(code)) {
				return trxType;
			}
		}
		return null;
	}
}
