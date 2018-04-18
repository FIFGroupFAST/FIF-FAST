package co.id.fifgroup.systemadmin.constant;

public enum TaskRunnerList {
	
//	LEA_CASHABLE_ENTITLEMENT(1),
//	LEA_DAILY_ENTITLEMENT_SOURCE(1),
//	LEA_DAILY_ENTITLMENT_CHILD(1),
	LEA_MONTHLY_ENTITLEMENT(10116L, "co.id.fifgroup.leave.batch.MonthlyEntitlement"),//Simplified
	LEA_YEARLY_ENTITLEMENT(10117L, "co.id.fifgroup.leave.batch.YearlyEntitlement"),//Simplified
	LEA_ONE_TIME_MONTHLY_ENTITLEMENT(10118L, "co.id.fifgroup.leave.batch.OneTimeMonthlyEntitlement"),//Simplified
	LEA_ONE_TIME_ONLY_ENTITLEMENT(10121L, "co.id.fifgroup.leave.batch.OneTimeOnlyEntitlement"),//Simplified
	LEA_ONE_TIME_REQUEST_ENTITLEMENT(10122L,"co.id.fifgroup.leave.batch.OneTimeRequestEntitlement"),//Simplified
	LEA_ONE_TIME_SERVICE_PERIOD_ENTITLEMENT(10120L, "co.id.fifgroup.leave.batch.OneTimeServicePeriodEntitlement"),//Simplified
	LEA_ONE_TIME_YEARLY_ENTITLEMENT(10119L, "co.id.fifgroup.leave.batch.OneTimeYearlyEntitlement"),//Simplified
//	LEA_RECALCULATE_ENTITLEMENT(1),
	LEA_SERVICE_PERIOD_ENTITLEMENT(10124L, "co.id.fifgroup.leave.batch.YearlyEntitlement"),//Simplified
	LEA_STATE_VERSION_MONTHLY_ENTITLEMENT(10113L, "co.id.fifgroup.leave.batch.StateVersionMonthlyEntitlement"),//Simplified
	LEA_STATE_VERSION_SERVICE_PERIOD_ENTITLEMENT(10115L, "co.id.fifgroup.leave.batch.StateVersionServicePeriodEntitlement"),//Simplified
	LEA_STATE_VERSION_YEARLY_ENTITLEMENT(10114L, "co.id.fifgroup.leave.batch.StateVersionYearlyEntitlement"),//Simplified
	BEN_BENEFIT_MONTHLY_ENTITLEMENT(10125L,"co.id.fifgroup.benefit.batch.BenefitMonthlyEntitlement"),
	BEN_BENEFIT_YEARLY_ENTITLEMENT(10126L, "co.id.fifgroup.benefit.batch.BenefitYearlyEntitlement"),
	BEN_BENEFIT_STATE_VERSION_MONTHLY_ENTITLEMENT(10127L, "co.id.fifgroup.benefit.batch.BenefitStateVersionMonthlyEntitlement"),//Simplified
	BEN_BENEFIT_STATE_VERSION_YEARLY_ENTITLEMENT(10128L, "co.id.fifgroup.benefit.batch.BenefitStateVersionYearlyEntitlement"),//Simplified
	BEN_BENEFIT_ONE_TIME_ONLY_ENTITLEMENT(10129L, "co.id.fifgroup.benefit.batch.BenefitOneTimeOnlyEntitlement"),//Simplified
	BEN_BENEFIT_ONE_TIME_REQUEST_ENTITLEMENT(10130L, "co.id.fifgroup.benefit.batch.BenefitOneTimeRequestEntitlement"),//Simplified
	BEN_BENEFIT_DAILY_ENTITLEMENT(10130L, "co.id.fifgroup.benefit.batch.BenefitDailyEntitlement"),//Simplified
	PAY_UPDATE_STATUS_AFTER_CLOSING(10207L,"co.id.fifgroup.payroll.batch.UpdateOtherModuleAfterClosing"),
//	BTR_SETTLEMENT_REMINDER(1),
//	BTR_TAXI_VOUCHER_REQUEST_NOTIFICATION(1),
//	BTR_TICKET_RESERVATION_NOTIFICATION(1),
	PAY_PAYMENT_CYCLE(10131L,"co.id.fifgroup.payroll.batch.PayrollCalculation"),//Simplified
//	PAY_EMPLOYEE_MASTER_DATA(1),
//	PAY_EMPLOYEE_MASTER_DATA_GENERATOR(1),
//	PAY_ESR_NOTIFICATION(1),
//	PAY_PAYROLL_PROCESS(1),
//	PEA_INTERFACING_TO_OTHER_SYSTEM(1),
//	PEA_PROBATION_NOTIFICATION(1),
//	PEA_PTKP_GENERATOR(1),
//	PRO_ACTING_REVIEW_NOTIFICATIONS(1),
//	PRO_PROMOTION_JOURNAL_MUTATION(1),
//	PRO_PROMOTION_TRANSACTION_PURGE(1),
//	SAD_DIRECT_RESPONSIBILITY_NOTIFICATION(1),
//	SAD_JOB_RESPONSIBILITY_SYNCHRONIZER(1),
//	SAD_UNUSED_FILE_REMOVER(1),
//	SWF_AUTO_APPROVE_REJECT_WORKFLOW_TRANSACTION(1),
//	SWF_REASSIGN_APPROVERS_BY_SYSTEM(1),
//	TMS_AUTO_UPLOAD_ATTENDANCE_TASK(1),
//	TMS_COMPARISON_PROCESS_TASK(1),
//	TRA_AUTO_CANCEL_TRANSFER_TRANSACTION_SERVICE_IMPL(1),
//	TRA_JOURNA_LMUTATION_SERVICE_IMPL(1);
	TMS_HOLIDAY_CALENDAR(10163L, "co.id.fifgroup.timemanagement.task.HolidayCalendarTask"),
	PAY_MANUAL_CALCULATION(0L,"co.id.fifgroup.payroll.batch.PayrollManualCalculation");

	
	private Long runnerId;
	private String className;

	private TaskRunnerList(Long runnerId, String className) {
		this.runnerId = runnerId;
		this.className = className;
	}
	

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}


	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}


	/**
	 * @return the runnerId
	 */
	public Long getRunnerId() {
		return runnerId;
	}

	/**
	 * @param runnerId the runnerId to set
	 */
	public void setRunnerId(Long runnerId) {
		this.runnerId = runnerId;
	}
	

	
	
	
}
