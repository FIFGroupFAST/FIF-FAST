package co.id.fifgroup.personneladmin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.ui.NavigationTransactionForm;

@VariableResolver(DelegatingVariableResolver.class)
public class TransactionInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(TransactionInquiryComposer.class);
	
	private TransactionInquiryComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private A linkAttendance;
	@Wire
	private A linkBenefit;
	@Wire
	private A linkBusinessTrip;
	@Wire
	private A linkIrregularPromotion;
	@Wire
	private A linkLeave;
	@Wire
	private A linkLoan;
	@Wire
	private A linkTransfer;
	@Wire
	private A linkOvertime;
	
	private PersonalInfoMainComposer parentComposer;
	private NavigationTransactionForm navigation;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		parentComposer = (PersonalInfoMainComposer) arg.get("parentPage");
		navigation = (NavigationTransactionForm) arg.get("workspaceMain");
		params.put("personId", parentComposer.getPersonMainDTO().getPersonDTO().getPersonId());
		params.put("companyId", parentComposer.getPersonMainDTO().getPersonDTO().getCompanyId());
	}
	
	@Listen("onClick = #linkAttendance")
	public void onClickAttendance() {		
		navigation.navigateForm("~./hcms/time-management/search_employee_from_personal_information.zul", "Time Management", params);
	}
	
	@Listen("onClick = #linkBenefit")
	public void onClickBenefit() {		
		navigation.navigateForm("~./hcms/benefit/benefit_transaction_history.zul", "Benefit", params);
	}
	
	@Listen("onClick = #linkBusinessTrip")
	public void onClickBusinessTrip() {		
		navigation.navigateForm("~./hcms/business-trip/btr_transaction_history.zul", "Business Trip", params);
	}
	
	@Listen("onClick = #linkIrregularPromotion")
	public void onClickIrregularPromotion() {
		navigation.navigateForm("~./hcms/promotion/promotion_transaction_history_employee.zul", "Promotion", params);
	}
	
	@Listen("onClick = #linkLeave")
	public void onClickLeave() {
		navigation.navigateForm("~./hcms/leave/search_leave_request_from_personnel_information.zul", "Leave", params);
	}
	
	@Listen("onClick = #linkLoan")
	public void onClickLoan() {
		navigation.navigateForm("~./hcms/loan/loan_transaction_history.zul", "Loan", params);
	}
	
	@Listen("onClick = #linkTransfer")
	public void onClickTransfer() {
		navigation.navigateForm("~./hcms/transfer/transfer_transaction_history_employee.zul", "Transfer", params);
	}
	
	@Listen("onClick = #linkOvertime")
	public void onClickOvertime() {
		navigation.navigateForm("~./hcms/time-management/tms_transaction_history.zul", "Time Management", params);
	}
	
}
