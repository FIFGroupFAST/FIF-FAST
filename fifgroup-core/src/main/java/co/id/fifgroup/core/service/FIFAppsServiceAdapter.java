package co.id.fifgroup.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import co.id.fifgroup.core.domain.interfacing.DepositHistoryInterface;
import co.id.fifgroup.core.domain.interfacing.DepositInterface;
import co.id.fifgroup.core.domain.interfacing.FIFAppsInterfaceException;
import co.id.fifgroup.core.domain.interfacing.GlInterface;
import co.id.fifgroup.core.domain.interfacing.HutangKoperasi;
import co.id.fifgroup.core.domain.interfacing.InvoiceDetail;
import co.id.fifgroup.core.domain.interfacing.InvoiceInterface;
import co.id.fifgroup.core.domain.interfacing.IuranKoperasi;
import co.id.fifgroup.core.domain.interfacing.MutationInterface;
import co.id.fifgroup.core.domain.interfacing.Prepayment;
import co.id.fifgroup.core.domain.interfacing.PrepaymentInterface;
import co.id.fifgroup.core.domain.interfacing.Rv;
import co.id.fifgroup.core.domain.interfacing.RvInterface;
import co.id.fifgroup.core.domain.interfacing.SettlementInterface;
import co.id.fifgroup.core.domain.interfacing.TabunganKoperasi;
import co.id.fifgroup.core.domain.interfacing.TaxInterface;
import co.id.fifgroup.core.dto.BudgetActivityLogDTO;


public interface FIFAppsServiceAdapter {
	
	public String settleWithoutPrepayment(SettlementInterface settlementInterface) throws FIFAppsInterfaceException ;
	
	//public SettlementInterface settleWithPrepayment(SettlementInterface settlementInterface) throws FIFAppsInterfaceException;
	
	public String createRV(Prepayment prepayment,String userId);
	
	public String createInvoice(InvoiceInterface invoiceInterface) throws FIFAppsInterfaceException;
	
	public String prepaymentProcess(PrepaymentInterface prepaymentInterface) throws FIFAppsInterfaceException;
	
	public void createJournalMutation(MutationInterface mutationInterface) throws FIFAppsInterfaceException;

	public boolean validateJournalMutation(String claimNumber, String employeeNumber, String companyCode, String beanchCode);
	
	public String createInvoice(List<InvoiceInterface> invoiceInterfaces) throws FIFAppsInterfaceException;
	
	public void createJournal(GlInterface glInterface) throws FIFAppsInterfaceException;
	
	public Long getGlBookId();
	
	public void createPPH(TaxInterface taxInterface) throws FIFAppsInterfaceException;
	
	public void updateTrnInvoiceDetail(InvoiceDetail invoiceDetail);
	
	public String generateDepositNumber(String branchCode, Long moduleId);
	
	public void insertRvTrnDeposit(DepositInterface depositInterface);
	
	public void insertRvTrnDepositHistory(DepositHistoryInterface depositHistoryInterface);
	
	public void updateLoanEmployeeByEmployeeNumberAndContractNumber(String username, String employeeNumber, String contractNumber);
	
	public void updateInsentifByEmployeeNumber(Date processDate, String employeeNumber, String instfFlag);
	
	public BigDecimal getInsentifByEmployeeNumber(String employeeNumber);
	
	public List<IuranKoperasi> selectIuranKoperasiByCompanyId(Long companyId, Date processDate);
	
	public void updateIuranKoperasi(String employeeNumber,String savProdTypeId,String userName, Integer savSequence, String fifFlag);
	
	public void createRv(RvInterface rvInterface) throws FIFAppsInterfaceException;

	public SettlementInterface settlementProcess(SettlementInterface settlementInterface) throws FIFAppsInterfaceException;
	
	public Rv bookRVNumber(String branchCode, String createdBy);
	
	public void updateHutangKoperasiByNpkAndContractNumber(HutangKoperasi hutangKoperasi);
	
	public List<HutangKoperasi> selectPotonganKoperasiByCompanyId(Long companyId, Date processDate);
	
	public String createInvoiceWithoutSequence(List<InvoiceInterface> invoiceInterfaces) throws FIFAppsInterfaceException;
	
	public void updateActivityCode(String activityCode, String invoiceNo);
	
	public void updateActivityCodeDetail(String activityCode, String invoiceNo, Integer sequence, BigDecimal amount);
	
	public void insertBudgetActivityLog(BudgetActivityLogDTO budgetActivityLog);
	
	public void updateGlInterfaceAttribute1ByReference4(String attribute1, String reference4);
	
	public SettlementInterface settlementProcessWithoutMutation(SettlementInterface settlementInterface) throws FIFAppsInterfaceException;
	
	public void createJournalMutationTransfer(MutationInterface mutationInterface) throws FIFAppsInterfaceException;
	
	//add by lisa,1-sep-2015,tiket - 14102810453648//
	public List<TabunganKoperasi> selectTabunganKoperasiByCompanyId(Long companyId, Date processDate);
	public void updateTabunganKoperasiByNpkAndContractNumber(TabunganKoperasi tabunganKoperasi);
	
	public double getTaxPercentageByTaxCode(String taxCode);
	public String getTaxRemarksByTaxCode(String taxCode);
	
	// start added by WLY, create prepayment dengan detail lebih dari satu, 29 Dec 2015
	public String prepaymentProcess(List<PrepaymentInterface> prepaymentInterfaces) throws FIFAppsInterfaceException;
	// end added by WLY
	
	// start added by Jatis, 5 Jan 2016
	// create invoice dengan invoice detail yang memiliki branch code lebih dari satu
	public String createInvoice(List<InvoiceInterface> invoiceInterfaces, String branchCode) throws FIFAppsInterfaceException;
	
	// update gl berdasarkan invoice number dan branch code
	public void updateGlInterfaceAttribute1ByReference4AndSegment2(String attribute1, String reference4, String segment2);
	// end added by Jatis, 5 Jan 2016
	
	//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 01-July-2016
	public String checkNullSegment2(String segment2);
	//end by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 01-July-2016

}
